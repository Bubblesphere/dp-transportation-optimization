package com.dynamic;

public class Dp {
	public DpEntry[] dp;
	
	public Dp() {
	}
	
	public int lastVehiculeIndexAtIndex(int index) {
		return dp[index].lastVehiculeIndex();
	}
	
	public void init(int capita) {
		reset(capita);
	}
	
	// Réinitialise le tableau de programmation dynamique 
	// La première entrée est initialisée à 0 puisque le prix pour 0 capita est toujours de 0
	// Les prochaines entrées sont initialisées à Integer.MAX_VALUE jusqu'à ce que qu'on les redéfinissent
	// lors de l'exécution de l'algorithme de programmation dynamique
	private void reset(int capita) {
		dp = new DpEntry[capita+1];
		dp[0] = new DpEntry(true);
		for(int i = 1; i < dp.length; i++) {
			dp[i] = new DpEntry(false);
		}
	}
}
