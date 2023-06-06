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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

public class TestErgebnisse {

	@Test
	public void TestMitUnerwarteterException() {
		throw new IllegalArgumentException("Unerwartete Exception!");
	}

	@Test
	public void abgebrochenerTest() {
		assumeFalse(true, "Die Annahme ist nicht Falsch!");
	}

	@Test
	public void erfolgreicherTest() {
		assertTrue(true, "Der Test erfordert true!");
	}

	@Test
	public void fehlerhafterTest() {
		assertTrue(false, "Der Test erfordert true!");
	}

	@Test
	public void fehlgeschlagenerTest() {
		fail("Dieser Test schlägt immer Fehl!");
	}

	@Test
	@Disabled
	public void uebersprungenerTest() {
		// Dieser Test wird nicht ausgeführt
	}
}
