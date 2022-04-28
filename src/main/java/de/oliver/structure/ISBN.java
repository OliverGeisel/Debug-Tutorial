package de.oliver.structure;


import java.util.Arrays;

// records sind spezielle Klassen. Sie haben nur (default-)getter und haben equals, toString und hashCode bereits überschrieben
public record ISBN(int praefix, int gruppe, int verlagnr, int titelnr, int pruefziffer) implements Comparable<ISBN> {


	String mitTrennstrich() {
		return String.format("%d-%d-%d-%d-%d", praefix, gruppe, verlagnr, titelnr, pruefziffer);
	}

	String ohneTrennstrich() {
		return String.format("%d %d %d %d %d", praefix, gruppe, verlagnr, titelnr, pruefziffer);
	}

	@Override
	public int compareTo(ISBN isbn) {
		return mitTrennstrich().compareTo(isbn.mitTrennstrich());
	}

	public static ISBN fromString(String isbn) {
		var numbers = isbn.split("-");
		var intNumbers = Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
		if (intNumbers.length < 5) {
			throw new IllegalArgumentException("ISBN nicht korrekt. Es wird eine ISBN-13 mit Trennstrichen benötigt!");
		}
		return new ISBN(intNumbers[0], intNumbers[1], intNumbers[2], intNumbers[3], intNumbers[4]);
	}
}
