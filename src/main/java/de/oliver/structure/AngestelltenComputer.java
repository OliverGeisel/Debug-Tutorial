package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.person.staff.Angestellter;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Kundenregister;

import java.util.Collection;

public class AngestelltenComputer extends Arbeitsplatz<Angestellter> implements Terminal {

	private final Kundenregister kunden;
	private final BestandsVerwaltung bestand;

	private final Leseraum[] raeume;
	// todo implement

	public AngestelltenComputer(Kundenregister kunden, BestandsVerwaltung bestand, Leseraum[] raeume) {
		this.kunden = kunden;
		this.bestand = bestand;
		this.raeume = raeume;
	}


	@Override
	public Buch sucheNachISBN(ISBN isbn) {
		return bestand.sucheNachISBN(isbn);
	}

	@Override
	public Collection<Buch> sucheNachAuthor(String author) {
		return bestand.sucheNachAuthor(author) ;
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
	public boolean ausleihen(Buch buch) {
		// todo fix
		return false;
	}

	@Override
	public boolean zurueckgeben(Buch buch) {
		// Todo fix
		return bestand.zurueckgeben(buch);
	}

	@Override
	public String findeRegalCode(Buch buch) {
		return bestand.getRegalCode(buch);
	}


	@Override
	public double getMahngebuehren(Besucher besucher) {


		return kunden.getStrafe(besucher);
	}

	@Override
	public Leseraum reservieren(Besucher... besucher) {

		// todo  fix
		return raeume[0];
	}

	@Override
	public boolean bezahlen(Besucher besucher, double betrag) {
		return kunden.bezahlen(besucher);
	}

	public boolean besucherHinzufuegen(Besucher besucher) {
		return kunden.addBesucher(besucher);
	}

	@Override
	public void verschmutzen() {

	}
}
