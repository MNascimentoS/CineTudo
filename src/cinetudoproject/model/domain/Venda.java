package cinetudoproject.model.domain;

import java.util.ArrayList;

public class Venda {

    private int id, cinema_id, ingresso_id; //Tudo que irá para o banco
    private String data, horario;
    private ArrayList<Ingresso> ingressos = new ArrayList<>();
    private float valor_total;

    public Venda() {
    }

    public Venda(String horario, int cinema_id, float valor_total) {
        this.horario = horario;
        this.cinema_id = cinema_id;
        this.valor_total = valor_total;
    }

    /*public Venda() {
        this.valorTotal = 0;
        setTodayCalendar();
        Calendar c = Calendar.getInstance();
		data.setDia(c.get(Calendar.DAY_OF_MONTH));
		data.setMes(c.get(Calendar.MONTH));
		data.setAno(c.get(Calendar.YEAR));
		horario.setHora(c.get(Calendar.HOUR));
		horario.setMinuto(c.get(Calendar.MINUTE));
    }*/

 /*public void setTodayCalendar() {
        Calendar c = Calendar.getInstance();
        this.data = new Data(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
        //this.horario = new Horario(c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
    }*/
    public void setIngresso_id(int ingresso_id) {
        this.ingresso_id = ingresso_id;
    }

    public int getIngresso_id() {
        return ingresso_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(int cinema_id) {
        this.cinema_id = cinema_id;
    }

    public ArrayList<Ingresso> getIngressos() {
        return ingressos;
    }

    public void addIngressos(Ingresso ingresso) {
        this.ingressos.add(ingresso);
    }

    public float getValor_total() {
        float valorFinal = 0;
        for (Ingresso ing : ingressos) {
            valorFinal = valorFinal + ing.getPreco();
        }
        this.valor_total = valorFinal;
        return this.valor_total;
    }

    public void setValor_total(float valorTotal) {
        this.valor_total = valorTotal;
    }
}
