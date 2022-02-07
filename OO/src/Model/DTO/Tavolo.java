package Model.DTO;

import java.util.ArrayList;

public class Tavolo{

	private int maxAvventori;
	private ArrayList<Tavolo> tavoliAdiacenti;
	private Sala sala;
	private ArrayList<Tavolata> Tavolate;

	public ArrayList<Tavolo> getTavoliAdiacenti() {
		return tavoliAdiacenti;
	}

	public void setTavoliAdiacenti(ArrayList<Tavolo> tavoliAdiacenti) {
		this.tavoliAdiacenti = tavoliAdiacenti;
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
		return maxAvventori;
	}
	
	public void setMaxAvventori(int maxAvventori) {
		this.maxAvventori = maxAvventori;
	}
}