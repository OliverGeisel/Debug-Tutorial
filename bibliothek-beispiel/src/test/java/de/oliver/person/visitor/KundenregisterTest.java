package de.oliver.person.visitor;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KundenregisterTest {

	private Buch ueberfaelligesBuch;
	private Kundenregister register;
	private Besucher besucher;

	@BeforeEach
	void setUp() {
		ueberfaelligesBuch = new Buch("Überzogenes Buch", ISBN.NullISBN);
		ueberfaelligesBuch.ausleihen();
		aendereAusleihdatum(ueberfaelligesBuch, LocalDate.now().minusDays(28));
		register = new Kundenregister();
		besucher = new Studierender(1, "Studierender", "Test");
	}

	@Test
	void addBesucherOkay() {
		assertTrue(register.addBesucher(besucher), "Der Besucher muss hinzugefügt werden");
	}


	private void verschiebeNachHinten(Buch buch, int tage) {
		LocalDate bisherigesDatum = buch.getAusleihdatum();
		aendereAusleihdatum(buch, bisherigesDatum.minusDays(tage));
	}

	private void aendereAusleihdatum(Buch buch, LocalDate date) {
		try {
			Field datum = Buch.class.getDeclaredField("ausleihdatum");
			datum.setAccessible(true);
			datum.set(buch, date);
			datum.setAccessible(false);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}


	@Test
	void gibBuchZurueckOkay() {
		addBesucherOkay();
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertTrue(register.gibBuchZurueck(ueberfaelligesBuch, besucher), "Das Buch muss zurück gegeben werden können");
		assertTrue(register.getAusgelieheneBuecher(besucher).stream().noneMatch(it -> it.equals(ueberfaelligesBuch)), "Das Buch darf nicht mehr mit dem Besucher verknüpft sein!");
	}

	@Test
	void gibBuchZurueckFehlerNichtDemBesucherZugeordnet() {
		addBesucherOkay();
		assertFalse(register.gibBuchZurueck(ueberfaelligesBuch, besucher), "Das Buch ist nicht richtigem Nutzer zugeordnet und darf nicht zurück gegeben werden");
	}

	@Test
	void gibBuchZurueckFehlerBuchNichtAusgeliehen() {
		Buch andersBuch = new Buch("NeuesBuch", ISBN.NullISBN);
		assertFalse(register.gibBuchZurueck(andersBuch, besucher), "Das Buch ist nicht ausgeliehen und kann nicht zurück gegeben werden!");
	}

	@Test
	void getStrafeNachRegistrierung() {
		addBesucherOkay();
		assertEquals(0, register.getStrafe(besucher), "Die Strafe muss am Anfang bei 0 liegen!");
	}

	@Test
	void getStrafe7TageVorAbgabe() {
		addBesucherOkay();
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		verschiebeNachHinten(ueberfaelligesBuch, -7);
		assertEquals(LocalDate.now().minusDays(28 - 7), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 21 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(0.0, strafe, "Die Strafe muss 0.0 betragen");
	}

	@Test
	void getStrafe1TagVorAbgabe() {
		addBesucherOkay();
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		verschiebeNachHinten(ueberfaelligesBuch, -1);
		assertEquals(LocalDate.now().minusDays(28 - 1), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 27 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(0.0, strafe, "Die Strafe muss 0.0 betragen");
	}

	@Test
	void getStrafeAmAbgabeTag() {
		addBesucherOkay();
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 28 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(0.0, strafe, "Die Strafe muss am Abgabetag  0.0 betragen");
	}

	@Test
	void getStrafeNach1Tag() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 1);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 1), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 29 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(1.0, strafe, "Die Strafe muss nach 1 Tag 1.0 betragen");
	}

	@Test
	void getStrafeNach7Tagen() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 7);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 7), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 35 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(7.0, strafe, "Die Strafe muss nach 7 Tagen 7.0 betragen");
	}

	@Test
	void getStrafeNach8Tagen() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 8);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 8), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 36 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(12.0, strafe, "Die Strafe muss nach 8 Tagen 12.0 betragen");
	}

	@Test
	void getStrafeNach14Tagen() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 14);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 14), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 42 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(12.0, strafe, "Die Strafe muss nach 14 Tagen 17.0 betragen");
	}

	@Test
	void getStrafeNach15Tagen() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 15);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 15), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 43 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(17.0, strafe, "Die Strafe muss nach 15 Tagen 17.0 betragen");
	}


	@Test
	void getStrafeNach43Tagen() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 43);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 43), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 71 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(34.0, strafe, "Die Strafe muss nach 43 Tagen 34.0 betragen");
	}

	@Test
	void getStrafeNach39Wochen() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 7 * 39);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 7 * 39), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 43 Wochen in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(98.0, strafe, "Die Strafe muss nach 39 Wochen 98.0 betragen");
	}

	@Test
	void getStrafeNach40Wochen() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 7 * 40);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 7 * 40), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 44 Wochen in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(100.0, strafe, "Die Strafe muss nach 40 Wochen 100.0 betragen");
	}

	@Test
	void getStrafeNach41Wochen() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 7 * 41);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 7 * 41), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 45 Wochen in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(100.0, strafe, "Die Strafe muss nach 41 Wochen 100.0 betragen");
	}

	@Test
	void getStrafeNach2Jahren() {
		addBesucherOkay();
		verschiebeNachHinten(ueberfaelligesBuch, 2 * 365);
		register.leiheBuchAus(ueberfaelligesBuch, besucher);
		assertEquals(LocalDate.now().minusDays(28 + 2 * 365), ueberfaelligesBuch.getAusleihdatum(), "Das Datum muss 2 Jahre und 28 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(ueberfaelligesBuch, besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(100.0, strafe, "Die Strafe muss nach 2 Jahren 100.0 betragen");
	}

	//------ JUnit Spezial-------------------------------------
// Der Test ersetzt alle Tests oben für den Dozenten
	@ParameterizedTest
	@CsvSource({"-7,0.0",
			"-1,0.0",
			"0,0.0",
			"8,0.0",
			"14,0.0",
			"15,0.0",
			"280,0.0",
			"287,0.0",
			"294,0.0",
	})
	void strafeFuerDozent(int tage, double strafe) {
		Besucher dozent = new Dozent(2, "Dozent", "TestDozent");
		register.addBesucher(dozent);
		verschiebeNachHinten(ueberfaelligesBuch, tage);
		register.leiheBuchAus(ueberfaelligesBuch, dozent);
		assertEquals(LocalDate.now().minusDays(28 + tage), ueberfaelligesBuch.getAusleihdatum(), String.format("Das Datum muss %d Tagen in der Vergangenheit liegen!", tage + 28));
		register.gibBuchZurueck(ueberfaelligesBuch, dozent);
		Double berechneteStrafe = register.getStrafe(dozent);
		assertEquals(strafe, berechneteStrafe, String.format("Die Strafe muss nach %d Tagen %.2f betragen", tage, strafe));

	}

	@Test
	void bezahlenOkay() {
		getStrafeNach1Tag();
		assertNotEquals(0, register.getStrafe(besucher), "Es muss eine Strafe für den Nutzer geben");
		register.bezahlen(besucher);
		assertEquals(0, register.getStrafe(besucher), "Nachdem bezahlt wurde muss die Strafe bei 0 liegen!");
	}

	@Test
	void erhoeheStrafeOkay() {
		addBesucherOkay();
		assertEquals(0, register.getStrafe(besucher), "Die Strafe muss am Anfang bei 0 liegen!");
		register.erhoeheStrafe(besucher, 2.0);
		assertEquals(2, register.getStrafe(besucher), "Die Strafe nach dem erhöhen bei 2 liegen!");


	}


	@Test
	void leiheBuchAusOkay() {
		addBesucherOkay();
		assertTrue(register.leiheBuchAus(ueberfaelligesBuch, besucher), "Das System muss das Buch dem Kunden ausleihen");
	}

	@Test
	void getAusgelieheneBuecherOkay() {
		addBesucherOkay();
		assertTrue(register.leiheBuchAus(ueberfaelligesBuch, besucher));
		assertArrayEquals(List.of(ueberfaelligesBuch).toArray(), register.getAusgelieheneBuecher(besucher).toArray(), "Das Buch muss in der Liste, ausgeliehener Bücher sein!");
	}
}