# Rapport – innlevering 4
**Team:** *The Blancs (Gruppe 2)* – *Medlemer: Michal, Lasse, Sebastian, Balder og Stian*

# Prosjektrapport
- Hvordan fungerer rollene i teamet? Trenger dere å oppdatere hvem som er teamlead eller kundekontakt?

I løpet av prosjektet har vi operert med flytende roller innad i teamet. Gjennom ukentlige møter og kontinuerlig dialog på Discord, har vi fordelt arbeidsoppgaver basert på deres prioritet og hvert medlems tilgjengelighet samt kompetanse. Dette har sikret at oppgavene ble tildelt de mest kompetente personene for hvert aspekt av koden som krever videreutvikling. Likevel har vi kommunisert bra, og det har vært lett å skulle jobbe videre på andre medlemmer sin kode, i tilfeller dette har vært et behov. 

Vi har benyttet Trello for å organisere og tildele oppgaver. Dette verktøyet har gjort det mulig for teammedlemmene å selv velge oppgaver de ønsker å arbeide med, samtidig som det gir hele gruppen en oversikt over hvem som arbeider med hva. Dette systemet har fungert effektivt og det har til nå ikke vært behov for å gjøre endringer i hvem som er teamleder eller kundekontakt.

- Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes teamet at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet fungerer på?

I løpet av prosjektet har bruken av Trello og flytende roller vært sentrale elementer i vår prosjektmetodikk. Denne tilnærmingen har hatt flere fordeler. For det første, ved å bruke Trello for oppgavefordeling, har alle teammedlemmer hatt en klar oversikt over prosjektets fremdrift og hvilke oppgaver som har blitt utført. Dette har bidratt til å øke ansvarligheten innen teamet. Videre har de flytende rollene gjort oss mer fleksible og tilpassningsdyktige, da hvert medlem kunne ta oppgaver de følte seg mest kompetente til å håndtere.

Selv om disse metodene generelt har fungert godt, har vi identifisert noen områder for forbedring. Med flytende roller har vi noen ganger opplevd utfordringer med å definere klare ansvarsområder, noe som i enkelte tilfeller kan ha ført til forvirring og ineffektivitet. I retrospekt kunne vi hatt en mer definert rollestruktur i perioder av prosjektet, som hadde vært med på å effektivisere arbeidet på spesifikke spesialiserte arbeidsoppgaver. 

Videre kunne vi forbedret vår bruk av Trello ved å implementere regelmessige gjennomganger av oppgavelisten for å sikre at alle oppgaver er oppdaterte og reflekterer den faktiske statusen. Dette ville sikret at alle teammedlemmer var kontinuerlig oppdaterte og at ingen viktige oppgaver ble oversett.

- Hvordan fungerer kommunikasjonen for dere?

Kommunikasjonen vår har foregått på gruppetimer, og på Discord. 
På gruppetimer har vi diskutert hva som har blitt gjort siden forrige gruppetime, og hva som er nødt å bli gjort eller fikset på til neste gruppetime. Her har vi også hatt muligheten til å spørre hverandre, dersom noen medlemmer synes at koden har vært uklar eller har lurt på hvor ulike funksjoner til programmet ligger. På denne måte har vi klart å jobbe med hverandres kode. 
Discord har i hovedsak blitt brukt til å gi beskjed, dersom man ikke har hatt mulighet til å stille på neste gruppetime. Utenom dette har Discord også blitt brukt til å gi viktige beskjeder angående bugs, eller andre ting som kan være verdt å vite om prosjektet, før kommende gruppetime. 

-  Gjør et retrospektiv hvor dere vurderer hvordan hele prosjektet har gått. Hva har dere gjort bra, hva hadde dere gjort annerledes hvis dere begynte på nytt?

Vi er på generelt grunnlag veldig fornøyd med selve implementasjonen av spillet, og hvordan det ferdige prosjektet ser ut. Vi har også vært flinke til å hjelpe hverandre med forbedring av kode. Tilnærmingen til prosjektarbeidet har vært preget av en åpen og konstruktiv holdning til forbedringer, som er et annet punkt vi fornøyd med. Når et teammedlem har foreslått en mer effektiv løsning på et problem, har resten av teamet vært positive til å integrere disse forbedringene i sin egen implementasjon. Dette har ført til at tilpasning og forbedring av arbeidet har foregått uten å skape konflikter. 
En enda tydeligere bruk av trello, som også ville ført til en enda bedre oversikt på hva som er gjort. Er noe vi kunne gjort annerledes, og er et forbedringspunkt. Til tider har det vært tilfeller hvor trello er blitt litt glemt, og medlemmer ikke har skrevet opp hva de jobber med. Dette har ikke ført til noen store problemer, men den generelle oversikten av hele prosjektet, for alle medlemmer har blitt lavere av dette. 

# Krav og spesifikasjon
- Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang. Er dere kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.

Vi har nå klart å oppnå alle MVP kravene som er spesifisert i oblig1.md. Siden sist har vi fått alle skjermene våre til å fungerer skikkelig, og fikset opp i bugs relatert til knapper og resizing av vinduer på disse. Det er også lagt til en HelpScreen klasse, denne viser "Game Controls", og forklarer til spilleren hvordan romskipet styres. Videre har vi også ferdigutviklet ny funksjonalitet som går forbi MVP kravene fra oblig1.md. Disse står spesifisert i oblig3.md med krav, brukerhistorier og akseptansekriterier/arbeidsoppgaver. Noen av disse er derimot blitt litt forenklet. Nivået i spillet endrer seg basert på "scoren" til spilleren, og kan ikke velges med en layout. Nye "enemyships" er også litt forenklet, vi har implementert at laseren deres er mer kraftfull og at de tåler mer, men bevegelsesmønsteret er likt. Utenom dette har vi også sett på tilbakemeldinger fra oblig3, og jobbet med å få implementert disse. Vi har laget objektfabrikker/abstract factory i deler av koden hvor dette er relevant, og sørget for at vi testene våre dekker større deler av programmet vårt. Vi har bla. laget manuelle tester for view-mappen, da dette er klasser det er vanskelig å skulle teste automatisk. 
Fram mot denne siste innleveringen har vi prioritert å få dekket alle kravene som vi har spesifisert tidligere, og jobbet med å få implementert disse på en best mulig måte. Dette er gjort med å gjøre koden mest mulig effektiv og lesbar, samtidig som vi har endret på småting som gir en bedre opplevelse til brukeren visuelt. Vi har ikke prioritert noe nytt, for å ha mest mulig fokus på det vi allerede har og utbedre dette på en best mulig måte. 

1. En hjelpeskjerm i spillet
- Brukerhistorie: Som en spiller ønsker jeg å få tilgang til en hjelpeskjerm, slik at jeg kan lære hvordan man kontrollerer romskipet effektivt.
  - Akseptansekriterier: Hjelpeskjermen er lett tilgjengelig fra hovedmenyen. Skjermen inneholder klare og forståelige instruksjoner om kontrollene for romskipet.
  - Arbeidsoppgaver: Designe layouten for hjelpeskjermen, skrive klare og konsise instruksjoner om hvordan man kontrollerer romskipet. 


# Produkt og kode 







