CREATE DATABASE appdb

USE appdb

CREATE TABLE logdata

%scala
spark.conf.set(
    "fs.azure.account.key.datalakez95pevs1.dfs.core.windows.net",
    "KEY")

COPY INTO logdata
FROM 'abfss://parquet@datalakez95pevs1.dfs.core.windows.net/ActivityLog01 (1).parquet'
FILEFORMAT = PARQUET
COPY_OPTIONS ('mergeSchema' = 'true');

SELECT * FROM logdata
