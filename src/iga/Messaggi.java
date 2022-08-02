//////////////////////////////////////////////////////////////////
//                    classe di gestione dei messaggi              //
//////////////////////////////////////////////////////////////////

package iga;

import javax.swing.JOptionPane;

/**
 *
 * @author demone
 */
public class Messaggi {

    /**
     * Costruttore principale
     */
    public Messaggi()
    {
    }

    /**
     * Messaggi visibili in base al testo scritto
     * @param sTesto
     * @return
     */
    public static int show(String sTesto)
    {
        return show(sTesto, 2, "OK", null, null, null, 0);
    }

    /**
     * Conferma del messaggio
     * @param sTesto
     * @return
     */
    public static int showConferma(String sTesto)
    {
        return show(sTesto, 1, "OK", "Annulla", null, null, 0);
    }

    public static int show(String sTesto, int iTipo)
    {
        return show(sTesto, iTipo, "OK", null, null, null, 0);
    }

    public static int show(String sTesto, int iTipo, String sOpzione1)
    {
        return show(sTesto, iTipo, sOpzione1, null, null, null, 0);
    }

    /**
     * Se si vogliono inserire ulteriori parametri al messaggio
     * @param sTesto
     * @param iTipo
     * @param sOpzione1
     * @param sOpzione2
     * @param iOpzioneDefault
     * @return
     */
    public static int show(String sTesto, int iTipo, String sOpzione1, String sOpzione2, int iOpzioneDefault)
    {
        return show(sTesto, iTipo, sOpzione1, sOpzione2, null, null, iOpzioneDefault);
    }

    /**
     * Se si vogliono inserire ulteriori parametri al messaggio
     * @param sTesto
     * @param iTipo
     * @param sOpzione1
     * @param sOpzione2
     * @param sOpzione3
     * @param iOpzioneDefault
     * @return
     */
    public static int show(String sTesto, int iTipo, String sOpzione1, String sOpzione2, String sOpzione3, int iOpzioneDefault)
    {
        return show(sTesto, iTipo, sOpzione1, sOpzione2, sOpzione3, null, iOpzioneDefault);
    }

    /**
     * Se si vogliono inserire ulteriori parametri al messaggio
     * @param sTesto
     * @param iTipo
     * @param sOpzione1
     * @param sOpzione2
     * @param sOpzione3
     * @param sOpzione4
     * @param iOpzioneDefault
     * @return
     */
    public static int show(String sTesto, int iTipo, String sOpzione1, String sOpzione2, String sOpzione3, String sOpzione4, int iOpzioneDefault)
    {
        String sTitolo = null;
        int iMessageType = 0;
        if(iTipo == 1)
        {
            sTitolo = " Messaggio";
            iMessageType = 1;
        } else
        if(iTipo == 2)
        {
            sTitolo = " Segnalazione";
            iMessageType = 2;
        } else
        {
            sTitolo = " Errore";
            iMessageType = 0;
        }
        int iNumOpzioni = 0;
        if(sOpzione1 != null)
            iNumOpzioni++;
        if(sOpzione2 != null)
            iNumOpzioni++;
        if(sOpzione3 != null)
            iNumOpzioni++;
        if(sOpzione4 != null)
            iNumOpzioni++;
        Object oOptions[] = new Object[iNumOpzioni];
        iNumOpzioni = -1;
        if(sOpzione1 != null)
        {
            iNumOpzioni++;
            oOptions[iNumOpzioni] = sOpzione1.trim();
        }
        if(sOpzione2 != null)
        {
            iNumOpzioni++;
            oOptions[iNumOpzioni] = sOpzione2.trim();
        }
        if(sOpzione3 != null)
        {
            iNumOpzioni++;
            oOptions[iNumOpzioni] = sOpzione3.trim();
        }
        if(sOpzione4 != null)
        {
            iNumOpzioni++;
            oOptions[iNumOpzioni] = sOpzione4.trim();
        }
        if(iOpzioneDefault > iNumOpzioni || iOpzioneDefault < 1)
            iOpzioneDefault = 1;
        sTesto = sTesto + "      ";
        int replay = JOptionPane.showOptionDialog(null, sTesto, sTitolo, 0, iMessageType, null, oOptions, oOptions[iOpzioneDefault - 1]);
        return replay + 1;
    }
}
