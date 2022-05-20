package de.oliver.core;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;


/**
 * Repräsentiert ein Buch in einer Bibliothek.
 * Wenn es mehrere Instanzen des gleichen physischen Buches gibt, so werden diese durch einen Code unterschieden.
 */

public class Buch implements Comparable<Buch>, Verschmutzbar {
	// Todo Anzahl Bugs: ||
	private final String titel;
	private String autor;
	private final ISBN isbn;
	private boolean ausgeliehen;
	private final long code;
	private double beschaedigung;

	private LocalDate ausleihdatum;

	private double verschmutzung;

	public Buch(String titel, ISBN isbn) {
		this(titel, "", isbn);
	}

	public Buch(String titel, String autor, ISBN isbn) {
		this.titel = titel;
		code = new Random().nextInt();
		this.autor = autor;
		this.isbn = isbn;
	}

	/**
	 * Setzt den Status auf ausgeliehen. Kann nicht ausgeliehen werden, wenn es nicht verfügbar ist.
	 */
	public void ausleihen() {
		// Todo Bug check felt.
		ausgeliehen = true;
		ausleihdatum = LocalDate.now();
	}

	/**
	 * Erlaubt das Ausleihen des Buches.
	 */
	public void verfuegbarMachen() {
		// Todo  Bug check fehlt .
		ausgeliehen = false;
		ausleihdatum = null;
	}

	/**
	 * Fügt eine kleine Beschädigung hinzu.
	 */
	public void beschaedigen() {
		beschaedigung += Math.random() * 0.5;
	}

	/**
	 * Das Buch wird stark beschädigt und ist zur Reparatur zu bringen.
	 */
	public void starkBeschaedigen() {
		beschaedigung = Math.max(0.8, beschaedigung + 0.1);
	}


	/**
	 * Repariert das Buch wieder und macht es wieder nutzbar.
	 */
	public void reparieren() {
		beschaedigung = 0.01;
	}


	/**
	 * Gibt Auskunft, ob ein Buch gereinigt werden sollte.
	 *
	 * @return true, wenn die Verschmutzung größer als 0.5 ist.
	 */
	@Override
	public boolean isDreckig() {
		return verschmutzung > 0.5;
	}

	/**
	 * Reinigt das Buch vollständig.
	 */
	@Override
	public void saeubern() {
		verschmutzung = 0.0;
	}

	/**
	 * Verschmutzt das Buch etwas.
	 */
	@Override
	public void verschmutzen() {
		verschmutzung += 0.07;
	}


	public String getTitel() {
		return titel;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public ISBN getIsbn() {
		return isbn;
	}

	public long getCode() {
		return code;
	}

	public double getBeschaedigung() {
		return beschaedigung;
	}

	public LocalDate getAusleihdatum() {
		return ausleihdatum;
	}

	public boolean isAusgeliehen() {
		return ausgeliehen;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Buch buch = (Buch) o;
		return isbn.equals(buch.isbn) && code == buch.code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn, code);
	}

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * <p>The implementor must ensure
	 * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
	 * for all {@code x} and {@code y}.  (This
	 * implies that {@code x.compareTo(y)} must throw an exception iff
	 * {@code y.compareTo(x)} throws an exception.)
	 *
	 * <p>The implementor must also ensure that the relation is transitive:
	 * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
	 * {@code x.compareTo(z) > 0}.
	 *
	 * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
	 * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
	 * all {@code z}.
	 *
	 * <p>It is strongly recommended, but <i>not</i> strictly required that
	 * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
	 * class that implements the {@code Comparable} interface and violates
	 * this condition should clearly indicate this fact.  The recommended
	 * language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 *
	 * <p>In the foregoing description, the notation
	 * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
	 * <i>signum</i> function, which is defined to return one of {@code -1},
	 * {@code 0}, or {@code 1} according to whether the value of
	 * <i>expression</i> is negative, zero, or positive, respectively.
	 *
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 */

	@Override
	public int compareTo(Buch o) {
		return this.isbn.compareTo(o.isbn);
	}


}
