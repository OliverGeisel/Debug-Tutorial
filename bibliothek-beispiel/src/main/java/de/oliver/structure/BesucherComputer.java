package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.person.staff.VerwaltungsException;
import de.oliver.person.visitor.Besucher;

import java.util.Collection;

//Todo Anzahl Bugs: ||||
public class BesucherComputer extends Arbeitsplatz<Besucher> implements Terminal {
	private static int counter = 1;
	private final int number;
	private final BestandsVerwaltung bestand;

	public BesucherComputer(BestandsVerwaltung bestand) {
		if (bestand == null) // Todo Bug Lösung - NullPointerException wenn bestand null ist
			throw new NullPointerException("BestandsVerwaltung darf nicht null sein");
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

	@Override
	public boolean ausleihen(Buch buch, Besucher besucher) throws VerwaltungsException {
		if (!besucher.equals(getNutzer()))
			throw new VerwaltungsException("Du kannst kein Buch für jemand anderen ausleihen");
		return ausleihen(buch); // Todo Bug Lösung
	}

	public boolean ausleihen(Buch buch) {
		if (!isBesetzt()) {
			return false;
		}
		return bestand.ausleihen(buch, getNutzer());
	}

	public boolean zurueckgeben(Buch buch) {
		if (!isBesetzt()) {
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

		throw new UnsupportedOperationException("Diese Art Terminals unterstützt diese Funktionalität nicht!");
	}

	@Override
	public Leseraum reservieren(Besucher... besucher) {
		throw new UnsupportedOperationException("Diese Art Terminals unterstützt diese Funktionalität nicht!");
	}

	@Override
	public boolean bezahlen(Besucher besucher, double betrag) {
		throw new UnsupportedOperationException("Diese Art Terminals unterstützt diese Funktionalität nicht!");
	}


	public String toString() {
		// TODO 'bestzt' muss 'besetzt' sein
		// TODO nutzer != null ? "niemanden" : nutzer zu nutzet == null...
		// VLT. besser einen StringBuilder
		return "Das ist Terminal " + number + ". Und es ist mit " + (nutzer == null ? "niemanden" : nutzer) + " besetzt."; // Todo Bug  Lösung
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.1;
	}
}
