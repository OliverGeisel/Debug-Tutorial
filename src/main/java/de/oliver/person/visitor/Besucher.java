package de.oliver.person.visitor;

import de.oliver.person.Person;
import de.oliver.core.Buch;
import de.oliver.structure.Terminal;

public abstract class Besucher implements Person {

	private String vorname,nachname;

	@Override
	public String getVorname() {
		return vorname;
	}

	@Override
	public String getNachname() {
		return nachname;
	}

	@Override
	public String getVollerName() {
		return null;
	}

	@Override
	public int getAlter() {
		return 0;
	}

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
