import java.util.stream.Stream;

public class BreakpointArten {

	
	public void eineMethode() { // Setze Breakpoint in dieser Zeile
		int a = 42;
		int b = 43; // Setze Breakpoint in dieser Zeile

		int summeDesQuadrates = Stream.of(a, b).map(number -> number * number).mapToInt(number -> number).sum();
		// Setze Breakpoint in der Zeile darüber für eine Lambda Ausdru
		
		System.out.printf("summe der quadrierten Zahlen: " + summeDesQuadrates); // setze einen deaktivierten Breakpoint
	}
}
