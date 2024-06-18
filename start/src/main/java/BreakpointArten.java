import java.util.stream.Stream;

public class BreakpointArten {

	public void eineMethode() { // Setze Breakpoint in dieser Zeile (Breakpoint für die Methode)
		int a = 42;
		int b = 43; // Setze Breakpoint in dieser Zeile (Line Breakpoint)

		int summeDesQuadrates = Stream.of(a, b).map(number -> number * number).mapToInt(number -> number).sum();
		// Setze Breakpoint in der Zeile darüber für einen Lambda-Ausdruck
		System.out.printf("summe der quadrierten Zahlen: " + summeDesQuadrates); // Setze einen deaktivierten Breakpoint
	}
}
