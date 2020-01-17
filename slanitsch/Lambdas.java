package slanitsch;


import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class Lambdas {

    /**
     * Main Methode
     * @param args Argumente der Main Methode
     */
    public static void main(String[] args) {
        Collection<String> test = new LinkedList<>(Arrays.asList("Test", "TEST", "test", "Test2", "Passt"));
        print(test);
        System.out.println(hauptworte(test));
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
        worte.removeIf(wort ->
                (wort.charAt(0)<='A')
                        || (wort.charAt(0)>='Z')
                        || (!wort.matches("[A-Za-z]+")));
        return worte;
    }
}
