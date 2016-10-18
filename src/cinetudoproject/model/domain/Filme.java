package cinetudoproject.model.domain;

import java.io.File;

/**
 * Created by diogo on 20/08/16.
 */
public class Filme {

    private String titulo, diretor, atorPrincipal;
    private int duracao, classEtaria, cinema_id;
    private Genero genero;
    private File imageFile;

    public Filme() {
    }

    public Filme(String titulo, String diretor, String atorPrincipal, int duracao, int classEtaria, Genero genero, File imageFile, int cinema_id) {
        super();
        this.titulo = titulo;
        this.diretor = diretor;
        this.atorPrincipal = atorPrincipal;
        this.duracao = duracao;
        this.classEtaria = classEtaria;
        this.genero = genero;
        this.imageFile = imageFile;
        this.cinema_id = cinema_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getAtorPrincipal() {
        return atorPrincipal;
    }

    public void setAtorPrincipal(String atorPrincipal) {
        this.atorPrincipal = atorPrincipal;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getClassEtaria() {
        return classEtaria;
    }

    public void setClassEtaria(int classEtaria) {
        this.classEtaria = classEtaria;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }
}
