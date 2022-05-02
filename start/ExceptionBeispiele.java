import java.util.Optional;

public class ExceptionBeispiele {

	static String hallo;

	public static void NullPointerBeispiel(){
		System.out.println(hallo.toUpperCase());
	}

	public static void einfacheException() {
		throw new RuntimeException("Zur Laufzeit gab es einen Fehler!");
	}

	public static void tiefeException() {
		Optional<String> meinString = Optional.of(null);
		meinString.get();
	}

	private static void gemeineException() {
		try {
			tiefeException();
		}catch (NullPointerException npe){
			throw new IllegalArgumentException(npe);
		}



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
