package de.oliver.structure;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Anzahl Bugs: |
public class Regal implements Verschmutzbar {
    public final static int REGALBRETTER_DEFAULT = 5;
    public final static int BUECHER_JE_BRETT_DEFAULT = 20;

    private Buch[][] inhalt;

    public Regal(int regalbretter, int buecherJeBrett) {
        inhalt = new Buch[regalbretter][buecherJeBrett];
    }

    public List<Buch> alleBuecher() { // ist korrekt implementiert
        List<Buch> back = new LinkedList<>();
        for (Buch[] regal : inhalt) {
            back.addAll(Arrays.stream(regal).filter(Objects::nonNull).collect(Collectors.toList()));
        }
        return back;
    }

    public boolean isVoll() {
        for (Buch[] regal : inhalt) {
            if (regal.length < BUECHER_JE_BRETT_DEFAULT) {// ----
                return false;
            }
        }
        return true;
    }

    public Buch hineinStellen(Buch buch){
        for (Buch[] brett: inhalt){
            for(Buch b: brett){
                if (b == null){
                    b = buch;
                }
            }
        }
        return buch;
    }



    @Override
    public boolean isDreckig() {
        return false;
    }

    @Override
    public void saeubern() {

    }

    @Override
    public void verschmutzen() {

    }
}
