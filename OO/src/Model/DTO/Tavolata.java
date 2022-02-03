package Model.DTO;

import java.util.Date;

public class Tavolata extends Tavolo {
	
	private Date DataArrivo = new Date();
	private String Camerieri;
	private int ID_Prenotazione;
	
	public Date getDataArrivo() {
		return DataArrivo;
	}
	
	public void setDataArrivo(Date dataArrivo) {
		DataArrivo = dataArrivo;
	}
	
	public String getCamerieri() {
		return Camerieri;
	}
	
	public void setCamerieri(String camerieri) {
		Camerieri = camerieri;
	}
	
	public int getID_Prenotazione() {
		return ID_Prenotazione;
	}
	
	public void setID_Prenotazione(int iD_Prenotazione) {
		ID_Prenotazione = iD_Prenotazione;
	}
}