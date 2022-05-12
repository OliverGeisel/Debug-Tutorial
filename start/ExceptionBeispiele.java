import de.oliver.person.Geschlecht;
import de.oliver.person.Person;
import de.oliver.person.staff.Angestellter;
import de.oliver.person.staff.Bibliothekar;

import java.util.Optional;
import java.util.stream.Stream;

public class ExceptionBeispiele {

	static String hallo;

	public static void NullPointerBeispiel() {
		System.out.println(hallo.toUpperCase());
	}

	public static void einfacheException() {
		throw new RuntimeException("Zur Laufzeit gab es einen Fehler!");
	}

	public static Integer tiefeException() {
		Optional<String> meinString = Optional.of(null);
		meinString.get();
		return -1;
	}

	private static void gemeineException() {
		Angestellter tom = new Bibliothekar("Tom", "Schimmer", Geschlecht.MAENNLICH, 26);
		try {
			aufstehen(tom);
			arbeiten(tom);
			freizeit(tom);
		} catch (NullPointerException npe) {
			throw new IllegalArgumentException(npe);
		}
	}

	private static void aufstehen(Person person) {
		System.out.printf("Guten Morgen %s! Es ist Zeit fürs Frühstück.🥞🥓\n", person.getVollerName());

	}

	private static void arbeiten(Person person) {
		System.out.println("Zeit zu arbeiten");
		Stream.of(person.getVorname()).
				map(it -> it.length()).
				filter(it -> it > 3).
				findFirst().orElseGet(ExceptionBeispiele::tiefeException);
	}

	private static void freizeit(Person person) {
		System.out.println("Viel Spaß beim Fußball! " + person.getVorname());
	}

	public static void main(String[] args) {
		switch (args[0]) {
			case "0":
				NullPointerBeispiel();
				break;
			case "1":
				einfacheException();
				break;
			case "2":
				tiefeException();
				break;
			default:
				gemeineException();
		}
	}
}
