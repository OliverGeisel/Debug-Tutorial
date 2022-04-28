package de.oliver.visitor;

import de.oliver.structure.Buch;
import de.oliver.structure.Terminal;

public interface Besucher {

    /**
     *
     * @param buch
     * @return
     */
    boolean ausleihen(Buch buch, Terminal terminal);

    /**
     *
     * @param buch
     * @return
     */
    boolean zurueckgeben(Buch buch, Terminal terminal);

    /**
     *
     * @return true wenn keine Probleme beim bezahlen gab, sonst false
     */
    boolean bezahlen(Terminal terminal);
}
