package de.oliver.visitor;

import de.oliver.structure.Buch;

public interface Besucher {

    /**
     *
     * @param buch
     * @return
     */
    boolean ausleihen(Buch buch);

    /**
     *
     * @param buch
     * @return
     */
    boolean zurueckgeben(Buch buch);

    /**
     *
     * @return true wenn keine Probleme beim bezahlen gab, sonst false
     */
    boolean bezahlen();
}
