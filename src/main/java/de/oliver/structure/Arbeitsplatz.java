package de.oliver.structure;

import de.oliver.core.Verschmutzbar;
import de.oliver.person.staff.Angestellter;

public abstract class Arbeitsplatz implements Verschmutzbar {

	protected Angestellter nutzer;
	protected double verschmutzung;

	protected final void checkState() throws IllegalStateException {
		if (!isBesetzt()) {
			throw new IllegalStateException("Der Arbeitsplatz ist nicht besetzt");
		}
	}

	public boolean isBesetzt() {
		return nutzer != null;
	}


	public boolean hinsetzen(Angestellter nutzer) {
		if (this.nutzer == null) {
			this.nutzer = nutzer;
			return true;
		} else {
			throw new IllegalStateException();
		}
	}

	public Angestellter aufstehen() {
		Angestellter back = nutzer;
		if (back == null) {
			throw new IllegalStateException();
		}
		nutzer = null;
		return back;
	}

	public Angestellter getNutzer() {
		return nutzer;
	}

	@Override
	public boolean isDreckig() {
		return verschmutzung > 0.7;
	}

	@Override
	public void saeubern() {
		verschmutzung = 0;
	}
}