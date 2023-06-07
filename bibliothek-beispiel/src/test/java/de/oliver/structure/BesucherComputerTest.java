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
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Studierender;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD) // Bestimmt ob Klasse bei jedem Test neu gemacht wird. seit 5.0
@TestMethodOrder(MethodOrderer.MethodName.class) // Reihenfolge der Tests festlegen seit 5.7
class BesucherComputerTest {

	private BesucherComputer computer;

	@Mock
	private BestandsVerwaltung bestandsVerwaltung;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		computer = new BesucherComputer(bestandsVerwaltung);
	}

	@AfterEach
	void tearDown(TestInfo info) {
		try {
			var counter = BesucherComputer.class.getDeclaredField("counter");
			counter.setAccessible(true);
			if (info.getTestMethod().isPresent()) {
				if (info.getTestMethod().get().isAnnotationPresent(RepeatedTest.class)) {
					var value = counter.get(null);
					if (value instanceof Integer i && i < 11) {
						return;
					}
				}
			}
			counter.set(null, 1);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("Erzeuge erfolgreich neuen BesucherComputer")
		// seit 5.0
	void erzeugeNeuenBesucherComputer() {
		var neuerComputer = new BesucherComputer(bestandsVerwaltung);
		assertNotNull(neuerComputer, "BesucherComputer war null! Es darf kein null-Objekt zurückgegeben werden.");
	}

	@Test
	@DisplayName("Erzeuge keinen neuen BesucherComputer mit null BestandsVerwaltung")
	void erzeugeNeuenBesucherComputerMitNullBestandsVerwaltung() {
		assertThrows(NullPointerException.class, () -> new BesucherComputer(null), "Es wurde kein Fehler geworfen, obwohl die BestandsVerwaltung null war.");
	}

	@RepeatedTest(10) // seit 5.0
	@DisplayName("Erzeuge BesucherComputer mit eindeutiger ID")
	void erzeugeNeuenBesucherComputerMitEindeutigerID(RepetitionInfo info) {
		var correctString = "Das ist Terminal " + (info.getCurrentRepetition()) + ". Und es ist mit niemanden besetzt.";
		assertEquals(correctString, computer.toString(), "Die ID des BesucherComputers ist nicht eindeutig.");
	}

	@ParameterizedTest
	@CsvSource({"978-0-00000-000-0, Das Lachen,Tim Taler", "978-1-12345-123-1,Helden der Hoffnung,Oliver Geisel", "978-2-23456-234-2,Java ist eine Insel,Max Mustermann"})
	@DisplayName("ausleihen() funktioniert")
	void ausleihenErfolgreich(String isbnString, String titel, String nutzer) {
		var isbn = ISBN.fromString(isbnString);
		var besucher = mock(Studierender.class);
		Buch buch = new Buch(titel, isbn);
		when(besucher.toString()).thenReturn(nutzer);
		when(bestandsVerwaltung.sucheNachISBN(isbn)).thenReturn(new Buch(titel, isbn));
		when(bestandsVerwaltung.ausleihen(buch, besucher)).thenReturn(true);
		when(bestandsVerwaltung.sucheNachISBN(isbn)).thenReturn(buch);

		computer.hinsetzen(besucher);
		assertTrue(computer.ausleihen(buch, besucher), "Das Buch konnte nicht ausgeliehen werden.");
	}

	@Test
	@DisplayName("ausleihen() fehlschlag weil nicht besetzt")
	void ausleihenUnbesetzt() {
		var besucher = mock(Studierender.class);
		when(besucher.toString()).thenReturn("Max Mustermann");
		var isbn = ISBN.fromString("978-0-00000-000-0");
		var buch = new Buch("Das Lachen", isbn);
		when(bestandsVerwaltung.sucheNachISBN(isbn)).thenReturn(buch);

		assertFalse(computer.ausleihen(buch), "Das Buch darf nicht ausgeliehen werden, wenn der Computer nicht besetzt ist.");
	}

	@Test
	@DisplayName("ausleihen() erfolgreich bei besetzt")
	void ausleihenBesetzt() {
		var besucher = mock(Studierender.class);
		when(besucher.toString()).thenReturn("Max Mustermann");
		var isbn = ISBN.fromString("978-0-00000-000-0");
		var buch = new Buch("Das Lachen", isbn);
		when(bestandsVerwaltung.ausleihen(buch, besucher)).thenReturn(true);

		computer.hinsetzen(besucher);
		assertTrue(computer.ausleihen(buch), "Das Buch muss ausgeliehen werden, wenn der Computer besetzt ist.");
	}

	@Test
	@DisplayName("sucheNachISBN() findet kein Buch")
	void sucheNachISBNKeinBuch() {
		when(bestandsVerwaltung.sucheNachISBN(ISBN.NullISBN)).thenReturn(null);
		assertNull(computer.sucheNachISBN(ISBN.NullISBN), "Es wird kein Buch erwartet.");
	}

	@Test
	@DisplayName("bezahlen nicht möglich, wenn nicht besetzt ist")
	void bezahlenNichtMoeglichNichtBesetzt() {
		Besucher besucher = mock(Studierender.class);
		assertThrows(UnsupportedOperationException.class, () -> computer.bezahlen(besucher, 1), "Es wurde kein Fehler geworfen, obwohl bezahlen nicht möglich ist.");
	}

	@Test
	@DisplayName("bezahlen nicht möglich, wenn besetzt ist")
	void bezahlenNichtMoeglichBesetzt() {
		Besucher besucher = mock(Studierender.class);
		computer.hinsetzen(besucher);
		assertThrows(UnsupportedOperationException.class, () -> computer.bezahlen(besucher, 1), "Es wurde kein Fehler geworfen, obwohl bezahlen nicht möglich ist.");
	}

	@Test
	@DisplayName("toString() wenn nicht besetzt ist")
	void toStringUnbesetzt() {
		assertNotNull(computer.toString());
		assertEquals("Das ist Terminal 1. Und es ist mit niemanden besetzt.", computer.toString());
	}

	@Test
	@DisplayName("toString() wenn besetzt ist")
	void toStringBesetzt() {
		Besucher besucher = mock(Studierender.class);
		when(besucher.toString()).thenReturn("Tim Taler");
		computer.hinsetzen(besucher);
		assertNotNull(computer.toString());
		assertEquals("Das ist Terminal 1. Und es ist mit Tim Taler besetzt.", computer.toString());
	}
}