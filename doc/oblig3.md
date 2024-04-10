# Rapport – innlevering 3
**Team:** *The Blancs (Gruppe 2)* – *Medlemer: Michal, Lasse, Sebastian, Balder og Stian*


# Prosjektrapport
- Rollene våre fungerer bra. Vi setter oss opp på arbeidsoppgaver på trello, og naturlig jobber man videre med ting som man selv har implementert og har god kontroll på. 
- Vi bruker Kanban gjennon å visualisere arbeidsprossesen på en Trello-tavle. Når vi møtes på de ukentlige gruppetimene diskuterer vi hva som har blitt gjort siden siste, og hvilke nye implementasjoner til spillet som det er på tide å se på. Disse oppgavene blir lagt til på trello, og blir fordelt fortløpende. På denne måten vet også de som ikke har mulighet til å være tilstede på gruppetimen hvilke oppgaver som skal prioriteres. 
- Kommunikasjonen vår skjer i hovedsak gjennom discord og på gruppetimer. På discord gir vi hverandre beskjed dersom man ikke kan møte på en gruppetime, eller dersom man har mer spesifikke spørsmål til prosjektet som ikke er blitt tatt opp på grupptimen. I gruppetimene legger vi en plan for hva som må jobbes med til neste time, slik at vi klarer å lage spille som dekker de ulike kravene. 
- Synes at kommunikasjonen vår er bra, og at alle er på lik bølgelengde når det gjelder hvordan vi ønsker at det fullverdige spillet skal se ut. Gruppen har også fått bedre kontroll på git, og vi er nå på samme linje når det gjelder hvilke branhces vi skal bruke når det pushes endringer i koden. 
- Når det gjelder forbedringspunkter kan vi gi hverandre enda mer spesifikke arbeidsoppgaver. Nå er det litt fri flyt på hvem som gjør hva, og vi kan muligens være enda tydeligere på hvem som skal utvilke de ulike delene av programmet vårt. Dette kommer bla. av at vi hjelper og samarbeider på flere deler/punkter. 

# Krav og spesifikasjon
- Vi har nå klart å dekke nesten alle MVP kravene våre som er spesifisert i oblig1.md. Det eneste kravet som mangler er en GameOverScreen. Det er laget en klasse for den, men har ikke helt fått den til å fungere skikkelig enda, og er forløpig en bug i programmet. Ny funksjonalitet som er lagt til er powerups, og er laget av Stian. Det er også lagt til en funksjonalitet som lagrer en highscore lokalt på maskinen, mulig dette utvides slik at den ikke bare lagres lokalt. Vi har også diskutert om vi skal implementere ulike forskjellige typer fiendeskip som vil ha ulike egenskaper. Videre har vi også et ønske om å kunne utvide til forskjellige nivåer i spillet, altså å kunne øke vanskelighetsgraden dersom brukeren vil det. 

Brukerhistorier basert på nye egenskaper

1. Ulike nivåer i spillet
- Brukerhistorie: Som en spiller ønsker jeg at spillet skal ha ulike nivåer, slik at jeg kan utfordres mer etter hvert som jeg blir bedre.
  - Akseptansekriterier: Spillet skal introdusere nye nivåer med økende vanskelighetsgrad. 
  - Arbeidsoppgaver: Design nivålayout, øk fiendens vanskelighetsgrad på høyere nivåer.

2. Highscore som lagres
- Brukerhistorie: Som en spiller ønsker jeg at min highscore lagres, slik at jeg kan se hvor godt jeg har spilt over tid.
  - Akseptansekriterier: Spillets grensesnitt viser spillerens høyeste poengsum. Poengsummen lagres mellom spilløkter.
  - Arbeidsoppgaver: Utvikle et system for lagring av poengsummer, oppdatere UI for å vise highscore, implementer funksjonalitet for å hente og lagre poengsum i lokal lagring eller en database.

3. Ulike fiendeskip med forskjellige egenskaper
- Brukerhistorie: Som en spiller ønsker jeg å møte ulike fiendeskip med forskjellige egenskaper, slik at spillet blir mer variert og utfordrende.
  - Akseptansekriterier: Spillet introduserer flere typer fiendeskip med unike bevegelsesmønstre, angrep, og motstandsdyktighet.
  - Arbeidsoppgaver: Design forskjellige fiendeskip, implementer unike bevegelsesmønstre og angrepsmekanismer, juster fiendens helse og skade basert på deres type.

4. Power-ups som kan plukkes opp
- Brukerhistorie: Som en spiller ønsker jeg å kunne plukke opp power-ups, slik at jeg kan forbedre mitt romskip eller få midlertidige fordeler.
  - Akseptansekriterier: Power-ups vises tilfeldig på spilletområdet og gir ulike fordeler når plukket opp (f.eks. forbedret skytekraft, ekstra liv, hurtigere bevegelse).
  - Arbeidsoppgaver: Design og implementer ulike power-ups, opprett logikk for å vise og anvende effekten av power-ups, integrer power-ups i spillets poengsystem og gameplay.

- Vi har hele veien prioritert å få spill logikken til å fungere først. Altså at skipet skal bevege seg skikkelig og kunne skyte lasere. På samme måte skal dette også gjelde for motstandereskipene, slik at de fungere om hverandre ved at det skytes frem og tilbake samtidig som det utføres skade. Etter dette begynte vi å se mer på ulike skjermer, og å legge til highscore. Altså mer visuelle ting som ikke har med selve logikken til spillet å gjøre. 
- Som sagt har vi en bug som gjør at spillet ikke går over til GameOverScreen når spillet er over (health<= 0). Vi har også et par tester som heller ikke fungerer som de skal. Tester er generelt noe vi må utbedre, og lage flere av. 

# Produkt og kode 


