import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BankMetodyTest {
    String getDir = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\Bank.db";
    Connection c = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;

    public void update(double nowyStanK, int id) {

        try {
            String url = getDir;
            c = DriverManager.getConnection(url);

            pstmt = c.prepareStatement("update Konta set StanKonta=? where IDKonta=?");
            pstmt.setDouble(1, nowyStanK);
            pstmt.setInt(2, id);
            pstmt.execute();
            int update = pstmt.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            }
            catch (Exception exc) {
                System.out.println(exc);
            }
        }
    }

    public void updateSK(double NowySK, int id) {
        try {
            String url = getDir;
            c = DriverManager.getConnection(url);

            pstmt = c.prepareStatement("update Konta set StanKonta=? where IDKonta=?");
            pstmt.setDouble(1, NowySK);
            pstmt.setInt(2, id);
            pstmt.execute();
            int update = pstmt.executeUpdate();
        }
        catch (Exception exc) {
            System.out.println(exc);
        }
    }
}