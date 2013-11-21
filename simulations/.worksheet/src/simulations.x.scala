package simulations

object x {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(62); 
	val es = new EpidemySimulator;System.out.println("""es  : simulations.EpidemySimulator = """ + $show(es ));$skip(73); val res$0 = 
	(1 to 100).map(_=>es.randomBelow(5)).toList.groupBy(identity).map(_._1);System.out.println("""res0: scala.collection.immutable.Iterable[Int] = """ + $show(res$0))}
}
