//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServeurJeu extends Thread {
    int secret = -1;

    public ServeurJeu() {
    }

    public static void main(String[] args) {
        (new ServeurJeu()).start();
    }

    public void run() {
        int num = 0;

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            this.secret = (new Random()).nextInt(1000);
            System.out.println("Le server est demarré");
            System.out.println("Le numero secret: " + this.secret);

            while(true) {
                Socket var10003 = serverSocket.accept();
                ++num;
                (new ServeurJeu.Conversation(var10003, num)).start();
            }
        } catch (Exception var3) {
        }
    }

    class Conversation extends Thread {
        Socket socket;
        int numero;

        public Conversation(Socket socket, int numero) {
            this.socket = socket;
            this.numero = numero;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                OutputStream os = this.socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                System.out.println("Client " + this.numero + " connecté: " + this.socket.getRemoteSocketAddress());
                pw.println("Bienvenue client num: " + this.numero);
                boolean fin = false;
                String gagnant = "";

                while(true) {
                    String line = br.readLine();
                    boolean var7 = true;

                    int nb;
                    try {
                        nb = Integer.parseInt(line);
                    } catch (NumberFormatException var9) {
                        pw.println("Le message envoyé n'est pas un nembre");
                        continue;
                    }

                    System.out.println("une tentative du client " + this.numero + " : " + nb);
                    if (!fin) {
                        if (nb < ServeurJeu.this.secret) {
                            pw.println("Le nombre " + nb + " est inferieur au nombre secret");
                        } else if (nb > ServeurJeu.this.secret) {
                            pw.println("Le nombre " + nb + " est superieur au nombre secret");
                        } else {
                            pw.println("Brave au client " + this.numero + ", le nombre est: " + ServeurJeu.this.secret);
                            System.out.println("Brave au client " + this.numero + ", le nombre est: " + ServeurJeu.this.secret);
                            gagnant = this.socket.getRemoteSocketAddress().toString();
                            fin = true;
                        }
                    } else {
                        pw.println("Le gagnan est :" + gagnant);
                    }
                }
            } catch (IOException var10) {
                var10.printStackTrace();
            }
        }
    }
}
