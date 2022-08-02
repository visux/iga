//////////////////////////////////////////////////////////////////
//                    Dichiarazioni dell'utente                 //
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
public class UserAgg extends User{

    public short Area;
    public String Cod_uff;
    public String Cng_area;
    public String Cng_uff;
    public int Dist_def;
    public String Solo_dist;
    public String Data_Mod;
    public String Ora_Mod;
    public boolean IsNew;

    public UserAgg()
    {
        Area = -1;
        Cod_uff = "";
        Cng_area = "";
        Cng_uff = "";
        Dist_def = -1;
        Solo_dist = "";
        Data_Mod = "";
        Ora_Mod = "";
        IsNew = true;
        IsNew = true;
    }

    public UserAgg(String sUtente)
        throws Exception
    {
        super(sUtente);
        Area = -1;
        Cod_uff = "";
        Cng_area = "";
        Cng_uff = "";
        Dist_def = -1;
        Solo_dist = "";
        Data_Mod = "";
        Ora_Mod = "";
        IsNew = true;
        leggiCampi();
        IsNew = false;
    }

    public UserAgg(String sUtente, String sPassword)
        throws Exception
    {
        super(sUtente, sPassword);
        Area = -1;
        Cod_uff = "";
        Cng_area = "";
        Cng_uff = "";
        Dist_def = -1;
        Solo_dist = "";
        Data_Mod = "";
        Ora_Mod = "";
        IsNew = true;
        leggiCampi();
        IsNew = false;
    }

    public void leggiCampi()
        throws Exception
    {
        if(super.utente == null)
            throw new Exception("Utente non valorizzato");
        String sqlGetRecords = "Select * from " + Utile.getTabella("t_utedichiarazioni") + " Where utente = '" + super.utente.trim() + "'";
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = IGAConn.getConnection().createStatement();
            rs = stmt.executeQuery(sqlGetRecords);
            if(rs.next())
            {
                Area = rs.getShort("area");
                if(rs.wasNull())
                    Area = -1;
                String Cod_uff = rs.getString("cod_uff");
                if(rs.wasNull())
                    this.Cod_uff = "";
                else
                    this.Cod_uff = Cod_uff.trim();
                String Cng_area = rs.getString("cng_area");
                if(rs.wasNull())
                    this.Cng_area = "";
                else
                    this.Cng_area = Cng_area.trim();
                String Cng_uff = rs.getString("cng_uff");
                if(rs.wasNull())
                    this.Cng_uff = "";
                else
                    this.Cng_uff = Cng_uff.trim();
                Dist_def = rs.getShort("dist_def");
                if(rs.wasNull())
                    Dist_def = -1;
                String Solo_dist = rs.getString("solo_dist");
                if(rs.wasNull())
                    this.Solo_dist = "";
                else
                    this.Solo_dist = Solo_dist.trim();
                String Data_Mod = rs.getString("data_mod");
                if(rs.wasNull())
                    this.Data_Mod = "";
                else
                    this.Data_Mod = Data_Mod.trim();
                String Ora_Mod = rs.getString("ora_mod");
                if(rs.wasNull())
                    this.Ora_Mod = "";
                else
                    this.Ora_Mod = Ora_Mod;
            } else
            {
                throw new Exception();
            }
        }
        catch(ConnDBaseException connectdbexception)
        {
            throw new Exception("Connessione non disponibile");
        }
        catch(Exception exception)
        {
            throw new Exception("Utente non trovato");
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(stmt != null)
                    stmt.close();
                IGAConn.chiudiConnessione();
            }
            catch(Exception exception2) { }
        }
    }

    public void registra(boolean flRegistraPadre, boolean transazioneaperta)
        throws Exception
    {
        PreparedStatement pStmt = null;
        try
        {
            if(!transazioneaperta)
                IGAConn.iniziaTransazione();
            if(flRegistraPadre)
                super.registra(transazioneaperta);
            if(IsNew)
            {
                String sql = "INSERT INTO " + Utile.getTabella("t_utedichiarazioni") +
                            " (" + "utente," +
                               "area," +
                               "cod_uff," +
                               "cng_area," +
                               "cng_uff," +
                               "dist_def," +
                               "solo_dist," +
                               "data_mod," +
                               "ora_mod" + ")" +
                           " VALUES (?,?,?,?,?,?,?,?,?)";
                pStmt = IGAConn.getConnection().prepareStatement(sql);
                if(!Stringhe.isVuota(super.utente))
                    pStmt.setString(1, super.utente.trim());
                else
                    pStmt.setNull(1, 1);

                pStmt.setShort(2, Area);

                if(!Stringhe.isVuota(Cod_uff))
                    pStmt.setString(3, Cod_uff.trim());
                else
                    pStmt.setNull(3, 1);

                if(!Stringhe.isVuota(Cng_area))
                    pStmt.setString(4, Cng_area.trim());
                else
                    pStmt.setNull(4, 1);

                if(!Stringhe.isVuota(Cng_uff))
                    pStmt.setString(5, Cng_uff.trim());
                else
                    pStmt.setNull(5, 1);

                pStmt.setInt(6, Dist_def);

                if(!Stringhe.isVuota(Solo_dist))
                    pStmt.setString(7, Solo_dist.trim());
                else
                    pStmt.setNull(7, 1);

                pStmt.setString(8, GenerDate.getSDateFromUtil(GenerDate.getCurrentDateTime()));
                pStmt.setString(9, GenerDate.getSTimeFromUtil(GenerDate.getCurrentDateTime()));
                if(pStmt.executeUpdate() == 0)
                    throw new Exception();
                IsNew = false;
            } else
            {
                String sql = "UPDATE " + Utile.getTabella("t_utedichiarazioni") + " SET " + "area = ?," + "cod_uff = ?," + "cng_area = ?," + "cng_uff = ?," + "dist_def = ?," + "solo_dist = ?," + "data_mod = ?," + "ora_mod = ?" + " WHERE utente = '" + super.utente.trim() + "'";
                pStmt = IGAConn.getConnection().prepareStatement(sql);
                pStmt.setShort(1, Area);
                if(!Stringhe.isVuota(Cod_uff))
                    pStmt.setString(2, Cod_uff.trim());
                else
                    pStmt.setNull(2, 1);
                if(!Stringhe.isVuota(Cng_area))
                    pStmt.setString(3, Cng_area.trim());
                else
                    pStmt.setNull(3, 1);
                if(!Stringhe.isVuota(Cng_uff))
                    pStmt.setString(4, Cng_uff.trim());
                else
                    pStmt.setNull(4, 1);
                if(Dist_def == -1)
                    pStmt.setNull(5, 4);
                else
                    pStmt.setInt(5, Dist_def);
                if(!Stringhe.isVuota(Solo_dist))
                    pStmt.setString(6, Solo_dist.trim());
                else
                    pStmt.setNull(6, 1);
                pStmt.setString(7, GenerDate.getSDateFromUtil(GenerDate.getCurrentDateTime()));
                pStmt.setString(8, GenerDate.getSTimeFromUtil(GenerDate.getCurrentDateTime()));
                if(pStmt.executeUpdate() == 0)
                    throw new Exception();
            }
            if(!transazioneaperta)
                IGAConn.confermaTransazione();
        }
        catch(ConnDBaseException e1)
        {
            Debug.out(e1, 2);
            if(!transazioneaperta)
                IGAConn.annullaTransazione();
            throw new Exception("Connessione non disponibile");
        }
        catch(Exception e)
        {
            Debug.out(e, 2);
            if(!transazioneaperta)
                IGAConn.annullaTransazione();
            throw new Exception("Problemi in fase di registrazione");
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

    public void controllaCampi()
        throws Exception
    {
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
            UserAgg oUtente;
            for(rs = sqlStmtGetRecords.executeQuery(); rs.next(); vtListaRecords.addElement(oUtente))
            {
                oUtente = new UserAgg();
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
                String CodiceUfficio = rs.getString("cod_uff");
                if(rs.wasNull())
                    oUtente.Cod_uff = "";
                else
                    oUtente.Cod_uff = CodiceUfficio.trim();
                String Cng_area = rs.getString("cng_area");
                if(rs.wasNull())
                    oUtente.Cng_area = "";
                else
                    oUtente.Cng_area = Cng_area.trim();
                String Cng_uff = rs.getString("cng_uff");
                if(rs.wasNull())
                    oUtente.Cng_uff = "";
                else
                    oUtente.Cng_uff = Cng_uff.trim();
                oUtente.Dist_def = rs.getInt("dist_def");
                if(rs.wasNull())
                    oUtente.Dist_def = -1;
                String Solo_dist = rs.getString("solo_dist");
                if(rs.wasNull())
                    oUtente.Solo_dist = "";
                else
                    oUtente.Solo_dist = Solo_dist.trim();
                String Data_Mod = rs.getString("data_mod");
                if(rs.wasNull())
                    oUtente.Data_Mod = "";
                else
                    oUtente.Data_Mod = Data_Mod.trim();
                String Ora_Mod = rs.getString("ora_mod");
                if(rs.wasNull())
                    oUtente.Ora_Mod = "";
                else
                    oUtente.Ora_Mod = Ora_Mod.trim();
            }

        }
        catch(ConnDBaseException connectdbexception) { }
        catch(SQLException sqlexception) { }
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
            catch(Exception exception1) { }
        }
        return vtListaRecords;
    }


}
