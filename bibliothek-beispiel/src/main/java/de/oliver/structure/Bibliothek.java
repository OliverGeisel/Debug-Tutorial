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

import de.oliver.person.Person;
import de.oliver.person.staff.AngestelltenVerwaltung;
import de.oliver.person.visitor.Kundenregister;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class Bibliothek {

	private final String name;

	private final BestandsVerwaltung bestandsverwaltung;
	private final List<Person> personenInBib;
	private final AngestelltenVerwaltung verwaltung;
	private final Kundenregister register;
	private final Leseraum[] raeume;
	private final Werkstatt werkstatt;
	private final List<BesucherComputer> besucherComputer;
	private final List<AngestelltenComputer> angestelltenComputer;
	private LocalTime oeffnung, schliessung;

	public Bibliothek(String name, int anzahlRaeume, int anzahlRegale, LocalTime oeffnung, LocalTime schliessung) {
		this.name = name;
		raeume = new Leseraum[anzahlRaeume];
		this.oeffnung = oeffnung;
		this.schliessung = schliessung;
		for (Leseraum raum : raeume) {
			raum = new Leseraum(2);
		}
		verwaltung = new AngestelltenVerwaltung();
		register = new Kundenregister();
		Set<Regal> regale = new HashSet<>();
		int i = 1;
		while (i < anzahlRegale) {
			regale.add(new Regal(Regal.REGALBRETTER_DEFAULT, Regal.BUECHER_JE_BRETT_DEFAULT, Integer.toString(i)));
			i++;
		}
		bestandsverwaltung = new BestandsVerwaltung(register, regale);
		werkstatt = bestandsverwaltung.getWerkstatt();
		personenInBib = new LinkedList<>();

		angestelltenComputer = new ArrayList<>();
		for (i = 0; i < 2; i++) {
			angestelltenComputer.add(new AngestelltenComputer(register, this.bestandsverwaltung, raeume));
		}
		besucherComputer = new ArrayList<>();
		for (i = 0; i < 3; i++) {
			besucherComputer.add(new BesucherComputer(this.bestandsverwaltung));
		}
	}

	public Person[] getPersonenInBib() {
		return personenInBib.toArray(new Person[1]);
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

	public void betreten(Person person, LocalTime zeit) throws IllegalStateException {
		if (zeit == null)
			throw new IllegalArgumentException();
		if (person == null)
			return;
		if (zeit.isBefore(oeffnung) || zeit.isAfter(schliessung))
			System.out.printf("%s betritt um %s die %s.%n", person.getVollerName(), zeit, getName());
		else
			throw new IllegalStateException("Die Bibliothek ist noch nicht geöffnet");
		personenInBib.add(person);
	}

	public void verlassen(Person person, LocalTime zeit) throws IllegalArgumentException {
		if (!personenInBib.contains(person)) {
			throw new IllegalAccessError("Die Person ist nicht in der Bibliothek!");
		}
		System.out.printf("%s verlässt um %s die %s.%n", person.getVollerName(), zeit, getName());
		personenInBib.remove(person);
	}

	public void schliessen() {
		for (Person p : personenInBib) {
			personenInBib.remove(p);
			System.out.printf("%s verlässt um %s die %s.%n", p.getVollerName(), LocalTime.now(), getName());
		}
	}

	public boolean isInBibliothek(Person person) {
		return personenInBib.contains(person);
	}

	OeffnungsZeiten getOefffnungsZeiten() {
		return new OeffnungsZeiten(oeffnung, schliessung);
	}

	static class OeffnungsZeiten {

		private final LocalTime start;
		private final LocalTime ende;

		public OeffnungsZeiten(LocalTime start, LocalTime ende) {
			this.start = start;
			this.ende = ende;
		}

		public boolean isInOeffnungszeit(LocalTime zeit) {
			return getEnde().isBefore(zeit) && getStart().isAfter(zeit);
		}

		public LocalTime getEnde() {
			return ende;
		}

		public LocalTime getStart() {
			return start;
		}

		public Duration getOeffnungsDauer() {
			return Duration.between(start, ende);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof OeffnungsZeiten)) return false;

			OeffnungsZeiten that = (OeffnungsZeiten) o;

			if (!start.equals(that.start)) return false;
			return ende.equals(that.ende);
		}

		@Override
		public int hashCode() {
			int result = start.hashCode();
			result = 31 * result + ende.hashCode();
			return result;
		}
	}
}
