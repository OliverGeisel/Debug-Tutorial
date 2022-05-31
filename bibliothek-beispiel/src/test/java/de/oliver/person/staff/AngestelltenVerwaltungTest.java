package de.oliver.person.staff;

import de.oliver.person.Geschlecht;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AngestelltenVerwaltungTest {


	private AngestelltenVerwaltung verwaltung;
	private Angestellter angestellter;

	@BeforeEach
	void setUp() {
		verwaltung = new AngestelltenVerwaltung();
		angestellter = new Reinigungskraft("Tina", "Von Richter", Geschlecht.WEIBLICH, 24);
	}

	@Test
	void isAngestelltOkay() {
		verwaltung.angestelltenHinzufuegen(angestellter, Bereich.REINIGUNG);
		assertTrue(verwaltung.isAngestellt(angestellter), "Der Angestellte ist bei der Bibliothek angestellt!");
	}

	@Test
	void isAngestelltFalse() {
		assertFalse(verwaltung.isAngestellt(angestellter), "Der Angestellte darf nicht als angestellt gelten!");
	}

	@Test
	void isAngestelltFalseNachEntlassung() {
		isAngestelltOkay();
		verwaltung.removeAngestellten(angestellter);
		assertFalse(verwaltung.isAngestellt(angestellter), "Der Angestellte darf nicht als angestellt gelten, nachdem er entlassen wurde!");
	}

	@Test
	void angestelltenHinzufuegenOkay() {
		verwaltung.angestelltenHinzufuegen(angestellter, Bereich.REINIGUNG);
		assertTrue(verwaltung.getAngestellte(Bereich.REINIGUNG).contains(angestellter), "Der ");
	}

	@Test
	void removeAngestellten() {
		angestelltenHinzufuegenOkay();
		verwaltung.removeAngestellten(angestellter);
		assertFalse(verwaltung.getAngestellte(Bereich.REINIGUNG).contains(angestellter), "Der Angestellte darf nicht mehr enthalten sein!");
	}
}