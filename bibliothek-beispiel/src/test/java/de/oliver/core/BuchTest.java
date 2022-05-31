package de.oliver.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
	void ausleihenFehlschlagWennAusgeliehen() {
		ausleihenErfolgreich();
		assertThrows(IllegalStateException.class, () -> buch.ausleihen(), "Die Ausleihe eines Buches darf nicht gelingen, wenn es bereits augeliehen ist");
	}

	@Test
	void verfuegbarMachenErfolgreich() {
		ausleihenErfolgreich();
		assertDoesNotThrow(() -> buch.verfuegbarMachen(), "Das Buch muss wieder ausleihbar sein!");
	}

	@Test
	void verfuegbarMachenFehlschlagWennNichtAusgeliehen() {
		assertThrows(IllegalStateException.class, () -> buch.verfuegbarMachen(), "Das Buch darf nicht erneut verfügbar gemacht werden.");
	}

	@Test
	void getBeschaedigungAmAnfang() {
		assertEquals(0, buch.getBeschaedigung(), "Beschädigung muss am Anfang 0 sein");
	}

	@Test
	void beschaedigenAenderungTest() {
		assertEquals(0, buch.getBeschaedigung(), "Die Beschädigung muss am Anfang 0 sein!");
		buch.beschaedigen();
		assertNotEquals(0, buch.getBeschaedigung(), "Es muss eine Beschädigung vorhanden sein.");
	}

	@Test
	void starkBeschaedigenAenderungTest() {
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
	void isDreckigFalseNachErstellen() {
		assertFalse(buch.isDreckig(), "Ein neues Buch darf nicht gleich dreckig sein");
	}

	@Test
	void isDreckigTrueNachVerschmutzung() {
		buch.verschmutzen();
		assertTimeout(Duration.of(1, ChronoUnit.SECONDS), () -> {
			while (!buch.isDreckig()) {
				buch.verschmutzen();
			}
		}, "Die Verschmutzung muss sich ändern!");
	}

	@Test
	void saeubernWennDreckig() {
		assertFalse(buch.isDreckig(), "Buch darf nicht dreckig sein!");
		isDreckigTrueNachVerschmutzung();
		buch.saeubern();
		assertFalse(buch.isDreckig(), "Nach der Säuberung darf das Buch nicht mehr dreckig sein!");
	}


	@Test
	void getAusleihdatumNachErstellung() {
		assertNull(buch.getAusleihdatum(), "Es darf kein Ausleihdatum geben!");
	}

	@Test
	void getAusleihdatumNachAusgeliehen() {
		buch.ausleihen();
		assertEquals(LocalDate.now(), buch.getAusleihdatum(), "Das Ausleihdatum muss heute sein!");
	}

	@Test
	void getAusleihdatumNachRueckgabe() {
		buch.ausleihen();
		buch.verfuegbarMachen();
		assertNull(buch.getAusleihdatum(), "Es darf kein Ausleihdatum geben, nachdem das Buch zurück gegeben wurde!");
	}

	@Test
	void compareToKleiner() {
		assertTrue(buch.compareTo(new Buch("Anderes Buch", ISBN.fromString("978-1-123-123-3"))) < 0, "Das Buch muss kleiner sein als das andere!");
	}

	@Test
	void compareToGleich() {
		assertEquals(0, buch.compareTo(new Buch("Anderes Buch", ISBN.fromString("978-1-123-123-2"))), "Das Buch muss gleich sein!");
	}

	@Test
	void compareToGroesser() {
		assertTrue(buch.compareTo(new Buch("Anderes Buch", ISBN.NullISBN)) > 0, "Das Buch muss größer sein als das andere");
	}
}