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

package de.oliver.person.staff;

import java.util.*;

public class AngestelltenVerwaltung {
	private final Map<Bereich, Set<Angestellter>> angestellte = new HashMap<>();

	public boolean isAngestellt(Angestellter angestellter) {
		for (Set<Angestellter> runSet : angestellte.values()) {
			if (runSet.contains(angestellter)) {
				return true;
			}
		}
		return false;
	}

	public void angestelltenHinzufuegen(Angestellter angestellter, Bereich bereich) {
		if (isAngestellt(angestellter)) {
			throw new VerwaltungsException(String.format("Der Angestellte %s ist bereits angestellt", angestellter.getVollerName()));
		}
		Set<Angestellter> bereichsMitarbeiter;
		if (angestellte.get(bereich) == null) {
			bereichsMitarbeiter = new HashSet<>();
			angestellte.put(bereich, bereichsMitarbeiter);
		} else {
			bereichsMitarbeiter = angestellte.get(bereich);
		}
		bereichsMitarbeiter.add(angestellter);
	}

	public boolean removeAngestellten(Angestellter angestellter) {
		angestellte.values().forEach(it -> it.remove(angestellter));
		return angestellte.values().stream().noneMatch(it -> it.contains(angestellter));
	}

	public Collection<Angestellter> getAngestellte(Bereich bereich) {
		return Collections.unmodifiableSet(angestellte.get(bereich));
	}

}

