package de.oliver.structure;

import java.util.Random;

public class Buch implements Comparable<Buch>, Verschmutzbar{
    private final String titel;
    private String autor = "";
    private String isbn = "";
    private boolean ausgeliehen;
    private final long code;
    private double beschaedigung;


    public Buch(String titel){
        this.titel = titel;
        code = new Random().nextInt();
    }

    public Buch(String titel,String autor,String isbn){
        this(titel);
        this.autor = autor;
        this.isbn = isbn;
    }

    public void ausleihen(){
        ausgeliehen = true;
    }

    public void verfuegbar(){
        ausgeliehen = false;
    }

    public void addBeschaedigung(){
        beschaedigung += Math.random() * 0.5;
    }

    public void reparieren() {
        beschaedigung = 0.01;
    }


    @Override
    public boolean isDreckig() {
        return true;
    }

    @Override
    public void saeubern() {

    }

    @Override
    public void verschmutzen() {

    }


    public String getTitel() {
        return titel;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public long getCode() {
        return code;
    }

    public double getBeschaedigung() {
        return beschaedigung;
    }

    public boolean isAusgeliehen() {
        return ausgeliehen;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */

    @Override
    public int compareTo(Buch o) {
        return this.titel.compareTo(o.titel);
    }



}
