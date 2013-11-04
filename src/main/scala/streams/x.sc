package streams

object x {
  val e = List[Int]().toStream                    //> e  : scala.collection.immutable.Stream[Int] = Stream()
  e match {
  case Stream() => 0
  case _ => 1
  }                                               //> res0: Int = 0
  
  val s1 = List(1, 2, 3).toStream                 //> s1  : scala.collection.immutable.Stream[Int] = Stream(1, ?)
  val s2 = List(4, 5, 6).toStream                 //> s2  : scala.collection.immutable.Stream[Int] = Stream(4, ?)
  val s3 = s1 ++ s2                               //> s3  : scala.collection.immutable.Stream[Int] = Stream(1, ?)
	s3 match {
	case Stream() => 0
	case h #:: t => h
	}                                         //> res1: Int = 1
  s3.take(3).toList                               //> res2: List[Int] = List(1, 2, 3)

  val level =
    """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin                  //> level  : String = ooo-------
                                                  //| oSoooo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----ooToo
                                                  //| ------ooo-

  val levelVector = Vector(level.split("\n").map(str => Vector(str: _*)): _*)
                                                  //> levelVector  : scala.collection.immutable.Vector[scala.collection.immutable.
                                                  //| Vector[Char]] = Vector(Vector(o, o, o, -, -, -, -, -, -, -), Vector(o, S, o,
                                                  //|  o, o, o, -, -, -, -), Vector(o, o, o, o, o, o, o, o, o, -), Vector(-, o, o,
                                                  //|  o, o, o, o, o, o, o), Vector(-, -, -, -, -, o, o, T, o, o), Vector(-, -, -,
                                                  //|  -, -, -, o, o, o, -))

  levelVector.indexWhere((vec) => {
    val index = vec.indexOf('-')
    println(index)
    index >= 0
  })                                              //> 3
                                                  //| res3: Int = 0
}