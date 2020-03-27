import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;

public class SwingMetody extends JFrame{
    Connection c = null;
    Statement stmt = null;
    int sumaProd = 0;
    int zaplacono = 0;
    String path = System.getProperty("user.dir");

    public void Welcome() throws Exception {
        JFrame frameWelcome = new JFrame("VapeShop");
        frameWelcome.setSize(200, 160);

        JLabel labelMain = new JLabel("Witaj w strefie");
        labelMain.setBounds(0, 10, 200, 20);
        labelMain.setHorizontalAlignment(SwingConstants.CENTER);

        JButton button1 = new JButton("Pokaż bazę liquidów");
        button1.setBounds(0, 40, 200, 20);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameWelcome.dispose();
                BazaLQ();
            }
        });

        JButton gotoKosz = new JButton("Zakupy");
        gotoKosz.setBounds(0, 70, 200, 20);

        gotoKosz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameWelcome.dispose();
                try {
                    Zakupy();
                }
                catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        JButton exit = new JButton("Wyjdź");
        exit.setBounds(0, 100, 200, 20);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(9);
            }
        });

        JLabel labelProd = new JLabel();
        labelProd.setBounds(10, 110, 400, 20);

        frameWelcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameWelcome.setVisible(true);
        frameWelcome.setLayout(null);
        frameWelcome.add(labelMain);
        frameWelcome.add(labelProd);
        frameWelcome.add(button1);
        frameWelcome.add(gotoKosz);
        frameWelcome.add(exit);
    }

    JScrollPane scrollPane;

    public void Zakupy() throws Exception{

        String[] koszyk = new String[20];
        String[] lqList = new String[20];
        int count = 0;
        int count2 = 0;
        int count3 = 0;
        int[] ceny = new int[20];

        JFrame frameZakupy = new JFrame("Zakupy");
        frameZakupy.setSize(400, 300);


        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:D:\\SQLiteDB\\KasaDB.db");
        c.setAutoCommit(false);

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM KasaTB;");

        while (rs.next()) {
            int id = rs.getInt("IDProd");
            String nazwa = rs.getString("NazwaProd");
            int cena = rs.getInt("Cena");

            if (count < lqList.length) {
                lqList[count] = nazwa;
                count++;
            }

            if (count3 < ceny.length) {
                ceny[count3] = cena;
                count3++;
            }
        }

        JComboBox cb = new JComboBox(lqList);
        cb.setBounds(0, 0, 200, 20);

        JLabel suma = new JLabel();
        suma.setBounds(0, 140, 150, 20);

        DefaultListModel<String> l1 = new DefaultListModel<>();

        JButton dodajButton = new JButton("Dodaj do koszyka");
        dodajButton.setBounds(240, 0, 150, 20);

        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = cb.getItemAt(cb.getSelectedIndex()).toString();

                l1.addElement(data);

                sumaProd += ceny[cb.getSelectedIndex()];

                String data2 = "Cena łączna: " + sumaProd + " PLN";
                suma.setText(data2);

                String sql = "ALTER TABLE KasaTB ADD COLUMN Column_name Datatype";
            }
        });

        JTextField doZaplaty = new JTextField();
        doZaplaty.setToolTipText("Wprowadź kwotę którą dał ci klient");
        doZaplaty.setBounds(0, 190, 150, 20);

        JButton zaplac = new JButton("Zapłać");
        zaplac.setBounds(0, 160, 150 , 20);

        zaplac.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int integer = Integer.parseInt(doZaplaty.getText());

                if (integer < sumaProd) {
                    JOptionPane.showMessageDialog(frameZakupy, "Klient dał za mało PLN", "Uwaga", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(frameZakupy, "Wydaj " + (integer - sumaProd) + " PLN reszty", "Reszta", JOptionPane.PLAIN_MESSAGE);

                    frameZakupy.dispose();
                    try {
                        Welcome();
                    }
                    catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });

        JButton wyczyscListe = new JButton("Wyczyść listę");
        wyczyscListe.setBounds(240, 30, 150, 20);

        wyczyscListe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l1.removeAllElements();
                sumaProd = 0;
            }
        });

        JButton exit = new JButton("Wyjdź do menu");
        exit.setBounds(240, 160, 150, 20);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameZakupy.dispose();
                try {
                    Welcome();
                }
                catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        JList<String> list = new JList<>(l1);
        list.setBounds(100, 40, 200, 200);
        list.setVisibleRowCount(5);

        scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0, 20, 140 ,100);

        frameZakupy.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameZakupy.setVisible(true);
        frameZakupy.setLayout(null);
        frameZakupy.add(cb);
        frameZakupy.add(dodajButton);
        frameZakupy.add(suma);
        frameZakupy.add(scrollPane);
        frameZakupy.add(zaplac);
        frameZakupy.add(doZaplaty);
        frameZakupy.add(wyczyscListe);
        frameZakupy.add(exit);
    }

    public void BazaLQ() {
        JFrame frameLQ = new JFrame("BazaLQ");
        frameLQ.setSize(400, 270);

        String[][] tabela = new String[50][4];
        int count1 = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:"+ path +"\\KasaDB.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM KasaTB;");

            while (rs.next()) {
                String id = rs.getString("IDProd");
                String nazwa = rs.getString("NazwaProd");
                String cena = rs.getString("Cena");

                if (count1 < tabela.length) {
                    if (true) {
                        if (nazwa != null) {
                            tabela[count1][0] = id;
                            tabela[count1][1] = nazwa;
                            tabela[count1][2] = cena;
                        }
                    }
                    count1++;
                }
            }
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }

        String column[]={"ID","Nazwa","Cena"};

        JTable jt = new JTable(tabela, column);
        jt.setBounds(30, 40, 300, 200);

        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(0, 0, frameLQ.getWidth(), 200);

        JButton powrot = new JButton("Wyjdź do menu");
        powrot.setBounds(120, 200, 150, 20);

        powrot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameLQ.dispose();
                try {
                    Welcome();
                }
                catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });

        frameLQ.add(sp);
        frameLQ.add(powrot);
        frameLQ.setLayout(null);
        frameLQ.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameLQ.setVisible(true);
    }
}