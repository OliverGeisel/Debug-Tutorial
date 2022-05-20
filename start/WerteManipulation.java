import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
			case 1:
				level1();
				break;
			case 2:
				level2();
				break;
			case 3:
				level3();
				break;
			case 4:
				level4();
				break;
			default:
				levelFehlschlag();
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
		final Level2Objekt zeus = new Level2Objekt("Zeus", 42, 3.14);
		final Level2Objekt jupiter = new Level2Objekt("Jupiter", 42, 3.14);
		if (zeus.equals(jupiter)) {
			levelErfolg(2);
		} else {
			levelFehlschlag();
		}

	}

	public static void level3() {
		// Todo
	}

	public static void level4() {
		System.out.println("Start Level 4!\nViel Erfolg!");
		Level4Objekt thanus = new Level4Objekt("Thanus", "unvermeidlich", "Balance");
		Level4Objekt ironmen = new Level4Objekt("Toni Starke", "ironmen", "Liebe");
		// Böser Trick mit Reflection
		try {
			Field handschuhFeld = Level4Objekt.class.getDeclaredField("handschuh");
			handschuhFeld.setAccessible(true);
			Level4Objekt.Handschuh handschuh = (Level4Objekt.Handschuh) handschuhFeld.get(thanus);
			handschuhFeld.setAccessible(false);
			Field inhaltFeld = Level4Objekt.Handschuh.class.getDeclaredField("inhalt");
			inhaltFeld.setAccessible(true);
			Constructor constructor = Level4Objekt.Handschuh.class.getDeclaredClasses()[0].getDeclaredConstructor(Level4Objekt.Handschuh.class, boolean.class);
			constructor.setAccessible(true);
			inhaltFeld.set(handschuh, constructor.newInstance(handschuh, true));
			constructor.setAccessible(false);
			inhaltFeld.setAccessible(false);
		} catch (
				NoSuchFieldException | IllegalAccessException | InstantiationException | NoSuchMethodException |
				InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		//Handschuh von thanus ist vollständig!
		// Herausforderung! Ohne die Änderung des Rückgabewertes der Methoden!
		// Ablauf
		try {
			thanus.sprechen();
			thanus.snap();
			ironmen.sprechen();
			ironmen.snap();
			levelErfolg(4);
			System.out.println("Du hast das Universum gerettet!");

		} catch (
				PerfekteBalanceException pbe) {
			pbe.printStackTrace();
			levelFehlschlag();
		}

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
		if (name.isEmpty() || wert == 0 || preis == 0.0) {
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

class Level4Objekt {
	final private String name;
	final private String sein;
	private Handschuh handschuh;
	final private String vorhaben;


	public Level4Objekt(String name, String sein, String vorhaben) {
		this.name = name;
		this.vorhaben = vorhaben;
		this.sein = sein;
		handschuh = new Handschuh();
	}

	public void snap() throws PerfekteBalanceException {
		if (handschuh.isSnapable()) {
			switch (vorhaben) {
				case "Liebe":
					System.out.println("Der Beweis, dass " + name + " ein Herz hat");
					break;
				case "Balance":
					throw new PerfekteBalanceException();
			}
		}
	}

	public void sprechen() {
		System.out.printf("%s: Ich bin %s!%n", name, sein);
	}

	class Handschuh {

		private Inhalt inhalt = new Inhalt();

		boolean isSnapable() {
			return inhalt.isFull();
		}

		private class Inhalt {
			final boolean inhalt1;
			final boolean inhalt2;
			final boolean inhalt3;
			final boolean inhalt4;
			final boolean inhalt5;

			private Inhalt(boolean wert) {
				inhalt1 = wert;
				inhalt2 = wert;
				inhalt3 = wert;
				inhalt4 = wert;
				inhalt5 = wert;
			}

			public Inhalt() {
				inhalt1 = false;
				inhalt2 = false;
				inhalt3 = false;
				inhalt4 = false;
				inhalt5 = false;
			}

			public boolean isFull() {
				return inhalt1 && inhalt2 && inhalt3 && inhalt4 && inhalt5;
			}
		}

	}
}

class PerfekteBalanceException extends RuntimeException {
	public PerfekteBalanceException() {
		super("Das Universum befindet sich nun in Balance!");
	}
}
