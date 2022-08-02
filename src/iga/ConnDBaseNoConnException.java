//////////////////////////////////////////////////////////////////
//                    Classi di Connessione                     //
//////////////////////////////////////////////////////////////////

package iga;

/**
 *
 * @author demone
 */
public class ConnDBaseNoConnException extends ConnDBaseException{

    public ConnDBaseNoConnException()
    {
    }

    public ConnDBaseNoConnException(String e)
    {
        super(e);
    }
}
