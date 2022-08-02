//////////////////////////////////////////////////////////////////
//                    Classi di Connessione                     //
//////////////////////////////////////////////////////////////////

package iga;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;


/**
 *
 * @author demone
 */
public class IGAConn implements IConn{

    private static Hashtable connessioni = new Hashtable();
    private static String aliasConnDBaseDefault = null;

    public IGAConn()
    {
    }

    public static void setAliasConnDBaseDefault(String alias)
        throws Exception
    {
        if(alias == null || alias.trim().length() == 0)
        {
            throw new Exception("Nome alias connessione vuoto");
        } else
        {
            aliasConnDBaseDefault = alias.toUpperCase().trim();
            return;
        }
    }

    public static String getAliasConnDBaseDefault()
    {
        return aliasConnDBaseDefault;
    }

    public static void initStartUpConnDBase(Impostazioni p)
        throws Exception
    {
        String nomialias = p.getProperty("db.connessione.startupalias");
        if(nomialias == null || nomialias.trim().length() == 0)
            throw new Exception("Alias Connessione non trovato");
        String alias;
        IConnDBase c;
        for(StringTokenizer st = new StringTokenizer(nomialias, ","); st.hasMoreElements(); addConnDBase(alias, c))
        {
            alias = st.nextToken().trim();
            c = creaConnDBase(alias, p);
        }

    }

    private static String getKey(String alias, String proprieta)
    {
        return "db.connessione." + alias.trim() + "." + proprieta.trim();
    }

    public static IConnDBase creaConnDBase(String alias, Impostazioni proprieta)
        throws Exception
    {
        int tipo = proprieta.getIntImpostazioni(getKey(alias, "tipo"));
        int tipoDB = proprieta.getIntImpostazioni(getKey(alias, "tipoDB"));
        int sa = proprieta.getIntImpostazioni(getKey(alias, "sempreaperta"));
        boolean sempreaperta = false;
        if(sa == 1)
            sempreaperta = true;
        String testtable = proprieta.getStringImpostazioni(getKey(alias, "testtable"));
        String secondaria = proprieta.getStringImpostazioni(getKey(alias, "secondaria"));
        int isolationLevel = proprieta.getIntImpostazioni(getKey(alias, "isolationlevel"));
        int dbg = proprieta.getIntImpostazioni(getKey(alias, "debug"));
        boolean debug = false;
        if(dbg == 1)
            debug = true;
        String driverDB = proprieta.getStringImpostazioni(getKey(alias, "driverDB"));
        String urlDB = proprieta.getStringImpostazioni(getKey(alias, "urlDB"));
        String userDB = proprieta.getStringImpostazioni(getKey(alias, "userDB"));
        String passwordDB = proprieta.getStringImpostazioni(getKey(alias, "passwordDB"));
        String poolAS = proprieta.getStringImpostazioni(getKey(alias, "poolAS"));
        String driverAS = proprieta.getStringImpostazioni(getKey(alias, "driverAS"));
        String urlAS = proprieta.getStringImpostazioni(getKey(alias, "urlAS"));
        String userAS = proprieta.getStringImpostazioni(getKey(alias, "userAS"));
        String passwordAS = proprieta.getStringImpostazioni(getKey(alias, "passwordAS"));
        String urlt3AS = proprieta.getStringImpostazioni(getKey(alias, "urlt3AS"));
        int numerotentativi = proprieta.getIntImpostazioni(getKey(alias, "tentativi"));
        if(numerotentativi <= 0)
            numerotentativi = 1;
        if(tipo < 1 || tipo > 4)
            throw new Exception("Tipo connessione " + alias + " non valido.");
        if(tipoDB == -1)
            tipoDB = 1;
        if(isolationLevel < 1 || isolationLevel > 4)
            throw new Exception("Isolation level " + alias + " non valido.");
        switch(tipo)
        {
        default:
            break;

        case 1: // '\001'
            if(driverDB.length() == 0)
                throw new Exception("Driver DB " + alias + " non trovato.");
            if(urlDB.length() == 0)
                throw new Exception("URL DB " + alias + " non trovato.");
            if(userDB.length() == 0)
                throw new Exception("User DB " + alias + " non trovato.");
            if(passwordDB.length() == 0)
                throw new Exception("Password DB " + alias + " non trovato.");
            break;

        case 2: // '\002'
            if(driverDB.length() == 0)
                throw new Exception("Driver DB " + alias + " non trovato.");
            if(urlDB.length() == 0)
                throw new Exception("URL DB " + alias + " non trovato.");
            if(userDB.length() == 0)
                throw new Exception("User DB " + alias + " non trovato.");
            if(passwordDB.length() == 0)
                throw new Exception("Password DB " + alias + " non trovato.");
            if(driverAS.length() == 0)
                throw new Exception("Driver AS " + alias + " non trovato.");
            if(urlAS.length() == 0)
                throw new Exception("URL AS " + alias + " non trovato.");
            if(userAS.length() == 0)
                throw new Exception("User AS " + alias + " non trovato.");
            if(passwordAS.length() == 0)
                throw new Exception("Password AS " + alias + " non trovato.");
            if(urlt3AS.length() == 0)
                throw new Exception("URL T3 AS " + alias + " non trovato.");
            break;

        case 3: // '\003'
            if(poolAS.length() == 0)
                throw new Exception("Nome POOL DB " + alias + " non trovato.");
            if(urlAS.length() == 0)
                throw new Exception("URL AS " + alias + " non trovato.");
            if(userAS.length() == 0)
                throw new Exception("User AS " + alias + " non trovato.");
            if(passwordAS.length() == 0)
                throw new Exception("Password AS " + alias + " non trovato.");
            if(urlt3AS.length() == 0)
                throw new Exception("URL T3 AS " + alias + " non trovato.");
            break;

        case 4: // '\004'
            if(poolAS.length() == 0)
                throw new Exception("Nome DATA SOURCE DB " + alias + " non trovato.");
            if(urlAS.length() == 0)
                throw new Exception("URL AS " + alias + " non trovato.");
            if(userAS.length() == 0)
                throw new Exception("User AS " + alias + " non trovato.");
            if(passwordAS.length() == 0)
                throw new Exception("Password AS " + alias + " non trovato.");
            if(urlt3AS.length() == 0)
                throw new Exception("URL T3 AS " + alias + " non trovato.");
            break;
        }
        IConnDBase connectDB = null;
        switch(tipo)
        {
        case 1: // '\001'
            connectDB = new ConnDBaseDiretta(alias, tipoDB, sempreaperta, testtable, isolationLevel, debug, driverDB, urlDB, userDB, passwordDB);
            break;

        case 2: // '\002'
            connectDB = new ConnDBaseWL(alias, tipoDB, sempreaperta, testtable, isolationLevel, debug, driverDB, urlDB, userDB, passwordDB, driverAS, urlAS, userAS, passwordAS, urlt3AS);
            break;

        case 3: // '\003'
            connectDB = new ConnDBaseWLPool(alias, tipoDB, sempreaperta, testtable, isolationLevel, debug, driverAS, urlAS, userAS, passwordAS, urlt3AS, poolAS, numerotentativi);
            break;

        case 4: // '\004'
            connectDB = new ConnDBaseWLPoolRMI(alias, tipoDB, sempreaperta, testtable, isolationLevel, debug, driverAS, urlAS, userAS, passwordAS, urlt3AS, poolAS, numerotentativi);
            break;
        }
        if(secondaria.length() > 0)
            connectDB.setConnDBasesecondaria((ConnDBase)creaConnDBase(secondaria, proprieta));
        return connectDB;
    }

    public static void addConnDBase(String name, IConnDBase c)
    {
        name = name.toUpperCase().trim();
        connessioni.put(name, c);
    }

    public static void removeAllConnDBase()
        throws ConnDBaseException
    {
        String alias;
        for(Enumeration e = connessioni.keys(); e.hasMoreElements(); removeConnDBase(alias))
            alias = ((String)e.nextElement()).trim();

    }

    public static void removeConnDBase(String name)
        throws ConnDBaseException
    {
        name = name.toUpperCase().trim();
        new IGAConn();
        ConnDBase connectDB = (ConnDBase)getConnDBase(name);
        connectDB.disposeConnessione();
        connectDB = null;
        connessioni.remove(name);
    }

    public static IConnDBase getConnDBase(String name)
        throws ConnDBaseException
    {
        name = name.toUpperCase().trim();
        IConnDBase c = (IConnDBase)connessioni.get(name);
        if(c == null)
            throw new ConnDBaseException("ConnectDB con nome " + name + " non trovata");
        else
            return c;
    }

    public IConnDBase getIConnDBase(String name)
        throws ConnDBaseException
    {
        return getConnDBase(name);
    }

    public static IConnDBase getDefaultConnDBase()
        throws ConnDBaseException
    {
        String name = getAliasConnDBaseDefault();
        if(name == null || name.length() == 0)
            throw new ConnDBaseException("ConnectDB default non trovata");
        IConnDBase c = (IConnDBase)connessioni.get(name);
        if(c == null)
            throw new ConnDBaseException("ConnectDB con nome " + name + " non trovata");
        else
            return c;
    }

    public IConnDBase getIDefaultConnDBase()
        throws ConnDBaseException
    {
        return getDefaultConnDBase();
    }

    public static int getTipoDBase()
        throws ConnDBaseException
    {
        return getDefaultConnDBase().getTipoDB();
    }

    public static Connection getConnection()
        throws ConnDBaseException
    {
        return getDefaultConnDBase().getConnection();
    }

    public static void chiudiConnessione()
        throws ConnDBaseException
    {
        getDefaultConnDBase().chiudiConnessione();
    }

    public static void iniziaTransazione()
        throws ConnDBaseException
    {
        getDefaultConnDBase().iniziaTransazione();
    }

    public static void confermaTransazione()
        throws ConnDBaseException
    {
        getDefaultConnDBase().confermaTransazione();
    }

    public static void annullaTransazione()
    {
        try
        {
            getDefaultConnDBase().annullaTransazione();
        }
        catch(Exception exception) { }
    }


}
