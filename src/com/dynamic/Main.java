package com.dynamic;

import java.util.List;

public class Main {
	// Affiche la vitesse d'exécution
	public static boolean viewBenchmark = true;

	public static void main(String[] args) {
		
		List<String> lines = FileReader.GetFileLines("Config.txt");
		Scenario[] scenarios = ScenarioParser.parseFromFileContent(lines);
		Dp dp = new Dp();
		
		long startTime = System.currentTimeMillis();
		for (Scenario scenario : scenarios) {
			dp.init(scenario.capita());
			ScenarioResult result = new ScenarioResult(scenario, dp);
			result.print();
		}
		long endTime = System.currentTimeMillis();
		if (viewBenchmark) {
			System.out.println("Temps total: " + (endTime - startTime) + "ms");
		}
	}
}
