package de.oliver.person.visitor;

import de.oliver.core.Buch;
import de.oliver.structure.Terminal;

public class Studierender extends Besucher {
	public Studierender(int id, String vorname, String nachname) {
		super(id,vorname,nachname);
	}

	@Override
	public boolean ausleihen(Buch buch, Terminal terminal) {
		return terminal.ausleihen(buch,this);
	}

	@Override
	public boolean zurueckgeben(Buch buch, Terminal terminal) {
		return terminal.zurueckgeben(buch);
	}

	@Override
	public boolean bezahlen(Terminal terminal) {
		// Todo implement
		return true;
	}
}
