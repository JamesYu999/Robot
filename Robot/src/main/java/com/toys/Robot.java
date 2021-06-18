package com.toys;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Robot {


	private static final String PLACE = "PLACE";
	private static final int xBoundary = 4;
	private static final int yBoundary = 4;
	
	private static final String COMMAND_PLACE = "PLACE\\s[0|1|2|3|4],[0|1|2|3|4],(NORTH|SOUTH|EAST|WEST)";
	private static final String COMMAND_MOVE = "MOVE";
	private static final String COMMAND_LEFT = "LEFT";
	private static final String COMMAND_RIGHT = "RIGHT";
	private static final String COMMAND_REPORT = "REPORT";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_QUIT = "q";
	
	private static final String VALID_COMMAND = "(" + COMMAND_HELP + "|" + COMMAND_MOVE + "|" + COMMAND_LEFT + "|" 
			+ COMMAND_RIGHT + "|" + COMMAND_REPORT + "|" + COMMAND_PLACE + ")";
	
	private Set<String> directionSet = new HashSet<String>();
	private Set<String> nonPlaceCommandSet = new HashSet<String>();
	
	private Pattern pattern;
	
	private RobotModel robotModel;
	
	
	public Robot() {
		init();
		
	}
	
	private void init() {
		
		robotModel = new RobotModel();
		
		directionSet.add(RobotModel.NORTH);
		directionSet.add(RobotModel.SOUTH);
		directionSet.add(RobotModel.EAST);
		directionSet.add(RobotModel.WEST);
		
		nonPlaceCommandSet.add(COMMAND_MOVE);
		nonPlaceCommandSet.add(COMMAND_LEFT);
		nonPlaceCommandSet.add(COMMAND_RIGHT);
		nonPlaceCommandSet.add(COMMAND_REPORT);
		
		pattern = Pattern.compile(VALID_COMMAND, Pattern.CASE_INSENSITIVE);
		
	}
	
	public static void main(String[] args) {
		
		Robot robot = new Robot();

		Scanner scanner = new Scanner(System.in);
        System.out.println(ResourceUtil.getMessage("PLEASE_ENTER_COMMAND_"));
        System.out.println(ResourceUtil.getMessage("HELP_FOR_HELP"));
        System.out.println(ResourceUtil.getMessage("Q_TO_QUIT"));
        String input = "";
        while (!COMMAND_QUIT.equalsIgnoreCase(input)) {
        	
    		input = scanner.nextLine();
    		
			if (input != null && !input.trim().equals("") && !input.equalsIgnoreCase(COMMAND_QUIT)) {
				robot.process(input.trim());
			}
			
        }
        System.out.println(ResourceUtil.getMessage("BYE"));
        scanner.close();
	}
	
	public void process(String command) {
		
		if (!commandCheck(command)) {
			System.out.println(ResourceUtil.getMessage("INVALID_COMMAND"));
			return;
		}
		// Now we will process valid command
		if (command.equalsIgnoreCase(COMMAND_HELP)) {
			printHelp();
		} else {
			processCommand(command.toUpperCase());
		}
		
	}
	
	private boolean commandCheck(String command) {
		boolean valid = false;
		if (command != null && !command.trim().equals("")) {
			Matcher matcher = pattern.matcher(command);
			valid = matcher.matches();
		}
		return valid;
		
	}
	
	private void processCommand(String command) {
		
		if (nonPlaceCommandSet.contains(command) && !robotModel.isPlaced()) {
			System.out.println(ResourceUtil.getMessage("PLEASE_ENTER_PLACE_COMMAND_FIRST"));
			return;
		} 
		
		switch (command) {
		case COMMAND_LEFT:
			robotModel.setFacing(robotModel.getFacing() - 1);
			break;
		case COMMAND_RIGHT:
			robotModel.setFacing(robotModel.getFacing() + 1);
			break;
		case COMMAND_MOVE:
			move();
			break;
		case COMMAND_REPORT:
			report();
			break;
		default:
			place(command);
		}
		
	}
	
	private void move() {
		
		if (robotModel.getFacingNumber() == 0) {
			if (robotModel.getYpos() < yBoundary) {
				robotModel.setYpos(robotModel.getYpos() + 1);
			} else {
				System.out.println(ResourceUtil.getMessage("OUT_Of_Y_BOUNDARY"));
			}
		} else if (robotModel.getFacingNumber() == 1) {
			if (robotModel.getXpos() < xBoundary) {
				robotModel.setXpos(robotModel.getXpos() + 1);
			} else {
				System.out.println(ResourceUtil.getMessage("OUT_Of_X_BOUNDARY"));
			}
		} else if (robotModel.getFacingNumber() == 2) {
			if (robotModel.getYpos() > 0) {
				robotModel.setYpos(robotModel.getYpos() - 1);
			} else {
				System.out.println(ResourceUtil.getMessage("OUT_Of_Y_BOUNDARY"));
			}
		} else {
			if (robotModel.getXpos() > 0) {
				robotModel.setXpos(robotModel.getXpos() - 1);
			} else {
				System.out.println(ResourceUtil.getMessage("OUT_Of_X_BOUNDARY"));
			}
		}
	}
	
	private void place(String command) {
		String comm = command.toUpperCase().replaceAll(PLACE, "").trim();
		String[] parts = comm.split(",");
		int xPos = Integer.parseInt(parts[0]);
		int yPos = Integer.parseInt(parts[1]);
		
		int facingIndex = RobotModel.directions.indexOf(parts[2]);
		robotModel = new RobotModel(xPos, yPos, facingIndex);
		
	}
		
	private void report() {
		System.out.println(robotModel.toString());
	}
	
	private void printHelp() {
		System.out.println(ResourceUtil.getMessage("VALID_COMMAND_"));
		System.out.println(ResourceUtil.getMessage("PLACE_X_Y_F"));
		System.out.println(ResourceUtil.getMessage("MOVE"));
		System.out.println(ResourceUtil.getMessage("LEFT"));
		System.out.println(ResourceUtil.getMessage("RIGHT"));
		System.out.println(ResourceUtil.getMessage("REPORT"));
		
	}

}
