package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  // singleton, find min
  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  def toHeap(l: List[Int]) : H = {
    l.foldLeft(empty)((h, a) => insert(a, h))
  }
  def toList(h: H) : List[Int] = 
    if (isEmpty(h)) Nil
    else findMin(h) :: toList(deleteMin(h))

  property("sorted") = forAll { l: List[Int] =>
    l.sorted == toList(toHeap(l))
  }


}
