package cinetudoproject.model.domain;

public class Sala {

    protected int id, numero, capacidade;
    protected float preco_ingresso;
    protected String tipo;

    public Sala(){
    }

    public Sala(int numero, int capacidade, String tipo, float preco_ingresso) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.tipo = tipo;
        this.preco_ingresso = preco_ingresso;
    }

    /*gets ^ sets*/

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPreco_ingresso(float preco_ingresso) {
        this.preco_ingresso = preco_ingresso;
    }

    /*retorna o valor do ingresso*/
    public float getPreco_ingresso(){
        return preco_ingresso;
    }
}
