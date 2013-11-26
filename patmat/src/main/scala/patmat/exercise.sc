package patmat

import patmat.Huffman._

object exercise {

  def sumList0(list: List[Int]): Int = {
    if (list == List()) 0
    else list.head + sumList0(list.tail)
  }                                               //> sumList0: (list: List[Int])Int

  def sumList(list: List[Int]): Int = {
    def loop(sum: Int, list: List[Int]): Int = {
      if (list == List()) sum
      else loop(sum + list.head, list.tail)
    }
    loop(0, list)
  }                                               //> sumList: (list: List[Int])Int

  def product(list: List[Int]): Int = {
    def loop(acc: Int, list: List[Int]): Int = {
      if (list == List()) acc
      else loop(acc * list.head, list.tail)
    }
    loop(1, list)
  }                                               //> product: (list: List[Int])Int
  
  List(1,2,3,4).foldRight(1)(_ * _)
                                                  //> res0: Int = 24
}