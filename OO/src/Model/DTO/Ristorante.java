package Model.DTO;

import java.util.ArrayList;
import java.util.Objects;

public class Ristorante {

	private String nome;
	private int numeroCamerieri;
	private int capienza;
	private ArrayList<Sala> Sale;

	public Ristorante(String nome, ArrayList<Sala> sale) {
		this.nome = nome;
		Sale = sale;
	}

	public Ristorante(String nome, int numeroCamerieri, ArrayList<Sala> sale) {
		this.nome = nome;
		this.numeroCamerieri = numeroCamerieri;
		Sale = sale;
	}

	public Ristorante(String nome, ArrayList<Sala> sale, int capienza) {
		this.nome = nome;
		Sale = sale;
		this.capienza = capienza;
	}

	public Ristorante(String nome, int numeroCamerieri, ArrayList<Sala> sale, int capienza) {
		this.nome = nome;
		this.numeroCamerieri = numeroCamerieri;
		Sale = sale;
		this.capienza = capienza;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Sala> getSale() {
		return Sale;
	}

	public void setSale(ArrayList<Sala> sale) {
		Sale = sale;
	}

	public int getNumeroCamerieri() {return numeroCamerieri;}

	public void setNumeroCamerieri(int numeroCamerieri) {this.numeroCamerieri = numeroCamerieri;}

	public int getCapienza() {return capienza;}

	public void setCapienza(int capienza) {this.capienza = capienza;}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ristorante that = (Ristorante) o;
		return nome.equals(that.nome);
	}
}