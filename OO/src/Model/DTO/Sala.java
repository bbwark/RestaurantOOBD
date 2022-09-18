package Model.DTO;

import java.util.ArrayList;

public class Sala {
	
	private String nome;
	private int idSala;
	private Ristorante ristorante;
	private int numero_tavoli;
	private int capienza;
	private ArrayList<Tavolo> Tavoli;

	public Sala(int id, String nome, Ristorante ristorante, ArrayList<Tavolo> tavoli) {
		this.nome = nome;
		this.ristorante = ristorante;
		Tavoli = tavoli;
	}

	public Sala(int id, String nome, Ristorante ristorante, int numero_tavoli, ArrayList<Tavolo> tavoli) {
		this.nome = nome;
		this.ristorante = ristorante;
		this.numero_tavoli = numero_tavoli;
		Tavoli = tavoli;
	}

	public Sala(int id, String nome, Ristorante ristorante, ArrayList<Tavolo> tavoli, int capienza) {
		this.nome = nome;
		this.ristorante = ristorante;
		Tavoli = tavoli;
		this.capienza = capienza;
	}

	public Sala(int id, String nome, Ristorante ristorante, int numero_tavoli, ArrayList<Tavolo> tavoli, int capienza) {
		this.nome = nome;
		this.ristorante = ristorante;
		this.numero_tavoli = numero_tavoli;
		Tavoli = tavoli;
		this.capienza = capienza;
	}

	public int getNumero_tavoli() {return numero_tavoli;}

	public int getCapienza() {return capienza;}

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

	public int getIdSala() {
		return idSala;
	}
}