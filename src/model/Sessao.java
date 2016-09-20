package model;

import java.util.ArrayList;

/**
 * Created by diogo on 20/08/16.
 */
public class Sessao {

	private Filme filme;
	private Sala sala;
	private Horario horario;
	private ArrayList<Assento> assentos = new ArrayList<>();
    private Data data;
    private float valorSessao;
	
    public Sessao(Filme filme, Sala sala, Horario horario, Data data, float valorSessao) {
		this.filme = filme;
		this.sala = sala;
		this.horario = horario;
		this.data = data;
		this.valorSessao = (float) this.sala.getPrecoIngresso();
		initAssentos();//cria os assentos disponiveis para a sessao
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	//inicializa os assentos
	void initAssentos()
	{
		char fila = 'A';
		int capacidade = this.getSala().getCapacidade();
		for(int i = 0; i < capacidade; i++)
		{
			if(i % 10 == 0 && i != 0) fila += 1;
			Assento novo_assento = new Assento(fila, i+1);
			this.assentos.add(novo_assento);
			
		}
	}
	
	public int getAssentosDisponiveis()
	{
		int disponiveis = 0;
		for(Assento i : this.assentos) {
			if(!i.isOcupado()) disponiveis++;
		}
		
		return disponiveis;
	}
	
	public void printarAssento(int numero)
	{
		for(Assento ass : this.assentos)
		{
			if(ass.getNumero() == numero)
			{
				System.out.println("Fila: "+ass.getFila()+" número: "+ass.getNumero());
				return;
			}
		}
		System.out.println("Este assento não existe!");
	}

	public float getValorSessao() {
		return valorSessao;
	}

	public void setValorSessao(float valorSessao) {
		this.valorSessao = valorSessao;
	}    
}
