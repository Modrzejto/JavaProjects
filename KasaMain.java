import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class KasaMain {
    public static void main(String[] args) throws InterruptedException {
        KasaMetody km = new KasaMetody();
        Scanner in = new Scanner(System.in);

        System.out.println("Witaj w systemie kasowym");
        System.out.println("Ładowanie systemu, proszę czekać");
        TimeUnit.SECONDS.sleep(5);

        for (;;) {
            System.out.println("1. Dodaj produkt do koszyka");
            System.out.println("2. Sprawdź zawartość koszyka");
            System.out.println("3. Zapłać");
            System.out.println("4. Wyszukaj ID");
            System.out.println("5. Wyjdź");
            int chc = in.nextInt();

            if (chc == 1) {
                km.DodajDoKoszyka();
            }
            else if (chc == 2) {
                km.WyswietlKoszyk();
            }
            else if (chc == 3) {
                km.WyswietlKoszyk();
                System.out.println("Naciśnij enter aby zapłacić");
                TimeUnit.SECONDS.sleep(3);
                System.exit(2);
            }
            else if (chc == 4) {
                System.out.println("1. Wyświetl całą bazę danych");
                System.out.println("2. Wyszukaj po ID");
                int chc2 = in.nextInt();

                if (chc2 == 1) {
                    km.PokazBaze();
                }
                else if (chc2 == 2) {
                    km.WyszukajProduktID();
                }
                else {
                    System.out.println("Błąd, wyłączam program");
                    System.exit(1);
                }
            }
            else if (chc == 5) {
                System.exit(2);
            }
            else {
                System.out.println("Błąd, wyłączam program");
                System.exit(1);
            }
        }
    }
}
