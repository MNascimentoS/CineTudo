package cinetudoproject.model.domain;

import java.util.Calendar;

/**
 * Created by diogo on 20/08/16.
 */
public class Data {
    private int dia, mes, ano;
    Calendar cal;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        cal = Calendar.getInstance();
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        if(dia > 0 && dia <= 31) this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        if(mes > 0 && mes <= 12) this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        if(ano > 0 && ano >= cal.get(Calendar.YEAR)) this.ano = ano;
    }
}
