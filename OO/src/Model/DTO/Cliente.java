package Model.DTO;

public class Cliente {

	private String nome;
	private String cognome;
	private String numeroIDCard;
	private String numeroTelefono;
	private Tavolata tavolata;

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

	public Tavolata getTavolata() {
		return tavolata;
	}

	public void setTavolata(Tavolata tavolata) {
		this.tavolata = tavolata;
	}

	public String getNumeroIDCard() {
		return numeroIDCard;
	}

	public void setNumeroIDCard(String numeroIDCard) {
		this.numeroIDCard = numeroIDCard;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}
}