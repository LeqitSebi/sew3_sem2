package slanitsch;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Auswertung {
    /**
     * Regex für IP Adressen
     */
    public static final Pattern ISIP = Pattern.compile("(\\d+\\.){3}\\d");
    /**
     * Regex für Webseiten
     */
    public static final Pattern ISWEBPAGE = Pattern.compile("http[s]?://\\S*");
    /**
     * Regex für heruntergeladene Bytes in einem Logfile
     */
    public static final Pattern ISBYTE = Pattern.compile("(\\d+) GET");

    /**
     * Gibt Statistiken aus.
     *
     * @param file Log-File
     */
    public static void printLogStatistic(Path file) {
        System.out.println("Heufigste IP: " + getMostCommonIp(file)
                + "\nHeufigste Webseite: " + getMostCommenPage(file)
                + "\nHeruntergeladene Bytes: " + countDownloadBytes(file));
    }

    /**
     * Gibt die heufigste version von regex zurück.
     *
     * @param file  Log-File
     * @param regex Regex nach welcher gesucht wird
     * @return Heufigste IP Adresse
     */
    public static String getMostCommon(Path file, Pattern regex) {
        Map<String, Long> ips = new TreeMap<>();
        try (BufferedReader in = Files.newBufferedReader(file)) {
            String line;
            while ((line = in.readLine()) != null) {
                Matcher m = regex.matcher(line);
                while (m.find()) {
                    ips.putIfAbsent(m.group(), (long) (0));
                    ips.put(m.group(), ips.get(m.group()) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ips.entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .findFirst()
                .get()
                .getKey();
    }

    /**
     * Gibt häufigste Webseite zurück.
     *
     * @param file Log-File
     * @return heufigste Webseite
     */
    public static String getMostCommenPage(Path file) {
        return getMostCommon(file, ISWEBPAGE);
    }

    /**
     * Zählt die heruntergeladenen Bytes.
     *
     * @param file Log-File
     * @return Anzahl als Long
     */
    public static long countDownloadBytes(Path file) {
        long numberOfBytes = 0;
        try (BufferedReader in = Files.newBufferedReader(file)) {
            String line;
            while ((line = in.readLine()) != null) {
                Matcher m = ISBYTE.matcher(line);
                while (m.find()) {
                    numberOfBytes += Long.parseLong(m.group(1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberOfBytes;
    }

    /**
     * Gibt die häufigste IP Adresse zurück.
     *
     * @param file Log-File
     * @return häufigste IP Adresse
     */
    public static String getMostCommonIp(Path file) {
        return getMostCommon(file, ISIP);
    }

    /**
     * Hauptmethode zum Aufrufen anderer
     * @param args
     */
    public static void main(String[] args) {
        printLogStatistic(Paths.get("resources/access.log"));
    }
}
