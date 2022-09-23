package Model.DTO;

import java.util.ArrayList;

public class Cameriere{

    private String nome;
    private String cognome;
    private final int codiceCameriere;

    public Cameriere(String nome, String cognome, int codiceCameriere) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceCameriere = codiceCameriere;
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

    public int getCodiceCameriere() {
        return codiceCameriere;
    }

    @Override
    public String toString() {
        return codiceCameriere + "# " + nome + " " + cognome;
    }
}
