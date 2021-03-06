package nodescala

import scala.language.postfixOps
import scala.util.{ Try, Success, Failure }
import scala.collection._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.async.Async.{ async, await }
import org.scalatest._
import NodeScala._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class NodeScalaSuite extends FunSuite {

  test("fail-fast sequence") {
    val f: Future[List[Int]] = Future.sequence2(List(1000, 2000, 500).map(sleep => Future {
      Thread.sleep(sleep)
      throw new Error("" + sleep)
    }))
    val g = f continue {
      case Success(v) => "success"
      case Failure(f:ExecutionException) => f.getCause().getMessage()
      case _ => "error"
    }
    expectResult("500")(Await.result(g, 1 second))
  }

  test("A Future should always be created") {
    val always = Future.always(517)

    assert(Await.result(always, 0 nanos) == 517)
  }

  test("A Future should never be created") {
    val never = Future.never[Int]

    try {
      Await.result(never, 1 second)
      assert(false)
    } catch {
      case t: TimeoutException => // ok!
    }
  }

  test("A list of future transformed into a future of list") {
    val list = List(1, 2, 3, 4).map(Future.always(_))
    val result = Await.result(Future.all(list), 1 second)
    assert(result == List(1, 2, 3, 4))
  }

  test("Any future which comes first wins") {
    val list = List(3, 1, 2, 4).map(i => future {
      blocking {
        Thread.sleep(i * 1000)
      }
      i
    })
    val result = Await.result(Future.any(list), 2 second)
    expectResult(1)(result)
  }

  test("delay") {
    val f = Future.delay(1 second)
    blocking {
      Thread.sleep(500)
    }
    assert(!f.isCompleted)
    blocking {
      Thread.sleep(510)
    }
    assert(f.isCompleted)
  }

  test("future now will throw if not completed or failed") {
    intercept[NoSuchElementException] {
      Future.never.now
    }
    intercept[NoSuchElementException] {
      Future.delay(1 millis).now
    }
    intercept[NoSuchElementException] {
      future { throw new Error() }.now
    }
    expectResult(1)(Future.always(1).now)
  }

  test("continueWith") {
    val f = future { 3 }.continueWith(f => 1 + Await.result(f, 1 second))
    expectResult(4)(Await.result(f, 1 second))

    val f2 = Future.failed(new Error).continueWith(f => 1)
    expectResult(4)(Await.result(f, 1 second))

    val n = Future.never
    val f3 = n.continueWith(f => 1)
    intercept[TimeoutException] {
      Await.result(f3, 1 second)
    }
  }

  test("CancellationTokenSource should allow stopping the computation") {
    val cts = CancellationTokenSource()
    val ct = cts.cancellationToken
    val p = Promise[String]()

    async {
      while (ct.nonCancelled) {
        // do work
      }

      p.success("done")
    }

    cts.unsubscribe()
    assert(Await.result(p.future, 1 second) == "done")
  }

  class DummyExchange(val request: Request) extends Exchange {
    @volatile var response = ""
    val loaded = Promise[String]()
    def write(s: String) {
      response += s
    }
    def close() {
      loaded.success(response)
    }
  }

  class DummyListener(val port: Int, val relativePath: String) extends NodeScala.Listener {
    self =>

    @volatile private var started = false
    var handler: Exchange => Unit = null

    def createContext(h: Exchange => Unit) = this.synchronized {
      assert(started, "is server started?")
      handler = h
    }

    def removeContext() = this.synchronized {
      assert(started, "is server started?")
      handler = null
    }

    def start() = self.synchronized {
      started = true
      new Subscription {
        def unsubscribe() = self.synchronized {
          started = false
        }
      }
    }

    def emit(req: Request) = {
      val exchange = new DummyExchange(req)
      if (handler != null) handler(exchange)
      exchange
    }
  }

  class DummyServer(val port: Int) extends NodeScala {
    self =>
    val listeners = mutable.Map[String, DummyListener]()

    def createListener(relativePath: String) = {
      val l = new DummyListener(port, relativePath)
      listeners(relativePath) = l
      l
    }

    def emit(relativePath: String, req: Request) = this.synchronized {
      val l = listeners(relativePath)
      l.emit(req)
    }
  }

  test("Listener should serve the next request as a future") {
    val dummy = new DummyListener(8191, "/test")
    val subscription = dummy.start()

    def test(req: Request) {
      val f = dummy.nextRequest()
      dummy.emit(req)
      val (reqReturned, xchg) = Await.result(f, 1 second)

      assert(reqReturned == req)
    }

    test(immutable.Map("StrangeHeader" -> List("StrangeValue1")))
    test(immutable.Map("StrangeHeader" -> List("StrangeValue2")))

    subscription.unsubscribe()
  }

  test("Server should serve requests") {
    val dummy = new DummyServer(8191)
    val dummySubscription = dummy.start("/testDir") {
      request =>
        for (kv <- request.iterator) yield (kv + "\n").toString
    }

    // wait until server is really installed
    Thread.sleep(500)

    def test(req: Request) {
      val webpage = dummy.emit("/testDir", req)
      val content = Await.result(webpage.loaded.future, 1 second)
      val expected = (for (kv <- req.iterator) yield (kv + "\n").toString).mkString
      assert(content == expected, s"'$content' vs. '$expected'")
    }

    test(immutable.Map("StrangeRequest" -> List("Does it work?")))
    test(immutable.Map("StrangeRequest" -> List("It works!")))
    test(immutable.Map("WorksForThree" -> List("Always works. Trust me.")))

    dummySubscription.unsubscribe()
  }
}




