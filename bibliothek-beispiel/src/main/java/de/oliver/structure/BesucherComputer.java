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

package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.person.staff.VerwaltungsException;
import de.oliver.person.visitor.Besucher;

import java.util.Collection;

// Anzahl Bugs:
public class BesucherComputer extends Arbeitsplatz<Besucher> implements Terminal {
	private static int counter = 1;
	private final int number;
	private final BestandsVerwaltung bestand;

	public BesucherComputer(BestandsVerwaltung bestand) {
		this.bestand = bestand;
		number = counter;
		counter++;
	}

	@Override
	public Buch sucheNachISBN(ISBN isbn) {
		return bestand.sucheNachISBN(isbn);
	}

	@Override
	public Collection<Buch> sucheNachAuthor(String author) {
		return bestand.sucheNachAuthor(author);
	}

	@Override
	public Buch sucheNachTitel(String titel) {
		return bestand.sucheNachTitel(titel);
	}

	@Override
	public Buch sucheNachTreffer(String text) {
		return bestand.sucheNachTreffer(text);
	}

	@Override
	public boolean ausleihen(Buch buch, Besucher besucher) throws VerwaltungsException {
		if (!besucher.equals(getNutzer()))
			throw new VerwaltungsException("Du kannst kein Buch für jemand anderen ausleihen");
		ausleihen(buch);
		return false;
	}

	public boolean ausleihen(Buch buch) {
		if (!isBesetzt()) {
			return false;
		}
		return bestand.ausleihen(buch, getNutzer());
	}

	public boolean zurueckgeben(Buch buch) {
		if (!isBesetzt()) {
			return false;
		}
		return bestand.zurueckgeben(buch);

	}

	@Override
	public String findeRegalCode(Buch buch) {
		return bestand.getRegalCode(buch);
	}

	@Override
	public double getMahngebuehren(Besucher besucher) {

		throw new UnsupportedOperationException("Diese Art Terminals unterstützt diese Funktionalität nicht!");
	}

	@Override
	public Leseraum reservieren(Besucher... besucher) {
		throw new UnsupportedOperationException("Diese Art Terminals unterstützt diese Funktionalität nicht!");
	}

	@Override
	public boolean bezahlen(Besucher besucher, double betrag) {
		throw new UnsupportedOperationException("Diese Art Terminals unterstützt diese Funktionalität nicht!");
	}

	public String toString() {
		// VLT. besser einen StringBuilder
		return "Das ist Terminal " + number + ". Und es ist mit " + (nutzer != null ? "niemanden" : nutzer) + " bestzt.";
	}

	@Override
	public void verschmutzen() {
		verschmutzung += 0.1;
	}
}
