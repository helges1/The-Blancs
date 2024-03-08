# Rapport – innlevering 1

oppgave A0 - A5

Teamnavn: The Blancs

Gruppe 2 

Medlemmer: Michal, Stian, Lasse, Balder og Sebastian

Trello: https://trello.com/invite/b/tr1xRmAG/ATTI346cf3dd7098f5c2c75c88b418352b7812169306/the-blancs

**Bakgrunnen til teammedlemmene:**

- Alle har erfaring med Java fra før av ved ulike INF emner fra UiB. Da innenfor INF101 og INF102. Alle har hatt INF100, og utover det har vi tatt ulike emner som er mer tilpasset studieprogtrammene våre.

Git: Det er opprettet en Gitlab gruppe, men ikke på the blancs som en gruppe, men på michal som har lagt til alle andre teammedelemene og gruppeledere.

### Kort om gruppefordelinger:

Temaet har ikke enda bestemt roller per dags dato. Vi har også laget trello som vi skal bruke underveis. link til trello finner man ovenfor.

## Kjøring

- Kompileres med `mvn package`.
- Path til main filen: `..\the-blancs\src\main\java\main\Main.java` kan kjøres fra en IDE.
- Skal virke med Java 17 eller nyere.

## Oppgave A2: Konsept

Ideen til spillet er å lage et spill som er liknende [Sinistar (1983)](https://en.wikipedia.org/wiki/Sinistar).
Spillet skal inneholde en spillfigur som skal kunne styres med musen og piltastene. Spillet skal være i 2D og ha en enkel grafikk. Spillet skal ha en enkel bakgrunnshistorie og enkle spillmekanikker.

Aspekter som skal være med i spillet:

- Spillfiguren skal kunne styres med musen og piltastene
- Spillet skal være i 2D
- Spillet skal ha en enkel grafikk
- Spillet skal ha en enkel bakgrunnshistorie
- Spillet skal ha enkle spillmekanikker
- Spiller skal kunne opptjene poeng

## Oppgave A3: Velg og tilpass en prosess for teamet

# Prosjektets Arbeidsstruktur

## Ukentlige Møter

Vi samles hver tirsdag kl. 10:15 for å koordinere vårt arbeid. Disse møtene er essensielle for å sikre at alle er oppdaterte og for å identifisere eventuelle utfordringer vi står overfor. Vi vil vurdere vår fremgang og legge en slagplan for den kommende uken, slik at vi er sikre på at alle vet hva de skal fokusere på.

## Kommunikasjon utenfor Møtene

Vår Discord-gruppe er vårt kommunikasjonssentrum utenfor de faste møtene. Den er åpen hele tiden for spørsmål, ideutveksling, og hurtig problemhåndtering. Det er viktig at vi opprettholder en åpen og aktiv linje for dialog slik at ingen føler seg isolert eller blokkert i sitt arbeid.

## Oppgavehåndtering med Kanban

Med vår Kanban-tavle på Trello har vi et klart bilde av hvilke oppgaver som må gjøres, hvilke som er i gang, og hvilke som er fullførte. Å ha oppgavene våre synlige og organiserte på denne måten forenkler oppfølging og gjør det enklere å se hvor vi trenger å legge inn en ekstra innsats.

## Deling og Lagring av Ressurser

Alle prosjektdokumenter, inkludert kode og diagrammer, deles og lagres på GitLab. Dette sentraliserte systemet sikrer at alle har tilgang til de nyeste ressursene og støtter et sømløst samarbeid. Ved å bruke GitLab kan vi enkelt spore endringer, gjennomgå hverandres arbeid, og opprettholde en høy standard på koden vår.

Vårt mål er å skape et åpent, strukturert og produktivt miljø hvor alle kan bidra og føle at deres arbeid er verdsatt. Ved å bruke disse metodene og verktøyene, legger vi grunnlaget for et vellykket prosjekt.

## Oppgave A3: Få oversikt over forventet produkt

Overordnet mål for applikasjonen:
Målet med space shooter-spillet er å tilby en engasjerende og underholdende opplevelse der spillere kontrollerer et romskip i verdensrommet for å bekjempe fiender og unngå hindringer. Spillerne må navigere seg rundt i verdensrommet for å samle poeng og overleve angrep fra fiendtlige romskip og boss-fiender for å oppnå høyest mulig poengsum og fullføre spillet.

Krav til Minimum Viable Product (MVP)

1. Vise et spillområde: Skal inneholde en bakgrunn som reprsenterer verdensrommet.
2. Vise romskipet: Spillerens karakter, et romskip, skal vises på skjermen.
3. Flytte romskipet: Spilleren skal kunne kontrollere romskipets bevegelser ved hjelp av tastatur og mus
4. Romskipet skyter: Spilleren skal kunne skyte prosjekttiler mot fiender.
5. Fiender/monstre: Fiendtlige romskip som beveger seg og angriper spilleren.
6. Samle poeng: Spilleren kan samle poeng ved å ødelegge fiender.
7. Romskipet kan ta skade og dø: Kontakt med fiender eller deres prosjektiler reduserer spillerens liv. Spillet slutter når livene er oppbrukt.
8. Enkelt nivå: Minst ett spillbart nivå der målet er å overleve fiendens angrep og oppnå en viss poengsum.
9. Start- og game over-skjerm: En enkel meny for å starte spillet på nytt eller avslutte etter at spilleren har tapt.

Brukerhistorier basert på MVP-krav:

1.  Som en spiller ønsker jeg å se mitt romskip på spillområdet, slik at jeg vet hvor jeg befinner meg.
    o Akseptansekriterier: Romskipet vises tydelig midt på skjermen ved spillstart.
    o Arbeidsoppgaver: Design romskip, implementer romskipets startposisjon.
2.  Som en spiller ønsker jeg å flytte romskipet, slik at jeg kan unngå fiender og samle poeng.
    o Akseptansekriterier: Romskipet beveger seg jevnt i alle retninger basert på brukerinput.
    o Arbeidsoppgaver: Implementer kontrollmekanikk for romskipet.
3.  Som en spiller ønsker jeg å skyte prosjektiler, slik at jeg kan ødelegge fiender.
    o Akseptansekriterier: Romskipet skyter prosjektiler når spilleren trykker på en bestemt tast.
    o Arbeidsoppgaver: Utvikle skytemekanikk og prosjektilgrafikk.
4.  Som en spiller ønsker jeg å se fiender, slik at jeg kan unngå eller ødelegge dem.
    o Akseptansekriterier: Fiendtlige romskip vises på skjermen og beveger seg på en forutsigbar måte.
    o Arbeidsoppgaver: Design og implementer fiendens bevegelsesmønstre.
5.  Som en spiller ønsker jeg å samle poeng ved å ødelegge fiender, slik at jeg kan forbedre min poengsum.
    o Akseptansekriterier: Spillerens poengsum øker for hver ødelagt fiende.
    o Arbeidsoppgaver: Implementer poengsystem og visning av poengsum.

## Oppgave A5:

Så langt i prosjektet har vi kommet med idee over hva vi ønsker å lage, og vi har satt opp en kanban board i Trello for å senre skulle kunne fordele arbeidsoppgaver når vi kommer i gang med å programmere spillet. Alle på gruppen må få satt seg inn i hvordan LibGDX rammeverket er bygd opp, og etter det få laget en typ arkitektur for hvordan vi skal utvikle spillet videre. Et tentativt UML diagram er utivklet, og vil bli endret etterhvert som vi kommer lengre i prosjektet. En GitLab repo har også blitt opprettet under Michal sin bruker, og der medlemmene har blitt lagt til sammen med gruppeleder.

**Forbedringspunkter:**
- Gruppen kan bli flinkere med å fordele arbeidsoppgaver.
- Gruppen kan bli flinkere med å holde kommunikasjonen oppe utenfor de faste møtene.
- Sørge for at folk får spesifike arbeidsoppgaver, og at de blir gjort i tide.

Vurdering i hvordan vi traff på oppgaven vil komme på et senere tidspunkt.