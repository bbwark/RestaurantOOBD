package Model.DTO;

import java.util.ArrayList;
import java.util.Date;

public class Tavolata {

	private Date DataArrivo = new Date();
	private ArrayList<Cameriere> camerieri;
	private Tavolo tavolo;
	private ArrayList<Cliente> clienti;

	public ArrayList<Cameriere> getCamerieri() {
		return camerieri;
	}

	public void setCamerieri(ArrayList<Cameriere> camerieri) {
		this.camerieri = camerieri;
	}

	public Tavolo getTavolo() {
		return tavolo;
	}

	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
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
}