package slanitsch;

import java.util.*;
import java.util.stream.Collectors;

import static slanitsch.Winner.tdfWinners;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class Streams {

    /**
     * Main Methode
     *
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
        System.out.println("h) mapWinnerYearNamesToList - " + mapWinnerYearNamesToList());
        System.out.println("i) mapWinnerNameLengthToList - " + nameLengths());
        System.out.println("j) Wiggins wins - " + wigginsWinYear());
        System.out.println("k) winnerYear2014 - " + winner2014());
        System.out.println("l) totalDistance - " + allTourLengths());
        System.out.println("m) shortestDistance - " + shortestTour());
        System.out.println("n) longestDistance - " + longestTour());
        System.out.println("o) fastestTDF winner - " + fastestWinner());
        System.out.println("p) fastestTDF aveSpeed - " + highestAvgSpeed());
        System.out.println("q) allTDFWinnersTeams - " + allTeams());
        System.out.println("r) winnersByNationality - " + winnersByNationality());
        System.out.println("s) winsByNationalityCounting - " + nationalityWins());
    }

    /**
     * Ermittelt alle Sieger, der Touren unter 3500 Kilometer
     *
     * @return Liste der Sieger
     */
    private static List<String> winnersUnder35() {
        return tdfWinners.stream().filter(n -> n.getLengthKm() < 3500).map(Winner::getName).collect(Collectors.toList());
    }

    /**
     * Ermittelt alle Sieger, der Touren über 3500 Kilometer
     *
     * @return Liste der Sieger
     */
    private static List<String> winnersOver35() {
        return tdfWinners.stream().filter(n -> n.getLengthKm() > 3500).map(Winner::getName).collect(Collectors.toList());
    }

    /**
     * Ermittelt die ersten zwei Sieger der Touren unter 3500 Kilometer.
     *
     * @return Liste der Sieger
     */
    private static List<Winner> firstTwoWinnersList() {
        return tdfWinners.stream().filter(n -> n.getLengthKm() < 3500).limit(2).collect(Collectors.toList());
    }

    /**
     * Ermittelt die ersten zwei Sieger der Touren unter 3500 Kilometer.
     *
     * @return Liste der Sieger
     */
    private static List<String> firstTwoWinners() {
        return tdfWinners.stream().filter(n -> n.getLengthKm() < 3500).limit(2).map(Winner::getName).collect(Collectors.toList());
    }

    /**
     * Ermittelt alle Tour de France Sieger und gibt sie nur einmal aus.
     *
     * @return Liste der Sieger
     */
    private static List<String> distinctTDFWinners() {
        return tdfWinners.stream().map(Winner::getName).distinct().collect(Collectors.toList());
    }

    /**
     * Ermittelt die Anzahl der Personen die schon mindestens eine Tour de France gewonnen haben.
     *
     * @return Anzahl der Sieger
     */
    private static int numberOfDistinctWinners() {
        return tdfWinners.stream().map(Winner::getName).distinct().collect(Collectors.toList()).size();
    }

    /**
     * Ermittelt eine Winner Objekt Liste die bei der 3. TDF startet
     *
     * @return Winner Objekt Liste
     */
    private static List<Winner> winnersStartAtTwo() {
        return tdfWinners.stream().skip(2).collect(Collectors.toList());
    }

    /**
     * Ermittelt alle Sieger plus Jahre in folgendem Format: Jahr - Sieger.
     *
     * @return Liste der Sieger plus Jahre
     */
    private static List<String> mapWinnerYearNamesToList() {
        List<String> output = new LinkedList<>();
        tdfWinners.forEach(n -> output.add(n.getYear() + " - " + n.getName()));
        return output;
    }

    /**
     * Ermittelt die Länge der Namen jedes TDF Siegers.
     *
     * @return Liste der Namenslängen
     */
    private static List<Integer> nameLengths() {
        List<Integer> output = new LinkedList<>();
        tdfWinners.forEach(n -> output.add(n.getName().length()));
        return output;
    }

    /**
     * Ermittelt das erste Jahr in dem Wiggins gewonnen hat.
     *
     * @return Jahr als Integer
     */
    private static int wigginsWinYear() {
        return tdfWinners.stream().filter(n -> n.getName().equals("Bradley Wiggins")).findFirst().get().getYear();
    }

    /**
     * Ermittelt den Sieger des Jahres 2014.
     *
     * @return Namen des Siegers als String
     */
    private static String winner2014() {
        return tdfWinners.stream().filter(n -> n.getYear() == 2014).findFirst().get().getName();
    }

    /**
     * Ermittelt die Summe aller TDF Längen
     *
     * @return Summe als Integer
     */
    private static int allTourLengths() {
        return tdfWinners.stream().mapToInt(Winner::getLengthKm).sum();
    }

    /**
     * Ermittelt die Länge der kürzesten Tour.
     *
     * @return Tourlänge als Integer
     */
    private static int shortestTour() {
        return tdfWinners.stream().mapToInt(Winner::getLengthKm).min().getAsInt();
    }

    /**
     * Ermittelt die Länge der längsten Tour
     *
     * @return Tourlänge als Integer
     */
    private static int longestTour() {
        return tdfWinners.stream().mapToInt(Winner::getLengthKm).max().getAsInt();
    }

    /**
     * Ermittelt den Sieger mit der schnellsten Durchschnittszeit.
     *
     * @return Name des Siegers als String
     */
    private static String fastestWinner() {
        return tdfWinners.stream().filter(n -> n.getAveSpeed() == highestAvgSpeed()).findFirst().get().getName();
    }

    /**
     * Ermittelt die Durchschnittszeit des schnellsten Siegers.
     *
     * @return Durchschnittszeit als Double
     */
    private static Double highestAvgSpeed() {
        return tdfWinners.stream().mapToDouble(Winner::getAveSpeed).max().getAsDouble();
    }

    /**
     * Ermittelt alle Teams der Sieger.
     *
     * @return Siegerteams durch Beistriche getrennt
     */
    private static String allTeams() {
        final String[] output = {""};
        tdfWinners.forEach(n -> output[0] += n.getTeam() + ",");
        return output[0];
    }

    /**
     * Ermittelt eine Map der Nationalitäten und ihrer Sieger.
     *
     * @return Map der Nationalitäten + Sieger
     */
    private static Map<String, List<Winner>> winnersByNationality() {
        return tdfWinners.stream().collect(Collectors.groupingBy(Winner::getNationality));
    }

    /**
     * Ermittelt eine Map der Nationalitäten und wie oft sie gewonnen haben.
     *
     * @return Map der Nationalitäten + Siegeszahl
     */
    private static TreeMap<String, Long> nationalityWins() {
        return tdfWinners.stream().collect(Collectors.groupingBy(Winner::getNationality, TreeMap::new, Collectors.counting()));
    }

}
