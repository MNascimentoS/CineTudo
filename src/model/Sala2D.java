package model;

public class Sala2D extends Sala{

	public Sala2D(int numero, int capacidade, float precoIngresso) {
		super(numero, capacidade, precoIngresso);
	}

	@Override
	float getPrecoIngresso() {
		// TODO Auto-generated method stub
		return precoIngresso;
	} 	
}
