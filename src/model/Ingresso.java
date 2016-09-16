package model;

public class Ingresso {
	
	final int MEIA = 0;
	
	private float preco;
	private int tipo;
	private Sessao sessao;
	
	public Ingresso(int tipo, Sessao sessao){
		this.tipo = tipo;
		this.sessao = sessao;
		setPreco(this.sessao.getValorSessao());
	}
	
	public float getPreco(){
		return preco;
	}
	public void setPreco(float preco) {
		if(tipo == MEIA) this.preco = preco / 2;
		else this.preco = preco;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}	
}
