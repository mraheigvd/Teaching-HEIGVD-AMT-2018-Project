FROM payara/server-full
MAINTAINER Mentor Reka <mentor.reka@heig-vd.ch>

ADD ./mysql-connector-java-5.1.34.jar /opt/payara5/glassfish/lib/
ADD ./mysql-connector-java-5.1.34.jar /opt/payara5/glassfish/domains/domain1/lib/ext/


RUN echo "AS_ADMIN_PASSWORD=admin" > /tmp/payaraPass.txt && \
./bin/asadmin start-domain domain1 && \
./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/payaraPass.txt create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --property user=root:password=root:DatabaseName=amt_project:ServerName=db:port=3306 amtProductionPool && \
./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/payaraPass.txt create-jdbc-resource --connectionpoolid amtProductionPool jdbc/amt && \
./bin/asadmin --interactive=false --user admin --passwordfile=/tmp/payaraPass.txt create-javamail-resource --mailhost smtp.gmail.com --mailuser stackoveramt --fromaddress amt2k18heig@gmail.com --storeprotocol=imap --storeprotocolclass=com.sun.mail.imap.IMAPStore --transprotocol=smtp --transprotocolclass=com.sun.mail.smtp.SMTPTransport --password holyamt2k18 --auth true --property mail.smtp.auth=true:mail.smtp.user=amt2k18heig@gmail.com:mail.smtp.password=holyamt2k18:mail.smtp.port=465:mail.smtp.socketFactory.port=465:mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory java/mail/swhp