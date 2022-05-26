package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.person.staff.VerwaltungsException;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Kundenregister;
import de.oliver.person.visitor.Studierender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BestandsVerwaltungTest {

	// todo implement
	private BestandsVerwaltung verwaltung;
	@Mock
	private Kundenregister kundenregister;

	private Regal regal1;

	private Regal regal2;
	private Besucher besucher;
	private Buch buch = new Buch("LeeresBuch", ISBN.NullISBN);

	@BeforeEach
	void setUp() {
		besucher = new Studierender(1, "Studierender", "Teststudierender");

		regal1 = new Regal(1, 1, "Regal1");
		regal2 = new Regal(2, 10, "Regal2");
		MockitoAnnotations.openMocks(this);

		Set<Regal> regale = new HashSet<>();
		regale.add(regal1);
		regale.add(regal2);
		when(kundenregister.gibBuchZurueck(any(), any(Besucher.class))).thenReturn(true);
		when(kundenregister.getStrafe(besucher)).thenReturn(0.0);
		//when(kundenregister.leiheBuchAus(any(Buch.class),besucher)).thenReturn(true);
		verwaltung = new BestandsVerwaltung(kundenregister, regale);

	}


	@Test
	void getRegaleOkay() {
		var regale = verwaltung.getRegale();
		assertNotNull(regale, "Es muss ein Array an Regalen geben!");
		assertEquals(2, regale.length, "Es müssen 2 Regale da sein!");
	}

	@Test
	void getWerkstattOkay() {
		var werkstatt = verwaltung.getWerkstatt();
		assertNotNull(werkstatt, " Die Werkstatt darf nicht null sein");
	}

	@Test
	void getRegalCodeVonBekanntemRegal() {
		Buch testBuch = new Buch("Testbuch", ISBN.NullISBN);
		verwaltung.neuesBuchHinzufuegen(testBuch,regal1);
		assertEquals("Regal1", verwaltung.getRegalCode(testBuch), "Der Code muss zurückgegeben werden!");
	}

	@Test
	void getRegalCodeVonUnbekanntemRegal() {
		Buch testBuch = new Buch("Testbuch", ISBN.NullISBN);
		assertThrows(NoSuchElementException.class, () -> verwaltung.getRegalCode(testBuch), "Der Code darf nicht gefunden werden!");
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
	void neuesBuchHinzufuegen() {
	}

	@Test
	void testNeuesBuchHinzufuegen() {
	}

	@Test
	void zurueckgeben() {
	}

	@Test
	void inEinRegalStellenIllegalStateExceptionTest() {
		assertThrows(IllegalStateException.class, () -> verwaltung.inEinRegalStellen(buch), "Wenn ein Buch nicht registriert ist, muss eine IllegalStateException kommen");
	}

	@Test
	void inEinRegalStellenVerwaltungsExceptionTest() {
		verwaltung.neuesBuchHinzufuegen(buch);
		verwaltung.ausRegalNehmen(buch);
		for (int i = 0; i < 21; i++) {
			verwaltung.neuesBuchHinzufuegen(new Buch("Test2-Buch",ISBN.NullISBN));
		}
		assertThrows(VerwaltungsException.class, () -> verwaltung.inEinRegalStellen(buch), "Wenn ein Buch nicht registriert ist, muss eine IllegalStateException kommen");
	}

	@Test
	void inEinRegalStellenOkay() {
		verwaltung.neuesBuchHinzufuegen(buch);
		Regal regal = verwaltung.ausRegalNehmen(buch);
		assertDoesNotThrow(() -> verwaltung.inEinRegalStellen(buch), "Wenn ein Buch registriert ist und Regale leer sind, darf keine Exception geworfen werden!");

	}

	@Test
	void ausleihen() {
	}

	@Test
	void ausRegalNehmen() {
	}

	@Test
	void testAusRegalNehmen() {
	}
}