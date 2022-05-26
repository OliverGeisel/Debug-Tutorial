package de.oliver.person.visitor;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KundenregisterTest {

	private Buch buch;
	Kundenregister register;
	Besucher besucher ;

	@BeforeEach
	void setUp(){
		buch = new Buch("Überzogenes Buch", ISBN.NullISBN);
		buch.ausleihen();
		aendereAusleihdatum(buch,LocalDate.now().minusDays(28));
		register = new Kundenregister();
		besucher = new Studierender(1,"Studierender","Test");
	}

	@Test
	void addBesucherOkay() {
		assertTrue(register.addBesucher(besucher),"Der Besucher muss hinzugefügt werden");
	}



	private void verschiebeNachHinten(Buch buch, int tage){
		LocalDate bisherigesDatum = buch.getAusleihdatum();
		aendereAusleihdatum(buch,bisherigesDatum.minusDays(tage));
	}

	private void aendereAusleihdatum(Buch buch, LocalDate date){
		try {
			Field datum = Buch.class.getDeclaredField("ausleihdatum");
			datum.setAccessible(true);
			datum.set(buch,date);
			datum.setAccessible(false);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}



	@Test
	void getStrafe7Tage() {
		addBesucherOkay();
		verschiebeNachHinten(buch,7);
		register.leiheBuchAus(buch,besucher);
		assertEquals(LocalDate.now().minusDays(28+7),buch.getAusleihdatum(),"Das Datum muss 35 Tage in der Vergangenheit liegen!");
		register.gibBuchZurueck(buch,besucher);
		Double strafe = register.getStrafe(besucher);
		assertEquals(7.0,strafe,"Die Strafe muss nach 7 Tagen 7.0 betragen");
	}

	@Test
	void bezahlenOkay() {
	}

	@Test
	void erhoeheStrafeOkay() {
	}

	@Test
	void leiheBuchAusOkay() {
		addBesucherOkay();
		assertTrue(register.leiheBuchAus(buch,besucher),"Das System muss das Buch dem Kunden ausleihen");
	}

	@Test
	void getAusgelieheneBuecherOkay() {
		addBesucherOkay();
		assertTrue(register.leiheBuchAus(buch,besucher));
		assertArrayEquals(List.of(buch).toArray(), register.getAusgelieheneBuecher(besucher).toArray(), "Das Buch muss in der Liste, ausgeliehener Bücher sein!");
	}
}