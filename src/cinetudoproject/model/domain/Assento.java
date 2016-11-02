package cinetudoproject.model.domain;

/**
 * Created by diogo on 20/08/16.
 */
public class Assento {

    private String fila;
    private int numero;
    private int ocupado;

    public Assento(String fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.ocupado = 0;
    }

    public Assento() {
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
         this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if(numero > 0) this.numero = numero;
    }
    
    public int getOcupado() {
	return ocupado;
    }

    public void setOcupado(int ocupado) {
        this.ocupado = ocupado;
    }
}
