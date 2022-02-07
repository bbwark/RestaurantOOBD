package Model.DTO;

import java.util.ArrayList;

public class Sala {
	
	private String nome;
	private Ristorante ristorante;
	private ArrayList<Tavolo> Tavoli;

	public Ristorante getRistorante() {
		return ristorante;
	}

	public void setRistorante(Ristorante ristorante) {
		this.ristorante = ristorante;
	}

	public ArrayList<Tavolo> getTavoli() {
		return Tavoli;
	}

	public void setTavoli(ArrayList<Tavolo> tavoli) {
		Tavoli = tavoli;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}