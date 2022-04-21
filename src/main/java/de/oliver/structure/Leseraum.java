package de.oliver.structure;

import de.oliver.person.visitor.Besucher;

import java.util.LinkedList;
import java.util.List;

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

	public List<Besucher> verlassen() {
		besetzt = false;
		List<Besucher> back = new LinkedList<>();
		for (int i = 0; i < leser.length; i++) {
			if (leser[i] == null){
				break;
			}
			back.add(leser[i]);
			leser[i] = null;
			verschmutzen();
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
