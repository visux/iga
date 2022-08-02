//////////////////////////////////////////////////////////////////
//                    Classi di Connessione                     //
//////////////////////////////////////////////////////////////////

package iga;

import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

/**
 *
 * @author demone
 */
public class ConnDBaseWLPool extends ConnDBase {

    private String driverAS;
    private String urlAS;
    private String poolAS;
    private String userAS;
    private String passwordAS;
    private String urlt3AS;
    private int numerotentativi;
    Driver myDriver;

    public ConnDBaseWLPool()
    {
        numerotentativi = 1;
    }

    public int getTipoConnessione()
    {
        return 3;
    }

    public ConnDBaseWLPool(String alias, int tipoDB, boolean sempreaperta, String testtable, int isolationlevel, boolean debug, String driverAS,
            String urlAS, String userAS, String passwordAS, String urlt3AS, String poolAS, int tentativi)
        throws ConnDBaseException
    {
        numerotentativi = 1;
        setAlias(alias);
        setTipoDB(tipoDB);
        setSempreAperta(sempreaperta);
        setTestTable(testtable);
        setIl(isolationlevel);
        setDebug(debug);
        this.driverAS = driverAS;
        this.urlAS = urlAS;
        this.poolAS = poolAS;
        this.userAS = userAS;
        this.passwordAS = passwordAS;
        this.urlt3AS = urlt3AS;
        if(tentativi <= 0)
            numerotentativi = 1;
        else
            numerotentativi = tentativi;
        try
        {
            myDriver = (Driver)Class.forName(this.driverAS).newInstance();
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
            Debug.out("INIT CONNESSIONE WL POOL " + getAlias() + "[" + getStato() + "]", 1);
        if(urlAS == null || poolAS == null || userAS == null || passwordAS == null || urlt3AS == null)
            throw new ConnDBaseNoConnException("Impossibile aprire la connessione: parametri mancanti");
        Properties t3props = new Properties();
        t3props.put("weblogic.t3.serverURL", urlAS);
        t3props.put("weblogic.t3.connectionPoolID", poolAS);
        t3props.put("weblogic.t3.user", userAS);
        t3props.put("weblogic.t3.password", passwordAS);
        for(int i = 1; i <= numerotentativi; i++)
            try
            {
                Connection c = myDriver.connect(urlt3AS, t3props);
                if(getIsolationLevel() != -1)
                    c.setTransactionIsolation(getIsolationLevel());
                c.setAutoCommit(true);
                return c;
            }
            catch(Exception e)
            {
                Debug.out("INIT CONNESSIONE WL POOL RMI " + getAlias() + "[TENTATIVO " + i + "(" + e + ")]", 2);
            }

        throw new ConnDBaseNoConnException("Impossibile aprire la connessione");
    }


}
