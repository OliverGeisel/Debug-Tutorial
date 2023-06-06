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

import java.util.stream.Stream;

public class WeiterBeispiele {

	public static void chainStreamWatch() {
		Stream<Integer> test = Stream.of(1, 4, 5, 4, 3, 6, 1, 4, 7, 2);
		test.mapToInt(it -> it)
				.distinct()
				.sorted()
				.filter(it -> it > 3)
				.findFirst();
	}

	public static void expressionEvaluation() {
		int a = 3;
		// werte einzelne Zeilen im Expression Evaluator aus
		if (a != 0
				&& 5 % 2 == 1
				|| true ^ false
				&& (true != false || 0 < 1)) {
			System.out.println("Es war wahr");
		} else {
			System.out.println("Es war falsch");
		}
	}

	public static void main(String[] args) {
		chainStreamWatch();
		expressionEvaluation();
	}
}
