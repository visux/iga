//////////////////////////////////////////////////////////////////
//                    Classi di Connessione                     //
//////////////////////////////////////////////////////////////////

package iga;

import java.sql.Connection;

/**
 *
 * @author demone
 */
public interface IConnDBase {

    public static final int CONN_CHIUSA = 1;
    public static final int CONN_APERTA = 2;
    public static final int CONN_IN_TRANSAZIONE = 3;
    public static final int CONN_SECONDARIA_ATTIVA = 4;

    public abstract int getStato();

    public abstract int getTipoConnessione();

    public abstract String getAlias();

    public abstract IConnDBase getConnDBasesecondaria();

    public abstract void setConnDBasesecondaria(IConnDBase iconnectdb);

    public abstract int getTipoDB();

    public abstract boolean getSempreAperta();

    public abstract int getIsolationLevel();

    public abstract Connection getConnection()
        throws ConnDBaseException;

    public abstract void chiudiConnessione()
        throws ConnDBaseException;

    public abstract void iniziaTransazione()
        throws ConnDBaseException;

    public abstract void confermaTransazione()
        throws ConnDBaseException;

    public abstract void annullaTransazione();


}
