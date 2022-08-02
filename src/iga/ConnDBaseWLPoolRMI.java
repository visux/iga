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
public class ConnDBaseWLPoolRMI extends ConnDBase{

    private String driverAS;
    private String urlAS;
    private String poolAS;
    private String userAS;
    private String passwordAS;
    private String urlt3AS;
    private int numerotentativi;
    Driver myDriver;

    public ConnDBaseWLPoolRMI()
    {
        numerotentativi = 1;
    }

    public int getTipoConnessione()
    {
        return 3;
    }

    public ConnDBaseWLPoolRMI(String alias, int tipoDB, boolean sempreaperta, String testtable, int isolationlevel, boolean debug, String driverAS,
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
            Debug.out("INIT CONNESSIONE WL POOL RMI" + getAlias() + "[" + getStato() + "]", 1);
        if(urlAS == null || poolAS == null || userAS == null || passwordAS == null || urlt3AS == null)
            throw new ConnDBaseNoConnException("Impossibile aprire la connessione: parametri mancanti");
        Properties props = new Properties();
        props.put("weblogic.server.url", urlAS);
        props.put("weblogic.jdbc.datasource", poolAS);
        props.put("weblogic.user", userAS);
        props.put("weblogic.credential", passwordAS);
        for(int i = 1; i <= numerotentativi; i++)
            try
            {
                Connection c = myDriver.connect(urlt3AS, props);
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
