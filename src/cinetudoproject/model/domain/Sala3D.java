package cinetudoproject.model.domain;

public class Sala3D extends Sala{

	public Sala3D(int numero, int capacidade, float precoIngresso) {
		super(numero, capacidade, precoIngresso);
		// TODO Auto-generated constructor stub
	}
	@Override
	public float getPrecoIngresso() {
		// adicional de 20% no valor do ingresso
		return (float) (precoIngresso * 0.20) + precoIngresso;
	}
}