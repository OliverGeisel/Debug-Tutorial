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

package de.oliver.person;

/**
 * Personen sind Besucher oder Angestellte einer Bibliothek. Sie haben immer einen Namen, Vornamen und ein Alter.
 * Außerdem können sie ihren vollständigen Namen angeben.
 */
public interface Person {
	// Todo Refactoring hier ist public überflüssig

	/**
	 * @return Vorname einer Person.
	 */
	public String getVorname();

	/**
	 * @return Nachname einer Person.
	 */
	public String getNachname();

	/**
	 * @return Vorname plus Nachname.
	 */
	default public String getVollerName() {
		return getVorname() + " " + getNachname();
	}

	/**
	 * @return Alter der Person.
	 */
	public int getAlter();

}
