%scala
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

from pyspark.sql.functions import col

# File location and type
file_location = "/FileStore/tables/json/customer_obj.json"
file_type = "json"

# CSV options
infer_schema = "false"
first_row_is_header = "false"
delimiter = ","

# The applied options are for CSV files. For other file types, these will be ignored.
df = spark.read.format(file_type) \
  .option("inferSchema", infer_schema) \
  .option("header", first_row_is_header) \
  .option("sep", delimiter) \
  .load(file_location)

display(df)

# Create a view or table

temp_table_name = "customer_obj_json"

df.createOrReplaceTempView(temp_table_name)

%sql

/* Query the created temp table in a SQL cell */

select * from `customer_obj_json`

# With this registered as a temp view, it will only be available to this particular notebook. If you'd like other users to be able to query this table, you can also create a table from the DataFrame.
# Once saved, this table will persist across cluster restarts as well as allow various users across different notebooks to query this data.
# To do so, choose your table name and uncomment the bottom line.

permanent_table_name = "customer_obj_json"

# df.write.format("parquet").saveAsTable(permanent_table_name)

%scala
val dfobj = spark.read.format("json").load("/FileStore/tables/json/customer_obj.json")

%scala
display(dfobj.select(col("customerid"),col("customername"),col("registered"),(col("details.city")),(col("details.mobile")), explode(col("courses")).alias("Courses")))

%scala
val dfjson = spark.read.format("json")
.option("multiline","true")
.load("/FileStore/tables/json/customer_obj.json")
display(dfjson)

