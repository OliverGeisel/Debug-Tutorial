package de.oliver.structure;

import de.oliver.person.staff.Angestelltenverwaltung;
import de.oliver.person.staff.Angestellter;
import de.oliver.person.staff.Bereich;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Kundenregister;

import java.util.*;

// Anzahl Bugs: ||
public class Bibliothek {

	private final String name;
	private final List<Buch> bestand;
	private final Bestandsverwaltung bestandsverwaltung;
	private final Angestelltenverwaltung verwaltung;
	private final Kundenregister register;
	private final Leseraum[] raeume;
	private final Werkstadt werkstadt;
	private final List<BesucherComputer> besucherComputer;
	private final List<AngestellenComputer> angestellenComputer;
	private final Set<Regal> regale;

	public Bibliothek(String name, int anzahlRaeume, Bestandtyp typ, int anzahlRegale) {
		this.name = name;
		raeume = new Leseraum[anzahlRaeume];
		for (Leseraum raum : raeume) { // ----
			raum = new Leseraum(2);
		}
		verwaltung = new Angestelltenverwaltung();
		register = new Kundenregister();
		bestandsverwaltung = new Bestandsverwaltung(register);
		switch (typ) {
			case Linked:
				bestand = new LinkedList<>();
				break;
			case Array:

			default:
				bestand = new ArrayList<>();
		}
		regale = new HashSet<>();
		int i = 1;
		while (i < anzahlRegale) {
			regale.add(new Regal(Regal.REGALBRETTER_DEFAULT, Regal.BUECHER_JE_BRETT_DEFAULT, Integer.toString(i)));
			i++;
		}
		werkstadt = new Werkstadt(this);
		angestellenComputer = new ArrayList<>();
		for (i = 0; i < 2; i++) {

			angestellenComputer.add(new AngestellenComputer(register));
		}
		besucherComputer = new ArrayList<>();
		for (i = 0; i < 3; i++) {
			besucherComputer.add(new BesucherComputer(this.bestandsverwaltung));
		}
	}

	public boolean anstellen(Angestellter neuerAngestellter, Bereich bereich) {
		verwaltung.angestelltenHinzufuegen(neuerAngestellter, bereich);
		System.out.printf("Der Angestellte: %s wurde angestellt\n", neuerAngestellter);
		return true;
	}

	Buch sucheNachISBN(String isbn) {
		for (Buch buch : bestand) {
			if (buch.getIsbn().equals(isbn)) {
				return buch;
			}
		}
		return null;
	}


	public boolean neuerBesucher(Besucher besucher) {
		return register.addBesucher(besucher);
	}

	public Buch einfuegen(Buch buch) {
		bestand.add(buch);
		Regal verweis = null;
		for (Regal regal : regale) {
			verweis = regal;
			if (regal.isVoll()) {
				continue;
			}
			regal.hineinStellen(buch);
		}
		bestandsverwaltung.hinzufuegen(buch, verweis);
		return buch;
	}

	public Buch sortiertesEinfuegen(Buch buch) {
		Buch back = einfuegen(buch);
		Collections.sort(bestand);
		return back;
	}

	public boolean ausleihen(Buch buch) {
		if (buch.isAusgeliehen()) {
			return false;
		}
		buch.ausleihen();
		ausRegalNehmen(buch);
		// Todo aus Regal nehmen
		return true;
	}

	Regal ausRegalNehmen(Buch b) {
		boolean success = false;
		for (Regal regal : regale) {
			try {
				regal.herausnehmen(b);
				success = true;
				break;
			} catch (NoMatchingBookException be) {
				// Nothing to do
			}
			bestandsverwaltung.entferneAusRegal(b);
		}
		return null;
	}

	boolean ausRegalNehmen(Buch buch, Regal regal) {
		regal.herausnehmen(buch);
		bestandsverwaltung.entferneAusRegal(buch);
		return true;
	}

	Regal insRegalStellen(Buch b) {
		for (Regal regal : regale) {
			if (regal.isVoll()) {
				continue;
			}
			regal.hineinStellen(b);
			bestandsverwaltung.hinzufuegen(b, regal);
			return regal;
		}
		return null;
	}

	public boolean zurueckgeben(Buch buch) {
		// Todo implement
		// annehmen

		// prüfen

		// einlagern (Regal oder Werkstadt)
		return true;
	}

	public List<AngestellenComputer> getAngestellenComputer() {
		return angestellenComputer;
	}

	public String getName() {
		return name;
	}

	public List<BesucherComputer> getBesucherComputer() {
		return besucherComputer;
	}

	public Leseraum[] getLeseraeume() {
		return raeume;
	}

	public Werkstadt getWerkstadt() {
		return werkstadt;
	}
}
