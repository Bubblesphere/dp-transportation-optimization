package com.dynamic;

public class Utils {
	// Crée un message d'erreur formaté
	public static String errorMessage(String errorMessage, Exception ex) {
		return "Expected " + errorMessage + ". Details: " + ex.getMessage();
	}
}
