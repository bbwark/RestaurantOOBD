package Model.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Tavolata {

	private LocalDate DataArrivo;
	private ArrayList<Cameriere> camerieri;
	private ArrayList<Cliente> clienti;
	private final int codicePrenotazione;

	public Tavolata(LocalDate dataArrivo, ArrayList<Cameriere> camerieri, ArrayList<Cliente> clienti, int codicePrenotazione) {
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

	public LocalDate getDataArrivo() {
		return DataArrivo;
	}

	public void setDataArrivo(LocalDate dataArrivo) {
		DataArrivo = dataArrivo;
	}

	public int getCodicePrenotazione() {
		return codicePrenotazione;
	}
}