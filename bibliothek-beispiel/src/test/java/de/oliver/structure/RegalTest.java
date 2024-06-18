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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegalTest {

	private Regal regal;
	private final Buch buch1 = new Buch("Buch 1", new ISBN(978, 3, 20, 123, 2));

	private void mitgleichemBuchfuellen(Regal regal) {
		while (!regal.isVoll()) {
			regal.hineinStellen(buch1);
		}
	}

	@BeforeEach
	void setUp() {
		regal = new Regal(Regal.REGALBRETTER_DEFAULT, Regal.BUECHER_JE_BRETT_DEFAULT, "R1");
	}

	@Test
	void isVollTrue() {
		mitgleichemBuchfuellen(regal);
		assertTrue(regal.isVoll(), "Das Regal muss bei vollem Bestand als voll markiert sein!");

	}

	@Test
	@Tag("CI-IGNORE")
	void isVollFalseBeimErzeugen() {
		assertFalse(regal.isVoll(), "Das Regal muss beim erstellen leer sein!");
	}

	@Test
	@Tag("CI-IGNORE")
	void isVollFalseBeimEntnehmenEinesBuches() {
		assertFalse(regal.isVoll(), "Das Regal muss beim erstellen leer sein!");
		mitgleichemBuchfuellen(regal);
		assertTrue(regal.isVoll(), "Buch ist nicht voll");
		regal.herausnehmen(buch1);
		assertFalse(regal.isVoll(), "Das Regal darf nicht als voll gelten, wenn ein Buch entnommen wurde");
	}

	@Test
	void nullBuecherAmAnfang() {
		assertEquals(0, regal.anzahlBuecherImRegal(), "Nach dem erzeugen darf kein Buch im Regal sein");
	}

	@Test
	@Tag("CI-IGNORE")
	void alleBuecherOkay() {
		regal.hineinStellen(buch1);
		regal.hineinStellen(buch1);
		assertEquals(List.of(buch1, buch1), regal.alleBuecher(), "Das");
	}

	@Test
	@Tag("CI-IGNORE")
	void leereVollesRegal() {
		mitgleichemBuchfuellen(regal);
		assertTrue(regal.isVoll(), "Das Regal muss voll sein");
		regal.leeren();
		assertFalse(regal.isVoll(), "Das Regal darf nicht als voll gekennzeichnet sein");
		assertEquals(0, regal.alleBuecher().size(), "Das Regal darf keine Bücher mehr enthalten");
	}

	@Test
	void enthaeltNullTest() {
		assertFalse(regal.enthaelt(null), "Null darf nicht im Regal gefunden werden");
	}

	@Test
	@Tag("CI-IGNORE")
	void enthaeltBuchOkay() {
		regal.hineinStellen(buch1);
		assertTrue(regal.enthaelt(buch1));
	}

	@Test
	void toStringTest() {
		assertEquals("Das ist Regal: R1", regal.toString(), "Der ");
	}
}