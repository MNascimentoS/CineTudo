package cinetudoproject.model.domain;

import java.lang.reflect.Array;
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

    private int id;
    private String nome, endereco, cpnj;
    private ArrayList<Filme> filmes = new ArrayList<>();
    private ArrayList<Promocao> promocoes = new ArrayList<>();
    private ArrayList<Sessao> sessoes = new ArrayList<>();
    private ArrayList<Sala> salas = new ArrayList<>();
    private ArrayList<Venda> vendas = new ArrayList<>();
    private float valorSemana, valorFDS, montante;

    public Cinema() {
    }

    public Cinema(int id, String nome, String endereco, String cnpj, float valorSemana, float valorFDS) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cpnj = cnpj;
        this.valorSemana = valorSemana;
        this.valorFDS = valorFDS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValorIngresso() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        float valorDoIngresso = 0;
        /*caso finais de semana - adote o preco do final de semana*/
        if (day == 1 || day == 7) {
            valorDoIngresso = valorFDS;
        } else {
            valorDoIngresso = valorSemana;
        }
        //the magic is happen
        if (this.promocoes.size() > 0) {
            for (Promocao promo : this.promocoes) {
                if (promo.getDiaDaSemana() == day) {
                    valorDoIngresso -= valorDoIngresso * (promo.getDesconto() / 100);
                    break;
                }
            }
        }
        return valorDoIngresso;
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

    public void addVenda(Venda venda) {
        this.vendas.add(venda);
    }

    public ArrayList<Sessao> getSessoes() {
        return sessoes;
    }

    public void addSessao(Sessao sessao) {
        this.sessoes.add(sessao);
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public void addSala(Sala sala) {
        this.salas.add(sala);
    }

    public float getMontante() {
        float montante = 0;

        for (Venda v : this.vendas) {
            montante += v.getValor_total();
        }
        this.montante = montante;
        return this.montante;
    }

    public int getTotalVendas() {
        int cont = 0;
        for (Venda v : this.vendas) {
            cont++;
        }
        return cont;
    }

    public void addPromocao(Promocao promo) {
        this.promocoes.add(promo);
    }
}
