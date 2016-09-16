package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Venda {
	
	private ArrayList<Ingresso> ingressos;
	private float valorTotal;
	private Data data;
	private Horario horario;
	
	public Venda(){
		this.ingressos = new ArrayList<>();
		this.valorTotal = 0;
		Calendar c = Calendar.getInstance();
		data.setDia(c.get(Calendar.DAY_OF_MONTH));
		data.setMes(c.get(Calendar.MONTH));
		data.setAno(c.get(Calendar.YEAR));
		horario.setHora(c.get(Calendar.HOUR));
		horario.setMinuto(c.get(Calendar.MINUTE));
	}

	public ArrayList<Ingresso> getIngressos() {
		return ingressos;
	}

	public void addIngressos(Ingresso ingresso){
		this.ingressos.add(ingresso);
	}

	public float getValorTotal(){
		for(Ingresso ing : ingressos)
		{
			this.valorTotal = this.valorTotal + ing.getPreco();
		}
		return this.valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
}
