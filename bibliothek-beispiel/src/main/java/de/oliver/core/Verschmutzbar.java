package de.oliver.core;

/**
 * Ein verschmutzbares Objekt wird über die Zeit immer dreckiger. Nach einer gewissen Zeit sollte es deshalb geeinigt werden.
 */
public interface Verschmutzbar {

	/**
	 * Überprüft, ob das Objekt dreckig ist und gesäubert werden muss.
	 *
	 * @return true, wenn die Verschmutzung hoch genug ist um als dreckig bezeichnet zu werden, sonst false.
	 */
	boolean isDreckig();

	/**
	 * Reinigt das Objekt. Danach sollte direkt isDreckig() false zurückgeben.
	 */
	void saeubern();

	/**
	 * Eine Methode, die das Objekt zu einem gewissen Anteil verschmutzt. Nach einer gewissen Verschmutzung muss isDreckig()
	 * true zurück geben.
	 */
	void verschmutzen();
}
