package cinetudoproject.model.domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by diogo on 20/08/16.
 */
public final class Sessao {

    private int id, cinema_id,ingresso_disponivel;
    private int filme_id;
    private int sala_id;
    private Sala sala;
    private int horario_id;
    private ArrayList<Assento> assentos;
    private int assento;
    private Date data;

    public Sessao() {
        this.assentos = new ArrayList<>();
    }

    public Sessao(int filme_id, Sala sala, int horario_id, Date data, int ingresso_disponivel, int assento) {
        this.assentos = new ArrayList<>();
        this.filme_id = filme_id;
        this.sala = sala;
        this.horario_id = horario_id;
        this.data = data;
        this.ingresso_disponivel = ingresso_disponivel;
        this.assento = assento;
        initAssentos();//cria os assentos disponiveis para a sessao
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }
    
    public void setIngresso_disponivel(int ingresso_disponivel) {
        this.ingresso_disponivel = ingresso_disponivel;
    }

    public int getIngresso_disponivel() {
        return ingresso_disponivel;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getFilme_id() {
        return filme_id;
    }

    public void setFilme_id(int filme_id) {
        this.filme_id = filme_id;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }

    public int getHorario_id() {
        return horario_id;
    }

    public void setHorario_id(int horario_id) {
        this.horario_id = horario_id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getAssento() {
        return assento;
    }

    public void setAssento(int assento) {
        this.assento = assento;
        initAssentos();
    }
    
    //inicializa os assentos
    public void initAssentos() {
        char fila = 'A';
        for (int i = 0; i < this.getAssento(); i++) {
            if (i % 10 == 0 && i != 0) {
                fila += 1;
            }
            Assento novo_assento = new Assento(fila, i + 1);
            this.assentos.add(novo_assento);
        }
    }

    public int getAssentosDisponiveis() {
        int disponiveis = 0;
        for (Assento i : this.assentos) {
            if (!i.isOcupado()) {
                disponiveis++;
            }
        }

        return disponiveis;
    }

    public void printarAssento(int numero) {
        for (Assento ass : this.assentos) {
            if (ass.getNumero() == numero) {
                System.out.println("Fila: " + ass.getFila() + " número: " + ass.getNumero());
                return;
            }
        }
        System.out.println("Este assento não existe!");
    }

}
