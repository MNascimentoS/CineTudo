package model;

import java.util.ArrayList;

/**
 * Created by diogo on 20/08/16.
 */
public class Filme {

    private String titulo, diretor, categoria;
    private ArrayList<String> elenco;
    private int duracao, classEtaria;

    public Filme(String titulo, String diretor, String categoria, ArrayList<String> elenco, int duracao, int classEtaria) {
        this.titulo = titulo;
        this.diretor = diretor;
        this.categoria = categoria;
        this.elenco = elenco;
        this.duracao = duracao;
        this.classEtaria = classEtaria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(titulo != null)  this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        if(diretor != null) this.diretor = diretor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if(categoria != null) this.categoria = categoria;
    }

    public ArrayList<String> getElenco() {
        return elenco;
    }

    public void addElenco(String nome) {
        if(nome != null) elenco.add(nome);
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        if(duracao > 0) this.duracao = duracao;
    }

    public int getClassEtaria() {
        return classEtaria;
    }

    public void setClassEtaria(int classEtaria) {
        if(classEtaria > 0) this.classEtaria = classEtaria;
    }
}
