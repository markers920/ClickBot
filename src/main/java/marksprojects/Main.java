package marksprojects;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {

	private static Random random = new Random(0);
	private static Robot robot;
	
	private static int[] DIGIT_KEYS = {
		KeyEvent.VK_0,
		KeyEvent.VK_1,
		KeyEvent.VK_2,
		KeyEvent.VK_3,
		KeyEvent.VK_4,
		KeyEvent.VK_5,
		KeyEvent.VK_6,
		KeyEvent.VK_7,
		KeyEvent.VK_8,
		KeyEvent.VK_9,
	};
	
	public static void main(String[] args) throws AWTException, InterruptedException, UnsupportedFlavorException, IOException {
		robot = new Robot();
		
		//initial wait to allow window shift 
		int initialSleepTime = 5;
		System.out.println(String.format("wait %d seconds", initialSleepTime));
		for(int sleepIndex = 1; sleepIndex <= initialSleepTime; sleepIndex++) {
			System.out.println("\t" + sleepIndex);
			Functions.sleep(1000);
		}
		
		//click logo
		Point logo = Functions.wiggle(random, new Point(55, 197), 3, 2);
		Functions.moveMouse(random, robot, logo, 1, 0);
		Functions.mouseClick(robot);
		Functions.sleep(1500 + random.nextInt(500));
		
		
		double runTimeSum = 0.0;
		int numberLiked = 0;
		Set<String> usersVisited = new HashSet<String>();
		for(int profileIndex = 1; true; profileIndex++) {
			long startTime = System.currentTimeMillis();
			
			boolean resetSettings = false;
			if(random.nextDouble() < 0.10) {
				System.out.println("adjusting settings");
				adjustSettings();
				resetSettings = true;
			}
			
			getNewProfile(resetSettings || profileIndex == 1);
			
			//get username and stats
			String copyString = Functions.copyText(random, robot, new Point(977, 404), new Point(670, 354), 5);
			//System.out.println("copyString: " + copyString);
			String[] copySplit1 = copyString.split("\n");
			String userName = copySplit1[0].trim();
			String[] copySplit2 = copySplit1[1].split("•");
			int age = Integer.parseInt(copySplit2[0]);
			//String location = copySplit2[1];
			int percentage = Integer.parseInt(copySplit2[2].substring(1, 3));
			//System.out.println("\tusername: " + userName);
			//System.out.println("\tage: " + age);
			//System.out.println("\tlocation: " + location);
			//System.out.println("\tpercentage: " + percentage);
			
			usersVisited.add(userName);
			
			if(percentage > 90 || (percentage > 70 && age < 22)) {
				Color color = robot.getPixelColor(1162, 379);
				//java.awt.Color[r=255,g=217,b=57]
				if(Math.abs(color.getRed()-255) < 5 && 
						Math.abs(color.getGreen()-217) < 5 && 
						Math.abs(color.getBlue()-57) < 5) {
					//then already liked!
				} else {
					Point likeButton = Functions.wiggle(random, new Point(1200, 375), 5, 2);
					Functions.moveMouse(random, robot, likeButton, 1, 0);
					Functions.mouseClick(robot);
					Functions.sleep(1500 + random.nextInt(500));
					System.out.println("================================================");
					System.out.println("LIKED: " + userName);
					System.out.println("================================================");
					numberLiked++;
				}
			}
			
			//click photo
			Point profilePhoto = Functions.wiggle(random, new Point(580, 376), 5, 5);
			Functions.moveMouse(random, robot, profilePhoto, 1, 0);
			Functions.mouseClick(robot);
			Functions.sleep(1500 + random.nextInt(500));
			
			//press right/left random combo
			int pictureCount = 0 + random.nextInt(3);
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
				Functions.sleep(1000 + random.nextInt(500));
			}
			
			//press escape
			robot.keyPress(KeyEvent.VK_ESCAPE);
			Functions.sleep(10 + random.nextInt(5));
			robot.keyRelease(KeyEvent.VK_ESCAPE);
			Functions.sleep(1500 + random.nextInt(500));
			
			
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			
			runTimeSum += duration;
			
			if(profileIndex % 20 == 0) {
				System.out.println("profileIndex: " + profileIndex);
				System.out.println("\trun duration: " + duration);
				System.out.println("\taverage run duration: " + (runTimeSum/profileIndex));
				System.out.println("\tunique users visited: " + usersVisited.size() + "\t" + ((double)usersVisited.size()/profileIndex));
				System.out.println("\tnumber liked: " + numberLiked);
			}
		}
	}
	
	//click browse matches
	private static void clickBrowseMatches() throws InterruptedException {
		Point browseMatches = Functions.wiggle(random, new Point(182, 204), 3, 2);
		Functions.moveMouse(random, robot, browseMatches, 1, 0);
		Functions.mouseClick(robot);
		Functions.sleep(1500 + random.nextInt(500));
	}
	
	private static void getNewProfile(boolean goToBrowse) throws InterruptedException {
		if(goToBrowse) {
			System.out.println("getNewProfile orig");
			clickBrowseMatches();
			
			//click profile
			int[] profileMainX = {652, 943, 1240};
			int[] profileMainY = {500, 920};
			Point profileMain = Functions.wiggle(
					random, 
					new Point(
							profileMainX[random.nextInt(profileMainX.length)], 
							profileMainY[random.nextInt(profileMainY.length)]), 
					5, 5);
			Functions.moveMouse(random, robot, profileMain, 1, 0);
			Functions.mouseClick(robot);
			Functions.sleep(1500 + random.nextInt(500));
		} else {
			//System.out.println("getNewProfile recommended");
			//scroll to the bottom
			//System.out.println("scroll down");
			for(int i = 0; i < 10; i++) {
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				Functions.sleep(10);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
				Functions.sleep(200);
			}
			
			//move to white just above blue banner
			//System.out.println("move to bottom");
			int[] profileSubX = {1110, 1190, 1270, 1350,};
			int profileSubY = 940;
			Point position = new Point(profileSubX[random.nextInt(profileSubX.length)], profileSubY);
			Functions.moveMouse(random, robot, position, 1, 0);
			//1110 1190 1270 1350
			//940
			
			//go up until find non white
			//java.awt.Color[r=250,g=251,b=253]
			Color color = robot.getPixelColor(position.x, position.y);
			//System.out.println("crawl up");
			while(Math.abs(color.getRed()-250) < 5 && 
					Math.abs(color.getGreen()-251) < 5 && 
					Math.abs(color.getBlue()-253) < 5) {
				
				position = new Point(position.x, position.y-10);
				Functions.moveMouse(random, robot, position, 1, 0);
				color = robot.getPixelColor(position.x, position.y);
			}
			
			Functions.mouseClick(robot);
			Functions.sleep(1000 + random.nextInt(1000));
		}
	}
	
	private static void adjustSettings() throws InterruptedException {
		clickBrowseMatches();
		
		if(random.nextDouble() < 0.95) {
			//click the age option
			Functions.moveMouse(random, robot, new Point(830, 270), 1, 0);
			Functions.mouseClick(robot);
			Functions.sleep(1500 + random.nextInt(500));
			
			//lower age
			Functions.moveMouse(random, robot, new Point(790, 365), 1, 0);
			Functions.mouseClick(robot);
			Functions.sleep(1500 + random.nextInt(500));
			
			for(int i = 0; i < 2; i++) {
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				Functions.sleep(10);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				Functions.sleep(500);
			}
			
			int lowerAge = 18 + random.nextInt(7);
			
			int tensPlace = DIGIT_KEYS[lowerAge / 10];
			robot.keyPress(tensPlace);
			Functions.sleep(10);
			robot.keyRelease(tensPlace);
			Functions.sleep(500);
			
			int onesPlace = DIGIT_KEYS[lowerAge % 10];
			robot.keyPress(onesPlace);
			Functions.sleep(10);
			robot.keyRelease(onesPlace);
			Functions.sleep(500);
			
			
			//upper age
			Functions.moveMouse(random, robot, new Point(900, 365), 1, 0);
			Functions.mouseClick(robot);
			Functions.sleep(1500 + random.nextInt(500));
			
			for(int i = 0; i < 2; i++) {
				robot.keyPress(KeyEvent.VK_BACK_SPACE);
				Functions.sleep(10);
				robot.keyRelease(KeyEvent.VK_BACK_SPACE);
				Functions.sleep(500);
			}
			
			int upperAge = Math.min(lowerAge+12, 40);
			
			tensPlace = DIGIT_KEYS[upperAge / 10];
			robot.keyPress(tensPlace);
			Functions.sleep(10);
			robot.keyRelease(tensPlace);
			Functions.sleep(500);
			
			onesPlace = DIGIT_KEYS[upperAge % 10];
			robot.keyPress(onesPlace);
			Functions.sleep(10);
			robot.keyRelease(onesPlace);
			Functions.sleep(500);
		}
		
		if(random.nextDouble() < 0.95) {
			//click distance
			Functions.moveMouse(random, robot, new Point(950, 270), 1, 0);
			Functions.mouseClick(robot);
			Functions.sleep(1500 + random.nextInt(500));
			
			if(random.nextDouble() < 0.7) {	//10 miles
				Functions.moveMouse(random, robot, new Point(950, 360), 1, 0);
				Functions.mouseClick(robot);
				Functions.sleep(1500 + random.nextInt(500));
			}
			else {		//25 miles
				Functions.moveMouse(random, robot, new Point(1000, 360), 1, 0);
				Functions.mouseClick(robot);
				Functions.sleep(1500 + random.nextInt(500));
			}
		}
		
	}
}
