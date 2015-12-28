package marksprojects;

public class MousePrinter {

	public static void main(String[] args) throws InterruptedException {
		while(true) {
			System.out.println(Functions.getMousePosition());
			Functions.sleep(10);
		}
	}

}
