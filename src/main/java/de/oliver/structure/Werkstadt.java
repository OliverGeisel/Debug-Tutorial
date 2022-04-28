package de.oliver.structure;

import de.oliver.person.staff.Arbeitsplatz;

import java.util.LinkedList;
import java.util.List;

// Anzahl Bugs: |
public class Werkstadt extends Arbeitsplatz {
	private List<Buch> beschaedigteBuecher;
	private final Bibliothek bibo;


	public Werkstadt(Bibliothek bibo) {
		this.bibo = bibo;
		beschaedigteBuecher = new LinkedList<>();

	}

	public Buch zurReparaturHinzufuegen(Buch buch) {
		beschaedigteBuecher.add(buch);
		return buch;
	}

	public Buch reparieren() {
		if (nutzer == null) {
			throw new IllegalStateException();
		}
		Buch buch = beschaedigteBuecher.get(0); // --- fehlende Kontrolle
		buch.reparieren();
		return buch;
	}

	private boolean zurueckstellen(Buch b) {
		beschaedigteBuecher.remove(b);
		bibo.insRegalStellen(b);
		return true;
	}
}


