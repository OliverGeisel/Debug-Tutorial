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

import java.time.Duration;
import java.time.Instant;

public class BreakpointBedingung {

	static double pi = 1.0;
	static boolean aendern = true;

	public static void piNaehern() {
		int i;
		for (i = 2; i <= 100; i++) {
			if (i != 97) { // Setze hier bedingten Breakpoint, der das letzte berechnete Ergebnis vor boeseAktion() zeigt.
				guteAktion(i);
			} else {
				boeseAktion();
			}
		}
		System.out.printf("Pi ist %f!%n", pi);
	}

	private static void boeseAktion() {
		pi = 3.0;
		aendern = false;
	}

	private static void guteAktion(int factor) {
		final int wiederholungen = 100_000 * factor; // Eventuell anpassen!
		double x;
		double y;
		int treffer = 0;
		for (int i = 0; i <= wiederholungen && aendern; i++) {
			x = Math.random();
			y = Math.random();
			if ((Math.pow(x, 2) + Math.pow(y, 2)) <= 1.0) {
				treffer++;
			}
		}
		pi = aendern ? (double) (4 * treffer) / wiederholungen : pi;
	}

	public static void main(String[] args) {
		guteAktion(1);
		System.out.println("Pi ist am Anfang: " + pi);
		System.out.printf("Achtung! Der Prozess ist sehr rechenintensiv!%nAbbruch mit Ctrl + C!%n");
		Instant start = Instant.now();
		piNaehern();
		Instant end = Instant.now();
		Duration zeit = Duration.between(start, end);
		System.out.printf("Benötigte Zeit: %d,%09d sec.%n", zeit.getSeconds(), zeit.getNano());
	}
}
