@main def dataApp() =
  val customers:List[String] = List("UserA", "UserB", "UserC", "UserD")
  customers.foreach{println}
  println("The customer at index number 2 is " + customers(2))