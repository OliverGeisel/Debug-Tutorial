package de.oliver.structure;

import de.oliver.person.staff.Arbeitsplatz;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Kundenregister;

import java.util.Collection;

public class AngestellenComputer extends Arbeitsplatz implements Terminal {

	private Kundenregister kunden;

	public AngestellenComputer(Kundenregister kunden) {
		this.kunden = kunden;
	}


	@Override
	public Buch sucheNachISBN(ISBN isbn) {
		return null;
	}

	@Override
	public Collection<Buch> sucheNachAuthor(String author) {
		return null;
	}

	@Override
	public Buch sucheNachTitel(String titel) {
		return null;
	}

	@Override
	public Buch sucheNachTreffer(String text) {
		return null;
	}

	@Override
	public boolean ausleihen(Buch buch) {
		return false;
	}

	@Override
	public boolean zurueckgeben(Buch buch) {
		return false;
	}

	@Override
	public Regal findeRegal(Buch buch) {
		return null;
	}


	@Override
	public double getMahngebuehren() {
		return 0;
	}

	public boolean besucherHinzufuegen(Besucher besucher) {
		return kunden.addBesucher(besucher);
	}
}
