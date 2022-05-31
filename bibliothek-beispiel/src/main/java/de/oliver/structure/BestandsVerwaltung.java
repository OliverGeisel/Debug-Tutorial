package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import de.oliver.person.staff.VerwaltungsException;
import de.oliver.person.visitor.Besucher;
import de.oliver.person.visitor.Kundenregister;

import java.util.*;

public class BestandsVerwaltung {
	// Todo Bugs: ||
	private final Map<ISBN, List<Regal>> buchRegalMapping;
	private final List<Buch> alleBuecher = new LinkedList<>();
	private final Kundenregister kundenregister;
	private Regal[] regale;

	public Regal[] getRegale() {
		return regale;
	}

	public Werkstatt getWerkstatt() {
		return werkstatt;
	}

	private final Werkstatt werkstatt;


	public BestandsVerwaltung(Kundenregister kundenregister, Set<Regal> regale) {
		this.kundenregister = kundenregister;
		this.regale = regale.toArray(new Regal[1]);
		werkstatt = new Werkstatt(this);
		buchRegalMapping = new HashMap<>();
	}

	private boolean mappingHinzufuegen(Buch buch, Regal regal) {
		var regale = buchRegalMapping.get(buch.getIsbn());
		if (regale == null) {
			regale = new LinkedList<>();
			buchRegalMapping.put(buch.getIsbn(), regale);
		}
		return regale.add(regal);
	}

	private boolean mappingEntfernen(Buch buch, Regal regal) {
		var regale = buchRegalMapping.get(buch.getIsbn());
		return regale.remove(regal);
	}


	private Regal getNichtVollesRegal() {
		for (Regal regal : regale) {
			if (!regal.isVoll()) {
				return regal;
			}
		}
		return null;
	}

	/**
	 * Sucht nach dem Regal, das das gesuchte Buch enthält.
	 * <p>
	 *
	 * @param buch Buch zu dem das Regal gefunden werden soll.
	 * @return Regal in dem das Buch ist.
	 * @throws NoSuchElementException wenn das Buch in keinem Regal ist.
	 */
	public String getRegalCode(Buch buch) throws NoSuchElementException {
		return buchRegalMapping.getOrDefault(buch.getIsbn(), List.of()).stream().filter(it -> it.enthaelt(buch)).findFirst().orElseThrow().getCode();
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
		for (Buch buch : alleBuecher) {
			if (buch.getTitel().equals(titel))
				return buch;
		}
		return null;
	}

	public Buch sucheNachTreffer(String text) {
		// todo implement freiwillig
		return null;
	}

	public void neuesBuchHinzufuegen(Buch buch, Regal regal) {
		alleBuecher.add(buch);
		var regale = buchRegalMapping.get(buch.getIsbn());
		if (regale == null) {
			regale = new ArrayList<>();
			buchRegalMapping.put(buch.getIsbn(), regale);
		}
		regale.add(regal);
		regal.hineinStellen(buch);
	}

	public void neuesBuchHinzufuegen(Buch buch) {
		Regal regal = getNichtVollesRegal();
		neuesBuchHinzufuegen(buch, regal);
	}

	public boolean zurueckgeben(Buch buch) {
		boolean back = kundenregister.gibBuchZurueck(buch);
		buch.verfuegbarMachen();
		if (buch.getBeschaedigung() >= 0.8)
			werkstatt.zurReparaturHinzufuegen(buch);// Todo Bug - Else fehlt
		inEinRegalStellen(buch);
		return back;
	}

	/**
	 * Stellt ein bereits registriertes Buch in ein Regal.
	 * <p>
	 * Diese Methode soll genutzt werden, wenn das Buch bereits in der Bibliothek registriert wurde
	 *
	 * @param buch Buch, das hineingestellt werden soll
	 * @return Regal, in das das Buch hineingestellt wurde.
	 * @throws VerwaltungsException,  wenn kein Regal mehr Platz hat.
	 * @throws IllegalStateException, wenn das Buch noch nicht registriert ist.
	 */
	Regal inEinRegalStellen(Buch buch) throws VerwaltungsException, IllegalStateException {
		// Todo Bug Lösung
		if (!buch.isAusgeliehen() && !alleBuecher.contains(buch))
			throw new IllegalStateException();
		Regal verweis = null;
		for (Regal regal : regale) {
			if (!regal.isVoll()) {
				verweis = regal;
				break;
			}
		}
		if (verweis == null) {
			throw new VerwaltungsException("Wir haben leider keinen Platz mehr!");
		}
		verweis.hineinStellen(buch);
		mappingHinzufuegen(buch, verweis);
		return verweis;
	}


	public boolean ausleihen(Buch buch, Besucher besucher) {
		if (!buch.isAusgeliehen()) {
			buch.ausleihen();
			kundenregister.leiheBuchAus(buch, besucher);
			ausRegalNehmen(buch);
		}
		return true;
	}


	/**
	 * Nimmt ein Buch aus den Regalen.
	 *
	 * @param regal Regal, aus dem das Buch genommen werden soll.
	 * @param buch  Buch, das aus dem Regal genommen werden soll.
	 * @return true, wenn das Buch herausgenommen wurde; sonst false.
	 * @throws NoMatchingBookException, wenn das Buch nicht im Regal ist.
	 */
	boolean ausRegalNehmen(Buch buch, Regal regal) throws NoMatchingBookException {
		regal.herausnehmen(buch);
		mappingEntfernen(buch, regal);
		return true;
	}

	/**
	 * Nimmt ein Buch aus einem Regaal, in dem das Buch steht.
	 *
	 * @param buch
	 * @return Regal, das das Buch hatte.
	 */
	Regal ausRegalNehmen(Buch buch) throws NoSuchElementException {
		Regal back = null;
		for (Regal regal : regale) {
			try {
				regal.herausnehmen(buch);
				back = regal;
				break;
			} catch (NoMatchingBookException ignored) {
			}
		}
		if (back == null) {
			throw new NoSuchElementException("Es gab leider kein Regal, das das Buch enthält.");
		}
		return back;
	}


}

