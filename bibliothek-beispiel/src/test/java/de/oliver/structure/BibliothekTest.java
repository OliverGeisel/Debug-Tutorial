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

import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Studierender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class BibliothekTest {

	private Bibliothek bibliothek;
	private Besucher person;

	@BeforeEach
	void setUp() {
		bibliothek = new Bibliothek("TestBibo", 2, 2, LocalTime.of(8, 0), LocalTime.of(20, 0));
		person = new Studierender(1, "Nico", "Hermann");
	}

	@Test
	void getName() {
		assertEquals("TestBibo", bibliothek.getName(), "Der Name passt nicht!");
	}

	@Test
	void getAngestellenComputerNachtInit() {
		var computer = bibliothek.getAngestellenComputer();
		assertNotNull(computer, "Die Computer der Angestellten dürfen nicht null sein");
		for (int i = 0; i < computer.size(); i++) {
			assertNotNull(computer.get(i), String.format("Der Computer %d in der Bibliothek ist null. Der Computer muss aber existieren!", i));
		}
	}

	@Test
	void getBesucherComputerNachInit() {
		var computer = bibliothek.getBesucherComputer();
		assertNotNull(computer, "Die Computer der Angestellten dürfen nicht null sein");
		for (int i = 0; i < computer.size(); i++) {
			assertNotNull(computer.get(i), String.format("Der Computer %d in der Bibliothek ist null. Der Computer muss aber existieren!", i));
		}
	}

	@Test
	void getBestandsverwaltungNachInit() {
		assertNotNull(bibliothek.getBestandsverwaltung(), "Die Bestandsverwaltung darf nicht null sein!");
	}

	@Test
	@Tag("CI-IGNORE")
	void getLeseraeumeNachInit() {
		var raeume = bibliothek.getLeseraeume();
		assertEquals(2, raeume.length, "Die Anzahl der Leseräume muss 2 betragen!");
		for (int i = 0; i < raeume.length; i++) {
			assertNotNull(raeume[i], String.format("Der Raum %d in der Bibliothek ist null. Der Raum muss aber existieren!", i));
		}
	}

	@Test
	void getWerkstattNachInit() {
		assertNotNull(bibliothek.getWerkstatt(), "Die Werkstatt darf nicht null sein!");
	}

	@Test
	void getVerwaltungNachInit() {
		assertNotNull(bibliothek.getVerwaltung(), "Die Verwaltung darf nicht null sein!");
	}

	@Test
	void getRegister() {
		assertNotNull(bibliothek.getRegister(), "Die Kundenregister darf nicht null sein!");
	}

	@Test
	void getOefffnungsZeiten() {
		Bibliothek.OeffnungsZeiten zeiten = new Bibliothek.OeffnungsZeiten(LocalTime.of(8, 0), LocalTime.of(20, 0));
		assertEquals(bibliothek.getOefffnungsZeiten(), zeiten, "Öffnungszeiten stimmen nicht den angegeben überein!");
	}

	@Test
	@Tag("CI-IGNORE")
	void betreten1StundeVorOeffnungszeit() {
		LocalTime eintrittsZeit = LocalTime.of(7, 0);
		assertThrows(IllegalStateException.class, () -> bibliothek.betreten(person, eintrittsZeit), "Die Person darf nicht eintreten, wenn die Bibliothek nicht geöffnet ist.");
	}

	@Test
	@Tag("CI-IGNORE")
	void betreten1MinuteVorOeffnungszeit() {
		LocalTime eintrittsZeit = LocalTime.of(7, 59);
		assertThrows(IllegalStateException.class, () -> bibliothek.betreten(person, eintrittsZeit), "Die Person darf nicht eintreten, wenn die Bibliothek nicht geöffnet ist.");
	}

	@Test
	@Tag("CI-IGNORE")
	void betretenZurOeffnungszeit() {
		LocalTime eintrittsZeit = LocalTime.of(8, 0);
		assertDoesNotThrow(() -> bibliothek.betreten(person, eintrittsZeit), "Die Person darf die Bibliothek betreten!");
	}

	@Test
	@Tag("CI-IGNORE")
	void betretenMittenInOeffnungszeit() {
		LocalTime eintrittsZeit = LocalTime.of(16, 0);
		assertDoesNotThrow(() -> bibliothek.betreten(person, eintrittsZeit), "Die Person darf die Bibliothek betreten!");
	}

	@Test
	@Tag("CI-IGNORE")
	void betretenZurSchliesszeit() {
		LocalTime eintrittsZeit = LocalTime.of(20, 0);
		assertDoesNotThrow(() -> bibliothek.betreten(person, eintrittsZeit), "Die Person darf die Bibliothek betreten!");
	}

	@Test
	@Tag("CI-IGNORE")
	void betreten1MinuteNachSchliesszeit() {
		LocalTime eintrittsZeit = LocalTime.of(20, 1);
		assertThrows(IllegalStateException.class, () -> bibliothek.betreten(person, eintrittsZeit), "Die Person darf die Bibliothek nicht mehr betreten!");
	}

	@Test
	@Tag("CI-IGNORE")
	void betreten2StundenNachSchliesszeit() {
		LocalTime eintrittsZeit = LocalTime.of(22, 0);
		assertThrows(IllegalStateException.class, () -> bibliothek.betreten(person, eintrittsZeit), "Die Person darf die Bibliothek nicht mehr betreten!");
	}

	@Test
	@Tag("CI-IGNORE")
	void verlassenOkay() {
		bibliothek.betreten(person, LocalTime.of(12, 0));
		assertTrue(bibliothek.isInBibliothek(person), "Person muss in der Bibliothek sein");
		assertDoesNotThrow(() -> bibliothek.verlassen(person, LocalTime.of(15, 0)), "Die Person muss die Bibliothek verlassen können!");
		assertFalse(bibliothek.isInBibliothek(person), "Person darf nicht in der Bibliothek sein");
	}

	@Test
	@Tag("CI-IGNORE")
	void verlassenFehler() {
		assertFalse(bibliothek.isInBibliothek(person), "Person darf nicht in der Bibliothek sein");
		assertThrows(IllegalArgumentException.class, () -> bibliothek.verlassen(person, LocalTime.of(15, 0)), "Die Person kann nicht in der Bibliothek sein und damit auch nicht verlassen!");
	}

}