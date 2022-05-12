package de.oliver.structure;

import de.oliver.core.Verschmutzbar;
import de.oliver.person.Person;
import de.oliver.person.staff.Angestellter;

public abstract class Arbeitsplatz<T extends Person> implements Verschmutzbar {

	protected T nutzer;
	protected double verschmutzung;

	protected final void checkState() throws IllegalStateException {
		if (!isBesetzt()) {
			throw new IllegalStateException("Der Arbeitsplatz ist nicht besetzt");
		}
	}

	public boolean isBesetzt() {
		return nutzer != null;
	}


	public boolean hinsetzen(T nutzer) {
		if (this.nutzer == null) {
			this.nutzer = nutzer;
			return true;
		} else {
			throw new IllegalStateException();
		}
	}

	public T aufstehen() {
		T back = nutzer;
		if (back == null) {
			throw new IllegalStateException();
		}
		nutzer = null;
		return back;
	}

	public T getNutzer() {
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
