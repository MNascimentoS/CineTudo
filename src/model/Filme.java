package model;

/**
 * Created by diogo on 20/08/16.
 */
public class Filme {

	private String titulo, diretor, atorPrincipal;
	private int duracao, classEtaria;
	private Genero genero;
	
	public Filme(String titulo, String diretor, String atorPrincipal, int duracao, int classEtaria, Genero genero) {
		super();
		this.titulo = titulo;
		this.diretor = diretor;
		this.atorPrincipal = atorPrincipal;
		this.duracao = duracao;
		this.classEtaria = classEtaria;
		this.genero = genero;
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
}
