package de.oliver.person.visitor;

import de.oliver.core.Buch;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kundenregister {
	// Todo Anzahl Bugs: |

	private final Map<Integer, BesucherStatus> alleBesucher;

	public Kundenregister() {
		alleBesucher = new HashMap<>();
	}

	public boolean addBesucher(Besucher besucher) {
		var alterEintrag = alleBesucher.put(besucher.getID(), new BesucherStatus());
		if (alterEintrag != null) {
			alleBesucher.put(besucher.getID(), alterEintrag);
			return false;
		}
		return true;
	}

	public boolean gibBuchZurueck(Buch buch, Besucher besucher) {
		var element = alleBesucher.get(besucher);
		if (element == null) {
			return false;
		}
		boolean back = alleBesucher.get(besucher).entferneBuchAusAusgeliehen(buch);
		if (!(besucher instanceof Dozent)) {
			erhoeheStrafe(besucher, berechneKosten(buch));
		}
		return back;
	}

	public Double getStrafe(Besucher besucher) {
		try {
			return alleBesucher.get(besucher.getID()).getStrafe();
		} catch (NullPointerException ne) {
			throw new IllegalArgumentException(ne.getMessage());
		}
	}

	private Double berechneKosten(Buch buch) {
		LocalDate rueckgabeDatum = buch.getAusleihdatum().plusDays(28);
		if (!rueckgabeDatum.isAfter(LocalDate.now())) {
			return 0.0;
		}
		long ueberzogeneTage = Duration.between(rueckgabeDatum.atStartOfDay(), LocalDate.now()).toDays();

		double kosten = 0.0;
		long ersteTage = Math.min(7, ueberzogeneTage);
		long restage = ueberzogeneTage - ersteTage;
		kosten += ersteTage * 1.0;
		// Für die Tage 1-7 wird jeden Tag 1€ berechnet.
		long wochen = (restage) / 7; // Fehler - Es fehlt +6 um immer ab wochenbeginn zu zählen
		// Ab dem 8. Tag wird jede Woche 5 € verlangt.
		kosten += 5 * Math.min(6, wochen);
		wochen -= Math.min(6, wochen);
		// Ab 43 Tagen wird 2€ die Woche verlangt.
		kosten += wochen * 2;
		// Die Strafe wird nie mehr als 100€ betragen
		return Math.min(100.0, kosten);
	}

	public boolean bezahlen(Besucher besucher) {
		alleBesucher.get(besucher.getID()).bezahlen();
		return true;
	}

	/**
	 * Erhöht die Strafe um den angegebenen Wert.
	 *
	 * @param kunde  Kunde, dessen Strafe erhöht wird.
	 * @param kosten Betrag, um den die Strafe erhöht wird.
	 * @return neuer Betrag der Strafe.
	 */
	public Double erhoeheStrafe(Besucher kunde, Double kosten) {
		var status = alleBesucher.get(kunde.getID());
		return status.erhoeheStrafe(kosten);
	}

	private class BesucherStatus {
		private List<Buch> ausgelieheneBuecher; // Todo Nicht intiialisiert
		private Double strafe = 0.0;

		private BesucherStatus() {
		}

		/**
		 * Bezahlt die Strafe und setzt sie wieder auf 0.
		 */
		void bezahlen() {
			this.strafe = 0.0;
		}

		public List<Buch> getAusgelieheneBuecher() {
			return ausgelieheneBuecher;
		}

		/**
		 * Erhöht die Strafe um den angegebenen Wert.
		 *
		 * @param kosten Betrag, um den die Strafe erhöht wird.
		 * @return neuer Betrag der Strafe.
		 */
		public Double erhoeheStrafe(Double kosten) {
			this.strafe += kosten;
			return strafe;
		}

		public Double getStrafe() {
			return strafe;
		}

		public boolean entferneBuchAusAusgeliehen(Buch buch) {
			return ausgelieheneBuecher.remove(buch);
		}
	}


}
