package de.oliver.structure;

import java.util.Collection;

public interface Terminal {
	Buch sucheNachISBN(ISBN isbn);

	Collection<Buch> sucheNachAuthor(String author);

	Buch sucheNachTitel(String titel);

	Buch sucheNachTreffer(String text);

	boolean ausleihen(Buch buch);

	boolean zurueckgeben(Buch buch);

	Regal findeRegal(Buch buch);

	double getMahngebuehren();
}
