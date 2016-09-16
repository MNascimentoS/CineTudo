package model;

public class Promocao {
	private String nome, descricao;
	private Data data;
	private int valorIngresso;
	
	public Promocao(String nome, String descricao, Data data, int valorIngresso) {
		this.nome = nome;
		this.descricao = descricao;
		this.data = data;
		this.valorIngresso = valorIngresso;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public int getValorIngresso() {
		return valorIngresso;
	}
	public void setValorIngresso(int valorIngresso) {
		this.valorIngresso = valorIngresso;
	}
}
