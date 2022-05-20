import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BreakpointTest {

	@Test
	void summeVonBisTest() {
		assertAll(
				() -> assertEquals(0, Breakpoint.summeVonBis(0, 0), "Die Summe von 0 bis 0 ist 0"),
				() -> assertEquals(6, Breakpoint.summeVonBis(1, 3), "Die Summe von 1 bis 3 muss 6 sein"),
				() -> assertEquals(4, Breakpoint.summeVonBis(4, 4), "Die Summe einer Zahl von a bis a ist a!"),
				() -> assertEquals(0, Breakpoint.summeVonBis(-3, 3), "Die Summer der Zahlen von -a bis a ist 0")
		);
	}
}