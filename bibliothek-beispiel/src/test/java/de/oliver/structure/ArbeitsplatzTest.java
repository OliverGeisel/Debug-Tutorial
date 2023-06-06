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

import de.oliver.person.Geschlecht;
import de.oliver.person.staff.Angestellter;
import de.oliver.person.staff.Bibliothekar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArbeitsplatzTest {

	private Arbeitsplatz<Angestellter> arbeitsplatz;
	private Angestellter angestellter;

	@BeforeEach
	public void setUp() {
		arbeitsplatz = new Arbeitsplatz<>() { // Anonyme Klasse
			@Override
			public void verschmutzen() {
				verschmutzung += 0.35;
			}
		};
		angestellter = new Bibliothekar("John", "Böhmer", Geschlecht.DIVERS, 23);
	}

	@Test
	void isBesetztFalse() {
		assertFalse(arbeitsplatz.isBesetzt());
	}

	@Test
	void isBesetztTrue() {
		arbeitsplatz.hinsetzen(angestellter);
		assertTrue(arbeitsplatz.isBesetzt());
	}

	@Test
	void hinsetzenOkay() {
		arbeitsplatz.hinsetzen(angestellter);
		assertEquals(arbeitsplatz.getNutzer(), angestellter);
	}

	@Test
	void hinsetzenFehler() {
		assertTrue(arbeitsplatz.hinsetzen(angestellter), "Der Nutzer muss sich am anfang hinsetzen können");
		assertThrowsExactly(IllegalStateException.class, () -> arbeitsplatz.hinsetzen(angestellter), "Der Nutzer kann sich nicht  erneut hinsetzen, wenn er bereits sitzt.");
		assertThrowsExactly(IllegalStateException.class, () -> arbeitsplatz.hinsetzen(new Bibliothekar("Jan", "Thorn", Geschlecht.MAENNLICH, 35)),
				"Der Nutzer kann sich nicht  erneut hinsetzen, wenn er bereits sitzt.");
		assertEquals(arbeitsplatz.getNutzer(), angestellter);
	}

	@Test
	void aufstehenOkay() {
		assertTrue(arbeitsplatz.hinsetzen(angestellter), "Der Nutzer muss sich hinsetzen können!");
		assertTrue(arbeitsplatz.isBesetzt(), "Nach dem hinsetzen muss der Platz besetzt sein!");
		assertEquals(angestellter, arbeitsplatz.aufstehen(), "Beim Aufstehen muss der Nutzer zurück gegeben werden.");
		assertNull(arbeitsplatz.getNutzer(), "Nach dem aufstehen muss getNutzer() null zurück geben.");
	}

	@Test
	void aufstehenFehler() {
		assertFalse(arbeitsplatz.isBesetzt(), "Es darf am Anfang nicht besetzt sein");
		assertThrowsExactly(IllegalStateException.class, () -> arbeitsplatz.aufstehen(), "Wenn kein Nutzer am Platz ist, dann muss aufstehen() eine Exception werfen.");
		assertNull(arbeitsplatz.getNutzer(), "Es muss null geben, wenn der Platz unbesetzt ist");
	}

	@Test
	void getNutzerOkay() {
		arbeitsplatz.hinsetzen(angestellter);
		assertEquals(angestellter, arbeitsplatz.getNutzer(), "Der Nutzer muss zurück gegeben werden!");
	}

	@Test
	void getNutzerNull() {
		assertFalse(arbeitsplatz.isBesetzt(), "Der Arbeitsplatz darf nicht besetzt sein!");
		assertNull(arbeitsplatz.getNutzer(), "Der Nutzer muss null sein, wenn der Platz nicht besetzt ist!");
	}

	@Test
	void isDreckigTrue() {
		assertFalse(arbeitsplatz.isDreckig(), "Am Anfang darf es nicht dreckig sein");
		var t = arbeitsplatz.verschmutzung;
		arbeitsplatz.verschmutzen();
		arbeitsplatz.verschmutzen();
		assertFalse(arbeitsplatz.isDreckig(), "Noch darf der Arbeitsplatz nicht dreckig sein!");
		arbeitsplatz.verschmutzen();
		assertTrue(arbeitsplatz.isDreckig(), "Der Arbeitsplatz muss dreckig sein!");
	}
}