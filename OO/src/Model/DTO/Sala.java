package Model.DTO;

import java.util.ArrayList;

public class Sala {
	
	private String nome;
	private int idSala;
	private int numeroTavoli;
	private int capienza;
	private ArrayList<Tavolo> Tavoli;

	public Sala(int id, String nome, ArrayList<Tavolo> tavoli) {
		this.idSala = id;
		this.nome = nome;
		Tavoli = tavoli;
	}

	public Sala(int id, String nome, int numeroTavoli, ArrayList<Tavolo> tavoli) {
		this.idSala = id;
		this.nome = nome;
		this.numeroTavoli = numeroTavoli;
		Tavoli = tavoli;
	}

	public Sala(int id, String nome, ArrayList<Tavolo> tavoli, int capienza) {
		this.idSala = id;
		this.nome = nome;
		Tavoli = tavoli;
		this.capienza = capienza;
	}

	public Sala(int id, String nome, int numeroTavoli, ArrayList<Tavolo> tavoli, int capienza) {
		this.idSala = id;
		this.nome = nome;
		this.numeroTavoli = numeroTavoli;
		Tavoli = tavoli;
		this.capienza = capienza;
	}

	public int getNumeroTavoli() {return numeroTavoli;}

	public int getCapienza() {return capienza;}

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

	@Override
	public String toString() {
		return Integer.toString(idSala) + "# " + nome;
	}
}