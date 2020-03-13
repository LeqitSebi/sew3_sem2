public class Bank {

    public static void main(String[] args) {
        Konto k1 = new Konto(60000000);
        Konto k2 = new Konto(70000000);
        Konto k3 = new Konto(120000000);
        System.out.println("k1 - " + k1);
        System.out.println("k2 - " + k2);
        System.out.println("k3 - " + k3);
        Ueberweiser ue1 = new Ueberweiser(k1, k2, 10000000, 10);
        Ueberweiser ue2 = new Ueberweiser(k2, k3, 10000000, 10);
        Ueberweiser ue3 = new Ueberweiser(k3, k1, 10000000, 10);
        long before = System.currentTimeMillis();
        ue1.run();
        ue2.run();
        ue3.run();
        long after = System.currentTimeMillis();
        System.out.println("k1 - " + k1);
        System.out.println("k2 - " + k2);
        System.out.println("k3 - " + k3);
        System.out.println("Alle Überweisungen in " + (after-before) + "ms abgeschlossen");
        //avg time for first methode 96ms
    }

}

class Ueberweiser{
    Konto von;
    Konto nach;
    int anzahl;
    int betrag;

    public Ueberweiser(Konto von, Konto nach, int anzahl, int betrag){
        this.von = von;
        this.nach = nach;
        this.anzahl = anzahl;
        this.betrag = betrag;
    }

    public void run(){
        for (int i = 0; i < anzahl; i++) {
            von.add(-betrag);
            nach.add(betrag);
        }
    }
}

class Konto{
    int kontostand;

    public Konto(){
        kontostand = 0;
    }

    public Konto(int kontostand){
        this.kontostand = kontostand;
    }

    public int getKontostand() {
        return this.kontostand;
    }

    public void setKontostand(int kontostand) {
        this.kontostand = kontostand;
    }

    void add(int betrag){
        this.kontostand += betrag;
    }

    @Override
    public String toString(){
        String kontostandstr = Integer.toString(kontostand);
        StringBuilder output = new StringBuilder();
        int helper = (kontostandstr.length()%3);
        if (helper!=0) {
            output.append(kontostandstr.substring(0, helper)).append(".");
        }
        String tripples = kontostandstr.substring(helper);
        for (int i = 0; i < tripples.length(); i+=3) {
            output.append(tripples.substring(i, i + 3)).append(".");
        }
        return "Kontostand: " + output.substring(0, output.length()-1) + "€";
    }
}

