# Eshop Deployment

Eshop web application uses the MySQL database, therefore you will need to install it (<https://www.apachefriends.org/download.html>). Make sure your **MySQL version 8 or higher**. Application was with **JDK 13**. It is expected that you have a **correclty configured `JAVA_HOME`** system property. In order to build the application you must have **MAVEN installed and correctly setup**.

## Downloading and launching Wildfly

1) Download wildfly v 18.0.1.Final, Java EE Full & Web Distribution (<https://wildfly.org/downloads/>).
2) Extract the zip file into your desired path (my will be located in `C://wildfly`)
3) Open terminal/CMD and navigate to the wildfly directory
4) In your wildfly directory navigate to `bin/`
5) Run the application server by executing `standalone.bat -c standalone-full.xml`
6) (Optional) **It is advised to set higher JVM memory allocation pool.** The web application seeds the database, therefore, it allocates more memory. You can do so by editing the `standalone.conf.bat`. Find the line that looks as follows:

   `set "JAVA_OPTS=-Xms64M -Xmx512M -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m"`

    and set it something as such:

   `set "JAVA_OPTS=-Xms64M -Xmx2048M -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m"`

## Configuring wildfly

### Mysql

1) First create a database. I will name it `eshop`

2) Download the MySQL Connector/J 8+ (<https://dev.mysql.com/downloads/connector/j/>). I will save the MySQL Connector into `C://mysql/[mysql-connector-filename]`.

3) Connect to the wildfly management console. In your wildfly's `bin/` directory, open up terminal/CMD and run the following command `jboss-cli.bat -c`.

4) Install the `com.mysql` with the following command:

   `module add --name=com.mysql --resources=C://mysql/[mysql-connector-filename] --dependencies=javax.api,javax.transaction.api`

5) Install the JDBC driver:

   `/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql)`

6) Install the datasource:

   `data-source add --jndi-name=java:/MySqlDS --name=MySQLPool --connection-url=jdbc:mysql://localhost:3306/[db-name] --driver-name=mysql --user-name=[mysql-username] --password=[mysql-password]`

7) You can test your datasource configuration by calling:

   `/subsystem=datasources/data-source=MySQLPool:test-connection-in-pool`

### JMS Queue

1) Connect to the wildfly management console (if not already). In your wildfly's `bin/` directory, open up terminal/CMD and run the following command `jboss-cli.bat -c`.

2) Create the JMS queue:

   `/subsystem=messaging-activemq/server=default/jms-queue=loggerQueue:add(entries=[java:jboss/exported/jms/queue/loggerQueue])`

3) Exit out of the manegement console (ctrl+c or the `exit` command).

4) Create a **application** user with the name `logger` and password `123`. (If you wish have a different login combination, change it in `cz.kodytek.shop.jms.JMSReciever.java`). Creating a user is as simple as running `add_user.bat`. The user must have the group `guest` asigned to him.

5) Restart the application server.

## Compilig and deploying the web application

1) Clone the source code form the repository <https://github.com/monarezio/JavaEE-eshop.git>

2) In the root of the project run `mvn clean install`

3) Navigate to the module `web-eshop`. Open the `assets/` dir and execute `npm i` and then `npm run-script build`. This will build the css and js

4) In the `web-eshop` module root run `mvn clean wildfly`. The average deployment time is 40s. The application automaticly seeds the database with few randomly generated entities. The application generates a user with administrator rights

   **Generated user:**

   Email: "samuel@kodytel.cz"\
   Password: "abcabc"

5) Open <http://localhost:8080/Shop/> in your browser

## Compiling and running remote stateless EJB

1) Navigate to the `remote-eshop` module

2) Run `mvn exec:java`

## Compiling and running JMS receiver

1) Navigate to the `jms-receiver-eshop`

2) Run `mvn exec:java`

## Openning rest API

1) Open your preffered REST API "viewer" (Postman, SoapUI ect.)

2) API url is <http://localhost:8080/Shop/api>
