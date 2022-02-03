package Model.DTO;

import java.util.ArrayList;

public class Tavolo{

	private int MaxAvventori;
	private ArrayList<Tavolo> TavoliAdiacenti;
	private Sala sala;
	private ArrayList<Tavolata> Tavolate;

	public ArrayList<Tavolo> getTavoliAdiacenti() {
		return TavoliAdiacenti;
	}

	public void setTavoliAdiacenti(ArrayList<Tavolo> tavoliAdiacenti) {
		TavoliAdiacenti = tavoliAdiacenti;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public ArrayList<Tavolata> getTavolate() {
		return Tavolate;
	}

	public void setTavolate(ArrayList<Tavolata> tavolate) {
		Tavolate = tavolate;
	}

	public int getMaxAvventori() {
		return MaxAvventori;
	}
	
	public void setMaxAvventori(int maxAvventori) {
		MaxAvventori = maxAvventori;
	}
}