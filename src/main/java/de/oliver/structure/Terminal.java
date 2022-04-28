package de.oliver.structure;

import de.oliver.staff.Arbeitsplatz;
import de.oliver.structure.Bibliothek;

import java.util.Collection;
import java.util.Collections;

// Anzahl Bugs:
public class Terminal extends Arbeitsplatz {
    private static int counter = 1;
    private final int number;
    private final Bibliothek bibo;

    public Terminal(Bibliothek bibo) {
        this.bibo = bibo;
        number = counter;
        counter++;
    }

    public Buch sucheNachISBN(String isbn) {
        return bibo.sucheNachISBN(isbn);
    }

    public Collection<Buch> sucheNachAuthor(String author) {
        return bibo.sucheNachAuthor(author);
    }

    public Buch sucheNachTitel(String titel) {
        return bibo.sucheNachTitel(titel);
    }

    public Buch sucheNachTreffer(String text) {
        return bibo.sucheNachTreffer(text);
    }

    public boolean ausleihen(Buch buch) {
        if (!isBesetzt()) {
            return false;
        }
        return bibo.ausleihen(buch);
    }

    public boolean zurueckgeben(Buch buch) {
        if (!isBesetzt()) {
            return false;
        }
        return bibo.zurueckgeben(buch);

    }

    public boolean bezahlen() {
        if (!isBesetzt()) {
            return false;
        }
        return false;
    }


    public String toString() {
        // VLT. besser einen StringBuilder
        return "Das ist Terminal " + number + ". Und es ist mit " + (nutzer != null ? "niemanden" : nutzer) + " bestzt.";
    }
}
