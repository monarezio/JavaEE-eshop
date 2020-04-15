# Eshop Deployment

Eshop web application uses the MySQL database, therefore you will need to install it (<https://www.apachefriends.org/download.html>). Make sure your **MySQL version is 8 or higher**. Application was with **JDK 13**. It is expected that you have a **correctly configured `JAVA_HOME`** system property. In order to build the application you must have **MAVEN installed and correctly setup**.

Additional information about how the project is structured is located below the installation.

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

3) Exit out of the management console (ctrl+c or the `exit` command).

4) Create a **application** user with the name `logger` and password `123`. (If you wish have a different login combination, change it in `cz.kodytek.shop.jms.JMSReciever.java`). Creating a user is as simple as running `add_user.bat`. The user must have the group `guest` assigned to him.

5) Restart the application server.

## Compiling and deploying the web application

1) Clone the source code form the repository <https://github.com/monarezio/JavaEE-eshop.git>

2) In the root of the project run `mvn clean install`

3) Navigate to the module `web-eshop`. Open the `assets/` dir and execute `npm i` and then `npm run-script build`. This will build the css and js

4) In the `web-eshop` module root run `mvn clean wildfly`. The average deployment time is 40s. The application automatically seeds the database with few randomly generated entities. The application generates a user with administrator rights

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

## Opening rest API

1) Open your preferred REST API "viewer" (Postman, SoapUI etc.)

2) API URL is <http://localhost:8080/Shop/api>

## Additional information

- Module `data-eshop` contains the database entities and a simple hibernate wrapper.

- Module `domain-shop` contains services, where I handle business logic. Moreover it contains interfaces for the EJB classes and a JMS Service that handles the communication with other JavaEE applications.

- Module `api-shop` contains implementations of the EJB interfaces and JAX-RS resources. Since the two APIs are supposed to be the same, I reuse the EJB classes in the JAX-RS.

- Module `web-shop` contains mainly the presentation layer (JSF implementation).
  - Services manage the stuff such as: flash messages, user sessions etc.
  - Controllers control the flow of the application. Manage button clicks
  - Helpers parse more complex entities/models for the JSF pages
  - The loading of external resources, such as product images, is managed by the servlet `ResourceServlet`
  - Page filters are located in `utils/filters`

- Module `jms-receiver-eshop` is a simple implementation of the JMS Receiver

- Module `remote-eshop` is the remote stateless EJB implementation. It's a simple console application.
