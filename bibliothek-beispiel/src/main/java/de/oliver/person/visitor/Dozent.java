package de.oliver.person.visitor;

import de.oliver.core.Buch;
import de.oliver.structure.Terminal;

public class Dozent extends Besucher {

	public Dozent(int id) {
		super(id);
	}

	@Override
	public boolean ausleihen(Buch buch, Terminal terminal) {
		return terminal.ausleihen(buch);
	}

	@Override
	public boolean zurueckgeben(Buch buch, Terminal terminal) {
		return terminal.zurueckgeben(buch);
	}

	@Override
	public boolean bezahlen(Terminal terminal) {
		// todo implement
		return true;
	}
}
