package de.oliver.structure;

public interface Verschmutzbar {

    /**
     * Überprüft ob das Objekt dreckig ist und gesäubert werden muss.
     *
     * @return true wenn die Verschmutzung hoch genug ist um als dreckig bezeichnet zu werden, sonst false.
     */
    boolean isDreckig();

    /**
     * Reinigt das Objekt. Danach sollte direkt isDreckig() false zurückgeben
     *
     */
    void saeubern();

    /**
     * Eine Methode, die das Objekt zu einem gewissen Anteil verschmutzt. Nach einer gewisser Verschmutzung muss isDreckig()
     * true zurück geben.
     *
     */
    void verschmutzen();
}
