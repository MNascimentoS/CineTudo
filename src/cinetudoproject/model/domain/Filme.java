package cinetudoproject.model.domain;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by diogo on 20/08/16.
 */
public class Filme {

    private int id;
    private String duracao, titulo, diretor, atorPrincipal;
    private int classEtaria, cinema_id;
    private Genero genero;
    private File imageFile;

    public Filme() {
    }

    public Filme(String titulo, String diretor, String atorPrincipal, String duracao, int classEtaria, Genero genero, File imageFile, int cinema_id) {
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
    
    public static boolean jaExisteNaLista(ArrayList<Filme> filmes, Filme filme)
    {
        for(Filme i : filmes)
        {
            if(i.getId() == filme.getId()) return true;
        }
        return false;
    }
    
    public Filme(int id, String titulo, String diretor, String atorPrincipal, String duracao, int classEtaria, Genero genero, File imageFile, int cinema_id) {
        super();
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.atorPrincipal = atorPrincipal;
        this.duracao = duracao;
        this.classEtaria = classEtaria;
        this.genero = genero;
        this.imageFile = imageFile;
        this.cinema_id = cinema_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
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
