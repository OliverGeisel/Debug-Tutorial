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

import de.oliver.core.Verschmutzbar;
import de.oliver.person.Person;

/**
 * Eine abstrakte Klasse, die eine Grundlage der verschiedenen Arbeitsplätze einer Bibliothek implementiert.
 *
 * @param <T> Art der Personen, die diesen Arbeitsplatz nutzen können.
 */
public abstract class Arbeitsplatz<T extends Person> implements Verschmutzbar {

	protected T nutzer;
	protected double verschmutzung;

	/**
	 * Gibt Auskunft, ob der Platz besetzt ist oder nicht.
	 *
	 * @return true, wenn Platz besetzt.
	 */
	public boolean isBesetzt() {
		return nutzer != null;
	}

	/**
	 * Setzt eine Person an den Platz.
	 *
	 * @param nutzer Person, die sich hinsetzen soll.
	 * @return true, wenn das Hinsetzen erfolgreich war.
	 * @throws IllegalStateException, wenn bereits eine Person auf dem Platz sitzt.
	 */
	public boolean hinsetzen(T nutzer) {
		if (this.nutzer == null) {
			this.nutzer = nutzer;
			return true;
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Lässt die Person auf dem Platz aufstehen und weggehen.
	 *
	 * @return Person, die dort saß.
	 * @throws IllegalStateException, wenn keine Person dor saß.
	 */
	public T aufstehen() throws IllegalStateException {
		T back = nutzer;
		if (back == null) {
			throw new IllegalStateException();
		}
		nutzer = null;
		return back;
	}

	/**
	 * Gibt momentane Person, die dort sitzt zurück.
	 *
	 * @return Person, wenn besetzt, sonst null.
	 */
	public T getNutzer() {
		return nutzer;
	}

	@Override
	public boolean isDreckig() {
		return verschmutzung > 0.7;
	}

	@Override
	public void saeubern() {
		verschmutzung = 0;
	}
}
