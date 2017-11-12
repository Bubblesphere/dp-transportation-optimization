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
		// Chaque entr�e du dp est formatt� comme suis: [v�hiculeUtilis�PourCeRendre�CetteIndex, CoutActuel]
		// Ainsi avec un dp tel {[-1, 0],[1, 10],[1, 13],[2, 23]} avec un v�hicule 1 (10$/1 personne), et un v�hicule 2 (13$/2 personnes)
		//
		// Exemple: Pour 3 personnes, on d�bute avec dp[3] = [2, 23]
		// On sait qu'un v�hicule de type 2 est d'abord n�cessaire. 
		// On sait qu'un v�hicule 2 peut transporter 2 personnes. Ainsi, pour obtenir le prochain v�hicule n�cessaire,
		// On doit v�rifier le v�hicule utilis� � dp[3-2]. On sait qu'un v�hicule de type 1 est aussi n�cessaire.
		// On sait qu'un v�hicule 1 peut transporter 1 personne. Ainsi, pour obtenir le prochain v�hicule n�cessaire,
		// On doit v�rifier le v�hicule utilis� � dp[1-1]. On se rend compte que c'est la fin et qu'aucune place suppl�mentaire est disponible
		// R�sultat: 1 v�hicule de type 1 et 1 v�hicule de type 2
		//
		// worst case: O(p) 
		long startTime = System.currentTimeMillis();
		int[] vehiculeDistribution = new int[_scenario.vehicules().length];
		vehiculeDistribution[dp.lastVehiculeIndexAtIndex(_scenario.capita())]++;
		int remainingCapita = _scenario.capita();
		// Continuer jusqu'� ce que tout les passagers ont �t� transport�s
		while (remainingCapita > 0) {
		  Vehicule v = _scenario.vehicules()[dp.lastVehiculeIndexAtIndex(remainingCapita)];
		  
		  // V�rifier s'il est temps de calculer le nombre de place disponible
		  if (remainingCapita-v.capacity() < 1) {
			  _remainingSeats = v.capacity() - remainingCapita;
		  } else {
			  // Incr�menter le nombre de v�hicule cur � utiliser
			  int cur = dp.lastVehiculeIndexAtIndex(remainingCapita - v.capacity());
			  vehiculeDistribution[cur]++;
		  }
		  
		  // Pr�paration du prochain index de dp � v�rifier
		  remainingCapita -= v.capacity(); 
		}
		long endTime = System.currentTimeMillis();
		if (Main.viewBenchmark) {
			System.out.println("Temps pour trouver la distribution de v�hicules du cas ci-dessous: " + (endTime - startTime) + "ms");
		}
		return vehiculeDistribution;
	}
	
	private int getLowestPrice(Dp dp) {
		long startTime = System.currentTimeMillis();
		// O(NP) where n = nombre de v�hicules diff�rent et p = nombre de passager
		for(int c = 1; c <= _scenario.capita(); c++) {
			for (int v = 0; v < _scenario.vehicules().length; v++) {
				int vP = _scenario.vehicules()[v].price();
				int vC = _scenario.vehicules()[v].capacity();
				
				// c - vC, tout en s'assurant que le r�sultat soit au moins 0, 
				// nous donne le co�t le plus efficace pr�c�demment calcul�
				// si on fini par prendre le v�hicule v
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
		
		// Le prix � l'index _scenario.capita() nous donne le prix le plus petit
		// pour le nombre de personnes � transporter
		return dp.dp[_scenario.capita()].price();
	}
	
	public void price(int amount) {
		_price = amount;
	}
	
	public void print() {
		String peoplePlural = _scenario.capita() > 1 ? "s" : "";
		String r = String.format("Le co�t total pour transporter %s personne%s est de %s $.", _scenario.capita(), peoplePlural, _price);

		int first = firstIndexToPrint();
		int last = lastIndexToPrint();
		for (int i = first; i <= last; i++) {
			if (_vehiculeDistribution[i] > 0) {
				String sentenceStart = i == first ? " Il faut louer " : " et ";
				String vehiculePlural = _vehiculeDistribution[i] > 1 ? "s" : "";
				String endPunctuation = i == last ? "." : "";
				r += String.format("%s%s v�hicule%s de cat�gorie %s%s", sentenceStart, _vehiculeDistribution[i], vehiculePlural, i, endPunctuation);
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
