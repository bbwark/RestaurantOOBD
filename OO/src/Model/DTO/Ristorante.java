package Model.DTO;

import java.util.ArrayList;

public class Ristorante {

	private String nome;
	private ArrayList<Sala> Sale;

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
}