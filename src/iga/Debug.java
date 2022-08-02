//////////////////////////////////////////////////////////////////
//                    Messaggi di Debug                         //
//////////////////////////////////////////////////////////////////
package iga;

import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * Messaggi di Debug in Console
 * @author demone
 */
public class Debug {


    static String utente = "";
    static int livellodebugvideo = 3;
    static int livellodebugfile = 0;
    static String nomefilelog = null;

    /**
     *  Costruttore principale
     */
    public Debug()
    {
    }

    /**
     * settaggio dell'utente in console
     * @param u
     */
    public static void setUtente(String u)
    {
        utente = u;
    }

    /**
     * Ricevi l'utente
     * @return
     */
    public static String getUtente()
    {
        return utente;
    }

    public static void setLivelloDebugVideo(int livello)
    {
        livellodebugvideo = livello;
    }

    public static int getLivelloDebugVideo()
    {
        return livellodebugvideo;
    }

    public static void setLivelloDebugFile(int livello)
    {
        livellodebugfile = livello;
    }

    public static int getLivelloDebugFile()
    {
        return livellodebugfile;
    }

    public static void setNomeFileLog(String nome)
    {
        nomefilelog = nome;
    }

    public static String getNomeFileLog()
    {
        return nomefilelog;
    }

    public static void out(String outString)
    {
        out(outString, 2);
    }

    public static void out(Exception e, int tipo)
    {
        out(e.getMessage(), tipo);
    }

    public static void out(String outString, int tipo)
    {
        if(outString == null)
            return;
        if(outString.trim().length() == 0)
            return;
        if(tipo < 0 || tipo > 2)
            tipo = 2;
        String messaggio = "[" + Calendar.getInstance().getTime() + "]";
        if(getUtente() != null && getUtente().trim().length() > 0)
            messaggio = messaggio + "[" + getUtente().trim() + "]";
        messaggio = messaggio + " " + outString;
        if(isAttivo(livellodebugvideo, tipo))
            System.out.println(messaggio);
        if(isAttivo(livellodebugfile, tipo))
            writeLog(messaggio);
    }

    static void writeLog(String messaggio)
    {
        if(nomefilelog == null || nomefilelog.equals(""))
            return;
        try
        {
            FileOutputStream log = new FileOutputStream(nomefilelog, true);
            log.write((messaggio + "\r\n").getBytes());
            log.close();
            log = null;
        }
        catch(Exception exception) { }
    }

    static boolean isAttivo(int livello, int tipo)
    {
        if(livello == 3)
            return true;
        if(livello == 1 && tipo == 1)
            return true;
        return livello == 2 && tipo == 2;
    }


}
