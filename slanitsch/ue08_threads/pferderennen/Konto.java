package slanitsch.ue08_threads.pferderennen;

public class Konto {
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
    void add(int betrag) {
        int wert = getKontostand();
        wert = wert + betrag;
        setKontostand(wert);
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
        return output.substring(0, output.length()-1) + "€";
    }
}
