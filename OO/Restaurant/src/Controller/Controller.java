package Controller;

import GUI.addFrame.addFrame;
import GUI.editFrame.editFrame;
import GUI.loginFrame.loginFrame;
import GUI.mainFrame.mainFrame;
import GUI.statisticFrame.statisticFrame;
import Model.DAO.ImplementationClass.PostgreSQL.*;
import Model.DAO.ImplementationClass.PostgreSQL.Connection.DatabasePostgresConnection;
import Model.DAO.Interfaces.*;
import Model.DTO.*;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    private Connection connection;
    private mainFrame mainFrame;

    public Controller() {
        loginFrame loginFrame = new loginFrame();
        listenersLoginFrame(loginFrame);
    }

    private void listenersLoginFrame(loginFrame loginFrame) {
        ActionListener listenerButtonConferma;
        ActionListener listenerButtonAnnulla;

        loginFrame.getLoginFrameContentPane().getLoginPanel().setTextFieldHost("localhost");
        loginFrame.getLoginFrameContentPane().getLoginPanel().setTextFieldPort("5432");
        loginFrame.getLoginFrameContentPane().getLoginPanel().setTextFieldDatabaseName("Restaurant");
        loginFrame.getLoginFrameContentPane().getLoginPanel().setTextFieldUsername("postgres");
        loginFrame.getLoginFrameContentPane().getLoginPanel().setTextFieldPassword("postgres");

        /*
        * Alla pressione del bottone conferma vengono estratte le credenziali inserite nella textField
        * e si tenta una connessione con il database, se questa fallisce appare un Message Dialog che informa dell'avvenuto errore,
        * altrimenti viene stabilita la connessione e aperto il mainFrame, viene chiamato il metodo per aggiungere i listeners al
        * primo pannello del frame principale da cui segue l'esecuzione del programma
        *
        * BUTTON CONFERMA
        * */
        listenerButtonConferma = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String host = loginFrame.getLoginFrameContentPane().getLoginPanel().getTextFieldHost();
                String port = loginFrame.getLoginFrameContentPane().getLoginPanel().getTextFieldPort();
                String databaseName = loginFrame.getLoginFrameContentPane().getLoginPanel().getTextFieldDatabaseName();
                String username = loginFrame.getLoginFrameContentPane().getLoginPanel().getTextFieldUsername();
                String password = loginFrame.getLoginFrameContentPane().getLoginPanel().getTextFieldPassword();
                DatabasePostgresConnection databasePostgresConnection = DatabasePostgresConnection.getInstance(host, port, databaseName, username, password);
                if(databasePostgresConnection.open()){
                    loginFrame.dispose();
                    connection = databasePostgresConnection.getConnection();
                    mainFrame = new mainFrame();
                    listenersMainPanelRistorante(mainFrame);
                }
                else{
                    JOptionPane.showMessageDialog(loginFrame, "Il database non esiste o le credenziali sono incorrette");
                }
            }
        };
        loginFrame.getLoginFrameContentPane().getLoginPanel().getButtonConferma().addActionListener(listenerButtonConferma);

        /*
        * Chiude il frame senza eseguire nulla, rilasciando le risorse e terminando il programma
        *
        * BUTTON ANNULLA
        * */
        listenerButtonAnnulla = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
            }
        };
        loginFrame.getLoginFrameContentPane().getLoginPanel().getButtonAnnulla().addActionListener(listenerButtonAnnulla);
    }

    private void listenersMainPanelRistorante(mainFrame mainFrame) {
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
            nomiRistoranti.add(i.getNome());
        }
        modelListaSelezione.addAll(nomiRistoranti);
        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().setModel(modelListaSelezione);

        if (Arrays.asList(mainFrame.getMainFrameContentPane().getMainPanelRistorante().getButtonEdit().getActionListeners()).isEmpty()) {
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
                    for (Sala s : salaDAO.getAllSaleByRistorante(tempRistorante.getNome())) {
                        nomiSale.add(s.toString());
                    }
                    modelListaVisualizza.addAll(nomiSale);
                    mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaVisualizza().setModel(modelListaVisualizza);
                    mainFrame.getMainFrameContentPane().getMainPanelRistorante().setLabelListaVisualizza(tempRistorante.getNome());

                    int capienza = 0;
                    for (Sala s : tempRistorante.getSale())
                        for (Tavolo t : s.getTavoli())
                            capienza = capienza + t.getMaxAvventori();

                    mainFrame.getMainFrameContentPane().getMainPanelRistorante().setLabelCapienza(Integer.toString(capienza));
                    mainFrame.getMainFrameContentPane().getMainPanelRistorante().setLabelNumeroCamerieri(Integer.toString(tempRistorante.getNumeroCamerieri()));
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                        Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                        statisticFrame statisticFrame = new statisticFrame();
                        listenersStatisticPanel(statisticFrame, tempRistorante);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Cliente");
                        Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                        listenersMainPanelClienti(mainFrame, tempRistorante);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Prenotazioni");
                        Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                        listenersMainPanelPrenotazioni(mainFrame, tempRistorante);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Sala");
                        Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                        listenersMainPanelSala(mainFrame, tempRistorante);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                        Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Seleziona Tavolo");
                        listenersMainPanelSelezionaTavolo(mainFrame, tempRistorante);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                        Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Camerieri");
                        listenersMainPanelCamerieri(mainFrame, tempRistorante);
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

                    listenersAddPanelRistorante(addFrame, mainFrame);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                        Ristorante tempRistorante = ristoranteDAO.getRistoranteByNome((String) mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().getSelectedValue());
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Ristorante");
                        listenersEditPanelRistorante(editFrame, mainFrame, tempRistorante);
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
                    } finally {
                        ((JFrame) (e.getComponent())).dispose();
                        try {
                            if(connection.isClosed())
                                System.exit(0);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

        }
    }

    private void listenersMainPanelSala(mainFrame mainFrame, Ristorante ristorante) {
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

        mainFrame.getMainFrameContentPane().getMainPanelSala().setLabelNomeSelezionato(ristorante.getNome());
        mainFrame.getMainFrameContentPane().getMainPanelSala().setLabelCapienza("");
        /*
         * Estrazione nomi e id di tutte le sale e inserimento nel defaultListModel da utilizzare
         * per visualizzare tutte le sale in memoria su vista
         *
         * LISTA SELEZIONE
         * */
        ArrayList<String> idNomiSale = new ArrayList<>();
        for (Sala s : ristorante.getSale()) {
            idNomiSale.add(s.toString());
        }
        modelListaSelezione.addAll(idNomiSale);
        mainFrame.getMainFrameContentPane().getMainPanelSala().setModelListaSeleziona(modelListaSelezione);

        if (Arrays.asList(mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonEdit().getActionListeners()).isEmpty()) {
            /*
             * Estrazione del ristorante selezionato dalla lista al momento del click su un elemento della lista
             * per estrarre tutti i sottoelementi del ristorante e visualizzarli nella lista di preview
             *
             * LISTA VISUALIZZA
             * */
            MouseListener mouseListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
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
                        mainFrame.getMainFrameContentPane().getMainPanelSala().setLabelListaVisualizza(tempSala.toString() + " [" + Integer.toString(tempSala.getNumeroTavoli()) + "]");

                        int capienza = 0;

                        for (Tavolo t : tempSala.getTavoli())
                            capienza = capienza + t.getMaxAvventori();

                        mainFrame.getMainFrameContentPane().getMainPanelSala().setLabelCapienza(Integer.toString(capienza));
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Cliente");

                        String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Sala tempSala = salaDAO.getSalaById(tempId);

                        listenersMainPanelClienti(mainFrame, tempSala);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Prenotazioni");

                        String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Sala tempSala = salaDAO.getSalaById(tempId);

                        listenersMainPanelPrenotazioni(mainFrame, tempSala);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Tavolo");

                        String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Sala tempSala = salaDAO.getSalaById(tempId);

                        listenersMainPanelTavolo(mainFrame, tempSala);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Seleziona Tavolo");

                        String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Sala tempSala = salaDAO.getSalaById(tempId);

                        listenersMainPanelSelezionaTavolo(mainFrame, tempSala);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Camerieri");

                        String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Sala tempSala = salaDAO.getSalaById(tempId);

                        listenersMainPanelCamerieri(mainFrame, tempSala);
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

                    listenersAddPanelSala(addFrame, mainFrame, ristoranteDAO.getRistoranteBySala(tempSala), true);
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
                    if (!mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().isSelectionEmpty()) {
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Sala");

                        String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Sala tempSala = salaDAO.getSalaById(tempId);

                        listenersEditPanelSala(editFrame, mainFrame, tempSala);
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
                    mainFrame.getMainFrameContentPane().getMainPanelSala().setLabelListaVisualizza("");
                }
            };
            mainFrame.getMainFrameContentPane().getMainPanelSala().getButtonIndietro().addActionListener(listenerButtonIndietro);
        }
    }

    private void listenersMainPanelTavolo(mainFrame mainFrame, Sala tempSala) {
        ActionListener listenerButtonAddPrenotazione;
        ActionListener listenerButtonMostraSottoelemento;
        ActionListener listenerButtonEditCamerieri;
        ActionListener listenerButtonElencoClienti;
        ActionListener listenerButtonAdd;
        ActionListener listenerButtonEdit;
        ActionListener listenerButtonIndietro;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();

        SalaDAO salaDAO = new SalaImpl(connection);
        TavoloDAO tavoloDAO = new TavoloImpl(connection);

        mainFrame.getMainFrameContentPane().getMainPanelTavolo().setLabelNomeSelezionato(tempSala.toString());
        /*
         * Estrazione id di tutti i tavoli e inserimento nel defaultListModel da utilizzare
         * per visualizzare tutti i tavoli in memoria su vista
         *
         * LISTA SELEZIONE
         * */
        ArrayList<String> idTavoli = new ArrayList<>();
        for (Tavolo t : tempSala.getTavoli()) {
            idTavoli.add(Integer.toString(t.getCodiceTavolo()));
        }
        modelListaSelezione.addAll(idTavoli);
        mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().setModel(modelListaSelezione);

        if (Arrays.asList(mainFrame.getMainFrameContentPane().getMainPanelTavolo().getButtonEdit().getActionListeners()).isEmpty()) {
            /*
             * Estrazione del tavolo selezionato dalla lista al click del bottone per aprire
             * il frame con il panel per aggiungere una prenotazione
             *
             * BUTTON ADD PRENOTAZIONE
             * */
            listenerButtonAddPrenotazione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().isSelectionEmpty()) {
                        addFrame addFrame = new addFrame();
                        CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                        cardLayout.show(addFrame.getContentPane(), "Panel Prenotazione");

                        int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().getSelectedValue());
                        Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                        listenersAddPanelPrenotazione(addFrame, mainFrame, tempTavolo, true);
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

                        listenersMainPanelPrenotazioni(mainFrame, tempTavolo);
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

                        listenersMainPanelCamerieri(mainFrame, tempTavolo);
                    }
                }
            };
            mainFrame.getMainFrameContentPane().getMainPanelTavolo().getButtonEditCamerieri().addActionListener(listenerButtonEditCamerieri);

            /*
             * Estrazione del tavolo selezionato dalla lista al click del bottone per aprire il pannello clienti
             *
             * BUTTON ELENCO CLIENTI
             * */
            listenerButtonElencoClienti = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().isSelectionEmpty()) {
                        CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                        cardLayout.show(mainFrame.getContentPane(), "Panel Cliente");

                        int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().getSelectedValue());
                        Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                        listenersMainPanelClienti(mainFrame, tempTavolo);
                    }
                }
            };
            mainFrame.getMainFrameContentPane().getMainPanelTavolo().getButtonElencoClienti().addActionListener(listenerButtonElencoClienti);

            /*
             * Istanzia addFrame ad addPanel Tavolo per aggiungere un nuovo Tavolo all'elenco
             *
             * BUTTON ADD TAVOLO
             * */
            listenerButtonAdd = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel Tavolo");

                    int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().getSelectedValue());
                    Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                    listenersAddPanelTavolo(addFrame, mainFrame, salaDAO.getSalaByTavolo(tempTavolo), true);
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

                        listenersEditPanelTavolo(editFrame, mainFrame, tempTavolo);
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
    }

    private void listenersMainPanelPrenotazioni(mainFrame mainFrame, Ristorante ristorante) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonEdit;
        ActionListener listenerButtonMostraData;
        ActionListener listenerButtonMostraAll;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti ai bottoni per far rimanere
         * presenti solo quelli che verranno aggiunti dal pannello chiamante
         * */
        ActionListener[] listenersButtonIndietro = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listenersButtonIndietro) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listenersButtonEdit = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonEdit().getActionListeners();
        for (ActionListener listener : listenersButtonEdit) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonEdit().removeActionListener(listener);
        }

        ActionListener[] listenersButtonMostraData = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraData().getActionListeners();
        for (ActionListener listener : listenersButtonMostraData) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraData().removeActionListener(listener);
        }

        ActionListener[] listenersButtonMostraAll = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraAll().getActionListeners();
        for (ActionListener listener : listenersButtonMostraAll) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraAll().removeActionListener(listener);
        }

        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        ArrayList<Tavolata> tavolateRistorante = tavolataDAO.getAllTavolateByRistorante(ristorante.getNome());
        ArrayList<String> codiciPrenotazioni = new ArrayList<>();
        for (Tavolata t : tavolateRistorante) {
            codiciPrenotazioni.add(t.toString());
        }
        modelListaSelezione.addAll(codiciPrenotazioni);
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione della tavolata selezionata dalla lista al click del bottone per aprire
         * il frame di edit della tavolata selezionata
         *
         * BUTTON EDIT TAVOLATA
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");


                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().getSelectedValue();
                    int tempCodice = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Tavolata tempTavolata = tavolataDAO.getTavolataById(tempCodice);

                    listenersEditPanelPrenotazione(editFrame, mainFrame, tempTavolata, true);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Estrazione della data selezionata per mostrare sulla lista solo le prenotazioni presenti in quella data
         *
         * BUTTON MOSTRA DATA
         * */
        listenerButtonMostraData = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getDate() != null) {
                    ArrayList<Tavolata> tempTavolateRistorante = tavolataDAO.getAllTavolateByRistorante(ristorante.getNome());
                    ArrayList<String> tempCodiciPrenotazioni = new ArrayList<>();
                    for (Tavolata t : tempTavolateRistorante)
                        if (t.getDataArrivo().isEqual(mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getDate()))
                            tempCodiciPrenotazioni.add(t.toString());
                    modelListaSelezione.clear();
                    modelListaSelezione.addAll(tempCodiciPrenotazioni);
                    mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(modelListaSelezione);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraData().addActionListener(listenerButtonMostraData);

        /*
         * Ripristina la lista come prima di premere il Button Mostra Data
         *
         * BUTTON MOSTRA ALL
         * */
        listenerButtonMostraAll = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Tavolata> tempTavolateRistorante = tavolataDAO.getAllTavolateByRistorante(ristorante.getNome());
                ArrayList<String> tempCodiciPrenotazioni = new ArrayList<>();
                for (Tavolata t : tempTavolateRistorante)
                    tempCodiciPrenotazioni.add(t.toString());
                modelListaSelezione.clear();
                modelListaSelezione.addAll(tempCodiciPrenotazioni);
                mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(modelListaSelezione);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraAll().addActionListener(listenerButtonMostraAll);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Ristorante <- Prenotazioni
         *
         * BUTTON INDIETRO
         * */
        listenerButtonIndietro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Ristorante");
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelPrenotazioni(mainFrame mainFrame, Sala sala) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonEdit;
        ActionListener listenerButtonMostraData;
        ActionListener listenerButtonMostraAll;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti ai bottoni per far rimanere
         * presenti solo quelli che verranno aggiunti dal pannello chiamante
         * */
        ActionListener[] listenersButtonIndietro = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listenersButtonIndietro) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listenersButtonEdit = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonEdit().getActionListeners();
        for (ActionListener listener : listenersButtonEdit) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonEdit().removeActionListener(listener);
        }

        ActionListener[] listenersButtonMostraData = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraData().getActionListeners();
        for (ActionListener listener : listenersButtonMostraData) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraData().removeActionListener(listener);
        }

        ActionListener[] listenersButtonMostraAll = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraAll().getActionListeners();
        for (ActionListener listener : listenersButtonMostraAll) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraAll().removeActionListener(listener);
        }

        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        ArrayList<Tavolata> tavolateSala = tavolataDAO.getAllTavolateBySala(sala.getIdSala());
        ArrayList<String> codiciPrenotazioni = new ArrayList<>();
        for (Tavolata t : tavolateSala) {
            codiciPrenotazioni.add(t.toString());
        }
        modelListaSelezione.addAll(codiciPrenotazioni);
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione della tavolata selezionata dalla lista al click del bottone per aprire
         * il frame di edit della tavolata selezionata
         *
         * BUTTON EDIT TAVOLATA
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().getSelectedValue();
                    int tempCodice = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Tavolata tempTavolata = tavolataDAO.getTavolataById(tempCodice);

                    listenersEditPanelPrenotazione(editFrame, mainFrame, tempTavolata, true);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Estrazione della data selezionata per mostrare sulla lista solo le prenotazioni presenti in quella data
         *
         * BUTTON MOSTRA DATA
         * */
        listenerButtonMostraData = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getDate() != null) {
                    ArrayList<Tavolata> tempTavolateSala = tavolataDAO.getAllTavolateBySala(sala.getIdSala());
                    ArrayList<String> tempCodiciPrenotazioni = new ArrayList<>();
                    for (Tavolata t : tempTavolateSala)
                        if (t.getDataArrivo().isEqual(mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getDate()))
                            tempCodiciPrenotazioni.add(t.toString());
                    modelListaSelezione.clear();
                    modelListaSelezione.addAll(tempCodiciPrenotazioni);
                    mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(modelListaSelezione);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraData().addActionListener(listenerButtonMostraData);

        /*
         * Ripristina la lista come prima di premere il Button Mostra Data
         *
         * BUTTON MOSTRA ALL
         * */
        listenerButtonMostraAll = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Tavolata> tempTavolateSala = tavolataDAO.getAllTavolateBySala(sala.getIdSala());
                ArrayList<String> tempCodiciPrenotazioni = new ArrayList<>();
                for (Tavolata t : tempTavolateSala)
                    tempCodiciPrenotazioni.add(t.toString());
                modelListaSelezione.clear();
                modelListaSelezione.addAll(tempCodiciPrenotazioni);
                mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(modelListaSelezione);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraAll().addActionListener(listenerButtonMostraAll);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Sala <- Prenotazioni
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
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelPrenotazioni(mainFrame mainFrame, Tavolo tavolo) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonEdit;
        ActionListener listenerButtonMostraData;
        ActionListener listenerButtonMostraAll;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti ai bottoni per far rimanere
         * presenti solo quelli che verranno aggiunti dal pannello chiamante
         * */
        ActionListener[] listenersButtonIndietro = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listenersButtonIndietro) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listenersButtonEdit = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonEdit().getActionListeners();
        for (ActionListener listener : listenersButtonEdit) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonEdit().removeActionListener(listener);
        }

        ActionListener[] listenersButtonMostraData = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraData().getActionListeners();
        for (ActionListener listener : listenersButtonMostraData) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraData().removeActionListener(listener);
        }

        ActionListener[] listenersButtonMostraAll = mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraAll().getActionListeners();
        for (ActionListener listener : listenersButtonMostraAll) {
            mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraAll().removeActionListener(listener);
        }

        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        ArrayList<Tavolata> tavolateTavolo = tavolataDAO.getAllTavolateByTavolo(tavolo.getCodiceTavolo());
        ArrayList<String> codiciPrenotazioni = new ArrayList<>();
        for (Tavolata t : tavolateTavolo) {
            codiciPrenotazioni.add(t.toString());
        }
        modelListaSelezione.addAll(codiciPrenotazioni);
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione della tavolata selezionata dalla lista al click del bottone per aprire
         * il frame di edit della tavolata selezionata
         *
         * BUTTON EDIT TAVOLATA
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().getSelectedValue();
                    int tempCodice = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Tavolata tempTavolata = tavolataDAO.getTavolataById(tempCodice);

                    listenersEditPanelPrenotazione(editFrame, mainFrame, tempTavolata, true);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Estrazione della data selezionata per mostrare sulla lista solo le prenotazioni presenti in quella data
         *
         * BUTTON MOSTRA DATA
         * */
        listenerButtonMostraData = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getDate() != null) {
                    ArrayList<Tavolata> tempTavolateTavolo = tavolataDAO.getAllTavolateByTavolo(tavolo.getCodiceTavolo());
                    ArrayList<String> tempCodiciPrenotazioni = new ArrayList<>();
                    for (Tavolata t : tempTavolateTavolo)
                        if (t.getDataArrivo().isEqual(mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getDate()))
                            tempCodiciPrenotazioni.add(t.toString());
                    modelListaSelezione.clear();
                    modelListaSelezione.addAll(tempCodiciPrenotazioni);
                    mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(modelListaSelezione);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraData().addActionListener(listenerButtonMostraData);

        /*
         * Ripristina la lista come prima di premere il Button Mostra Data
         *
         * BUTTON MOSTRA ALL
         * */
        listenerButtonMostraAll = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Tavolata> tempTavolateTavolo = tavolataDAO.getAllTavolateByTavolo(tavolo.getCodiceTavolo());
                ArrayList<String> tempCodiciPrenotazioni = new ArrayList<>();
                for (Tavolata t : tempTavolateTavolo)
                    tempCodiciPrenotazioni.add(t.toString());
                modelListaSelezione.clear();
                modelListaSelezione.addAll(tempCodiciPrenotazioni);
                mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(modelListaSelezione);
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonMostraAll().addActionListener(listenerButtonMostraAll);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Tavolo <- Prenotazioni
         *
         * BUTTON INDIETRO
         * */
        listenerButtonIndietro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Tavolo");
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelCamerieri(mainFrame mainFrame, Ristorante ristorante) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonEdit;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti al bottone indietro per far rimanere
         * presente solo quello che verrà aggiunto dal pannello chiamante
         * */
        ActionListener[] listeners = mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listeners) {
            mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listeners2 = mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonEdit().getActionListeners();
        for (ActionListener listener : listeners2) {
            mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonEdit().removeActionListener(listener);
        }

        CameriereDAO cameriereDAO = new CameriereImpl(connection);

        ArrayList<Cameriere> camerieriRistorante = cameriereDAO.getAllCamerieriByRistorante(ristorante.getNome());
        ArrayList<String> codiciNomiCamerieri = new ArrayList<>();
        for (Cameriere c : camerieriRistorante) {
            codiciNomiCamerieri.add(c.toString());
        }
        modelListaSelezione.addAll(codiciNomiCamerieri);
        mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione del cameriere selezionato dalla lista al click del bottone per aprire
         * il frame di edit del cameriere selezionato
         *
         * BUTTON EDIT CAMERIERE
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Cameriere");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Cameriere tempCameriere = cameriereDAO.getCameriereById(tempId);

                    listenersEditPanelCameriere(editFrame, mainFrame, tempCameriere);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Ristorante <- Camerieri
         *
         * BUTTON INDIETRO
         * */
        listenerButtonIndietro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Ristorante");
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelCamerieri(mainFrame mainFrame, Sala sala) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonEdit;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti al bottone indietro per far rimanere
         * presente solo quello che verrà aggiunto dal pannello chiamante
         * */
        ActionListener[] listeners = mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listeners) {
            mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listeners2 = mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonEdit().getActionListeners();
        for (ActionListener listener : listeners2) {
            mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonEdit().removeActionListener(listener);
        }

        CameriereDAO cameriereDAO = new CameriereImpl(connection);

        ArrayList<Cameriere> camerieriSala = cameriereDAO.getAllCamerieriBySala(sala.getIdSala());
        ArrayList<String> codiciNomiCamerieri = new ArrayList<>();
        for (Cameriere c : camerieriSala) {
            codiciNomiCamerieri.add(c.toString());
        }
        modelListaSelezione.addAll(codiciNomiCamerieri);
        mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione del cameriere selezionato dalla lista al click del bottone per aprire
         * il frame di edit del cameriere selezionato
         *
         * BUTTON EDIT CAMERIERE
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Cameriere");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Cameriere tempCameriere = cameriereDAO.getCameriereById(tempId);

                    listenersEditPanelCameriere(editFrame, mainFrame, tempCameriere);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Sala <- Camerieri
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
        mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelCamerieri(mainFrame mainFrame, Tavolo tavolo) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonEdit;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti al bottone indietro per far rimanere
         * presente solo quello che verrà aggiunto dal pannello chiamante
         * */
        ActionListener[] listeners = mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listeners) {
            mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listeners2 = mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonEdit().getActionListeners();
        for (ActionListener listener : listeners2) {
            mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonEdit().removeActionListener(listener);
        }

        CameriereDAO cameriereDAO = new CameriereImpl(connection);

        ArrayList<Cameriere> camerieriTavolo = cameriereDAO.getAllCamerieriByTavolo(tavolo.getCodiceTavolo());
        ArrayList<String> codiciNomiCamerieri = new ArrayList<>();
        for (Cameriere c : camerieriTavolo) {
            codiciNomiCamerieri.add(c.toString());
        }
        modelListaSelezione.addAll(codiciNomiCamerieri);
        mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione del cameriere selezionato dalla lista al click del bottone per aprire
         * il frame di edit del cameriere selezionato
         *
         * BUTTON EDIT CAMERIERE
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Cameriere");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().getSelectedValue();
                    int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                    Cameriere tempCameriere = cameriereDAO.getCameriereById(tempId);

                    listenersEditPanelCameriere(editFrame, mainFrame, tempCameriere);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Sala <- Camerieri
         *
         * BUTTON INDIETRO
         * */
        listenerButtonIndietro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Tavolo");
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelSelezionaTavolo(mainFrame mainFrame, Ristorante ristorante) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonAddPrenotazione;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti al bottone indietro per far rimanere
         * presente solo quello che verrà aggiunto dal pannello chiamante
         * */
        ActionListener[] listeners = mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listeners) {
            mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listeners2 = mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonAddPrenotazione().getActionListeners();
        for (ActionListener listener : listeners2) {
            mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonAddPrenotazione().removeActionListener(listener);
        }

        TavoloDAO tavoloDAO = new TavoloImpl(connection);

        ArrayList<Tavolo> tavoliRistorante = tavoloDAO.getAllTavoliByRistorante(ristorante.getNome());
        ArrayList<String> codiciTavoli = new ArrayList<>();
        for (Tavolo t : tavoliRistorante) {
            codiciTavoli.add(Integer.toString(t.getCodiceTavolo()));
        }
        modelListaSelezione.addAll(codiciTavoli);
        mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione del Tavolo selezionato dalla lista al click del bottone per aprire
         * il frame di add della prenotazione
         *
         * BUTTON ADD PRENOTAZIONE
         * */
        listenerButtonAddPrenotazione = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getListaSelezione().isSelectionEmpty()) {
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel Prenotazione");

                    int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getListaSelezione().getSelectedValue());
                    Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                    listenersAddPanelPrenotazione(addFrame, mainFrame, tempTavolo, true);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonAddPrenotazione().addActionListener(listenerButtonAddPrenotazione);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Ristorante <- Seleziona Tavolo
         *
         * BUTTON INDIETRO
         * */
        listenerButtonIndietro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Ristorante");
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelSelezionaTavolo(mainFrame mainFrame, Sala sala) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonAddPrenotazione;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti al bottone indietro per far rimanere
         * presente solo quello che verrà aggiunto dal pannello chiamante
         * */
        ActionListener[] listeners = mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listeners) {
            mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listeners2 = mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonAddPrenotazione().getActionListeners();
        for (ActionListener listener : listeners2) {
            mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonAddPrenotazione().removeActionListener(listener);
        }

        TavoloDAO tavoloDAO = new TavoloImpl(connection);

        ArrayList<Tavolo> tavoliSala = tavoloDAO.getAllTavoliBySala(sala.getIdSala());
        ArrayList<String> codiciTavoli = new ArrayList<>();
        for (Tavolo t : tavoliSala) {
            codiciTavoli.add(Integer.toString(t.getCodiceTavolo()));
        }
        modelListaSelezione.addAll(codiciTavoli);
        mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione del Tavolo selezionato dalla lista al click del bottone per aprire
         * il frame di add della prenotazione
         *
         * BUTTON ADD PRENOTAZIONE
         * */
        listenerButtonAddPrenotazione = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getListaSelezione().isSelectionEmpty()) {
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel Prenotazione");

                    int tempId = Integer.parseInt((String) mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getListaSelezione().getSelectedValue());
                    Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                    listenersAddPanelPrenotazione(addFrame, mainFrame, tempTavolo, true);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonAddPrenotazione().addActionListener(listenerButtonAddPrenotazione);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Sala <- Seleziona Tavolo
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
        mainFrame.getMainFrameContentPane().getMainPanelSelezionaTavolo().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelClienti(mainFrame mainFrame, Ristorante ristorante) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonEdit;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti al bottone indietro per far rimanere
         * presente solo quello che verrà aggiunto dal pannello chiamante
         * */
        ActionListener[] listeners = mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listeners) {
            mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listeners2 = mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonEdit().getActionListeners();
        for (ActionListener listener : listeners2) {
            mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonEdit().removeActionListener(listener);
        }

        ClienteDAO clienteDAO = new ClienteImpl(connection);

        ArrayList<Cliente> clientiRistorante = clienteDAO.getAllClientiByRistorante(ristorante.getNome());
        ArrayList<String> idCardsNomiCognomi = new ArrayList<>();
        for (Cliente c : clientiRistorante) {
            idCardsNomiCognomi.add(c.toString());
        }
        modelListaSelezione.addAll(idCardsNomiCognomi);
        mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione del cliente selezionato dalla lista al click del bottone per aprire
         * il frame di edit del cliente selezionato
         *
         * BUTTON EDIT CLIENTE
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Cliente");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().getSelectedValue();
                    tempString = tempString.substring(0, tempString.indexOf("#"));
                    Cliente tempCliente = clienteDAO.getClienteById(tempString);

                    listenersEditPanelCliente(editFrame, mainFrame, tempCliente);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Ristorante <- Clienti
         *
         * BUTTON INDIETRO
         * */
        listenerButtonIndietro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Ristorante");
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelClienti(mainFrame mainFrame, Sala sala) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonEdit;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti al bottone indietro per far rimanere
         * presente solo quello che verrà aggiunto dal pannello chiamante
         * */
        ActionListener[] listeners = mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listeners) {
            mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listeners2 = mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonEdit().getActionListeners();
        for (ActionListener listener : listeners2) {
            mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonEdit().removeActionListener(listener);
        }

        ClienteDAO clienteDAO = new ClienteImpl(connection);

        ArrayList<Cliente> clientiSala = clienteDAO.getAllClientiBySala(sala.getIdSala());
        ArrayList<String> idCardsNomiCognomi = new ArrayList<>();
        for (Cliente c : clientiSala) {
            idCardsNomiCognomi.add(c.toString());
        }
        modelListaSelezione.addAll(idCardsNomiCognomi);
        mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione del cliente selezionato dalla lista al click del bottone per aprire
         * il frame di edit del cliente selezionato
         *
         * BUTTON EDIT CLIENTE
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Cliente");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().getSelectedValue();
                    tempString = tempString.substring(0, tempString.indexOf("#"));
                    Cliente tempCliente = clienteDAO.getClienteById(tempString);

                    listenersEditPanelCliente(editFrame, mainFrame, tempCliente);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Sala <- Clienti
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
        mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersMainPanelClienti(mainFrame mainFrame, Tavolo tavolo) {
        ActionListener listenerButtonIndietro;
        ActionListener listenerButtonEdit;
        DefaultListModel<Object> modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear(); //Pulisce la lista di selezione per evitare che si riempia con prenotazioni di altri pannelli

        /*
         * Rimuove i listener precedentemente aggiunti al bottone indietro per far rimanere
         * presente solo quello che verrà aggiunto dal pannello chiamante
         * */
        ActionListener[] listeners = mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonIndietro().getActionListeners();
        for (ActionListener listener : listeners) {
            mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonIndietro().removeActionListener(listener);
        }

        ActionListener[] listeners2 = mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonEdit().getActionListeners();
        for (ActionListener listener : listeners2) {
            mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonEdit().removeActionListener(listener);
        }

        ClienteDAO clienteDAO = new ClienteImpl(connection);

        ArrayList<Cliente> clientiTavolo = clienteDAO.getAllClientiByTavolo(tavolo.getCodiceTavolo());
        ArrayList<String> idCardsNomiCognomi = new ArrayList<>();
        for (Cliente c : clientiTavolo) {
            idCardsNomiCognomi.add(c.toString());
        }
        modelListaSelezione.addAll(idCardsNomiCognomi);
        mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().setModel(modelListaSelezione);

        /*
         * Estrazione del cliente selezionato dalla lista al click del bottone per aprire
         * il frame di edit del cliente selezionato
         *
         * BUTTON EDIT CLIENTE
         * */
        listenerButtonEdit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().isSelectionEmpty()) {
                    editFrame editFrame = new editFrame();
                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                    cardLayout.show(editFrame.getContentPane(), "Panel Cliente");

                    String tempString = (String) mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().getSelectedValue();
                    tempString = tempString.substring(0, tempString.indexOf("#"));
                    Cliente tempCliente = clienteDAO.getClienteById(tempString);

                    listenersEditPanelCliente(editFrame, mainFrame, tempCliente);
                }
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonEdit().addActionListener(listenerButtonEdit);

        /*
         * Bottone che riporta al pannello precedente rispetto al pannello attuale
         *
         * Tavolo <- Clienti
         *
         * BUTTON INDIETRO
         * */
        listenerButtonIndietro = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainFrame.getContentPane().getLayout();
                cardLayout.show(mainFrame.getContentPane(), "Panel Tavolo");
            }
        };
        mainFrame.getMainFrameContentPane().getMainPanelClienti().getButtonIndietro().addActionListener(listenerButtonIndietro);
    }

    private void listenersEditPanelRistorante(editFrame editFrame, mainFrame mainFrame, Ristorante ristorante) {
        ActionListener listenerButtonAddSelezione;
        ActionListener listenerButtonRemoveSelezionato;
        ActionListener listenerButtonModificaSelezionato;
        ActionListener listenerButtonElimina;
        ActionListener listenerButtonConferma;
        ActionListener listenerButtonAnnulla;
        DefaultListModel modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear();

        editFrame.getEditFrameContentPane().getEditPanelRistorante().setTextFieldNome(ristorante.getNome());
        String oldName = ristorante.getNome();

        RistoranteDAO ristoranteDAO = new RistoranteImpl(connection);
        SalaDAO salaDAO = new SalaImpl(connection);

        /*
         * Estrazione nomi e id di tutte le sale e inserimento nel defaultListModel da utilizzare
         * per visualizzare tutte le sale di ristorante su vista
         *
         * LISTA SELEZIONE
         * */
        ArrayList<String> idNomiSale = new ArrayList<>();
        for (Sala s : ristorante.getSale()) {
            idNomiSale.add(s.toString());
        }
        modelListaSelezione.addAll(idNomiSale);
        editFrame.getEditFrameContentPane().getEditPanelRistorante().setModelListaSeleziona(modelListaSelezione);

        if (Arrays.asList(editFrame.getEditFrameContentPane().getEditPanelRistorante().getButtonConferma().getActionListeners()).isEmpty()) {

            /*
            * Istanzia addFrame su Panel Sala per inserire una nuova sala nel ristorante
            * se la sala viene creata correttamente allora la lista selezione in editPanel Ristorante si aggiorna
            *
            * BUTTON ADD SALA
            * */
            listenerButtonAddSelezione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editFrame.dispose();
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel Sala");

                    listenersAddPanelSala(addFrame, mainFrame, ristorante, false);
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelRistorante().getButtonAddSelezione().addActionListener(listenerButtonAddSelezione);

            /*
            * Estrae la sala selezionata dalla lista di selezione per effettuare il delete su database
            * dopo il delete della sala elimina anche l'elemento corrispondente nella lista selezione
            *
            * BUTTON ELIMINA SALA
            * */
            listenerButtonRemoveSelezionato = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                        String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelRistorante().getListaSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Sala tempSala = salaDAO.getSalaById(tempId);

                        if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"" + tempSala.toString() + "\"?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                            salaDAO.deleteSala(tempSala);
                            ristorante.getSale().remove(tempSala);

                            idNomiSale.remove(tempString);
                            modelListaSelezione.clear();
                            modelListaSelezione.addAll(idNomiSale);
                            editFrame.getEditFrameContentPane().getEditPanelRistorante().getListaSelezione().setModel(modelListaSelezione);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelRistorante().getButtonRemoveSelezionato().addActionListener(listenerButtonRemoveSelezionato);

            /*
            * Estrae l'elemento selezionato dalla lista di selezione per aprire il panel di modifica dell'elemento
            *
            * BUTTON MODIFICA SELEZIONATO
            * */
            listenerButtonModificaSelezionato = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelRistorante().getListaSelezione().isSelectionEmpty()) {
                        if (JOptionPane.showConfirmDialog(null, "Le modifiche apportate andranno perse, sei sicuro di voler continuare?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            CardLayout cardLayout = (CardLayout) editFrame.getEditFrameContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Sala");

                            String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelRistorante().getListaSelezione().getSelectedValue();
                            int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                            Sala tempSala = salaDAO.getSalaById(tempId);

                            listenersEditPanelSala(editFrame, mainFrame, tempSala);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelRistorante().getButtonModificaSelezionato().addActionListener(listenerButtonModificaSelezionato);

            /*
            * Elimina il ristorante di cui si sta facendo l'edit e aggiorna la lista selezione di mainPanel Ristorante
            *
            * BUTTON ELIMINA
            * */
            listenerButtonElimina = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"" + ristorante.getNome() + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                        ristoranteDAO.deleteRistorante(ristorante);
                        DefaultListModel tempModel = new DefaultListModel<>();

                        ArrayList<String> nomiRistoranti = new ArrayList<>();
                        for (Ristorante i : ristoranteDAO.getAllRistoranti()) {
                            nomiRistoranti.add(i.getNome());
                        }
                        tempModel.addAll(nomiRistoranti);
                        mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().setModel(tempModel);
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelRistorante().getButtonElimina().addActionListener(listenerButtonElimina);

            /*
            * Aggiorna in database il ristorante di cui si sta facendo l'edit e aggiorna la lista selezione di mainPanel Ristorante
            *
            * BUTTON CONFERMA
            * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler modificare \"" + ristorante.getNome() + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        ArrayList<String> nomiRistoranti = new ArrayList<>();
                        for (Ristorante i : ristoranteDAO.getAllRistoranti()) {
                            nomiRistoranti.add(i.getNome());
                        }

                        if (!nomiRistoranti.contains(editFrame.getEditFrameContentPane().getEditPanelRistorante().getTextFieldNome())) {
                            if(!editFrame.getEditFrameContentPane().getEditPanelRistorante().getTextFieldNome().equals(ristorante.getNome())) {
                                ristorante.setNome(editFrame.getEditFrameContentPane().getEditPanelRistorante().getTextFieldNome());
                                editFrame.dispose();
                                ristoranteDAO.updateRistorante(ristorante, oldName);

                                DefaultListModel tempModel = new DefaultListModel<>();

                                nomiRistoranti.remove(oldName);
                                nomiRistoranti.add(ristorante.getNome());
                                tempModel.addAll(nomiRistoranti);
                                mainFrame.getMainFrameContentPane().getMainPanelRistorante().getListaSelezione().setModel(tempModel);
                            }
                        } else {
                            JOptionPane.showMessageDialog(editFrame, "Il nome selezionato è già in uso");
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelRistorante().getButtonConferma().addActionListener(listenerButtonConferma);

            /*
            * Chiude editFrame senza effettuare nessun cambiamento
            *
            * BUTTON ANNULLA
            * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelRistorante().getButtonAnnulla().addActionListener(listenerButtonAnnulla);
        }
    }

    private void listenersEditPanelSala(editFrame editFrame, mainFrame mainFrame, Sala sala) {
        ActionListener listenerButtonAddSelezione;
        ActionListener listenerButtonRemoveSelezionato;
        ActionListener listenerButtonModificaSelezionato;
        ActionListener listenerButtonElimina;
        ActionListener listenerButtonConferma;
        ActionListener listenerButtonAnnulla;
        DefaultListModel modelListaSelezione = new DefaultListModel<>();
        modelListaSelezione.clear();

        editFrame.getEditFrameContentPane().getEditPanelSala().setTextFieldNome(sala.getNome());

        RistoranteDAO ristoranteDAO = new RistoranteImpl(connection);
        SalaDAO salaDAO = new SalaImpl(connection);
        TavoloDAO tavoloDAO = new TavoloImpl(connection);

        /*
         * Estrazione id di tutti i tavoli e inserimento nel defaultListModel da utilizzare
         * per visualizzare tutti i tavoli di sala su vista
         *
         * LISTA SELEZIONE
         * */
        ArrayList<String> idTavoli = new ArrayList<>();
        for (Tavolo t : sala.getTavoli()) {
            idTavoli.add(Integer.toString(t.getCodiceTavolo()));
        }
        modelListaSelezione.addAll(idTavoli);
        editFrame.getEditFrameContentPane().getEditPanelSala().getListaSelezione().setModel(modelListaSelezione);

        if (Arrays.asList(editFrame.getEditFrameContentPane().getEditPanelSala().getButtonConferma().getActionListeners()).isEmpty()) {

            /*
             * Istanzia addFrame su Panel Tavolo per inserire un nuovo tavolo nella sala.
             * Se il tavolo viene creato correttamente allora la lista selezione in editPanel si aggiorna
             *
             * BUTTON ADD TAVOLO
             * */
            listenerButtonAddSelezione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editFrame.dispose();
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel Tavolo");

                    listenersAddPanelTavolo(addFrame, mainFrame, sala, false);
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelSala().getButtonAddSelezione().addActionListener(listenerButtonAddSelezione);

            /*
            * Estrae il tavolo selezionato dalla lista di selezione per effettuare il delete su database.
            * Dopo il delete del tavolo elimina anche l'elemento corrispondente della lista selezione
            *
            * BUTTON ELIMINA TAVOLO
            * */
            listenerButtonRemoveSelezionato = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelSala().getListaSelezione().isSelectionEmpty()) {
                        int tempId = Integer.parseInt((String) editFrame.getEditFrameContentPane().getEditPanelSala().getListaSelezione().getSelectedValue());
                        Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);
                        if (JOptionPane.showConfirmDialog(null, "Sicuro di voler eliminare \"Tavolo " + tempId + "\"?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                            tavoloDAO.deleteTavolo(tempTavolo);
                            sala.getTavoli().remove(tempTavolo);

                            idTavoli.remove(Integer.toString(tempId));
                            modelListaSelezione.clear();
                            modelListaSelezione.addAll(idTavoli);
                            editFrame.getEditFrameContentPane().getEditPanelSala().getListaSelezione().setModel(modelListaSelezione);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelSala().getButtonRemoveSelezionato().addActionListener(listenerButtonRemoveSelezionato);

            /*
            * Estrae l'elemento selezionato dalla lista di selezione per aprire il panel di modifica dell'elemento
            *
            * BUTTON MODIFICA SELEZIONATO
            * */
            listenerButtonModificaSelezionato = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!editFrame.getEditFrameContentPane().getEditPanelSala().getListaSelezione().isSelectionEmpty()) {
                        if (JOptionPane.showConfirmDialog(null, "Le modifiche apportate andranno perse, sei sicuro di voler continuare?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            CardLayout cardLayout = (CardLayout) editFrame.getEditFrameContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Tavolo");

                            int tempId = Integer.parseInt((String) editFrame.getEditFrameContentPane().getEditPanelSala().getListaSelezione().getSelectedValue());
                            Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                            listenersEditPanelTavolo(editFrame, mainFrame, tempTavolo);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelSala().getButtonModificaSelezionato().addActionListener(listenerButtonModificaSelezionato);

            /*
            * Elimina la sala di cui si sta facendo l'edit e aggiorna la lista selezione di mainPanel Sala
            *
            * BUTTON ELIMINA
            * */
            listenerButtonElimina = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"" + sala + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                        Ristorante tempRistorante = ristoranteDAO.getRistoranteBySala(sala);
                        DefaultListModel tempModel = new DefaultListModel();

                        ArrayList<String> idNomiSale = new ArrayList<>();
                        for (Sala s : tempRistorante.getSale()) {
                            idNomiSale.add(s.toString());
                        }
                        idNomiSale.remove(sala.toString());
                        tempModel.addAll(idNomiSale);
                        mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().setModel(tempModel);
                        salaDAO.deleteSala(sala);
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelSala().getButtonElimina().addActionListener(listenerButtonElimina);

            /*
            * Aggiorna in database la sala di cui si sta facendo l'edit e aggiorna la lista selezione di mainPanel Sala
            *
            * BUTTON CONFERMA
            * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler modificare \"" + sala + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        sala.setNome(editFrame.getEditFrameContentPane().getEditPanelSala().getTextFieldNome());
                        editFrame.dispose();
                        salaDAO.updateSala(sala);
                        Ristorante tempRistorante = ristoranteDAO.getRistoranteBySala(sala);
                        DefaultListModel tempModel = new DefaultListModel();

                        ArrayList<String> idNomiSale = new ArrayList<>();
                        for (Sala s : tempRistorante.getSale()) {
                            idNomiSale.add(s.toString());
                        }
                        tempModel.addAll(idNomiSale);
                        mainFrame.getMainFrameContentPane().getMainPanelSala().getListaSelezione().setModel(tempModel);
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelSala().getButtonConferma().addActionListener(listenerButtonConferma);

            /*
            * Chiude editFrame senza effettuare nessun cambiamento
            *
            * BUTTON ANNULLA
            * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelSala().getButtonAnnulla().addActionListener(listenerButtonAnnulla);
        }
    }

    private void listenersEditPanelTavolo(editFrame editFrame, mainFrame mainFrame, Tavolo tavolo) {
        ActionListener listenerButtonAddSelezionePrenotazione;
        ActionListener listenerButtonAddSelezioneTavoloAdiacente;
        ActionListener listenerButtonRemoveSelezionatoPrenotazione;
        ActionListener listenerButtonRemoveSelezionatoTavoloAdiacente;
        ActionListener listenerButtonModificaSelezionatoPrenotazione;
        ActionListener listenerButtonModificaSelezionatoTavoloAdiacente;
        ActionListener listenerButtonAddTavoloAdiacenteEsistente;
        ActionListener listenerButtonElimina;
        ActionListener listenerButtonConferma;
        ActionListener listenerButtonAnnulla;
        DefaultListModel modelListaSelezionePrenotazione = new DefaultListModel<>();
        DefaultListModel modelListaSelezioneTavoloAdiacente = new DefaultListModel<>();
        modelListaSelezioneTavoloAdiacente.clear();
        modelListaSelezionePrenotazione.clear();

        editFrame.getEditFrameContentPane().getEditPanelTavolo().setTextFieldMaxAvventori(Integer.toString(tavolo.getMaxAvventori()));

        SalaDAO salaDAO = new SalaImpl(connection);
        TavoloDAO tavoloDAO = new TavoloImpl(connection);
        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        /*
        * Estrazione dei codici di tutte le prenotazioni del tavolo e inserimento nel defaultListModel da utilizzare
        * per visualizzare tutte le prenotazioni di tavolo su vista
        *
        * LISTA SELEZIONE PRENOTAZIONI
        * */
        ArrayList<String> codiciPrenotazioni = new ArrayList<>();
        for (Tavolata t : tavolo.getTavolate()) {
            codiciPrenotazioni.add(t.toString());
        }
        modelListaSelezionePrenotazione.addAll(codiciPrenotazioni);

        /*
         * Estrazione dei codici dei tavoli adiacenti al tavolo e inserimento nel defaultListModel da utilizzare
         * per visualizzare tutti i tavoli adiacenti di tavolo su vista
         *
         * LISTA SELEZIONE TAVOLI ADIACENTI
         * */
        ArrayList<String> codiciTavoliAdiacenti = new ArrayList<>();
        for (Tavolo t : tavolo.getTavoliAdiacenti()) {
            codiciTavoliAdiacenti.add(Integer.toString(t.getCodiceTavolo()));
        }
        modelListaSelezioneTavoloAdiacente.addAll(codiciTavoliAdiacenti);

        editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezionePrenotazione().setModel(modelListaSelezionePrenotazione);
        editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezioneTavoloAdiacente().setModel(modelListaSelezioneTavoloAdiacente);

        if (Arrays.asList(editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonConferma().getActionListeners()).isEmpty()) {


            /*
            * Istanzia addFrame su Panel Prenotazione per inserire una nuova prenotazione al tavolo.
            * Se la prenotazione viene creata correttamente allora la lista selezione in editPanel si aggiorna
            *
            * BUTTON ADD PRENOTAZIONE
            * */
            listenerButtonAddSelezionePrenotazione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editFrame.dispose();
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel Prenotazione");

                    listenersAddPanelPrenotazione(addFrame, mainFrame, tavolo, false);
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonAddSelezionePrenotazione().addActionListener(listenerButtonAddSelezionePrenotazione);


            /*
             * Istanzia addFrame su Panel Tavolo per inserire un nuovo tavolo adiacente al tavolo.
             * Se il tavolo viene creato correttamente allora la lista selezione in editPanel si aggiorna
             *
             * BUTTON ADD TAVOLO ADIACENTE
             * */
            listenerButtonAddSelezioneTavoloAdiacente = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editFrame.dispose();
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel Tavolo");

                    Sala tempSala = salaDAO.getSalaByTavolo(tavolo);
                    listenersAddPanelTavoloAdiacente(addFrame, mainFrame, tempSala, tavolo);
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonAddSelezioneTavoloAdiacente().addActionListener(listenerButtonAddSelezioneTavoloAdiacente);

            /*
            * Estrae la prenotazione selezionata dalla lista di selezione per effettuare il delete su database.
            * Dopo il delete della prenotazione elimina anche l'elemento corrispondente dalla lista selezione
            *
            * BUTTON ELIMINA PRENOTAZIONE
            * */
            listenerButtonRemoveSelezionatoPrenotazione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezionePrenotazione().isSelectionEmpty()) {

                        String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezionePrenotazione().getSelectedValue();
                        int tempCodice = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Tavolata tempTavolata = tavolataDAO.getTavolataById(tempCodice);

                        if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"Prenotazione " + tempCodice + "\"?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                            tavolataDAO.deletePrenotazione(tempTavolata);
                            tavolo.getTavolate().remove(tempTavolata);

                            codiciPrenotazioni.remove(tempTavolata.toString());
                            modelListaSelezionePrenotazione.clear();
                            modelListaSelezionePrenotazione.addAll(codiciPrenotazioni);
                            editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezionePrenotazione().setModel(modelListaSelezionePrenotazione);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonRemoveSelezionatoPrenotazione().addActionListener(listenerButtonRemoveSelezionatoPrenotazione);


            /*
             * Estrae il tavolo selezionato dalla lista di selezione per effettuare il delete su database.
             * Dopo il delete del tavolo elimina anche l'elemento corrispondente dalla lista selezione
             *
             * BUTTON ELIMINA TAVOLO ADIACENTE
             * */
            listenerButtonRemoveSelezionatoTavoloAdiacente = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezioneTavoloAdiacente().isSelectionEmpty()) {
                        int tempCodice = Integer.parseInt((String) editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezioneTavoloAdiacente().getSelectedValue());
                        Tavolo tempTavolo = tavoloDAO.getTavoloById(tempCodice);
                        if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"Tavolo " + tempCodice + "\"?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                            tavolo.getTavoliAdiacenti().remove(tempTavolo);
                            tavoloDAO.updateTavolo(tavolo);
                            tavoloDAO.updateTavolo(tempTavolo);

                            codiciTavoliAdiacenti.remove(Integer.toString(tempTavolo.getCodiceTavolo()));
                            modelListaSelezioneTavoloAdiacente.clear();
                            modelListaSelezioneTavoloAdiacente.addAll(codiciTavoliAdiacenti);
                            editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezioneTavoloAdiacente().setModel(modelListaSelezioneTavoloAdiacente);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonRemoveSelezionatoTavoloAdiacente().addActionListener(listenerButtonRemoveSelezionatoTavoloAdiacente);

            /*
            * Estrae l'elemento selezionato dalla lista di selezione per aprire il panel di modifica dell'elemento
            *
            * BUTTON MODIFICA SELEZIONATO PRENOTAZIONE
            * */
            listenerButtonModificaSelezionatoPrenotazione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezionePrenotazione().isSelectionEmpty()) {
                        if (JOptionPane.showConfirmDialog(null, "Le modifiche apportate andranno perse, sei sicuro di voler continuare?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            CardLayout cardLayout = (CardLayout) editFrame.getEditFrameContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");


                            String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezionePrenotazione().getSelectedValue();
                            int tempCodice = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                            Tavolata tempTavolata = tavolataDAO.getTavolataById(tempCodice);

                            listenersEditPanelPrenotazione(editFrame, mainFrame, tempTavolata, false);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonModificaSelezionatoPrenotazione().addActionListener(listenerButtonModificaSelezionatoPrenotazione);

            /*
             * Estrae l'elemento selezionato dalla lista di selezione per aprire il panel di modifica dell'elemento
             *
             * BUTTON MODIFICA SELEZIONATO TAVOLO ADIACENTE
             * */
            listenerButtonModificaSelezionatoTavoloAdiacente = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezioneTavoloAdiacente().isSelectionEmpty()) {
                        if (JOptionPane.showConfirmDialog(null, "Le modifiche apportate andranno perse, sei sicuro di voler continuare?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            int tempId = Integer.parseInt((String) editFrame.getEditFrameContentPane().getEditPanelTavolo().getListaSelezioneTavoloAdiacente().getSelectedValue());
                            Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);
                            editFrame.dispose();

                            editFrame tempEditFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) tempEditFrame.getEditFrameContentPane().getLayout();
                            cardLayout.show(tempEditFrame.getContentPane(), "Panel Tavolo");

                            listenersEditPanelTavolo(tempEditFrame, mainFrame, tempTavolo);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonModificaSelezionatoTavoloAdiacente().addActionListener(listenerButtonModificaSelezionatoTavoloAdiacente);


            /*
            * Istanzia un addFrame a panel TavoloAdiacente Tavolo per aggiungere un tavolo già esistente come tavolo adiacente
            *
            * BUTTON ADD TAVOLOADIACENTE ESISTENTE
            * */
            listenerButtonAddTavoloAdiacenteEsistente = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editFrame.dispose();
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel TavoloAdiacenteToTavolo");

                    Sala tempSala = salaDAO.getSalaByTavolo(tavolo);
                    listenersAddPanelTavoloAdiacenteTavolo(addFrame, mainFrame, tempSala, tavolo);
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonAddTavoloAdiacenteEsistente().addActionListener(listenerButtonAddTavoloAdiacenteEsistente);

            /*
            * Elimina il tavolo di cui si sta facendo l'edit e aggiorna la lista selezione di mainPanel Tavolo
            *
            * BUTTON ELIMINA
            * */
            listenerButtonElimina = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"Tavolo " + tavolo.getCodiceTavolo() + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                        Sala tempSala = salaDAO.getSalaByTavolo(tavolo);
                        DefaultListModel tempModel = new DefaultListModel();

                        ArrayList<String> codiceTavoli = new ArrayList<>();
                        for (Tavolo t : tempSala.getTavoli()) {
                            codiceTavoli.add(Integer.toString(t.getCodiceTavolo()));
                        }
                        tempModel.addAll(codiceTavoli);
                        mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().setModel(tempModel);
                        tavoloDAO.deleteTavolo(tavolo);
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonElimina().addActionListener(listenerButtonElimina);

            /*
            * Aggiorna in database il tavolo di cui si sta facendo l'edit
            * se il numero di MaxAvventori nella textField non è inferiore ad 1
            * o inferiore al numero di avventori più alto nelle prenotazioni future
            * e aggiorna la lista selezione di mainPanel Tavolo
            *
            * BUTTON CONFERMA
            * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler modificare \"Tavolo " + tavolo.getCodiceTavolo() + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        if (Integer.parseInt(editFrame.getEditFrameContentPane().getEditPanelTavolo().getTextFieldMaxAvventori()) < 1) {
                            JOptionPane.showMessageDialog(editFrame, "Non puoi inserire un numero inferiore ad 1 come numero massimo di avventori per il tavolo");
                        }
                        else {
                            int max = 0;
                            for (Tavolata t : tavolo.getTavolate()) {
                                if (t.getDataArrivo().isAfter(LocalDate.now(ZoneId.systemDefault())))
                                    if(t.getClienti().size() > max)
                                        max = t.getClienti().size();
                            }

                            if (Integer.parseInt(editFrame.getEditFrameContentPane().getEditPanelTavolo().getTextFieldMaxAvventori()) > max) {

                                tavolo.setMaxAvventori(Integer.parseInt(editFrame.getEditFrameContentPane().getEditPanelTavolo().getTextFieldMaxAvventori()));
                                editFrame.dispose();

                                tavoloDAO.updateTavolo(tavolo);

                                Sala tempSala = salaDAO.getSalaByTavolo(tavolo);
                                DefaultListModel tempModel = new DefaultListModel<>();

                                ArrayList<String> codiciTavoli = new ArrayList<>();
                                for(Tavolo t : tempSala.getTavoli()){
                                    codiciTavoli.add(Integer.toString(t.getCodiceTavolo()));
                                }
                                tempModel.addAll(codiciTavoli);
                                mainFrame.getMainFrameContentPane().getMainPanelTavolo().getListaSelezione().setModel(tempModel);
                            } else {
                                JOptionPane.showMessageDialog(editFrame, "Non puoi abbassare il numero di avventori massimi " +
                                        "che possono sedersi al tavolo sotto il numero: " + max + "\nEsiste almeno una prenotazione dopo "+ LocalDate.now() +" con numero di avventori pari a " + max);
                            }
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonConferma().addActionListener(listenerButtonConferma);


            /*
            *
            * Chiude editFrame senza effettuare nessun cambiamento
            *
            * BUTTON ANNULLA
            * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelTavolo().getButtonAnnulla().addActionListener(listenerButtonAnnulla);
        }
    }

    private void listenersEditPanelPrenotazione(editFrame editFrame, mainFrame mainFrame, Tavolata tavolata, boolean fromMain) {
        ActionListener listenerButtonAddSelezioneCliente;
        ActionListener listenerButtonAddSelezioneCameriere;
        ActionListener listenerButtonAddClienteEsistente;
        ActionListener listenerButtonAddCameriereEsistente;
        ActionListener listenerButtonRemoveSelezionatoCliente;
        ActionListener listenerButtonRemoveSelezionatoCameriere;
        ActionListener listenerButtonModificaSelezionatoCliente;
        ActionListener listenerButtonModificaSelezionatoCameriere;
        ActionListener listenerButtonElimina;
        ActionListener listenerButtonConferma;
        ActionListener listenerButtonAnnulla;
        DefaultListModel modelListaSelezioneCliente = new DefaultListModel<>();
        DefaultListModel modelListaSelezioneCameriere = new DefaultListModel();
        modelListaSelezioneCliente.clear();
        modelListaSelezioneCameriere.clear();

        TavoloDAO tavoloDAO = new TavoloImpl(connection);
        TavolataDAO tavolataDAO = new TavolataImpl(connection);
        ClienteDAO clienteDAO = new ClienteImpl(connection);
        CameriereDAO cameriereDAO = new CameriereImpl(connection);

        editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().setDate(tavolata.getDataArrivo());
        editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().setLabelCodiceTavolo(Integer.toString(tavoloDAO.getTavoloByTavolata(tavolata).getCodiceTavolo()));

        /*
        * Estrazione clienti per tavolata da inserire nella lista
        *
        * LISTA SELEZIONE CLIENTE
        * */
        ArrayList<Cliente> clientiPrenotazione = clienteDAO.getAllClientiByTavolata(tavolata.getCodicePrenotazione());
        ArrayList<String> idCardsNomiCognomi = new ArrayList<>();
        for (Cliente c : clientiPrenotazione)
            idCardsNomiCognomi.add(c.toString());
        modelListaSelezioneCliente.addAll(idCardsNomiCognomi);
        editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCliente().setModel(modelListaSelezioneCliente);

        /*
        * Estrazione camerieri per tavolata da inserire nella lista
        *
        * LISTA SELEZIONE CAMERIERI
        * */
        ArrayList<Cameriere> camerieriPrenotazione = cameriereDAO.getAllCamerieriByTavolata(tavolata.getCodicePrenotazione());
        ArrayList<String> codiciNomiCamerieri = new ArrayList<>();
        for (Cameriere c : camerieriPrenotazione)
            codiciNomiCamerieri.add(c.toString());
        modelListaSelezioneCameriere.addAll(codiciNomiCamerieri);
        editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCameriere().setModel(modelListaSelezioneCameriere);

        if (Arrays.asList(editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonConferma().getActionListeners()).isEmpty()){
            /*
            * Istanzia addFrame su Panel Cliente per inserire un nuovo cliente nel database e alla prenotazione.
            * Se il cliente viene creato correttamente allora la lista selezione in editPanel si aggiorna
            *
            * BUTTON ADD CLIENTE
            * */
            listenerButtonAddSelezioneCliente = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Tavolo tempTavolo = tavoloDAO.getTavoloByTavolata(tavolata);
                    if (tempTavolo.getMaxAvventori() >= tavolata.getClienti().size() || tavolata.getDataArrivo().isBefore(LocalDate.now(ZoneId.systemDefault()))) {
                        editFrame.dispose();
                        addFrame addFrame = new addFrame();
                        CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                        cardLayout.show(addFrame.getContentPane(), "Panel Cliente");
                        listenersAddPanelCliente(addFrame, mainFrame, tavolata);
                    }
                    else{
                        JOptionPane.showMessageDialog(editFrame, "Il numero massimo di clienti per questo tavolo è stato raggiunto");
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonAddSelezioneCliente().addActionListener(listenerButtonAddSelezioneCliente);

            /*
             * Istanzia addFrame su Panel Cameriere per inserire un nuovo cameriere nel database e alla prenotazione.
             * Se il cameriere viene creato correttamente allora la lista selezione in editPanel si aggiorna
             *
             * BUTTON ADD CAMERIERE
             * */
            listenerButtonAddSelezioneCameriere = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editFrame.dispose();
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel Cameriere");
                    listenersAddPanelCameriere(addFrame, mainFrame, tavolata);
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonAddSelezioneCameriere().addActionListener(listenerButtonAddSelezioneCameriere);

            /*
            * Istanzia un addFrame a panel ClienteToPrenotazione per aggiungere un cliente già esistente alla prenotazione
            *
            * BUTTON ADD CLIENTE ESISTENTE
            * */
            listenerButtonAddClienteEsistente = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Tavolo tempTavolo = tavoloDAO.getTavoloByTavolata(tavolata);
                    if (tempTavolo.getMaxAvventori() >= tavolata.getClienti().size() || tavolata.getDataArrivo().isBefore(LocalDate.now(ZoneId.systemDefault()))) {
                        editFrame.dispose();
                        addFrame addFrame = new addFrame();
                        CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                        cardLayout.show(addFrame.getContentPane(), "Panel ClienteToPrenotazione");
                        listenersAddPanelClienteToPrenotazione(addFrame, mainFrame, tavolata);
                    }
                    else {
                        JOptionPane.showMessageDialog(editFrame, "Il numero massimo di clienti per questo tavolo è stato raggiunto");
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonAddClienteEsistente().addActionListener(listenerButtonAddClienteEsistente);

            /*
             * Istanzia un addFrame a panel CameriereToServizio per aggiungere un cameriere già esistente alla prenotazione
             *
             * BUTTON ADD CAMERIERE ESISTENTE
             * */
            listenerButtonAddCameriereEsistente = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editFrame.dispose();
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel CameriereToServizio");
                    listenersAddPanelCameriereToServizio(addFrame, mainFrame, tavolata);
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonAddCameriereEsistente().addActionListener(listenerButtonAddCameriereEsistente);

            /*
            * Estrae il cliente selezionato dalla lista di selezione per rimuoverlo dalla prenotazione.
            * Dopo la rimozione del cliente elimina anche l'elemento corrispondente dalla lista selezione
            *
            * BUTTON ELIMINA CLIENTE
            * */
            listenerButtonRemoveSelezionatoCliente = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCliente().isSelectionEmpty()){
                        String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCliente().getSelectedValue();
                        tempString = tempString.substring(0, tempString.indexOf("#"));
                        Cliente tempCliente = clienteDAO.getClienteById(tempString);
                        if(JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"" + tempCliente.toString() + "\"?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){

                            tavolata.getClienti().remove(tempCliente);

                            idCardsNomiCognomi.remove(tempCliente.toString());
                            modelListaSelezioneCliente.clear();
                            modelListaSelezioneCliente.addAll(idCardsNomiCognomi);
                            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCliente().setModel(modelListaSelezioneCliente);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonRemoveSelezionatoCliente().addActionListener(listenerButtonRemoveSelezionatoCliente);

            /*
             * Estrae il cameriere selezionato dalla lista di selezione per rimuoverlo dal servizio.
             * Dopo la rimozione del cameriere elimina anche l'elemento corrispondente dalla lista selezione
             *
             * BUTTON ELIMINA CAMERIERE
             * */
            listenerButtonRemoveSelezionatoCameriere = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCameriere().isSelectionEmpty()){
                        String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCameriere().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Cameriere tempCameriere = cameriereDAO.getCameriereById(tempId);
                        if(JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"" + tempCameriere.toString() + "\"?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){

                            tavolata.getCamerieri().remove(tempCameriere);

                            codiciNomiCamerieri.remove(tempCameriere.toString());
                            modelListaSelezioneCameriere.clear();
                            modelListaSelezioneCameriere.addAll(codiciNomiCamerieri);
                            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCameriere().setModel(modelListaSelezioneCameriere);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonRemoveSelezionatoCameriere().addActionListener(listenerButtonRemoveSelezionatoCameriere);

            /*
            * Estrae l'elemento selezionato dalla lista di selezione per aprire il panel di modifica dell'elemento
            *
            * BUTTON MODIFICA SELEZIONATO CLIENTE
            * */
            listenerButtonModificaSelezionatoCliente = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCliente().isSelectionEmpty()) {
                        if(JOptionPane.showConfirmDialog(null, "Le modifiche apportate andranno perse, sei sicuro di voler continuare?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Cliente");

                            String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCliente().getSelectedValue();
                            tempString = tempString.substring(0, tempString.indexOf("#"));
                            Cliente tempCliente = clienteDAO.getClienteById(tempString);

                            listenersEditPanelCliente(editFrame, mainFrame, tempCliente);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonModificaSelezionatoCliente().addActionListener(listenerButtonModificaSelezionatoCliente);

            /*
             * Estrae l'elemento selezionato dalla lista di selezione per aprire il panel di modifica dell'elemento
             *
             * BUTTON MODIFICA SELEZIONATO CAMERIERE
             * */
            listenerButtonModificaSelezionatoCameriere = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCameriere().isSelectionEmpty()) {
                        if (JOptionPane.showConfirmDialog(null, "Le modifiche apportate andranno perse, sei sicuro di voler continuare?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Cameriere");

                            String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getListaSelezioneCameriere().getSelectedValue();
                            int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                            Cameriere tempCameriere = cameriereDAO.getCameriereById(tempId);

                            listenersEditPanelCameriere(editFrame, mainFrame, tempCameriere);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonModificaSelezionatoCameriere().addActionListener(listenerButtonModificaSelezionatoCameriere);

            /*
            * Elimina la prenotazione di cui si sta facendo l'edit
            *
            * BUTTON ELIMINA
            * */
            listenerButtonElimina = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"Prenotazione " + tavolata.getCodicePrenotazione() + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                        tavolataDAO.deletePrenotazione(tavolata);
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonElimina().addActionListener(listenerButtonElimina);

            /*
            * Aggiorna in database la prenotazione di cui si sta facendo l'edit
            * se il giorno selezionato per il tavolo è disponibile;
            * se la prenotazione viene inserita in un giorno successivo a quello odierno
            * viene controllato anche se il numero di clienti della prenotazione superano
            * il numero massimo di avventori che possono sedersi al tavolo.
            * Alla fine estrae la lista di elementi da mainPanelPrenotazioni
            * e aggiorna la visualizzazione della prenotazione modificata
            *
            * BUTTON CONFERMA
            * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getDate() != null) {
                        if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler modificare \"Prenotazione " + tavolata.getCodicePrenotazione() + "\"?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                            LocalDate nuovaData = editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getDate();
                            String oldToString = tavolata.toString();

                            if (!tavolata.getDataArrivo().isEqual(nuovaData)) {
                                boolean checkDisponibile = true;
                                Tavolo tempTavolo = tavoloDAO.getTavoloByTavolata(tavolata);
                                for (Tavolata t : tempTavolo.getTavolate())
                                    if (t.getDataArrivo().isEqual(nuovaData))
                                        checkDisponibile = false;

                                if (checkDisponibile) {
                                    if (nuovaData.isAfter(LocalDate.now(ZoneId.systemDefault()))) {
                                        if (tempTavolo.getMaxAvventori() < tavolata.getClienti().size()) {
                                            if (JOptionPane.showConfirmDialog(null, "\"Tavolo " + tempTavolo.getCodiceTavolo() + " non può ospitare più di " + tempTavolo.getMaxAvventori() + " avventori per le prenotazioni successive alla data odierna" +
                                                            "\nVuoi aumentare i posti di \"Tavolo " + tempTavolo.getCodiceTavolo() + "\" per poter ospitare " + tavolata.getClienti().size() + " avventori?",
                                                    "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                                editFrame.dispose();
                                                tempTavolo.setMaxAvventori(tavolata.getClienti().size());
                                                tavoloDAO.updateTavolo(tempTavolo);
                                                tavolata.setDataArrivo(nuovaData);
                                                tavolataDAO.updatePrenotazione(tavolata);
                                            }
                                        } else {
                                            editFrame.dispose();
                                            tavolata.setDataArrivo(nuovaData);
                                            tavolataDAO.updatePrenotazione(tavolata);
                                        }
                                    } else {
                                        editFrame.dispose();
                                        tavolata.setDataArrivo(nuovaData);
                                        tavolataDAO.updatePrenotazione(tavolata);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(editFrame, "Il tavolo è già occupato per la data scelta");
                                }
                            } else {
                                editFrame.dispose();
                                tavolataDAO.updatePrenotazione(tavolata);
                            }

                            if (fromMain) {
                                DefaultListModel tempModel = (DefaultListModel) mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().getModel();

                                ArrayList<String> tempArrayString = new ArrayList<>();
                                for (int i = 0; i < tempModel.getSize(); i++) {
                                    tempArrayString.add((String) tempModel.getElementAt(i));
                                }

                                tempArrayString.set(tempArrayString.indexOf(oldToString), tavolata.toString());

                                tempModel.clear();
                                tempModel.addAll(tempArrayString);
                                mainFrame.getMainFrameContentPane().getMainPanelPrenotazioni().getListaSelezione().setModel(tempModel);
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(editFrame, "Devi selezionare una data per poter confermare le modifiche");
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonConferma().addActionListener(listenerButtonConferma);


            /*
            * Chiude editFrame senza effettuare nessun cambiamento
            *
            * BUTTON ANNULLA
            * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sei sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelPrenotazioni().getButtonAnnulla().addActionListener(listenerButtonAnnulla);
        }
    }

    private void listenersEditPanelCliente(editFrame editFrame, mainFrame mainFrame, Cliente cliente) {
        ActionListener listenerButtonAddPrenotazione;
        ActionListener listenerButtonRemovePrenotazione;
        ActionListener listenerButtonModificaPrenotazioneSelezionata;
        ActionListener listenerButtonElimina;
        ActionListener listenerButtonConferma;
        ActionListener listenerButtonAnnulla;
        DefaultListModel modelListaSeleziona = new DefaultListModel();
        modelListaSeleziona.clear();

        editFrame.getEditFrameContentPane().getEditPanelCliente().setTextFieldNome(cliente.getNome());
        editFrame.getEditFrameContentPane().getEditPanelCliente().setTextFieldCognome(cliente.getCognome());
        editFrame.getEditFrameContentPane().getEditPanelCliente().setTextFieldCartaID(cliente.getNumeroIdCard());
        editFrame.getEditFrameContentPane().getEditPanelCliente().setTextFieldNumeroTel(cliente.getNumeroTelefono());

        TavolataDAO tavolataDAO = new TavolataImpl(connection);
        ClienteDAO clienteDAO = new ClienteImpl(connection);

        /*
        * Estrazione dei codici di tutte le prenotazioni effettuate dal cliente
        * e inserimento nel defaultListModel da utilizzare per
        * visualizzare tutte le prenotazioni del cliente su vista
        *
        * LISTA SELEZIONE PRENOTAZIONI
        * */
        ArrayList<Tavolata> tavolateCliente = tavolataDAO.getAllTavolateByCliente(cliente.getNumeroIdCard());
        ArrayList<String> codiciPrenotazioni = new ArrayList<>();
        for(Tavolata t : tavolateCliente)
            codiciPrenotazioni.add(t.toString());
        modelListaSeleziona.addAll(codiciPrenotazioni);
        editFrame.getEditFrameContentPane().getEditPanelCliente().getListPrenotazioni().setModel(modelListaSeleziona);

        if(Arrays.asList(editFrame.getEditFrameContentPane().getEditPanelCliente().getButtonConferma().getActionListeners()).isEmpty()){

            /*
            * Istanzia addFrame su Panel PrenotazioneToCliente per inserire una nuova prenotazione (già esistente) al cliente
            *
            * BUTTON ADD PRENOTAZIONE ESISTENTE
            * */
            listenerButtonAddPrenotazione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editFrame.dispose();
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel PrenotazioneToCliente");
                    listenersAddPanelPrenotazioneToCliente(addFrame, mainFrame, cliente);
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCliente().getButtonAddPrenotazione().addActionListener(listenerButtonAddPrenotazione);

            /*
            * Estrae la prenotazione selezionata dalla lista di selezione per rimuoverla dalle prenotazioni del cliente.
            * Dopo la rimozione della prenotazione elimina anche l'elemento corrispondente dalla lista selezione
            *
            * BUTTON RIMUOVI PRENOTAZIONE
            * */
            listenerButtonRemovePrenotazione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!editFrame.getEditFrameContentPane().getEditPanelCliente().getListPrenotazioni().isSelectionEmpty()){
                        String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelCliente().getListPrenotazioni().getSelectedValue();
                        int tempCodice = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Tavolata tempTavolata = tavolataDAO.getTavolataById(tempCodice);

                        if(JOptionPane.showConfirmDialog(null, "Sei sicuro di voler rimuovere \"Prenotazione " + tempCodice +
                                "\" dalle prenotazioni di \"" + cliente + "\"?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            tempTavolata.getClienti().remove(cliente);
                            tavolataDAO.updatePrenotazione(tempTavolata);
                            codiciPrenotazioni.remove(tempTavolata.toString());
                            modelListaSeleziona.clear();
                            modelListaSeleziona.addAll(codiciPrenotazioni);
                            editFrame.getEditFrameContentPane().getEditPanelCliente().getListPrenotazioni().setModel(modelListaSeleziona);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCliente().getButtonRemovePrenotazione().addActionListener(listenerButtonRemovePrenotazione);

            /*
            * Estrae l'elemento selezionato dalla lista di selezione per aprire il panel di modifica dell'elemento
            *
            * BUTTON MODIFICA SELEZIONATO PRENOTAZIONE
            * */
            listenerButtonModificaPrenotazioneSelezionata = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelCliente().getListPrenotazioni().isSelectionEmpty()) {
                        if (JOptionPane.showConfirmDialog(null, "Le modifiche apportate andranno perse, sei sicuro di voler continuare?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            CardLayout cardLayout = (CardLayout) editFrame.getEditFrameContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");

                            String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelCliente().getListPrenotazioni().getSelectedValue();
                            int tempCodice = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                            Tavolata tempTavolata = tavolataDAO.getTavolataById(tempCodice);

                            listenersEditPanelPrenotazione(editFrame, mainFrame, tempTavolata, false);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCliente().getButtonModificaPrenotazioneSelezionata().addActionListener(listenerButtonModificaPrenotazioneSelezionata);

            /*
            * Elimina il cliente di cui si sta facendo l'edit e aggiorna la lista selezione in mainPanelCliente
            *
            * BUTTON ELIMINA
            * */
            listenerButtonElimina = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"" + cliente + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                        clienteDAO.deleteCliente(cliente);

                        DefaultListModel tempModel = (DefaultListModel) mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().getModel();

                        ArrayList<String> tempArrayString = new ArrayList<>();
                        for (int i = 0; i < tempModel.getSize(); i++)
                            tempArrayString.add((String) tempModel.getElementAt(i));

                        if (tempArrayString.contains(cliente.toString())) {
                            tempArrayString.remove(cliente.toString());

                            tempModel.clear();
                            tempModel.addAll(tempArrayString);
                            mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().setModel(tempModel);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCliente().getButtonElimina().addActionListener(listenerButtonElimina);

            /*
            * Aggiorna in database le credenziali del cliente di cui si sta facendo l'edit
            * se il nuovo codice della carta d'identità è diverso da qualsiasi altro in database.
            * Aggiorna la lista selezione di mainPanel Clienti
            *
            * BUTTON CONFERMA
            * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nuovoId = editFrame.getEditFrameContentPane().getEditPanelCliente().getTextFieldCartaID();
                    if (nuovoId.length() == 9) {
                        if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler modificare \"" + cliente + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        ArrayList<Cliente> allClienti = clienteDAO.getAllClienti();

                        String vecchioId = cliente.getNumeroIdCard();
                        String oldToString = cliente.toString();

                            boolean checkDisponibile = true;
                            for (Cliente c : allClienti)
                                if (c.getNumeroIdCard().equals(nuovoId) && !c.getNumeroIdCard().equals(vecchioId)) {
                                    checkDisponibile = false;
                                    break;
                                }

                            if (checkDisponibile) {
                                cliente.setNome(editFrame.getEditFrameContentPane().getEditPanelCliente().getTextFieldNome());
                                cliente.setCognome(editFrame.getEditFrameContentPane().getEditPanelCliente().getTextFieldCognome());
                                cliente.setNumeroIdCard(editFrame.getEditFrameContentPane().getEditPanelCliente().getTextFieldCartaID());
                                cliente.setNumeroTelefono(editFrame.getEditFrameContentPane().getEditPanelCliente().getTextFieldNumeroTel());
                                clienteDAO.updateCliente(cliente, vecchioId);
                                editFrame.dispose();

                                DefaultListModel tempModel = (DefaultListModel) mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().getModel();

                                ArrayList<String> tempArrayString = new ArrayList<>();
                                for (int i = 0; i < tempModel.getSize(); i++){
                                    tempArrayString.add((String) tempModel.getElementAt(i));
                                }

                                if(tempArrayString.contains(oldToString)) {
                                    tempArrayString.set(tempArrayString.indexOf(oldToString), cliente.toString());

                                    tempModel.clear();
                                    tempModel.addAll(tempArrayString);
                                    mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().setModel(tempModel);
                                }
                            } else {
                                JOptionPane.showMessageDialog(editFrame, "Il numero di Carta d'Identità " + nuovoId + " è già in uso");
                            }
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(editFrame, "Il valore inserito come Numero di Carda d'Identità non è valido: deve essere lungo 9 caratteri");
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCliente().getButtonConferma().addActionListener(listenerButtonConferma);

            /*
            * Chiude editFrame senza effettuare nessun cambiamento
            *
            * BUTTON ANNULLA
            * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestr?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        editFrame.dispose();
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCliente().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

        }
    }

    private void listenersEditPanelCameriere(editFrame editFrame, mainFrame mainFrame, Cameriere cameriere) {
        ActionListener listenerButtonAddPrenotazione;
        ActionListener listenerButtonRemovePrenotazione;
        ActionListener listenerButtonModificaPrenotazioneSelezionata;
        ActionListener listenerButtonElimina;
        ActionListener listenerButtonConferma;
        ActionListener listenerButtonAnnulla;
        DefaultListModel modelListaSeleziona = new DefaultListModel();
        modelListaSeleziona.clear();

        editFrame.getEditFrameContentPane().getEditPanelCameriere().setTextFieldNome(cameriere.getNome());
        editFrame.getEditFrameContentPane().getEditPanelCameriere().setTextFieldCognome(cameriere.getCognome());

        TavolataDAO tavolataDAO = new TavolataImpl(connection);
        CameriereDAO cameriereDAO = new CameriereImpl(connection);

        /*
         * Estrazione dei codici di tutte i servizi operati dal cameriere
         * e inserimento nel defaultListModel da utilizzare per
         * visualizzare tutti i servizi del cameriere su vista
         *
         * LISTA SELEZIONE PRENOTAZIONI/SERVIZI
         * */
        ArrayList<Tavolata> tavolateCameriere = tavolataDAO.getAllTavolateByCameriere(cameriere.getCodiceCameriere());
        ArrayList<String> codiciPrenotazioni = new ArrayList<>();
        for (Tavolata t : tavolateCameriere)
            codiciPrenotazioni.add(t.toString());
        modelListaSeleziona.addAll(codiciPrenotazioni);
        editFrame.getEditFrameContentPane().getEditPanelCameriere().getListPrenotazioni().setModel(modelListaSeleziona);

        if (Arrays.asList(editFrame.getEditFrameContentPane().getEditPanelCameriere().getButtonConferma().getActionListeners()).isEmpty()) {

            /*
             * Istanzia addFrame su Panel ServizioToCameriere per inserire un nuovo servizio (già esistente) al cameriere
             *
             * BUTTON ADD PRENOTAZIONE/SERVIZIO ESISTENTE
             * */
            listenerButtonAddPrenotazione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editFrame.dispose();
                    addFrame addFrame = new addFrame();
                    CardLayout cardLayout = (CardLayout) addFrame.getContentPane().getLayout();
                    cardLayout.show(addFrame.getContentPane(), "Panel ServizioToCameriere");
                    listenersAddPanelServizioToCameriere(addFrame, mainFrame, cameriere);
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCameriere().getButtonAddPrenotazione().addActionListener(listenerButtonAddPrenotazione);

            /*
             * Estrae la prenotazione selezionata dalla lista di selezione per rimuoverla dai servizi del cameriere.
             * Dopo la rimozione del servizio elimina anche l'elemento corrispondente dalla lista selezione
             *
             * BUTTON RIMUOVI PRENOTAZIONE
             * */
            listenerButtonRemovePrenotazione = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelCameriere().getListPrenotazioni().isSelectionEmpty()) {
                        String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelCameriere().getListPrenotazioni().getSelectedValue();
                        int tempCodice = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Tavolata tempTavolata = tavolataDAO.getTavolataById(tempCodice);

                        if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler rimuovere \"Servizio " + tempCodice +
                                        "\" dai servizi di \"" + cameriere + "\"?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            tempTavolata.getCamerieri().remove(cameriere);
                            tavolataDAO.updatePrenotazione(tempTavolata);
                            codiciPrenotazioni.remove(tempTavolata.toString());
                            modelListaSeleziona.clear();
                            modelListaSeleziona.addAll(codiciPrenotazioni);
                            editFrame.getEditFrameContentPane().getEditPanelCameriere().getListPrenotazioni().setModel(modelListaSeleziona);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCameriere().getButtonRemovePrenotazione().addActionListener(listenerButtonRemovePrenotazione);

            /*
             * Estrae l'elemento selezionato dalla lista di selezione per aprire il panel di modifica dell'elemento
             *
             * BUTTON MODIFICA SELEZIONATO PRENOTAZIONE/SERVIZIO
             * */
            listenerButtonModificaPrenotazioneSelezionata = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!editFrame.getEditFrameContentPane().getEditPanelCameriere().getListPrenotazioni().isSelectionEmpty()) {
                        if (JOptionPane.showConfirmDialog(null, "Le modifiche apportate andranno perse, sei sicuro di voler continuare?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            CardLayout cardLayout = (CardLayout) editFrame.getEditFrameContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");

                            String tempString = (String) editFrame.getEditFrameContentPane().getEditPanelCameriere().getListPrenotazioni().getSelectedValue();
                            int tempCodice = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                            Tavolata tempTavolata = tavolataDAO.getTavolataById(tempCodice);

                            listenersEditPanelPrenotazione(editFrame, mainFrame, tempTavolata, false);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCameriere().getButtonModificaPrenotazioneSelezionata().addActionListener(listenerButtonModificaPrenotazioneSelezionata);

            /*
             * Elimina il cameriere di cui si sta facendo l'edit e aggiorna la lista selezione in mainPanelCamerieri
             *
             * BUTTON ELIMINA
             * */
            listenerButtonElimina = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare \"" + cameriere + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                        cameriereDAO.deleteCameriere(cameriere);

                        DefaultListModel tempModel = (DefaultListModel) mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().getModel();

                        ArrayList<String> tempArrayString = new ArrayList<>();
                        for (int i = 0; i < tempModel.getSize(); i++)
                            tempArrayString.add((String) tempModel.getElementAt(i));

                        if (tempArrayString.contains(cameriere.toString())) {
                            tempArrayString.remove(cameriere.toString());

                            tempModel.clear();
                            tempModel.addAll(tempArrayString);
                            mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().setModel(tempModel);
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCameriere().getButtonElimina().addActionListener(listenerButtonElimina);

            /*
             * Aggiorna in database le credenziali del cameriere di cui si sta facendo l'edit
             * se il nuovo codice cameriere è diverso da qualsiasi altro in database.
             * Aggiorna la lista selezione di mainPanel Camerieri
             *
             * BUTTON CONFERMA
             * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Sei sicuro di voler modificare \"" + cameriere + "\"?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {


                        String oldToString = cameriere.toString();

                        cameriere.setNome(editFrame.getEditFrameContentPane().getEditPanelCameriere().getTextFieldNome());
                        cameriere.setCognome(editFrame.getEditFrameContentPane().getEditPanelCameriere().getTextFieldCognome());
                        cameriereDAO.updateCameriere(cameriere);
                        editFrame.dispose();

                        if (!Arrays.asList(mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getButtonIndietro().getActionListeners()).isEmpty()) {
                            DefaultListModel tempModel = (DefaultListModel) mainFrame.getMainFrameContentPane().getMainPanelCamerieri().getListaSelezione().getModel();

                            ArrayList<String> tempArrayString = new ArrayList<>();
                            for (int i = 0; i < tempModel.getSize(); i++) {
                                tempArrayString.add((String) tempModel.getElementAt(i));
                            }

                            if (tempArrayString.contains(oldToString)) {
                                tempArrayString.set(tempArrayString.indexOf(oldToString), cameriere.toString());

                                tempModel.clear();
                                tempModel.addAll(tempArrayString);
                                mainFrame.getMainFrameContentPane().getMainPanelClienti().getListaSelezione().setModel(tempModel);
                            }
                        }
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCameriere().getButtonConferma().addActionListener(listenerButtonConferma);

            /*
             * Chiude editFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestr?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        editFrame.dispose();
                    }
                }
            };
            editFrame.getEditFrameContentPane().getEditPanelCameriere().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

        }
    }

    private void listenersAddPanelCameriere(addFrame addFrame, mainFrame mainFrame, Tavolata tavolata) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonConferma;

        CameriereDAO cameriereDAO = new CameriereImpl(connection);

        if (Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelCameriere().getButtonConferma().getActionListeners()).isEmpty()) {
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");
                        listenersEditPanelPrenotazione(editFrame, mainFrame, tavolata, false);
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelCameriere().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae i valori inseriti nelle TextField e aggiunge il nuovo cameriere al database.
             * Istanzia un nuovo editFrame per ritornare a PanelPrenotazione della tavolata aggiornata con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nuovoNome = addFrame.getAddFrameContentPane().getAddPanelCameriere().getTextFieldNome();
                    String nuovoCognome = addFrame.getAddFrameContentPane().getAddPanelCameriere().getTextFieldCognome();
                    if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta del Cameriere " + nuovoNome + " " + nuovoCognome + " al sistema?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        Cameriere cameriere = new Cameriere(nuovoNome, nuovoCognome);
                        cameriereDAO.createCameriere(cameriere, tavolata);
                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");
                        listenersEditPanelPrenotazione(editFrame, mainFrame, tavolata, false);
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelCameriere().getButtonConferma().addActionListener(listenerButtonConferma);
        }
    }

    private void listenersAddPanelCameriereToServizio(addFrame addFrame, mainFrame mainFrame, Tavolata tavolata) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonAggiungi;
        DefaultListModel modelListaSelezione = new DefaultListModel();
        modelListaSelezione.clear();

        RistoranteDAO ristoranteDAO = new RistoranteImpl(connection);
        SalaDAO salaDAO = new SalaImpl(connection);
        TavoloDAO tavoloDAO = new TavoloImpl(connection);
        TavolataDAO tavolataDAO = new TavolataImpl(connection);
        CameriereDAO cameriereDAO = new CameriereImpl(connection);

        Ristorante ristorante = ristoranteDAO.getRistoranteBySala(salaDAO.getSalaByTavolo(tavoloDAO.getTavoloByTavolata(tavolata)));

        ArrayList<Cameriere> camerieriDisponibili = cameriereDAO.getAllCamerieriByRistorante(ristorante.getNome());
        ArrayList<String> codiciNomiCamerieri = new ArrayList<>();
        for (Cameriere c : camerieriDisponibili)
            codiciNomiCamerieri.add(c.toString());
        for (Cameriere c : tavolata.getCamerieri())
            codiciNomiCamerieri.remove(c.toString());
        modelListaSelezione.addAll(codiciNomiCamerieri);
        addFrame.getAddFrameContentPane().getAddPanelCameriereToServizio().setModelListaSeleziona(modelListaSelezione);

        if(Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelCameriereToServizio().getButtonAggiungi().getActionListeners()).isEmpty()){
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Non verrà aggiunto nessun cameriere al servizio, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");
                        listenersEditPanelPrenotazione(editFrame, mainFrame, tavolata, false);
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelCameriereToServizio().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
            * Estrae il cameriere selezionato dalla lista e lo aggiunge alla tavolata di cui poi fa l'update.
            * Istanzia un nuovo editFrame per ritornare a PanelPrenotazione della tavolata aggiornata con il nuovo valore
            *
            * BUTTON CONFERMA
            * */
            listenerButtonAggiungi = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!addFrame.getAddFrameContentPane().getAddPanelCameriereToServizio().getListSelezione().isSelectionEmpty()) {
                        String tempString = (String) addFrame.getAddFrameContentPane().getAddPanelCameriereToServizio().getListSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Cameriere tempCameriere = cameriereDAO.getCameriereById(tempId);
                        if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta del Cameriere " + tempCameriere.getNome() + " " + tempCameriere.getCognome() + " al servizio?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            tavolata.getCamerieri().add(tempCameriere);
                            tavolataDAO.updatePrenotazione(tavolata);
                            addFrame.dispose();
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");
                            listenersEditPanelPrenotazione(editFrame, mainFrame, tavolata, false);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelCameriereToServizio().getButtonAggiungi().addActionListener(listenerButtonAggiungi);
        }
    }

    private void listenersAddPanelCliente(addFrame addFrame, mainFrame mainFrame, Tavolata tavolata) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonConferma;

        ClienteDAO clienteDAO = new ClienteImpl(connection);
        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        if (Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelCliente().getButtonConferma().getActionListeners()).isEmpty()) {
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");
                        listenersEditPanelPrenotazione(editFrame, mainFrame, tavolata, false);
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelCliente().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae i valori inseriti nelle TextField e aggiunge il nuovo cliente al database.
             * Istanzia un nuovo editFrame per ritornare a PanelPrenotazione della tavolata aggiornata con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nuovoNome = addFrame.getAddFrameContentPane().getAddPanelCliente().getTextFieldNome();
                    String nuovoCognome = addFrame.getAddFrameContentPane().getAddPanelCliente().getTextFieldCognome();
                    String nuovoIdCard = addFrame.getAddFrameContentPane().getAddPanelCliente().getTextFieldCartaID();
                    String nuovoNumTel = addFrame.getAddFrameContentPane().getAddPanelCliente().getTextFieldNumerotel();
                    if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta del Cliente " + nuovoNome + " " + nuovoCognome + " al sistema?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        boolean checkDisponibile = true;
                        for (Cliente c : clienteDAO.getAllClienti())
                            if (c.getNumeroIdCard().equals(nuovoIdCard))
                                checkDisponibile = false;

                        if (checkDisponibile) {
                            Cliente cliente = new Cliente(nuovoNome, nuovoCognome, nuovoIdCard, nuovoNumTel);
                            clienteDAO.createCliente(cliente);
                            tavolata.getClienti().add(cliente);
                            tavolataDAO.updatePrenotazione(tavolata);
                            addFrame.dispose();
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");
                            listenersEditPanelPrenotazione(editFrame, mainFrame, tavolata, false);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(addFrame, "Esiste già un cliente con questa carta d'identità");
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelCliente().getButtonConferma().addActionListener(listenerButtonConferma);
        }
    }

    private void listenersAddPanelClienteToPrenotazione(addFrame addFrame, mainFrame mainFrame, Tavolata tavolata) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonAggiungi;
        DefaultListModel modelListaSelezione = new DefaultListModel();
        modelListaSelezione.clear();

        TavolataDAO tavolataDAO = new TavolataImpl(connection);
        ClienteDAO clienteDAO = new ClienteImpl(connection);

        ArrayList<Cliente> clientiDisponibili = clienteDAO.getAllClienti();
        ArrayList<String> idCardsNomiCognomi = new ArrayList<>();
        for (Cliente c : clientiDisponibili)
            idCardsNomiCognomi.add(c.toString());
        for (Cliente c : tavolata.getClienti())
            idCardsNomiCognomi.remove(c.toString());
        modelListaSelezione.addAll(idCardsNomiCognomi);
        addFrame.getAddFrameContentPane().getAddPanelClienteToPrenotazione().setModelListaSeleziona(modelListaSelezione);

        if (Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelClienteToPrenotazione().getButtonAggiungi().getActionListeners()).isEmpty()) {
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Non verrà aggiunto nessun cliente alla prenotazione, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");
                        listenersEditPanelPrenotazione(editFrame, mainFrame, tavolata, false);
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelClienteToPrenotazione().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae il cliente selezionato dalla lista e lo aggiunge alla tavolata di cui poi fa l'update.
             * Istanzia un nuovo editFrame per ritornare a PanelPrenotazione della tavolata aggiornata con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonAggiungi = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!addFrame.getAddFrameContentPane().getAddPanelClienteToPrenotazione().getListSelezione().isSelectionEmpty()) {

                        String tempString = (String) addFrame.getAddFrameContentPane().getAddPanelClienteToPrenotazione().getListSelezione().getSelectedValue();
                        tempString = tempString.substring(0, tempString.indexOf("#"));
                        Cliente tempCliente = clienteDAO.getClienteById(tempString);

                        if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta del Cliente " + tempCliente.getNome() + " " + tempCliente.getCognome() + " alla prenotazione?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            tavolata.getClienti().add(tempCliente);
                            tavolataDAO.updatePrenotazione(tavolata);
                            addFrame.dispose();
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Prenotazione");
                            listenersEditPanelPrenotazione(editFrame, mainFrame, tavolata, false);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelClienteToPrenotazione().getButtonAggiungi().addActionListener(listenerButtonAggiungi);
        }
    }

    private void listenersAddPanelPrenotazione(addFrame addFrame, mainFrame mainFrame, Tavolo tavolo, boolean fromMain) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonConferma;

        TavoloDAO tavoloDAO = new TavoloImpl(connection);
        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        if (Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelPrenotazione().getButtonConferma().getActionListeners()).isEmpty()) {
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        if (!fromMain) {
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Tavolo");
                            listenersEditPanelTavolo(editFrame, mainFrame, tavolo);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelPrenotazione().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae i valori inseriti nel TextField e aggiunge la nuova prenotazione al database.
             * Se la funzione è stata chiamata da editPanelTavolo istanzia un nuovo editFrame per ritornare a PanelTavolo aggiornato con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (addFrame.getAddFrameContentPane().getAddPanelPrenotazione().getDate() != null) {
                        if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta della Prenotazione al sistema?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                            LocalDate data = addFrame.getAddFrameContentPane().getAddPanelPrenotazione().getDate();
                            Tavolata tavolata = new Tavolata(data);

                            boolean checkDisponibile = true;
                            for (Tavolata t : tavolo.getTavolate())
                                if (t.getDataArrivo().isEqual(data))
                                    checkDisponibile = false;
                            if (checkDisponibile) {
                                tavolataDAO.createPrenotazione(tavolata, tavolo);

                                addFrame.dispose();
                                if (!fromMain) {
                                    editFrame editFrame = new editFrame();
                                    CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                                    cardLayout.show(editFrame.getContentPane(), "Panel Tavolo");
                                    listenersEditPanelTavolo(editFrame, mainFrame, tavolo);
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(addFrame, "Il tavolo è già occupato per la data scelta");
                            }
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelPrenotazione().getButtonConferma().addActionListener(listenerButtonConferma);
        }
    }

    private void listenersAddPanelServizioToCameriere(addFrame addFrame, mainFrame mainFrame, Cameriere cameriere) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonAggiungi;
        DefaultListModel modelListaSelezione = new DefaultListModel();
        modelListaSelezione.clear();

        RistoranteDAO ristoranteDAO = new RistoranteImpl(connection);
        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        Ristorante ristorante = ristoranteDAO.getRistoranteByCameriere(cameriere);

        ArrayList<Tavolata> serviziDisponibili = tavolataDAO.getAllTavolateByRistorante(ristorante.getNome());
        ArrayList<String> codiciServizi = new ArrayList<>();
        for (Tavolata t : serviziDisponibili)
            codiciServizi.add(t.toString());
        for (Tavolata t : tavolataDAO.getAllTavolateByCameriere(cameriere.getCodiceCameriere()))
            codiciServizi.remove(t.toString());

        modelListaSelezione.addAll(codiciServizi);
        addFrame.getAddFrameContentPane().getAddPanelServizioToCameriere().setModelListaSeleziona(modelListaSelezione);

        if(Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelServizioToCameriere().getButtonAggiungi().getActionListeners()).isEmpty()){
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Non verrà aggiunto nessun servizio al cameriere, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Cameriere");
                        listenersEditPanelCameriere(editFrame, mainFrame, cameriere);
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelServizioToCameriere().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae il servizio selezionato dalla lista e lo aggiunge al cameriere, poi fa l'update della tavolata.
             * Istanzia un nuovo editFrame per ritornare a PanelCameriere del cameriere aggiornato con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonAggiungi = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!addFrame.getAddFrameContentPane().getAddPanelServizioToCameriere().getListSelezione().isSelectionEmpty()) {

                        String tempString = (String) addFrame.getAddFrameContentPane().getAddPanelServizioToCameriere().getListSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Tavolata tavolata = tavolataDAO.getTavolataById(tempId);

                        if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta del Servizio " + tempId + " al cameriere?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            tavolata.getCamerieri().add(cameriere);
                            tavolataDAO.updatePrenotazione(tavolata);
                            addFrame.dispose();
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Cameriere");
                            listenersEditPanelCameriere(editFrame, mainFrame, cameriere);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelServizioToCameriere().getButtonAggiungi().addActionListener(listenerButtonAggiungi);
        }
    }

    private void listenersAddPanelPrenotazioneToCliente(addFrame addFrame, mainFrame mainFrame, Cliente cliente) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonAggiungi;
        DefaultListModel modelListaSelezione = new DefaultListModel();
        modelListaSelezione.clear();

        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        ArrayList<Tavolata> serviziDisponibili = tavolataDAO.getAllTavolate();
        ArrayList<String> codiciServizi = new ArrayList<>();
        for (Tavolata t : serviziDisponibili)
            codiciServizi.add(t.toString());
        for (Tavolata t : tavolataDAO.getAllTavolateByCliente(cliente.getNumeroIdCard()))
            codiciServizi.remove(t.toString());

        modelListaSelezione.addAll(codiciServizi);
        addFrame.getAddFrameContentPane().getAddPanelPrenotazioneToCliente().setModelListaSeleziona(modelListaSelezione);

        if(Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelPrenotazioneToCliente().getButtonAggiungi().getActionListeners()).isEmpty()){
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Non verrà aggiunto nessuna prenotazione al cliente, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Cliente");
                        listenersEditPanelCliente(editFrame, mainFrame, cliente);
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelPrenotazioneToCliente().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae la prenotazione selezionata dalla lista e lo aggiunge al cliente, poi fa l'update della tavolata.
             * Istanzia un nuovo editFrame per ritornare a PanelCliente del cliente aggiornato con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonAggiungi = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!addFrame.getAddFrameContentPane().getAddPanelPrenotazioneToCliente().getListSelezione().isSelectionEmpty()) {

                        String tempString = (String) addFrame.getAddFrameContentPane().getAddPanelPrenotazioneToCliente().getListSelezione().getSelectedValue();
                        int tempId = Integer.parseInt(tempString.substring(0, tempString.indexOf("#")));
                        Tavolata tavolata = tavolataDAO.getTavolataById(tempId);

                        if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta della Prenotazione " + tempId + " al cliente?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            tavolata.getClienti().add(cliente);
                            tavolataDAO.updatePrenotazione(tavolata);
                            addFrame.dispose();
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Cliente");
                            listenersEditPanelCliente(editFrame, mainFrame, cliente);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelPrenotazioneToCliente().getButtonAggiungi().addActionListener(listenerButtonAggiungi);
        }
    }

    private void listenersAddPanelTavoloAdiacenteTavolo(addFrame addFrame, mainFrame mainFrame, Sala sala, Tavolo tavolo) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonAggiungi;
        DefaultListModel modelListaSelezione = new DefaultListModel();
        modelListaSelezione.clear();

        TavoloDAO tavoloDAO = new TavoloImpl(connection);
        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        ArrayList<String> codiciTavoliStessaSala = new ArrayList<>();
        for (Tavolo t : sala.getTavoli())
            codiciTavoliStessaSala.add(Integer.toString(t.getCodiceTavolo()));
        for (Tavolo t : tavolo.getTavoliAdiacenti())
            codiciTavoliStessaSala.remove(Integer.toString(t.getCodiceTavolo()));
        codiciTavoliStessaSala.remove(Integer.toString(tavolo.getCodiceTavolo()));

        modelListaSelezione.addAll(codiciTavoliStessaSala);
        addFrame.getAddFrameContentPane().getAddPanelTavoloAdiacenteToTavolo().setModelListaSeleziona(modelListaSelezione);

        if(Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelTavoloAdiacenteToTavolo().getButtonAggiungi().getActionListeners()).isEmpty()){
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Non verrà aggiunto nessun tavolo adiacente al tavolo, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Tavolo");
                        listenersEditPanelTavolo(editFrame, mainFrame, tavolo);
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelTavoloAdiacenteToTavolo().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae il potenziale tavolo adiacente selezionato dalla lista e lo aggiunge al tavolo di cui poi fa l'update.
             * Istanzia un nuovo editFrame per ritornare a PanelTavolo del tavolo aggiornato con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonAggiungi = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!addFrame.getAddFrameContentPane().getAddPanelTavoloAdiacenteToTavolo().getListSelezione().isSelectionEmpty()) {

                        int tempId = Integer.parseInt((String) addFrame.getAddFrameContentPane().getAddPanelTavoloAdiacenteToTavolo().getListSelezione().getSelectedValue());
                        Tavolo tempTavolo = tavoloDAO.getTavoloById(tempId);

                        if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta del Servizio " + tempId + " al cameriere?",
                                "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            tavolo.getTavoliAdiacenti().add(tempTavolo);
                            tavoloDAO.updateTavolo(tavolo);
                            addFrame.dispose();
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Tavolo");
                            listenersEditPanelTavolo(editFrame, mainFrame, tavolo);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelTavoloAdiacenteToTavolo().getButtonAggiungi().addActionListener(listenerButtonAggiungi);
        }
    }

    private void listenersAddPanelRistorante(addFrame addFrame, mainFrame mainFrame) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonConferma;

        RistoranteDAO ristoranteDAO = new RistoranteImpl(connection);

        if (Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelRistorante().getButtonConferma().getActionListeners()).isEmpty()) {
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA*/
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelRistorante().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
            * Estrae il valore inserito nella TextField e aggiunge il nuovo ristorante al database.
            *
            * BUTTON CONFERMA
            * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nuovoNome = addFrame.getAddFrameContentPane().getAddPanelRistorante().getTextFieldNome();
                    if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta del Ristorante " + nuovoNome + " al sistema?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        boolean checkDisponibile = true;
                        for (Ristorante r : ristoranteDAO.getAllRistoranti())
                            if (r.getNome().equals(nuovoNome))
                                checkDisponibile = false;

                        if (checkDisponibile) {
                            Ristorante ristorante = new Ristorante(nuovoNome);
                            ristoranteDAO.createRistorante(ristorante);
                            addFrame.dispose();
                            listenersMainPanelRistorante(mainFrame);
                        }
                        else {
                            JOptionPane.showMessageDialog(addFrame, "Esiste già un ristorante con questo nome");
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelRistorante().getButtonConferma().addActionListener(listenerButtonConferma);
        }
    }

    private void listenersAddPanelSala(addFrame addFrame, mainFrame mainFrame, Ristorante ristorante, boolean fromMain) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonConferma;

        SalaDAO salaDAO = new SalaImpl(connection);

        if (Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelSala().getButtonConferma().getActionListeners()).isEmpty()) {
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        if (!fromMain) {
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Ristorante");
                            listenersEditPanelRistorante(editFrame, mainFrame, ristorante);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelSala().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae i valori inseriti nel TextField e aggiunge la nuova sala al database.
             * Se la funzione è stata chiamata da editPanelRistorante istanzia un nuovo editFrame per ritornare a PanelRistorante aggiornato con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta della Sala al sistema?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        Sala sala = new Sala(addFrame.getAddFrameContentPane().getAddPanelSala().getTextFieldNome());
                        salaDAO.createSala(sala, ristorante);

                        addFrame.dispose();
                        if (!fromMain) {
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Ristorante");
                            listenersEditPanelRistorante(editFrame, mainFrame, ristorante);
                        }
                        else {
                            listenersMainPanelSala(mainFrame, ristorante);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelSala().getButtonConferma().addActionListener(listenerButtonConferma);
        }
    }

    private void listenersAddPanelTavolo(addFrame addFrame, mainFrame mainFrame, Sala sala, boolean fromMain) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonConferma;

        TavoloDAO tavoloDAO = new TavoloImpl(connection);

        if (Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelTavolo().getButtonConferma().getActionListeners()).isEmpty()) {
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        if (!fromMain) {
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Sala");
                            listenersEditPanelSala(editFrame, mainFrame, sala);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelTavolo().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae i valori inseriti nel TextField e aggiunge il nuovo tavolo al database.
             * Se la funzione è stata chiamata da editPanelSala istanzia un nuovo editFrame per ritornare a PanelSala aggiornato con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta del Tavolo al sistema?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        tavoloDAO.createTavolo(new Tavolo(Integer.parseInt(addFrame.getAddFrameContentPane().getAddPanelTavolo().getTextFieldMaxAvventori())), sala);

                        addFrame.dispose();
                        if (!fromMain) {
                            editFrame editFrame = new editFrame();
                            CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                            cardLayout.show(editFrame.getContentPane(), "Panel Sala");
                            listenersEditPanelSala(editFrame, mainFrame, sala);
                        }
                        else {
                            listenersMainPanelTavolo(mainFrame, sala);
                        }
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelTavolo().getButtonConferma().addActionListener(listenerButtonConferma);
        }
    }

    private void listenersAddPanelTavoloAdiacente(addFrame addFrame, mainFrame mainFrame, Sala sala, Tavolo tavolo) {
        ActionListener listenerButtonAnnulla;
        ActionListener listenerButtonConferma;

        TavoloDAO tavoloDAO = new TavoloImpl(connection);

        if (Arrays.asList(addFrame.getAddFrameContentPane().getAddPanelPrenotazione().getButtonConferma().getActionListeners()).isEmpty()) {
            /*
             * Chiude addFrame senza effettuare nessun cambiamento
             *
             * BUTTON ANNULLA
             * */
            listenerButtonAnnulla = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Le attuali modifiche andranno perse, sicuro di voler chiudere la finestra?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Tavolo");
                        listenersEditPanelTavolo(editFrame, mainFrame, tavolo);
                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelPrenotazione().getButtonAnnulla().addActionListener(listenerButtonAnnulla);

            /*
             * Estrae i valori inseriti nel TextField e aggiunge il nuovo tavolo al database.
             * Se la funzione è stata chiamata da editPanelTavolo istanzia un nuovo editFrame per ritornare a PanelTavolo aggiornato con il nuovo valore
             *
             * BUTTON CONFERMA
             * */
            listenerButtonConferma = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Confermi l'aggiunta del Tavolo al sistema?",
                            "Attenzione", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        Tavolo tempTavolo = new Tavolo(Integer.parseInt(addFrame.getAddFrameContentPane().getAddPanelTavolo().getTextFieldMaxAvventori()));
                        tavoloDAO.createTavolo(tempTavolo, sala);
                        tavolo.getTavoliAdiacenti().add(tempTavolo);
                        tavoloDAO.updateTavolo(tavolo);

                        addFrame.dispose();
                        editFrame editFrame = new editFrame();
                        CardLayout cardLayout = (CardLayout) editFrame.getContentPane().getLayout();
                        cardLayout.show(editFrame.getContentPane(), "Panel Tavolo");
                        listenersEditPanelTavolo(editFrame, mainFrame, tavolo);

                    }
                }
            };
            addFrame.getAddFrameContentPane().getAddPanelTavolo().getButtonConferma().addActionListener(listenerButtonConferma);
        }
    }

    private void listenersStatisticPanel(statisticFrame statisticFrame, Ristorante ristorante) {
        ActionListener listenerButtonSettimana;
        ActionListener listenerButtonMese;
        ActionListener listenerButtonAnno;
        DefaultCategoryDataset avventoriDataset = statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().getAvventoriDataset();

        TavolataDAO tavolataDAO = new TavolataImpl(connection);

        if (Arrays.asList(statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().getRadioSettimana().getActionListeners()).isEmpty()) {

            listenerButtonSettimana = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LocalDate dataSelezionata = statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().getDate();
                    if (dataSelezionata != null) {
                        avventoriDataset.clear();

                        //estrazione dei dati per la settimana e costruzione del dataset
                        int totAvventori = 0;
                        int counterGiorno = 6;
                        for (int i = 0; i < 7; i++) {
                            int totAvventoriGiornaliero = 0;
                            for (Tavolata t : tavolataDAO.getAllTavolateByRistorante(ristorante.getNome())) {
                                if (t.getDataArrivo().isEqual(dataSelezionata.minusDays(counterGiorno)))
                                    totAvventoriGiornaliero = totAvventoriGiornaliero + t.getClienti().size();
                            }
                            totAvventori = totAvventori + totAvventoriGiornaliero;
                            String giorno = dataSelezionata.minusDays(counterGiorno).getDayOfMonth() + "/" + dataSelezionata.minusDays(counterGiorno).getMonthValue();
                            avventoriDataset.addValue(totAvventoriGiornaliero, "Giorni", giorno);
                            counterGiorno--;
                        }

                        float medAvventori = (float) totAvventori / 7;
                        medAvventori = (float) Math.floor(medAvventori * 100) / 100;

                        //calcolo dei dati per la settimana precedente
                        int totAvventoriPrecedente = 0;
                        for(int i = 0; i < 7; i++){
                            int totAvventoriGiornaliero = 0;
                            for (Tavolata t : tavolataDAO.getAllTavolateByRistorante(ristorante.getNome())){
                                if(t.getDataArrivo().isEqual(dataSelezionata.minusDays(7).minusDays(i)))
                                    totAvventoriGiornaliero = totAvventoriGiornaliero + t.getClienti().size();
                            }
                            totAvventoriPrecedente = totAvventoriPrecedente + totAvventoriGiornaliero;
                        }

                        float medAvventoriPrecedente = (float) totAvventoriPrecedente / 7;
                        medAvventoriPrecedente = (float) Math.floor(medAvventoriPrecedente * 100) / 100;

                        //calcolo delle percentuali e aggiunta dei dati di crescita se definiti
                        if(medAvventoriPrecedente != 0) {
                            float percentualeCrescitaMedia = (medAvventori / medAvventoriPrecedente) * 100 - 100;
                            percentualeCrescitaMedia = (float) Math.floor((percentualeCrescitaMedia * 100) /100);
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriMedi(percentualeCrescitaMedia + "%");
                        }
                        else
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriMedi("ND");

                        if(totAvventoriPrecedente != 0) {
                            float percentualeCrescitaTotale = (float) (totAvventori / totAvventoriPrecedente) * 100 - 100;
                            percentualeCrescitaTotale = (float) Math.floor(percentualeCrescitaTotale * 100) / 100;
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriTotali(percentualeCrescitaTotale + "%");
                        }
                        else{
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriTotali("ND");
                        }

                        statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setNumeroAvventoriTotali(Integer.toString(totAvventori));
                        statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setNumeroAvventoriMedi(Float.toString((medAvventori)));
                    }
                }
            };
            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().getRadioSettimana().addActionListener(listenerButtonSettimana);

            listenerButtonMese = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LocalDate dataSelezionata = statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().getDate();
                    if (dataSelezionata != null){
                        avventoriDataset.clear();

                        //estrazione dei dati per il mese e costruzione del dataset
                        int totAvventori = 0;
                        int counterGiorno = 29;
                        for (int i = 0; i < 30; i++){
                            int totAvventoriGiornaliero = 0;
                            for(Tavolata t : tavolataDAO.getAllTavolateByRistorante(ristorante.getNome())){
                                if (t.getDataArrivo().isEqual(dataSelezionata.minusDays(counterGiorno)))
                                    totAvventoriGiornaliero = totAvventoriGiornaliero + t.getClienti().size();
                            }
                            totAvventori = totAvventori + totAvventoriGiornaliero;
                            String giorno = Integer.toString(dataSelezionata.minusDays(counterGiorno).getDayOfMonth());
                            avventoriDataset.addValue(totAvventoriGiornaliero, "Giorni", giorno);
                            counterGiorno--;
                        }


                        float medAvventori = (float) totAvventori / 30;
                        medAvventori = (float) Math.floor(medAvventori * 100) / 100;

                        //calcolo dei dati per il mese precedente
                        int totAvventoriPrecedente = 0;
                        for(int i = 0; i < 30; i++){
                            int totAvventoriGiornaliero = 0;
                            for (Tavolata t : tavolataDAO.getAllTavolateByRistorante(ristorante.getNome())){
                                if(t.getDataArrivo().isEqual(dataSelezionata.minusDays(30).minusDays(i)))
                                    totAvventoriGiornaliero = totAvventoriGiornaliero + t.getClienti().size();
                            }
                            totAvventoriPrecedente = totAvventoriPrecedente + totAvventoriGiornaliero;
                        }

                        float medAvventoriPrecedente = (float) totAvventoriPrecedente / 30;
                        medAvventoriPrecedente = (float) Math.floor(medAvventoriPrecedente * 100) / 100;

                        if(medAvventoriPrecedente != 0) {
                            float percentualeCrescitaMedia = (medAvventori / medAvventoriPrecedente) * 100 - 100;
                            percentualeCrescitaMedia = (float) Math.floor((percentualeCrescitaMedia * 100) /100);
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriMedi(percentualeCrescitaMedia + "%");
                        }
                        else
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriMedi("ND");

                        if(totAvventoriPrecedente != 0) {
                            float percentualeCrescitaTotale = (float) (totAvventori / totAvventoriPrecedente) * 100 - 100;
                            percentualeCrescitaTotale = (float) Math.floor(percentualeCrescitaTotale * 100) / 100;
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriTotali(percentualeCrescitaTotale + "%");
                        }
                        else{
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriTotali("ND");
                        }

                        statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setNumeroAvventoriTotali(Integer.toString(totAvventori));
                        statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setNumeroAvventoriMedi(Float.toString((medAvventori)));
                    }
                }
            };
            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().getRadioMese().addActionListener(listenerButtonMese);


            listenerButtonAnno = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LocalDate dataSelezionata = statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().getDate();
                    if (dataSelezionata != null){
                        avventoriDataset.clear();

                        //estrazione dei dati per l'anno e costruzione del dataset
                        int totAvventori = 0;
                        int counterMese = 11;
                        for (int i = 0; i < 12; i++){
                            int totAvventoriMensile = 0;
                            for(Tavolata t : tavolataDAO.getAllTavolateByRistorante(ristorante.getNome())){
                                if(t.getDataArrivo().getMonthValue() == dataSelezionata.minusMonths(counterMese).getMonthValue()
                                        && t.getDataArrivo().getYear() == dataSelezionata.getYear()){
                                    totAvventoriMensile = totAvventoriMensile + t.getClienti().size();
                                }
                            }
                            totAvventori = totAvventori + totAvventoriMensile;
                            String mese = Integer.toString(dataSelezionata.minusMonths(counterMese).getMonthValue());
                            avventoriDataset.addValue(totAvventoriMensile, "Mesi", mese);
                            counterMese--;
                        }


                        float medAvventori = (float) totAvventori / 12;
                        medAvventori = (float) Math.floor(medAvventori * 100) / 100;

                        //calcolo dei dati per l'anno precedente
                        int totAvventoriPrecedente = 0;
                        for(int i = 0; i < 12; i++){
                            int totAvventoriMensile = 0;
                            for (Tavolata t : tavolataDAO.getAllTavolateByRistorante(ristorante.getNome())){
                                if(t.getDataArrivo().getMonthValue() == dataSelezionata.minusYears(1).minusMonths(i).getMonthValue()
                                        && t.getDataArrivo().getYear() == dataSelezionata.minusYears(1).getYear())
                                    totAvventoriMensile = totAvventoriMensile + t.getClienti().size();
                            }
                            totAvventoriPrecedente = totAvventoriPrecedente + totAvventoriMensile;
                        }

                        float medAvventoriPrecedente = (float) totAvventoriPrecedente / 12;
                        medAvventoriPrecedente = (float) Math.floor(medAvventoriPrecedente * 100) / 100;

                        if(medAvventoriPrecedente != 0) {
                            float percentualeCrescitaMedia = (medAvventori / medAvventoriPrecedente) * 100 - 100;
                            percentualeCrescitaMedia = (float) Math.floor((percentualeCrescitaMedia * 100) /100);
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriMedi(percentualeCrescitaMedia + "%");
                        }
                        else
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriMedi("ND");

                        if(totAvventoriPrecedente != 0) {
                            float percentualeCrescitaTotale = (float) (totAvventori / totAvventoriPrecedente) * 100 - 100;
                            percentualeCrescitaTotale = (float) Math.floor(percentualeCrescitaTotale * 100) / 100;
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriTotali(percentualeCrescitaTotale + "%");
                        }
                        else{
                            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setCrescitaAvventoriTotali("ND");
                        }

                        statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setNumeroAvventoriTotali(Integer.toString(totAvventori));
                        statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().setNumeroAvventoriMedi(Float.toString((medAvventori)));
                    }
                }
            };
            statisticFrame.getStatisticFrameContentPane().getStatisticPanelRistorante().getRadioAnno().addActionListener(listenerButtonAnno);
        }
    }
}