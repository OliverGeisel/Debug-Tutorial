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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

enum Geschenk implements Comparable<Geschenk> {

	Liebe("Liebe", 3),
	Ruhm("Ruhm", 2),
	Koenigreich("Koenigreich", 1),
	Nichts("Nichts", 0);

	private final String bezeichnung;

	private final int wert;

	Geschenk(String bezeichnung, int wert) {
		this.bezeichnung = bezeichnung;
		this.wert = wert;
	}

}

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
		// Aufgabe: Verhindere den Trojanischen Krieg.
		System.out.println("Start Level 3!\nViel Erfolg!");
		System.out.println("Paris sollte entscheiden welche der drei Göttinnen Athene, Hera und Aphrodite die schönste ist.\n" +
				"Die Göttinnen beeinflussten Paris indem Sie ihm Geschenke anboten!\n" +
				"Hera: Ich mache die zum mächtigsten König des größten Königreichs!\n" +
				"Athene: Ich mache dich zum unbesiegbar und zum größten Helden!\n" +
				"Aphrodite: Ich gebe dir die Frau, die du über alles liebst!\n" +
				"Paris entschied sich für die Liebe und stahl die Frau Helena aus Sparta.\n" +
				"Die löste den Trojanischen Krieg aus.\n\n"
		);
		System.out.println("So sagen es die Mythen.\nDoch sehen wir, ob man den Lauf der Geschichte ändern kann!");

		Level3ObjektGott athene = new Level3ObjektGott("Athene", Geschenk.Ruhm);
		Level3ObjektGott hera = new Level3ObjektGott("Hera", Geschenk.Koenigreich);
		Level3ObjektGott aphrodite = new Level3ObjektGott("Aphrodite", Geschenk.Liebe);
		Level3ObjektPerson menelaos = new Level3ObjektPerson("Menelaos", null);
		Level3ObjektPerson helena = new Level3ObjektPerson("Helena", menelaos);
		menelaos.setLiebe(helena);
		Level3ObjektPerson paris = new Level3ObjektPerson("Paris", helena);
		System.out.println("Paris hütet seine Schafherde. Hermes brachte die drei Göttinnen Hera, Athene und Aphrodite. \nSie wollen wissen, wer die Schönste ist. Paris soll dies entscheiden!\n " +
				"Paris soll der Schönsten einen goldenen Apfel geben!");
		paris.angebotAnhoeren(hera);
		paris.angebotAnhoeren(athene);
		paris.angebotAnhoeren(aphrodite);
		System.out.println("Paris begehrte am meisten " + paris.begehren());

		Geschenk entscheidung = paris.entscheiden();
		göttlicherWille(entscheidung, helena, paris);
		raubDerHelena(helena, paris);
		String text = entscheidung == Geschenk.Ruhm ? "großer Held. Er besiegte Achilles und brachte der Welt frieden!" : "ein großer König! Sein Königreich Troja wurde das größte, das die Menschheit je gesehen hat. Alle Länder leben in Frieden miteinander.";
		System.out.println("Im laufe seines Leben wird Paris ein " + text);
		levelErfolg(3);
	}

	private static void göttlicherWille(Geschenk geschenk, Level3ObjektPerson helena, Level3ObjektPerson paris) {
		switch (geschenk) {
			case Koenigreich:
				break;
			case Ruhm:
				break;
			case Liebe:
				helena.setLiebe(paris);
				break;
			default:
				System.out.println("Es geschieht nichts");
		}

	}

	private static void raubDerHelena(Level3ObjektPerson helena, Level3ObjektPerson paris) {
		System.out.println("Paris begibt sich nach Sparta und trifft die Frau des Königs von Sparta.");
		if (helena.begehren().equals(paris.toString()) && paris.begehren().equals(helena.toString())) {
			System.out.println("Helena verliebt sich auf den ersten Blick in Paris!\n" +
					"Paris entführt Helena mit nach Troja. Der trojanische Krieg beginnt!");
			levelFehlschlag();
		}
		System.out.println("Helena ignoriert Paris und geht zu ihrem Mann!" +
				"Paris kehrt nach Troja allein zurück");
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

	//region getter / setter
	public String getName() {
		return name;
	}

	public int getWert() {
		return wert;
	}

	public double getPreis() {
		return preis;
	}
//endregion

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Level2Objekt)) return false;

		Level2Objekt objekt = (Level2Objekt) o;

		if (wert != objekt.wert) return false;
		if (Double.compare(objekt.preis, preis) != 0) return false;
		return name.equals(objekt.name);
	}
}

class Level3ObjektPerson {
	private final String name;
	private final List<Geschenk> angebote;

	private Level3ObjektPerson liebe;

	public Level3ObjektPerson(String name, Level3ObjektPerson liebe) {
		this.name = name;
		angebote = new LinkedList<>();
		this.liebe = liebe;
	}

	public void angebotAnhoeren(Level3ObjektGott goettin) {
		angebote.add(goettin.anbieten());
	}

	public String begehren() {
		return liebe.toString();
	}

	public Geschenk entscheiden() {
		return angebote.stream().sorted().findFirst().orElse(Geschenk.Nichts);
	}

	//region getter / setter
	public void setLiebe(Level3ObjektPerson person) {
		System.out.println(name + " hat sich unsterblich in " + person + "verliebt");
		liebe = person;
	}
//endregion

	@Override
	public String toString() {
		return name;
	}
}

class Level3ObjektGott {
	private final String name;
	private Geschenk geschenk;

	Level3ObjektGott(String name, Geschenk geschenk) {
		this.name = name;
		this.geschenk = geschenk;
	}

	public Geschenk anbieten() {
		System.out.println(name + ": Ich schenke dir " + geschenk);
		return geschenk;
	}
}

class Level4Objekt {
	final private String name;
	final private String sein;
	final private String vorhaben;
	private Handschuh handschuh;

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

		//region getter / setter
		boolean isSnapable() {
			return inhalt.isFull();
		}
//endregion

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

			//region getter / setter
			public boolean isFull() {
				return inhalt1 && inhalt2 && inhalt3 && inhalt4 && inhalt5;
			}
//endregion
		}
	}
}

class PerfekteBalanceException extends RuntimeException {
	public PerfekteBalanceException() {
		super("Das Universum befindet sich nun in Balance!");
	}
}
