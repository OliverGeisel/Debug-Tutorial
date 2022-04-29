package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.person.staff.Angestelltenverwaltung;
import de.oliver.person.staff.Angestellter;
import de.oliver.person.staff.Bereich;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Dozent;
import de.oliver.person.visitor.Kundenregister;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

// Anzahl Bugs: ||
public class Bibliothek {

	private final String name;
	private final Bestandsverwaltung bestandsverwaltung;
	private final Angestelltenverwaltung verwaltung;
	private final Kundenregister register;
	private final Leseraum[] raeume;
	private final Werkstatt werkstatt;
	private final List<BesucherComputer> besucherComputer;
	private final List<AngestellenComputer> angestellenComputer;
	private final Set<Regal> regale;

	public Bibliothek(String name, int anzahlRaeume, Bestandtyp typ, int anzahlRegale) {
		this.name = name;
		raeume = new Leseraum[anzahlRaeume];
		for (Leseraum raum : raeume) { // ---- innere hat keine Wirkung
			raum = new Leseraum(2);
		}
		verwaltung = new Angestelltenverwaltung();
		register = new Kundenregister();
		bestandsverwaltung = new Bestandsverwaltung(register);
		regale = new HashSet<>();
		int i = 1;
		while (i < anzahlRegale) {
			regale.add(new Regal(Regal.REGALBRETTER_DEFAULT, Regal.BUECHER_JE_BRETT_DEFAULT, Integer.toString(i)));
			i++;
		}
		werkstatt = new Werkstatt(this);
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


	public boolean neuerBesucher(Besucher besucher) {
		return register.addBesucher(besucher);
	}

	public Buch einfuegen(Buch buch) {
		// Todo fix
		bestandsverwaltung.hinzufuegen(buch,null);
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

	Regal insRegalStellen(Buch buch) {
		for (Regal regal : regale) {
			if (regal.isVoll()) {
				continue;
			}
			regal.hineinStellen(buch);
			bestandsverwaltung.hinzufuegen(buch, regal);
			return regal;
		}
		return null;
	}

	public boolean zurueckgeben(Buch buch, Besucher kunde) {
		// prüfen/ kosten berechnen;
		Double kosten = berechneKosten(buch);
		if (kunde instanceof Dozent){
			kosten = 0.0;
		}
		register.erhoeheStrafe(kunde, kosten);
		// einlagern (Regal oder Werkstadt)
		if (buch.getBeschaedigung() >= 0.5) {
			werkstatt.zurReparaturHinzufuegen(buch);
		} else {
			insRegalStellen(buch);
		}
		return true;
	}

	private Double berechneKosten(Buch buch) {
		LocalDate rueckgabeDatum = buch.getAusleihdatum().plusDays(28);
		if (!rueckgabeDatum.isAfter(LocalDate.now())) {
			return 0.0;
		}
		long ueberzogeneTage = Duration.between(rueckgabeDatum.atStartOfDay(), LocalDate.now()).toDays();

		double kosten = 0.0;
		long ersteTage = Math.min(7,ueberzogeneTage);
		long restage = ueberzogeneTage-ersteTage;
		kosten += ersteTage * 1.0;
		// Für die Tage 1-7 wird jeden Tag 1€ berechnet.
		long wochen = (restage) / 7; // Fehler - Es fehlt +6 um immer ab wochenbeginn zu zählen
		// Ab dem 8. Tag wird jede Woche 5 € verlangt.
		kosten += 5 * Math.min(6,wochen);
		wochen -= Math.min(6,wochen);
		// Ab 43 Tagen wird 2€ die Woche verlangt.
		kosten += wochen*2;
		// Die Strafe wird nie mehr als 100€ betragen
		return Math.min(100.0, kosten);
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

	public Werkstatt getWerkstadt() {
		return werkstatt;
	}
}
