## Glassfish installation of MySQL

Here is the procedure for configuring a JDBC Connection Pools with Glassfish.

```
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.45.zip ~/Downloads/
unzip ~/Downloads/mysql-connector-java-5.1.45.zip
cp ~/Downloads/mysql-connector-java-5.1.45/mysql-connector-java-5.1.45-bin.jar /usr/local/Cellar/glassfish/5.0/libexec/glassfish/domains/domain1/lib/ext
```

Go to the Glassfish via the default URL: http://localhost:4848

Then go to **Resources > JDBC > JDBC Connection Pools** and create a new pool 

- Pool name: ``amtProductionPool``
- Resource Type: ``javax.sql.DataSource``
- Database Driver Vendor: ``MySQL``

In **Datasource Classname** make sure to have **com.mysql.jdbc.jdbc2.optional.MysqlDataSource** and for **ping** click enabled.

In additional Properties add these properties:

- **user:** root
- **password:** root
- **database:** amt_project
- **hostname:** localhost
- **port:** 3306

When it's done, go to **Resources > JDBC > JDBC Resources** and create a new one with these properties:

- **JNDI Name:** jdbc/amt
- **Pool Name:** amtProductionPool
- **Status:** enabled

After that, restart you Glassfish server and try to run your application.

You can now ask glassfish ton inject the MySQL DataSource into your code by doing this:

```java
    @Resource(mappedName = "jdbc/amt")
    private DataSource database;
```