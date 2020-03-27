package slanitsch.ue09_tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<ClientThread> al = new ArrayList<ClientThread>();

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        ServerSocket ss = null;
        Socket s = null;
        try {
            ss = new ServerSocket(10023);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server gestartet!");
        System.out.println("Warte auf Clients...");
        while(!ss.isClosed()) {
            try {
                s = ss.accept();
                ClientThread chat = new ClientThread(s);
                System.out.println(chat.getId());
                al.add(chat);
                chat.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean sendPrivate(String os, ClientThread sender){
        String[] senderAndMsg = os.split(":");
        for (ClientThread ct : al){
            if (ct.spitzname.equals(senderAndMsg[0])){
                ct.writemsg("\n" + sender.spitzname + " flüsterte: " + senderAndMsg[1]);
                ct.writemsg("\n" + ct.spitzname + "> ");
                return true;
            }
        }
        return false;
    }

    public static void send(String os, ClientThread sender) {
        if(!os.equalsIgnoreCase("exit")) System.out.println(os);
        for(ClientThread ct : al){
            if (!ct.equals(sender)){
                ct.writemsg("\n");
                ct.writemsg(os);
            }
            ct.writemsg(ct.spitzname + "> ");
        }

    }

    public static void close() {
        System.out.println("Server wird beendet!");
        if(al.size() != 0) send("exit", null);
        System.exit(0);
    }
}