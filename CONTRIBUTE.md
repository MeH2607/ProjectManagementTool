***Velkommen til Project Management Tool teamet***

Dette er en introduktion til hvordan du kan komme i gang med at arbejdet på Project Management Tool.

Du vil få en introduktion til vores arbejdsproces og teknologierne i brug for programmet.

*Sæt koden op*

Kildekoden for Project Management Tool findes i Github i dette repository: https://github.com/MeH2607/ProjectManagementTool
Klon dette repository og sørg for at forbinde din Github til IntelliJ for at kunne pull og push direkte fra Intellij.

*Produktions miljø*
I repositoriet kan man finde SQL skabelonen og test data. Kør querysne lokalt på MySQL Workbench.

For at oprette et produktionsmiljø skal du oprette en application.properties. Den skal pege på din lokale MySQL database sådan her:

'''spring.datasource.url=jdbc: mysql://localhost:3306/*datbase navn*
spring.datasource.username=*Workbench brugernavn*
spring.datasource.password=*Workbench kode*
'''
