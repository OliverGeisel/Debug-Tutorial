package de.oliver.structure;

import de.oliver.core.Buch;
import de.oliver.core.ISBN;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegalTest {

	private Regal regal;
	private final Buch buch1 = new Buch("Buch 1",new ISBN(978,3,20,123,2));

	private void mitgleichemBuchfuellen(Regal regal) {
		while (!regal.isVoll()) {
			regal.hineinStellen(buch1);
		}
	}

	@BeforeEach
	void setUp() {
		regal = new Regal(Regal.REGALBRETTER_DEFAULT, Regal.BUECHER_JE_BRETT_DEFAULT, "R1");
	}

	@AfterEach
	void tearDown() {

	}

	@Test
	void isVollTrue() {
		mitgleichemBuchfuellen(regal);
		assertTrue(regal.isVoll(),"Das Regal muss bei vollem Bestand als voll markiert sein!");

	}

	@Test
	void nullBuecherAmAnfang(){
		assertEquals(0,regal.anzahlBuecherImRegal(),"Nach dem erzeugen darf kein Buch im Regal sein");
	}

	@Test
	void isVollFalseBeimErzeugen() {
		assertFalse(regal.isVoll(), "Das Regal muss beim erstellen leer sein!");
	}

	@Test
	void isVollFalseBeimEntnehmenEinesBuches() {;
		assertFalse(regal.isVoll(), "Das Regal muss beim erstellen leer sein!");
		mitgleichemBuchfuellen(regal);
		assertTrue(regal.isVoll(),"Buch ist nicht voll");
		regal.herausnehmen(buch1);
		assertFalse(regal.isVoll(),"Das Regal darf nicht als voll gelten, wenn ein Buch entnommen wurde");
	}

	@Test
	void alleBuecherOkay() {
		regal.hineinStellen(buch1);
		regal.hineinStellen(buch1);
		assertEquals(List.of(buch1,buch1), regal.alleBuecher(),"Das");
	}

	@Test
	void leereVollesRegal(){
		mitgleichemBuchfuellen(regal);
		assertTrue(regal.isVoll(),"Das Regal muss voll sein");
		regal.leeren();
		assertFalse(regal.isVoll(),"Das Regal darf nicht als voll gekennzeichnet sein");
		assertEquals(0,regal.alleBuecher().size(),"Das Regal darf keine Bücher mehr enthalten");
	}

	@Test
	void enthaeltNullTest(){
		assertFalse(regal.enthaelt(null),"Null darf nicht im Regal gefunden werden");
	}

	@Test
	void enthaltBuchOkay(){
		regal.hineinStellen(buch1);
		assertTrue(regal.enthaelt(buch1));
	}

	@Test
	void iteratorTest() {
	}

	@Test
	void toStringTest(){
		assertEquals("Das ist Regal: R1", regal.toString(),"Der ");
	}
}