package cinetudoproject.model.domain;

import javafx.collections.ObservableList;

public class Sala2D extends Sala {

    public Sala2D() {
    }

    public Sala2D(int numero, int capacidade, String tipo, float preco_ingresso) {
        super(numero, capacidade, tipo, preco_ingresso);
    }

    @Override
    public float getPreco_ingresso() {
        // TODO Auto-generated method stub
        return preco_ingresso;
    }
}
