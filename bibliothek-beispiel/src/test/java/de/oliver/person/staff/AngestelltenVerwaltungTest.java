/*
 * Copyright 2023 Oliver Geisel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
		verwaltung.angestelltenHinzufuegen(angestellter, Bereich.REINIGUNG);
		assertTrue(verwaltung.isAngestellt(angestellter), "Der Angestellte ist bei der Bibliothek angestellt!");
		verwaltung.removeAngestellten(angestellter);
		assertFalse(verwaltung.isAngestellt(angestellter), "Der Angestellte darf nicht als angestellt gelten, nachdem er entlassen wurde!");
	}

	@Test
	void angestelltenHinzufuegenOkay() {
		verwaltung.angestelltenHinzufuegen(angestellter, Bereich.REINIGUNG);
		assertTrue(verwaltung.getAngestellte(Bereich.REINIGUNG).contains(angestellter), "Der Angestellte muss hinzugefügt werden");
	}

	@Test
	void removeAngestellten() {
		verwaltung.angestelltenHinzufuegen(angestellter, Bereich.REINIGUNG);
		assertTrue(verwaltung.getAngestellte(Bereich.REINIGUNG).contains(angestellter), "Der Angestellte muss hinzugefügt werden");
		verwaltung.removeAngestellten(angestellter);
		assertFalse(verwaltung.getAngestellte(Bereich.REINIGUNG).contains(angestellter), "Der Angestellte darf nicht mehr enthalten sein!");
	}
}