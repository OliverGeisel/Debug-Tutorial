package de.oliver.staff;

public abstract class Arbeitsplatz {

    protected Angestellter nutzer;


    public boolean isBesetzt() {
        return nutzer != null;
    }


    public boolean hinsetzen(Angestellter nutzer) {
        if (this.nutzer == null) {
            this.nutzer = nutzer;
            return true;
        } else {
            throw new IllegalStateException();
        }
    }

    public Angestellter aufstehen() {
        Angestellter back = nutzer;
        if (back == null) {
            throw new IllegalStateException();
        }
        nutzer = null;
        return back;
    }

}
