package de.oliver.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ISBNTest {

	private ISBN isbn1 = new ISBN(978, 1, 12345, 123, 1);
	private ISBN isbn2 = new ISBN(978, 1, 12345, 123, 1);
	private ISBN isbn3 = new ISBN(978, 1, 12345, 123, 2);

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
		assertThrows(IllegalArgumentException.class,()->ISBN.fromString(isbnString));
	}
@Test
	void fromStringOhneZahl() {
		String isbnString = "-";
		assertThrows(IllegalArgumentException.class,()->ISBN.fromString(isbnString));
	}

	@Test
	void equalsOkay() {
		assertEquals(isbn1, isbn2, "Equals von record vergleichen auf inhaltliche Gleichheit");
	}
}