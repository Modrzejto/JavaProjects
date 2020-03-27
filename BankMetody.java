import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import javax.swing.*;

public class BankMetody {
    String getDir = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\Bank.db";
    BankMetodyTest bmt = new BankMetodyTest();

    public void Welcome() {
        JFrame frameWelcome = new JFrame("BMBank");
        frameWelcome.setSize(400, 400);

        JLabel labelWelcome = new JLabel("Witaj w BMBank");
        labelWelcome.setBounds(90, 50, 200, 20);
        labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        labelWelcome.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton loginButton = new JButton("Zaloguj");
        loginButton.setBounds(90, 150, 200, 20);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameWelcome.dispose();
                zaloguj();
            }
        });

        JButton zarejestruj = new JButton("Zarejestruj");
        zarejestruj.setBounds(90, 180, 200, 20);

        zarejestruj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameWelcome.dispose();
                zarejestruj();
            }
        });

        JButton wyjdz = new JButton("Wyjście");
        wyjdz.setBounds(90, 210, 200, 20);

        wyjdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameWelcome.dispose();
                System.exit(1);
            }
        });

        frameWelcome.setLayout(null);
        frameWelcome.setVisible(true);
        frameWelcome.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameWelcome.add(labelWelcome);
        frameWelcome.add(loginButton);
        frameWelcome.add(wyjdz);
        frameWelcome.add(zarejestruj);
    }

    String idChk = null;
    String loginChk = null;
    String pwdChk = null;
    String login = null;
    String pwd = null;
    String id = null;
    String idDoKonta = null;

    public void zaloguj() {
        Connection c = null;
        Statement stmt = null;

        int countLogin = 0;
        int countPwd = 0;
        int countIDs = 0;

        String[] logins = new String[50];
        String[] pwds = new String[50];
        String[] ids = new String[50];

        try {
            String url = getDir;
            c = DriverManager.getConnection(url);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Logins;");

            while (rs.next()) {
                id = rs.getString("IDLogin");
                login = rs.getString("Login");
                pwd = rs.getString("Password");

                if (countIDs < ids.length) {
                    ids[countIDs] = id;
                    countIDs++;
                }

                if (countLogin < logins.length) {
                    logins[countLogin] = login;
                    countLogin++;
                }

                if (countPwd < pwds.length) {
                    pwds[countPwd] = pwd;
                    countPwd++;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    c.close();
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }

        JFrame loginFrame = new JFrame("Bank");
        loginFrame.setSize(230, 245);

        JLabel loginLabel = new JLabel("Login:");
        loginLabel.setBounds(5, 10, 200, 20);

        JTextField loginText = new JTextField();
        loginText.setBounds(5, 30, 200, 20);

        JLabel pwdLabel = new JLabel("Hasło: ");
        pwdLabel.setBounds(5, 50, 200, 20);

        JPasswordField pwdField = new JPasswordField();
        pwdField.setBounds(5, 70, 200, 20);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(5, 90, 200, 20);

        JTextField idTF = new JTextField();
        idTF.setBounds(5, 110, 200, 20);

        JLabel wrong = new JLabel();
        wrong.setBounds(5, 130, 200, 20);
        wrong.setForeground(Color.red);

        JButton loginButton = new JButton("Zaloguj");
        loginButton.setBounds(5, 150, 200, 20);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginChk = loginText.getText();
                pwdChk = new String(pwdField.getPassword());
                idChk = idTF.getText();

                for (int i = 0; i < logins.length; i++) {
                    if (loginChk.equals(logins[i])) {
                        if (pwdChk.equals(pwds[i]) && idChk.equals(ids[i])) {
                            idDoKonta = ids[i];
                            loginFrame.dispose();
                            BankGlowna();
                            return;
                        }
                    } else {
                        wrong.setText("Niewłaściwe hasło i/lub login");
                    }
                }
            }
        });

        JButton wyjdz = new JButton("Wyjdź do menu");
        wyjdz.setBounds(5, 180, 200, 20);

        wyjdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                Welcome();
            }
        });

        loginFrame.setVisible(true);
        loginFrame.setLayout(null);
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.add(loginLabel);
        loginFrame.add(loginText);
        loginFrame.add(pwdLabel);
        loginFrame.add(pwdField);
        loginFrame.add(wrong);
        loginFrame.add(loginButton);
        loginFrame.add(idLabel);
        loginFrame.add(idTF);
        loginFrame.add(wyjdz);
    }

    Connection cnc = null;
    Statement stmtZ = null;

    public void zarejestruj() {
        JFrame frameZare = new JFrame("Zarejestruj");
        frameZare.setSize(230, 400);

        int countLogin = 0;
        int countPwd = 0;
        int countIDs = 0;

        String[] logins = new String[50];
        String[] pwds = new String[50];
        int[] ids = new int[50];

        Connection c2 = null;
        Statement stmt3 = null;

        try {
            String url = getDir;
            c2 = DriverManager.getConnection(url);
            c2.setAutoCommit(false);

            stmt3 = c.createStatement();
            ResultSet rs = stmt3.executeQuery("SELECT * FROM Logins;");

            while (rs.next()) {
                int id = rs.getInt("IDLogin");
                login = rs.getString("Login");
                pwd = rs.getString("Password");

                if (countIDs < ids.length) {
                    ids[countIDs] = id;
                    countIDs++;
                }

                if (countLogin < logins.length) {
                    logins[countLogin] = login;
                    countLogin++;
                }

                if (countPwd < pwds.length) {
                    pwds[countPwd] = pwd;
                    countPwd++;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        finally {
            try {
                if (c2 != null) {
                    c2.close();
                }
                if (stmt3 != null) {
                    stmt3.close();
                }
            }
            catch (Exception exc) {
                System.out.println(exc);
            }
        }

        Connection c = null;
        Statement stmt = null;

        JLabel nazwaU = new JLabel("Wpisz nazwę użytkownika");
        nazwaU.setBounds(5, 10, 200, 20);

        JTextField wpiszNU = new JTextField();
        wpiszNU.setBounds(5, 30, 200, 20);

        JLabel pwdLabel = new JLabel("Wpisz hasło");
        pwdLabel.setBounds(5, 50, 200 ,20);

        JPasswordField pwdField = new JPasswordField();
        pwdField.setBounds(5, 70, 200 ,20);

        JLabel potwPwd = new JLabel("Potwierdź hasło");
        potwPwd.setBounds(5, 90, 200 ,20);

        JPasswordField potwPwdField = new JPasswordField();
        potwPwdField.setBounds(5, 110, 200,20);

        JLabel imie = new JLabel("Wpisz swoje imię");
        imie.setBounds(5, 130, 200,20);

        JTextField imieField = new JTextField();
        imieField.setBounds(5, 150, 200, 20);

        JLabel nazw = new JLabel("Wpisz swoje nazwisko");
        nazw.setBounds(5, 170, 200,20);

        JTextField nazwTF = new JTextField();
        nazwTF.setBounds(5, 190, 200, 20);

        JLabel idLabel = new JLabel("Wpisz swoje ID");
        idLabel.setBounds(5, 210, 200, 20);

        JTextField idTF = new JTextField();
        idTF.setBounds(5, 230, 200, 20);

        JButton zatwierdz = new JButton("Zatwierdź");
        zatwierdz.setBounds(5, 270, 200, 20);

        class insertTB {
            public void insertIntoTB(int id, String login, String pwd, String imie, String nazw) {
                try {
                    String url = getDir;
                    cnc = DriverManager.getConnection(url);

                    PreparedStatement pstmt = cnc.prepareStatement("insert into Logins values(?, ?, ?)");
                    pstmt.setInt(1, id);
                    pstmt.setString(2, login);
                    pstmt.setString(3, pwd);

                    pstmt.execute();
                    //int update = pstmt.executeUpdate();

                    PreparedStatement pstmt2 = cnc.prepareStatement("insert into Konta values(?, 0, ?, ?)");
                    pstmt2.setInt(1, id);
                    pstmt2.setString(2, imie);
                    pstmt2.setString(3, nazw);

                    pstmt2.execute();
                    //int update2 = pstmt2.executeUpdate();
                }
                catch (Exception exc) {
                    System.out.println(exc);
                } finally {
                    try {
                        if (stmtZ != null) {
                            cnc.close();
                        }
                    } catch (Exception exc) {
                        System.out.println(exc);
                    }
                }
            }
        }

        insertTB itb = new insertTB();

        zatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int getID = Integer.parseInt(idTF.getText());
                String getLogin = wpiszNU.getText();
                String passText = new String(pwdField.getPassword());
                String passChkText = new String(potwPwdField.getPassword());
                String getImie = imieField.getText();
                String getNazw = nazwTF.getText();

                if (passText.equals(passChkText) && passText != null && passChkText != null) {
                    for (int i = 0; i < ids.length; i++) {
                        if (getLogin != null && getImie != null && getNazw != null) {
                            if (getID != ids[i]) {
                                itb.insertIntoTB(getID, getLogin, passText, getImie, getNazw);
                                JOptionPane.showMessageDialog(frameZare, "Konto utworzone pomyślnie");
                                frameZare.dispose();
                                Welcome();
                                return;
                            }
                            else {
                                JOptionPane.showMessageDialog(frameZare, "Podane ID już istnieje", "Uwaga", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(frameZare, "Wartości nie mogą być puste", "Uwaga", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(frameZare, "Hasła nie są zgodne", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton powrot = new JButton("Wyjdź do menu");
        powrot.setBounds(5, 310, 200, 20);

        powrot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameZare.dispose();
                Welcome();
            }
        });

        frameZare.setVisible(true);
        frameZare.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameZare.setLayout(null);
        frameZare.add(nazwaU);
        frameZare.add(idLabel);
        frameZare.add(idTF);
        frameZare.add(zatwierdz);
        frameZare.add(nazw);
        frameZare.add(imieField);
        frameZare.add(powrot);
        frameZare.add(nazwTF);
        frameZare.add(potwPwd);
        frameZare.add(wpiszNU);
        frameZare.add(pwdField);
        frameZare.add(pwdLabel);
        frameZare.add(imie);
        frameZare.add(potwPwdField);
    }

    public void BankGlowna() {
        Connection c = null;
        Statement stmt = null;

        int countIDK = 0;
        int countSK = 0;
        int countI = 0;
        int countN = 0;

        String[] idKonta = new String[50];
        String[] stanK = new String[50];
        String[] imiona = new String[50];
        String[] nazwiska = new String[50];

        JFrame bankFrame = new JFrame("Bank");
        bankFrame.setSize(260, 190);

        try {
            String url = getDir;
            c = DriverManager.getConnection(url);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM Konta;");

            while (rs2.next()) {
                String id = rs2.getString("IDKonta");
                String stanKonta = rs2.getString("StanKonta");
                String imie = rs2.getString("Imie");
                String nazwisko = rs2.getString("Nazwisko");

                if (countIDK < idKonta.length) {
                    idKonta[countIDK] = id;
                    countIDK++;
                }

                if (countSK < stanK.length) {
                    stanK[countSK] = stanKonta;
                    countSK++;
                }

                if (countI < imiona.length) {
                    imiona[countI] = imie;
                    countI++;
                }

                if (countN < nazwiska.length) {
                    nazwiska[countN] = nazwisko;
                    countN++;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    c.close();
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }

        JLabel imieLabel = new JLabel();
        imieLabel.setBounds(5, 10, 390, 20);
        imieLabel.setFont(new Font("Arial", Font.BOLD, 17));

        JLabel stanKonta = new JLabel();
        stanKonta.setBounds(5, 40, 200, 20);

        for (int i = 0; i < idKonta.length; i++) {
            if (idDoKonta.equals(idKonta[i])) {
                stanKonta.setText("Stan konta: " + stanK[i] + " PLN");
                imieLabel.setText("Witaj " + imiona[i] + " " + nazwiska[i]);
            }
        }

        JButton powrot = new JButton("Wyloguj");
        powrot.setBounds(5, 80, 200, 20);

        powrot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankFrame.dispose();
                zaloguj();
            }
        });

        JMenu menu = new JMenu("Operacje");
        JMenuItem przelew = new JMenuItem("Przelew");
        JMenuBar mb = new JMenuBar();
        menu.add(przelew);
        mb.add(menu);

        przelew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bankFrame.dispose();
                przelew();
            }
        });

        bankFrame.setVisible(true);
        bankFrame.setLayout(null);
        bankFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        bankFrame.add(stanKonta);
        bankFrame.add(powrot);
        bankFrame.add(imieLabel);
        bankFrame.setJMenuBar(mb);
    }

    double przelewKw = 0.0;
    String nazwaDoP = null;
    Connection c = null;
    double roznica = 0.0;

    public void przelew() {
        JFrame framePrzelew = new JFrame("Bank");
        framePrzelew.setSize(300, 170);

        Statement stmt = null;

        int countIDK = 0;
        int countSK = 0;
        int countI = 0;
        int countN = 0;

        String[] idKonta = new String[50];
        double[] stanK = new double[50];
        String[] imiona = new String[50];
        String[] nazwiska = new String[50];

        try {
            String url = getDir;
            c = DriverManager.getConnection(url);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM Konta;");

            while (rs2.next()) {
                String id = rs2.getString("IDKonta");
                double stanKonta = rs2.getDouble("StanKonta");
                String imie = rs2.getString("Imie");
                String nazwisko = rs2.getString("Nazwisko");

                if (countIDK < idKonta.length) {
                    idKonta[countIDK] = id;
                    countIDK++;
                }

                if (countSK < stanK.length) {
                    stanK[countSK] = stanKonta;
                    countSK++;
                }

                if (countI < imiona.length) {
                    imiona[countI] = imie;
                    countI++;
                }

                if (countN < nazwiska.length) {
                    nazwiska[countN] = nazwisko;
                    countN++;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    c.close();
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }

        JLabel labelDop = new JLabel("Wybierz klienta któremu chcesz zrobić przelew");
        labelDop.setBounds(5, 10, 300, 20);

        String data[] = new String[50];

        for (int i = 0; i < idKonta.length; i++) {
            if (idKonta[i] != null) {
                if (idKonta[i].equals("1")) {

                } else {
                    if (idKonta[i].equals(idDoKonta)) {

                    } else {
                        data[i] = "ID: " + idKonta[i] + " " + imiona[i] + " " + nazwiska[i];
                    }
                }
            }
        }

        JComboBox listaDoP = new JComboBox(data);
        listaDoP.setBounds(5, 30, 270, 20);

        JLabel opis = new JLabel("Wpisz kwotę jaką chcesz przelać");
        opis.setBounds(5, 50, 270, 20);

        JTextField kwotaF = new JTextField();
        kwotaF.setBounds(5, 70, 270, 20);

        JButton zatwierdz = new JButton("Zatwierdź");
        zatwierdz.setBounds(5, 100, 270, 20);

        int index = Integer.parseInt(idDoKonta);

        class updateDB {
            public double round(double value, int places) {
                if (places < 0) throw new IllegalArgumentException();

                BigDecimal bd = BigDecimal.valueOf(value);
                bd = bd.setScale(places, RoundingMode.HALF_UP);
                return bd.doubleValue();
            }

            public void odejmij() {
                try {
                    for (int i = 0; i < nazwiska.length; i++) {
                        if (nazwaDoP.contains(idKonta[i])) {
                            if (stanK[index - 1] >= przelewKw) {
                                roznica = stanK[index - 1] - przelewKw;
                                round(roznica, 2);
                                round(przelewKw, 2);
                                bmt.update(roznica, index);
                                stanK[index - 1] = stanK[index - 1] - przelewKw;

                                JOptionPane.showMessageDialog(framePrzelew, "Pomyślnie przelano " + przelewKw + " PLN" + " do " + imiona[listaDoP.getSelectedIndex()] + " " + nazwiska[listaDoP.getSelectedIndex()], "Przelew", JOptionPane.PLAIN_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(framePrzelew, "Zbyt mało środków na koncie", "Uwaga", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                } catch (Exception ex) {
                    //System.out.println(ex);
                }
            }
            public void dodaj() {
                double nowySK = przelewKw + stanK[listaDoP.getSelectedIndex()];
                int noweID = listaDoP.getSelectedIndex() + 1;
                round(nowySK, 2);
                bmt.updateSK(nowySK, noweID);
            }
        }

        updateDB uDB = new updateDB();

        zatwierdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                przelewKw = Double.parseDouble(kwotaF.getText());

                nazwaDoP = listaDoP.getItemAt(listaDoP.getSelectedIndex()).toString();

                uDB.odejmij();
                uDB.dodaj();

                framePrzelew.dispose();
                BankGlowna();
            }
        });

        framePrzelew.setVisible(true);
        framePrzelew.setLayout(null);
        framePrzelew.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        framePrzelew.add(listaDoP);
        framePrzelew.add(labelDop);
        framePrzelew.add(kwotaF);
        framePrzelew.add(opis);
        framePrzelew.add(zatwierdz);
    }
}