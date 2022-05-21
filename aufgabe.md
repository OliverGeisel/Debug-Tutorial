
---
title: "Debug-Tutorial"
---

# Debug-Tutorial
Dies ist eine Einführung in das Debugging mit IDEs. Es soll anhand mehrerer kleiner Beispiele die Grundlagen verständlich beigebracht werden.
Wenn diese Grundlagen abgeschlossen wurden, dann ist in einem großen Beispiel das Erlernte geübt. 

Die Einführung, mit den entsprechenden Java-Dateien, ist in dem Ordner "start" zu finden.


## Wie es nicht geht!
In der Datei *Breakpoint.java* enthält eine Funktion summeVonBis(a,b). Diese Funktion soll die Summe von a bis b (inklusive beider Zahlen) zurückgeben.
Jedoch ist ein Fehler in dem Programm und das Ergebnis ist falsch. Auch die dazugehörigen Tests (test.java.BreakpointTest.java) schlagen fehl.
### Was man instinktiv tut.
Wenn der Fehler jetzt nicht ersichtlich ist, würde ein erster Gedanke sein, die Werte, die sich in der Schleife verändern, auf der Konsole auszugeben.
Das wäre beispielsweise ```System.out.println("run ist: " + run);``` 


## Grundlagen des Debuggen mit einer IDE

### Debug-Modus

Der Debug-Modus ist das Starten einer Anwendung, in der man in der Lage ist den Speicher auszulesen und Schritt für
Schritt durch das Programm gehen kann.
Dieser Modus ist deutlich langsamer, als der normale Modus. In IDEs ist dieser Modus durch das Klicken auf einen
speziellen Button (meist ein Käfer-Symbol) startbar.
Überwiegend ändert sich dabei die Ansicht in der IDE oder ein extra Fenster erscheint.

**Aufgabe:** Finden Sie diesen Button zum Starten des Debug-Modus in ihrer IDE. Hier kann wieder die Datei *Breakpoint.java* genommen werden.

### Breakpoints
Wenn der Debug-Modus gestartet wurde, dann sollte nix besonders passieren. Lediglich in der Konsole stehen zwei extra Zeilen.
Das liegt daran, dass der Debugger nicht weiß, wann er was tun soll. So läuft er einfach ganz normal durch das Programm.\
Es wird also eine Markierung benötigt, die dem Debugger sagt: "Hier bitte halten!" Das erledigt ein Breakpoint.

Breakpoints sind Haltepunkte im Code, die der Entwickler selbständig setzt. Sie werden gesetzt, indem man links neben
Zeile im Programmcode einfach oder doppelt klickt.
Dort sollte dan eine Markierung auftauchen. Dies ist dann ein gesetzter Breakpoint.
Breakpoints lassen sich beliebig an und ausschalten oder auch wieder entfernen. Ein Breakpoint ist ausgeschaltet, wenn
er (je nach IDE) ausgegraut oder durchgestrichen ist.
Viele IDEs besitzen eine Breakpoint-Übersicht, in der listen artig steht, wo welcher Breakpoint ist und ob dieser
aktiviert ist.

**Wichtig!** Ein Breakpoint hält **vor** der markierten Zeile. Die Zeile wurde also noch nicht ausgeführt.

**Aufgabe:** Setzen Sie an den Anfang eines Programms (Breakpoint.java) einen Breakpoint und starten Sie den Debug-Modus. Das Programm
sollte an dieser Stelle halten und die Fenster des Debug-Modus sollten nun gefüllt sein.

#### Breakpoint Arten
Der normale Breakpoint ist ein Line-Breakpoint. Dieser hält in der bzw. vor der Zeile.
Es gibt aber auch andere Breakpoints, die nur bei einem Lambda-Ausdruck halten.
Auch die Methode kann einen Breakpoint besitzen. Jedoch verlangsamen Methoden-Breakpoints das System sehr und sollten deswegen nur begrenzt eingesetzt werden. 
Ein normaler Line-Breakpoint in der ersten Zeile des Rumpfes funktioniert genauso.
Die letzte Art sind bedingte Breakpoints. Diese können beispielsweise bei einem Schleifendurchlauf erst nach fünfmaligem passieren aktiv werden und eben erst den sechsten Lauf pausieren. 
**Aufgabe:** In der Datei *BreakpointArten.java* ist eine Methode mit Kommentaren. Diese Kommentare beschreiben die Breakpoint Arten. Setzen Sie diese in die entsprechende Zeile ein. Testen Sie die Breakpoints. 
Für bedingte-Breakpoints gibt es eine extra Datei. In "BreakpointBedingung.java" ist es das Ziel sich pi zu näheren. Jedoch ist ein Fehler im Programm.
Um zumindest das letzte Ergebnis vor dem Fehler zu erhalten, soll der Breakpoint nur vor dem Ausführen der bösen Aktion halten. 
Es könnte auch anders gelöst werden, jedoch soll hier ein bedingter Breakpoint genutzt werden, der nur ein mal hält und sonst ignoriert wird.


### Im Code vorangehen

Es gibt viele verschiedene Möglichkeiten im Code voranzukommen. Die gängigsten sind:

- Step over → geht zum nächsten Befehl, der folgt
- Step into → springt in die folgende Methode hinein und wird dort weiter geführt
- Step out → Gegenteil zu step into. Spring aus der Methode und landet in der Methode die im Stack "darunter" liegt.
- Continue → führt so lange fort bis der nächste Breakpoint kommt.

Weitere Optionen, die aber nur manche IDEs haben, sind:

- Run to Cursor → Das Programm läuft bis zum Cursor weiter (oder zum nächsten Breakpoint)
- Drop Frame → Dies verwirft eine Methode und "resetet" sie. Es startet also die Methode neu. 

**Aufgabe:** Finden Sie diese Möglichkeiten in der IDE ihrer Wahl. Die Datei *Breakpoint.java* kann dabei helfen die Funktion dieser Buttons zu verstehen 


### Auslesen/Manipulation des Speichers

Wenn ein Breakpoint erreicht wurde, so können alle Objekte und Variablen, die zu dieser Zeit existieren eingesehen
und manipuliert werden.
#### Auslesen
Im Normalfall sollte nun ein "Variablen"-Fenster auftauchen. In diesem sind Bezeichnungen wie this, args usw zu finden.
Das sind die momentanen Objekte, die in dem aktuellen Scope genutzt werden können. 
Da Objekte, aus mehreren Teilen bestehen, können auch diese in dem Variablen-Fenster angesehen werden. 

#### Manipulation
Manchmal ist es sinnvoll die Objekte während des Debugging zu ändern. Damit kann beispielsweise ein ```assert condition;``` geprüft werden.
Eine andere Möglichkeit wäre es in einem if-Statement die Variablen in der condition zu ändern und damit in den anderen Zweig zu gehen, als eigentlich vorgesehen.
Leider lassen sich nicht alle Variablen ändern. Variablen/Attribute, die mit ```final``` gekennzeichnet sind, können nicht geändert werden. Auch ein Debugger kann das nicht umgehen. 

**Aufgabe:** Öffnen Sie WerteManipulation.java. Dieser Code darf *nicht* verändert werden. 
Wählen Sie ein Level durch die Angabe eines zusätzlichen Programmargumentes. Ziel ist es das Level zu absolvieren, indem durch den Debugger die Werte der Variablen geändert werden.
Ein Level ist geschafft, wenn am Ende "Glückwunsch! Du hast Level X abgeschlossen!" auf der Konsole ausgegeben wird. 
**Achtung** Level 4 ist sehr schwer und benötigt deswegen etwas an Überlegung.


### Frame stack
Ein weiters Fenster sollte "Frames" oder ähnlich heißen. Dort sollte als Erstes die momentane Methode auftauchen 
und darunter alle aufgerufenen Methoden stehen. Die letzte Methode müsste die Main-Methode sein.











## Tests und Debugging
Dies ist das große Beispiel, in dem die neuen Kenntnisse angewendet und gefestigt werden sollen. 
### Situation

Die HelloLibrary wurde inzwischen um eine Mitarbeiterverwaltung und ein Kundenregister erweitert.
Die Bibliothek ist inzwischen ziemlich groß geworden und wird deshalb von Personal verwaltet und hat weitere Räume
bekommen.
Nun soll es auch möglich sein, dass Kunden Bücher ausleihen können und auch zurückgeben können.
Bücher werden dabei in Regale gestellt. Werden Bücher ausgeliehen, so werden sie aus dem entsprechenden Regal
genommen. Zwei SHKs haben diese Anwendung gebaut.
Der eine hat die Tests, der andere den Quellcode geschrieben.
Leider sind im Quellcode Fehler. Dies wurde durch die Tests herausgefunden.
Die SHKs sind nicht mehr angestellt und die Fehler müssen schnell behoben werden.
Die Tests sind **korrekt** implementiert und benötigen **keine Anpassung**.

## Aufgabe

Finden Sie die Fehler in der Implementierung. Dabei sollen die Tests als Hilfestellung dienen.
Die HelloLibrary-Methoden können als korrekt angeshen werden. Auch die Tests sind korrekt.
Es gibt zwei Phasen dieser Aufgabe. Die Erste ist es die Bugs anhand der Tests zu finden. In der zweiten Phase sollen
dann durch die falschen Konsolen-Outputs Fehler gefunden werden.
Tests, die bereits zu Anfang erfolgreich sind, sollen als Hilfe dienen, wo der Fehler **nicht** ist.  
Da nicht alle Klassen getestet sind muss hier durch clevers Debuggen der Fehler gefunden werden.

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
Ein Buch ist ein Medium, das von einem Author verfasst wurde. Es enthält Text und einen Titel.
Ist ein Buch mehrmals in der Bibliothek, werden die Instanzen durch einen Code (Zufallszahl) unterschieden. 

#### ISBN
Die ISBN ist eine eindeutige ID des Buches. Es sind 13 Zahlen, die Region, Verlag, Titel und eine Prüfziffer repräsentieren. 
#### Beschädigung
Jedes Buch kann beschädigt werden. Wenn es eine bestimmte Beschädigung überschreitet, gilt es als kaputt. Ist dies der Fall, muss es repariert werden.
In dieser Aufgabe ist es möglich Bücher über 100% zu beschädigen. Das ist jedoch kein Fehler.
#### Ausleihbar
Ein Buch ist ausleihbar. Dies ist dann einem Besucher zugeordnet. 

### Bestand

