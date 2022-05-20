package de.oliver.person.staff;

import de.oliver.core.Buch;
import de.oliver.core.Verschmutzbar;
import de.oliver.person.Geschlecht;
import de.oliver.structure.Regal;

public class Reinigungskraft extends Angestellter {


	public Reinigungskraft(String vorname, String nachname, Geschlecht geschlecht, int alter) {
		super(vorname, nachname, geschlecht, alter);
	}

	public boolean saeubern(Verschmutzbar gegenstand) {
		if (gegenstand instanceof Regal regal) {
			for (Buch buch : regal.alleBuecher()) {
				buch.saeubern();
			}
		}
		gegenstand.saeubern();
		return true;
	}


}
