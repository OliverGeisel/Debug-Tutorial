package de.oliver.person.visitor;

import de.oliver.core.Buch;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class Kundenregister {
	//todo Anzahl Bugs: ||

	private final Map<Integer, BesucherStatus> alleBesucher;

	public Kundenregister() {
		alleBesucher = new HashMap<>();
	}

	public boolean addBesucher(Besucher besucher) {
		var alterEintrag = alleBesucher.put(besucher.getID(), new BesucherStatus(besucher));
		if (alterEintrag != null) {
			alleBesucher.put(besucher.getID(), alterEintrag);
			return false;
		}
		return true;
	}

	/**
	 * Gibt das Buch der Bibliothek zurück.
	 *
	 * @param buch     Buch, das zurückgegeben werden soll.
	 * @param besucher Besucher, der das Buch ausgeliehen hat.
	 * @return true, wenn es keine Probleme gab. Gibt false zurück, wenn Besucher nicht gefunden wird, oder das Buch nicht dem Besucher gehört.
	 */
	public boolean gibBuchZurueck(Buch buch, Besucher besucher) {
		return gibtBuchZurueckIntern(buch, besucher.getID());
	}

	private boolean gibtBuchZurueckIntern(Buch buch, Integer id) {
		var element = alleBesucher.get(id);
		if (element == null) {
			return false;
		}
		boolean back = element.entferneBuchAusAusgelieheneBuecher(buch);
		if (element.getBesucherTyp() != BesucherTyp.Dozent) {
			erhoeheStrafeIntern(id, berechneKosten(buch));
		}
		return back;
	}

	public boolean gibBuchZurueck(Buch buch) {

		Integer besucherID = alleBesucher.entrySet().stream()
				.filter(it -> it.getValue().getAusgelieheneBuecher().contains(buch)).map(it -> it.getKey())
				.findFirst().orElseThrow();
		return gibtBuchZurueckIntern(buch, besucherID);
	}

	public boolean leiheBuchAus(Buch buch, Besucher besucher) {
		return alleBesucher.get(besucher.getID()).registriereAusgeliehenesBuch(buch);
	}

	public Double getStrafe(Besucher besucher) {
		try {
			return alleBesucher.get(besucher.getID()).getStrafe();
		} catch (NullPointerException ne) {
			throw new IllegalArgumentException(ne.getMessage());
		}
	}

	/**
	 * Berechnet die Strafe für ausgeliehene Bücher.
	 *
	 * @param buch Buch, füch das die Strafe berechnet werden soll
	 * @return Strafe, wenn das Buch jetzt zurückgegeben wird.
	 * @throws IllegalStateException, wenn das Buch nicht ausgeliehen ist.
	 */
	private Double berechneKosten(Buch buch) throws IllegalStateException {
		if (!buch.isAusgeliehen()) {
			throw new IllegalStateException("Das Buch ist nicht ausgeliehen");
		}
		LocalDate rueckgabeDatum = buch.getAusleihdatum().plusDays(28);
		if (rueckgabeDatum.isAfter(LocalDate.now())) {
			return 0.0;
		}
		long ueberzogeneTage = Duration.between(rueckgabeDatum.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();

		double kosten = 0.0;
		long ersteTage = Math.min(7, ueberzogeneTage);
		long restTage = ueberzogeneTage - ersteTage;
		// Für die Tage 1-7 wird jeden Tag 1€ berechnet.
		kosten += ersteTage * 1.0;
		long wochen = (restTage+6) / 7; // Todo Bug Lösung
		// Ab dem 8. Tag wird jede Woche 5 € verlangt.
		kosten += 5 * Math.min(5, wochen);
		wochen -= Math.min(5, wochen);
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
	 * @param besucher Kunde, dessen Strafe erhöht wird.
	 * @param betrag   Betrag, um den die Strafe erhöht wird.
	 * @return neuer Betrag der Strafe.
	 */
	Double erhoeheStrafe(Besucher besucher, Double betrag) {
		return erhoeheStrafeIntern(besucher.getID(), betrag);
	}

	private Double erhoeheStrafeIntern(Integer id, Double kosten) {
		var status = alleBesucher.get(id);
		return status.erhoeheStrafe(kosten);
	}

	public List<Buch> getAusgelieheneBuecher(Besucher besucher) {
		return alleBesucher.get(besucher.getID()).getAusgelieheneBuecher();
	}

	/**
	 * Speichert den Staus des Kunden in der Bibliothek.
	 */
	private static final class BesucherStatus {
		private List<Buch> ausgelieheneBuecher =new LinkedList<>(); // Todo Bug Lösung
		private Double strafe = 0.0;
		private final BesucherTyp besucherTyp;

		private BesucherStatus(Besucher besucher) {
			besucherTyp =
					switch (besucher) {
						case Dozent d -> BesucherTyp.Dozent;
						case Studierender s -> BesucherTyp.Studierender;
						default -> BesucherTyp.Normal;
					};
		}

		/**
		 * Bezahlt die Strafe und setzt sie wieder auf 0.
		 */
		void bezahlen() {
			this.strafe = 0.0;
		}

		boolean registriereAusgeliehenesBuch(Buch buch) {
			return ausgelieheneBuecher.add(buch);
		}

		public List<Buch> getAusgelieheneBuecher() {
			return Collections.unmodifiableList(ausgelieheneBuecher);
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

		public boolean entferneBuchAusAusgelieheneBuecher(Buch buch) {
			return ausgelieheneBuecher.remove(buch);
		}

		public BesucherTyp getBesucherTyp() {
			return besucherTyp;
		}
	}


}
