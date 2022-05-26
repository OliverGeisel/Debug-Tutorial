package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.core.Verschmutzbar;
import de.oliver.person.visitor.Besucher;

import java.util.Collection;

/**
 * Ein Terminal ist eine Schnittstelle der Bibliothek, die die Services der Bibliothek zugänglich macht.
 */
public interface Terminal extends Verschmutzbar {
	Buch sucheNachISBN(ISBN isbn);

	Collection<Buch> sucheNachAuthor(String author);

	Buch sucheNachTitel(String titel);

	Buch sucheNachTreffer(String text);

	boolean ausleihen(Buch buch, Besucher besucher);

	boolean zurueckgeben(Buch buch);

	String findeRegalCode(Buch buch);

	double getMahngebuehren(Besucher besucher);

	Leseraum reservieren(Besucher... besucher);

	boolean bezahlen(Besucher besucher, double betrag);
}