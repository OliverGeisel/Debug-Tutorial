package de.oliver.structure;

import de.oliver.person.visitor.Besucher;

public class Leseraum implements Verschmutzbar {
	private final Besucher[] leser;
	private boolean besetzt;
	private double verschmutzung;

	public Leseraum(int lesersitze) {
		leser = new Besucher[lesersitze];
	}

	public void betreten(Besucher... besucher) {
		int i = 0;
		for (Besucher b : besucher) {
			leser[i] = b;
			i++;
		}
		besetzt = true;
	}

	public void verlassen() {
		besetzt = false;
		for (int i = 0; i < leser.length; i++) {
			if (leser[i] == null) {
				break;
			}
			leser[i] = null;
			verschmutzen();
		}
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
