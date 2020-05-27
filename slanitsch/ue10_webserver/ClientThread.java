package slanitsch.ue10_webserver;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientThread extends Thread {
    private Socket socket;

    public ClientThread(Socket s) {
        socket = s;
        start();
    }

    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream out = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));


            String s = in.readLine(); //Erste Zeile einlesen - GET /Pfad/filename.html ...
            System.out.println(s);  // Request Loggen

            //Versuchen das File anzuzeigen, falls es nicht findet gibt es einen 404 Error
            String pfad = "";
            StringTokenizer st = new StringTokenizer(s);

            try {

                //Filename nach dem GET filtern
                if (st.hasMoreElements() && st.nextToken().equalsIgnoreCase("GET") && st.hasMoreElements()) {
                    pfad = st.nextToken();
                    //Könnte man auch mit Regex machen, fand Token angenehmer da er direkt das nächstgeschriebene/Token nimmt
                } else
                    throw new FileNotFoundException();

                // Append trailing "/" with "index.html"
                if (pfad.endsWith("/")) {
                    pfad += "index.html";
                }
                // Das führende / vorm Pfad "wegschmeißen"
                while (pfad.indexOf("/") == 0)
                    pfad = pfad.substring(1);

                // / in \ umwandeln wegen PC
                pfad = pfad.replace('/', '\\');

                // Hier noch prüfen ob illegale Characters vorkommen um Zugriff auf Superdirectorys zu vermeiden
                //Falls ja keine "Kein-Zugriff" Exception sondern einfach FileNotFound
                if (pfad.indexOf("..") >= 0 || pfad.indexOf(':') >= 0 || pfad.indexOf('|') >= 0) {
                    throw new FileNotFoundException();
                }

                // Falls ein Directory angefragt wird und ein / fehlt am Ende wird dem Client
                // ein HTTP Request geschickt dies noch hinzuzufügen mit der Nachricht dass er es vergessen hat
                if (new File(pfad).isDirectory()) {
                    pfad = pfad.replace('\\', '/');
                    out.print("HTTP/1.0 301 Moved Permanently\r\n" +
                            "Location: /" + pfad + "/\r\n\r\n");
                    out.close();
                    return;
                }

                // Versucht das File zu öffnen, FALLS es nicht existiert -> File Not Found Exception
                InputStream file = new FileInputStream(pfad);

                // Findet den Document Type heraus und printet anhand dessen den HTTP header
                String docType = "text/plain";

                if (pfad.endsWith(".html") || pfad.endsWith(".htm"))
                    docType = "text/html";

                else if (pfad.endsWith(".jpg") || pfad.endsWith(".jpeg"))
                    docType = "image/jpeg";

                else if (pfad.endsWith(".gif"))
                    docType = "image/gif";

                else if (pfad.endsWith(".class"))
                    docType = "application/octet-stream";

                out.print("HTTP/1.0 200 OK\r\n" +
                        "Content-type: " + docType + "\r\n\r\n");

                // Sendet den Inhalt des Files zum Client und schließt danach die Connection
                byte[] a = new byte[4096];
                int fileContent;
                while ((fileContent = file.read(a)) > 0)
                    out.write(a, 0, fileContent);
                out.close();

            } catch (FileNotFoundException e) {
                out.println("HTTP/1.0 404 Not Found\r\n" +
                        "Content-type: text/html\r\n\r\n" +
                        "<html><head></head><body>" + pfad + " not found</body></html>\n");
                out.close();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}