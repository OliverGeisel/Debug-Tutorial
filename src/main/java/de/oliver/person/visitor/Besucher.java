package de.oliver.person.visitor;

import de.oliver.person.Person;
import de.oliver.core.Buch;
import de.oliver.structure.Terminal;

public abstract class Besucher implements Person {

	private final int ID;

	public Besucher(int id) {
		ID = id;
	}

	/**
	 * @param buch
	 * @return
	 */
	abstract boolean ausleihen(Buch buch, Terminal terminal);

	/**
	 * @param buch
	 * @return
	 */
	abstract boolean zurueckgeben(Buch buch, Terminal terminal);

	/**
	 * @return true wenn keine Probleme beim bezahlen gab, sonst false
	 */
	abstract boolean bezahlen(Terminal terminal);

	public int getID() {
		return ID;
	}
}
