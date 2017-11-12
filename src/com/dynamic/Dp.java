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
	
	// R�initialise le tableau de programmation dynamique 
	// La premi�re entr�e est initialis�e � 0 puisque le prix pour 0 capita est toujours de 0
	// Les prochaines entr�es sont initialis�es � Integer.MAX_VALUE jusqu'� ce que qu'on les red�finissent
	// lors de l'ex�cution de l'algorithme de programmation dynamique
	private void reset(int capita) {
		dp = new DpEntry[capita+1];
		dp[0] = new DpEntry(true);
		for(int i = 1; i < dp.length; i++) {
			dp[i] = new DpEntry(false);
		}
	}
}
