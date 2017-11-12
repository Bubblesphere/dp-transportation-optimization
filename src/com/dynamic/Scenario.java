package com.dynamic;

public class Scenario {
	private int _capita;
	private Vehicule[] _vehicules;
	
	public Scenario(int capita, Vehicule[] vehicules) {
		_capita = capita;
		_vehicules = vehicules;
	}
	
	public Vehicule[] vehicules() {
		return _vehicules;
	}
	
	public int capita() {
		return _capita;
	}
}
