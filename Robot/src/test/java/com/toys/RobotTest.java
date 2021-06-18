package com.toys;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
 
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RobotTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	Robot robot;

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    robot = new Robot();
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	}

	@Test
	public void testProcess1() {
		robot.process("PLACE 0,0,NORTH");
		robot.process("MOVE");
		robot.process("REPORT");

		assertEquals("Current position: [xpos = 0, ypos = 1, facing = NORTH]\r\n", outContent.toString());
	}
	
	@Test
	public void testProcess2() {
		robot.process("PLACE 0,0,NORTH");
		robot.process("LEFT");
		robot.process("REPORT");

		assertEquals("Current position: [xpos = 0, ypos = 0, facing = WEST]\r\n", outContent.toString());
	}
	
	@Test
	public void testProcess3() {
		robot.process("PLACE 1,2,EAST");
		robot.process("MOVE");
		robot.process("MOVE");
		robot.process("LEFT");
		robot.process("MOVE");
		robot.process("REPORT");

		assertEquals("Current position: [xpos = 3, ypos = 3, facing = NORTH]\r\n", outContent.toString());
	}

	@Test
	public void testProcessInvalidCommand1() {
		robot.process("PLACE 0,0,Middle");
		assertEquals("Invalid command\r\n", outContent.toString());
	}
	
	@Test
	public void testProcessInvalidCommand2() {
		robot.process("MOVE");
		assertEquals("Please enter place command first[PLACE X,Y,F]\r\n", outContent.toString());
	}
	
	@Test
	public void testProcessInvalidCommand3() {
		robot.process("PLACE 5,0,EAST");
		assertEquals("Invalid command\r\n", outContent.toString());
	}
	
	@Test
	public void testProcessOutOfXBoundary() {
		robot.process("PLACE 4,2,EAST");
		robot.process("MOVE");

		assertEquals("Out of X boundary\r\n", outContent.toString());
	}
	
	@Test
	public void testProcessOutOfYBoundary() {
		robot.process("PLACE 1,4,NORTH");
		robot.process("MOVE");

		assertEquals("Out of Y boundary\r\n", outContent.toString());
	}
	
	@Test
	public void testProcessHelp() {
		robot.process("help");

		assertEquals("Valid command:\r\n" + "PLACE X,Y,F\r\n" + "MOVE\r\n" + "LEFT\r\n" + "RIGHT\r\n" + "REPORT\r\n", outContent.toString());
	}
}
