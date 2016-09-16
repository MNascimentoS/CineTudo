package model;

public class Promocao {
	private String nome, descricao;
	private int diaDaSemana;
	private float desconto;
	
	public Promocao(String nome, String descricao,  int diaDaSemana, float desconto) {
		this.nome = nome;
		this.descricao = descricao;
		this.diaDaSemana = diaDaSemana;
		this.desconto = desconto;
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
	public int getDiaDaSemana() {
		return this.diaDaSemana;
	}
	public void setDiaDaSemana(int diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}
	
	public float getDesconto() {
		return desconto;
	}
	public void setDesconto(float desconto) {
		this.desconto = desconto;
	}
}
