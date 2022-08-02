//////////////////////////////////////////////////////////////////
//                    Classe Utenti di I.G.A.                   //
//////////////////////////////////////////////////////////////////

package iga;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author demone
 */
public class User {

    public String utente;
    public String password;
    public String operatore;
    public String attiva;
    public int livello;
    public String sigla;
    public String gruppo;
    public String log_date;
    public String log_time;
    public String log_pcname;
    public String log_ipaddress;
    public String force_1cngpwd;
    public String force_cngpwd;
    public short pwd_interval;
    public String data_cngpwd;
    public String data_crea;
    public String data_mod;
    public String ora_mod;
    public boolean fl_collegato;
    public static String DataLoginPrec = "";
    public static String OraLoginPrec = "";
    public static String NomePcPrec = "";
    public static String IndirizzoIpPrec = "";
    public boolean IsNew;

    public User()
    {
        // variabili settate al richiamo del costruttore principale
        utente = "";
        password = "";
        operatore = "";
        attiva = "";
        livello = 99;
        sigla = "";
        gruppo = "";
        log_date = "";
        log_time = "";
        log_pcname = "";
        log_ipaddress = "";
        force_1cngpwd = "";
        force_cngpwd = "";
        pwd_interval = 0;
        data_cngpwd = "";
        data_crea = "";
        data_mod = "";
        ora_mod = "";
        fl_collegato = false;
        IsNew = true;
        IsNew = true;
    }

    public User(String sUtente)
        throws Exception
    {
        this();
        if(Stringhe.isVuota(sUtente))
            throw new Exception("L' Identificativo Utente deve essere fornito!");
        String sqlGetRecords = "Select * from " + Utile.getTabella("t_utenti") + " Where utente = '" + sUtente.trim() + "'";
        Statement sqlStmtGetRecords = null;
        ResultSet rs = null;
        try
        {
            sqlStmtGetRecords = IGAConn.getConnection().createStatement();
            rs = sqlStmtGetRecords.executeQuery(sqlGetRecords);
            if(rs.next())
            {
                String utente = rs.getString("utente");
                if(rs.wasNull())
                    this.utente = "";
                else
                    this.utente = utente.trim();
                String passwd = rs.getString("passwd");
                if(rs.wasNull())
                    password = "";
                else
                    password = passwd.trim();
                String operatore = rs.getString("operatore");
                if(rs.wasNull())
                    this.operatore = "";
                else
                    this.operatore = operatore.trim();
                String attiva = rs.getString("attiva");
                if(rs.wasNull())
                    this.attiva = "N";
                else
                    this.attiva = attiva.trim();
                livello = rs.getInt("livello");
                if(rs.wasNull())
                    livello = 99;
                String sigla = rs.getString("sigla");
                if(rs.wasNull())
                    this.sigla = "";
                else
                    this.sigla = sigla.trim();
                String gruppo = rs.getString("gruppo");
                if(rs.wasNull())
                    this.gruppo = "";
                else
                    this.gruppo = gruppo.trim();
                String force_1cngpwd = rs.getString("force_1cngpwd");
                if(rs.wasNull())
                    this.force_1cngpwd = "";
                else
                    this.force_1cngpwd = force_1cngpwd.trim();
                String force_cngpwd = rs.getString("force_cngpwd");
                if(rs.wasNull())
                    this.force_cngpwd = "";
                else
                    this.force_cngpwd = force_cngpwd.trim();
                pwd_interval = rs.getShort("pwd_interval");
                if(rs.wasNull())
                    pwd_interval = 0;
                String data_cngpwd = rs.getString("data_cngpwd");
                if(rs.wasNull())
                    this.data_cngpwd = "";
                else
                    this.data_cngpwd = data_cngpwd.trim();
                String log_date = rs.getString("log_date");
                if(rs.wasNull())
                    this.log_date = "";
                else
                    this.log_date = log_date.trim();
                String log_time = rs.getString("log_time");
                if(rs.wasNull())
                    this.log_time = "";
                else
                    this.log_time = log_time.trim();
                String log_pcname = rs.getString("log_pcname");
                if(rs.wasNull())
                    this.log_pcname = "";
                else
                    this.log_pcname = log_pcname.trim();
                String log_ipaddress = rs.getString("log_ipaddress");
                if(rs.wasNull())
                    this.log_ipaddress = "";
                else
                    this.log_ipaddress = log_ipaddress.trim();
                String data_crea = rs.getString("data_crea");
                if(rs.wasNull())
                    this.data_crea = "";
                else
                    this.data_crea = data_crea.trim();
                String data_mod = rs.getString("data_mod");
                if(rs.wasNull())
                    this.data_mod = "";
                else
                    this.data_mod = data_mod.trim();
                String ora_mod = rs.getString("ora_mod");
                if(rs.wasNull())
                    this.ora_mod = "";
                else
                    this.ora_mod = ora_mod.trim();
                fl_collegato = rs.getBoolean("fl_collegato");
                if(rs.wasNull())
                    fl_collegato = false;
            } else
            {
                throw new Exception("");
            }
        }
        catch(ConnDBaseException connectdbexception)
        {
            throw new Exception("Connessione non disponibile");
        }
        catch(Exception exception)
        {
            throw new Exception("Utente '" + sUtente.trim() + "' non riconosciuto");
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(sqlStmtGetRecords != null)
                    sqlStmtGetRecords.close();
                IGAConn.chiudiConnessione();
            }
            catch(Exception exception2) { }
        }
        IsNew = false;
    }

    public User(String sUtente, String sPassword)
        throws Exception
    {
        this(sUtente);
        if(Stringhe.isVuota(sPassword))
            throw new Exception("La Password Utente deve essere fornita!");
        sPassword = CryptStr(sPassword);
        if(!sPassword.trim().equals(password.trim()))
            throw new Exception("Password Utente non riconosciuta!");
        else
            return;
    }

    public boolean setLogin(String ip)
    {
        DataLoginPrec = log_date;
        OraLoginPrec = log_time;
        NomePcPrec = log_pcname;
        IndirizzoIpPrec = log_ipaddress;
        String dData = GenerDate.getSDateFromUtil(GenerDate.getCurrentDateTime());
        String sTime = GenerDate.getSTimeFromUtil(GenerDate.getCurrentDateTime());
        log_date = dData;
        log_time = sTime;
        log_pcname = "";
        log_ipaddress = ip.trim();
        PreparedStatement pStmt = null;
        try
        {
            IGAConn.iniziaTransazione();
            String StringaSql = "Update " + Utile.getTabella("t_utenti") + " Set log_date=?,log_time=?,log_pcname=?,log_ipaddress=?, fl_collegato=? " + " Where utente = '" + utente + "'";
            pStmt = IGAConn.getConnection().prepareStatement(StringaSql);
            pStmt.setString(1, dData);
            pStmt.setString(2, sTime);
            if(!Stringhe.isVuota(log_pcname))
                pStmt.setString(3, log_pcname.trim());
            else
                pStmt.setNull(3, 1);
            if(!Stringhe.isVuota(log_ipaddress))
                pStmt.setString(4, log_ipaddress.trim());
            else
                pStmt.setNull(4, 1);
            pStmt.setShort(5, (short)1);
            if(pStmt.executeUpdate() == 0)
                throw new Exception();
            IGAConn.confermaTransazione();
            fl_collegato = true;
        }
        catch(Exception e)
        {
            Debug.out(e, 2);
            IGAConn.annullaTransazione();
            boolean flag = false;
            return flag;
        }
        finally
        {
            try
            {
                if(pStmt != null)
                    pStmt.close();
                IGAConn.chiudiConnessione();
            }
            catch(Exception exception1) { }
        }
        return true;
    }

    public void unSetLogin()
    {
        Statement stmt = null;
        try
        {
            IGAConn.iniziaTransazione();
            String StringaSql = "Update " + Utile.getTabella("t_utenti") + " Set fl_collegato = 0 " + " Where utente = '" + utente.trim() + "'";
            stmt = IGAConn.getConnection().createStatement();
            fl_collegato = false;
            if(stmt.executeUpdate(StringaSql) == 0)
                throw new Exception();
            IGAConn.confermaTransazione();
        }
        catch(Exception e)
        {
            Debug.out(e, 2);
            IGAConn.annullaTransazione();
        }
        finally
        {
            try
            {
                if(stmt != null)
                    stmt.close();
                IGAConn.chiudiConnessione();
            }
            catch(Exception exception1) { }
        }
    }

    public boolean isUser()
    {
        return !isAdmin();
    }

    public boolean isAdmin()
    {
        return livello == 1 || Utile.Operator;
    }

    public static Vector getListaRecords(String sFilter)
    {
        Vector vtListaRecords = new Vector();
        String sqlGetRecords = sFilter;
        PreparedStatement sqlStmtGetRecords = null;
        ResultSet rs = null;
        try
        {
            sqlStmtGetRecords = IGAConn.getConnection().prepareStatement(sqlGetRecords);
            User oUtente;
            for(rs = sqlStmtGetRecords.executeQuery(); rs.next(); vtListaRecords.addElement(oUtente))
            {
                oUtente = new User();
                oUtente.IsNew = false;
                String utente = rs.getString("utente");
                if(rs.wasNull())
                    oUtente.utente = "";
                else
                    oUtente.utente = utente.trim();
                String passwd = rs.getString("passwd");
                if(rs.wasNull())
                    oUtente.password = "";
                else
                    oUtente.password = passwd.trim();
                String operatore = rs.getString("operatore");
                if(rs.wasNull())
                    oUtente.operatore = "";
                else
                    oUtente.operatore = operatore.trim();
                String attiva = rs.getString("attiva");
                if(rs.wasNull())
                    oUtente.attiva = "N";
                else
                    oUtente.attiva = attiva.trim();
                oUtente.livello = rs.getInt("livello");
                if(rs.wasNull())
                    oUtente.livello = 99;
                String sigla = rs.getString("sigla");
                if(rs.wasNull())
                    oUtente.sigla = "";
                else
                    oUtente.sigla = sigla.trim();
                String gruppo = rs.getString("gruppo");
                if(rs.wasNull())
                    oUtente.gruppo = "";
                else
                    oUtente.gruppo = gruppo.trim();
                String force_1cngpwd = rs.getString("force_1cngpwd");
                if(rs.wasNull())
                    oUtente.force_1cngpwd = "";
                else
                    oUtente.force_1cngpwd = force_1cngpwd.trim();
                String force_cngpwd = rs.getString("force_cngpwd");
                if(rs.wasNull())
                    oUtente.force_cngpwd = "";
                else
                    oUtente.force_cngpwd = force_cngpwd.trim();
                oUtente.pwd_interval = rs.getShort("pwd_interval");
                if(rs.wasNull())
                    oUtente.pwd_interval = 0;
                String data_cngpwd = rs.getString("data_cngpwd");
                if(rs.wasNull())
                    oUtente.data_cngpwd = "";
                else
                    oUtente.data_cngpwd = data_cngpwd.trim();
                String log_date = rs.getString("log_date");
                if(rs.wasNull())
                    oUtente.log_date = "";
                else
                    oUtente.log_date = log_date.trim();
                String log_time = rs.getString("log_time");
                if(rs.wasNull())
                    oUtente.log_time = "";
                else
                    oUtente.log_time = log_time.trim();
                String log_pcname = rs.getString("log_pcname");
                if(rs.wasNull())
                    oUtente.log_pcname = "";
                else
                    oUtente.log_pcname = log_pcname.trim();
                String log_ipaddress = rs.getString("log_ipaddress");
                if(rs.wasNull())
                    oUtente.log_ipaddress = "";
                else
                    oUtente.log_ipaddress = log_ipaddress.trim();
                String data_crea = rs.getString("data_crea");
                if(rs.wasNull())
                    oUtente.data_crea = "";
                else
                    oUtente.data_crea = data_crea.trim();
                String data_mod = rs.getString("data_mod");
                if(rs.wasNull())
                    oUtente.data_mod = "";
                else
                    oUtente.data_mod = data_mod.trim();
                String ora_mod = rs.getString("ora_mod");
                if(rs.wasNull())
                    oUtente.ora_mod = "";
                else
                    oUtente.ora_mod = ora_mod.trim();
                oUtente.fl_collegato = rs.getBoolean("fl_collegato");
                if(rs.wasNull())
                    oUtente.fl_collegato = false;
            }

        }
        catch(Exception exception)
        {
            sqlGetRecords = "";
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(sqlStmtGetRecords != null)
                    sqlStmtGetRecords.close();
                IGAConn.chiudiConnessione();
            }
            catch(Exception exception2) { }
        }
        return vtListaRecords;
    }

    public void registra(boolean transazioneaperta)
        throws Exception
    {
        PreparedStatement pStmt = null;
        try
        {
            if(!transazioneaperta)
                IGAConn.iniziaTransazione();
            if(IsNew)
            {
                String sql = "INSERT INTO " + Utile.getTabella("t_utenti") +
                             " (" + "utente," +
                                    "passwd," +
                                    "operatore," +
                                    "attiva," +
                                    "livello," +
                                    "sigla," +
                                    "gruppo," +
                                    "log_date," +
                                    "log_time," +
                                    "log_pcname," +
                                    "log_ipaddress," +
                                    "force_1cngpwd," +
                                    "force_cngpwd," +
                                    "pwd_interval," +
                                    "data_cngpwd," +
                                    "data_crea," +
                                    "data_mod," +
                                    "ora_mod," +
                                    "fl_collegato" + ")" +
                                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pStmt = IGAConn.getConnection().prepareStatement(sql);
                if(!Stringhe.isVuota(utente))
                    pStmt.setString(1, utente.trim());
                else
                    pStmt.setNull(1, 1);
                if(!Stringhe.isVuota(password))
                    pStmt.setString(2, password.trim());
                else
                    pStmt.setNull(2, 1);
                if(!Stringhe.isVuota(operatore))
                    pStmt.setString(3, operatore.trim());
                else
                    pStmt.setNull(3, 1);
                if(!Stringhe.isVuota(attiva))
                    pStmt.setString(4, attiva.trim());
                else
                    pStmt.setNull(4, 1);
                pStmt.setInt(5, livello);
                if(!Stringhe.isVuota(sigla))
                    pStmt.setString(6, sigla.trim());
                else
                    pStmt.setNull(6, 1);
                if(!Stringhe.isVuota(gruppo))
                    pStmt.setString(7, gruppo.trim());
                else
                    pStmt.setNull(7, 1);

                if(!Stringhe.isVuota(log_date))
                    pStmt.setString(8, log_date.trim());
                else
                    pStmt.setNull(8, 1);

                if(!Stringhe.isVuota(log_time))
                    pStmt.setString(9, log_time.trim());
                else
                    pStmt.setNull(9, 1);

                if(!Stringhe.isVuota(log_pcname))
                    pStmt.setString(10, log_pcname.trim());
                else
                    pStmt.setNull(10, 1);

               if(!Stringhe.isVuota(log_ipaddress))
                    pStmt.setString(11, log_ipaddress.trim());
                else
                    pStmt.setNull(11, 1);

                if(!Stringhe.isVuota(force_1cngpwd))
                    pStmt.setString(12, force_1cngpwd.trim());
                else
                    pStmt.setNull(12, 1);

                if(!Stringhe.isVuota(force_cngpwd))
                    pStmt.setString(13, force_cngpwd.trim());
                else
                    pStmt.setNull(13, 1);

                pStmt.setShort(14, pwd_interval);
                pStmt.setString(15, GenerDate.getSDateFromUtil(GenerDate.getCurrentDateTime()));
                pStmt.setString(16, GenerDate.getSDateFromUtil(GenerDate.getCurrentDateTime()));
                pStmt.setString(17, GenerDate.getSDateFromUtil(GenerDate.getCurrentDateTime()));
                pStmt.setString(18, GenerDate.getSTimeFromUtil(GenerDate.getCurrentDateTime()));
                pStmt.setShort(19, (short)0);
                if(pStmt.executeUpdate() == 0)
                    throw new Exception("Problemi in fase di registrazione Utente!");
                IsNew = false;
            } else
            {
                String sql = "UPDATE " + Utile.getTabella("t_utenti") + " SET " + "passwd = ?," + "operatore = ?," + "livello = ?," + "sigla = ?," + "gruppo = ?," + "attiva = ?," + "force_1cngpwd = ?," + "force_cngpwd = ?," + "pwd_interval = ?," + "data_mod = ?," + "ora_mod = ?" + " WHERE utente = '" + utente.trim() + "'";
                pStmt = IGAConn.getConnection().prepareStatement(sql);
                if(!Stringhe.isVuota(password))
                    pStmt.setString(1, password.trim());
                else
                    pStmt.setNull(1, 1);
                if(!Stringhe.isVuota(operatore))
                    pStmt.setString(2, operatore.trim());
                else
                    pStmt.setNull(2, 1);
                pStmt.setInt(3, livello);
                if(!Stringhe.isVuota(sigla))
                    pStmt.setString(4, sigla.trim());
                else
                    pStmt.setNull(4, 1);
                if(!Stringhe.isVuota(gruppo))
                    pStmt.setString(5, gruppo.trim());
                else
                    pStmt.setNull(5, 1);
                if(!Stringhe.isVuota(attiva))
                    pStmt.setString(6, attiva.trim());
                else
                    pStmt.setNull(6, 1);
                if(!Stringhe.isVuota(force_1cngpwd))
                    pStmt.setString(7, force_1cngpwd.trim());
                else
                    pStmt.setNull(7, 1);
                if(!Stringhe.isVuota(force_cngpwd))
                    pStmt.setString(8, force_cngpwd.trim());
                else
                    pStmt.setNull(8, 1);
                pStmt.setShort(9, pwd_interval);
                pStmt.setString(10, GenerDate.getSDateFromUtil(GenerDate.getCurrentDateTime()));
                pStmt.setString(11, GenerDate.getSTimeFromUtil(GenerDate.getCurrentDateTime()));
                if(pStmt.executeUpdate() == 0)
                    throw new Exception("Problemi in fase di registrazione Utente!");
            }
            if(!transazioneaperta)
                IGAConn.confermaTransazione();
        }
        catch(Exception e)
        {
            Debug.out(e, 2);
            if(!transazioneaperta)
                IGAConn.annullaTransazione();
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                if(pStmt != null)
                    pStmt.close();
                if(!transazioneaperta)
                    IGAConn.chiudiConnessione();
            }
            catch(Exception exception1) { }
        }
    }

    public void registraCambioPassword()
        throws Exception
    {
        PreparedStatement pStmt = null;
        try
        {
            IGAConn.iniziaTransazione();
            String sql = "UPDATE " + Utile.getTabella("t_utenti") + " SET " + "passwd = ?," + "force_1cngpwd = ?," + "data_cngpwd = ? " + " WHERE utente = '" + utente.trim() + "'";
            pStmt = IGAConn.getConnection().prepareStatement(sql);
            if(!Stringhe.isVuota(password))
                pStmt.setString(1, password.trim());
            else
                pStmt.setNull(1, 1);
            if(!Stringhe.isVuota(force_1cngpwd))
                pStmt.setString(2, force_1cngpwd.trim());
            else
                pStmt.setNull(2, 1);
            if(!Stringhe.isVuota(data_cngpwd))
                pStmt.setString(3, data_cngpwd.trim());
            else
                pStmt.setNull(3, 1);
            if(pStmt.executeUpdate() == 0)
                throw new Exception("Problemi in fase di registrazione Utente!");
            IGAConn.confermaTransazione();
        }
        catch(Exception e)
        {
            Debug.out(e, 2);
            IGAConn.annullaTransazione();
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                if(pStmt != null)
                    pStmt.close();
                IGAConn.chiudiConnessione();
            }
            catch(Exception exception1) { }
        }
    }

    public void controllaCampi()
        throws Exception
    {
        if(Stringhe.isVuota(utente))
            throw new Exception("L'Utente deve essere fornito!");
        if(Stringhe.isVuota(password))
            throw new Exception("La Password deve essere fornita!");
        if(password.length() < 4)
            throw new Exception("La Password deve contenere almeno 4 caratteri !");
        if(Stringhe.isVuota(sigla))
            throw new Exception("La Sigla Utente deve essere fornita!");
        if(Stringhe.isVuota(gruppo))
            throw new Exception("Il Gruppo di Appartenenza deve essere fornito!");

        /*
        GruppiUtenti oGruppiUtenti = new GruppiUtenti();
        if(!oGruppiUtenti.isOkCodice(gruppo))
            throw new Exception("Il Gruppo di Appartenenza non \350 presente in archivio!");
        */
         PreparedStatement pStmt = null;
        ResultSet rs = null;
        try
        {
            pStmt = IGAConn.getConnection().prepareStatement("SELECT * FROM " + Utile.getTabella("t_utenti") + " where utente <> ? and sigla = ? ");
            pStmt.setString(1, utente.trim());
            pStmt.setString(2, sigla.trim());
            rs = pStmt.executeQuery();
            if(rs.next())
                throw new Exception("La Sigla \350 gi\340 stata utilizzata da un altro Utente!");
        }
        catch(ConnDBaseException e1)
        {
            throw new ConnDBaseException(e1.getMessage());
        }
        catch(SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(pStmt != null)
                    pStmt.close();
                IGAConn.chiudiConnessione();
            }
            catch(Exception exception1) { }
        }
    }

    public boolean cancella()
    {
        boolean bRetVal = true;
        PreparedStatement pStmt = null;
        try
        {
            IGAConn.iniziaTransazione();
            String sql = "UPDATE " + Utile.getTabella("t_utenti") + " SET " + "attiva = ?" + " WHERE utente = '" + utente.trim() + "'";
            pStmt = IGAConn.getConnection().prepareStatement(sql);
            pStmt.setString(1, "N");
            if(pStmt.executeUpdate() == 0)
                throw new Exception();
            IGAConn.confermaTransazione();
        }
        catch(Exception e)
        {
            Debug.out(e, 2);
            IGAConn.annullaTransazione();
            bRetVal = false;
        }
        finally
        {
            try
            {
                if(pStmt != null)
                    pStmt.close();
                IGAConn.chiudiConnessione();
            }
            catch(Exception exception1) { }
        }
        return bRetVal;
    }

    public static String CryptStr(String sSource)
    {
        String sDest = null;
        if(Stringhe.isVuota(sSource))
            return "-1";
        char cvDest[] = sSource.toCharArray();
        int i = 0;
        int iOffSet = 1;
        for(; i < sSource.length(); i++)
        {
            char cSource = cvDest[i];
            int iSource = Character.getNumericValue(cSource) + iOffSet;
            String Appo = String.valueOf(Character.forDigit(iSource, 36));
            if(!Stringhe.isVuota(sDest))
                sDest = sDest.trim() + Appo.trim();
            else
                sDest = Appo.trim();
            int iLenDest = sDest.length();
            int k = 1;
            int j = 1;
            for(; k < iLenDest; k++)
                j += k;

            sDest = sDest.trim() + String.valueOf(Character.forDigit(j, 36));
        }

        if(!Stringhe.isVuota(sDest))
            sDest = sDest.toUpperCase();
        return sDest;
    }

    public static String decryptStr(String sSource)
    {
        String sDest = null;
        if(Stringhe.isVuota(sSource))
            return "-1";
        String sPassToFind = sSource;
        String sOri = " ";
        int cntChar = 1;
        for(boolean flBreakFind = false; !flBreakFind;)
        {
            for(int i = 0; i <= 36; i++)
            {
                String sTry = sOri.trim() + String.valueOf(Character.forDigit(i, 36));
                String sTryCrypt = CryptStr(sTry.trim());
                cntChar++;
                if(!sPassToFind.startsWith(sTryCrypt.trim()))
                    continue;
                sOri = sTry.trim();
                if(!sTryCrypt.trim().equals(sPassToFind.trim()))
                    continue;
                flBreakFind = true;
                sDest = sOri;
                break;
            }

        }

        return sDest;
    }

    /*
    // Imposta un Item per le ComboBox
    public static ComboBox getItemPerCombo(User oUtente)
    {
        return new ComboBox("" + oUtente.livello+" - " + oUtente.utente.trim().toUpperCase(),oUtente);
    }

    // Imposta il Codice di Ritorno da un ComboBox
    public static int getCodiceDaCombo(ComboBox itemselezionato)
    {

        User oUtente = (User)itemselezionato.getOggettoItem();
        if (oUtente == null)
           return -1;
        else
           return oUtente.livello;
    }

    // Mi ritorna la descrizione della combo box

    public static String getDescrDaCombo(ComboBox itemselezionato)
    {

        User oUtente = (User)itemselezionato.getOggettoItem();
        if (oUtente == null)
           return "";
        else
           return oUtente.utente;
    }
    */


}
