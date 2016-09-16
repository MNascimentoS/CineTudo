package model;

import java.util.ArrayList;
import java.util.Calendar;

/* CineTudo						
 * 
 * Project Created by:
 * 		- José Diôgo
 * 		- Mateus Nascimento
 * 		- Wellington Correia
 * 								*/

public class Cinema {
	
	private String nome, endereco, cpnj;
	private ArrayList<Filme> filmes = new ArrayList<>();
	private ArrayList<Sessao> sessoes = new ArrayList<>();
	private ArrayList<Sala> salas = new ArrayList<>();
	private ArrayList<Venda> vendas = new ArrayList<>();
	private float valorSemana, valorFDS, valorIngresso;
	
	public Cinema(String nome, String endereco, String cnpj, float valorSemana, float valorFDS){
		this.nome = nome;
		this.endereco = endereco;
		this.cpnj = cnpj;
		this.valorSemana = valorSemana;
		this.valorFDS = valorFDS;
	}

	public float getValorIngresso() {
		return valorIngresso;
	}

	public void setValorIngresso(float valorIngresso) {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		/*caso finais de semana - adote o preco do final de semana*/
		if(day == 1 || day == 7) this.valorIngresso = valorFDS;
		else this.valorIngresso = valorSemana;
	}

	public float getValorSemana() {
		return valorSemana;
	}

	public void setValorSemana(float valorSemana) {
		this.valorSemana = valorSemana;
	}

	public float getValorFDS() {
		return valorFDS;
	}

	public void setValorFDS(float valorFDS) {
		this.valorFDS = valorFDS;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCpnj() {
		return cpnj;
	}

	public void setCpnj(String cpnj) {
		this.cpnj = cpnj;
	}

	public ArrayList<Filme> getFilmes() {
		return filmes;
	}

	public void addFilme(Filme filme) {
		this.filmes.add(filme);
	}

	public ArrayList<Sessao> getSessoes() {
		return sessoes;
	}

	public void addSessao(Sessao sessao) {
		this.sessoes.add(sessao);
	}

	public ArrayList<Sala> getSalas(){
		return salas;
	}

	public void addSala(Sala sala) {
		this.salas.add(sala);
	}
}
