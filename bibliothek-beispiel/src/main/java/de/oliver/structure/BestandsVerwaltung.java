package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Kundenregister;

import java.util.*;

public class BestandsVerwaltung {
	// Todo Bugs rein bauen
	private final Map<ISBN, List<Regal>> buchRegalMapping;
	private final List<Buch> alleBuecher = new LinkedList<>();
	private final Kundenregister kundenregister;
	private Regal[] regale;


	public BestandsVerwaltung(Kundenregister kundenregister) {
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
	 *
	 * @param buch
	 * @return Regal in dem das Buch ist.
	 * @throws NoSuchElementException wenn das Buch in keinem Regal ist.
	 */
	public String getRegalCode(Buch buch) {
		return buchRegalMapping.get(buch.getIsbn()).stream().filter(it -> it.enthaelt(buch)).findFirst().orElseThrow().getCode();
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

	public boolean ausleihen(Buch buch, Besucher besucher) {
		if (!buch.isAusgeliehen()) {
			buch.ausleihen();
			kundenregister.leiheBuchAus(buch,besucher);
		}
		return true;
	}

	public boolean zurueckgeben(Buch buch) {

		// Todo implement
		Buch back = null ;
		back.verfuegbarMachen();
		return false;
	}

	boolean ausRegalNehmen(Buch buch, Regal regal) {
		regal.herausnehmen(buch);
		return true;
	}

	Regal insRegalStellen(Buch buch) {
		for (Regal regal : regale) {
			if (regal.isVoll()) {
				continue;
			}
			regal.hineinStellen(buch);
			hinzufuegen(buch, regal);
			return regal;
		}
		return null;
	}


	public Buch einfuegen(Buch buch) {
		// Todo fix
		hinzufuegen(buch, null);
		Regal verweis = null;
		for (Regal regal : regale) {
			verweis = regal;
			if (regal.isVoll()) {
				continue;
			}
			regal.hineinStellen(buch);
		}
		hinzufuegen(buch, verweis);
		return buch;
	}

	Regal ausRegalNehmen(Buch b) {
		boolean success = false;
		for (Regal regal : regale) {
			try {
				regal.herausnehmen(b);
				success = true;
				break;
			} catch (NoMatchingBookException be) {
				// Nothing to do
			}
			entferneAusRegal(b);
		}
		return null;
	}
}

