package slanitsch.ue09_tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author slanitsch
 * klasse: 3CI
 * school_year: 2019/20
 */
public class Server {

    /**
     * Arrayliste in der alle Clients gelagert werden.
     */
    static ArrayList<ClientThread> al = new ArrayList<ClientThread>();

    /**
     * Main Methode, die den Server initialisiert.
     * @param args Variablen für die main methode
     * @throws Exception mögliche Exceptions könnten geworfen werden
     */
    public static void main(String[] args) throws Exception {
        new Server();
    }

    /**
     * Server Klasse, die für die Handlungen des Servers zuständig ist.
     * @throws Exception
     */
    public Server() throws Exception {
        ServerSocket ss = null;
        Socket s = null;
        try {
            ss = new ServerSocket(10023);
        } catch (IOException e) {
            throw new Exception("Server konnte nicht gestartet werden");
        }
        System.out.println("Server gestartet!");
        System.out.println("Warte auf Clients...");
        while(!ss.isClosed()) {
            try {
                s = ss.accept();
                ClientThread chat = new ClientThread(s);
                System.out.println(chat.getId());
                chat.start();
            } catch (IOException e) {
                throw new Exception("Client konnte keine Verbindung herstellen");
            }
        }
    }

    /**
     * Methode um eine Privatnachricht an einen bestimmten User zu senden.
     * @param os Nachricht die gesendet werden soll
     * @param sender Client der die Nachricht gesendet hat
     * @return gibt zurück, ob die Nachricht erfolgreich versendet wurde
     */
    public static boolean sendPrivate(String os, ClientThread sender){
        String[] senderAndMsg = os.split(":");
        ArrayList<ClientThread> toTest = al;
        for (ClientThread ct : toTest){
            if (ct.spitzname.equals(senderAndMsg[0])){
                ct.writemsg("\n" + sender.spitzname + " flüsterte: " + senderAndMsg[1]);
                ct.writemsg("\n" + ct.spitzname + "> ");
                return true;
            }
        }
        return false;
    }

    /**
     * Methode um eine Nachricht an alle anderen Clients zu schicken.
     * @param os Nachricht die gesendet werden soll
     * @param sender Client der die Nachricht gesendet hat
     */
    public static void send(String os, ClientThread sender) {
        if(!os.equalsIgnoreCase("exit")) System.out.println(os);
        ArrayList<ClientThread> toTest = al;
        for(ClientThread ct : toTest){
            if (!ct.equals(sender)){
                ct.writemsg("\n");
                ct.writemsg(os);
            }
            ct.writemsg(ct.spitzname + "> ");
        }

    }

    /**
     * Methode, die den Server beendet.
     */
    public static void close() {
        System.out.println("Server wird beendet!");
        if(al.size() != 0) send("exit", null);
        System.exit(0);
    }
}