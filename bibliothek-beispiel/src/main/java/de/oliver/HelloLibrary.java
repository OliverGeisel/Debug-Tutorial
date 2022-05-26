package de.oliver;


import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.person.Geschlecht;
import de.oliver.person.staff.*;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Dozent;
import de.oliver.person.visitor.Kundenregister;
import de.oliver.person.visitor.Studierender;
import de.oliver.structure.BestandsVerwaltung;
import de.oliver.structure.Bibliothek;

import java.time.LocalTime;
import java.util.List;

/**
 * Achtung die Klasse dient als Laufzeit-Beispiel. Hier ist nicht alles OOP konform implementiert.
 */
public class HelloLibrary {

	private Bibliothek bib;

	private Bibliothekar biblo1;
	private Bibliothekar biblo2;
	private Reinigungskraft reinigung1;
	private Reinigungskraft reinigung2;
	private Restaurator restaurator;

	private Studierender stu1;
	private Studierender stu2;
	private Studierender stu3;
	private Dozent dozent;


	private List<Besucher> getAlleBesucher() {
		return List.of(stu1, stu2, stu3, dozent);
	}

	public static void main(String[] args) {
		// write your code here
		HelloLibrary run = new HelloLibrary();
		run.init();
		run.spezielleBuecher();
	}

	public HelloLibrary() {
		bib = new Bibliothek("SLUB", 20, 2, LocalTime.of(8, 0), LocalTime.of(23, 0));

		biblo1 = new Bibliothekar("Anna", "Kohl", Geschlecht.WEIBLICH, 25);
		biblo2 = new Bibliothekar("John", "Zimmer", Geschlecht.MAENNLICH, 32);
		reinigung1 = new Reinigungskraft("Tom", "Müller", Geschlecht.MAENNLICH, 20);
		reinigung2 = new Reinigungskraft("Andre", "Kellner", Geschlecht.MAENNLICH, 31);
		restaurator = new Restaurator("Lena", "Büttner", Geschlecht.WEIBLICH, 26);

		stu1 = new Studierender(1,"Timo", "Morgenstern");
		stu2 = new Studierender(2, "Nina","Richter");
		stu3 = new Studierender(3,"Sven","Kahlschlag");
		dozent = new Dozent(4,"Heiko","Werner");
	}

	private void init() {
		System.out.println("❤lich Willkommen in der " + bib.getName() + "!");
		AngestelltenVerwaltung angestelltenVerwaltung = bib.getVerwaltung();
		angestelltenVerwaltung.angestelltenHinzufuegen(biblo1, Bereich.KUNDENBETREUUNG);
		angestelltenVerwaltung.angestelltenHinzufuegen(biblo2, Bereich.KUNDENBETREUUNG);
		angestelltenVerwaltung.angestelltenHinzufuegen(reinigung1, Bereich.REINIGUNG);
		angestelltenVerwaltung.angestelltenHinzufuegen(reinigung2, Bereich.REINIGUNG);
		angestelltenVerwaltung.angestelltenHinzufuegen(restaurator, Bereich.WERKSTATT);


		var arbeitsplatz1 = bib.getAngestellenComputer().get(0);
		arbeitsplatz1.hinsetzen(biblo1);

		for (Besucher besucher : getAlleBesucher()) {
			arbeitsplatz1.besucherHinzufuegen(besucher);
		}

		Kundenregister kunden = bib.getRegister();

		kunden.addBesucher(stu1);
		kunden.addBesucher(stu2);
		kunden.addBesucher(stu3);
		kunden.addBesucher(dozent);

		BestandsVerwaltung bestand = bib.getBestandsverwaltung();
		for (int i = 0; i < 100; i++) {
			var isbn = new ISBN(978, i, 34, 234, 2);
			bestand.neuesBuchHinzufuegen(new Buch(Integer.toString(i), isbn));
		}
	}

	private void spezielleBuecher() {

	}

	private void ausleihen() {

	}

	private void saeubern() {

	}

	private void reparieren() {

	}


}
