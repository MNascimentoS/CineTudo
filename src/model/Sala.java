package cinema;

public class Sala {
	private int numSala, numAssentos;
	private Sessoes sessoes;
	private String tipo;
	private float valor;
	/*************************************************/
	public Sala(){
		
	}
	
	
	
	
	
	/*************************************************/
	/*              Getters and Setters              */
	/*************************************************/
	public int getNumSala() {
		return numSala;
	}
	public void setNumSala(int numSala) {
		this.numSala = numSala;
	}
	public int getNumAssentos() {
		return numAssentos;
	}
	public void setNumAssentos(int numAssentos) {
		this.numAssentos = numAssentos;
	}
	public Sessoes getSessoes() {
		return sessoes;
	}
	public void setSessoes(Sessoes sessoes) {
		this.sessoes = sessoes;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
}
