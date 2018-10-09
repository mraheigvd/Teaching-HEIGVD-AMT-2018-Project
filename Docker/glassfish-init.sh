#!/bin/bash

#cd $GLASSFISH_HOME
#wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.45.zip .
#unzip mysql-connector-java-5.1.45.zip
#cp mysql-connector-java-5.1.45/mysql-connector-java-5.1.45-bin.jar ./glassfish/domains/domain1/lib/ext/
#echo "MENTOR"
#chmod 755 ./glassfish/domains/domain1/lib/ext/mysql-connector-java-5.1.45-bin.jar
#function gracefulShutdown {
#  echo "SIGTERM trapped : shutting down"
#  $GLASSFISH_HOME/glassfish/bin/asadmin stop-domain
#}
#trap gracefulShutdown SIGTERM
#$GLASSFISH_HOME/glassfish/bin/asadmin start-domain & wait
# Create the JDBC pool and resource

#$GLASSFISH_HOME/glassfish/bin/asadmin restart-domain & wait


echo "--- Setup the password file ---" && \
    echo "AS_ADMIN_PASSWORD=" > /tmp/glassfishpwd && \
    echo "AS_ADMIN_NEWPASSWORD=admin" >> /tmp/glassfishpwd  && \
    echo "--- Enable DAS, change admin password, and secure admin access ---" && \
    $GLASSFISH_HOME/glassfish/bin/asadmin --user=admin --passwordfile=/tmp/glassfishpwd change-admin-password --domain_name domain1 && \
    $GLASSFISH_HOME/glassfish/bin/asadmin start-domain && \
    echo "AS_ADMIN_PASSWORD=admin" > /tmp/glassfishpwd && \
    $GLASSFISH_HOME/glassfish/bin/asadmin --user=admin --passwordfile=/tmp/glassfishpwd enable-secure-admin

#cp -f /usr/local/glassfish4/glassfish/domain.xml /usr/local/glassfish4/glassfish/domains/domain1/config/domain.xml
cp -f /usr/local/glassfish4/glassfish/admin-keyfile /usr/local/glassfish4/glassfish/domains/domain1/config/admin-keyfile
echo "Finished"
echo "AS_ADMIN_PASSWORD=admin" > /tmp/glassfishpwd


$GLASSFISH_HOME/glassfish/bin/asadmin start-domain

$GLASSFISH_HOME/glassfish/bin/asadmin --user=admin --passwordfile=/tmp/glassfishpwd create-jdbc-connection-pool --ping=true --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype javax.sql.DataSource --property user=root:password=root:DatabaseName=amt_project:ServerName=db_amt_mysql:port=6666:useSSL=false:useUnicode=true:useJDBCCompliantTimezoneShift=true:useLegacyDatetimeCode=false:serverTimezone=UTC:allowPublicKeyRetrieval=true:ssl=false:verifyServerCertificate=false amtProductionPool
$GLASSFISH_HOME/glassfish/bin/asadmin --user=admin --passwordfile=/tmp/glassfishpwd create-jdbc-resource --connectionpoolid amtProductionPool jdbc/amt
$GLASSFISH_HOME/glassfish/bin/asadmin --user=admin --passwordfile=/tmp/glassfishpwd deploy Gamification-WP1.war --verbose
$GLASSFISH_HOME/glassfish/bin/asadmin stop-domain
echo "Stopped"
$GLASSFISH_HOME/glassfish/bin/asadmin start-domain --verbose