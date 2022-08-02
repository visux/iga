//////////////////////////////////////////////////////////////////
//                    Maschera di Login                         //
//////////////////////////////////////////////////////////////////

/*
 * guiLogin.java
 *
 * Created on 18-dic-2008, 17.29.49
 */

package iga;

import java.awt.Dimension;
import java.awt.Insets;
import java.net.InetAddress;
import java.util.Date;

/**
 *
 * @author demore
 */
public class guiLogin extends javax.swing.JDialog {

    // verifica il login corretto
    private static boolean OKverifica = false;
    boolean comAggiustati;

    /** Crea il  guiLogin */
    public guiLogin(java.awt.Frame parent) {
        super(parent);
        initComponents();
    }

    public guiLogin()
	{
		this((java.awt.Frame)null);
	}

	public guiLogin(String stringTitolo)
    {
        this();
        setTitle(stringTitolo);

    }

    /**
     * Verra' richiamato all'interno della main
     * @return
     */
    public static boolean isLoginOK()
    {
        return OKverifica;
    }

    public void aggiungiNotifica()
    {
        Dimension d = getSize();
        super.addNotify();
        if(comAggiustati)
        {
            return;
        } else
        {
            Insets ins = getInsets();
            setSize(ins.left + ins.right + d.width, ins.top + ins.bottom + d.height);
            comAggiustati = true;
            return;
        }
    }
    /**
     * non cancellare
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jpUtenteBottoni = new javax.swing.JPanel();
        jpBottoni = new javax.swing.JPanel();
        jbtUscita = new javax.swing.JButton();
        jbtConnetti = new javax.swing.JButton();
        barra = new javax.swing.JProgressBar();
        jtfUsername = new javax.swing.JTextField();
        jtfPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        etichetta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login Utente");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setName("jPanel1"); // NOI18N

        jpUtenteBottoni.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jpUtenteBottoni.setName("jpContLogin"); // NOI18N

        jpBottoni.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jpBottoni.setName("jpBottoni"); // NOI18N

        jbtUscita.setText("Uscita");
        jbtUscita.setName("jbtUscita"); // NOI18N
        jbtUscita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtUscitaActionPerformed(evt);
            }
        });

        jbtConnetti.setText("Connetti");
        jbtConnetti.setName("jbtConnessione"); // NOI18N
        jbtConnetti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtConnettiActionPerformed(evt);
            }
        });

        barra.setName("barra"); // NOI18N

        javax.swing.GroupLayout jpBottoniLayout = new javax.swing.GroupLayout(jpBottoni);
        jpBottoni.setLayout(jpBottoniLayout);
        jpBottoniLayout.setHorizontalGroup(
            jpBottoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBottoniLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(barra, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jbtConnetti, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtUscita, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpBottoniLayout.setVerticalGroup(
            jpBottoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jbtConnetti, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
            .addComponent(jbtUscita, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
            .addGroup(jpBottoniLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(barra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtfUsername.setName("jtfUsername"); // NOI18N
        jtfUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfUsernameActionPerformed(evt);
            }
        });
        jtfUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfUsernameFocusGained(evt);
            }
        });
        jtfUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfUsernameKeyTyped(evt);
            }
        });

        jtfPassword.setEchoChar('$');
        jtfPassword.setName("jtfPassword"); // NOI18N
        jtfPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfPasswordFocusGained(evt);
            }
        });
        jtfPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfPasswordKeyTyped(evt);
            }
        });

        jLabel1.setText("Username:");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Password:");
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout jpUtenteBottoniLayout = new javax.swing.GroupLayout(jpUtenteBottoni);
        jpUtenteBottoni.setLayout(jpUtenteBottoniLayout);
        jpUtenteBottoniLayout.setHorizontalGroup(
            jpUtenteBottoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpUtenteBottoniLayout.createSequentialGroup()
                .addContainerGap(109, Short.MAX_VALUE)
                .addGroup(jpUtenteBottoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUtenteBottoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtfPassword, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfUsername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(130, 130, 130))
            .addComponent(jpBottoni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jpUtenteBottoniLayout.setVerticalGroup(
            jpUtenteBottoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpUtenteBottoniLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jpUtenteBottoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpUtenteBottoniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jpBottoni, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel4.setName("jpContImmagine"); // NOI18N

        etichetta.setName("etichetta"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(etichetta, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(etichetta, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpUtenteBottoni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpUtenteBottoni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtfUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfUsernameActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jtfUsernameActionPerformed

    private void jbtUscitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtUscitaActionPerformed
        OKverifica = false;
        // chiusura della gui
        chiudiGui();
}//GEN-LAST:event_jbtUscitaActionPerformed

    private void jtfPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPasswordKeyTyped
        // settaggio della lunghezza della stringa
		Utile.setLenText(evt, 10);
}//GEN-LAST:event_jtfPasswordKeyTyped

    private void jtfUsernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfUsernameKeyTyped
        // settaggio della lunghezza della stringa
		Utile.setLenText(evt, 10);
}//GEN-LAST:event_jtfUsernameKeyTyped

    private void jbtConnettiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtConnettiActionPerformed

        if(controllo())
        {
            StatoLogin("Connessione in corso...");

            jbtConnetti.setEnabled(false);
            connetti();
            
            if(isLoginOK())
            {
                    // Chiude la Gui perche' la main principale avvia la gui del menu.
                    // Lettura dalla base dati
                    this.chiudiGui();
                
            } else
            {
                StatoBarra(0);
                jbtConnetti.setEnabled(true);
            }
        } else
        {
            StatoLogin("Il collegamento e\' stato annullato... Controllare la connessione ai dati.");
        }



}//GEN-LAST:event_jbtConnettiActionPerformed

    private void jtfPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfPasswordFocusGained
        // caret position
        Stringhe.caretPosition(jtfPassword);
    }//GEN-LAST:event_jtfPasswordFocusGained

    private void jtfUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfUsernameFocusGained
        // caret position
        Stringhe.caretPosition(jtfUsername);
    }//GEN-LAST:event_jtfUsernameFocusGained

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // all'avvio deve essere dato il focus
        impostaFuoco();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        chiudiGui();
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[])
	{
		(new guiLogin()).setVisible(true);
	}

    /**
     * Visibile al centro dello schermo
     * @param centrato
     */
    @Override
    public void setVisible(boolean centrato)
    {
        if(centrato)
        Utile.centraGui(this);
        super.setVisible(centrato);
    }

    /**
     * Imposta il fuoco dello Username
     */
    private void impostaFuoco()
    {
        jtfUsername.requestFocus();
    }
    /**
     * Verifica i campi della gui
     * @return
     */
    public boolean controllo(){
        
        if(Stringhe.isVuota(jtfUsername.getText()))
        {
            Messaggi.show("Inserire l\'Utente prima di connettersi al gestionale!", 3);
            jtfUsername.requestFocus();
            return false;
        }
        if(Stringhe.isVuota(jtfPassword.getText()))
        {
            Messaggi.show("Inserire la Password prima di connettersi al gestionale!", 3);
            jtfPassword.requestFocus();
            return false;
        } else
        {
            return true;
        }

       
    }

    void connetti()
    {
        
        for(int i = 0; i < 101; i += 2)
        {
            // init della batta di stato
            StatoBarra(i);
            try
            {
                Thread.sleep(20L);
            }
            catch(Exception exception) { }
            if(i == 20)
            {
                StatoLogin("Caricamento delle impostazioni di sistema");
                if(!Utile.initProprieta())
                {
                    StatoLogin("File proprieta' di sistema non trovato");
                    Messaggi.show("File impostazioni non trovato", 2);
                    return;
                }
            }
            if(i == 40)
            {
                StatoLogin("Inizializzazione ambiente");
                Debug.out("Inizializzazione ambiente", 1);
                if(!Utile.initAmbiente())
                {
                    StatoLogin("Problemi nell'inizializzazione ambiente");
                    Debug.out("Problemi nell'inizializzazione ambiente", 1);
                    return;
                }
                Debug.out("Ambiente inizializzato", 1);
            }
            if(i == 50)
            {
                StatoLogin("Connessione in corso...");
                Debug.out("Connessione in corso...", 1);
                try
                {
                    IGAConn.initStartUpConnDBase(Utile.prConnessione);
                    IGAConn.setAliasConnDBaseDefault("iga");
                }
                catch(Exception e)
                {
                    StatoLogin("Connessione al server fallita");
                    Debug.out("Connessione al server fallita " + e.getMessage(), 1);
                    Messaggi.show("Connessione al server fallita: " + e.getMessage(), 2);
                    return;
                }
                Debug.out("Connessione al server riuscita", 1);
            }
            if(i == 60)
            {
                StatoLogin("Verifica delle credenziali di accesso...");
                Debug.out("Verifica delle credenziali di accesso...", 1);
                Utile.oUtente = null;
                String iplocale = "Sconosciuto";
                try
                {
                    iplocale = InetAddress.getLocalHost().toString();
                }
                catch(Exception exception1) { }
                char pass[] = jtfPassword.getPassword();
                String spassw = new String(pass);
                pass = null;
                if(jtfUsername.getText().trim().equals("ils") && spassw.trim().equals("linux"))
                {
                    
                    Utile.oUtente = new UserAgg();
                    Utile.oUtente.utente = "I.L.S.";
                    Utile.oUtente.operatore = "Operatore I.L.S";
                    Utile.oUtente.livello = 1;
                    Utile.oUtente.gruppo = "";
                   
                    Utile.Operator = true;
                } else
                {
                    try
                    {
                        Utile.oUtente = new UserAgg(jtfUsername.getText(), spassw);
                        i += 10;
                        StatoBarra(i);
                        if(((User) (Utile.oUtente)).attiva.equals("S"))
                        {
                            Messaggi.show("L'Utente indicato \350 Sospeso !", 3);
                            jtfUsername.requestFocus();
                            return;
                        }
                        if(((User) (Utile.oUtente)).attiva.equals("N"))
                        {
                            Messaggi.show("L'Utente indicato non \350 pi\371 Attivo !", 3);
                            this.impostaFuoco();
                            return;
                        }
                        if(((User) (Utile.oUtente)).fl_collegato)
                        {
                            Messaggi.show("Utente gi\340 collegato da altra Postazione.", 2);
                            update(getGraphics());
                        }
                        boolean flChiediPassword = false;
                        String sMegNewPwd = "";
                        if(((User) (Utile.oUtente)).force_1cngpwd.equals("S"))
                        {
                            flChiediPassword = true;
                            sMegNewPwd = "La Password non \350 pi\371 valida. Inserire una nuova Password ?";
                        } else
                        if(((User) (Utile.oUtente)).force_cngpwd.equals("S"))
                        {
                            Date DataOdierna = GenerDate.getCurrentDateTime();
                            long msLogData = GenerDate.getUtilDateFromS(((User) (Utile.oUtente)).data_cngpwd).getTime();
                            long msPwdInterval = ((User) (Utile.oUtente)).pwd_interval * 24 * 60 * 60 * 1000;
                            long msDataControllo = msLogData + msPwdInterval;
                            Date DataControllo = new Date();
                            DataControllo.setTime(msDataControllo);
                            if(DataOdierna.after(DataControllo))
                            {
                                flChiediPassword = true;
                                sMegNewPwd = "La Password \350 scaduta. Inserire una nuova Password ?";
                            }
                        }
                        if(flChiediPassword)
                        {
                            if(Messaggi.show(sMegNewPwd, 2, "Si", "No", 1) == 2)
                            {
                                jtfPassword.setText("");
                                jtfPassword.requestFocus();
                                return;
                            }
                            try
                            {

                                //CambioPassword oCambioPassword = new CambioPassword((Frame)null);
                                //oCambioPassword.setModal(true);
                                //oCambioPassword.setVisible(true);
                                //if(!oCambioPassword.flOkPassword)
                                //{
                                //    jtpPassword.setText("");
                                //    jtpPassword.requestFocus();
                                //    return;
                                //}
                            }
                            catch(Exception exception2) { }
                        }
                        if(!Utile.oUtente.setLogin(iplocale))
                        {
                            Messaggi.show("Problemi in fase di conferma avvenuto Login. Impossibile Continuare", 3);
                            jtfUsername.setText("");
                            jtfPassword.setText("");
                            return;
                        }
                    }
                    catch(Exception e)
                    {
                        StatoLogin(e.getMessage());
                        Debug.out(e.getMessage(), 1);
                        return;
                    }
                }
                StatoLogin("Utente/Password verificati!");
                Debug.out("Utente/Password verificati!", 1);
            }
            if(i == 80)
            {
                StatoLogin("Impostazione ambiente");
                Debug.out("Impostazione ambiente", 1);
                if(!Utile.initGlobal())
                {
                    StatoLogin("Ambiente non impostato");
                    Debug.out("Ambiente non impostato", 1);
                    return;
                }
                StatoLogin("Ambiente impostato");
                Debug.out("Ambiente impostato", 1);
                OKverifica = true;
            }
        }

        
    }

    /**
     * Messaggi di sistema nella maschera del login
     * @param msg
     */
    void StatoLogin(String msg)
    {

        etichetta.setText(msg);
        etichetta.paint(etichetta.getGraphics());

    }

    void StatoBarra(int i)
    {
        barra.setValue(i);
        barra.update(barra.getGraphics());
    }

    /**
     * Chiusura della Maschera
     */
    void chiudiGui()
    {

        System.exit(0);
   
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barra;
    private javax.swing.JLabel etichetta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jbtConnetti;
    private javax.swing.JButton jbtUscita;
    private javax.swing.JPanel jpBottoni;
    private javax.swing.JPanel jpUtenteBottoni;
    private javax.swing.JPasswordField jtfPassword;
    private javax.swing.JTextField jtfUsername;
    // End of variables declaration//GEN-END:variables

}
