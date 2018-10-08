## Glassfish installation of MySQL

Here is the procedure for configuring a JDBC Connection Pools with Glassfish.

First of all, you need to download the java mysql connector:

```
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.45.zip ~/Downloads/
unzip ~/Downloads/mysql-connector-java-5.1.45.zip
cp ~/Downloads/mysql-connector-java-5.1.45/mysql-connector-java-5.1.45-bin.jar /usr/local/Cellar/glassfish/5.0/libexec/glassfish/domains/domain1/lib/ext
```

Now, restart glassfish in order to take into account the new library.
After that, go to the Glassfish administration area via the default URL: http://localhost:4848

Then **Resources > JDBC > JDBC Connection Pools** and create a new pool:

- Pool name: ``amtProductionPool``
- Resource Type: ``javax.sql.DataSource``
- Database Driver Vendor: ``MySQL``

In **Datasource Classname** make sure to have **com.mysql.jdbc.jdbc2.optional.MysqlDataSource** and enable **ping** in order to see if the pool is correctly configured.

In additional Properties add these properties and adapt it to your context:

- **user:** root
- **password:** root
- **database:** amt_project
- **hostname:** localhost
- **port:** 3306

When it's done, go to **Resources > JDBC > JDBC Resources** and create a new resource with these properties:

- **JNDI Name:** jdbc/amt
- **Pool Name:** amtProductionPool
- **Status:** enabled

After that, restart you Glassfish server and try to run your application.

You can now ask the container to inject the resource into your code by using the @Resource annotation with the **mappedName** as the **JNDI Name**:

```java
    @Resource(mappedName = "jdbc/amt")
    private DataSource database;
```

