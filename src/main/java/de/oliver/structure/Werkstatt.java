package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ZuSchmutzigException;
import de.oliver.person.staff.Angestellter;

import java.util.LinkedList;
import java.util.List;

// Anzahl Bugs: |
public class Werkstatt extends Arbeitsplatz<Angestellter> {
	private final List<Buch> beschaedigteBuecher;
	private final BestandsVerwaltung bestand;


	public Werkstatt(BestandsVerwaltung bestand) {
		this.bestand = bestand;
		beschaedigteBuecher = new LinkedList<>();

	}

	public Buch zurReparaturHinzufuegen(Buch buch) {
		beschaedigteBuecher.add(buch);
		return buch;
	}

	public Buch reparieren() {
		if (nutzer == null) {
			throw new IllegalStateException("Es fehlt ein Mitarbeiter an der Werkstatt!");
		}
		if(isDreckig()){
			throw new ZuSchmutzigException("Die Werkstatt ist zu schmutzig um Bücher zu reparieren!");
		}
		Buch buch = beschaedigteBuecher.get(0); // --- fehlende Kontrolle
		buch.reparieren();
		verschmutzen();
		return buch;
	}

	private boolean zurueckstellen(Buch b) {
		beschaedigteBuecher.remove(b);
		bestand.insRegalStellen(b);
		return true;
	}

	@Override
	public void verschmutzen() {
		verschmutzung+=0.2;
	}
}


