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
import de.oliver.core.ISBN;
import de.oliver.person.Geschlecht;
import de.oliver.person.staff.Bibliothekar;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Dozent;
import de.oliver.person.visitor.Kundenregister;
import de.oliver.person.visitor.Studierender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AngestelltenComputerTest {

	@Mock
	private Kundenregister kundenregister;
	@Mock
	private BestandsVerwaltung bestandsVerwaltung;
	@Mock
	private Leseraum leseraum;

	private AngestelltenComputer computer;
	private Bibliothekar bibliothekar;

	@BeforeEach
	void setUpChild() {

		MockitoAnnotations.openMocks(this);

		// Stub kundenregister
		when(kundenregister.getStrafe(any(Studierender.class))).thenReturn(5.0);
		when(kundenregister.getStrafe(any(Dozent.class))).thenReturn(0.0);
		// Stub bestandsVerwaltung
		when(bestandsVerwaltung.ausleihen(any(Buch.class), any(Besucher.class))).thenReturn(true);
		when(bestandsVerwaltung.zurueckgeben(any(Buch.class))).thenReturn(true);
		// Stub leseraum

		Leseraum[] raeume = {leseraum};
		computer = new AngestelltenComputer(kundenregister, bestandsVerwaltung, raeume);
		bibliothekar = new Bibliothekar("Tim", "Hummels", Geschlecht.MAENNLICH, 45);
	}

	@Test
	void isBesetztTrue() {
		hinsetzenOkay();
		assertTrue(computer.isBesetzt(), "Nach dem hinsetzen muss der Platz besetzt sein!");
	}

	@Test
	void isBesetztFalse() {
		assertFalse(computer.isBesetzt(), "Der Platz darf am Anfang nicht besetzt sein!");
	}

	@Test
	void hinsetzenOkay() {
		assertTrue(computer.hinsetzen(bibliothekar), "Das hinsetzen muss möglich sein");
	}

	@Test
	void zurueckgebenTest() {
		Buch ausgeliehenesBuch = new Buch("Testbuch", ISBN.NullISBN);
		boolean result = computer.ausleihen(ausgeliehenesBuch, new Studierender(1, "Test", "Besucher"));
		ausgeliehenesBuch.ausleihen();
		assertTrue(result, "Das Buch muss als ausgeliehen werden!");
		assertTrue(ausgeliehenesBuch.isAusgeliehen(), "Das Buch muss ausgeliehen sein!");

		assertTrue(computer.zurueckgeben(ausgeliehenesBuch), "Das Buch muss zurückgegeben werden!");
	}

	@Test
	void getMahngebuehrenStudierenderOkay() {
		assertEquals(5.0, computer.getMahngebuehren(new Studierender(1, "Test", "Studierender")), "Die Gebühren müssen die der des Kundenregisters sein!");
	}

	@Test
	void getMahngebuehrenDozentOkay() {
		assertEquals(0.0, computer.getMahngebuehren(new Dozent(2, "Test", "Dozent")), "Die Gebühren müssen die der des Kundenregisters und 0 sein!");
	}

	/*
	@Test
	void aufstehen() {
	}

	@Test
	void getNutzer() {
	}

	@Test
	void isDreckig() {
	}

	@Test
	void saeubern() {
	}

	@Test
	void sucheNachISBN() {
	}

	@Test
	void sucheNachAuthor() {
	}

	@Test
	void sucheNachTitel() {
	}

	@Test
	void sucheNachTreffer() {
	}

	@Test
	void ausleihen() {
	}



	@Test
	void findeRegal() {
	}



	@Test
	void reservieren() {
	}

	@Test
	void bezahlen() {
	}

	@Test
	void besucherHinzufuegen() {
	}

	@Test
	void verschmutzen() {
	}*/
}