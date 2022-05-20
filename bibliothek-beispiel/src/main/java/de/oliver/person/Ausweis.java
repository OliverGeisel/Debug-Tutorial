package de.oliver.person;


import java.util.Random;

public class Ausweis<P extends Person> {
	private final P person;
	private final int id;


	public Ausweis(P person) {
		this.person = person;
		id = new Random().nextInt(9000) + 1000;
	}

	public P getPerson() {
		return person;
	}

	public int getId() {
		return id;
	}
}
