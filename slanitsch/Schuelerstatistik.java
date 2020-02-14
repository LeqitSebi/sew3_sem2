package slanitsch;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Schuelerstatistik {
    /**
     * Regex für die Schülerliste
     */
    public static final Pattern SCHUELERLISTE = Pattern.compile("(.*?);(.*?);(.*?);(.*?);(.*?)");

    /**
     * Gibt verschiedene Statistiken an Hand einer Liste aus.
     *
     * @param file Path
     */
    public static void printSchuelerstatistic(Path file) {
        System.out.println(
                "Students per Department: " + getStudentsPerDepartment(file) +
                        "\nNumber of Classes: " + getNumberOfClasses(file) +
                        "\nAverage Number of Students: " + getAverageNumberOfStudentPerClass(file) +
                        "\nNumber of Birthsdays per Month: " + getBirthsPerMonth(file) +
                        "\nClasses where 2 or more Students have the same Birthdate: "
                        + getNumberOfClassesWithSameNameStudents(file)
        );
    }

    /**
     * Gibt das File als String Liste zurück (einzelne Linien).
     *
     * @param file Path
     * @return Eingelesenes File als Stringliste
     */
    public static List<String> lines(Path file) {
        List<String> lines = new LinkedList<>();
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines.remove(0);
        return lines;
    }

    /**
     * Ermittelt die ANzahl der SChüler pro Abteilung.
     *
     * @param file Path
     * @return Map mit K=Abteilung V=Anzahl der Schüler
     */
    public static Map<String, Long> getStudentsPerDepartment(Path file) {
        Map<String, Long> departments;
        List<String> lines = lines(file);
        departments = lines.stream().collect(Collectors.groupingBy(e -> {
            Matcher m = SCHUELERLISTE.matcher(e);
            m.matches();
            return m.group(5);
        }, Collectors.counting()));
        return departments;
    }

    /**
     * Ermittelt die Anzahl an Klassen.
     *
     * @param file Path
     * @return Anzahl als int
     */
    public static int getNumberOfClasses(Path file) {
        Set<String> classes = new HashSet<>();

        lines(file).stream().forEach(e -> {
            Matcher m = SCHUELERLISTE.matcher(e);
            m.matches();
            classes.add(m.group(3));
        });
        return classes.size();
    }

    /**
     * Ermittelt die durchschnittliche Anzahl an Schülern pro Klasse.
     *
     * @param file Path
     * @return Float Durchschnitt
     */
    public static float getAverageNumberOfStudentPerClass(Path file) {
        return lines(file).size() / getNumberOfClasses(file);
    }

    /**
     * Gibt die Anzahl an Schülern die in jedem Monat geboren wurden.
     *
     * @param file Path
     * @return Map K=Monat V=Anzahl der Schüler
     */
    public static Map<String, Long> getBirthsPerMonth(Path file) {
        Map<String, Long> departments;
        List<String> lines = lines(file);
        departments = lines.stream().collect(Collectors.groupingBy(e -> {
            Matcher m = SCHUELERLISTE.matcher(e);
            m.matches();
            return m.group(4).substring(3, 5);
        }, Collectors.counting()));
        return departments;
    }

    /**
     * Gibt die Anzahl an Klassen zurück in welchen mindestens 2 Schüler den gleichen Geburtstag haben.
     *
     * @param file Path
     * @return Anzahl
     */
    public static AtomicInteger getNumberOfClassesWithSameNameStudents(Path file) {
        AtomicInteger numberOfClassesWithSameNameStudents = new AtomicInteger();
        Map<String, LinkedList<String>> classes = new HashMap<>();
        lines(file)
                .stream()
                .forEach(e -> {
                    Matcher m = SCHUELERLISTE.matcher(e);
                    m.matches();
                    classes.putIfAbsent(m.group(3), new LinkedList<>());
                    classes.get(m.group(3)).add(m.group(4));
                    return;
                });
        classes.entrySet().stream().forEach(e -> {
            if (e.getValue().size() != e.getValue().stream().distinct().collect(Collectors.toList()).size()) {
                numberOfClassesWithSameNameStudents.addAndGet(1);
            }
        });
        return numberOfClassesWithSameNameStudents;
    }

    /**
     * Hauptmethode zum Aufrufen anderer
     *
     * @param args
     */
    public static void main(String[] args) {
        printSchuelerstatistic(Paths.get("resources/Schuelerliste_UTF-8.csv"));
    }
}
