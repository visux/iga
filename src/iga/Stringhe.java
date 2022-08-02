//////////////////////////////////////////////////////////////////
//                    classe utile per le formattazioni testo   //
//////////////////////////////////////////////////////////////////

package iga;

/**
 *
 * @author demore
 */
public class Stringhe {

    /**
     * Costruttore principale
     */
    public Stringhe()
    {
    }

    /** Mi restituisce un boleano se la stringa è vuota o meno
    */
    public static boolean isVuota(String stringa)
    {
        if(stringa == null)
            return true;
        return stringa.equals("") || stringa.length() == 0;
    }

    /** Imposta la selezione e il caret position
	*/
	public static void caretPosition(javax.swing.JTextField jtfObj){
	     ///selezione di un oggetto da tastiera
        if(!Stringhe.isVuota(jtfObj.getText())){
	        jtfObj.setSelectionStart(0);
	        jtfObj.setSelectionEnd(jtfObj.getText().length());
        }


	}



}
