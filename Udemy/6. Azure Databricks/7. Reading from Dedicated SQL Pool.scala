// For Staging Location
spark.conf.set(
    "fs.azure.account.key.datalakez95pevs1.dfs.core.windows.net",
    "rtzbGEf8o/0vWN2myNq8WhgmMdVodR6bjrpKdD4Vco1l4UITjrDJm03/mwRo3wh8unOo1mLoy3ch+AStxuCbOQ==")

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

