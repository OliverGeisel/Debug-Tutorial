package de.oliver.staff;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class AngestellterTest {
	private Angestellter angestellter;

	@BeforeEach
	void setUp() {
		angestellter = new Angestellter("Oliver", "Geisel", Geschlecht.MAENNLICH, 24) {
			// Realisierte Klasse vom Typ Angestellter
		};
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void getAlter() {
		assertEquals(24, angestellter.getAlter(), "Das Alter passt nicht!");
	}

	@Test
	void setAlterOkay() {
		assertEquals(24, angestellter.getAlter(), "Alter wurde falsch initialisiert");

		angestellter.setAlter(25);
		assertEquals(25, angestellter.getAlter(), "Alter wurde nicht geändert!");
	}

	@Test
	void setAlterZuJung() {
		assertEquals(24, angestellter.getAlter(), "Alter wurde falsch initialisiert!");

		assertThrowsExactly(IllegalArgumentException.class, () -> angestellter.setAlter(17));
		assertEquals(24, angestellter.getAlter(), "Das Alter darf nicht ungültig werden!");
	}

	@Test
	void getAlterZugriffsrechte() {
		boolean result = false;
		try {
			int modifier = Angestellter.class.getDeclaredMethod("getAlter").getModifiers();
			String visibility = Modifier.toString(modifier);
			// empty string ist package
			result = visibility.isEmpty();

		} catch (NoSuchMethodException nsme) {
			fail("Die Methode \"getAlter()\" existiert nicht");
		}
		assertTrue(result,"Die Methode \"getAlter()\" sollte nur im package sichtbar sein");
	}


	@Test
	void getVornameOkay() {
		assertEquals("Oliver", angestellter.getVorname(), "Der Vorname passt nicht");
	}

	@Test
	void getGeschlechtOkay() {
		assertEquals(Geschlecht.MAENNLICH, angestellter.getGeschlecht(), "Das Geschlecht stimmt nicht!");
	}

	@Test
	void getGeschlechtZugriffsrechte() {
		boolean result = false;
		try {
			int modifier = Angestellter.class.getDeclaredMethod("getGeschlecht").getModifiers();
			String visibility = Modifier.toString(modifier);
			// empty string ist package
			result = visibility.isEmpty();

		} catch (NoSuchMethodException nsme) {
			fail("Die Methode \"getGeschlecht()\" existiert nicht");
		}
		assertTrue(result,"Die Methode \"getGeschlecht()\" sollte nur im package sichtbar sein");
	}

	@Test
	void getNachnameOkay() {
		assertEquals("Geisel",angestellter.getNachname(),"Nachname passt nicht!");
	}

	@Test
	void getVollerNameOkay() {
		assertEquals(angestellter.getVorname()+" "+angestellter.getNachname(), angestellter.getVollerName(), "Der volle Name passt nicht. Erst der Vorname dann der Nachname!");
	}
}