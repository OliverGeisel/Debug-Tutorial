package de.oliver.staff;

import java.util.List;

public class Angestellenverwaltung {
    private List<Angestellter> angestellte;

    public void addAngestellten(Angestellter angestellter){
        angestellte.add(angestellter);
    }

    public Angestellter removeAngestellten(Angestellter angestellter){
        if(angestellte.contains(angestellter)) {
            int index = angestellte.indexOf(angestellter);

            Angestellter back = angestellte.get(index);
            angestellte.remove(angestellter);
            return back;
        }
        return null;
    }
}
