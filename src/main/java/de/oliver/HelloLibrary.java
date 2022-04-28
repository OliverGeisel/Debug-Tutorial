package de.oliver;


import de.oliver.staff.Bibliothekar;
import de.oliver.staff.Reinigungskraft;
import de.oliver.staff.Restaurator;
import de.oliver.structure.Bestandtyp;
import de.oliver.structure.Bibliothek;
import de.oliver.structure.Buch;
import de.oliver.visitor.Dozent;
import de.oliver.visitor.Studierender;
import de.oliver.staff.Geschlecht;

/**
 * Achtung die Klasse dient als Laufzeit-Beispiel. Hier ist nicht alles OOP konform implementiert.
 */
public class HelloLibrary {

    private Bibliothek bib;

    private Bibliothekar biblo1;
    private Bibliothekar biblo2;
    private Reinigungskraft reinigung1;
    private Reinigungskraft reinigung2;
    private Restaurator restaurator;

    private Studierender stu1;
    private Studierender stu2;
    private Studierender stu3;
    private Dozent dozent;



    public static void main(String[] args) {
	// write your code here
        HelloLibrary run = new HelloLibrary();
        run.init();
        run.spezielleBuecher();
    }

    public HelloLibrary(){
        bib = new Bibliothek("SLUB", 20, Bestandtyp.Array,2);

        biblo1 = new Bibliothekar("Anna", "Kohl", Geschlecht.WEIBLICH, 25);
        biblo2 = new Bibliothekar("John", "Zimmer",Geschlecht.MAENNLICH, 32);
        reinigung1 = new Reinigungskraft("Tom","Müller", Geschlecht.MAENNLICH,20);
        reinigung2 = new Reinigungskraft("Andre","Kellner", Geschlecht.MAENNLICH, 31 );
        restaurator = new Restaurator("Lena","Büttner", Geschlecht.WEIBLICH, 26);

        stu1 = new Studierender();
        stu2 = new Studierender();
        stu3 = new Studierender();
        dozent = new Dozent();
    }

    private void init(){
        bib.anstellen(biblo1);
        bib.anstellen(biblo2);
        bib.anstellen(reinigung1);
        bib.anstellen(reinigung2);
        bib.anstellen(restaurator);

        bib.neuerBesucher(stu1);
        bib.neuerBesucher(stu2);
        bib.neuerBesucher(stu3);
        bib.neuerBesucher(dozent);

        for (int i =0; i< 100;i++){
            bib.einfuegen(new Buch(Integer.toString(i)));
        }
    }

    private void spezielleBuecher(){

    }

    private void ausleihen(){

    }

    private void saeubern(){

    }

    private void reparieren(){

    }

}
