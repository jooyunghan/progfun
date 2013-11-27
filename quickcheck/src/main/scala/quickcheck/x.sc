package quickcheck

object x {
	val x = List(1)                           //> x  : List[Int] = List(1)
	x.sliding(2).toList                       //> res0: List[List[Int]] = List(List(1))
	
	x.sliding(2).forall { pair => pair(0) <= pair(1) }
                                                  //> java.lang.IndexOutOfBoundsException: 1
                                                  //| 	at scala.collection.LinearSeqOptimized$class.apply(LinearSeqOptimized.sc
                                                  //| ala:52)
                                                  //| 	at scala.collection.immutable.List.apply(List.scala:84)
                                                  //| 	at quickcheck.x$$anonfun$main$1$$anonfun$1.apply(quickcheck.x.scala:7)
                                                  //| 	at quickcheck.x$$anonfun$main$1$$anonfun$1.apply(quickcheck.x.scala:7)
                                                  //| 	at scala.collection.Iterator$class.forall(Iterator.scala:739)
                                                  //| 	at scala.collection.AbstractIterator.forall(Iterator.scala:1157)
                                                  //| 	at quickcheck.x$$anonfun$main$1.apply$mcV$sp(quickcheck.x.scala:7)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at quickcheck.x$.main(quickcheck.x.scala:3)
                                                  //| 	at quickcheck.x.main(quickcheck.x.scala)
}