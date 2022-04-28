package de.oliver.structure;

import de.oliver.person.visitor.Kundenregister;

import java.util.*;

public class Bestandsverwaltung {

	private Map<ISBN, List<Regal>> buchRegalMapping;
	private List<Buch> alleBuecher = new LinkedList<>();
	private Kundenregister kundenregister;


	public Bestandsverwaltung(Kundenregister kundenregister) {
		this.kundenregister = kundenregister;
		buchRegalMapping = new HashMap<>();
	}

	public void hinzufuegen(Buch buch, Regal regal) {
		alleBuecher.add(buch);
		var regale = buchRegalMapping.get(buch.getIsbn());
		if (regale == null) {
			regale = new ArrayList<>();
			buchRegalMapping.put(buch.getIsbn(), regale);
		}
		regale.add(regal);
	}

	/**
	 * Sucht nach dem Regal, das das gesuchte Buch enthält.
	 * <p>
	 * Das Buch wird
	 *
	 * @param buch
	 * @return
	 */
	public Regal getRegal(Buch buch) {
		return buchRegalMapping.get(buch.getIsbn()).stream().filter(it -> it.enthaelt(buch)).findFirst().orElseThrow();
	}

	public boolean entferneAusRegal(Buch buch) {
		var regale = buchRegalMapping.get(buch.getIsbn());
		if (regale == null) {
			return false;
		}
		regale.remove(0).herausnehmen(buch);
		return true;
	}


	public Buch sucheNachISBN(ISBN isbn) {
		int index = Collections.binarySearch(alleBuecher, new Buch("Testbuch", isbn));
		if (index < 0) {
			return null;
		}
		return alleBuecher.get(index);
	}

	public Collection<Buch> sucheNachAuthor(String author) {
		Set<Buch> back = new HashSet<>();
		for (Buch buch : alleBuecher) {
			if (author.equals(buch.getAutor())) {
				back.add(buch);
			}
		}
		return back;
	}

	public Buch sucheNachTitel(String titel) {
		return null;
	}

	public Buch sucheNachTreffer(String text) {
		// todo implement
		return null;
	}

	public boolean ausleihen(Buch buch) {
		if (!buch.isAusgeliehen()) {
			buch.ausleihen();
			//TODO für kunden markieren.
		}
		return true;
	}

	public boolean zurueckgeben(Buch buch) {

		// Todo implement
		return false;
	}
}

