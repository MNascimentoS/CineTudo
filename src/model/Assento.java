package model;

/**
 * Created by diogo on 20/08/16.
 */
public class Assento {

    private char fila;
    private int numero;
    private boolean ocupado;

    public Assento(char fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.ocupado = false;
    }

    public char getFila() {
        return fila;
    }

    public void setFila(char fila) {
        if(fila >= 'a' && fila <= 'z') this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if(numero > 0) this.numero = numero;
    }

}
