package slanitsch;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.counting;

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
        return tdfWinners.stream().map(winner -> winner.getYear() + " - " + winner.getName()).collect(Collectors.toList());
    }

    /**
     * Ermittelt die Länge der Namen jedes TDF Siegers.
     *
     * @return Liste der Namenslängen
     */
    private static List<Integer> nameLengths() {
        return tdfWinners.stream().map(winner -> winner.getName().length()).collect(Collectors.toList());
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
        return tdfWinners.stream().map(Winner::getTeam).collect(Collectors.toList()).toString().replaceAll("[\\[\\]]", "");
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

    /**
     * Gibt einen fibonacci Stream zurück.
     *
     * @param numberOfValues values
     * @return Stream
     */
    public static Stream<BigInteger> fibonacci(int numberOfValues) {
        return Stream.iterate(new BigInteger[]{BigInteger.ONE, BigInteger.ONE}, i -> new BigInteger[]{i[1], i[0].add(i[1])})
                .limit(numberOfValues)
                .map(i -> i[0]);
    }

    /**
     * Gibt die N größten Files in einem Ordner zurück.
     *
     * @param path                 path String
     * @param numberOFLargestFiles Anzahl der auszugebenden Files
     * @return List
     * @throws IOException wird geworfen wenn zB kein File gefunden wird
     */
    public static Stream<Path> greatestFiles(Path path, int numberOFLargestFiles) {
        try {
            return Files.walk(path)
                    .sorted((f1, f2) -> Long.compare(f2.toFile().length(), f1.toFile().length()))
                    .filter(file -> file.toFile().isFile())
                    .limit(numberOFLargestFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }

    /**
     * Gibt die absolute Häufigkeit aller chars in einer String Liste als Map zurück.
     *
     * @param stringList List<String>
     * @return Map (Key ist char; Value ist Häufigkeit (Long))
     */
    public static Map<Character, Long> getCharStatistic(List<String> stringList) {
        return stringList.stream()
                .collect(Collectors.joining())
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, TreeMap::new, counting()));
    }

    public static final Pattern ISMORSE = Pattern.compile("([·−]+/+)+\\s*");

    /**
     * Konvertiert einen String zu Morse und wieder zurück; berücksichtigt keine Satzzeichen oder Groß- Kleinschreibung
     *
     * @param input String (entweder Morse Code oder normaler Text
     * @return Konvertierter String
     */
    public static String toMorseCode(String input) {
        final Map<String, String> translator = new TreeMap<>();
        String[] keys = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
                "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
                "Ä", "Ö", "Ü", "ß", "·−", "−···", "−·−·", "−··", "·", "··−·", "−−·", "····", "··", "·−−−", "−·−",
                "·−··", "−−", "−·", "−−−", "·−−·", "−−·−", "·−·", "···", "−", "··−", "···−", "·−−", "−··−", "−·−−",
                "−−··", "·−−−−", "··−−−", "···−−", "····−", "·····", "−····", "−−···", "−−−··", "−−−−·", "−−−−−",
                "·−·−", "−−−·", "··−−", "···−−··", " ", "/", "!", ".", "?"};
        String[] values = new String[]{"·−/", "−···/", "−·−·/", "−··/", "·/", "··−·/", "−−·/", "····/", "··/", "·−−−/",
                "−·−/", "·−··/", "−−/", "−·/", "−−−/", "·−−·/", "−−·−/", "·−·/", "···/", "−/", "··−/", "···−/", "·−−/",
                "−··−/", "−·−−/", "−−··/", "·−−−−/", "··−−−/", "···−−/", "····−/", "·····/", "−····/", "−−···/",
                "−−−··/", "−−−−·/", "−−−−−/", "·−·−/", "−−−·/", "··−−/", "···−−··/", "A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1",
                "2", "3", "4", "5", "6", "7", "8", "9", "0", "Ä", "Ö", "Ü", "ß", "/", " ", "//", "//", "//"};

        input = input.replaceAll("[,]", "").toUpperCase();
        for (int i = 0; i < keys.length; i++) translator.put(keys[i], values[i]);

        String[] inputArray;
        if (ISMORSE.matcher(input).matches()) inputArray = input.split("/");
        else inputArray = input.split("");

        return Arrays
                .stream(inputArray)
                .map(translator::get)
                .map(e -> {
                    if (e == null) return " ";
                    return e;
                })
                .collect(Collectors.joining());
    }


}