
public class Breakpoint {

	public static void main(String[] args) {

		int a = 7;
		int b = 3;
		int c = 0;
		for (int run = 0; run < b; run++) {
			c += a;
		}
		System.out.print("a + b =" + c);

	}

}