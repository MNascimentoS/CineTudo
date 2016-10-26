package cinetudoproject.model.domain;

public class Sala3D extends Sala {

    public Sala3D() {
    }

    public Sala3D(int numero, int capacidade, String tipo, float preco_ingresso) {
        super(numero, capacidade, tipo, preco_ingresso);
    }

    @Override
    public float getPreco_ingresso() {
        // adicional de 20% no valor do ingresso
        return (float) (preco_ingresso * 0.20) + preco_ingresso;
    }
}
