package Model.DTO;

import java.util.ArrayList;
import java.util.Date;

public class Tavolata {

	private Date DataArrivo = new Date();
	private ArrayList<Cameriere> camerieri;
	private ArrayList<Cliente> clienti;
	private final int codicePrenotazione;

	public Tavolata(Date dataArrivo, ArrayList<Cameriere> camerieri, ArrayList<Cliente> clienti, int codicePrenotazione) {
		DataArrivo = dataArrivo;
		this.camerieri = camerieri;
		this.clienti = clienti;
		this.codicePrenotazione = codicePrenotazione;
	}

	public ArrayList<Cameriere> getCamerieri() {
		return camerieri;
	}

	public void setCamerieri(ArrayList<Cameriere> camerieri) {
		this.camerieri = camerieri;
	}

	public ArrayList<Cliente> getClienti() {
		return clienti;
	}

	public void setClienti(ArrayList<Cliente> clienti) {
		this.clienti = clienti;
	}

	public Date getDataArrivo() {
		return DataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		DataArrivo = dataArrivo;
	}

	public int getCodicePrenotazione() {
		return codicePrenotazione;
	}
}