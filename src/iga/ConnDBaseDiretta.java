//////////////////////////////////////////////////////////////////
//                    Classi di Connessione                     //
//////////////////////////////////////////////////////////////////

package iga;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author demone
 */
public class ConnDBaseDiretta extends ConnDBase{

    public ConnDBaseDiretta()
    {
    }

    public int getTipoConnessione()
    {
        return 1;
    }

    public ConnDBaseDiretta(String alias, int tipoDB, boolean sempreaperta, String testtable, int isolationlevel, boolean debug, String driverDB,
            String urlDB, String userDB, String passwordDB)
        throws ConnDBaseException
    {
        setAlias(alias);
        setTipoDB(tipoDB);
        setSempreAperta(sempreaperta);
        setTestTable(testtable);
        setIl(isolationlevel);
        setDebug(debug);
        setDriverDB(driverDB);
        setUrlDB(urlDB);
        setUserDB(userDB);
        setPasswordDB(passwordDB);
        try
        {
            Class.forName(getDriverDB()).newInstance();
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new ConnDBaseNoConnException("Driver DB non disponibile");
        }
        catch(InstantiationException instantiationexception)
        {
            throw new ConnDBaseNoConnException("Driver DB non disponibile");
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            throw new ConnDBaseNoConnException("Driver DB non disponibile");
        }
    }

    protected Connection initConnessione()
        throws ConnDBaseException
    {
        if(getDebug())
            Debug.out("INIT CONNESSIONE DIRETTA " + getAlias() + "[" + getStato() + "]", 1);
        try
        {
            if(getUserDB() == null || getPasswordDB() == null || getUrlDB() == null)
                throw new ConnDBaseNoConnException("Impossibile aprire la connessione: parametri mancanti");
            Properties p = new Properties();
            p.put("user", getUserDB());
            p.put("password", getPasswordDB());
            Connection c = DriverManager.getConnection(getUrlDB(), p);
            if(getIsolationLevel() != -1)
                c.setTransactionIsolation(getIsolationLevel());
            c.setAutoCommit(true);
            return c;
        }
        catch(Exception exception)
        {
            throw new ConnDBaseNoConnException("Impossibile aprire la connessione");
        }
    }

    public IConnDBase getConnectDBsecondaria() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setConnectDBsecondaria(IConnDBase iconnectdb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
