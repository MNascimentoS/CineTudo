package cinetudoproject.model.domain;

import java.io.File;

public class Promocao {

    private int id;
    private String nome, descricao, data;
    private int diaDaSemana;
    private float desconto;
    private int cinema_id;
    private File imageFile;

    public Promocao() {
    }

    public Promocao(String nome, String descricao, int diaDaSemana, float desconto, File imageFile) {
        this.nome = nome;
        this.descricao = descricao;
        this.diaDaSemana = diaDaSemana;
        this.desconto = desconto;
        this.imageFile = imageFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
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
    
    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}
