package streams

object x {
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

  levelVector.indexWhere( (vec) => {
  	val index = vec.indexOf('-')
  	println(index)
  	index >= 0
  } )                                             //> 3
                                                  //| res0: Int = 0
}