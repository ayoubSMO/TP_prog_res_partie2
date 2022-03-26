//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMT extends Thread {
    private int nombreClient;

    public ServeurMT() {
    }

    public static void main(String[] args) {
        (new ServeurMT()).start();
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Démarage de serveur ...");

            while(true) {
                Socket socket = ss.accept();
                ++this.nombreClient;
                (new ServeurMT.Conversation(socket, this.nombreClient)).start();
            }
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    class Conversation extends Thread {
        private Socket socket;
        private int numeroClient;

        public Conversation(Socket s, int num) {
            this.socket = s;
            this.numeroClient = num;
        }

        public void run() {
            try {
                InputStream is = this.socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = this.socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                String IP = this.socket.getRemoteSocketAddress().toString();
                System.out.println("Connexion du client numéro " + this.numeroClient + " IP = " + IP);
                pw.println("Bienvenu vous etes le client " + this.numeroClient);

                while(true) {
                    String req = br.readLine();
                    System.out.println("Le client " + IP + "à envoyé une requete " + req);
                    pw.println(req.length());
                }
            } catch (IOException var8) {
                var8.printStackTrace();
            }
        }
    }
}
