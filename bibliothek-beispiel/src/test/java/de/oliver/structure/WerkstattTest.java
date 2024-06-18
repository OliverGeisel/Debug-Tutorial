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
import de.oliver.core.ZuSchmutzigException;
import de.oliver.person.Geschlecht;
import de.oliver.person.staff.Restaurator;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WerkstattTest {

	private Werkstatt werkstatt;
	private Restaurator restaurator;

	@Mock
	private BestandsVerwaltung bestand;
	private Regal regal = new Regal(1, 1, "Test");

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(bestand.inEinRegalStellen(any())).thenReturn(regal);

		werkstatt = new Werkstatt(bestand);
		restaurator = new Restaurator("John", "Auerbach", Geschlecht.DIVERS, 45);
		werkstatt.hinsetzen(restaurator);
	}

	@AfterEach
	void tearDown() {
		regal.leeren();
	}

	@Test
	void zurReparaturHinzufuegenTest() {
		Buch kaputtesBuch = new Buch("Testen für Anfänger", ISBN.NullISBN);
		kaputtesBuch.starkBeschaedigen();
		assertTrue(werkstatt.zurReparaturHinzufuegen(kaputtesBuch), "Das Buch muss aufgenommen werden!");
		assertEquals(werkstatt.reparieren(), kaputtesBuch);
	}

	@Test
	void zuReparaturHinzufuegenFehlschlagDoppelt() {
		Buch buch = new Buch("Testbuch", ISBN.NullISBN);
		assertTrue(werkstatt.zurReparaturHinzufuegen(buch), "Das Buch muss aufgenommen werden!");
		assertFalse(werkstatt.zurReparaturHinzufuegen(buch), "Das selbe Buch darf nicht mehrmals in der Werkstatt sein");
	}

	@Test
	@DisplayName("Reparieren schlägt fehl, weil der Arbeitsplatz zu  dreckig ist")
	void reparierenFehlschlagDreckig() {
		werkstatt.verschmutzen();
		werkstatt.verschmutzen();
		werkstatt.verschmutzen();
		werkstatt.verschmutzen();
		assertTrue(werkstatt.isDreckig(), "Werkstatt muss dreckig sein");
		assertThrows(ZuSchmutzigException.class, () -> werkstatt.reparieren(), "Die Methode muss abbrechen, da die Werkstatt zu dreckig ist um Bücher zu reparieren");

	}

	@Test
	void reparierenFehlschlagKeinAngestellter() {
		Buch kaputtesBuch = new Buch("Testen für Anfänger", ISBN.NullISBN);
		kaputtesBuch.starkBeschaedigen();
		werkstatt.zurReparaturHinzufuegen(kaputtesBuch);
		werkstatt.aufstehen();
		assertFalse(werkstatt.isBesetzt(), "Es darf kein Angestellter die Werkstatt besetzen!");
		assertThrows(IllegalStateException.class, () -> werkstatt.reparieren());
	}

	@Test
	void reparierenFehlschlagKeinBuch() {
		assertThrows(IndexOutOfBoundsException.class, () -> werkstatt.reparieren(), "Es muss eine Exception geben, wenn kein Buch zum reparieren da ist");
	}

	@Test
	void reparierenFehlschlagNichtBesetzt() {
		assertNotNull(werkstatt.aufstehen(), "Der Nutzer muss aufstehen können.");
		assertThrows(IllegalStateException.class, () -> werkstatt.reparieren(), "Es muss eine Exception geben, wenn kein Nutzer an der Werkstatt ist.");
	}

	@Test
	void reparierenOkay() {
		Buch buch = new Buch("Testbuch", ISBN.NullISBN);
		buch.starkBeschaedigen();
		assertTrue(buch.getBeschaedigung() > 0.5);
		double beschaedigung = buch.getBeschaedigung();
		werkstatt.zurReparaturHinzufuegen(buch);
		Buch repariertesBuch = werkstatt.reparieren();
		assertTrue(werkstatt.isRepariert(buch), "Das Buch muss repariert sein");
		assertEquals(buch, repariertesBuch, "Das reparierte Buch muss gleich dem übergebenen Buch sein!");
		assertTrue(beschaedigung > repariertesBuch.getBeschaedigung(), "Die Beschädigung des Buches muss kleiner sein als zuvor!");
	}

	@Test
	@Tag("CI-IGNORE")
	void reparierenFehlschlagEndlos() {
		Buch buch = new Buch("Testbuch", ISBN.NullISBN);
		buch.starkBeschaedigen();
		assertTrue(buch.getBeschaedigung() > 0.5);
		werkstatt.zurReparaturHinzufuegen(buch);
		werkstatt.reparieren();
		assertThrows(IndexOutOfBoundsException.class, () -> werkstatt.reparieren(), "Nach der Reparatur, darf das Buch nicht noch mal repariert werden!");
	}

	@Test
	@Timeout(10)
	void verschmutzenTest() {
		assertFalse(werkstatt.isDreckig(), "Die Werkstatt darf am Anfang nicht dreckig sein!");
		while (!werkstatt.isDreckig()) {
			Buch kaputtesBuch = new Buch("Testen für Anfänger", ISBN.NullISBN);
			kaputtesBuch.starkBeschaedigen();
			werkstatt.zurReparaturHinzufuegen(kaputtesBuch);
			werkstatt.reparieren();
		}
		werkstatt.saeubern();
		assertFalse(werkstatt.isDreckig(), "Nach der Reinigung darf eine Werkstatt nicht dreckig sein");
	}

	@Test
	void isRepariertOkayNachReparatur() {
		Buch b = new Buch("Testbuch", ISBN.NullISBN);
		b.starkBeschaedigen();
		assertFalse(werkstatt.isRepariert(b), "Das Buch muss beschädigt sein!");
		werkstatt.zurReparaturHinzufuegen(b);
		werkstatt.reparieren();
		assertTrue(werkstatt.isRepariert(b), "Das Buch muss nach der Reparatur als ganz gelten!");
	}

	@Test
	void isRepariertOkayNichtBeschaedigt() {
		Buch b = new Buch("Testbuch", ISBN.NullISBN);
		assertTrue(werkstatt.isRepariert(b), "Ein neues Buch darf nicht beschädigt sein!");
	}

	@Test
	@DisplayName("isRepariert ist false, wenn Buch kaum beschädigt ist und nicht repariert wurde!")
	void isRepariertFalseInReparaturUndVorherNichtBeschaedigt() {
		Buch b = new Buch("Testbuch", ISBN.NullISBN);
		assertTrue(werkstatt.isRepariert(b), "Ein neues Buch darf nicht beschädigt sein!");
		werkstatt.zurReparaturHinzufuegen(b);
		assertFalse(werkstatt.isRepariert(b), "Das Buch darf nicht asl repariert gelten, wenn es in der Werkstatt noch nicht repariert wurde!");
	}

	@Test
	@DisplayName("isRepariert ist true, wenn Buch kaum beschädigt ist und repariert wurde!")
	void isRepariertTrueNachReparaturUndVorherNichtBeschaedigt() {
		Buch b = new Buch("Testbuch", ISBN.NullISBN);
		assertTrue(werkstatt.isRepariert(b), "Ein neues Buch darf nicht beschädigt sein!");
		werkstatt.zurReparaturHinzufuegen(b);
		assertFalse(werkstatt.isRepariert(b), "Das Buch darf nicht asl repariert gelten, wenn es in der Werkstatt noch nicht repariert wurde!");
	}

	@Test
	@DisplayName("isReapriert ist false wenn Buch beschädigt ist und in der Werkstatt ist")
	void isRepariertTrueInReparaturUndVorherBeschaedigt() {
		Buch b = new Buch("Testbuch", ISBN.NullISBN);
		assertTrue(werkstatt.isRepariert(b), "Ein neues Buch darf nicht beschädigt sein!");
	}

	@Test
	void zurueckstellenInsRegalTest() {
		Buch b = new Buch("Testbuch", ISBN.NullISBN);
		werkstatt.zurReparaturHinzufuegen(b);
		werkstatt.reparieren();
		assertTrue(List.of(b).containsAll(Arrays.stream(werkstatt.alleRepariertenZurueckstellen()).toList()));
		verify(bestand).inEinRegalStellen(b);
	}

}