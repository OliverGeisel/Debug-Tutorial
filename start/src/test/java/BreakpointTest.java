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

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BreakpointTest {

	@Test
	@Tag("CI-IGNORE")
	@Tag("LOESUNG-IGNORE")
	void summeVonBisTest() {
		assertAll(
				() -> assertEquals(0, Breakpoint.summeVonBis(0, 0), "Die Summe von 0 bis 0 ist 0"),
				() -> assertEquals(6, Breakpoint.summeVonBis(1, 3), "Die Summe von 1 bis 3 muss 6 sein"),
				() -> assertEquals(4, Breakpoint.summeVonBis(4, 4), "Die Summe einer Zahl von a bis a ist a!"),
				() -> assertEquals(0, Breakpoint.summeVonBis(-3, 3), "Die Summer der Zahlen von -a bis a ist 0")
		);
	}
}