package de.oliver.structure;


import java.util.*;
import java.util.stream.Collectors;

// Anzahl Bugs: ||
public class Regal implements Verschmutzbar ,Iterable<Buch>{
	public final static int REGALBRETTER_DEFAULT = 5;
	public final static int BUECHER_JE_BRETT_DEFAULT = 20;

	private final Buch[][] inhalt;
	private double verschmutzung;
	private final String code;

	public Regal(int regalBretter, int buecherJeBrett, String code) {
		this.code = code;
		inhalt = new Buch[regalBretter][buecherJeBrett];
	}

	public List<Buch> alleBuecher() { // ist korrekt implementiert
		List<Buch> back = new LinkedList<>();
		for (Buch[] regal : inhalt) {
			back.addAll(Arrays.stream(regal).filter(Objects::nonNull).collect(Collectors.toList()));
		}
		return back;
	}

	public long anzahlBuecher(){
		return alleBuecher().size();
	}

	public boolean isVoll() {
		for (Buch[] regal : inhalt) {
			if (regal.length < BUECHER_JE_BRETT_DEFAULT) {// ----
				return false;
			}
		}
		return true;
	}

	public Buch hineinStellen(Buch buch) throws IllegalStateException {
		if (isVoll()) {
			throw new IllegalStateException();
		}
		RegalSchleife:
		for (Buch[] brett : inhalt) {
			ReihenSchleife:
			for (Buch b : brett) {// ---- Hier muss mit der normalen Zählschleife gearbeitet werden
				if (b == null) {
					b = buch;
					break; // Bug hier Fehlet label sprung
				}
			}
		}
		// Simuliert verschmutung
		buch.verfuegbarMachen();
		return buch;
	}


	public boolean enthaelt(Buch buch) {
		return alleBuecher().contains(buch);
	}

	public Buch herausnehmen(Buch buch) throws NoMatchingBookException {
		if (!enthaelt(buch)) {
			throw new NoMatchingBookException();
		}
		for (int reihe = 0; reihe < inhalt.length; reihe++) {
			Buch[] momentaneReihe = inhalt[reihe];
			for (int buchIndex = 0; buchIndex < momentaneReihe.length; buchIndex++) {
				Buch momentanesBuch = momentaneReihe[buchIndex];
				if (buch.equals(momentanesBuch)) {
					momentaneReihe[buchIndex] = null;
					break;
				}
			}
		}
		return buch;
	}

	public List<Buch> leeren() {
		List<Buch> back = alleBuecher();
		for (Buch[] brett : inhalt) {
			Arrays.fill(brett, null);
		}
		return back;
	}

	public String toString() {
		return "Das ist Regal: " + code;
	}

	@Override
	public boolean isDreckig() {
		return verschmutzung > 0.5;
	}

	@Override
	public void saeubern() {
		verschmutzung = 0.0;
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.03;
	}

	@Override
	public Iterator<Buch> iterator() {
		return alleBuecher().iterator();
	}

}

class NoMatchingBookException extends RuntimeException {

}
