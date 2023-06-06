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

import java.util.logging.Logger;

public class BreakpointLogging {

	private static Logger log = Logger.getLogger("Debug-Logger");

	public static int summeVonBis(int a, int b) {
		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		int summe = 0;
		for (int run = a; run < b; run++) {
			log.info("run ist: " + run);
			summe += run;
			log.info("Summe ist: " + summe);
		}
		log.info("Summe am Ende der Methode: " + summe);
		return summe;
	}

	public static void main(String[] args) {

		// Gib die Summe der Zahlen von a bis b (inklusive a,b) aus!
		// es können auch negative Zahlen eingegeben werden
		int a = 2;
		int b = 7;
		int c = summeVonBis(a, b);

		System.out.print("Summe der Zahlen a bis b = " + c);
	}

}