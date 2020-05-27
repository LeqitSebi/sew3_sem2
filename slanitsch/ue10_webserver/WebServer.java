package slanitsch.ue10_webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class WebServer {

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(1080);

        while (true) {
            try {
                Socket s = serverSocket.accept();
                new ClientThread(s);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

