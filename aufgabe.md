# Debug-Tutorial

## Grundlagen des Debuggen mit einer IDE

### Debug-Modus

Der Debug-Modus ist das starten einer Anwendung, in der man in der Lage ist den Speicher auszulesen und Schritt für
Schritt durch das Programm gehen kann.
Diese Modus ist deutlich langsamer als die normale Modus. In IDEs ist dieser Modus durch das klicken auf einen
speziellen Button (meist ein Käfer-Symbol) startbar.
Meist änders sich dabei die Ansicht in der IDE oder ein extra Fenster erscheint.

**Aufgabe:** Finden Sie diesn Button zum starten des Debug-Modus in ihrer IDE.

### Breakpoints

Breakpoints sind Haltepunkte im Code, die der Entwickler selbständig setzt. Sie werden gesetzt, indem man links neben
Zeile im Programmcode einfach oder doppelt klickt.
Dort sollte dan eine Markierung auftauchen. Dies ist dann ein gesetzter Breakpoint.
Breakpoints lassen sich beliebig an und ausschalten oder auch wieder entfernen. Ein Breakpoint ist ausgeschaltet, wenn
er (je nach IDE) ausgegraut oder durchgestrichen ist.
Viele IDEs besitzen eine Breakpoint-Übersicht, in der listen artig steht, wo welcher Breakpoint ist und ob dieser
aktiviert ist.

**Aufgabe:** Setzen Sie an den Anfang eines Programms (Breakpoint.java) einen Breakpoint und starten Sie den Debug-Modus. Das Programm
sollte an dieser Stelle halten und die Fenster des Debug-Modus sollten nun gefüllt sein.

### FrameStack


### Auslesen/Manipulation des Speichers

Wenn ein Breakpoint erreicht wurde, so können alle Objekte und variablen, die zu dieser Zeit existieren eingesehen
werden und manipuliert werden.
Im Normalfall sollte nun ein "Variablen"-Fenster auftauchen. In diesem sind Bezeichnungen wie this, args usw zu finden.
Das sind die momentanen Objekte, die in dem aktuellen Scope aktiv sind.
Der Scope ist dabei die aktuelle Methode. Ein weiters Fenster sollte "Frames" heißen und dort sollte als erstes die
momentane Methode auftauchen und darunter alle aufgerufenen Methoden stehen. Die letzte Methode müsste die Main-Methode
sein.

### Im Code voran gehen

Es gibt viele verschiedene Möglichkeiten im Code vorran zu kommen. Die gängigsten sind:

- Step over -> geht zum nächsten Befehl der folgt
- Stepp into -> springt in die folgende Methode hinein und wird dort weiter geführt
- Step out -> Gegenteil zu step into. Spring aus der Methode und landet in der Methode die im Stack "darunter" liegt.
- Continue -> führt so lange fort bis der nächste Breakpoint kommt.

Weitere Optionen, die aber nur mache IDEs haben sind:

- Run to Cursor
- Drop Frame

Finden Sie diese Möglichkeiten in ihrer IDE ihrer Wahl.

## Situation

Die HelloLibrary wurde inzwischen um eine Mitarbeiterverwaltung und ein Kundenregister erweitert.
Die Bibliothek ist inzwischen ziemlich groß geworden und wird deshalb von Personal verwaltet und hat weitere Räume
bekommen.
Nun soll es auch möglich sein, dass Kunden Bücher ausleihen können und auch zurück geben können.
Bücher werden dabei in Regale gestellt. Werden Bücher ausgeliehen, so werden sie aus dem entsprechenden Regal
genommen.  

### Die Bibliothek
Die Verwaltung der Bibliothek besteht aus einer Bestandsverwaltung, Mitarbeiterverwaltung und dem Kundenregister. 
Die Bibliothek besitzt jetzt Leseräume, sowie eine Werkstatt. An Terminals (BesucherComputer, AngestelltenComputer) können Dienste der Bibliothek genutzt werden.

### Verschmutzbare Objekte
Jeder Arbeitsplatz, jedes Regal und Bücher werden über die Zeit verschmutzt. Diese müssen dann von einer Reinigungskraft gesäubert werden.

### Personal
Es gibt drei Arten von Mitarbeitern. 
- Bibliothekare arbeiten an Tresen, und vergeben die Leseräume der Bibliothek und
können am Terminal nach Büchern suchen. Sie können auch neue Besucher in der Bibliothek anlegen (Kundenregister).
- Reinigungskräfte säubern alle Arbeitsplätze. Regale und die darin enthaltenen Bücher werden ebenfalls von den Reinigungskräften gesäubert.  
- In der Werkstatt wird ein beschädigtes Buch von einem Restaurator wieder in stand gesetzt und dann zurück in ein Regal gestellt.

### Besucher

### Ausleihe
Bücher können immer 28 Tage ausgeliehen werden. Danach fallen Mahngebühren an.
Die Kosten der Rückgabe berechnen sich wie folgt. 
1. Für die Tage 1-7 wird jeden Tag 1€ berechnet.
2. Ab dem 8. Tag wird jede Woche 5 € verlangt.
3. Ab 43 Tagen wird 2€ die Woche verlangt.
4. Die Strafe darf nie mehr als 100€ betragen
5. Dozenten können das Buch so lange sie wollen ausleihen. Sie zahlen keine Gebühren.

Bei der Rückgabe wird überprüft, ob ein Buch beschädigt ist und ob es rechtzeitig zurückgegeben wurde. Ist die
Beschädigung über 80%, so wird es zur Restauration in die Werkstatt geschickt. Das Buch ist bis zur fertigen Reparatur
bzw. bis es wieder in einem Regal ist nicht ausleihbar.

### Bücher
Es ist möglich Bücher über 100% zu beschädigen


## Aufgabe

Finden Sie die Fehler in der Implementierung. Dabei sollen die Tests als Hilfestellung dienen.
Die HelloLibrary-Methoden können als korrekt angeshen werden. Auch die Tests sind korrekt.
Es gibt zwei Phasen dieser Aufgabe. Die erste ist es die Bugs anhand der Tests zu finden. In der zweiten Phase sollen
dann durch die falschen Consolenoutputs gefunden werden.
Tests, die bereits zu Anfang erfolgreich sind, sollen als Hilfe dienen, wo der Fehler **nicht** ist.  
Da nicht alle Klassen getestet sind muss hier durch clevers Debuggen der Fehler gefunden werden. 