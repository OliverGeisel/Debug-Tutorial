package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.person.visitor.Besucher;

import java.util.Collection;

// Anzahl Bugs:
public class BesucherComputer extends Arbeitsplatz<Besucher> implements Terminal {
	private static int counter = 1;
	private final int number;
	private final BestandsVerwaltung bestand;

	public BesucherComputer(BestandsVerwaltung bestand) {
		this.bestand = bestand;
		number = counter;
		counter++;
	}

	@Override
	public Buch sucheNachISBN(ISBN isbn) {
		return bestand.sucheNachISBN(isbn);
	}

	@Override
	public Collection<Buch> sucheNachAuthor(String author) {
		return bestand.sucheNachAuthor(author);
	}

	@Override
	public Buch sucheNachTitel(String titel) {
		return bestand.sucheNachTitel(titel);
	}

	@Override
	public Buch sucheNachTreffer(String text) {
		return bestand.sucheNachTreffer(text);
	}

	public boolean ausleihen(Buch buch) {
		if (isBesetzt()) {
			return false;
		}
		return bestand.ausleihen(buch);
	}

	public boolean zurueckgeben(Buch buch) {
		if (isBesetzt()) {
			return false;
		}
		return bestand.zurueckgeben(buch);

	}

	@Override
	public String findeRegalCode(Buch buch) {
		return bestand.getRegalCode(buch);
	}

	@Override
	public double getMahngebuehren(Besucher besucher) {
		// Todo fix
		return 0;
	}

	@Override
	public Leseraum reservieren(Besucher... besucher) {
		return null;
	}

	@Override
	public boolean bezahlen(Besucher besucher, double betrag) {
		return false;
	}


	public String toString() {
		// VLT. besser einen StringBuilder
		return "Das ist Terminal " + number + ". Und es ist mit " + (nutzer != null ? "niemanden" : nutzer) + " bestzt.";
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.1;
	}
}
