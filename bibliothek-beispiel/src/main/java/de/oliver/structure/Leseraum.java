package de.oliver.structure;

import de.oliver.core.Verschmutzbar;
import de.oliver.person.visitor.Besucher;

import java.util.LinkedList;
import java.util.List;

public class Leseraum implements Verschmutzbar {
	// Todo Anzahl Bugs: ||
	private final Besucher[] leser;
	private boolean besetzt;
	private double verschmutzung;
	private int imRaum;

	public Leseraum(int leserSitze) {
		if (leserSitze < 1) {
			throw new IllegalArgumentException("Es muss mindestens eine Person in den Raum passen");
		}
		leser = new Besucher[leserSitze];
	}

	public Besucher[] betreten(Besucher... besucher) {
		// todo Bug fehlender Schutz vor überfüllung
		// todo Bug da es immer überschreibt.
		int i = 0;
		List<Besucher> back = new LinkedList<>();
		for (Besucher b : besucher) {
			leser[i] = b;
			back.add(b);
			i++;
		}
		besetzt = true;
		return back.toArray(new Besucher[1]);
	}

	public List<Besucher> verlassen() {
		besetzt = false;
		List<Besucher> back = new LinkedList<>();
		for (int i = 0; i < leser.length; i++) {
			if (leser[i] == null) {
				break;
			}
			back.add(leser[i]);
			leser[i] = null;
			verschmutzen();
		}
		return back;
	}

	public List<Besucher> getPersonenImRaum() {
		List<Besucher> back = new LinkedList<>();
		for (int i = 0; i < leser.length; i++) {
			if (leser[i] != null) {
				back.add(leser[i]);
			}
		}
		return back;
	}

	public boolean isBesetzt() {
		return besetzt;
	}

	@Override
	public boolean isDreckig() {
		return verschmutzung >= 0.75;
	}

	@Override
	public void saeubern() {
		verschmutzung = 0.0;
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.1;
	}
}
