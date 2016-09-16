package model;

/**
 * Created by diogo on 20/08/16.
 */
public class Sessao {

	private Filme filme;
	private Sala sala;
	private Horario horario;
    private Data data;
    private int ingressosDisponiveis;
    private float valorSessao;
	
    public Sessao(Filme filme, Sala sala, Horario horario, Data data, float valorSessao) {
		this.filme = filme;
		this.sala = sala;
		this.horario = horario;
		this.data = data;
		this.ingressosDisponiveis = this.sala.getCapacidade();
		this.valorSessao = (float) this.sala.getPrecoIngresso();
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

	public int getIngressosDisponiveis() {
		return ingressosDisponiveis;
	}

	public void setIngressosDisponiveis(int ingressosDisponiveis) {
		this.ingressosDisponiveis = ingressosDisponiveis;
	}

	public float getValorSessao() {
		return valorSessao;
	}

	public void setValorSessao(float valorSessao) {
		this.valorSessao = valorSessao;
	}    
}
