# SpillProsjekt – IDATG2003 Mappe-gr7

Et Java-basert gruppeprosjekt levert som del av mappeinnlevering i emnet **IDATG2003 – Programmering 2** ved NTNU. Prosjektet kombinerer grafisk grensesnitt, brukerinteraksjon og filhåndtering i en enkel, men gjennomført applikasjon.

## Innhold

- [Beskrivelse](#beskrivelse)
- [Krav](#krav)
- [Hvordan kjøre prosjektet](#hvordan-kjøre-prosjektet)
- [Struktur](#struktur)
- [Bidragsytere](#bidragsytere)
- [Lisens](#lisens)

## Beskrivelse

Dette prosjektet er utviklet som en del av mappeoppgaven i emnet **IDATG2003 – Programmering 2** ved NTNU våren 2025. Oppgaven gikk ut på å utvikle et digitalt brettspill med grafisk brukergrensesnitt, basert på stigespill-konseptet, der spillere flytter seg mellom felter ved hjelp av terningkast.

Spillet er bygget opp med fokus på objektorientert design og inneholder støtte for stiger, spesialfelter, samt muligheten for å velge mellom flere spill. Det er lagt vekt på bruk av designmønstre, filbehandling (lesing/lagring av spill og spillere i JSON og CSV), samt implementering av egne utvidelser.

Prosjektet er strukturert som et Maven-prosjekt og bruker JavaFX for GUI. Prosjektet er strukturert i henhold til MVC-prinsippet, med tydelig separasjon mellom logikk, brukergrensesnitt og datastrukturer.
Dette legger til rette for videreutvikling og gjenbruk av komponenter, samtidig som koden er lettere å vedlikeholde og teste.

Her er en forenklet domenemodell som viser de sentrale klassene og deres relasjoner:

<p align="center">
  <img src="https://github.com/user-attachments/assets/75610fcf-75de-441a-abf7-273608dfeb02" alt="Domain_model" width="600"/>
</p>

## Krav

- Java 21 (LTS)
- Maven 3.9.8 eller nyere
- En IDE (IntelliJ IDEA, Eclipse, VS Code anbefalt)

## Hvordan kjøre prosjektet

1. **Klon repoet med SSH:**
   
   ```bash
   git clone git@github.com:Wayansk/Mappe-gr7.git
   cd Mappe-gr7

2. Bygg prosjektet:
   ```bash
   mvn clean install

3. Kjør programmet:
   ```bash
   mvn javafx:run

<br><br>
  **Obs!**
  <br>
  Sørg for at du har satt opp SSH-nøkkel med GitHub. Hvis du ikke har det, bruk heller HTTPS-URL:
  https://github.com/Wayansk/Mappe-gr7.git

## Struktur

Prosjektet er organisert i moduler basert på funksjonalitet, med egne mapper for hvert spill, samt delt logikk i core. 
Koden er plassert under src/main/java, mens ressurser som bilder og brettdata ligger i src/main/resources. 
Denne strukturen gjør det lettere å vedlikeholde og utvide prosjektet.

Figuren under er lagt til for å vise inndeling på en simpel måte, uten å inkludere alle elementer i applikasjonen. 

<div align="center">
  <img src="https://github.com/user-attachments/assets/5a9dacb0-b0bb-4b07-baae-9f1dd5b7e257" alt="Struktur_simpel" />
</div>


## Bidragsytere

- Wayan S. K.
- Rikard S.

## Lisens
Dette prosjektet er utviklet som en del av et skoleprosjekt i faget IDATG2003 ved NTNU i Gjøvik.
Alt innhold er kun ment for læringsformål. Opphavsrett og eierskap kan være underlagt universitetets retningslinjer.
