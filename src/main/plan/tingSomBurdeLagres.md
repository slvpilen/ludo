# Ting som må lagres til fil


## Spillere
- Antall menneskelige spillere
- Hvilke spillere som er bots, og hvilke spillere som er mennesker
- Hva spillerne heter
- Hvilket husnummer spillerne har

## GameEngine
- Hvem sin tur det er
- Hvor mange kast spilleren har tatt denne turen

## Brikker
- Posisjonen til brikkene
- Indeksen til brikkene
- Eierne til de forskjellige brikkene.

## Når skal man få lov til å lagre?
- Jeg foreslår at man kun skal få lov til å lagre når det er en menneskespiller sin tur.
- Menneskespilleren kan ikke ha et lovlig trekk på lagringstidspunktet.
- Spilleren må være i den posisjonen at han skal kaste terningen.

- Hvis kravene ikke er oppfylte, så skal det komme en popUp, eller en form for rød tekst, slik som vi har i createGame-scene.
- Eventuelt kunne vi hatt det slik at lagre-knappen er halvveis fadet når man ikke kan lagre spillet. 

## Hvordan skal man laste inn spillet?
- Jeg foreslår i første omgang at det kun skal være lov å lagre ett enkelt spill om gangen, og at du ikke skal få lov til å velge navnet på filen som lagres.
- Når du trykker på load game, så skal du bli kastet inn i det ene spillet som er lagret. 