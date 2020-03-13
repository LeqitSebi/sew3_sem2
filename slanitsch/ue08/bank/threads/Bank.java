package slanitsch.ue08.bank.threads;

/**
 * Bank that is used for main method (to manage accounts and transactions).
 */
public class Bank {

    /**
     * Main Methode.
     * @param args Args for main methode
     */
    public static void main(String[] args) throws InterruptedException {
        Konto k0 = new Konto();
        System.out.println("Empty account: " + k0);
        System.out.println(k0.getKontostand());
        k0.setKontostand(100);
        System.out.println(k0.getKontostand());
        System.out.println("-----------------------------");
        Konto k1 = new Konto(60000000);
        Konto k2 = new Konto(70000000);
        Konto k3 = new Konto(120000000);
        System.out.println("k1 - " + k1);
        System.out.println("k2 - " + k2);
        System.out.println("k3 - " + k3);
        System.out.println("-----------------------------");
        Ueberweiser ue1 = new Ueberweiser(k1, k2, 10000000, 10);
        Ueberweiser ue2 = new Ueberweiser(k2, k3, 10000000, 10);
        Ueberweiser ue3 = new Ueberweiser(k3, k1, 10000000, 10);
        long before = System.currentTimeMillis();
        ue1.start();
        ue2.start();
        ue3.start();
        ue3.join();
        long after = System.currentTimeMillis();
        System.out.println("k1 - " + k1);
        System.out.println("k2 - " + k2);
        System.out.println("k3 - " + k3);
        System.out.println("Alle Überweisungen in " + (after-before) + "ms abgeschlossen");
        //avg time for threaded methode 120ms - but wrong
        //avg time for synced methode 1500ms - still wrong
    }

}

/**
 * Class used to transfer money between accounts more than once.
 */
class Ueberweiser extends Thread{
    Konto von;
    Konto nach;
    int anzahl;
    int betrag;

    /**
     * Constructor given all variables.
     * @param von Konto from which the money should be taken
     * @param nach Konto to which the money should be added
     * @param anzahl how many times the betrag should be transfered
     * @param betrag which amount of money should be transfered
     */
    public Ueberweiser(Konto von, Konto nach, int anzahl, int betrag){
        this.von = von;
        this.nach = nach;
        this.anzahl = anzahl;
        this.betrag = betrag;
    }

    /**
     * Methode to run a Ueberweiser.
     */
    public void run(){
        for (int i = 0; i < anzahl; i++) {
            von.add(-betrag);
            nach.add(betrag);
        }
    }
}

/**
 * Manages an account with balance.
 */
class Konto{
    int kontostand;

    /**
     * Empty constructor.
     * initialises account with balance 0€
     */
    public Konto(){
        kontostand = 0;
    }

    /**
     * Constructor given all variables.
     * @param kontostand balance that the account should be initialised with
     */
    public Konto(int kontostand){
        this.kontostand = kontostand;
    }

    /**
     * gets the current balance of an account
     * @return current balance as an Integer
     */
    public int getKontostand() {
        return this.kontostand;
    }

    /**
     * sets the balance of an account
     * @param kontostand balance to set to as an Integer
     */
    public void setKontostand(int kontostand) {
        this.kontostand = kontostand;
    }

    /**
     * adds a specific balance to an account
     * @param betrag amount to add
     */
    public synchronized void add(int betrag){
        this.kontostand += betrag;
    }

    /**
     * Converts an account to a printable useful string.
     * @return Kontostand + current balance with decimals and € as a String
     */
    @Override
    public String toString(){
        String kontostandstr = Integer.toString(kontostand);
        StringBuilder output = new StringBuilder();
        int helper = (kontostandstr.length()%3);
        if (helper!=0) {
            output.append(kontostandstr, 0, helper).append(".");
        }
        String tripples = kontostandstr.substring(helper);
        for (int i = 0; i < tripples.length(); i+=3) {
            output.append(tripples, i, i + 3).append(".");
        }
        return "Kontostand: " + output.substring(0, output.length()-1) + "€";
    }
}


