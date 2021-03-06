package slanitsch;

import java.util.*;
import java.util.function.UnaryOperator;

public class Lambdas {

    /**
     * Main Methode
     *
     * @param args Argumente der Main Methode
     */
    public static void main(String[] args) {
        Collection<String> test = new LinkedList<>(Arrays.asList("Test", "TEST", "test", "Test2", "Passt"));
        List<Double> zahlen = new LinkedList<>(Arrays.asList(2.2, 4.4, 0.3, 1.0, 6.0));
        print(test);
        System.out.println(hauptworte(test));
        System.out.println(mult(zahlen, 2.0));
        System.out.println(mult(zahlen, 4.0));
        System.out.println(func(zahlen, aDouble -> aDouble * 5));
        System.out.println(numerisch("-12", "5", "4", "Gustav", "-78", "15", "0", "Otto"));
    }

    /**
     * Printet eine Collection mit einem Element pro Zeile.
     *
     * @param collection Collection die geprintet werden soll
     */
    private static void print(Collection<?> collection) {
        collection.forEach(System.out::println);
    }

    /**
     * Lässt nur Hauptworte in der Liste.
     *
     * @param worte Liste an Worten
     * @return Liste aus Hauptwörtern
     */
    private static Collection<String> hauptworte(Collection<String> worte) {
        worte.removeIf(wort -> wort == null || (!wort.matches("[A-Z][A-Za-z]+")));
        return worte;
    }

    /**
     * Multipliziert eine Double Liste mit einem bestimmten Wert.
     *
     * @param zahlen Liste an Zahlen
     * @param faktor Faktor mit dem Multipliziert wird
     * @return Multiplizierte Liste
     */
    private static List<Double> mult(List<Double> zahlen, double faktor) {
        zahlen.replaceAll(zahl -> zahl * faktor);
        return zahlen;
    }

    /**
     * Wendet einen UnaryOperator auf eine Double Liste an.
     *
     * @param zahlen Liste an Doubles
     * @param op     UnaryOperator welcher angewandt werden soll
     * @return UnaryOperator angewandte Liste
     */
    private static List<Double> func(List<Double> zahlen, UnaryOperator<Double> op) {
        zahlen.replaceAll(op);
        return zahlen;
    }

    /**
     * Sortiert eine Liste.
     *
     * @param elements String array zum sortieren
     * @return sortierte Liste
     */
    public static List<String> numerisch(String... elements){
        List<String> erg = new ArrayList<>(Arrays.asList(elements));
        erg.sort((a, b) -> {
            if (a.matches("[\\d-]+") && b.matches("[\\d-]+")){
                return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
            } else if(a.matches("[\\d-]+")){
                return -1;
            } else if (b.matches("[\\d-]+")){
                return 1;
            } else {
                return a.compareTo(b);
            }
        });
        return erg;
    }
}
