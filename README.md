# questquadrix
Spring + JSF + JPA + Jersey + AngularJS
=============================

Banco de dados Derby - Habilitar na JDK poprta 21 ->
Find out exactly where the java home is by executing this instruction from NetBeans 7.4 :

System.out.println(System.getProperty("java.home"));
This is the output for my case:

C:\Program Files\Java\jdk1.7.0_51\jre
which is quite important for me, I was modifying another java.policy and took no effect and wasted me a couple of hours.

For reason of java.policy is an unix style file and read-only, I opened and edited it with notepad++ and executed as administrator (under the same java home):

C:\Program Files\Java\jdk1.7.0_51\jre\lib\security\java.policy
Add only these lines into the file after the first grant:

grant {
    permission java.net.SocketPermission "localhost:1527", "listen";
};
Save the file, which is a little tricky for reason of the permission. But if you run notepad++ or any other edit program as administrator, you can solve the problem. 

Then try to connect the database from NetBeans, it works for me.
Good luck.

não embarca no eclipse, baicha 
se for usar servidor de aplicação com suporte a CDI desabilite o Weld no POM
habilite o FACETS no eclipse DWP 3.0

http://localhost:8080/questquadrix/


o exemplo abaixo também roda com JSF 2 e Prime 5 

http://localhost:8080/questquadrix/index.xhtml

querendo colaborar me procurem, vem muito mais por ai.

- Version 1.4.0.GA
  - Added H2 JAR dependency for built-in support to H2 database;
  - Set H2 as default database (no configurations needed to run the project);

- Version 1.3.1.GA
  - Fixed a bug in the edition form that, in some circumstances, was keeping outdated validation data: added attribute resetValues="true" to the insertion and edition action buttons;

- Version 1.3.0.GA
  - Added a full-featured CRUD operations example: possibility to READ over a table, CREATE, EDIT and DELETE entries;
  - Changed default entity from "Code" to "Country", with properties:
    - Name (text);
    - Acronym (text);
    - Population (numeric);
  - Added a Spring application context listener that automatically adds mock data to database;
  - Moved internal page CSS to an external file at the `resources` folder;
  - Updated dependencies versions;

- Version 1.2.2.GA
  - Fixed JPA Transaction Manager bean name to conventional "transactionManager";

- Version 1.2.1.GA
  - Added PostgreSQL 9.x JDBC 4.1 driver;
  - Project encoding strictly defined as UTF-8;
  - Updated Primefaces version to 5.1;

- Version 1.2.0.GA
  - Full support component injection (@Inject and @Autowired) by extending <del>the new utility class InjectionAwareBean</del> Spring context's `SpringBeanAutowiringSupport` utility class; 

- Version 1.1.2.GA
  - Changed `index.xhtml` to show a `p:datatable` with mock objects;
  - Changed default Primefaces Aristo theme to Bootstrap;

- Version 1.1.0.GA
  - Internationalization;
  - Bean utilities (class FacesUtils);
  - BUG: Spring does not fully support CDI, then @Named could not be used to define JSF Managed Beans;

- Version 1.0.4.GA
  - Built-in MySQL Server connection for JDBC/JPA (extensible to ANY database - add specific JDBC drivers to POM if needed);
    - Oracle;
    - PostgreSQL;
    - Microsoft SQL Server;
    - Informix;
    - H2;
    - Derby;
    - and many more...
  - Hibernate 4.x as JPA 2.1 provider (switchable to EclipseLink if convenient - see web.xml);

