package de.oliver.structure;

import de.oliver.person.visitor.Dozent;
import de.oliver.person.visitor.Studierender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeseraumTest {
	// Todo implement

	private Leseraum raum;

	@BeforeEach
	void setUp() {
		raum = new Leseraum(2);
	}

	@Test
	void ueberfuelleLeseraum() {
		assertFalse(raum.isBesetzt(), "Raum muss am Anfang leer sein");
		assertDoesNotThrow(() -> raum.betreten(new Dozent(2), new Studierender(3), new Studierender(1)), "Wenn mehr Personen den Raum betreten, als hinein passen dürfen nur so viel hinein, wie noch hineinpassen!");

	}
}