package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.person.staff.VerwaltungsException;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Kundenregister;
import de.oliver.person.visitor.Studierender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BestandsVerwaltungTest {

	private BestandsVerwaltung verwaltung;
	@Mock
	private Kundenregister kundenregister;

	@Mock
	private Regal regal1;

	private Regal regal2;
	private Besucher besucher;
	private Buch buch = new Buch("LeeresBuch", ISBN.NullISBN);

	@BeforeEach
	void setUp() {
		besucher = new Studierender(1, "Studierender", "Teststudierender");

		regal2 = new Regal(2, 10, "Regal2");
		MockitoAnnotations.openMocks(this);

		Set<Regal> regale = new HashSet<>();
		regale.add(regal1);
		regale.add(regal2);
		// stub regal1
		when(regal1.getCode()).thenReturn("Regal1");
		when(regal1.hineinStellen(buch)).thenReturn(buch);
		when(regal1.herausnehmen(buch)).thenReturn(buch);
		when(regal1.anzahlBuecherImRegal()).thenReturn(0L);
		when(regal1.isDreckig()).thenReturn(false);
		when(regal1.isVoll()).thenReturn(false);
		// stub kundenregister
		when(kundenregister.gibBuchZurueck(any(), any(Besucher.class))).thenReturn(true);
		when(kundenregister.gibBuchZurueck(any())).thenReturn(true);
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
		verwaltung.neuesBuchHinzufuegen(buch, regal1);
		when(regal1.enthaelt(buch)).thenReturn(true);
		assertEquals("Regal1", verwaltung.getRegalCode(buch), "Der Code muss zurückgegeben werden!");
	}

	@Test
	void getRegalCodeVonUnbekanntemRegal() {
		Buch testBuch = new Buch("Testbuch", ISBN.NullISBN);
		assertThrows(NoSuchElementException.class, () -> verwaltung.getRegalCode(testBuch), "Der Code darf nicht gefunden werden!");
	}


	@Test
	void sucheNachISBNTreffer() {
		verwaltung.neuesBuchHinzufuegen(buch);
		Buch zweitesBuch = new Buch("Zweites Buch", ISBN.fromString("978-1-1-1-1"));
		verwaltung.neuesBuchHinzufuegen(zweitesBuch);
		assertEquals(2, verwaltung.getAnzahlBuecher(), "Es müssen beide Bücher aufgenommen worden sein!");
		var result = verwaltung.sucheNachISBN(ISBN.NullISBN);
		assertEquals(buch, result, "Es muss das Buch mit der Null-ISBN zurückgegeben werden.");
	}

	@Test
	void sucheNachISBN_KeinTreffer() {
		verwaltung.neuesBuchHinzufuegen(buch);
		assertEquals(1, verwaltung.getAnzahlBuecher(), "Es muss ein Buch aufgenommen worden sein!");
		var result = verwaltung.sucheNachISBN(ISBN.fromString("978-1-1-1-1"));
		assertNull(result, "Es muss das Buch mit der ISBN zurückgegeben werden, wenn kein Buch die ISBN besitzt.");
	}

	@Test
	void sucheNachISBN_KeinTrefferWennLeer() {
		assertEquals(0, verwaltung.getAnzahlBuecher(), "Es darf kein Buch vorhanden sein!");
		var result = verwaltung.sucheNachISBN(ISBN.NullISBN);
		assertNull(result, "Es darf kein Buch mit der Null-ISBN zurückgegeben, wenn kein Buch enthalten ist.");
	}

	@Test
	void sucheNachAuthor1TrefferOkay() {
		buch.setAutor("Robert Test");
		verwaltung.neuesBuchHinzufuegen(buch);
		assertEquals(1, verwaltung.getAnzahlBuecher(), "Buch muss hinzugefügt sein!");
		assertArrayEquals(List.of(buch).toArray(), verwaltung.sucheNachAuthor("Robert Test").toArray(), "Es muss das Buch zurückgeben!");
	}

	@Test
	void sucheNachAuthor2TrefferOkay() {
		buch.setAutor("Robert Test");
		verwaltung.neuesBuchHinzufuegen(buch);
		verwaltung.neuesBuchHinzufuegen(new Buch("Hallo Welt", "Robert Test", ISBN.fromString("978-1-2-3-4")));
		assertEquals(2, verwaltung.getAnzahlBuecher(), "Bücher müssen hinzugefügt sein!");
		assertEquals(2, verwaltung.sucheNachAuthor("Robert Test").size(), "Es muss das Buch zurückgeben!");
	}

	@Test
	void sucheNachAuthor2TrefferGleicheISBN_Okay() {
		buch.setAutor("Robert Test");
		verwaltung.neuesBuchHinzufuegen(buch);
		var buch2 = new Buch("Ein anderes Buch", "Robert Test", ISBN.NullISBN);
		verwaltung.neuesBuchHinzufuegen(buch2);
		assertEquals(2, verwaltung.getAnzahlBuecher(), "Buch muss hinzugefügt sein!");
		var result = verwaltung.sucheNachAuthor("Robert Test");
		assertTrue(List.of(buch, buch2).containsAll(result), "Es muss das Buch zurückgeben!");
	}

	@Test
	void sucheNachAuthor2TrefferGleichesBuch_Okay() {
		buch.setAutor("Robert Test");
		verwaltung.neuesBuchHinzufuegen(buch);
		var buch2 = new Buch(buch.getTitel(), buch.getAutor(), buch.getIsbn());
		verwaltung.neuesBuchHinzufuegen(buch2);
		assertEquals(2, verwaltung.getAnzahlBuecher(), "Buch muss hinzugefügt sein!");
		assertArrayEquals(List.of(buch, buch2).toArray(), verwaltung.sucheNachAuthor("Robert Test").toArray(), "Es muss das Buch zurückgeben!");
	}

	@Test
	void sucheNachAuthorKeinTrefferOkay() {
		buch.setAutor("Robert Test");
		verwaltung.neuesBuchHinzufuegen(buch);
		assertEquals(1, verwaltung.getAnzahlBuecher(), "Buch muss hinzugefügt sein!");
		assertArrayEquals(List.of().toArray(), verwaltung.sucheNachAuthor("Major Tom").toArray(), "Es darf kein Buch zurückgeben werden!");
	}


	@Test
	@Disabled("Nicht implementiert")
	void sucheNachTreffer() {
		// todo freiwillig Implementiert
	}

	@Test
	void neuesBuchHinzufuegenOkay() {
		assertEquals(0, verwaltung.getAnzahlBuecher(), "Es darf kein Buch am Anfang enthalten sein!");
		verwaltung.neuesBuchHinzufuegen(buch);
		assertEquals(1, verwaltung.getAnzahlBuecher(), "Das Buch muss dabei sein.");
	}

	@Test
	void neuesBuchHinzufuegenFehler() {
		assertEquals(0, verwaltung.getAnzahlBuecher(), "Es darf kein Buch am Anfang enthalten sein!");
		verwaltung.neuesBuchHinzufuegen(buch);
		assertEquals(1, verwaltung.getAnzahlBuecher(), "Das Buch muss dabei sein.");
		assertThrows(VerwaltungsException.class, () -> verwaltung.neuesBuchHinzufuegen(buch), "Das Buch darf nicht erneut hinzugefügt werden!");
	}

	@Test
	void zurueckgebenOkay() {
		assertEquals(0, verwaltung.getAnzahlBuecher(), "Es dürfen keine Bücher vorhanden sein!");
		verwaltung.neuesBuchHinzufuegen(buch);
		assertEquals(1, verwaltung.getAnzahlBuecher(), "Das Buch muss drin sein!");
		verwaltung.ausleihen(buch, new Studierender(1, "Test", "Studierender"));
		assertTrue(buch.isAusgeliehen(), "Buch muss ausgeliehen sein!");
		assertTrue(verwaltung.zurueckgeben(buch), "Das Buch muss zurück gegeben werden!");
		assertFalse(buch.isAusgeliehen(), "Das Buch muss wieder verfügbar sein!");
	}

	@Test
	void inEinRegalStellenIllegalStateExceptionTest() {
		assertThrows(IllegalStateException.class, () -> verwaltung.inEinRegalStellen(buch), "Wenn ein Buch nicht registriert ist, muss eine IllegalStateException kommen");
	}

	@Test
	void inEinRegalStellenVerwaltungsExceptionTest() {
		verwaltung.neuesBuchHinzufuegen(buch);
		verwaltung.ausRegalNehmen(buch);
		when(regal1.isVoll()).thenReturn(false, true);
		for (int i = 0; i < 21; i++) {
			verwaltung.neuesBuchHinzufuegen(new Buch("Test2-Buch", ISBN.NullISBN));
		}
		assertThrows(VerwaltungsException.class, () -> verwaltung.inEinRegalStellen(buch), "Wenn kein Platz mehr ist, muss eine VerwaltungsException kommen");
	}

	@Test
	void inEinRegalStellenOkay() {
		verwaltung.neuesBuchHinzufuegen(buch);
		Regal regal = verwaltung.ausRegalNehmen(buch);
		assertDoesNotThrow(() -> verwaltung.inEinRegalStellen(buch), "Wenn ein Buch registriert ist und Regale leer sind, darf keine Exception geworfen werden!");

	}

	@Test
	void getAnzahlBuecherDuplicateOkay() {
		for (int i = 0; i < 3; i++) {
			verwaltung.neuesBuchHinzufuegen(new Buch("Ein Buch der vielen Kopien", ISBN.NullISBN));
		}
		assertEquals(3, verwaltung.getAnzahlBuecher(), "Es müssen alle Bücher hinzugefügt worden sein!");
	}

	@Test
	void getAnzahlBuecherKeinBuchOkay() {
		assertEquals(0, verwaltung.getAnzahlBuecher(), "Es darf kein Buch in der Verwaltung sein!");
	}

	@Test
	void getAnzahlBuecherVerschieneOkay() {
		for (int i = 0; i < 3; i++) {
			verwaltung.neuesBuchHinzufuegen(new Buch("Ein Buch der vielen Kopien", ISBN.fromString(String.format("978-1-1-1-%s", i))));
		}
		assertEquals(3, verwaltung.getAnzahlBuecher(), "Es müssen alle Bücher hinzugefügt worden sein!");
	}

	@Test
	void getAnzahlVerschiedenerBuecherKeinBuchOkay() {
		assertEquals(0, verwaltung.getAnzahlVerschiedenerBuecher(), "Es darf kein Buch in der Verwaltung sein!");
	}

	@Test
	void getAnzahlVerschiedenerBuecherVerschieneOkay() {
		for (int i = 0; i < 3; i++) {
			verwaltung.neuesBuchHinzufuegen(new Buch("Ein Buch der vielen Kopien", ISBN.fromString(String.format("978-1-1-1-%s", i))));
		}
		assertEquals(3, verwaltung.getAnzahlVerschiedenerBuecher(), "Es müssen alle Bücher als Verschieden gelten!");

	}

	@Test
	void getAnzahlVerschiedenerBuecherDuplicateOkay() {
		for (int i = 0; i < 3; i++) {
			verwaltung.neuesBuchHinzufuegen(new Buch("Ein Buch der vielen Kopien", ISBN.NullISBN));
		}
		assertEquals(1, verwaltung.getAnzahlVerschiedenerBuecher(), "Es darf nur ein Buch ausgegeben werden, wenn die ISBN gleich ist!");
	}
}