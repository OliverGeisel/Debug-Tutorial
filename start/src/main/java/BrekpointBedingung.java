import java.time.Duration;
import java.time.Instant;

public class BrekpointBedingung {

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
		System.out.printf("Benötigte Zeit: %d,%9d sec.%n", zeit.getSeconds(), zeit.getNano());
	}
}
