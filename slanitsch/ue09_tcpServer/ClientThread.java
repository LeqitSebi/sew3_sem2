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
    String spitzname;

    public ClientThread(Socket so) {
        s = so;
    }

    public void writemsg(String s) {
        out.print(s);
        out.flush();
    }

    @Override
    public void run() {
        try {
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            writemsg("Willkommen im Text-Chat der 3CI\n\n");
            writemsg("Um die Verbindung zu beenden gib /exit ein\n");
            writemsg("Um dir alle verfügbaren Befehle anzeigen zu lassen, gib /befehle ein\n\n");
            writemsg("Welchen Spitznamen möchtest du haben: ");
            if (in.hasNext()){
                n = in.nextLine();
                writemsg("Hallo " + n + "\n");
                spitzname = n;
            }

            while(true) {
                if(in.hasNext()) {
                    n = in.nextLine();
                    if(n.equalsIgnoreCase("/exit")) {
                        s.close();
                        Server.al.remove(this);
                        Server.send(spitzname + " hat den Server verlassen");

                    } else if(n.equalsIgnoreCase("/ping")) {
                        writemsg("pong\n");

                    } else if(n.equalsIgnoreCase("/list")) {
                        writemsg("Im Chat sind :");
                        Server.al.forEach(n -> writemsg(n.spitzname + ", "));
                        writemsg("\n");

                    } else if(n.equalsIgnoreCase("/befehle")) {
                        writemsg("Verfügbare Befehle :\n");
                        writemsg("    - /list  : Sendet einen Ping an den Server(Antwort: Pong)\n");
                        writemsg("    - /exit   : Verlässt den Server und schließt den Chat\n");
                        writemsg("    - /ping  : Zeigt an wer im Chat online ist\n");

                    } else Server.send(n + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

