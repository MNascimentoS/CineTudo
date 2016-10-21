package cinetudoproject.model.domain;

public abstract class Sala {

    protected int id, numero, capacidade;
    protected float precoIngresso;

    public Sala(int numero, int capacidade, float precoIngresso) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.precoIngresso = precoIngresso;
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

    public void setPrecoIngresso(float precoIngresso) {
        this.precoIngresso = precoIngresso;
    }

    /*retorna o valor do ingresso*/
    public abstract float getPrecoIngresso();
}
