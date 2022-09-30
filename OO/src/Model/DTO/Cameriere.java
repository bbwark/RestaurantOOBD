package Model.DTO;

public class Cameriere{

    private String nome;
    private String cognome;
    private int codiceCameriere;

    public Cameriere(String nome, String cognome, int codiceCameriere) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceCameriere = codiceCameriere;
    }

    public Cameriere(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cameriere cameriere = (Cameriere) o;
        return codiceCameriere == cameriere.codiceCameriere;
    }
}
