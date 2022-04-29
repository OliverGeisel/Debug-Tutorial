package de.oliver.structure;

import de.oliver.person.Geschlecht;
import de.oliver.person.staff.Angestellter;
import de.oliver.person.staff.Arbeitsplatz;
import de.oliver.person.staff.Bibliothekar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArbeitsplatzTest {

    private Arbeitsplatz arbeitsplatz;
    private Angestellter angestellter;

    @BeforeEach
    public void setUp() {
        arbeitsplatz = new Arbeitsplatz() { // Anonyme Klasse
            @Override
            public void verschmutzen() {
                verschmutzung+=0.35;
            }
        } ;
        angestellter = new Bibliothekar("John", "Böhmer", Geschlecht.DIVERS, 23);
    }

    @Test
    void chekStateOkay() {
        arbeitsplatz.hinsetzen(angestellter);
        //assertDoesNotThrow(arbeitsplatz::chekState);
        assertTrue(arbeitsplatz.isBesetzt());
    }

    @Test
    void chekStateException() {
       // assertThrows(IllegalStateException.class, arbeitsplatz::chekState);
        assertFalse(arbeitsplatz.isBesetzt());
    }

    @Test
    void isBesetztFalse() {
        assertFalse(arbeitsplatz.isBesetzt());
    }

    @Test
    void isBesetztTrue() {
        arbeitsplatz.hinsetzen(angestellter);
        assertTrue(arbeitsplatz.isBesetzt());
    }

    @Test
    void hinsetzenOkay() {
        arbeitsplatz.hinsetzen(angestellter);
        assertEquals(arbeitsplatz.getNutzer(), angestellter);
    }

    @Test
    void aufstehenOkay() {
        arbeitsplatz.hinsetzen(angestellter);
        assertTrue(arbeitsplatz.isBesetzt());
        arbeitsplatz.aufstehen();
        assertNotEquals(angestellter, arbeitsplatz.getNutzer());
    }
}