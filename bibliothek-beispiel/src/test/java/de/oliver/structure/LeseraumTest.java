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

import de.oliver.person.visitor.Dozent;
import de.oliver.person.visitor.Studierender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class LeseraumTest {

	private Leseraum raum;

	@BeforeEach
	void setUp() {
		raum = new Leseraum(2);
	}

	@Test
	void ueberfuelleLeseraum() {
		assertFalse(raum.isBesetzt(), "Raum muss am Anfang leer sein");
		assertThrows(IndexOutOfBoundsException.class, () -> raum.betreten(new Dozent(2, "Dozent", "Test"), new Studierender(3, "Studierender", "Test1"), new Studierender(1, "Studierender", "Test2")), "Wenn mehr Personen den Raum betreten, als hinein passen dürfen nur so viel hinein, wie noch hineinpassen!");
	}

	@Test
	void KonstruktorOkay() {
		assertDoesNotThrow(() -> new Leseraum(1), "Es darf ein Raum für nur eine Person geben!");
	}

	@Test
	void Konstruktor0Fehler() {
		assertThrows(IllegalArgumentException.class, () -> new Leseraum(0), "Es darf keinen Raum für keine Person geben!");
	}

	@Test
	void KonstruktorNagativeAnzhalFehler() {
		assertThrows(IllegalArgumentException.class, () -> new Leseraum(-1), "Es darf kein Raum für eine negative Anzahl an Personen geben!");
	}

	@Test
	void betretenLeerenRaum() {
		assertFalse(raum.isBesetzt(), "Raum muss am Anfang leer sein");
		assertDoesNotThrow(() -> raum.betreten(new Dozent(1, "Dozent", "Test")), "Es darf nichts passieren, wenn eine Person einen leeren Raum betrit");
	}

	@Test
	@Tag("CI-IGNORE")
	void zweitePersonBetrittRaum() {
		assertFalse(raum.isBesetzt(), "Raum muss am Anfang leer sein");
		assertDoesNotThrow(() -> raum.betreten(new Dozent(1, "Dozent", "Test")), "Es darf nichts passieren, wenn eine Person einen leeren Raum betrit");
		assertDoesNotThrow(() -> raum.betreten(new Studierender(2, "Studierender", "Test")), "Eine zweite Person kann den Raum betreten!");
		assertEquals(2, raum.getPersonenImRaum().size());
	}

	@Test
	@Tag("CI-IGNORE")
	void verlassen() {
		assertFalse(raum.isBesetzt(), "Raum muss am Anfang leer sein");
		assertDoesNotThrow(() -> raum.betreten(new Dozent(1, "Dozent", "Test")), "Es darf nichts passieren, wenn eine Person einen leeren Raum betrit");
		assertDoesNotThrow(() -> raum.betreten(new Studierender(2, "Studierender", "Test")), "Eine zweite Person kann den Raum betreten!");
		assertEquals(2, raum.getPersonenImRaum().size());
		var personen = raum.verlassen();
		assertFalse(raum.isBesetzt(), "Der Raum darf nicht mehr besetzt sein, nachdem die Personen raus sind");
		assertEquals(2, personen.size(), "Es müssen zwei personen sein");
		assertEquals(2, personen.stream().distinct().count(), "Die Personen dürfen nicht gleich sein");
	}

	@Test
	void isBesetztLeer() {
		assertFalse(raum.isBesetzt(), "Darf nicht am Anfang besetzt sein!");
	}

	@Test
	void isDreckigAmAnfang() {
		assertFalse(raum.isDreckig(), "Der Raum muss sauber sein am Anfang!");
	}

	@Test
	void saeubernTest() {
		assertTimeout(Duration.of(1, ChronoUnit.SECONDS), () -> {
			while (!raum.isDreckig()) {
				raum.verschmutzen();
			}
		}, "Der Raum muss dreckig werden!");
		raum.saeubern();
		assertFalse(raum.isDreckig(), "Der Raum muss nach der Reinigung sauber sein!");
	}

	@Test
	void verschmutzenTest() {
		assertTimeout(Duration.of(1, ChronoUnit.SECONDS), () -> {
			while (!raum.isDreckig()) {
				raum.verschmutzen();
			}
		}, "Der Raum muss dreckig werden!");
	}
}