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

package de.oliver.core;

/**
 * Ein verschmutzbares Objekt wird über die Zeit immer dreckiger. Nach einer gewissen Zeit sollte es deshalb geeinigt werden.
 */
public interface Verschmutzbar {

	/**
	 * Überprüft, ob das Objekt dreckig ist und gesäubert werden muss.
	 *
	 * @return true, wenn die Verschmutzung hoch genug ist um als dreckig bezeichnet zu werden, sonst false.
	 */
	boolean isDreckig();

	/**
	 * Reinigt das Objekt. Danach sollte direkt isDreckig() false zurückgeben.
	 */
	void saeubern();

	/**
	 * Eine Methode, die das Objekt zu einem gewissen Anteil verschmutzt. Nach einer gewissen Verschmutzung muss isDreckig()
	 * true zurück geben.
	 */
	void verschmutzen();
}
