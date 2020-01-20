package slanitsch;

import java.util.LinkedList;
import java.util.List;

import static slanitsch.Winner.tdfWinners;

public class Streams {

    /**
     * Main Methode
     * @param args Argumente zur Main Methode
     */
    public static void main(String[] args) {
        System.out.println("a) Winners of Tours Less than 3500km - " + winnersUnder35());
        System.out.println("b) Winners of Tours Greater than 3500km - " + winnersOver35());
        System.out.println("c) winnerObjectsOfToursLessThan3500kmLimit2 - " + firstTwoWinnersList());
        System.out.println("d) firstTwoWinnersOfToursLessThan3500km - " + firstTwoWinners());
        System.out.println("e) distinctTDFWinners - " + distinctTDFWinners());
        System.out.println("f) numberOfDistinceWinners - " + numberOfDistinctWinners());
        System.out.println("g) skipEveryOtherTDFWinner - " + winnersStartAtTwo());
        System.out.println("h) mapWinnerYearNamesToList - "+ mapWinnerYearNamesToList());
    }

    /**
     * Ermittelt alle Sieger, der Touren unter 3500 Kilometer
     * @return Liste der Sieger
     */
    private static List<String> winnersUnder35(){
        List<String> output = new LinkedList<>();
        tdfWinners.stream().filter(n -> n.getLengthKm() < 3500).forEach(n -> output.add(n.getName()));
        return output;
    }

    /**
     * Ermittelt alle Sieger, der Touren über 3500 Kilometer
     * @return Liste der Sieger
     */
    private static List<String> winnersOver35(){
        List<String> output = new LinkedList<>();
        tdfWinners.stream().filter(n -> n.getLengthKm() > 3500).forEach(n -> output.add(n.getName()));
        return output;
    }

    /**
     * Ermittelt die ersten zwei Sieger der Touren unter 3500 Kilometer.
     * @return Liste der Sieger
     */
    private static List<Winner> firstTwoWinnersList(){
        List<Winner> output = new LinkedList<>();
        tdfWinners.stream().filter(n -> n.getLengthKm() < 3500).limit(2).forEach(output::add);
        return output;
    }

    /**
     * Ermittelt die ersten zwei Sieger der Touren unter 3500 Kilometer.
     * @return Liste der Sieger
     */
    private static List<String> firstTwoWinners(){
        List<String> output = new LinkedList<>();
        firstTwoWinnersList().forEach(n -> output.add(n.getName()));
        return output;
    }

    /**
     * Ermittelt alle Tour de France Sieger und gibt sie nur einmal aus.
     * @return Liste der Sieger
     */
    private static List<String> distinctTDFWinners(){
        List<String> output = new LinkedList<>();
        tdfWinners.forEach(n -> output.add(n.getName()));
        return output;
    }

    /**
     * Ermittelt die Anzahl der Personen die schon mindestens eine Tour de France gewonnen haben.
     * @return Anzahl der Sieger
     */
    private static int numberOfDistinctWinners(){
        List<String> distinctWinners = distinctTDFWinners();
        return distinctWinners.size();
    }

    private static List<Winner> winnersStartAtTwo(){
        List<Winner> output = new LinkedList<>();
        tdfWinners.stream().skip(2).forEach(output::add);
        return output;
    }

    /**
     * Ermittelt alle Sieger plus Jahre in folgendem Format: Jahr - Sieger.
     * @return Liste der Sieger plus Jahre
     */
    private static List<String> mapWinnerYearNamesToList(){
        List<String> output = new LinkedList<>();
        tdfWinners.forEach(n -> output.add(n.getYear() + " - " + n.getName()));
        return output;
    }

}
