package simulations

object x {
	val es = new EpidemySimulator             //> es  : simulations.EpidemySimulator = simulations.EpidemySimulator@596e1fb1
	(1 to 100).map(_=>es.randomBelow(5)).toList.groupBy(identity).map(_._1)
                                                  //> res0: scala.collection.immutable.Iterable[Int] = List(0, 1, 2, 3, 4)
}