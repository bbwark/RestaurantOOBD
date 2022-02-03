package Model.DTO;

public class Ristorante {
	
	private String NomeRistorante;
	private int ID_Ristorante;

	public String getNome() {
		return NomeRistorante;
	}

	public void setNome(String nome) {
		NomeRistorante = nome;
	}

	public int getID() {
		return ID_Ristorante;
	}

	public void setID(int iD) {
		ID_Ristorante = iD;
	}
	
}