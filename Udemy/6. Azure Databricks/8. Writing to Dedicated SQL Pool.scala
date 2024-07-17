%scala
import org.apache.spark.sql.types._

val path="abfss://csv@datalakez95pevs1.dfs.core.windows.net/DimCustomer/"
val checkpointPath="abfss://checkpoint@datalakez95pevs1.dfs.core.windows.net/"

val dataSchema = StructType(Array(    
    StructField("CustomerID", IntegerType, true),
    StructField("CompanyName", StringType, true),
    StructField("SalesPerson", StringType, true)))

%scala
spark.conf.set(
    "fs.azure.account.key.datalakez95pevs1.dfs.core.windows.net",
    "rtzbGEf8o/0vWN2myNq8WhgmMdVodR6bjrpKdD4Vco1l4UITjrDJm03/mwRo3wh8unOo1mLoy3ch+AStxuCbOQ==")

val dfDimCustomer=(spark.readStream.format("cloudfiles")
    .schema(dataSchema)    
    .option("cloudFiles.format","csv")
    .option("header",true)
    .load(path))

dfDimCustomer.writeStream
  .format("com.databricks.spark.sqldw")
  .option("url", "jdbc:sqlserver://sa-z95pevs.sql.azuresynapse.net:1433;database=datapool")
  .option("user","sqladminuser")
  .option("password","sqlpassword@123")
  .option("tempDir", "abfss://staging@datalakez95pevs1.dfs.core.windows.net/databricks")  
  .option("forwardSparkAzureStorageCredentials", "true")
  .option("dbTable", "DimCustomer") 
  .option("checkpointLocation", checkpointPath) 
  .start()

