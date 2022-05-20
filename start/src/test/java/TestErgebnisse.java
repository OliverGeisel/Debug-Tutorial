import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

public class TestErgebnisse {

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
	public void TestMitUnerwarteterException() {
		throw new IllegalArgumentException("Unerwartete Exception!");
	}

	@Test
	public void abgebrochenerTest() {
		assumeFalse(true, "Die Annahme ist nicht Falsch!");
	}

	@Test
	@Disabled
	public void uebersprungenerTest() {
		// Dieser Test wird nicht ausgeführt
	}
}
