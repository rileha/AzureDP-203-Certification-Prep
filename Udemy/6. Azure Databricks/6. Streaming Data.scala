import org.apache.spark.sql.types.{StructType, StructField, StringType, IntegerType};

%sql
CREATE TABLE DimCustomer(
    CustomerID INT,
    CompanyName STRING,
	  SalesPerson STRING
)

%sql 
DROP TABLE DimCustomer

%sql
DELETE FROM DimCustomer

%sql
SELECT * FROM DimCustomer

%scala
spark.conf.set(
    "fs.azure.account.key.datalakez95pevs1.dfs.core.windows.net",
    "KEY")

val path="abfss://csv@datalakez95pevs1.dfs.core.windows.net/DimCustomer/"
val checkpointPath="abfss://checkpoint@datalakez95pevs1.dfs.core.windows.net/"
val schemaLocation="abfss://schema@datalakez95pevs1.dfs.core.windows.net/"

%scala
val dataSchema = StructType(Array(    
    StructField("CustomerID", IntegerType, true),
    StructField("CompanyName", StringType, true),
    StructField("SalesPerson", StringType, true)))

%scala
val dfDimCustomer=(spark.readStream.format("cloudfiles")
    .schema(dataSchema)
    .option("header", "true")
    // Defining Own Schema Now 
    //.option("cloudFiles.schemaLocation", schemaLocation)
    .option("cloudFiles.format","csv")
    .option("cloudFiles.schemaEvolutionMode", "none")
    .load(path))

val finalDimCustomer = dfDimCustomer.dropDuplicates("CustomerID")

finalDimCustomer.writeStream.format("delta")
.option("checkpointLocation",checkpointPath)
.option("mergeSchema", "true")
.table("DimCustomer")

%sql
SELECT * FROM DimCustomer

%sql
DESCRIBE HISTORY DimCustomer

%sql
SELECT * FROM DimCustomer VERSION as of 1

%sql
SELECT CustomerID,count(CustomerID)
FROM DimCustomer
GROUP BY CustomerID
HAVING count(CustomerID)>1

