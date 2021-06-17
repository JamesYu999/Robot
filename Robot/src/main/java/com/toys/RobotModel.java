package com.toys;

import java.util.Arrays;
import java.util.List;

public class RobotModel {

	public static final String NORTH = "NORTH";
	public static final String SOUTH = "SOUTH";
	public static final String EAST = "EAST";
	public static final String WEST = "WEST";
	public static List<String> directions = Arrays.asList(NORTH, EAST, SOUTH, WEST);
	
	private int xpos = 1;
	private int ypos = 1;
	private int facing = 0;
	private boolean isPlaced = false;
	
	public RobotModel() {
		
	}
	
	public RobotModel(int xpos, int ypos, int facing) {
		super();
		this.xpos = xpos;
		this.ypos = ypos;
		this.facing = facing;
		this.isPlaced = true;
	}
	

	public int getXpos() {
		return xpos;
	}

	public void setXpos(int xpos) {
		this.xpos = xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public int getFacing() {
		return facing;
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}

	public boolean isPlaced() {
		return isPlaced;
	}

	public void setPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}

	@Override
	public String toString() {
		return "Current position: [xpos = " + xpos + ", ypos = " + ypos + ", facing = " + getFacingDesc() + ", isPlaced = " + isPlaced + "]";
	}
	
	private String getFacingDesc() {
		return directions.get(getFacingNumber());
	}
	
	public int getFacingNumber() {
		int reminder = facing % 4;
		if (reminder < 0) {
			reminder += 4;
		}
		return reminder;
	}
}
