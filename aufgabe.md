
---
title: "Debug-Tutorial"
---

# Debug-Tutorial
Dies ist eine Einführung in das Debugging mit IDEs. Es soll anhand mehrerer kleiner Beispiele die Grundlagen verständlich beigebracht werden.
Wenn diese Grundlagen abgeschlossen wurden, dann ist in einem großen Beispiel das Erlernte geübt. 

Die Einführung, mit den entsprechenden Java-Dateien, ist in dem Ordner "start/src/main/java" zu finden.


## Wie es nicht geht!
In der Datei *Breakpoint.java* enthält eine Funktion summeVonBis(a,b). Diese Funktion soll die Summe von a bis b (inklusive beider Zahlen) zurückgeben.
Jedoch ist ein Fehler in dem Programm und das Ergebnis ist falsch. Auch die dazugehörigen Tests (src/test/java/BreakpointTest.java) schlagen fehl.
Analysiert man jetzt den Code, so findet man ziemlich schnell den Fehler. Doch um den zu verstehen, wozu ein Debugger gebraucht wird, sei jetzt mal angenommen der Fehler wird nicht
auf den ersten Blick gefunden.

### Was man instinktiv tut.
Wenn der Fehler jetzt nicht ersichtlich ist, würde ein erster Gedanke sein, die Werte, die sich in der Schleife verändern, auf der Konsole auszugeben.
Das wäre beispielsweise ```System.out.println("run ist: " + run);``` 
*BreakpointPrint.java* zeigt eine Möglichkeit, wie "print" zur Fehlersuche genutzt werden kann.
In der Konsole fällt auf, dass "run" höchstens 6 ist. Damit fehlt ein Durchlauf und es ist klar, wo der Fehler ist. Es ist im Schleifenkopf. Es handelt sich um einen sogenannten "Off by one"-Fehler.
Das bedeutet, das große Ganze ist korrekt, nur ist die Berechnung um eins falsch. Eine Lösung wäre demnach entweder die Bedingung in ```i < b+1``` oder ```i <= b``` zu ändern.

#### Probleme
Diese Lösung ist für kleine Beispiele noch vertretbar. Wenn jedoch Projekte mit tausenden Zeilen Code einen Fehler haben, dann ist diese Lösung sehr suboptimal.
Da überall print-Statements gesetzt werden müssen, ist das der Code damit überschwemmt und nachdem der Fehler behoben wurde müssen alle print-Statements entfernt werden.\
Zudem sind alle print-Anweisungen mit extra Informationen zu versehen, damit der Entwickler überhaupt weiß, welche Variable jetzt ausgegeben wurde. 

Ein weiteres Problem ist, dass das Programm durchläuft und nicht angehalten werden kann. Es muss die gesamte Ausgabe durchsucht und verstanden werden, um zu wissen, was an welcher Stelle etwas passiert ist. Es wäre also gut auf Wunsch anzuhalten und dann Schritt für Schritt voranzugehen. 
Das Könnte mit einer Input-Abfrage programmiert werden, aber auch das muss am Ende wieder entfernt werden. 
Das nächste Problem ist, dass die Werte nicht verändert werden können. Das klingt erst mal nutzlos, aber es gibt Situationen, in denen man den Wert einer Variable ändern möchte, um ein anderes Verhalten als normal zu erhalten. 
Durch die vielen Ausgaben ist die Übersicht sehr schwer. Eine Filterung der Ausgaben muss deshalb mit programmiert werden. 

### Etwas besser aber...
Zumindest zwei Probleme lassen sich mit Loggern lösen. Durch Logger kann die Filterung deutlich einfacher programmiert werden. Auch durch die Nutzung mehrerer Logger bzw. durch die Nutzung der verschiedenen Level ist die zusätzliche Information und Filterung kein Problem mehr.
Ein Beispiel für das Nutzen eines Loggers ist in der Datei "BreakpointLogging.java" zu finden.

Es bleiben aber die Probleme, dass Code geschrieben und wieder entfernt werden muss und das Programm nicht unterbrechbar ist.

### Ort des Fehlers finden.
Wenn eine nicht gefangene Exception in Java auftritt, dann wird ein sogenannter Stack Trace ausgegeben. 
Dieser enthält 4 wichtige Informationen:
1. Name des Threads in dem die Exception auftrat
2. Typ der Exception
3. Beschreibung/ Grund der Exception
4. Methoden Stack

Der Name des Threads ist bei single threaded-Anwendungen immer der main-Thread (Java). Er ist nur wichtig, wenn die Anwendung mehrere Threads nutzt.\
Der Typ der Exception ist schon wichtiger. Er soll eine Einordnung geben, wieso der Fehler auftrat. Deswegen sollten auch immer die Typen der Exception gut gewählt und beschreibend sein.\
Die konkrete Information, warum der Fehler auftrat, soll dann die Beschreibung der Exception geben. In "ExceptionBeispiele.java" gibt es ein Beispiel, das keine Informationen über die Ursache gibt. Deshalb sollte immer gut überlegt werden, welche Exception geworfen wird und welche Information mitgegeben wird.  
Beispielsweise gibt es in der Datei "ExceptionBeispiele.java", wenn man die Anwendung mit dem Argument 2 startet, einen Stack Trace der eine NoSuchElementException wirft und als Grund "No value Present!" ausgibt.
Beide Informationen zusammen sagen, dass ein bestimmtes Element gesucht wurde (Exception), es aber nicht gefunden wurde, da in dem genutzten Objekt kein Wert verfügbar war.
Das lässt darauf schließen irgendwo muss ```null``` einer Variable zugewiesen worden sein. 

Die Information, in welcher Zeile der Fehler geworfen wurde, steht im Methoden Stack. Und zwar steht die Information nicht am Ende des Stackes, sonder ganz oben. Direkt nach dem Grund der Exception.
In dem zuvor genannten Beispiel ist es die Zeile 143 in der Klasse Optional des JDK. Dort findet man auch die entsprechende Zeile ```throw new NoSuchElementException("No value present!");```.
Das sagt uns jetzt aber nur wo sie geworfen wurde. Das ist aber nicht die Zeile, die den Fehler verursacht. Deshalb muss eine Zeile weiter unten im Methoden Stack geschaut werden. 
Dort sieht man, dass in Zeile 24 der ExceptionBeispiele in der Methode "tiefeException" der Aufruf des Optionals war. Da man im Normalfall davon ausgehen kann, dass die Klassen im JDK korrekt sind, muss der Fehler in der Nähe dieser Zeile 24 liegen.\
Eine weitere Hilfe bietet hier die JavaDoc von Optional.get(). In dieser steht, dass die Exception nur geworfen wird, wenn der Wert innerhalb des Optionals ```null``` ist.
Nun schaut man sich den Code an und sieht, dass ein paar Zeilen darüber ```Optional.ofNullable(null)``` aufgerufen wird. Damit ist der Fehler gefunden.

Das letzte Beispiel der "ExceptionBeispiele.java" ist etwas schwerer. Hier wird eine Exception geworfen, die durch eine Exception verursacht wurde. Den Fehler hier zu finden ist deutlich schwerer und benötigt etwas Bedenkzeit.
Jedoch lässt sich der Fehler auch finden, wenn die verursachende Exception analysiert wird. Wieder ist es eine NoSuchElementException und wieder ist es das Optional.get(). Es muss also wieder irgendwie mit dem Optional zu tun haben.

**Aufgabe:** Führen Sie die "ExceptionBeispiele.java" aus und probieren Sie in allen Fällen die Ursache zu finden.\
*Hinweis!* Das Beispiel mit dem Programmargument 1 ist nur ein Demonstrationsbeispiel, wie Exceptions nicht aussehen sollten. 

## Grundlagen des Debuggen mit einer IDE
Da die Änderungen direkt am Code nicht ideal sind, um Fehler zu finden, muss eine ander Lösung gefunden werden. 
Anstatt das Programm normal laufen zu lassen, wäre es doch cleverer das Programm in eine Umgebung zu setzen, in der der Nutzer bestimmt, wann etwas geschieht und alles überwachen zu können.
Genau das macht ein Debugger.

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
2. Ab dem 8. Tag wird jede angefangene Woche 5 € verlangt.
3. Ab 43 Tagen wird 2€ pro angefangener Woche verlangt.
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

