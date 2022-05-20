public class BreakpointPrint {

	public static int summeVonBis(int a, int b) {
		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		int summe = 0;
		for (int run = a; run < b; run++) {
			System.out.println("run ist: " + run);
			summe += run;
			System.out.println("Summe ist: " + summe);
		}
		System.out.println("Summe am Ende der Methode: " + summe);
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