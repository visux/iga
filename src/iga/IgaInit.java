///////////////////////////////////////////////////////////////////
//       Avvio del software                                      //
//////////////////////////////////////////////////////////////////

package iga;

import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.InputMap;
import javax.swing.JApplet;
import javax.swing.KeyStroke;
import javax.swing.UIManager;


/**
 *
 * @author demone
 */
public class IgaInit {

    
    /**
	   Avvia la gui di Login
	**/
    public static void main(String args[])
    {
       // settaggio della maschera in base ad alcuni parametri
       RichiamaGui();
    }

    /**
	   Avvio tramite Html
	**/
    public IgaInit(JApplet startApplet)
    {
        Utile.ac = startApplet.getAppletContext();
        Utile.codebase = startApplet.getCodeBase().toString();
        RichiamaGui();
    }

   

    /**
       richiama la maschera iniziale e setta il percorso generale
    */
    public static void RichiamaGui()
    {
        Utile.ac = null;
        //URL richiamaCodBase;
        try
        {
          ClassLoader c = ClassLoader.getSystemClassLoader();
          Utile.codebase = c.getResource("").toString();
            
            
        }
        //catch(MalformedURLException e)
        catch(Exception e)
        {
            Debug.out(e, 2);
        }


        setFocusTastiera();


        // modalita' normale
        normale();

    }

    public static void setFocusTastiera()
    {
        try
        {



            UIManager.put("RootPane.defaultButtonWindowKeyBindings", new String[0]);
            InputMap im = (InputMap)UIManager.get("Button.focusInputMap");
            im.put(KeyStroke.getKeyStroke(10, 0, false), "pressed");
            im.put(KeyStroke.getKeyStroke(10, 0, true), "released");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
	   Avvia la maschera di Login in modalita' normale
	**/
	private static void normale(){
	    guiLogin oGui = new guiLogin();
        oGui.setModal(true);
        oGui.setVisible(true);
        if (oGui.isLoginOK()){
            oGui.dispose();
            oGui=null;
            // Il Menu non è modale
            /*
            (new FrBaseMenuApplicativo(Utile.oUtente,
        	            Utile.getTabella("autorizza"),
        	            Utile.getTabella("menu"),
        	            Utile.codebase)).setVisible(true);
        */
         }else{
           oGui.dispose();
           oGui=null;
           if (Utile.ac==null){
               System.exit(0);
           }
        }


     }


}
