package model;

import java.util.ArrayList;

/**
 * Created by diogo on 20/08/16.
 */
public class Sessao {

    private ArrayList<Assento> assentos;
    private Data data;
    private Horario horario;
    private Filme filme;
    private int duracao;

    public Sessao(ArrayList<Assento> assentos, Data data, Horario horario, Filme filme, int duracao) {
        this.assentos = assentos;
        this.data = data;
        this.horario = horario;
        this.filme = filme;
        this.duracao = duracao;
    }

    public ArrayList<Assento> getAssentos() {
        return assentos;
    }

    public void setAssentos(char fila, int num) {
        Assento assento = new Assento(fila, num);
        this.assentos.add(assento);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        if(duracao > 0) this.duracao = duracao;
    }
}
