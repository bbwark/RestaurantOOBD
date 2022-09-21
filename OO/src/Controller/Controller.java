package Controller;

import GUI.addFrame.addFrame;
import GUI.editFrame.editFrame;
import GUI.mainFrame.mainFrame;
import GUI.statisticFrame.statisticFrame;
import Model.DAO.ImplementationClass.PostgreSQL.RistoranteImpl;
import Model.DAO.ImplementationClass.PostgreSQL.SalaImpl;
import Model.DAO.ImplementationClass.PostgreSQL.TavolataImpl;
import Model.DAO.ImplementationClass.PostgreSQL.TavoloImpl;
import Model.DAO.Interfaces.*;
import Model.DTO.Ristorante;
import Model.DTO.Sala;
import Model.DTO.Tavolo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {

    Connection connection;

    public Controller(mainFrame mainFrame, Connection connection){
        this.connection = connection;
        listenerMainPanelRistorante(mainFrame);
    }

    private void listenerMainPanelRistorante(mainFrame mainFrame){
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

        RistoranteDAO ristoranteDAO = new RistoranteImpl(connection);
        SalaDAO salaDAO = new SalaImpl(connection);

        /*
        * Estrazione nomi di tutti i ristoranti e inserimento nel defaultListModel da utilizzare
        * per visualizzare tutti i ristoranti in memoria su vista
        *
        * LISTA SELEZIONE
        * */
        modelListaSelezione.clear();
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
                modelListaVisualizza.clear();
                super.mouseClicked(e);
                Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                ArrayList<String> nomiSale = new ArrayList<>();
                for (Sala s : salaDAO.getAllSaleByRistorante(tempRistorante.getNome())){
                    nomiSale.add(s.toString());}
                modelListaVisualizza.addAll(nomiSale);
                mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaVisualizza().setModel(modelListaVisualizza);
                mainFrame.getMainFrameContentPane().getMainPanelRistorante().setLabelTableVisualizza(tempRistorante.getNome());
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().addMouseListener(mouseListener);


        /*
        * Estrazione del ristorante selezionato dalla lista al click del bottone per mostrare le statistiche
        * e passaggio del ristorante al frameStatistic per mostrarne i dati
        *
        * BUTTON STATISTIC
        * */
        listenerButtonStatistics = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                    Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                    statisticFrame statisticFrame = new statisticFrame();
                    listenerStatisticPanel(statisticFrame, tempRistorante);
                }
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
                if(!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Cliente");
                    Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                    listenerMainPanelCliente(mainFrame, tempRistorante);
                }
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
                if(!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Prenotazioni");
                    Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                    listenerMainPanelPrenotazioni(mainFrame, tempRistorante);
                }
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
                if(!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Sala");
                    Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                    listenerMainPanelSala(mainFrame, tempRistorante);
                }
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
                if(!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                    Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Seleziona Tavolo");
                    listenerMainPanelSelezionaTavolo(mainFrame, tempRistorante);
                }
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
                if(!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                    Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Camerieri");
                    listenerMainPanelCamerieri(mainFrame, tempRistorante);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonEditCamerieri().addActionListener(listenerButtonEditCamerieri);


        /*
        * Istanzia addFrame ad addPanel Ristorante per aggiungere un nuovo ristorante all'elenco
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
                if(!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                    Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Ristorante");
                    listenerEditRistorante(editFrame, tempRistorante);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonEdit().addActionListener(listenerButtonEdit);


        /*
        * Listener del bottone di chiusura del frame
        * Chiude la connessione al database prima di terminare l'esecuzione del programma
        *
        * BUTTON CLOSE FRAME
        * */
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                finally {
                    ((JFrame)(e.getComponent())).dispose();
                }
            }
        });

    }

    private void listenerMainPanelSala(mainFrame mainFrame, Ristorante tempRistorante) {
        ActionListener listenerButtonElencoClienti;
        ActionListener listenerButtonEditPrenotazioni;
        ActionListener listenerButtonMostraSottoelemento;
        ActionListener listenerButtonAddPrenotazione;
        ActionListener listenerButtonEditCamerieri;
        ActionListener listenerButtonAdd;
        ActionListener listenerButtonEdit;
        ActionListener listenerButtonIndietro;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        DefaultListModel<Object> modelListaVisualizza = new DefaultListModel<>();

        RistoranteDAO ristoranteDAO = new RistoranteImpl(connection);
        SalaDAO salaDAO = new SalaImpl(connection);
        TavoloDAO tavoloDAO = new TavoloImpl(connection);

        mainFrame.getMainFrameContentPane().getMainPanelSala().setLabelNomeSelezionato(tempRistorante.getNome());
        /*
         * Estrazione nomi e id di tutte le sale e inserimento nel defaultListModel da utilizzare
         * per visualizzare tutte le sale in memoria su vista
         *
         * LISTA SELEZIONE
         * */
        ArrayList<String> idNomiSale = new ArrayList<>();
        for (Sala s : tempRistorante.getSale()){
            idNomiSale.add(s.toString());
        }
        modelListaSelezione.addAll(idNomiSale);
        mainFrame.getMainFrameContentPane().getMainPanelSala().setModelListaSeleziona(modelListaSelezione);

        /*
         * Estrazione del ristorante selezionato dalla lista al momento del click su un elemento della lista
         * per estrarre tutti i sottoelementi del ristorante e visualizzarli nella lista di preview
         *
         * LISTA VISUALIZZA
         * */
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                    modelListaVisualizza.clear();
                    super.mouseClicked(e);

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Sala tempSala = salaDAO.getSalaById(tempId);

                    ArrayList<String> idTavoli = new ArrayList<>();
                    for (Tavolo t : tavoloDAO.getAllTavoliBySala(tempSala.getIdSala())) {
                        idTavoli.add(Integer.toString(t.getCodiceTavolo()));
                    }
                    modelListaVisualizza.addAll(idTavoli);
                    mainFrame.getMainFrameContentPane().getMainPanelSala().getListaVisualizza().setModel(modelListaVisualizza);
                    mainFrame.getMainFrameContentPane().getMainPanelSala().setLabelTableVisualizza(tempSala.toString());
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().addMouseListener(mouseListener);

        /*
         * Estrazione della sala selezionata dalla lista al click del bottone per aprire il pannello clienti
         *
         * BUTTON ELENCO CLIENTI
         * */
        listenerButtonElencoClienti = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Cliente");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Sala tempSala = salaDAO.getSalaById(tempId);

                    listenerMainPanelCliente(mainFrame, tempSala);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonElencoClienti().addActionListener(listenerButtonElencoClienti);

        /*
         * Estrazione della selezionata dalla lista al click del bottone per aprire
         * il panel che elenca tutte le prenotazioni effettuate presso la sala
         *
         * BUTTON EDIT PRENOTAZIONI
         * */
        listenerButtonEditPrenotazioni = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Prenotazioni");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Sala tempSala = salaDAO.getSalaById(tempId);

                    listenerMainPanelPrenotazioni(mainFrame, tempSala);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonEditPrenotazioni().addActionListener(listenerButtonEditPrenotazioni);

        /*
         * Estrazione della sala selezionata dalla lista al click del bottone per aprire
         * il panel che elenca tutti i tavoli presenti dentro la sala
         *
         * BUTTON MOSTRA SOTTOELEMENTO
         * */
        listenerButtonMostraSottoelemento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Tavolo");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Sala tempSala = salaDAO.getSalaById(tempId);

                    listenerMainPanelTavolo(mainFrame, tempSala);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonMostraSottoelemento().addActionListener(listenerButtonMostraSottoelemento);

        /*
         * Estrazione della sala selezionata dalla lista al click del bottone per aprire
         * il panel che elenca tutti i tavoli presenti dentro la sala
         * per poter scegliere a quale aggiungere la prenotazione
         *
         * BUTTON ADD PRENOTAZIONE
         * */
        listenerButtonAddPrenotazione = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Seleziona Tavolo");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Sala tempSala = salaDAO.getSalaById(tempId);

                    listenerMainPanelSelezionaTavolo(mainFrame, tempSala);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonAddPrenotazione().addActionListener(listenerButtonAddPrenotazione);

        /*
         * Estrazione della sala selezionata dalla lista al click del bottone per aprire
         * il frame di edit per modificare i camerieri che prestano servizio presso la sala
         *
         * BUTTON EDIT CAMERIERI
         * */
        listenerButtonEditCamerieri = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Camerieri");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Sala tempSala = salaDAO.getSalaById(tempId);

                    listenerMainPanelCamerieri(mainFrame, tempSala);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonEditCamerieri().addActionListener(listenerButtonEditCamerieri);


        /*
         * Istanzia addFrame ad addPanel Sala per aggiungere una nuova Sala all'elenco
         *
         * BUTTON ADD SALA
         * */
        listenerButtonAdd = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFrame addFrame = new addFrame();
                CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                cardLayout.show(addFrame.getContentPane(), "Panel Sala");

                String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                Sala tempSala = salaDAO.getSalaById(tempId);

                listenerAddPanelSala(addFrame, ristoranteDAO.getRistoranteBySala(tempSala));
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonAdd().addActionListener(listenerButtonAdd);


        /*
         * Estrazione della sala selezionata dalla lista al click del bottone per aprire
         * il frame di edit della sala selezionato
         *
         * BUTTON EDIT SALA
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Sala");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Sala tempSala = salaDAO.getSalaById(tempId);

                    listenerEditPanelSala(editFrame, tempSala);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
        * Bottone che riporta al pannello precedente rispetto al pannello attuale
        *
        * Ristorante <- Sala
        *
        * BUTTON INDIETRO
        * */
        listenerButtonIndietro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Ristorante");
                modelListaVisualizza.removeAllElements();
                mainFrame.getMainFrameContentPane().getMainPanelSala().setLabelTableVisualizza("");
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenerMainPanelTavolo(mainFrame mainFrame, Sala tempSala) {
        ActionListener listenerButtonAddPrenotazione;
        ActionListener listenerButtonMostraSottoelemento;
        ActionListener listenerButtonEditCamerieri;
        ActionListener listenerButtonAdd;
        ActionListener listenerButtonEdit;
        ActionListener listenerButtonIndietro;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();

        RistoranteDAO ristoranteDAO = new RistoranteImpl(connection);
        SalaDAO salaDAO = new SalaImpl(connection);
        TavoloDAO tavoloDAO = new TavoloImpl(connection);
        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        mainFrame.getMainFrameContentPane().getMainPanelTavolo().setLabelNomeSelezionato(tempSala.toString());
        /*
         * Estrazione id di tutti i tavoli e inserimento nel defaultListModel da utilizzare
         * per visualizzare tutti i tavoli in memoria su vista
         *
         * LISTA SELEZIONE
         * */
        ArrayList<String> idTavoli = new ArrayList<>();
        for (Tavolo t : tempSala.getTavoli()){
            idTavoli.add(Integer.toString(t.getCodiceTavolo()));
        }
        modelListaSelezione.addAll(idTavoli);
        mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione del tavolo selezionato dalla lista al click del bottone per aprire
         * il frame con il panel per aggiungere una prenotazione
         *
         * BUTTON ADD PRENOTAZIONE
         * */
        listenerButtonAddPrenotazione = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().isSelectionEmpty()){
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Prenotazione");

                    int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().getSelectedValue());
                    Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                    listenerAddPanelPrenotazione(addFrame, tempTavolo);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelTavolo().getButtonAddPrenotazione().addActionListener(listenerButtonAddPrenotazione);

        /*
         * Estrazione del tavolo selezionato dalla lista al click del bottone per aprire
         * il panel che elenca tutte le prenotazioni presenti per il tavolo
         *
         * BUTTON MOSTRA SOTTOELEMENTO
         * */
        listenerButtonMostraSottoelemento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Prenotazioni");

                    int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().getSelectedValue());
                    Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                    listenerMainPanelPrenotazioni(mainFrame, tempTavolo);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelTavolo().getButtonMostraSottoelemento().addActionListener(listenerButtonMostraSottoelemento);

        /*
         * Estrazione del tavolo selezionato dalla lista al click del bottone per aprire
         * il frame di edit per modificare i camerieri che prestano servizio presso il tavolo
         *
         * BUTTON EDIT CAMERIERI
         * */
        listenerButtonEditCamerieri = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().isSelectionEmpty()) {
                    CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                    cardLayout.show(mainFrame.getContentPane(), "Panel Camerieri");

                    int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().getSelectedValue());
                    Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                    listenerMainPanelCamerieri(mainFrame, tempTavolo);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelTavolo().getButtonEditCamerieri().addActionListener(listenerButtonEditCamerieri);

        /*
         * Istanzia addFrame ad addPanel Tavolo per aggiungere un nuovo Tavolo all'elenco
         *
         * BUTTON ADD SALA
         * */
        listenerButtonAdd = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFrame addFrame = new addFrame();
                CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                cardLayout.show(addFrame.getContentPane(), "Panel Tavolo");

                int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().getSelectedValue());
                Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                listenerAddPanelTavolo(addFrame, salaDAO.getSalaByTavolo(tempTavolo));
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelTavolo().getButtonAdd().addActionListener(listenerButtonAdd);

        /*
         * Estrazione del tavolo selezionato dalla lista al click del bottone per aprire
         * il frame di edit del tavolo selezionato
         *
         * BUTTON EDIT TAVOLO
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Tavolo");

                    int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().getSelectedValue());
                    Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                    listenerEditPanelTavolo(editFrame, tempTavolo);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelTavolo().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Sala <- Tavolo
         *
         * BUTTON INDIETRO
         * */
        listenerButtonIndietro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Sala");
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelTavolo().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenerEditPanelTavolo(editFrame editFrame, Tavolo tempTavolo) {
        //TODO editPanel Tavolo
    }

    private void listenerAddPanelTavolo(addFrame addFrame, Sala sala) {
        //TODO addPanel Tavolo
    }

    private void listenerAddPanelPrenotazione(addFrame addFrame, Tavolo tavolo) {
        //TODO addPanel Prenotazione - Tavolo - SelezionaTavolo
    }

    private void listenerEditPanelSala(editFrame editFrame, Sala sala) {
        //TODO editPanel Sala - Sala
    }

    private void listenerAddPanelSala(addFrame addFrame, Ristorante ristorante) {
        //TODO addPanel Sala
    }

    private void listenerEditRistorante(editFrame editFrame, Ristorante ristorante) {
        //TODO editPanel Ristorante - Ristorante
    }

    private void listenerAddPanelRistorante(addFrame addFrame) {
        //TODO addPanel Ristorante
    }

    private void listenerMainPanelCamerieri(mainFrame mainFrame, Ristorante ristorante) {
        //TODO button edit camerieri - Ristorante
    }


    private void listenerMainPanelCamerieri(mainFrame mainFrame, Sala sala) {
        //TODO button edit camerieri - Sala
    }


    private void listenerMainPanelCamerieri(mainFrame mainFrame, Tavolo tavolo) {
        //TODO button edit camerieri - Tavolo
    }

    private void listenerMainPanelSelezionaTavolo(mainFrame mainFrame, Ristorante ristorante) {
        //TODO button add prenotazione - Ristorante
    }

    private void listenerMainPanelSelezionaTavolo(mainFrame mainFrame, Sala sala){
        //TODO button add prenotazione - Sala
    }

    private void listenerMainPanelPrenotazioni(mainFrame mainFrame, Ristorante ristorante) {
        //TODO mainPanel Prenotazioni - Ristorante
    }

    private void listenerMainPanelPrenotazioni(mainFrame mainFrame, Sala sala) {
        //TODO mainPanel Prenotazioni - Sala
    }

    private void listenerMainPanelPrenotazioni(mainFrame mainFrame, Tavolo tavolo) {
        //TODO mainPanel Prenotazioni - Tavolo
    }

    private void listenerEditPanelPrenotazioni(editFrame editFrame, Ristorante ristorante) {
        //TODO editPanel Prenotazioni
    }

    private void listenerStatisticPanel(statisticFrame statisticFrame, Ristorante ristorante) {
        //TODO Statistic Panel
    }

    private void listenerMainPanelCliente(mainFrame mainFrame, Ristorante ristorante){
        //TODO mainPanel Clienti - Ristorante
    }

    private void listenerMainPanelCliente(mainFrame mainFrame, Sala sala){
        //TODO mainPanel Clienti - Sala
    }

    private void listenerMainPanelCliente(mainFrame mainFrame, Tavolo tavolo){
        //TODO mainPanel Clienti - Tavolo
    }
}