public class Cliente extends Tavolata {
	
	private String NomeCliente;
	private String CognomeCliente;
	private String NumeroIDCard;
	private String NumeroTelefono;
	
	public String getNomeCliente() {
		return NomeCliente;
	}
	
	public void setNomeCliente(String nomeCliente) {
		NomeCliente = nomeCliente;
	}
	
	public String getCognomeCliente() {
		return CognomeCliente;
	}
	
	public void setCognomeCliente(String cognomeCliente) {
		CognomeCliente = cognomeCliente;
	}
	
	public String getNumeroIDCard() {
		return NumeroIDCard;
	}
	
	public void setNumeroIDCard(String numeroIDCard) {
		NumeroIDCard = numeroIDCard;
	}
	
	public String getNumeroTelefono() {
		return NumeroTelefono;
	}
	
	public void setNumeroTelefono(String numeroTelefono) {
		NumeroTelefono = numeroTelefono;
	}
}