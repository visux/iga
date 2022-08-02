//////////////////////////////////////////////////////////////////
//                    classe per le Funzioni Utili              //
//////////////////////////////////////////////////////////////////

package iga;

import java.applet.AppletContext;
import java.awt.*;
import javax.swing.text.JTextComponent;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
/**
 *
 * @author demone
 */
public class Utile {

    // titolo del Software
    public static String titolo = "I.G.A. Gestionale Associazioni";
    public static short idApp = 1;

    public static Impostazioni prSistema;
    public static Impostazioni prConnessione;
    public static Impostazioni prCatalog;
    public static Impostazioni prConfigurazione;
    public static boolean Operator = false;
    public static boolean isdesign = false;
    public static boolean ProfiloSistema = false;
    

    public static UserAgg oUtente;
    // per la gestione di applet
    public static AppletContext ac = null;
    public static String codebase;
    // stati della maschera
    public static final int igaINDEFINITO = -1;
    public static final int igaBROWSE = 0;
    public static final int igaINSERIMENTO = 1;
    public static final int igaMODIFICA = 2;

    /**
     *  metodo per portare la maschera al centro schermo
     * @param w
     */
    public static void centraGui(Window w)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = w.getSize();
        if(frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if(frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        w.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    /** controlla la lunghezza del testo
    */
    public static void setPropText(KeyEvent event, int len)
    {
        if(event.isConsumed())
            return;
        char c = event.getKeyChar();
        c = Character.toUpperCase(c);
        event.setKeyChar(c);
        JTextComponent source = (JTextComponent)event.getSource();
        if(source.getText().length() >= len && event.getKeyChar() != '\b' && source.getSelectionStart() == source.getSelectionEnd())
        {
            source.getToolkit().beep();
            event.consume();
        }
    }

    /** controlla la lunghezza del testo
    */
    public static void setLenText(KeyEvent event, int len)
    {
        if(event.isConsumed())
            return;
        char c = event.getKeyChar();
        JTextComponent source = (JTextComponent)event.getSource();
        if(source.getText().length() >= len && event.getKeyChar() != '\b' && source.getSelectionStart() == source.getSelectionEnd())
        {
            source.getToolkit().beep();
            event.consume();
        }
    }

    public static boolean initProprieta()
    {
        prSistema = new Impostazioni();
        if(!prSistema.caricaImpostazioni(getURL("iga.ini")))
        {
            Messaggi.show("File proprieta' di sistema non trovato", 2);
            return false;
        }
        prConnessione = new Impostazioni();
        String nomefileconnect = prSistema.getStringImpostazioni("nomefileconnect");
        if(nomefileconnect.length() == 0)
        {
            System.out.println("Nome file parametri connessione non specificato");
            return false;
        }
        if(!prConnessione.caricaImpostazioni(getURL("" + nomefileconnect)))
        {
            Messaggi.show("File proprieta' connessione " + nomefileconnect + " non trovato", 2);
            return false;
        } else
        {
            prCatalog = new Impostazioni();
            prCatalog.caricaImpostazioni(getURL("prop.ini"));
            return true;
        }
    }

    /**
     * Gestisce gli url
     * @param nomefile
     * @return
     */
    public static URL getURL(String nomefile)
    {
        URL myURL = null;
        if(!Stringhe.isVuota(nomefile))
            try
            {
                myURL = new URL(codebase + nomefile);
            }
            catch(MalformedURLException e)
            {
                Debug.out(e, 2);
            }
        return myURL;
    }

    public static boolean initAmbiente()
    {
        int formatodate = prSistema.getIntImpostazioni("formatodate");
        if(formatodate == -1)
            formatodate = 1;
        if(formatodate > 3)
            formatodate = 1;
        GenerDate.FORMAT_DATE = formatodate;
        int debugvideo = prSistema.getIntImpostazioni("debugvideo");
        if(debugvideo == -1)
            Debug.setLivelloDebugVideo(0);
        else
            Debug.setLivelloDebugVideo(debugvideo);
        int debugfile = prSistema.getIntImpostazioni("debugfile");
        if(debugfile == -1)
            Debug.setLivelloDebugFile(0);
        else
            Debug.setLivelloDebugFile(debugfile);
        if(Debug.getLivelloDebugFile() != 0)
        {
            String nomedebugfile = prSistema.getStringImpostazioni("debugnomefile");
            if(Stringhe.isVuota(nomedebugfile))
                nomedebugfile = "iga.log";
            Debug.setNomeFileLog(nomedebugfile);
        }
        
        return true;
    }

    public static boolean initGlobal()
    {
        return carica();
    }

    public static boolean carica()
    {
        return true;
    }

    /**
     * ricarica la pabella dalla base dati in base al suo nome
     * @param nometabella
     * @return
     */
    public static String getTabella(String nometabella)
    {
        nometabella = nometabella.trim();
        String newnome = prCatalog.getStringImpostazioni(nometabella);
        if(newnome.length() == 0)
            return nometabella.trim();
        else
            return newnome.trim();
    }



}
