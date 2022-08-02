//////////////////////////////////////////////////////////////////
//                    Classi di Connessione                     //
//////////////////////////////////////////////////////////////////

package iga;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author demore
 */
public class ConnDBaseWL extends ConnDBase{

    private String driverAS;
    private String urlAS;
    private String userAS;
    private String passwordAS;
    private String urlt3AS;

    public ConnDBaseWL()
    {
    }

    public int getTipoConnessione()
    {
        return 2;
    }

    public ConnDBaseWL(String alias, int tipoDB, boolean sempreaperta, String testtable, int isolationlevel, boolean debug, String driverDB,
            String urlDB, String userDB, String passwordDB, String driverAS, String urlAS, String userAS, String passwordAS,
            String urlt3AS)
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
        this.driverAS = driverAS;
        this.urlAS = urlAS;
        this.userAS = userAS;
        this.passwordAS = passwordAS;
        this.urlt3AS = urlt3AS;
        try
        {
            Class.forName(this.driverAS).newInstance();
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            throw new ConnDBaseNoConnException("Driver AS non valido o non trovato");
        }
        catch(InstantiationException instantiationexception)
        {
            throw new ConnDBaseNoConnException("Driver AS non valido o non trovato");
        }
        catch(IllegalAccessException illegalaccessexception)
        {
            throw new ConnDBaseNoConnException("Driver AS non valido o non trovato");
        }
    }

    protected Connection initConnessione()
        throws ConnDBaseException
    {
        if(getDebug())
            Debug.out("INIT CONNESSIONE WL " + getAlias() + "[" + getStato() + "]", 1);
        try
        {
            if(getUserDB() == null || getPasswordDB() == null || urlAS == null || getDriverDB() == null || getUrlDB() == null || userAS == null || passwordAS == null || urlt3AS == null)
                throw new ConnDBaseNoConnException("Impossibile aprire la connessione: parametri mancanti");
            Properties dbprops = new Properties();
            dbprops.put("user", getUserDB());
            dbprops.put("password", getPasswordDB());
            Properties t3props = new Properties();
            t3props.put("weblogic.t3.dbprops", dbprops);
            t3props.put("weblogic.t3.serverURL", urlAS);
            t3props.put("weblogic.t3.driverClassName", getDriverDB());
            t3props.put("weblogic.t3.driverURL", getUrlDB());
            t3props.put("weblogic.t3.user", userAS);
            t3props.put("weblogic.t3.password", passwordAS);
            Connection c = DriverManager.getConnection(urlt3AS, t3props);
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


}
