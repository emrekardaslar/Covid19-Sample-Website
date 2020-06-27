# Covid19-Sample-Website
Website for entering/editing daily covid data. 
Backend: Java Servlets 
Database: Oracle DBMS 
Frontend: HTML, CSS, JSP, JST


Follow the steps below to setup the web application.

Install Eclipse EE 2020-03 (Community Edition cannot open a Dynamic Web Project)
Install Apache Tomcat Server 8.5
Install Oracle RDBMS 19c

After installing Oracle Database, look at the directory below:
<ORACLE-HOME>\network\admin\tnsnames.ora

Check the description that defines your address, port, service name etc. The web application uses the description below. 
You can modify the code according to your connection description later. 

ORCL =
  (DESCRIPTION =
    (ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = 1521))
    (CONNECT_DATA =
      (SERVER = DEDICATED)
      (SERVICE_NAME = orcl)
    )
  )


CONTINUE HERE IF YOU HAVE ORACLE INSTALLED 
If the Oracle setup is complete, initialize the tables since we need to login. We will be able to create, drop and reset tables from the web application later.
Extract the zip file and run the covid19.sql script from cmd

Loading initial database schema and initial data......
open command prompt:
cmd

-cd c:\users\User\Desktop (change directory to the covid19.sql)

-sqlplus emre/emre@orcl @covid19.sql (run this command from command prompt to run the script, also change the command as username/password@orcl @covid19.sql)

-exit (exit cmd)

The tables and triggers are created once the script gets executed.

Open Eclipse EE 2020-03 and create a Dynamic Web Application. While creating the project, the IDE will ask you the Tomcat Server version, choose 8.5, choose Tomcat directory and continue.
Copy src and WebContent files from zip and change with the files in your project. Java classes are inside src and other files for web application like JSPs are in WebContent/WEB-INF/. (JSTL packages are present inside in WebContent/WEB-INF/lib file)
Install oracle driver by :
Windows -> Perspective -> Open Perspective -> Other
Choose Database Development
Create a new Database Connection, select Oracle, select version and driver (the driver compatible with the web application is present in WebContent/WEB-INF/lib/ojdbc8.jar)
Open Java EE perspective back to see the Java code again. Open DatabaseConnection class.
Change the second and third parameters in "Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","emre","emre");" line in DatabaseConnection Class accoring to your username and password.
Now you can right click to the project and: Run as -> run on server -> select Tomcat Server
That way, you can run it in your server. Dont forget to stop Tomcat Service in your computer, since the ports of Tomcat in the IDE and the Tomcat in your computer can overlap.

If you want to run it as a war file, right click the project, export WAR file and export it to the destination below: 
C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps

Once you have exported it, you can stop the Tomcat Server in Eclipse IDE and start Tomcat service, run the web application in your browser without Eclipse IDE. 
