package slanitsch;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Lambdas {

    /**
     * Main Methode
     * @param args Argumente der Main Methode
     */
    public static void main(String[] args) {
        Collection<String> test = new LinkedList<>(Arrays.asList("Test", "TEST", "test", "Test2", "Passt"));
        List<Double> zahlen = new LinkedList<>(Arrays.asList(2.2, 4.4, 0.3, 1.0, 6.0));
        print(test);
        System.out.println(hauptworte(test));
        System.out.println(mult(zahlen, 2.0));
        System.out.println(func(zahlen, new UnaryOperator<Double>() {
            @Override
            public Double apply(Double aDouble) {
                return aDouble*5;
            }
        }));
    }

    /**
     * Printet eine Collection mit einem Element pro Zeile.
     * @param collection Collection die geprintet werden soll
     */
    private static void print(Collection<?> collection){
        collection.forEach(System.out::println);
    }

    /**
     * Lässt nur Hauptworte in der Liste.
     * @param worte Liste an Worten
     * @return Liste aus Hauptwörtern
     */
    private static Collection<String> hauptworte(Collection<String> worte){
        worte.removeIf(wort -> (!wort.matches("[A-Z][A-Za-z]+")));
        return worte;
    }

    private static List<Double> mult(List<Double> zahlen, double faktor){
        zahlen.replaceAll(zahl -> zahl*faktor);
        return zahlen;
    }

    private static List<Double> func(List<Double> zahlen, UnaryOperator<Double> op){
        zahlen.replaceAll(zahl -> op.apply(zahl));
        return zahlen;
    }

    private static List<Double> numerisch(String ... elements){
        return null;
    }
}
