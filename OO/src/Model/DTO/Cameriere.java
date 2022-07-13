package Model.DTO;

import java.util.ArrayList;

public class Cameriere{

    private String nome;
    private String cognome;
    private Ristorante ristorante;
    private ArrayList<Tavolata> servizio;
    private final int codiceCameriere;

    public Cameriere(String nome, String cognome, Ristorante ristorante, ArrayList<Tavolata> servizio, int codiceCameriere) {
        this.nome = nome;
        this.cognome = cognome;
        this.ristorante = ristorante;
        this.servizio = servizio;
        this.codiceCameriere = codiceCameriere;
    }

    public ArrayList<Tavolata> getServizio() {
        return servizio;
    }

    public void setServizio(ArrayList<Tavolata> servizio) {
        this.servizio = servizio;
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

    public Ristorante getRistorante() {
        return ristorante;
    }

    public void setRistorante(Ristorante ristorante) {
        this.ristorante = ristorante;
    }

    public int getCodiceCameriere() {
        return codiceCameriere;
    }
}
