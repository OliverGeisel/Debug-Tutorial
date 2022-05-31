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
