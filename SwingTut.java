import javax.swing.*;

public class SwingTut {
    public static void main(String[] args) {
          SwingMetody sm = new SwingMetody();

        try {
            sm.Welcome();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
