@main def dataApp() =
  val numbers:List[Int] = List(10, 20, 20, 40)
  println("The head of the list is " + numbers.head)
  numbers.foreach{println}