package de.oliver.structure;

import de.oliver.staff.Angestellenverwaltung;
import de.oliver.staff.Angestellter;
import de.oliver.visitor.Besucher;
import de.oliver.visitor.Kundenregister;

import java.util.*;

// Anzahl Bugs: ||
public class Bibliothek {

    private final String name;
    private final List<Buch> bestand;
    private final Angestellenverwaltung verwaltung;
    private final Kundenregister register;
    private final Leseraum[] raeume;
    private final Werkstadt werkstadt;
    private final List<Terminal> terminals;
    private final Set<Regal> regale;

    public Bibliothek(String name, int anzahlRaeume, Bestandtyp typ, int anzahlRegale) {
        this.name = name;
        raeume = new Leseraum[anzahlRaeume];
        for (Leseraum raum : raeume) { // ----
            raum = new Leseraum(2);
        }
        verwaltung = new Angestellenverwaltung();
        register = new Kundenregister();
        switch (typ) {
            case Linked:
                bestand = new LinkedList<>();
                break;
            case Array:

            default:
                bestand = new ArrayList<>();
        }
        regale = new HashSet<>();
        int i = 1;
        while (i < anzahlRegale) {
            regale.add(new Regal(Regal.REGALBRETTER_DEFAULT, Regal.BUECHER_JE_BRETT_DEFAULT));
            i++;
        }
        werkstadt = new Werkstadt();
        terminals = new ArrayList<>();
        terminals.add(new Terminal(this));
        terminals.add(new Terminal(this));

    }

    public boolean anstellen(Angestellter neuerAngestellter) {
        verwaltung.addAngestellten(neuerAngestellter);
        System.out.printf("Der Angestellte: %s wurde angestellt\n", neuerAngestellter);
        return true;
    }

    Buch sucheNachISBN(String isbn) {
        for (Buch buch : bestand) {
            if (buch.getIsbn().equals(isbn)) {
                return buch;
            }
        }
        return null;
    }

    Buch sucheNachAuthor(String author) {
        for (int i = 0; i <= bestand.size(); i++) {// ----
            Buch b = bestand.get(i);
            if (b.getAutor().equals(author)) {
                return b;
            }
        }
        return null;
    }

    Buch sucheNachTitel(String titel) {
        int index = Collections.binarySearch(bestand, new Buch(titel));
        return bestand.get(index);
    }

    Buch sucheNachTreffer(String text) {
        // Todo implement
        return null;
    }

    public boolean neuerBesucher(Besucher besucher) {
        return register.addBesucher(besucher);
    }

    public Buch einfuegen(Buch buch){
        bestand.add(buch);
        for (Regal regal : regale){
            if (regal.isVoll()){
                continue;
            }
            regal.hineinStellen(buch);
        }
        return buch;
    }

    public Buch sortiertesEinfuegen(Buch buch){
        bestand.add(buch);
        Collections.sort(bestand);
        for (Regal regal : regale){
            if (regal.isVoll()){
                continue;
            }
            regal.hineinStellen(buch);
        }
        return buch;
    }

    public boolean ausleihen(Buch buch){
        if (buch.isAusgeliehen()){
            return false;
        }
        buch.ausleihen();

        // Todo aus Regal nehmen
        return true;
    }

    public String getName() {
        return name;
    }
}
