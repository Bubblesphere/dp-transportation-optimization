package com.dynamic;

public class ScenarioResult {
	private Scenario _scenario;
	private int _remainingSeats;
	private int _price;
	private int[] _vehiculeDistribution;

	public ScenarioResult(Scenario scenario, Dp dp) {
		_scenario = scenario;
		_remainingSeats = 0;
		_price = getLowestPrice(dp);
		_vehiculeDistribution = buildVehiculeDistribution(dp);
	}
	
	private int[] buildVehiculeDistribution(Dp dp) {
		// Chaque entrée du dp est formatté comme suis: [véhiculeUtiliséPourCeRendreÀCetteIndex, CoutActuel]
		// Ainsi avec un dp tel {[-1, 0],[1, 10],[1, 13],[2, 23]} avec un véhicule 1 (10$/1 personne), et un véhicule 2 (13$/2 personnes)
		//
		// Exemple: Pour 3 personnes, on débute avec dp[3] = [2, 23]
		// On sait qu'un véhicule de type 2 est d'abord nécessaire. 
		// On sait qu'un véhicule 2 peut transporter 2 personnes. Ainsi, pour obtenir le prochain véhicule nécessaire,
		// On doit vérifier le véhicule utilisé à dp[3-2]. On sait qu'un véhicule de type 1 est aussi nécessaire.
		// On sait qu'un véhicule 1 peut transporter 1 personne. Ainsi, pour obtenir le prochain véhicule nécessaire,
		// On doit vérifier le véhicule utilisé à dp[1-1]. On se rend compte que c'est la fin et qu'aucune place supplémentaire est disponible
		// Résultat: 1 véhicule de type 1 et 1 véhicule de type 2
		//
		// worst case: O(p) 
		long startTime = System.currentTimeMillis();
		int[] vehiculeDistribution = new int[_scenario.vehicules().length];
		vehiculeDistribution[dp.lastVehiculeIndexAtIndex(_scenario.capita())]++;
		int remainingCapita = _scenario.capita();
		// Continuer jusqu'à ce que tout les passagers ont été transportés
		while (remainingCapita > 0) {
		  Vehicule v = _scenario.vehicules()[dp.lastVehiculeIndexAtIndex(remainingCapita)];
		  
		  // Vérifier s'il est temps de calculer le nombre de place disponible
		  if (remainingCapita-v.capacity() < 1) {
			  _remainingSeats = v.capacity() - remainingCapita;
		  } else {
			  // Incrémenter le nombre de véhicule cur à utiliser
			  int cur = dp.lastVehiculeIndexAtIndex(remainingCapita - v.capacity());
			  vehiculeDistribution[cur]++;
		  }
		  
		  // Préparation du prochain index de dp à vérifier
		  remainingCapita -= v.capacity(); 
		}
		long endTime = System.currentTimeMillis();
		if (Main.viewBenchmark) {
			System.out.println("Temps pour trouver la distribution de véhicules du cas ci-dessous: " + (endTime - startTime) + "ms");
		}
		return vehiculeDistribution;
	}
	
	private int getLowestPrice(Dp dp) {
		long startTime = System.currentTimeMillis();
		// O(NP) where n = nombre de véhicules différent et p = nombre de passager
		for(int c = 1; c <= _scenario.capita(); c++) {
			for (int v = 0; v < _scenario.vehicules().length; v++) {
				int vP = _scenario.vehicules()[v].price();
				int vC = _scenario.vehicules()[v].capacity();
				
				// c - vC, tout en s'assurant que le résultat soit au moins 0, 
				// nous donne le coût le plus efficace précédemment calculé
				// si on fini par prendre le véhicule v
				int capitaAvantVehiculeCourant = (vC < c) ? c - vC : 0;
				int coutTotal = vP + dp.dp[capitaAvantVehiculeCourant].price();
			    
			    // dp[c-vC] + vP < dp[c] ?
			    if (coutTotal < dp.dp[c].price()) {
			      dp.dp[c] = new DpEntry(coutTotal, v);
			    }
			}
		}
		long endTime = System.currentTimeMillis();
		if (Main.viewBenchmark) {
			System.out.println("Temps pour trouver le prix du cas ci-dessous: " + (endTime - startTime) + "ms");
		}
		
		// Le prix à l'index _scenario.capita() nous donne le prix le plus petit
		// pour le nombre de personnes à transporter
		return dp.dp[_scenario.capita()].price();
	}
	
	public void price(int amount) {
		_price = amount;
	}
	
	public void print() {
		String peoplePlural = _scenario.capita() > 1 ? "s" : "";
		String r = String.format("Le coût total pour transporter %s personne%s est de %s $.", _scenario.capita(), peoplePlural, _price);

		int first = firstIndexToPrint();
		int last = lastIndexToPrint();
		for (int i = first; i <= last; i++) {
			if (_vehiculeDistribution[i] > 0) {
				String sentenceStart = i == first ? " Il faut louer " : " et ";
				String vehiculePlural = _vehiculeDistribution[i] > 1 ? "s" : "";
				String endPunctuation = i == last ? "." : "";
				r += String.format("%s%s véhicule%s de catégorie %s%s", sentenceStart, _vehiculeDistribution[i], vehiculePlural, i, endPunctuation);
			}
		}
		
		if (_remainingSeats == 0) {
			r += " Il n'y a pas de places libres disponibles.";
		} else if (_remainingSeats == 1) {
			r += String.format(" Il y aura une place libre disponible.");
		} else {
			r += String.format(" Il y aura %s places libres disponibles.", _remainingSeats);
		}
		
		System.out.println(r);
	}
	
	private int firstIndexToPrint() {
		for(int i = 0; i < _vehiculeDistribution.length; i++) {
			if (_vehiculeDistribution[i] > 0) {
				return i;
			}
		}
		return -1;
	}
	
	private int lastIndexToPrint() {
		for(int i = _vehiculeDistribution.length - 1; i >= 0; i--) {
			if (_vehiculeDistribution[i] > 0) {
				return i;
			}
		}
		return -1;
	}
}
