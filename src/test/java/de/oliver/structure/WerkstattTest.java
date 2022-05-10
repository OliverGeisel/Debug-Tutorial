package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.core.ZuSchmutzigException;
import de.oliver.person.Geschlecht;
import de.oliver.person.staff.Angestellter;
import de.oliver.person.staff.Restaurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class WerkstattTest {


	private Werkstatt werkstatt;
	private Angestellter restaurator;
	@Mock
	private Bibliothek bib;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(bib.insRegalStellen(any())).thenReturn(new Regal(1, 1, "Test"));
		werkstatt = new Werkstatt(bib);
		restaurator = new Restaurator("John", "Auerbach", Geschlecht.DIVERS, 45);
		werkstatt.hinsetzen(restaurator);
	}


	@Test
	void zurReparaturHinzufuegenTest() {
		Buch kaputtesBuch = new Buch("Testen für Anfänger", ISBN.NullISBN);
		kaputtesBuch.starkBeschaedigen();
		werkstatt.zurReparaturHinzufuegen(kaputtesBuch);
		assertEquals(werkstatt.reparieren(), kaputtesBuch);

	}


	@Test
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
}