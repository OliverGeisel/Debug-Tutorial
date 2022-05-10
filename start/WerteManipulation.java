public class WerteManipulation {

	//Erreiche das Ziel, ohne den Code zu ändern. Nur die Variablen dürfen ihren Wert ändern


	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Bitte geben Sie ein als erstes Programmargument eine Zahl von 1 bis 4 ein, um das Level zu wählen!");
			System.exit(1);
		}
		int level;
		try {
			level = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
			level = 5;
		}
		// Breakpoint hier darunter setzen!
		switch (level) {
			case 1 -> level1();
			case 2 -> level2();
			case 3 -> level3();
			case 4 -> level4();
			default -> levelFehlschlag();
		}
	}

	public static void level1() {
		System.out.println("Start Level 1!\nViel Erfolg!");
		int i = 1;
		if (i > 2) {
			levelErfolg(1);
		} else {
			levelFehlschlag();
		}
	}

	public static void level2() {
		System.out.println("Start Level 2!\nViel Erfolg!");
		Level2Objekt zeus = new Level2Objekt("Zeus", 42, 3.14);
		Level2Objekt jupiter = new Level2Objekt("Jupiter", 42, 3.14);
		if (zeus.equals(jupiter)) {
			levelErfolg(2);
		} else {
			levelFehlschlag();
		}

	}

	public static void level3() {

	}

	public static void level4() {

	}

	private static void levelFehlschlag() {
		System.out.println("Sorry! Du hast verloren!");
		System.exit(4);

	}

	private static void levelErfolg(int level) {
		System.out.printf("Glückwunsch! Du hast Level %d abgeschlossen!%n", level);
	}


}

class Level2Objekt {

	private String name;
	private int wert;
	private double preis;

	public Level2Objekt(String name, int wert, double preis) {
		if (name == null) {
			throw new IllegalArgumentException("Name darf nicht null sein!");
		}
		if (name.isBlank() || wert == 0 || preis == 0.0) {
			throw new IllegalArgumentException("Ungültige Werteingabe!");
		}
		this.name = name;
		this.wert = wert;
		this.preis = preis;
	}

	public String getName() {
		return name;
	}

	public int getWert() {
		return wert;
	}

	public double getPreis() {
		return preis;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Level2Objekt)) return false;

		Level2Objekt objekt = (Level2Objekt) o;

		if (wert != objekt.wert) return false;
		if (Double.compare(objekt.preis, preis) != 0) return false;
		return name.equals(objekt.name);
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = name.hashCode();
		result = 31 * result + wert;
		temp = Double.doubleToLongBits(preis);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}