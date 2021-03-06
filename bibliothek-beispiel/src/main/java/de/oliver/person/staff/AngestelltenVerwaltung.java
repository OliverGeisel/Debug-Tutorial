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

