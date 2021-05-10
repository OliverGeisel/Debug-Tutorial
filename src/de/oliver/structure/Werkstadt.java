package de.oliver.structure;

import de.oliver.staff.Arbeitsplatz;

import java.util.LinkedList;
import java.util.List;

public class Werkstadt extends Arbeitsplatz {
    private List<Buch> beschaedigteBuecher;


    public Werkstadt() {
        beschaedigteBuecher = new LinkedList<>();
    }

    public Buch zurReparaturHinzufuegen(Buch buch) {
        beschaedigteBuecher.add(buch);
        return buch;
    }

    public Buch reparieren() {
        if (nutzer == null) {
            throw new IllegalStateException();
        }
        Buch buch = beschaedigteBuecher.remove(0);
        buch.reparieren();
        return buch;
    }
}


