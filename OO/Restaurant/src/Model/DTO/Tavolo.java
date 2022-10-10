package Model.DTO;

import java.util.ArrayList;

public class Tavolo{

	private int maxAvventori;
	private ArrayList<Tavolo> tavoliAdiacenti;
	private ArrayList<Tavolata> Tavolate;
	private int codiceTavolo;


	public Tavolo(int maxAvventori, ArrayList<Tavolo> tavoliAdiacenti, ArrayList<Tavolata> tavolate, int codiceTavolo) {
		this.maxAvventori = maxAvventori;
		this.tavoliAdiacenti = tavoliAdiacenti;
		Tavolate = tavolate;
		this.codiceTavolo = codiceTavolo;
	}

	public Tavolo(int maxAvventori, ArrayList<Tavolata> tavolate, int codiceTavolo) {
		this.maxAvventori = maxAvventori;
		Tavolate = tavolate;
		this.codiceTavolo = codiceTavolo;
	}

	public Tavolo(int maxAvventori) {
		this.maxAvventori = maxAvventori;
	}

	public ArrayList<Tavolo> getTavoliAdiacenti() {
		return tavoliAdiacenti;
	}

	public void setTavoliAdiacenti(ArrayList<Tavolo> tavoliAdiacenti) {
		this.tavoliAdiacenti = tavoliAdiacenti;
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

	public int getCodiceTavolo() {
		return codiceTavolo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tavolo tavolo = (Tavolo) o;
		return codiceTavolo == tavolo.codiceTavolo;
	}
}