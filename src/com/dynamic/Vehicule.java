package com.dynamic;

public class Vehicule {
	private int _capacity;
	private int _price;
	
	public Vehicule(int capacity, int price) {
		_capacity = capacity;
		_price = price;
	}
	
	public int capacity() {
		return _capacity;
	}
	
	public int price() {
		return _price;
	}
}
