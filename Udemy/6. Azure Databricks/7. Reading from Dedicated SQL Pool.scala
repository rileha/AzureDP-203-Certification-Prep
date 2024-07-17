// For Staging Location
spark.conf.set(
    "fs.azure.account.key.datalakez95pevs1.dfs.core.windows.net",
    "KEY")

val df = spark.read
  .format("com.databricks.spark.sqldw")
  .option("url", "jdbc:sqlserver://sa-z95pevs.sql.azuresynapse.net:1433;database=datapool")
  .option("user","sqluser")
  .option("password","sqlpassword@123")
  .option("tempDir", "abfss://staging@datalakez95pevs1.dfs.core.windows.net/databricks")  
  .option("forwardSparkAzureStorageCredentials", "true")
  .option("dbTable", "BlobDiagnostics")
  .load()

display(df)

