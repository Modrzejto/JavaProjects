import java.sql.*;
import java.util.Scanner;

public class KasaMetody {
    Connection c = null;
    Statement stmt = null;
    Scanner in = new Scanner(System.in);

    int count = 0;
    int id = 0;
    String nazwa = null;
    String[] koszyk = new String[50];
    double cena = 0;
    double total = 0;

    public void PokazBaze() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:D:\\SQLiteDB\\KasaDB.db");
            c.setAutoCommit(false);

            System.out.println("Wpisz nazwę produktu bez spacji: ");
            String toSearch = in.next();

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM KasaTB;");

            while (rs.next()) {
                id = rs.getInt("IDProd");
                nazwa = rs.getString("NazwaProd");
                cena = rs.getDouble("Cena");

                System.out.println("ID: " + id + " " + nazwa + " " + cena + " PLN");
            }
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    public String WyszukajProduktNazwa() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:D:\\SQLiteDB\\KasaDB.db");
            c.setAutoCommit(false);

            System.out.println("Wpisz nazwę produktu bez spacji: ");
            String toSearch = in.next();

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM KasaTB WHERE NazwaProd LIKE \"%" + toSearch + "%\"; ");

            while (rs.next()) {
                id = rs.getInt("IDProd");
                nazwa = rs.getString("NazwaProd");
                cena = rs.getDouble("Cena");

                System.out.println("ID: " + id + " " + nazwa + " " + cena + " PLN");
            }
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }

        return nazwa;
    }

    public int WyszukajProduktID() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:D:\\SQLiteDB\\KasaDB.db");
            c.setAutoCommit(false);

            System.out.println("Wpisz ID Produktu: ");
            int toSearch = in.nextInt();

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM KasaTB WHERE IDProd =" + toSearch);

            while (rs.next()) {
                id = rs.getInt("IDProd");
                nazwa = rs.getString("NazwaProd");
                cena = rs.getDouble("Cena");
            }
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }

        return id;
    }

    public void DodajDoKoszyka() {
        WyszukajProduktNazwa();

        System.out.println("Czy dodać " + nazwa + " do koszyka (T/N)");
        String chc = in.next().toLowerCase();

        if (chc.equals("t")) {
            koszyk[count] = nazwa;
            total += cena;
            count++;
        }
        else {

        }
    }

    public void WyswietlKoszyk() {
        for (int i = 0; i < koszyk.length; i++) {
            if (koszyk[i] == null) {

            }
            else {
                System.out.println(koszyk[i]);
            }
        }

        System.out.println("Cena łączna: " + total);
    }
}
