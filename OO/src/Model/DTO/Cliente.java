package Model.DTO;

import java.util.ArrayList;

public class Cliente {

	private String nome;
	private String cognome;
	private String numeroIdCard;
	private String numeroTelefono;
	private ArrayList<Tavolata> prenotazioni;

	public Cliente(String nome, String cognome, String numeroIdCard, ArrayList<Tavolata> prenotazioni) {
		this.nome = nome;
		this.cognome = cognome;
		this.numeroIdCard = numeroIdCard;
		this.prenotazioni = prenotazioni;
	}

	public Cliente(String nome, String cognome, String numeroIdCard, String numeroTelefono, ArrayList<Tavolata> prenotazioni) {
		this.nome = nome;
		this.cognome = cognome;
		this.numeroIdCard = numeroIdCard;
		this.numeroTelefono = numeroTelefono;
		this.prenotazioni = prenotazioni;
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

	public ArrayList<Tavolata> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(ArrayList<Tavolata> prenotazioni) {
		this.prenotazioni = prenotazioni;
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
}