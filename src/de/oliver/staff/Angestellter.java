package de.oliver.staff;

public abstract class Angestellter {
    private int alter;
    private String name;
    private Geschlecht geschlecht;


    public Angestellter(String name, Geschlecht geschlecht, int alter){
        this.name = name;
        this.geschlecht = geschlecht;
        this.alter = alter;
    }

}


