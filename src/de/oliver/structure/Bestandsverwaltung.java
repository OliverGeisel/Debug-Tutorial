package de.oliver.structure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Bestandsverwaltung {

    private Map<Buch,Regal> eintraege;


    public Bestandsverwaltung(){
        eintraege = new HashMap<>();
    }

    public void hinzufuegen(Buch buch, Regal regal) {
        eintraege.put(buch,regal);
    }

    public Regal getRegal(Buch buch){
        return eintraege.get(buch);
    }

    public boolean entferneRegal(Buch buch){
        eintraege.put(buch,null);
        return true;
    }

    public boolean neuesRegal(Buch buch, Regal regal){
        eintraege.put(buch, regal);
        return true;
    }

}

