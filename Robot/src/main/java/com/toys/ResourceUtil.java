package com.toys;

import java.util.ResourceBundle;

public class ResourceUtil {

	private static ResourceBundle messages;
	
	static {
		messages = ResourceBundle.getBundle("messages");

	}
	
	public ResourceUtil() {
		
		
	}
	
	public static String getMessage(String key) {
		
		return key == null? "" : messages.getString(key);
	}
}
