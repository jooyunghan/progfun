package patmat

import patmat.Huffman._

object exercise {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(171); 

  def sumList0(list: List[Int]): Int = {
    if (list == List()) 0
    else list.head + sumList0(list.tail)
  };System.out.println("""sumList0: (list: List[Int])Int""");$skip(192); 

  def sumList(list: List[Int]): Int = {
    def loop(sum: Int, list: List[Int]): Int = {
      if (list == List()) sum
      else loop(sum + list.head, list.tail)
    }
    loop(0, list)
  };System.out.println("""sumList: (list: List[Int])Int""");$skip(192); 

  def product(list: List[Int]): Int = {
    def loop(acc: Int, list: List[Int]): Int = {
      if (list == List()) acc
      else loop(acc * list.head, list.tail)
    }
    loop(1, list)
  };System.out.println("""product: (list: List[Int])Int""");$skip(39); val res$0 = 
  
  List(1,2,3,4).foldRight(1)(_ * _);System.out.println("""res0: Int = """ + $show(res$0))}
}
