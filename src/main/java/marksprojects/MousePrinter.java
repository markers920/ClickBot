package marksprojects;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;

public class MousePrinter {

	public static void main(String[] args) throws InterruptedException, AWTException {
		Robot robot = new Robot();
		while(true) {
			Point position = Functions.getMousePosition();
			Color color = robot.getPixelColor(position.x, position.y);
			System.out.println(position + "\t" + color);
			Functions.sleep(10);
		}
	}

}
