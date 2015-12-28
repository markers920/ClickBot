package marksprojects;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

public class Functions {

	public static Point getMousePosition() {
		return MouseInfo.getPointerInfo().getLocation();
	}
	
	public static void sleep(long milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}
	
	public static double getDistance(Point p1, Point p2) {
		double deltaX = p1.x - p2.x;
		double deltaY = p1.y - p2.y;
		return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	}
	
	public static void moveMouse(Random random, Robot robot, Point destinationPosition, double pixelsPerMillisecond, double noiseStandardDeviation) throws InterruptedException {
		Point sourcePosition = getMousePosition();
		
		double jumpLength = 3.0;
		int numberOfMoves = 0;
		while(getDistance(sourcePosition, destinationPosition) > jumpLength && numberOfMoves < 500) {
			double deltaX = destinationPosition.x - sourcePosition.x;
			double deltaY = destinationPosition.y - sourcePosition.y;
			
			double distance = getDistance(sourcePosition, destinationPosition);
			
			double deltaUnitX = deltaX / distance;
			double deltaUnitY = deltaY / distance;
			
			double deltaJumpX = deltaUnitX * jumpLength;
			double deltaJumpY = deltaUnitY * jumpLength;
			
			double x = sourcePosition.x;
			double y = sourcePosition.y;
			
			///////////////////////////
			
			x += deltaJumpX + noiseStandardDeviation*random.nextGaussian();
			y += deltaJumpY + noiseStandardDeviation*random.nextGaussian();
			robot.mouseMove((int)x, (int)y);
			numberOfMoves++;
			sleep((long)(jumpLength / pixelsPerMillisecond));
			
			sourcePosition = getMousePosition();
		}
		robot.mouseMove(destinationPosition.x, destinationPosition.y);
	}
	
	public static Point wiggle(Random random, Point p, double stdx, double stdy) {
		return new Point((int)(p.x + stdx*random.nextGaussian()), (int)(p.y + stdy*random.nextGaussian()));
	}
	
	public static void mouseClick(Robot robot) throws InterruptedException {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		sleep(5);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static void mouseScroll(Robot robot, boolean up, int scrollAmount, long sleepTime) throws InterruptedException {
		int direction = up ? -1 : +1;
		for(int i = 0; i < Math.abs(scrollAmount); i++) {
			robot.mouseWheel(direction);
			sleep(sleepTime);
		}
	}
}
