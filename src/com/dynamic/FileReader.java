package com.dynamic;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
	// Transforme un fichier texte en une list<String> correspondant à chaque ligne
	public static List<String> GetFileLines(String path) {
		Path actualPath = FileSystems.getDefault().getPath(path);	
		List<String> lines = new ArrayList<String>();
		try {
			lines = Files.readAllLines(actualPath, StandardCharsets.UTF_8);
		} catch (Exception ex) {
			System.out.println("Erreur lors de la lecture du fichier");
		}
		return lines;
	}
}
