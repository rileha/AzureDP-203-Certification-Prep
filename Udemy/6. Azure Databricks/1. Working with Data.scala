import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

val file_location = "/FileStore/tables/csv/ActivityLog01.csv"
val file_type = "csv"

val dataSchema = StructType(Array(    
    StructField("Correlationid", StringType, true),
    StructField("Operationname", StringType, true),
    StructField("Status", StringType, true),
    StructField("Eventcategory", StringType, true),
    StructField("Level", StringType, true),
    StructField("Time", TimestampType, true),
    StructField("Subscription", StringType, true),
    StructField("Eventinitiatedby", StringType, true),
    StructField("Resourcetype", StringType, true),
    StructField("Resourcegroup", StringType, true),
    StructField("Resource", StringType, true)))

val df = spark.read.format(file_type).
options(Map("header"->"true")).
schema(dataSchema).
load(file_location)
display(df)

display(df.groupBy(df("Operationname")).count())

display(df.groupBy(df("Operationname")).count().alias("Count").filter(col("Count")>100))

display(df.select(year(col("time")),month(col("time")),dayofyear(col("time"))))

display(df.select(year(col("time")).alias("Year"),month(col("time")).alias("Month"),dayofyear(col("time")).alias("Day of Year")))

display(df.select(to_date(col("time"),"dd-mm-yyyy").alias("Date")))

val dfNull=df.filter(col("Resourcegroup").isNull)
dfNull.count()

val dfNotNull=df.filter(col("Resourcegroup").isNotNull)
dfNotNull.count()

df.write.saveAsTable("logdata")

%sql
SELECT * FROM logdata
