package de.oliver.structure;

import de.oliver.person.staff.Arbeitsplatz;

import java.util.Collection;

// Anzahl Bugs:
public class BesucherComputer extends Arbeitsplatz implements Terminal {
	private static int counter = 1;
	private final int number;
	private final Bestandsverwaltung bestand;

	public BesucherComputer(Bestandsverwaltung bestand) {
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
	public Regal findeRegal(Buch buch) {
		return bestand.getRegal(buch);
	}

	@Override
	public double getMahngebuehren() {
		return 0;
	}


	public String toString() {
		// VLT. besser einen StringBuilder
		return "Das ist Terminal " + number + ". Und es ist mit " + (nutzer != null ? "niemanden" : nutzer) + " bestzt.";
	}
}
