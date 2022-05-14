package de.oliver.structure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BibliothekTest {


	private Bibliothek bibliothek;

	@BeforeEach
	void setUp() {
		bibliothek = new Bibliothek("TestBibo", 2, 2, LocalTime.of(8, 0), LocalTime.of(20, 0));
	}

	@AfterEach
	void tearDown() {
	}


	@Test
	void getName() {
		assertEquals("TestBibo", bibliothek.getName(), "Der Name passt nicht!");
	}

	@Test
	void getAngestellenComputerNachtInit() {
		var computer = bibliothek.getAngestellenComputer();
		assertNotNull(computer, "Die Computer der Angestellten dürfen nicht null sein");
		for (int i = 0; i < computer.size(); i++) {
			assertNotNull(computer.get(i), String.format("Der Computer %d in der Bibliothek ist null. Der Computer muss aber existieren!", i));
		}
	}

	@Test
	void getBesucherComputerNachInit() {
		var computer = bibliothek.getBesucherComputer();
		assertNotNull(computer, "Die Computer der Angestellten dürfen nicht null sein");
		for (int i = 0; i < computer.size(); i++) {
			assertNotNull(computer.get(i), String.format("Der Computer %d in der Bibliothek ist null. Der Computer muss aber existieren!", i));
		}
	}

	@Test
	void getBestandsverwaltungNachInit() {
		assertNotNull(bibliothek.getBestandsverwaltung(), "Die Bestandsverwaltung darf nicht null sein!");
	}

	@Test
	void getLeseraeumeNachInit() {
		var raeume = bibliothek.getLeseraeume();
		assertEquals(2, raeume.length, "Die Anzahl der Leseräume muss 2 betragen!");
		for (int i = 0; i < raeume.length; i++) {
			assertNotNull(raeume[i], String.format("Der Raum %d in der Bibliothek ist null. Der Raum muss aber existieren!", i));
		}
	}

	@Test
	void getWerkstattNachInit() {
		assertNotNull(bibliothek.getWerkstatt(), "Die Werkstatt darf nicht null sein!");
	}

	@Test
	void getVerwaltungNachInit() {
		assertNotNull(bibliothek.getVerwaltung(), "Die Verwaltung darf nicht null sein!");
	}

	@Test
	void getRegister() {
		assertNotNull(bibliothek.getRegister(), "Die Kundenregister darf nicht null sein!");
	}

	@Test
	void getOefffnungsZeiten() {
		Bibliothek.OeffnungsZeiten zeiten = new Bibliothek.OeffnungsZeiten(LocalTime.of(8, 0), LocalTime.of(20, 0));
		assertEquals(bibliothek.getOefffnungsZeiten(), zeiten, "Öffnungszeiten stimmen nicht den angegeben überein!");
	}
}