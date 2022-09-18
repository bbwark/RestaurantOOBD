package Controller;

import GUI.addFrame.addFrame;
import GUI.editFrame.editFrame;
import GUI.mainFrame.mainFrame;
import GUI.statisticFrame.statisticFrame;
import Model.DAO.ImplementationClass.PostgreSQL.RistoranteImpl;
import Model.DAO.ImplementationClass.PostgreSQL.SalaImpl;
import Model.DAO.Interfaces.*;
import Model.DTO.Ristorante;
import Model.DTO.Sala;
import Model.DTO.Tavolo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Controller {

    public void listenerMainPanelRistorante(mainFrame mainFrame){
        ActionListener listenerButtonStatistics;
        ActionListener listenerButtonElencoClienti;
        ActionListener listenerButtonEditPrenotazione;
        ActionListener listenerButtonMostraSottoelemento;
        ActionListener listenerButtonAddPrenotazione;
        ActionListener listenerButtonEditCamerieri;
        ActionListener listenerButtonAdd;
        ActionListener listenerButtonEdit;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        DefaultListModel<Object> modelListaVisualizza = new DefaultListModel<>();

        RistoranteDAO ristoranteDAO = new RistoranteImpl();
        SalaDAO salaDAO = new SalaImpl();

        /*
        * Estrazione nomi di tutti i ristoranti e inserimento nel defaultListModel da utilizzare
        * per visualizzare tutti i ristoranti in memoria su vista
        *
        * LISTA SELEZIONE
        * */
        ArrayList<String> nomiRistoranti = new ArrayList<>();
        for (Ristorante i : ristoranteDAO.getAllRistoranti()) {
            nomiRistoranti.add(i.getNome());}
        modelListaSelezione.addAll(nomiRistoranti);
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().setModel(modelListaSelezione);
        
        /*
         * Estrazione del ristorante selezionato dalla lista al momento del click su un elemento della lista
         * per estrarre tutti i sottoelementi del ristorante e visualizzarli nella lista di preview
         *
         * LISTA VISUALIZZA
         * */
        MouseListener mouseListener = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome(mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue().toString());
                modelListaVisualizza.addAll(salaDAO.getAllSaleByRistorante(tempRistorante.getNome()));
                mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaVisualizza().setModel(modelListaVisualizza);
            }
        };

        /*
        * Estrazione del ristorante selezionato dalla lista al click del bottone per mostrare le statistiche
        * e passaggio del ristorante al frameStatistic per mostrarne i dati
        *
        * BUTTON STATISTIC
        * */
        listenerButtonStatistics = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome(mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue().toString());
                statisticFrame statisticFrame = new statisticFrame();
                listenerStatisticPanel(statisticFrame, tempRistorante);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonStatistics().addActionListener(listenerButtonStatistics);


        /*
        * Estrazione del ristorante selezionato dalla lista al click del bottone per aprire il pannello clienti
        *
        * BUTTON ELENCO CLIENTI
        * */
        listenerButtonElencoClienti = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Cliente");
                Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome(mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue().toString());
                listenerMainPanelCliente(mainFrame, tempRistorante);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonElencoClienti().addActionListener(listenerButtonElencoClienti);

        /*
        * Estrazione del ristorante selezionato dalla lista al click del bottone per aprire
        * il panel che elenca tutte le prenotazioni effettuate presso il ristorante
        *
        * BUTTON EDIT PRENOTAZIONI
        * */
        listenerButtonEditPrenotazione = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Prenotazioni");
                Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome(mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue().toString());
                listenerMainPanelPrenotazioni(mainFrame, tempRistorante);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonEditPrenotazioni().addActionListener(listenerButtonEditPrenotazione);

        /*
        * Estrazione del ristorante selezionato dalla lista al click del bottone per aprire
        * il panel che elenca tutte le sale presenti dentro il ristorante
        *
        * BUTTON MOSTRA SOTTOELEMENTO
        * */
        listenerButtonMostraSottoelemento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Sala");
                Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome(mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue().toString());
                listenerMainPanelSala(mainFrame, tempRistorante);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonMostraSottoelemento().addActionListener(listenerButtonMostraSottoelemento);

        /*
        * Estrazione del ristorante selezionato dalla lista al click del bottone per aprire
        * il panel che elenca tutti i tavoli presenti dentro il ristorante
        * per poter scegliere a quale aggiungere la prenotazione
        *
        * BUTTON ADD PRENOTAZIONE
        * */
        listenerButtonAddPrenotazione = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome(mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue().toString());
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Seleziona Tavolo");
                listenerMainPanelSelezionaTavolo(mainFrame, tempRistorante);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonAddPrenotazione().addActionListener(listenerButtonAddPrenotazione);

        /*
        * Estrazione del ristorante selezionato dalla lista al click del bottone per aprire
        * il frame di edit per modificare i camerieri che prestano servizio presso il ristorante
        *
        * BUTTON EDIT CAMERIERI
        * */
        listenerButtonEditCamerieri = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome(mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue().toString());
                editFrame editFrame = new editFrame();
                CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                cardLayout.show(editFrame.getContentPane(), "Panel Camerieri");
                listenerEditPanelCamerieri(editFrame, tempRistorante);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonEditCamerieri().addActionListener(listenerButtonEditCamerieri);


        /*
        * Chiamata ad addPanel Ristorante per aggiungere un nuovo ristorante all'elenco
        *
        * BUTTON ADD RISTORANTE
        * */
        listenerButtonAdd = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFrame addFrame = new addFrame();
                CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                cardLayout.show(addFrame.getContentPane(), "Panel Ristorante");
                listenerAddPanelRistorante(addFrame);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonAdd().addActionListener(listenerButtonAdd);


        /*
        * Estrazione del ristorante selezionato dalla lista al click del bottone per aprire
        * il frame di edit del ristorante selezionato
        *
        * BUTTON EDIT RISTORANTE
        * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome(mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue().toString());
                editFrame editFrame = new editFrame();
                CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                cardLayout.show(editFrame.getContentPane(), "Panel Ristorante");
                listenerEditRistorante(editFrame, tempRistorante);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonEdit().addActionListener(listenerButtonEdit);


    }

    public void listenerEditRistorante(editFrame editFrame, Ristorante tempRistorante) {
        //TODO editPanel Ristorante - Ristorante
    }

    public void listenerAddPanelRistorante(addFrame addFrame) {
        //TODO addPanel Ristorante
    }

    public void listenerEditPanelCamerieri(editFrame editFrame, Ristorante ristorante) {
        //TODO button edit camerieri - Ristorante
    }


    public void listenerEditPanelCamerieri(editFrame editFrame, Sala sala) {
        //TODO button edit camerieri - Sala
    }


    public void listenerEditPanelCamerieri(editFrame editFrame, Tavolo tavolo) {
        //TODO button edit camerieri - Tavolo
    }

    public void listenerMainPanelSelezionaTavolo(mainFrame mainFrame, Ristorante ristorante) {
        //TODO button add prenotazione - Ristorante
    }

    public void listenerMainPanelSelezionaTavolo(mainFrame mainFrame, Sala sala){
        //TODO button add prenotazione - Sala
    }

    public void listenerAddPanelPrenotazione(addFrame addFrame, Ristorante ristorante) {
        //TODO addPanel Prenotazione
    }

    public void listenerMainPanelSala(mainFrame mainFrame, Ristorante tempRistorante) {
        //TODO mainPanel Sala
    }

    public void listenerMainPanelPrenotazioni(mainFrame mainFrame, Ristorante ristorante) {
        //TODO mainPanel Prenotazioni - Ristorante
    }

    public void listenerMainPanelPrenotazioni(mainFrame mainFrame, Sala sala) {
        //TODO mainPanel Prenotazioni - Sala
    }

    public void listenerMainPanelPrenotazioni(mainFrame mainFrame, Tavolo tavolo) {
        //TODO mainPanel Prenotazioni - Tavolo
    }

    public void listenerEditPanelPrenotazioni(editFrame editFrame, Ristorante tempRistorante) {
        //TODO editPanel Prenotazioni
    }

    public void listenerStatisticPanel(statisticFrame statisticFrame, Ristorante tempRistorante) {
        //TODO Statistic Panel
    }

    public void listenerMainPanelCliente(mainFrame mainFrame, Ristorante ristorante){
        //TODO mainPanel Clienti - Ristorante
    }

    public void listenerMainPanelCliente(mainFrame mainFrame, Sala sala){
        //TODO mainPanel Clienti - Sala
    }

    public void listenerMainPanelCliente(mainFrame mainFrame, Tavolo tavolo){
        //TODO mainPanel Clienti - Tavolo
    }
}