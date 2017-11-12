package com.dynamic;

public class DpEntry {
	private int _price;
	private int _lastVehiculeIndex;
	
	public DpEntry(boolean isFirst) {
		// Voir détails Dp.java
		_price = isFirst ? 0 : Integer.MAX_VALUE;
		_lastVehiculeIndex = -1;
	}
	
	public DpEntry(int price, int lastVehiculeIndex) {
		_price = price;
		_lastVehiculeIndex = lastVehiculeIndex;
	}
	
	public int price() {
		return _price;
	}
	
	public int lastVehiculeIndex() {
		return _lastVehiculeIndex;
	}
}
