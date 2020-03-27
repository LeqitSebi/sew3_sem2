package slanitsch.ue09_tcpServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread {

    Socket s;
    String n;
    Scanner in;
    PrintWriter out;

    public ClientThread(Socket so) {
        s = so;
    }

    public void writemsg(String s) {
        out.println(s);
        out.flush();
    }

    @Override
    public void run() {
        try {
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            while(true) {
                if(in.hasNext()) {
                    n = in.nextLine();
                    String[] nsplitt = n.split(" ");
                    if(nsplitt[1].equalsIgnoreCase("/exit")) {
                        s.close();
                        Server.al.remove(this);
                        Server.send(nsplitt[0] + " Server verlassen");

                    } else if(nsplitt[1].equalsIgnoreCase("/ping")) {
                        writemsg("pong");

                    } else if(nsplitt[1].equalsIgnoreCase("/whois")) {
                        writemsg("Im Chat sind :");

                    } else if(nsplitt[1].equalsIgnoreCase("/befehle")) {
                        writemsg("Verfügbare Befehle :");
                        writemsg("    - /ping   : Sendet einen Ping an den Server(Antwort: Pong)");
                        writemsg("    - /exit   : Verlässt den Server und schließt den Chat");
                        writemsg("    - /whois  : Zeigt an wer im Chat online ist");

                    } else Server.send(n);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

