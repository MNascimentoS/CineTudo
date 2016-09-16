package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Venda {
	
	private ArrayList<Ingresso> ingressos = new ArrayList<>();
	private float valorTotal;
	private Data data;
	private Horario horario;
	
	public Venda(){
		this.valorTotal = 0;
		setTodayCalendar();
		/*Calendar c = Calendar.getInstance();
		data.setDia(c.get(Calendar.DAY_OF_MONTH));
		data.setMes(c.get(Calendar.MONTH));
		data.setAno(c.get(Calendar.YEAR));
		horario.setHora(c.get(Calendar.HOUR));
		horario.setMinuto(c.get(Calendar.MINUTE));*/
	}

	public void setTodayCalendar(){
		Calendar c = Calendar.getInstance();
		this.data = new Data(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH)+1, c.get(Calendar.YEAR));
		this.horario = new Horario(c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	}
	
	public int getDia()
	{
		return data.getDia();
	}
	
	public int getMes()
	{
		return data.getMes();
	}
	
	public int getAno()
	{
		return data.getAno();
	}
	
	public ArrayList<Ingresso> getIngressos() {
		return ingressos;
	}

	public void addIngressos(Ingresso ingresso){
		this.ingressos.add(ingresso);
	}

	public float getValorTotal(){
		float valorFinal = 0;
		for(Ingresso ing : ingressos)
		{
			valorFinal = valorFinal + ing.getPreco();
		}
		this.valorTotal = valorFinal;
		return this.valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
}
