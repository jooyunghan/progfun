package suggestions

import java.util.concurrent.SynchronousQueue
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.swing.event.Event
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import rx.lang.scala._
import rx.lang.scala.concurrency.Schedulers
import suggestions.observablex.ObservableEx
import java.util.concurrent.TimeUnit
import scala.collection.mutable.ListBuffer

@RunWith(classOf[org.scalatest.junit.JUnitRunner])
class ObservableExTest extends FunSuite {
  test("future to observable") {
    val q = new SynchronousQueue[Int]
    val f = future { 10 }
    val o = ObservableEx(f)
    o.observeOn(Schedulers.newThread).subscribe(q put _)
    expectResult(10)(q.poll)
  }

  test("future to observable:failure") {
    val vq = ListBuffer[Int]()
    val eq = ListBuffer[Error]()
    val f: Future[Int] = future { throw new Error("errormessage") }
    val o = ObservableEx(f)
    o.subscribe(v => vq += v, e => eq += e.getCause().asInstanceOf[Error])
    assert(vq.size == 0)
    expectResult(1)(eq.size)
  }
}