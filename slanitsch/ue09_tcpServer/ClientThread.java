package slanitsch.ue09_tcpServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ClientThread extends Thread {

    Socket s;
    String n;
    Scanner in;
    PrintWriter out;
    String spitzname;
    int sentMSG;

    public String getSpitzname() {
        return spitzname;
    }

    public void setSpitzname(String spitzname) {
        this.spitzname = spitzname;
    }

    public int getSentMSG() {
        return sentMSG;
    }

    public void setSentMSG(int sentMSG) {
        this.sentMSG = sentMSG;
    }

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
            spitzname = "diesennamendarfniemandnehmenbittebittebitte";
            sentMSG = 0;
            in = new Scanner(s.getInputStream());
            out = new PrintWriter(s.getOutputStream());
            boolean nameset = false;
            while(!nameset) {
                writemsg("Willkommen im Text-Chat der 3CI\n\n");
                writemsg("Um die Verbindung zu beenden gib /exit ein\n");
                writemsg("Um dir alle verfügbaren Befehle anzeigen zu lassen, gib /befehle ein\n\n");
                writemsg("Welchen Spitznamen möchtest du haben: ");
                if (in.hasNext()) {
                    n = in.nextLine();
                    if (Server.al.stream().noneMatch(x -> x.spitzname.equals(n))){
                        writemsg("Hallo " + n + "\n");
                        spitzname = n;
                        writemsg(spitzname + "> ");
                        nameset = true;
                    }else{
                        writemsg("Name schon vorhanden!\n Wähle einen anderen: ");
                    }
                }
            }
            while(true) {
                if(in.hasNext()) {
                    n = in.nextLine();
                    if(n.equalsIgnoreCase("/exit")) {
                        s.close();
                        Server.al.remove(this);
                        Server.send(spitzname + " hat den Server verlassen\n", this);
                        writemsg(spitzname + "> ");
                    } else if(n.equalsIgnoreCase("/ping")) {
                        writemsg("pong\n");
                        writemsg(spitzname + "> ");
                    } else if(n.equalsIgnoreCase("/stats")){
                        List<String> names = Server.al.stream().map(ClientThread::getSpitzname).collect(Collectors.toList());
                        List<Integer> sentMSGall = Server.al.stream().map(ClientThread::getSentMSG).collect(Collectors.toList());
                        StringBuilder statMassage = new StringBuilder();
                        for (int i = 0; i < names.size(); i++) {
                            statMassage.append(names.get(i)).append(" hat ").append(sentMSGall.get(i)).append(" Nachrichten gesendet.\n");
                        }
                        writemsg(statMassage.toString());
                        writemsg(spitzname + "> ");
                    } else if(n.equalsIgnoreCase("/list")) {
                        writemsg("Im Chat sind :");
                        Server.al.forEach(n -> writemsg(n.spitzname + ", "));
                        writemsg("\n");
                        writemsg(spitzname + "> ");
                    } else if(n.equalsIgnoreCase("/befehle")) {
                        writemsg("Verfügbare Befehle :\n");
                        writemsg("    - /list  : Sendet einen Ping an den Server(Antwort: Pong)\n");
                        writemsg("    - /exit   : Verlässt den Server und schließt den Chat\n");
                        writemsg("    - /ping  : Zeigt an wer im Chat online ist\n");
                        writemsg("    - /stats  : Zeigt an wer wieviele Nachrichten gesendet hat\n");
                        writemsg(spitzname + "> ");
                    } else{
                        if (!Server.sendPrivate(n, this)){
                            Server.send(spitzname + " sagte: " + n + "\n", this);
                            sentMSG++;
                        }else{
                            writemsg(spitzname + "> ");
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

