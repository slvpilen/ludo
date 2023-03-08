# Dette er planen

## beskrivelse
Vi skal lage et ludo-spill, som iallfall kan spilles mot en datamaskin. 
Målet med spillet er å vinne i ludo. Man gjør dette ved å få alle brikkene ut fra spawn-area ved å trille terning og inn i midten av brettet. Vanlige ludoregler gjelder.
Det skal være mulig å lagre et spill som ikke er ferdig. Det skal også være mulig å laste inn gamle spill. 

## grunnklasser
Grunnklassene kan sees i klassediagrammet som vi har laget. Dette er GameEngine, Player, Board, Settings, 
SaveAndReadToFile.
Vi tenker å implementere enkel filbehandling i denne appen. Det skal være mulig å lagre et spill til en fil, 
slik at du kan plukke opp spillet, og spille videre på et senere tidspunkt. Dette tenker vi å løse ved 
å lagre attributtene til GameEngine. For å laste inn et eksisterende spill fra fil løser vi det ved å
lage en ny GameEngine med verdiene som er lagret til filen (spillere, brikkersplassering, hvem sin tur etc)
I tillegg til klassene i klassediagrammet må vi ha en App-klass og en kontroller-klasse. 
Disse skal være relativt enkle, og kun bruke logikk fra klassene i klassediagrammet.

## tester
-Vil teste at spillet slutter når noen kommer i mål. 
-Teste at spiller havner på riktig startfelt, når han går ut av spawn-area.
-Teste at det går an å slå ut en motstander, men at man ikke kan slå ut sin egen brikke.
-Teste at riktig spiller får riktig tur. Kaste seks får nytt kast, kaste seks tre ganger på rad ny spiller sin tur.
-Dersom nocker ut, så får man ekstra kast.
-Test at brikker står på samme plass, og at det er riktig person sin tur, etter man laster inn et lagret spill.




## ekstra
Hvis vi får til, så håper vi å få laget funksjonalitet slik at man kan spille mot en venn. Håndtering av brukernavn og toppliste som sorterer 
gamle resultater burde være mulig å få til.

Vinneren av hvert spill skal legges til i scoreboard-statistikk, som lagres til en systemfil. Mulighet for å nullstille scoreboard.

Legge inn funksjonalitet for freespace. Plasser på brettet der brikker ikke kan bli slått ut. 

Settings for hvordan vil man at spille skal fungere:
- Skal spillet slutte når en spiller har kommet i mål, eller skal man spille til alle har kommet i mål?
- Skal man ha med freespace eller ikke freespace?
- Skal det være lov med tårn, eller skal det være ulovlig. 
- Velge hvor mange spillere som kan spille.
















































