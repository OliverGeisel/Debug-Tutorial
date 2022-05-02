package de.oliver.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuchTest {

	private Buch buch;

	@BeforeEach
	void setUp() {
		buch = new Buch("Buch1", new ISBN(978, 1, 123, 123, 2));
	}

	@Test
	void ausleihenErfolgreich() {
		assertFalse(buch.isAusgeliehen(), "Buch darf am Anfang nicht ausgeliehen sein!");
		buch.ausleihen();
		assertTrue(buch.isAusgeliehen(), "Nach dem ausleihen muss das als ausgeliehen markiert sein!");
	}

	@Test
	void ausleihenFehlschlag() {
		ausleihenErfolgreich();
		assertThrows(IllegalStateException.class, () -> buch.ausleihen(), "Die Ausleihe eines Buches darf nicht gelingen, wenn es bereits augeliehen ist");
	}

	@Test
	void verfuegbarMachen() {
	}

	@Test
	void beschaedigen() {
		assertEquals(0, buch.getBeschaedigung(), "Die Beschädigung muss am Anfang 0 sein!");
		buch.beschaedigen();
		assertNotEquals(0, buch.getBeschaedigung(), "Es muss eine Beschädigung vorhanden sein.");
	}

	@Test
	void starkBeschaedigenTest() {
		assertEquals(0, buch.getBeschaedigung(), "Die Beschädigung muss am Anfang 0 sein!");
		buch.starkBeschaedigen();
		assertTrue(buch.getBeschaedigung() >= 0.8, "Bei starkBeschädigen muss die Beschädigung mindesten 0.8 betragen.");
	}

	@Test
	void reparieren() {
		var beschaedigung = buch.getBeschaedigung();
		buch.beschaedigen();
		assertTrue(beschaedigung < buch.getBeschaedigung(), "Die Beschädigung muss größer sein als vor der Beschädigung!");
		buch.reparieren();
		assertTrue(0.01 >= buch.getBeschaedigung(), "Nach der Reparatur darf die Beschädigung höchstens 0.01 betragen!");
	}

	@Test
	void isDreckig() {
	}

	@Test
	void saeubern() {
	}

	@Test
	void verschmutzen() {
	}

	@Test
	void getBeschaedigung() {
	}

	@Test
	void getAusleihdatum() {
	}

	@Test
	void isAusgeliehenFalse () {
		ass
	}

	@Test
	void compareTo() {
	}
}