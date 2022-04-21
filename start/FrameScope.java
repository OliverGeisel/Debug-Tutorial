public class FrameScope {

	private int number;
	private String name;

	public void method1(){
		int a = 42;
		method2();
	}


	public void method2(){
	 int result = method3();
	}

	private int method3(){
		return 0;
	}

	public static void main (String[] args){
		FrameScope scope = new FrameScope();
		scope.method1();
	}
}
