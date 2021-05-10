package de.oliver.structure;

import de.oliver.staff.Arbeitsplatz;
import de.oliver.structure.Bibliothek;

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

    Buch sucheNachISBN(String isbn) {
        return bibo.sucheNachISBN(isbn);
    }

    Buch sucheNachAuthor(String author) {
        return bibo.sucheNachAuthor(author);
    }

    Buch sucheNachTitel(String titel) {
        return bibo.sucheNachTitel(titel);
    }

    Buch sucheNachTreffer(String text) {
     return bibo.sucheNachTreffer(text);
    }


    public String toString(){
        // VLT. besser einen StringBuilder
        return "Das ist Terminal "+ number + ". Und es ist mit " + (nutzer != null? "niemanden": nutzer) + " bestzt.";
    }
}
