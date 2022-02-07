package Model.DTO;

import java.util.ArrayList;

public class Cameriere{

    private String nome;
    private String cognome;
    private ArrayList<Tavolata> prenotazioni;

    public ArrayList<Tavolata> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(ArrayList<Tavolata> prenotazioni) {
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
}
