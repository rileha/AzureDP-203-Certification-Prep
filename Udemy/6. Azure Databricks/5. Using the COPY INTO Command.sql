CREATE DATABASE appdb

USE appdb

CREATE TABLE logdata

%scala
spark.conf.set(
    "fs.azure.account.key.datalakez95pevs1.dfs.core.windows.net",
    "rtzbGEf8o/0vWN2myNq8WhgmMdVodR6bjrpKdD4Vco1l4UITjrDJm03/mwRo3wh8unOo1mLoy3ch+AStxuCbOQ==")

COPY INTO logdata
FROM 'abfss://parquet@datalakez95pevs1.dfs.core.windows.net/ActivityLog01 (1).parquet'
FILEFORMAT = PARQUET
COPY_OPTIONS ('mergeSchema' = 'true');

SELECT * FROM logdata
