package Model.DTO;

public class Cliente extends Persona {

	private String numeroIDCard;
	private String numeroTelefono;
	private Tavolata tavolata;

	public Cliente(String nome, String cognome, String numeroIDCard, String numeroTelefono, Tavolata tavolata) {
		super(nome, cognome);
		this.numeroIDCard = numeroIDCard;
		this.numeroTelefono = numeroTelefono;
		this.tavolata = tavolata;
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