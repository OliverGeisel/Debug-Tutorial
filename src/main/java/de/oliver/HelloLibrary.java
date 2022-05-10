package de.oliver;


import de.oliver.person.Geschlecht;
import de.oliver.person.staff.*;
import de.oliver.structure.Bestandtyp;
import de.oliver.structure.Bibliothek;
import de.oliver.core.Buch;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Dozent;
import de.oliver.person.visitor.Studierender;
import de.oliver.core.ISBN;

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
		bib = new Bibliothek("SLUB", 20, Bestandtyp.Array, 2);

		biblo1 = new Bibliothekar("Anna", "Kohl", Geschlecht.WEIBLICH, 25);
		biblo2 = new Bibliothekar("John", "Zimmer", Geschlecht.MAENNLICH, 32);
		reinigung1 = new Reinigungskraft("Tom", "Müller", Geschlecht.MAENNLICH, 20);
		reinigung2 = new Reinigungskraft("Andre", "Kellner", Geschlecht.MAENNLICH, 31);
		restaurator = new Restaurator("Lena", "Büttner", Geschlecht.WEIBLICH, 26);

		stu1 = new Studierender(1);
		stu2 = new Studierender(2);
		stu3 = new Studierender(3);
		dozent = new Dozent(4);
	}

	private void init() {
		bib.anstellen(biblo1, Bereich.KUNDENBETREUUNG);
		bib.anstellen(biblo2, Bereich.KUNDENBETREUUNG);
		bib.anstellen(reinigung1, Bereich.REINIGUNG);
		bib.anstellen(reinigung2, Bereich.REINIGUNG);
		bib.anstellen(restaurator, Bereich.WERKSTATT);


		var arbeitsplatz1 = bib.getAngestellenComputer().get(0);
		arbeitsplatz1.hinsetzen(biblo1);

		for (Besucher besucher : getAlleBesucher()) {
			arbeitsplatz1.besucherHinzufuegen(besucher);
		}

		bib.neuerBesucher(stu1);
		bib.neuerBesucher(stu2);
		bib.neuerBesucher(stu3);
		bib.neuerBesucher(dozent);
		var isbn = new ISBN(123, 2, 34, 234, 2);
		for (int i = 0; i < 100; i++) {
			bib.einfuegen(new Buch(Integer.toString(i), isbn));
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