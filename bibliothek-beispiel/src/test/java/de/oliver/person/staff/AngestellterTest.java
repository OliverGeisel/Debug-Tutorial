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

import de.oliver.person.Geschlecht;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class AngestellterTest {
	private Angestellter angestellter;

	@BeforeEach
	void setUp() {
		angestellter = new Angestellter("Oliver", "Geisel", Geschlecht.MAENNLICH, 24) {
			// Realisierte Klasse vom Typ Angestellter
		};
	}

	@Test
	void getAlter() {
		assertEquals(24, angestellter.getAlter(), "Das Alter passt nicht!");
	}

	@Test
	void setAlterOkay() {
		assertEquals(24, angestellter.getAlter(), "Alter wurde falsch initialisiert");
		angestellter.setAlter(25);
		assertEquals(25, angestellter.getAlter(), "Alter wurde nicht geändert!");
	}

	@Test
	void setAlterZuJung() {
		assertEquals(24, angestellter.getAlter(), "Alter wurde falsch initialisiert!");

		assertThrowsExactly(IllegalArgumentException.class, () -> angestellter.setAlter(17));
		assertEquals(24, angestellter.getAlter(), "Das Alter darf nicht ungültig werden!");
	}

	@Test
	@Disabled("Zur Zeit unnötig da nicht mehr package")
	void getAlterZugriffsrechte() {
		boolean result = false;
		try {
			int modifier = Angestellter.class.getDeclaredMethod("getAlter").getModifiers();
			result = Modifier.isPublic(modifier);
			// empty string ist package
			//result = visibility.();

		} catch (NoSuchMethodException nsme) {
			fail("Die Methode \"getAlter()\" existiert nicht");
		}
		assertTrue(result, "Die Methode \"getAlter()\" sollte nur im package sichtbar sein");
	}

	@Test
	void getVornameOkay() {
		assertEquals("Oliver", angestellter.getVorname(), "Der Vorname passt nicht");
	}

	@Test
	void getGeschlechtOkay() {
		assertEquals(Geschlecht.MAENNLICH, angestellter.getGeschlecht(), "Das Geschlecht stimmt nicht!");
	}

	@Test
	@Disabled("Zur Zeit unnötig da nicht mehr package")
	void getGeschlechtZugriffsrechte() {
		boolean result = false;
		try {
			int modifier = Angestellter.class.getDeclaredMethod("getGeschlecht").getModifiers();
			String visibility = Modifier.toString(modifier);
			// empty string ist package
			result = visibility.isEmpty();

		} catch (NoSuchMethodException nsme) {
			fail("Die Methode \"getGeschlecht()\" existiert nicht");
		}
		assertTrue(result, "Die Methode \"getGeschlecht()\" sollte nur im package sichtbar sein");
	}

	@Test
	void getNachnameOkay() {
		assertEquals("Geisel", angestellter.getNachname(), "Nachname passt nicht!");
	}

	@Test
	void getVollerNameOkay() {
		assertEquals(angestellter.getVorname() + " " + angestellter.getNachname(), angestellter.getVollerName(), "Der volle Name passt nicht. Erst der Vorname dann der Nachname!");
	}
}