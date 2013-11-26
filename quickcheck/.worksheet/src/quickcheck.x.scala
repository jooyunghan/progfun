package quickcheck

object x {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(47); 
	val x = List(1);System.out.println("""x  : List[Int] = """ + $show(x ));$skip(21); val res$0 = 
	x.sliding(2).toList;System.out.println("""res0: List[List[Int]] = """ + $show(res$0));$skip(54); val res$1 = 
	
	x.sliding(2).forall { pair => pair(0) <= pair(1) };System.out.println("""res1: Boolean = """ + $show(res$1))}
}
