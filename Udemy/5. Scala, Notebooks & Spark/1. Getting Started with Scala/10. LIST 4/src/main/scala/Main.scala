@main def dataApp() =
  val customers:List[String] = List("UserA", "UserB", "UserC", "UserD")
  print(customers.indexOf("UserB"))