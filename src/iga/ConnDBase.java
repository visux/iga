//////////////////////////////////////////////////////////////////
//                    Classi di Connessione                     //
//////////////////////////////////////////////////////////////////

package iga;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author demone
 */
public abstract class ConnDBase implements IConnDBase{
     /**
      STATO DELLA CONNESSIONE
      CONN_CHIUSA: la connessione è chiusa
      CONN_APERTA: la connessione è aperta
      CONN_IN_TRANSAZIONE: la connessione è aperta con transazione in corso
      CONN_SECONDARIA_ATTIVA: la connessione secondaria è attiva
    */
    private int stato;

    /** Alias assegnato
    */
    private String alias;

    /** Connessione DB
    */
    private Connection conn;

    /** Eventuale connessione secondaria
    */
    private ConnDBase connectDBsecondaria;

    /** TIPO DB 1=Informix, 2=Oracle, 3=Db2, 4=SQL SERVER, 5=Access
    */
    private int tipoDB;

    /** TRUE la connessione viene sempre tenuta aperta (viene aperta alla
      prima chiamata)
    */
    private boolean sempreaperta;

    /** TRUE la connessione viene sempre tenuta aperta (viene aperta alla
      prima chiamata)
    */
    private String testtable;

    /**livello isolazione 1 = UNCOMMITED_READ 2=COMMITED_READ 3=REPEATABLE_READ 4=SERIALIZABLE
    */
    private int il;


    private String driverDB;
    private String urlDB;
    private String userDB;
    private String passwordDB;

    /** se TRUE Attiva il debug
    */
    private boolean debug;


    /**
     *  Costruttore Principale con le variabili valorizzate
     */
    public ConnDBase()
    {
        stato = 1;
        alias = "";
        conn = null;
        connectDBsecondaria = null;
        sempreaperta = false;
        testtable = "";
        debug = false;
    }

    /** Ritorna Tipo connessione
    */
    public int getStato()
    {
        return stato;
    }

    public void setStato(int stato)
    {
        this.stato = stato;
        switch(getStato())
        {
        case 1: // '\001'
            conn = null;
            // fall through

        case 2: // '\002'
        case 3: // '\003'
        case 4: // '\004'
        default:
            return;
        }
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public IConnDBase getConnDBasesecondaria()
    {
        return connectDBsecondaria;
    }

    public void setConnDBasesecondaria(IConnDBase connectdb)
    {
        connectDBsecondaria = (ConnDBase)connectdb;
    }

    public Connection getConn()
    {
        return conn;
    }

    public void setConn(Connection conn)
    {
        this.conn = conn;
    }

    public int getTipoDB()
    {
        return tipoDB;
    }

    public void setTipoDB(int tipoDB)
    {
        this.tipoDB = tipoDB;
    }

    public boolean getSempreAperta()
    {
        return sempreaperta;
    }

    public void setSempreAperta(boolean sempreaperta)
    {
        this.sempreaperta = sempreaperta;
    }

    public String getTestTable()
    {
        return testtable;
    }

    public void setTestTable(String testtable)
    {
        this.testtable = testtable;
    }

    public int getIl()
    {
        return il;
    }

    public void setIl(int isolationlevel)
    {
        il = isolationlevel;
    }

    public String getDriverDB()
    {
        return driverDB;
    }

    public void setDriverDB(String driverDB)
    {
        this.driverDB = driverDB;
    }

    public String getUrlDB()
    {
        return urlDB;
    }

    public void setUrlDB(String urlDB)
    {
        this.urlDB = urlDB;
    }

    public String getUserDB()
    {
        return userDB;
    }

    public void setUserDB(String userDB)
    {
        this.userDB = userDB;
    }

    public String getPasswordDB()
    {
        return passwordDB;
    }

    public void setPasswordDB(String passwordDB)
    {
        this.passwordDB = passwordDB;
    }

    public int getIsolationLevel()
    {
        switch(il)
        {
        case 1: // '\001'
            return 1;

        case 2: // '\002'
            return 2;

        case 3: // '\003'
            return 4;

        case 4: // '\004'
            return 8;
        }
        return -1;
    }

    public boolean getDebug()
    {
        return debug;
    }

    public void setDebug(boolean flag)
    {
        debug = flag;
    }

    /** Attiva la connessione al database e aggiorna lo stato:
	    STATO :
	    - CONN_CHIUSA : apre la connessione, se errore prova con la secondaria (se presente)
	    - CONN_APERTA : la connessione viene testata, se non OK  viene attivata la secondaria (se presente)
	    - CONN_IN_TRANSAZIONE : nessuna azione
	    - CONN_SECONDARIA_ATTIVA : attiva la connessione secondaria
	*/
    private void attivaConnessione()
        throws ConnDBaseException
    {
        if(getDebug())
            Debug.out("ATTIVAZIONE CONNESSIONE " + getAlias() + "[" + getStato() + "]", 1);
        switch(getStato())
        {
        case 3: // '\003'
        default:
            break;

        case 1: // '\001'
            try
            {
                setConn(initConnessione());
                setStato(2);
                break;
            }
            catch(Exception exception) { }
            if(getDebug())
                Debug.out("FALLITA ATTIVAZIONE CONNESSIONE " + getAlias() + "[" + getStato() + "]", 1);
            try
            {
                if(getConnDBasesecondaria() == null)
                    throw new Exception();
                ((ConnDBase)getConnDBasesecondaria()).attivaConnessione();
                setStato(4);
                break;
            }
            catch(Exception exception2)
            {
                setStato(1);
            }
            throw new ConnDBaseNoConnException("Connessione non disponibile");

        case 2: // '\002'
            try
            {
                testConnessione();
                break;
            }
            catch(Exception e)
            {
                if(getDebug())
                {
                    Debug.out(e.getMessage(), 1);
                    Debug.out("FALLITO TEST CONNESSIONE " + getAlias() + "[" + getStato() + "]", 1);
                }
            }
            setStato(1);
            try
            {
                setConn(null);
                if(getConnDBasesecondaria() == null)
                    throw new Exception();
                ((ConnDBase)getConnDBasesecondaria()).attivaConnessione();
                setStato(4);
            }
            catch(Exception exception3)
            {
                throw new ConnDBaseNoConnException("Connessione non disponibile");
            }
            break;

        case 4: // '\004'
            try
            {
                if(getDebug())
                    Debug.out("ATTIVA CONNESSIONE " + getAlias() + " Uso connessione secondaria [" + getStato() + "]", 1);
                ((ConnDBase)getConnDBasesecondaria()).attivaConnessione();
                break;
            }
            catch(Exception exception1)
            {
                setStato(1);
            }
            throw new ConnDBaseNoConnException("Connessione non disponibile");
        }
    }

    /** Ritorna la connessione al database.
	    La connessione viene attivata e quindi in riferimento allo stato:
	    - CONN_CHIUSA : Exception
	    - CONN_APERTA : ritorna Connection
	    - CONN_IN_TRANSAZIONE : ritorna Connection
	    - CONN_SECONDARIA_ATTIVA : ritorna connessione secondaria
	*/
    public Connection getConnection()
        throws ConnDBaseException
    {
        attivaConnessione();
        switch(getStato())
        {
        case 1: // '\001'
            throw new ConnDBaseNoConnException("Connessione non disponibile");

        case 2: // '\002'
            return getConn();

        case 3: // '\003'
            return getConn();

        case 4: // '\004'
            try
            {
                return getConnDBasesecondaria().getConnection();
            }
            catch(Exception exception)
            {
                setStato(1);
            }
            throw new ConnDBaseNoConnException("Connessione non disponibile");
        }
        return null;
    }

    /** Verifica se la connessione è attiva
    */
    protected void testConnessione()
        throws Exception
    {
        long t0 = 0L;
        String nometabellatest = getTestTable();
        if(nometabellatest == null || nometabellatest.length() == 0)
            return;
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = getConn().createStatement();
            rs = stmt.executeQuery("select count(*) from " + nometabellatest);
            if(!rs.next())
                throw new Exception("Connessione non disponibile");
        }
        catch(Exception e)
        {
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
            }
            catch(Exception e)
            {
                throw new Exception(e.getMessage());
            }
        }
    }

    /** Creazione connessione
    */
    protected abstract Connection initConnessione()
        throws ConnDBaseException;

    /** Chiusura connessione.
      Nel caso di transazione aperta non viene fatto niente, la chiusura avviene in
      fase di conferma transazione e successiva chiusura connessione
      Nel caso di connessione dichiarata sempre aperta
      la connessione non viene chiusa.
    */
    public final void chiudiConnessione()
    {
        switch(getStato())
        {
        case 1: // '\001'
        case 3: // '\003'
        default:
            break;

        case 2: // '\002'
            try
            {
                if(sempreaperta)
                    break;
                if(getDebug())
                    Debug.out("CHIUSURA CONNESSIONE " + getAlias() + "[" + getStato() + "]", 1);
                conn.close();
                setStato(1);
            }
            catch(Exception exception)
            {
                setStato(1);
            }
            break;

        case 4: // '\004'
            try
            {
                getConnDBasesecondaria().chiudiConnessione();
            }
            catch(Exception exception1) { }
            break;
        }
    }

    /** Chiusura forzata connessione, nel caso di transazione aperta viene
      fatto il rollback.
      Nel caso di connessione dichiarata sempre aperta
      la connessione non viene chiusa.
    */
    public final void disposeConnessione()
    {
        switch(getStato())
        {
        case 1: // '\001'
        default:
            break;

        case 2: // '\002'
            try
            {
                if(getDebug())
                    Debug.out("DISPOSE CONNESSIONE " + getAlias() + "[" + getStato() + "]", 1);
                conn.close();
                setStato(1);
            }
            catch(Exception exception)
            {
                setStato(1);
            }
            break;

        case 3: // '\003'
            try
            {
                annullaTransazione();
            }
            catch(Exception exception1) { }
            try
            {
                if(getDebug())
                    Debug.out("DISPOSE CONNESSIONE " + getAlias() + "[" + getStato() + "]", 1);
                conn.close();
                setStato(1);
            }
            catch(Exception exception2)
            {
                setStato(1);
            }
            break;

        case 4: // '\004'
            try
            {
                ((ConnDBase)getConnDBasesecondaria()).disposeConnessione();
            }
            catch(Exception exception3) { }
            break;
        }
    }

    /** START transazione.
	    La connessione viene attivata e quindi in riferimento allo stato:
	    - CONN_CHIUSA : Exception
	    - CONN_APERTA : Apertura transazione
	    - CONN_IN_TRANSAZIONE : Exception
	    - CONN_SECONDARIA_ATTIVA : Apertura transazione sulla connessione secondaria
	*/
    public void iniziaTransazione()
        throws ConnDBaseException
    {
        attivaConnessione();
        switch(getStato())
        {
        default:
            break;

        case 1: // '\001'
            throw new ConnDBaseNoConnException("Connessione non disponibile");

        case 2: // '\002'
            try
            {
                conn.setAutoCommit(false);
                setStato(3);
                break;
            }
            catch(Exception exception)
            {
                setStato(1);
            }
            throw new ConnDBaseNoConnException("Connessione non disponibile");

        case 3: // '\003'
            throw new ConnDBaseTransazioneException("Transazione gi\340 aperta");

        case 4: // '\004'
            getConnDBasesecondaria().iniziaTransazione();
            break;
        }
    }

    /** COMMIT transazione.
	    In riferimento allo stato:
	    - CONN_CHIUSA : Exception
	    - CONN_APERTA : Exception
	    - CONN_IN_TRANSAZIONE : Chiusura transazione
	    - CONN_SECONDARIA_ATTIVA : Chiusura transazione connessione secondaria
	*/
    public void confermaTransazione()
        throws ConnDBaseException
    {
        switch(getStato())
        {
        default:
            break;

        case 1: // '\001'
            throw new ConnDBaseTransazioneException("COMMIT non effettuato: la connessione \350 chiusa");

        case 2: // '\002'
            throw new ConnDBaseTransazioneException("COMMIT non effettuato: la transazione non risulta aperta");

        case 3: // '\003'
            try
            {
                conn.commit();
                conn.setAutoCommit(true);
                setStato(2);
                break;
            }
            catch(Exception exception)
            {
                setStato(1);
            }
            throw new ConnDBaseTransazioneException("COMMIT non effettuato: connessione non disponibile");

        case 4: // '\004'
            getConnDBasesecondaria().confermaTransazione();
            break;
        }
    }

    /** ROLLBACK transazione.
	    In riferimento allo stato:
	    - CONN_CHIUSA : Exception
	    - CONN_APERTA : Exception
	    - CONN_IN_TRANSAZIONE : Chiusura transazione
	    - CONN_SECONDARIA_ATTIVA : Chiusura transazione connessione secondaria
	*/
    public void annullaTransazione()
    {
        switch(getStato())
        {
        case 1: // '\001'
        case 2: // '\002'
        default:
            break;

        case 3: // '\003'
            try
            {
                conn.rollback();
                conn.setAutoCommit(true);
                setStato(2);
            }
            catch(Exception exception)
            {
                setStato(1);
            }
            break;

        case 4: // '\004'
            getConnDBasesecondaria().annullaTransazione();
            break;
        }
    }

    public abstract int getTipoConnessione();
}
