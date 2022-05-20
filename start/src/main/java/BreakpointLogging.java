import java.util.logging.Logger;

public class BreakpointLogging {


	private static Logger log = Logger.getLogger("Debug-Logger");

	public static int summeVonBis(int a, int b) {
		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		int summe = 0;
		for (int run = a; run < b; run++) {
			log.info("run ist: " + run);
			summe += run;
			log.info("Summe ist: " + summe);
		}
		log.info("Summe am Ende der Methode: " + summe);
		return summe;
	}

	public static void main(String[] args) {

		// Gib die Summe der Zahlen von a bis b (inklusive a,b) aus!
		// es können auch negative Zahlen eingegeben werden
		int a = 2;
		int b = 7;
		int c = summeVonBis(a, b);

		System.out.print("Summe der Zahlen a bis b = " + c);
	}

}