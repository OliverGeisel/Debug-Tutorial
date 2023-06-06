/*
 * Copyright 2023 Oliver Geisel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

import hilfen.Angestellter;
import hilfen.Bibliothekar;
import hilfen.Geschlecht;
import hilfen.Person;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

public class ExceptionBeispiele {

	static String hallo;

	public static void NullPointerBeispiel() {
		System.out.println(hallo.toUpperCase());
	}

	public static void einfacheException() {
		throw new RuntimeException("Zur Laufzeit gab es einen Fehler!");
	}

	public static Integer tiefeException() {
		Optional<String> meinString = Optional.ofNullable(null);
		meinString.get();
		return -1;
	}

	private static void gemeineException() {
		Angestellter tom = new Bibliothekar("Tom", "Schimmer", Geschlecht.MAENNLICH, 26);
		try {
			aufstehen(tom);
			arbeiten(tom);
			freizeit(tom);
		} catch (NoSuchElementException npe) {
			throw new IllegalArgumentException(npe);
		}
	}

	private static void aufstehen(Person person) {
		System.out.printf("Guten Morgen %s! Es ist Zeit fürs Frühstück.🥞🥓\n", person.getVollerName());
	}

	private static void arbeiten(Person person) {
		System.out.println("Zeit zu arbeiten");
		Stream.of(person.getVorname()).
				map(it -> it.length()).
				filter(it -> it > 3).
				findFirst().orElseGet(ExceptionBeispiele::tiefeException);
	}

	private static void freizeit(Person person) {
		System.out.println("Viel Spaß beim Fußball! " + person.getVorname());
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Bitte gib eine Zahl von 0 bis 3 an!");
			System.exit(1);
		}
		switch (args[0]) {
			case "0":
				NullPointerBeispiel();
				break;
			case "1":
				einfacheException();
				break;
			case "2":
				tiefeException();
				break;
			default:
				gemeineException();
		}
	}
}
