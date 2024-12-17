/opt/mssql-tools/bin/sqlcmd -S azure_sql_server -U sa -P 'testSecure!123' -d master -Q  'IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = "my_cool_db")
                                                                                         BEGIN
                                                                                           CREATE DATABASE my_cool_db;
                                                                                         END;'
