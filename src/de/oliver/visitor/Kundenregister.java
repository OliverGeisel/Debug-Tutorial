package de.oliver.visitor;

import java.util.LinkedList;
import java.util.List;

public class Kundenregister {

    private List<Besucher> alleBesucher;

    public Kundenregister (){
        alleBesucher = new LinkedList<>();
    }

    public boolean addBesucher(Besucher besucher) {
        return alleBesucher.add(besucher);
    }
}
