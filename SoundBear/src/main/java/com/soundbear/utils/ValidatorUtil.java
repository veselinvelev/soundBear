package com.soundbear.utils;

public class ValidatorUtil {

	public static boolean isStringValid(String str) {

		return ((str != null) && (str.length() > 0));
	}
}
