package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.core.Verschmutzbar;
import de.oliver.person.visitor.Besucher;

import java.util.Collection;

public interface Terminal extends Verschmutzbar {
	Buch sucheNachISBN(ISBN isbn);

	Collection<Buch> sucheNachAuthor(String author);

	Buch sucheNachTitel(String titel);

	Buch sucheNachTreffer(String text);

	boolean ausleihen(Buch buch);

	boolean zurueckgeben(Buch buch);

	Regal findeRegal(Buch buch);

	double getMahngebuehren();

	Leseraum reservieren(Besucher... besucher);

	boolean bezahlen(Besucher besucher, double betrag);
}