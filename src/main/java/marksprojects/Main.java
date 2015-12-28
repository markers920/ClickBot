package marksprojects;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Main {

	private static Random random = new Random(0);
	private static Robot robot;
	
	public static void main(String[] args) throws AWTException, InterruptedException {
		robot = new Robot();
		
		//initial wait to allow window shift 
		int initialSleepTime = 5;
		System.out.println(String.format("wait %d seconds", initialSleepTime));
		for(int sleepIndex = 1; sleepIndex <= initialSleepTime; sleepIndex++) {
			System.out.println("\t" + sleepIndex);
			Functions.sleep(1000);
		}
		
		//Functions.moveMouse(random, robot, new Point(1000, 500), 0.5, 0);
		
		//click logo
		Point logo = Functions.wiggle(random, new Point(55, 197), 3, 2);
		Functions.moveMouse(random, robot, logo, 0.5, 0);
		Functions.mouseClick(robot);
		Functions.sleep(1500 + random.nextInt(500));
		
		double runTimeSum = 0.0;
		for(int profileIndex = 1; true; profileIndex++) {
			long startTime = System.currentTimeMillis();
			
			if(profileIndex % 10 == 0) {
				System.out.println("pausing for a break");
				Functions.sleep(5000 + random.nextInt(500));
			}
			
			//click browse matches
			Point browseMatches = Functions.wiggle(random, new Point(182, 204), 3, 2);
			Functions.moveMouse(random, robot, browseMatches, 0.5, 0);
			Functions.mouseClick(robot);
			Functions.sleep(1500 + random.nextInt(500));
			
			//click profile
			int[] profileMainX = {652, 943, 1240};
			int[] profileMainY = {500, 920};
			Point profileMain = Functions.wiggle(
					random, 
					new Point(
							profileMainX[random.nextInt(profileMainX.length)], 
							profileMainY[random.nextInt(profileMainY.length)]), 
					5, 5);
			Functions.moveMouse(random, robot, profileMain, 0.5, 0);
			Functions.mouseClick(robot);
			Functions.sleep(1500 + random.nextInt(500));
			
			//click photo
			Point profilePhoto = Functions.wiggle(random, new Point(580, 376), 5, 5);
			Functions.moveMouse(random, robot, profilePhoto, 0.5, 0);
			Functions.mouseClick(robot);
			Functions.sleep(1500 + random.nextInt(500));
			
			//press right/left random combo
			int pictureCount = 0 + random.nextInt(5);
			for(int pictureIndex = 0; pictureIndex < pictureCount; pictureIndex++) {
				int directionKey;
				//if(random.nextBoolean()) {
				if(random.nextDouble() < 0.8) {
					directionKey = KeyEvent.VK_RIGHT;
				} else {
					directionKey = KeyEvent.VK_LEFT;
				}
				
				robot.keyPress(directionKey);
				Functions.sleep(10 + random.nextInt(5));
				robot.keyRelease(directionKey);
				Functions.sleep(1500 + random.nextInt(500));
			}
			
			//press escape
			robot.keyPress(KeyEvent.VK_ESCAPE);
			Functions.sleep(10 + random.nextInt(5));
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			Functions.sleep(1500 + random.nextInt(500));
			
			//click more - not always a "more" on short pages
			//Point moreButton = Functions.wiggle(random, new Point(537, 964), 4, 1);
			//Functions.moveMouse(random, robot, moreButton, 0.5, 0);
			//Functions.mouseClick(robot);
			//Functions.sleep(1500 + random.nextInt(500));
			
			//scroll down (random)
			//int scrollDownAmount = 5 + random.nextInt(3);
			//Functions.mouseScroll(robot, false, scrollDownAmount, 200);
			//Functions.sleep(1500 + random.nextInt(500));
			//
			//scroll up (amt down + extra)
			//Functions.mouseScroll(robot, true, scrollDownAmount+2, 200);
			//Functions.sleep(1500 + random.nextInt(500));
			
			//scroll down 5
			//Functions.mouseScroll(robot, false, 5, 200);
			//Functions.sleep(1500 + random.nextInt(500));
			
			//click random new profile
			//int[] profileSubX = {1108, 1190, 1270};
			//int[] profileSubY = {900, 980};
			//Point profileSUB = Functions.wiggle(
			//		random, 
			//		new Point(
			//				profileSubX[random.nextInt(profileSubX.length)], 
			//				profileSubY[random.nextInt(profileSubY.length)]), 
			//		5, 5);
			//Functions.moveMouse(random, robot, profileSUB, 0.5, 0);
			//Functions.mouseClick(robot);
			
			Functions.sleep(3000 + random.nextInt(1000));
			
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			
			runTimeSum += duration;
			
			System.out.println("profileIndex: " + profileIndex);
			System.out.println("\trun duration: " + duration);
			System.out.println("\taverage run duration: " + (runTimeSum/profileIndex));
		}
	}
}
