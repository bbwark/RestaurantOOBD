package Model.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

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

	@Override
	public String toString() {
		return codicePrenotazione + "# " + DataArrivo.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tavolata tavolata = (Tavolata) o;
		return codicePrenotazione == tavolata.codicePrenotazione;
	}
}