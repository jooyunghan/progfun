package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int =
    if (c == 0 || c == r) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)

  /**
   * Exercise 2
   *
   * d(0) ---> d(1) ---> d(2) ---> d(3) ...
   *      <---      <---      <---
   */
  def balance(chars: List[Char]): Boolean = {
    def inner(d: Int, chars: List[Char]): Boolean =
      if (d < 0) false
      else if (chars.isEmpty) d == 0
      else if (chars.head == '(') inner(d + 1, chars.tail)
      else if (chars.head == ')') inner(d - 1, chars.tail)
      else inner(d, chars.tail)
    inner(0, chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def inner(money: Int, coins: List[Int]): Int =
      if (money == 0) 1
      else if (coins.isEmpty || money < coins.head) 0
      else inner(money - coins.head, coins) + inner(money, coins.tail)
    inner(money, coins.sorted)
  }

}
