package de.oliver.staff;

import de.oliver.structure.Buch;
import de.oliver.structure.Regal;
import de.oliver.structure.Verschmutzbar;

public class Reinigungskraft extends Angestellter {


    public Reinigungskraft(String name, Geschlecht geschlecht, int alter) {
        super(name, geschlecht, alter);
    }

    public boolean saeubern(Verschmutzbar gegenstand){
        if (gegenstand instanceof Regal){
            Regal regal = (Regal) gegenstand;
            for (Buch buch: regal.alleBuecher()){
                buch.saeubern();
            }
        }
        gegenstand.saeubern();
        return true;
    }


}
