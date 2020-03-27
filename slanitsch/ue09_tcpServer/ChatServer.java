package slanitsch.ue09_tcpServer;

import java.io.*;
import java.lang.module.ModuleDescriptor;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatServer {
    public static int PORT = 10023;

    public static void main(String[] args) {

        // Server auf Port 10023 horchen lassen
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Echo-Server bereit am Port " + PORT);

            while (true) {
                try (
                        // Auf ankommende Verbindung warten, accept() blockiert so lange
                        Socket verbindung = server.accept();

                        Writer w = new OutputStreamWriter(verbindung.getOutputStream(), StandardCharsets.ISO_8859_1);
                        BufferedWriter bw = new BufferedWriter(w);

                        Reader r = new InputStreamReader(verbindung.getInputStream());
                        BufferedReader br = new BufferedReader(r)
                ) {

                    System.out.println("Verbindung angenommen von " + verbindung.getRemoteSocketAddress());

                    while (true) {
                        // Eingabeaufforderung senden und Zeile einlesen
                        bw.write("Willkommen beim Chat-Server der 3CI!" + System.lineSeparator());
                        bw.write("Um die Verbindung zu beenden, gib quit ein. Um eine Liste der Chatteilnehmer zu erhalten, gib list ein" + System.lineSeparator());
                        bw.write("Welchen Spitznamen möchtest du haben?");
                        bw.write("Gib ihn bitte hier ein: ");
                        bw.flush();

                        String zeile = br.readLine();
                        if (zeile.equals("quit")) {
                            break;
                        }
                    }

                    System.out.println("Verbindung beendet mit " + verbindung.getRemoteSocketAddress());

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
