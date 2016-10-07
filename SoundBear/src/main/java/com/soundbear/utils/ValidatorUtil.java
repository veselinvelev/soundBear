package com.soundbear.utils;

public class ValidatorUtil {
	
	public static boolean isStringValid(String str) {
		boolean isValid;
		if (str != null && str.length() > 0) {
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}
}
