<h1>Velkommen til Project Management Tool teamet</h1>

Dette er en introduktion til hvordan du kan komme i gang med at arbejdet på Project Management Tool.

Du vil få en introduktion til vores arbejdsproces og teknologierne i brug for programmet.

<h2>Sæt kildekoden op</h2>

Kildekoden for Project Management Tool findes i Github i dette repository: https://github.com/MeH2607/ProjectManagementTool
Klon dette repository og sørg for at forbinde din Github til IntelliJ for at kunne pull og push direkte fra Intellij.

<h2>Produktions miljø</h2>
Når man arbejder på systemet vil man arbejde på en test server, som er defineret i application-test.properties. 

Når du pusher til main vil koden automatisk blive testet. Hvis en test fejler under push vil main ikke blive deployet af render, så hjemmesiden vil ikke bryde ned. Men sørg for at få testet koden inden der merges ind til main.

<h2>Kode struktur</h2>
Gør dig bekendt med systemets pakkestruktur. Systemets følger MVC arkitekturen som er visualiseret i dette diagram:

![mvc](https://github.com/MeH2607/ProjectManagementTool/assets/113069009/d1323605-91fa-4201-a21c-d8b51eb65824)

For en kort gennemgang af pakkestrukturen:
- Frontend ligger i Ressources mappen. HTML filerne findes i template mappen og CSS filerne i static mappen.
- Controller pakken håndterer HTTP Endpoints og kommunikation mellem Frontend og repository.
- Repository håndterer forretningslogikken og kommunikation med databasen.
- Model pakken indeholder vores objekt klasser.


<h2>Teknologier</h2>
Gør det bekendt med de teknologier vi bruger til at udvikle systemet.
 Her er en kort opsummering af de teknologier at bruge.
- Systemets logik kodes i Java, Databasen skrives i MySQL og frontend skrives i HTML.
- Frontend laves i HTML og styles med CSS, Bootstrap og Pico.
- Thymeleaf skabelonen behandler data til og fra frontend.
- Springboot håndterer HTTP endpoints.
- JDBC api'en håndterer Database kald i repository.

<h2>Arbejdsstruktur</h2>
Teamet arbejder i Scrum. 
Vi holder et Scrum møde hver dag, hvor vi går igennem hvad vi har lavet siden sidste møde, hvilke udfordringer vi har stødt ind på og hvad vi vil arbejde med til næste møde.
Hvis man har haft problemer med sit arbejde kan man søge hjælp til standup møderne, og arrangere pair eller mob programming session. 
Mohamed aggerer som Scrum master til møderne, og møder kan fortages både i person eller online.

<h3>Scrum board</h3>
Vi bruger Github Project til at oprette et scrumboard. 
Scrumboardet er delt op i et Backlog hvor alle opgaver og user stories ligger.
Til PO mødet hver anden uge bliver det næste sprint besluttet og opgaver for næste sprint bliver flyttet til ToDo.
Til de daglige scrum mødet bliver boardet kiggen igennem og opdateret. Når man påtager sig en user story eller en opgave kan man få den tildelt i Github projects, så alle kan se hvem der er ansvarlig for den.


<h3>Branching</h3>
Vi arbejder ud for branching strategien Github Flow Branching. 
Med denne strategi opretter vi branches for hver ny feature eller arbejdsopgave man har sat sig til at arbejde på.
Når man er klar til at merge sit arbejde ind til main branchen, så mødes man med teamet og overser processen. 
Ved merge konflikter går man igenne konflikterne sammen for at sikre at der ikke går ændringer tabt.
