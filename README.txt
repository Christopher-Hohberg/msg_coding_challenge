Ausführen des Programms (unter Windows 10):

Schritt 1:
Das gesamte Projekt als .ZIP herunterladen und in ein beliebiges lokales Verzeichnis entpacken.

Schritt 2:
Sicherstellen, dass Java Version 8 Update 261 oder später installiert ist.
Falls nicht installiert, hier ein Link zum Herunterladen der neusten Version
- https://www.java.com/de/download/windows-64bit.jsp

Schritt 3:
Ausführen der Datei
- msg_coding_challenge.jar
zu finden in \msg_coding_challenge-master\out\artifacts\msg_coding_challenge_jar
-----------------------------------------------------------------------------------------------------------------------------------

Der verwendete Algorithmus ist eine Abwandlung des k-opt oder auch 2-opt Algorithmus.
Ich habe mich für diesen Algorithmus entschieden, da er, auch bei größeren Eingabemengen, eine vergleichsweise kurze Laufzeit hat und dennoch
gute bis Perfekte Ergebnisrouten erzeugen kann. Um zu verhindern, dass sich der Algorithmus zu schnell in einem Lokalen
Maxima verliert, lasse ich ihn wiederholt über die gleiche, jedoch neu durchmischte, Eingaberoute laufen.

-----------------------------------------------------------------------------------------------------------------------------------

Die beste gefundene Route für die gegebene Liste von Standorten ist:

1 Ismaning/München (Hauptsitz)
2 Passau
3 Ingolstadt
4 Nürnberg
5 Chemnitz
6 Görlitz
7 Berlin
8 Hamburg
9 Schortens/Wilhelmshaven
10 Lingen (Ems)
11 Hannover
12 Braunschweig
13 Münster
14 Essen
15 Düsseldorf
16 Köln/Hürth
17 Frankfurt
18 Walldorf
19 Bretten
20 Stuttgart
21 St. Georgen
Gesamtlänge: 1254,8499 km
Die Gesamtlänge stellt nur eine Annährung der tatsächlichen Entfernung dar, da Höhenunterschiede bei der Umrechnung aus
Geokoordinaten nicht berücksichtigt werden können.

Es kommt vor, dass der Algorithmus die Umgekehrte Route als optimal ausgibt:

1 Ismaning/München (Hauptsitz)
2 St. Georgen
3 Stuttgart
4 Bretten
5 Walldorf
6 Frankfurt
7 Köln/Hürth
8 Düsseldorf
9 Essen
10 Münster
11 Braunschweig
12 Hannover
13 Lingen (Ems)
14 Schortens/Wilhelmshaven
15 Hamburg
16 Berlin
17 Görlitz
18 Chemnitz
19 Nürnberg
20 Ingolstadt
21 Passau
Gesamtlänge: 1254,85 km
