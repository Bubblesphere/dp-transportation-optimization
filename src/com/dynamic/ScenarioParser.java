package com.dynamic;

import java.util.List;

public class ScenarioParser {
	private static int lineCursor = 0;
	
	public static Scenario[] parseFromFileContent(List<String> lines) {
		// Premi�rement, on cr�e un tableau des n sc�narios pr�sents dans le fichier
		Scenario[] scenarios = new Scenario[retrieveNumberOfScenarios(lines.get(lineCursor))];
		
		for(int i = 0; i < scenarios.length; i++) {
			// La premi�re ligne d'un sc�nario contient le nombre de v�hicule unique
			int nbVehicules = retrieveNumberOfVehicules(lines.get(lineCursor));
			
			// Les prochaines lignes correspondent aux d�tails de chaque v�hicule
			Vehicule vehicules[] = retrieveAllVehicules(lines.subList(lineCursor, lineCursor + nbVehicules));
			
			// La derni�re ligne d'un sc�nario contient le nombre de personne � transporter
			int capita = retrieveCapita(lines.get(lineCursor));
			
			scenarios[i] = new Scenario(capita, vehicules);
		}

		return scenarios;
	}
	
	private static int retrieveNumberOfScenarios(String line) {
		try {
			int nbScenarios = Integer.parseInt(line);
			++lineCursor;
			return nbScenarios;
		} catch (Exception ex) {
			System.err.println(Utils.errorMessage("an integer on line " + lineCursor, ex));
			System.exit(1);
		}
		return 0;
	}
	
	private static int retrieveNumberOfVehicules(String line) {
		final int MAX_NB_VEHICULE = 10;
		try {
			int nbVehicule = Integer.parseInt(line);
			if (nbVehicule >= MAX_NB_VEHICULE) {
				throw new Exception();
			}
			++lineCursor;
			return nbVehicule;
		} catch (Exception ex) {
			System.err.println(Utils.errorMessage("an integer lower than " + MAX_NB_VEHICULE + "  on line " + lineCursor, ex));
			System.exit(1);
		}
		return 0;
	}
	
	private static Vehicule[] retrieveAllVehicules(List<String> lines) {
		Vehicule vehicules[] = new Vehicule[lines.size()];
		for(int i = 0; i < lines.size(); i++) {
			vehicules[i] = getVehiculeFromLine(lines.get(i));
			++lineCursor;
		}
		return vehicules;
	}
		
	private static Vehicule getVehiculeFromLine(String line) {
		String[] parts = line.split("\\s+");
		
		try {
			return new Vehicule(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		} catch (IndexOutOfBoundsException|NumberFormatException ex) {
			System.err.println(Utils.errorMessage("2 space-delimited integers on line " + lineCursor, ex));
			System.exit(1);
		}
		return null;
	}
	
	private static int retrieveCapita(String line) {
		try {
			int capita = Integer.parseInt(line);
			++lineCursor;
			return capita;
		} catch (NumberFormatException ex) {
			System.err.println(Utils.errorMessage("an integer on line " + lineCursor, ex));
			System.exit(1);
		}
		return 0;
	}
}
