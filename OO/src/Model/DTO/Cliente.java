package Model.DTO;

import java.util.ArrayList;
import java.util.Objects;

public class Cliente {

	private String nome;
	private String cognome;
	private String numeroIdCard;
	private String numeroTelefono;

	public Cliente(String nome, String cognome, String numeroIdCard) {
		this.nome = nome;
		this.cognome = cognome;
		this.numeroIdCard = numeroIdCard;
	}

	public Cliente(String nome, String cognome, String numeroIdCard, String numeroTelefono) {
		this.nome = nome;
		this.cognome = cognome;
		this.numeroIdCard = numeroIdCard;
		this.numeroTelefono = numeroTelefono;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNumeroIdCard() {
		return numeroIdCard;
	}

	public void setNumeroIdCard(String numeroIdCard) {
		this.numeroIdCard = numeroIdCard;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	@Override
	public String toString() {
		return numeroIdCard + "# " + nome + " " + cognome;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cliente cliente = (Cliente) o;
		return numeroIdCard.equals(cliente.numeroIdCard);
	}
}