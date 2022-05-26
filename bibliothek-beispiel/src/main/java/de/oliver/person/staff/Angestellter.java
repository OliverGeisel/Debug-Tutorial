package de.oliver.person.staff;

import de.oliver.person.Geschlecht;
import de.oliver.person.Person;

public abstract class Angestellter implements Person {

	private int alter;
	private final String vorname, nachname;
	private final Geschlecht geschlecht;

	public Angestellter(String name, String nachname, Geschlecht geschlecht, int alter) {
		if (name == null || nachname == null || alter < 18) {
			throw new IllegalArgumentException("Ungültige Namen bzw. Alter!");
		}
		if (name.isBlank() || nachname.isBlank()) {
			throw new IllegalArgumentException("Der Vor-/Name darf nicht leer sein");
		}
		this.vorname = name;
		this.geschlecht = geschlecht;
		this.alter = alter;
		this.nachname = nachname;
	}

	@Override
	public int getAlter() {
		return alter;
	}

	public void setAlter(int alter) {
		if (alter < 18) {
			throw new IllegalArgumentException("Zu Jung um angestellt zu werden.");
		}
		this.alter = alter;
	}

	public String getVorname() {
		return vorname;
	}

	public Geschlecht getGeschlecht() {
		return geschlecht;
	}

	public String getNachname() {
		return nachname;
	}

	public String getVollerName() {
		return String.format("%s %s", vorname, nachname);
	}
}


