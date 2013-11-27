package patmat

import patmat.Huffman._

// Expression Problem :
// 1) more types _ > object oriented wins, functional loses
// 2) more operations -> object oriented loses, functional wins
/*
trait Expr {
  def evaluate : Int
  def countTerms : Int
}

class Num(value:Int) extends Expr {
  override def toString = value.toString
  def evaluate: Int = value
  def countTerms = 1
}

class Sum(left:Expr, right:Expr) extends Expr {
  override def toString = left.toString + " + " + right.toString
  def evaluate:Int = left.evaluate + right.evaluate
  def countTerms = left.countTerms + right.countTerms
}

class Prod(left:Expr, right:Expr) extends Expr {
  override def toString = left.toString + " x " + right.toString
  def evaluate:Int = left.evaluate * right.evaluate
  def countTerms = left.countTerms + right.countTerms
}
*/

trait Expr {
}

case class Num(value: Int) extends Expr {
}

case class Sum(left: Expr, right: Expr) extends Expr {
}

object exercise {
  def evaluate(e: Expr): Int = e match {
  case Num(x) => x
  }
  	
  def countTerms(e: Expr): Int =
  evaluate(Sum(Num(2), Num(3))  )

}
