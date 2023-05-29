<h1>Velkommen til Project Management Tool teamet</h1>

Dette er en introduktion til hvordan du kan komme i gang med at arbejdet på Project Management Tool.

Du vil få en introduktion til vores arbejdsproces og teknologierne i brug for programmet.

<h2>Sæt kildekoden op</h2>

Kildekoden for Project Management Tool findes i Github i dette repository: https://github.com/MeH2607/ProjectManagementTool
Klon dette repository og sørg for at forbinde din Github til IntelliJ for at kunne pull og push direkte fra Intellij.

<h2>Produktions miljø</h2>
I repositoriet kan man finde SQL skabelonen og test data. Kør querysne lokalt på MySQL Workbench.

For at oprette et produktionsmiljø skal du oprette en application properties fil. 
Kald den application-test.properties få den til at pege på din lokale MySQL database sådan her:

```
spring.datasource.url=jdbc: mysql://localhost:3306/*datbase navn*
spring.datasource.username=*Workbench brugernavn*
spring.datasource.password=*Workbench kode*
```
Nu er din lokale version af koden forbundet til din lokale MySQL database.

<h2>Kode struktur</h2>
Gør dig bekendt med systemets pakkestruktur. Systemets følger MVC arkitekturen som er visualiseret i dette diagram:

![mvc](https://github.com/MeH2607/ProjectManagementTool/assets/113069009/d1323605-91fa-4201-a21c-d8b51eb65824)

For en detaljeret gennemgang, så læs "Pakke struktur" kapitlet i rapporten.
For en hurtig gennemgang:
- Frontend ligger i Ressources mappen. HTML filerne findes i template mappen og CSS filerne i static mappen.
- Controller pakken håndterer HTTP Endpoints og kommunikation mellem Frontend og repository.
- Repository håndterer forretningslogikken og kommunikation med databasen.
- Model pakken indeholder vores objekt klasser.


<h2>Teknologier</h2>
Gør det bekendt med de teknologier vi bruger til at udvikle systemet.
I rapporten er der en gennemgang af de teknologier vi bruger. Her er en kort opsummering.
- Systemets logik kodes i Java, Databasen skrives i MySQL og frontend skrives i HTML.
- HTML styles med CSS, Bootstrap og Pico.
- Thymeleaf skabelonen behander data i frontend.
- Spring boot håndterer HTTP endpoints.
- JDBC api'en håndterer Database kald i repository.
