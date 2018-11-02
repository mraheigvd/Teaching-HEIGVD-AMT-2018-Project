FROM payara/server-full
MAINTAINER Mentor Reka <mentor.reka@heig-vd.ch>

ADD ./mysql-connector-java-5.1.34.jar /opt/payara5/glassfish/lib/

RUN echo "AS_ADMIN_PASSWORD=admin" > /tmp/payaraPass.txt && \
./bin/asadmin start-domain domain1 && \
./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/payaraPass.txt create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=root:DatabaseName=amt_project:ServerName=192.168.99.100:port=3306 amtProductionPool && \
./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/payaraPass.txt create-jdbc-resource --connectionpoolid amtProductionPool jdbc/amt

