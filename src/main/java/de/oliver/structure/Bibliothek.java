package de.oliver.structure;

import de.oliver.person.staff.AngestelltenVerwaltung;
import de.oliver.person.visitor.Kundenregister;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

// Anzahl Bugs: ||
public class Bibliothek {

	private final String name;

	private final BestandsVerwaltung bestandsverwaltung;

	private final AngestelltenVerwaltung verwaltung;
	private final Kundenregister register;

	private final Leseraum[] raeume;

	private final Werkstatt werkstatt;
	private final List<BesucherComputer> besucherComputer;
	private final List<AngestelltenComputer> angestelltenComputer;
	private final Set<Regal> regale;
	private LocalTime oeffnung, schliessung;
	public Bibliothek(String name, int anzahlRaeume, int anzahlRegale, LocalTime oeffnung, LocalTime schliessung) {
		this.name = name;
		raeume = new Leseraum[anzahlRaeume];
		this.oeffnung = oeffnung;
		this.schliessung = schliessung;
		for (Leseraum raum : raeume) { // ---- innere hat keine Wirkung
			raum = new Leseraum(2);
		}
		verwaltung = new AngestelltenVerwaltung();
		register = new Kundenregister();
		bestandsverwaltung = new BestandsVerwaltung(register);
		regale = new HashSet<>();
		int i = 1;
		while (i < anzahlRegale) {
			regale.add(new Regal(Regal.REGALBRETTER_DEFAULT, Regal.BUECHER_JE_BRETT_DEFAULT, Integer.toString(i)));
			i++;
		}
		werkstatt = new Werkstatt(bestandsverwaltung);
		angestelltenComputer = new ArrayList<>();
		for (i = 0; i < 2; i++) {
			angestelltenComputer.add(new AngestelltenComputer(register, this.bestandsverwaltung, raeume));
		}
		besucherComputer = new ArrayList<>();
		for (i = 0; i < 3; i++) {
			besucherComputer.add(new BesucherComputer(this.bestandsverwaltung));
		}
	}


	public List<AngestelltenComputer> getAngestellenComputer() {
		return angestelltenComputer;
	}

	public String getName() {
		return name;
	}

	public List<BesucherComputer> getBesucherComputer() {
		return besucherComputer;
	}

	public BestandsVerwaltung getBestandsverwaltung() {
		return bestandsverwaltung;
	}

	public Leseraum[] getLeseraeume() {
		return raeume;
	}

	public Werkstatt getWerkstatt() {
		return werkstatt;
	}

	public AngestelltenVerwaltung getVerwaltung() {
		return verwaltung;
	}

	public Kundenregister getRegister() {
		return register;
	}

	OeffnungsZeiten getOefffnungsZeiten() {
		return new OeffnungsZeiten(oeffnung,schliessung);
	}
	static class OeffnungsZeiten {

		private final LocalTime start;
		private final LocalTime ende;

		public OeffnungsZeiten(LocalTime start, LocalTime ende) {
			this.start = start;
			this.ende = ende;
		}

		public LocalTime getEnde() {
			return ende;
		}

		public LocalTime getStart() {
			return start;
		}

		public Duration getOeffnungsDauer(){
			return Duration.between(start,ende);
		}

	}
}
