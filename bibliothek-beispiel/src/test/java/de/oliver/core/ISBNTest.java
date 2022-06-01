package de.oliver.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ISBNTest {

	private ISBN isbn1 = new ISBN(978, 1, 12345, 123, 1);
	private ISBN isbn2 = new ISBN(978, 1, 12345, 123, 1);
	private ISBN isbn3 = new ISBN(978, 1, 12345, 123, 2);

	@Test
	void constructorWith978Okay() {
		assertDoesNotThrow(() -> new ISBN(978, 1, 1, 2, 2), "Die ISBN muss erstellt werden!");
	}

	@Test
	void constructorWith979Okay() {
		assertDoesNotThrow(() -> new ISBN(979, 1, 1, 2, 2), "Die ISBN muss erstellt werden!");
	}

	@Test
	void constructorFehler() {
		assertThrows(IllegalArgumentException.class, () -> new ISBN(9, 1, 1, 2, 2), "Die ISBN darf nicht erstellt werden, wenn der Präfix nicht gültig ist!");
	}

	@Test
	void constructorGruppeFehler() {
		assertThrows(IllegalArgumentException.class, () -> new ISBN(978, 10, 1, 2, 2), "Die ISBN darf nicht erstellt werden, wenn die Gruppe nicht gültig ist!");
	}

	@Test
	void constructorVerlagnummerFehler() {
		assertThrows(IllegalArgumentException.class, () -> new ISBN(978, 1, 100_000, 2, 2), "Die ISBN darf nicht erstellt werden, wenn die Verlagsnummer nicht gültig ist!");
	}

	@Test
	void constructorTitelNummerFehler() {
		assertThrows(IllegalArgumentException.class, () -> new ISBN(978, 10, 1, 1_000, 2), "Die ISBN darf nicht erstellt werden, wenn die Titelnummer nicht gültig ist!");
	}

	@Test
	void constructorPruefzifferFehler() {
		assertThrows(IllegalArgumentException.class, () -> new ISBN(978, 10, 1, 2, 10), "Die ISBN darf nicht erstellt werden, wenn die Prüfziffer nicht gültig ist!");
	}

	@Test
	void mitTrennstrich() {
		assertEquals("978-1-12345-123-1", isbn1.mitTrennstrich(), "Die ISBN gibt nicht die Richtige Formatierung zurück!");
		assertEquals("978-1-00005-003-1", new ISBN(978, 1, 5, 3, 1).mitTrennstrich(), "Die ISBN gibt nicht die Richtige Formatierung zurück!");
	}

	@Test
	void ohneTrennstrich() {
		assertEquals("978 1 12345 123 1", isbn1.ohneTrennstrich(), "Die ISBN gibt nicht die Richtige Formatierung zurück!");
		assertEquals("978 1 00005 003 1", new ISBN(978, 1, 5, 3, 1).ohneTrennstrich(), "Die ISBN gibt nicht die Richtige Formatierung zurück!");

	}

	@Test
	void compareToOkay() {
		assertTrue(isbn1.compareTo(isbn3) < 0);
		assertEquals(0, isbn1.compareTo(isbn2));
		assertTrue(isbn3.compareTo(isbn1) > 0);

	}

	@Test
	void fromStringOkay() {
		String isbnString = "978-1-00005-003-1";
		var isbn = ISBN.fromString(isbnString);
		assertEquals(new ISBN(978, 1, 5, 3, 1), isbn, "Die ISBN muss mit einer ISBN aus den gleichen Zahlen passen");
		assertEquals(isbnString, isbn.mitTrennstrich(), "Die Methode mitTrennstrich() muss den gleichen String, wie den, der für die Erzeugung genutzt wurde zurückgeben");
	}

	@Test
	void fromStringException() {
		String isbnString = "978-1-00005-1";
		assertThrows(IllegalArgumentException.class, () -> ISBN.fromString(isbnString));
	}

	@Test
	void fromStringOhneZahl() {
		String isbnString = "-";
		assertThrows(IllegalArgumentException.class, () -> ISBN.fromString(isbnString));
	}

	@Test
	void equalsOkay() {
		assertEquals(isbn1, isbn2, "Equals von record vergleichen auf inhaltliche Gleichheit");
	}
}