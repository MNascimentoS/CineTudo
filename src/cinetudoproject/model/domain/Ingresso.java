package cinetudoproject.model.domain;

public class Ingresso {

    final int MEIA = 0;
    final boolean Sala3D = false;

    private float preco;
    private int id, tipo, sessao_id, venda_id;
    private String numAssento;

    public Ingresso() {
    }

    public Ingresso(float preco, int tipo, int sessao_id, int venda_id, String numAssento) {
        this.preco = preco;
        this.tipo = tipo;
        this.sessao_id = sessao_id;
        this.venda_id = venda_id;
        this.numAssento = numAssento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSessao_id() {
        return sessao_id;
    }

    public void setSessao_id(int sessao_id) {
        this.sessao_id = sessao_id;
    }

    public int getVenda_id() {
        return venda_id;
    }

    public void setVenda_id(int venda_id) {
        this.venda_id = venda_id;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco, boolean calcularPreco) {
        if (calcularPreco){
            if (tipo == MEIA) {
                this.preco = preco / 2;
            } else {
                this.preco = preco;
            }
            
        } else {
            this.preco = preco;
        }
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNumAssento() {
        return numAssento;
    }
    
    public void setAssento(String numAssento) {
        this.numAssento = numAssento;
    }

}
