//////////////////////////////////////////////////////////////////
//                    Utile per trattare le date                //
//////////////////////////////////////////////////////////////////

package iga;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author demore
 */
public class GenerDate {

    public static int FORMAT_DATE = 1;
    
    /**
     * Costruttore Principale
     */
    public GenerDate()
    {
    }

    public static java.util.Date getUtilDateFromS(String data)
    {
        return getUtilDateFromS(data, "00:00");
    }

    public static java.util.Date getUtilDateFromS(String datain, String orain)
    {
        if(isDataOK(datain, false) && isOraOK(orain, false))
            try
            {
                int giorno = Integer.parseInt(datain.substring(0, 2));
                int mese = Integer.parseInt(datain.substring(3, 5)) - 1;
                int anno = Integer.parseInt(datain.substring(6));
                int ora = Integer.parseInt(orain.substring(0, 2));
                int minuto = Integer.parseInt(orain.substring(3));
                Calendar calendario = Calendar.getInstance();
                calendario.clear();
                calendario.set(anno, mese, giorno, ora, minuto);
                java.util.Date utildata = calendario.getTime();
                return utildata;
            }
            catch(NumberFormatException numberformatexception)
            {
                return null;
            }
        else
            return null;
    }

    public static Date getSqlDateFromS(String dataingresso)
    {
        if(isDataOK(dataingresso, false))
        {
            int giorno = Integer.parseInt(dataingresso.substring(0, 2));
            int mese = Integer.parseInt(dataingresso.substring(3, 5));
            int anno = Integer.parseInt(dataingresso.substring(6));
            Date dbdata = new Date(anno - 1900, mese - 1, giorno);
            return dbdata;
        } else
        {
            return null;
        }
    }

    public static String getSDateFromUtil(java.util.Date utildata)
    {
        if(utildata != null)
        {
            Calendar calendario = Calendar.getInstance();
            calendario.clear();
            calendario.setTime(utildata);
            int giorno = calendario.get(5);
            int mese = calendario.get(2) + 1;
            int anno = calendario.get(1);
            return getSDateFromInt(giorno, mese, anno);
        } else
        {
            return "";
        }
    }

    public static String getSTimeFromUtil(java.util.Date utildata)
    {
        if(utildata != null)
        {
            try
            {
                Calendar calendario = Calendar.getInstance();
                calendario.clear();
                calendario.setTime(utildata);
                int ora = calendario.get(11);
                int minuti = calendario.get(12);
                return getSTimeFromInt(ora, minuti);
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e);
            }
            return "";
        } else
        {
            return "";
        }
    }

    public static String getSDateFromSql(Date sqldata)
    {
        return getSDateFromUtil(sqldata);
    }

    public static boolean isDataOK(String data, boolean ok_null)
    {
        if(!Stringhe.isVuota(data))
        {
            int vDD;
            int vMM;
            int vYY;
            try
            {
                vDD = Integer.parseInt(data.substring(0, 2));
                vMM = Integer.parseInt(data.substring(3, 5));
                vYY = Integer.parseInt(data.substring(6));
            }
            catch(NumberFormatException numberformatexception)
            {
                return false;
            }
            switch(vMM)
            {
            case 4: // '\004'
            case 6: // '\006'
            case 9: // '\t'
            case 11: // '\013'
                if(vDD > 30)
                    return false;
                break;

            case 2: // '\002'
                if(vYY % 4 == 0)
                {
                    if(vYY % 100 == 0 && vYY % 400 != 0)
                    {
                        if(vDD > 28)
                            return false;
                    } else
                    if(vDD > 29)
                        return false;
                } else
                if(vDD > 28)
                    return false;
                break;

            case 1: // '\001'
            case 3: // '\003'
            case 5: // '\005'
            case 7: // '\007'
            case 8: // '\b'
            case 10: // '\n'
            case 12: // '\f'
                if(vDD > 31)
                    return false;
                break;

            default:
                return false;
            }
        } else
        if(!ok_null)
            return false;
        return true;
    }

    public static boolean isOraOK(String ora, boolean ok_null)
    {
        if(!Stringhe.isVuota(ora))
        {
            int vHH;
            int vMN;
            try
            {
                vHH = Integer.parseInt(ora.substring(0, 2));
                vMN = Integer.parseInt(ora.substring(3, 5));
            }
            catch(NumberFormatException numberformatexception)
            {
                return false;
            }
            if(vHH > 23)
                return false;
            if(vMN > 59)
                return false;
        } else
        if(!ok_null)
            return false;
        return true;
    }

    public static java.util.Date getUtilDateFromSql(Date data)
    {
        String d = getSDateFromSql(data);
        return getUtilDateFromS(d, "00:00");
    }

    public static Date getSqlDateFromUtil(java.util.Date data)
    {
        Calendar calendario = Calendar.getInstance();
        calendario.clear();
        calendario.setTime(data);
        int giorno = calendario.get(5);
        int mese = calendario.get(2);
        int anno = calendario.get(1) - 1900;
        return new Date(anno, mese, giorno);
    }

    public static String getDMYFromYMD(String data)
    {
        String giorno = "";
        String mese = "";
        String anno = "";
        if(!Stringhe.isVuota(data))
        {
            giorno = data.substring(8);
            mese = data.substring(5, 7);
            anno = data.substring(0, 4);
            return giorno + "/" + mese + "/" + anno;
        } else
        {
            return "";
        }
    }

    public static String getYMDFromGMY(String data)
    {
        String giorno = "";
        String mese = "";
        String anno = "";
        if(!Stringhe.isVuota(data))
        {
            giorno = data.substring(0, 2);
            mese = data.substring(3, 5);
            anno = data.substring(6);
            return anno + "/" + mese + "/" + giorno;
        } else
        {
            return "";
        }
    }

    public static java.util.Date getCurrentDateTime()
    {
        return Calendar.getInstance().getTime();
    }

    public static String getSDateFromInt(int giorno, int mese, int anno)
    {
        return getSDateFromInt(giorno, mese, anno, 1);
    }

    public static String getSDateFromInt(int giorno, int mese, int anno, int tipo)
    {
        if(giorno != 0 && mese != 0 && anno != 0)
        {
            String giornoS = "";
            String meseS = "";
            String annoS = "";
            if(giorno < 10)
                giornoS = "0" + String.valueOf(giorno);
            else
                giornoS = String.valueOf(giorno);
            if(mese < 10)
                meseS = "0" + String.valueOf(mese);
            else
                meseS = String.valueOf(mese);
            annoS = "0000" + String.valueOf(anno);
            annoS = annoS.substring(annoS.length() - 4);
            switch(tipo)
            {
            case 1: // '\001'
                return giornoS + "/" + meseS + "/" + annoS;

            case 2: // '\002'
                return meseS + "/" + giornoS + "/" + annoS;

            case 3: // '\003'
                return annoS + "/" + meseS + "/" + giornoS;
            }
            return giornoS + "/" + meseS + "/" + annoS;
        } else
        {
            return "";
        }
    }

    public static String getSTimeFromInt(int ora, int minuti)
    {
        String oraS = "";
        String minutiS = "";
        if(ora < 10)
            oraS = "0" + String.valueOf(ora);
        else
            oraS = String.valueOf(ora);
        if(minuti < 10)
            minutiS = "0" + String.valueOf(minuti);
        else
            minutiS = String.valueOf(minuti);
        return oraS + ":" + minutiS;
    }

    public static String getSDatabaseDateFromUtil(java.util.Date utildata)
    {
        if(utildata != null)
        {
            Calendar calendario = Calendar.getInstance();
            calendario.clear();
            calendario.setTime(utildata);
            int giorno = calendario.get(5);
            int mese = calendario.get(2) + 1;
            int anno = calendario.get(1);
            return getSDateFromInt(giorno, mese, anno, FORMAT_DATE);
        } else
        {
            return "";
        }
    }

    public static boolean isDataNull(java.util.Date data)
    {
        return data == null;
    }

    public static java.util.Date getDataNull()
    {
        return null;
    }

    public static boolean isDataAfterDataSystem(java.util.Date data)
    {
        if(data != null)
        {
            java.util.Date datasistema = getCurrentDateTime();
            if(data.after(datasistema))
                return true;
        }
        return false;
    }

    public static boolean isData1AfterData2(java.util.Date data1, String ora1, java.util.Date data2, String ora2)
    {
        if(data1 == null || data2 == null || Stringhe.isVuota(ora1) || Stringhe.isVuota(ora2))
            return false;
        java.util.Date dt1 = getUtilDateFromS(getSDateFromUtil(data1), ora1);
        java.util.Date dt2 = getUtilDateFromS(getSDateFromUtil(data2), ora2);
        return data1.after(data2);
    }

    public static int getEta(java.util.Date datanascita, java.util.Date datarif)
    {
        if(isDataNull(datanascita))
            return 0;
        if(isDataNull(datarif))
            return 0;
        Calendar calendario = Calendar.getInstance();
        calendario.clear();
        calendario.setTime(datanascita);
        int giornonascita = calendario.get(5);
        int mesenascita = calendario.get(2) + 1;
        int annonascita = calendario.get(1);
        calendario.clear();
        calendario.setTime(datarif);
        int giornorif = calendario.get(5);
        int meserif = calendario.get(2) + 1;
        int annorif = calendario.get(1);
        int eta = 0;
        eta = annorif - annonascita;
        if(meserif - mesenascita < 0)
            eta--;
        else
        if(meserif - mesenascita == 0 && giornorif - giornonascita < 0)
            eta--;
        return eta;
    }

    public static int getIntYMDFromSGMY(String data)
    {
        int iYMD = 0;
        String sYMD = "";
        String giorno = "";
        String mese = "";
        String anno = "";
        if(!Stringhe.isVuota(data))
        {
            giorno = data.substring(0, 2);
            mese = data.substring(3, 5);
            anno = data.substring(6);
            sYMD = anno + mese + giorno;
            iYMD = Integer.parseInt(sYMD);
        }
        return iYMD;
    }

    public static int getIntYMDFromUtil(java.util.Date utildata)
    {
        int iYMD = 0;
        if(utildata != null)
        {
            Calendar calendario = Calendar.getInstance();
            calendario.clear();
            calendario.setTime(utildata);
            int iGiorno = calendario.get(5);
            int iMese = calendario.get(2) + 1;
            int iAnno = calendario.get(1);
            iYMD = iAnno * 10000 + iMese * 100 + iGiorno;
        }
        return iYMD;
    }

    public static String getSDateFromIntYMD(int data)
    {
        if(data != 0)
        {
            Integer i = new Integer(data);
            String sData = i.toString();
            if(sData.length() == 8)
            {
                String annoS = sData.substring(0, 4);
                String meseS = sData.substring(4, 6);
                String giornoS = sData.substring(6);
                return giornoS + "/" + meseS + "/" + annoS;
            } else
            {
                return "";
            }
        } else
        {
            return "";
        }
    }

    public static java.util.Date getUtilFromIntYMD(int data)
    {
        Integer i = new Integer(data);
        String sData = i.toString().trim();
        if(sData.length() == 8)
        {
            String annoS = sData.substring(0, 4);
            String meseS = sData.substring(4, 6);
            String giornoS = sData.substring(6);
            sData = giornoS + "/" + meseS + "/" + annoS;
            return getUtilDateFromS(sData);
        } else
        {
            return null;
        }
    }

    public static java.util.Date getPrimoGiornoMeseCorrente(java.util.Date data)
    {
        return getUtilFromIntYMD((getIntYMDFromUtil(data) / 100) * 100 + 1);
    }

    public static java.util.Date getGiornoPrima(java.util.Date data)
    {
        Calendar calendario = Calendar.getInstance();
        calendario.clear();
        calendario.setTime(data);
        int igiorno = calendario.get(5);
        int imese = calendario.get(2) + 1;
        int ianno = calendario.get(1);
        if(--igiorno == 0)
            if(--imese == 0)
            {
                ianno--;
                imese = 12;
                igiorno = 31;
            } else
            if(imese == 11 || imese == 4 || imese == 6 || imese == 9)
                igiorno = 30;
            else
            if(imese == 2)
            {
                if(ianno % 4 == 0)
                {
                    if(ianno % 100 == 0 && ianno % 400 != 0)
                        igiorno = 28;
                    else
                        igiorno = 29;
                } else
                {
                    igiorno = 28;
                }
            } else
            {
                igiorno = 31;
            }
        return getUtilDateFromS(getSDateFromInt(igiorno, imese, ianno));
    }

    /** ricava l'anno dalla data
    */
    public static int getAnno(java.util.Date utildata)
    {
        Calendar calendario = Calendar.getInstance();
        calendario.clear();
        calendario.setTime(utildata);
        return calendario.get(1);
    }

    /** Ricava il giorno dalla data
	*/
	public static int getGiorno(java.util.Date data)
	{
	    java.util.Calendar calendario = java.util.Calendar.getInstance();
        calendario.clear();
        calendario.setTime(data);
        int igiorno = calendario.get(calendario.DATE );

	    return igiorno;

	}

    public static java.util.Date sommaGiorniData(java.util.Date utildata, int numgiorni, String segno)
    {
        java.util.Date NewDate = getDataNull();
        if(!isDataOK(getSDateFromUtil(utildata), false))
            return getDataNull();
        if(numgiorni <= 0)
            return utildata;
        if(Stringhe.isVuota(segno))
            segno = "+";
        if(!segno.equals("+") && !segno.equals("-"))
        {
            return getDataNull();
        } else
        {
            long lNumGiorni = numgiorni;
            long iGMT = utildata.getTime();
            iGMT += lNumGiorni * 24L * 60L * 60L * 1000L;
            NewDate = new java.util.Date(iGMT);
            return NewDate;
        }
    }

    /** verifica l'intervallo in minuti tra due oRe :D
    */
    public static boolean verificaIntervalloMinutiDaOraCorrente(String Ora2, int intervallo)
    {
        boolean ok = false;
        // si ricava l'ora odierna
        String Ora1 = "" + GenerDate.getSTimeFromUtil(GenerDate. getCurrentDateTime());

        // verifica se sono presenti le ore in modo corretto
        if(!Stringhe.isVuota(Ora1) && !Stringhe.isVuota(Ora2)){
            // ricava i valori dalle stringhe
            String Ore1 = "" + Ora1.substring(0, 2).trim();
            String Minuti1 = "" + Ora1.substring(3, 5).trim();
            String Ore2 = "" + Ora2.substring(0, 2).trim();
            String Minuti2 = "" + Ora2.substring(3, 5).trim();
                      int interv =0;
                      int minuto1 = Integer.parseInt(Minuti1);
                      int minuto2 = Integer.parseInt(Minuti2);
                      int ora1 = Integer.parseInt(Ore1);
                      int ora2 = Integer.parseInt(Ore2);

                // ricava gli intervalli tra i minuti
                if(minuto1 <= minuto2){
                         interv=minuto2 - minuto1;
                }else{
                         interv=minuto1 - minuto2;
                }
                // se le ore sono uguali
                if(ora1 == ora2){
                      // controlla l'intervallo
                      if(interv < intervallo){
                             return ok = true;
                      }

                }else {
                      int intervOre = 0;
                      // altrimenti se e' diversa questa condizione non e' verificata la condizione
                      if(ora1 < ora2){
                         intervOre = ora2 - ora1;
                         if(intervOre == 1){

                            if(interv < intervallo){
                                    return ok = true;
                            }


                         }


                      }



                }
        }


        return ok;


    }

    /** aggiunge i minuti
    */
    public static String aggiungiMinuti(int min)
    {
        String OraFinale = "";
        // si ricava l'ora odierna
        String OraOd = "" + GenerDate.getSTimeFromUtil(GenerDate. getCurrentDateTime());
		int ora=0;
        int somma=0;
        // verifica l'ora e' corretta
        if(!Stringhe.isVuota(OraOd)){
            // ricava i valori dalle stringhe
            String Ore = "" + OraOd.substring(0, 2).trim();
            String Minuti = "" + OraOd.substring(3, 5).trim();
                      int interv =0;
                      int minuto = Integer.parseInt(Minuti);
                      ora = Integer.parseInt(Ore);

                somma = minuto + min;
                int temp=0;
                if(somma > 59){
                       temp = somma-59;
                       temp = temp -1;
                       somma = temp;
                       ora=ora+1;
                }


                if (ora > 23){
                       ora = 0;
                }


        }

        if (ora <= 9 )
              OraOd = "0";
          else
              OraOd = "" ;

        // segno per la composizione della stringa
        String Segno = ":";
        if(somma <= 9){
             Segno=":0";
        }

        return OraFinale = "" + OraOd + "" + ora + Segno + somma ;
       }

    
}
