package cinetudoproject.model.domain;

public class Sala2D extends Sala{

	public Sala2D(int numero, int capacidade, float precoIngresso) {
		super(numero, capacidade, precoIngresso);
	}

	@Override
	public float getPrecoIngresso() {
		// TODO Auto-generated method stub
		return precoIngresso;
	} 	
}
