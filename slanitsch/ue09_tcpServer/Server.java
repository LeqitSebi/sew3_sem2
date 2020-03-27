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
        System.out.println("Warte auf Clinets...");
        while(!ss.isClosed()) {
            try {
                s = ss.accept();
                ClientThread chat = new ClientThread(s);
                al.add(chat);
                chat.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void send(String os) {
        if(!os.equalsIgnoreCase("exit")) System.out.println(os);
        for(ClientThread ct : al) ct.writemsg(os);
    }

    public static void close() {
        //write("Server wird beendet!");
        if(al.size() != 0) send("exit");
        System.exit(0);
    }
}