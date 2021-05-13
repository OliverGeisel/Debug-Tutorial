package de.oliver.visitor;

import de.oliver.structure.Buch;
import de.oliver.structure.Terminal;
import de.oliver.visitor.Besucher;

public class Studierender implements Besucher {
    @Override
    public boolean ausleihen(Buch buch , Terminal terminal) {
        return terminal.ausleihen(buch);
    }

    @Override
    public boolean zurueckgeben(Buch buch, Terminal terminal) {
        return terminal.zurueckgeben(buch);
    }

    @Override
    public boolean bezahlen(Terminal terminal) {
        return terminal.bezahlen();
    }
}
