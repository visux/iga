//////////////////////////////////////////////////////////////////
//                    Impostazioni e lettura da file            //
//                    delle proprieta' del software             //
//////////////////////////////////////////////////////////////////

package iga;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


/**
 *
 * @author demone
 */
public class Impostazioni extends Properties{

    /**
     *  Costruttore principale
     */
    public Impostazioni()
    {
    }

    public int getIntImpostazioni(String chiave)
    {
        String valore = getProperty(chiave);
        if(valore == null)
            return -1;
        if(valore.length() == 0)
            return -1;
        try
        {
            int ivalore = Integer.valueOf(valore).intValue();
            return ivalore;
        }
        catch(NumberFormatException numberformatexception)
        {
            return -1;
        }
    }

    public String getStringImpostazioni(String chiave)
    {
        String valore = getProperty(chiave);
        if(valore == null)
            return "";
        else
            return valore;
    }

    public String getTab(String nometabella)
    {
        nometabella = nometabella.trim();
        String nomeproprieta = "database.tabella." + nometabella.trim();
        String newnome = getProperty(nomeproprieta);
        if(newnome == null || newnome.length() == 0)
            return nometabella;
        else
            return newnome.trim();
    }

    public boolean caricaImpostazioni(URL url)
    {
        try
        {
            InputStream is = url.openStream();
            load(is);
            is.close();
            return true;
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e);
            return false;
        }
        catch(IOException e1)
        {
            System.out.println(e1);
        }
        return false;
    }
}
