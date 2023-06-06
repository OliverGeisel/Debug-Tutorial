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

import java.util.stream.Stream;

public class BreakpointArten {

	public void eineMethode() { // Setze Breakpoint in dieser Zeile
		int a = 42;
		int b = 43; // Setze Breakpoint in dieser Zeile

		int summeDesQuadrates = Stream.of(a, b).map(number -> number * number).mapToInt(number -> number).sum();
		// Setze Breakpoint in der Zeile darüber für einen Lambda-Ausdruck
		System.out.printf("summe der quadrierten Zahlen: " + summeDesQuadrates); // setze einen deaktivierten Breakpoint
	}
}
