package de.oliver.core;


import java.util.Arrays;

/**
 * Eine ISBN-13. Die ISBN ist eine eindeutige Representation eines Buches.
 * <p>
 * Eine ISBN besteht aus 5 Teilen. Dem Präfix, der Gruppe, der Verlagsnummer, des Titels und der Prüfziffer.
 * <p>
 * Die Prüfziffer wird nicht überprüft
 *
 * @param praefix     Präfix der ISBN. Entweder 978 oder 979
 * @param gruppe      Eine einstellige Zahl, die die Gruppe repräsentiert
 * @param verlagnr    Nummer die den Verlag repräsentiert. Muss kleiner 100_000 sein.
 * @param titelnr     Nummer des Buchtitels im Verlag. Muss kleiner 1_000 sein
 * @param pruefziffer Eine einstellige Zahl, die die anderen Zahlen auf Korrektheit prüft.
 */
// records sind spezielle Klassen. Sie haben nur (default-)getter. equals, toString und hashCode sind bereits überschrieben
public record ISBN(int praefix, int gruppe, int verlagnr, int titelnr, int pruefziffer) implements Comparable<ISBN> {

	/**
	 * @param praefix     Präfix der ISBN. Entweder 978 oder 979
	 * @param gruppe      Eine einstellige Zahl, die die Gruppe repräsentiert
	 * @param verlagnr    Nummer die den Verlag repräsentiert. Muss kleiner 100_000 sein.
	 * @param titelnr     Nummer des Buchtitels im Verlag. Muss kleiner 1_000 sein
	 * @param pruefziffer Eine einstellige Zahl, die die anderen Zahlen auf Korrektheit prüft.
	 */
	// Kontrolle der Parameter
	public ISBN {
		if (praefix < 978 || praefix > 979) {
			throw new IllegalArgumentException("Präfix ist unzulässig");
		}
		if (gruppe > 9 || gruppe < 0) {
			throw new IllegalArgumentException("Gruppe ist ungültig");
		}
		if (verlagnr >= 100_000 || verlagnr < 0) {
			throw new IllegalArgumentException("Verlagsnummer zu groß");
		}
		if (titelnr >= 1_000 || titelnr < 0) {
			throw new IllegalArgumentException("Titel zu groß");
		}
		if (pruefziffer > 9 || pruefziffer < 0) {
			throw new IllegalArgumentException("Prüfziffer ungültig");
		}
	}

	/**
	 * Gibt die ISBN mit Trennstrichen zurück.
	 *
	 * @return Die ISBN mit führenden Nullen und Trennstrichen.
	 */
	String mitTrennstrich() {
		return String.format("%d-%d-%05d-%03d-%d", praefix, gruppe, verlagnr, titelnr, pruefziffer);
	}

	/**
	 * Gibt die ISBN mit Leerzeichen zurück.
	 *
	 * @return Die ISBN mit führenden Nullen und Leerzeichen.
	 */
	String ohneTrennstrich() {
		return String.format("%d %d %05d %03d %d", praefix, gruppe, verlagnr, titelnr, pruefziffer);
	}


	@Override
	public int compareTo(ISBN isbn) {
		return mitTrennstrich().compareTo(isbn.mitTrennstrich());
	}

	/**
	 * Nimmt eine ISBN-Stringrepräsentation und gibt das passende ISBN Objekt zurück.
	 *
	 * @param isbn String der eine ISBN-13 ist.
	 * @return ISBN die zu dem angegeben String passt
	 * @throws IllegalArgumentException wenn der String eine ungültige ISBN-13 ist.
	 */
	public static ISBN fromString(String isbn) {
		var numbers = isbn.split("-");
		var intNumbers = Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
		if (intNumbers.length < 5) {
			throw new IllegalArgumentException("ISBN nicht korrekt. Es wird eine ISBN-13 mit Trennstrichen benötigt!");
		}
		return new ISBN(intNumbers[0], intNumbers[1], intNumbers[2], intNumbers[3], intNumbers[4]);
	}
}
