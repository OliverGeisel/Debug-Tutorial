/*
 * Copyright 2023 Oliver Geisel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ZuSchmutzigException;
import de.oliver.person.staff.Restaurator;

import java.util.LinkedList;
import java.util.List;

public class Werkstatt extends Arbeitsplatz<Restaurator> {
	private final List<Buch> beschaedigteBuecher;
	private final List<Buch> reparierteBuecher = new LinkedList<>();
	private final BestandsVerwaltung bestand;

	public Werkstatt(BestandsVerwaltung bestand) {
		this.bestand = bestand;
		beschaedigteBuecher = new LinkedList<>();

	}

	public boolean zurReparaturHinzufuegen(Buch buch) {
		if (beschaedigteBuecher.contains(buch)) {
			return false;
		}
		return beschaedigteBuecher.add(buch);
	}

	public Buch reparieren() throws IllegalStateException, ZuSchmutzigException {
		if (nutzer == null) {
			throw new IllegalStateException("Es fehlt ein Mitarbeiter an der Werkstatt!");
		}
		if (isDreckig()) {
			throw new ZuSchmutzigException("Die Werkstatt ist zu schmutzig um Bücher zu reparieren!");
		}
		Buch buch = beschaedigteBuecher.get(0);
		buch.reparieren();
		verschmutzen();
		reparierteBuecher.add(buch);
		return buch;
	}

	public Buch[] alleRepariertenZurueckstellen() {
		var back = reparierteBuecher.toArray(new Buch[1]);
		reparierteBuecher.forEach(this::zurueckstellen);
		return back;
	}

	private boolean zurueckstellen(Buch b) {
		reparierteBuecher.remove(b);
		bestand.inEinRegalStellen(b);
		return true;
	}

	public boolean isRepariert(Buch buch) {
		return (!beschaedigteBuecher.contains(buch) && buch.getBeschaedigung() < 0.05)
				|| reparierteBuecher.contains(buch);
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.2;
	}
}


