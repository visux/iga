//////////////////////////////////////////////////////////////////
//                    Classi di Connessione                     //
//////////////////////////////////////////////////////////////////


package iga;

/**
 *
 * @author demone
 */
public interface IConn {

    public abstract IConnDBase getIConnDBase(String s)
        throws ConnDBaseException;

    public abstract IConnDBase getIDefaultConnDBase()
        throws ConnDBaseException;
}
