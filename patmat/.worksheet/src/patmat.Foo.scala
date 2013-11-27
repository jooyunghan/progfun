package patmat

import patmat.Huffman._



class Foo(x:Int) {
  def method() = {
   -1
  }
}


new Foo().method()

object Foo {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(147); 
  def method() = -1;System.out.println("""method: ()Int""")}
}

Foo.method();


object exercise {

// call by value
// call by name
  def foo(pred: boolean, trueValue: => Int, falseValue: => Int) = x + y
  foo(1, 3 + 4)
  
  
  
  // Haskell : expression lazy

  List(1, 2, 3) ++ List(4, 5)

  val tree = Fork(Fork(Leaf('a', 1), Leaf('b', 1), List('a', 'b'), 2), Leaf('c', 2), List('a', 'b', 'c'), 4)
  tree.left
  convert(tree)

  quickEncode(tree)(List('a', 'b'))
  decode(tree, List(0, 0))
  encode(tree)(List('a', 'b'))

}

class Node {
   private int value;
   private Node next;
   public Node(int value, Node next) {
		this.value = value;
		this.next = next;
   }
}

trait Base {
  def foo(x:Int) = bar(x) + bar(x+1)
  def bar(x:Int)
}

class Node(value:Int, next:Node) extends Base {
    
}
