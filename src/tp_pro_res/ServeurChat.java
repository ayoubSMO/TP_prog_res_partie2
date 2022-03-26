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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServeurChat extends Thread {
    private int nombreClient;
    private List<ServeurChat.Conversation> clients = new ArrayList();

    public ServeurChat() {
    }

    public static void main(String[] args) {
        (new ServeurChat()).start();
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Démarage du serveur");

            while(true) {
                Socket socket = ss.accept();
                ++this.nombreClient;
                ServeurChat.Conversation conversation = new ServeurChat.Conversation(socket, this.nombreClient);
                this.clients.add(conversation);
                conversation.start();
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }

    public class Conversation extends Thread {
        protected Socket socket;
        protected int nombreClient;

        public Conversation(Socket s, int num) {
            this.socket = s;
            this.nombreClient = num;
        }

        public void broadcastMessage(String message, Socket socket_N, int numClient) {
            try {
                Iterator var5 = ServeurChat.this.clients.iterator();

                while(true) {
                    ServeurChat.Conversation client;
                    do {
                        do {
                            if (!var5.hasNext()) {
                                return;
                            }

                            client = (ServeurChat.Conversation)var5.next();
                        } while(client.socket == socket_N);
                    } while(client.nombreClient != numClient && numClient != -1);

                    PrintWriter printWriter = new PrintWriter(client.socket.getOutputStream(), true);
                    printWriter.println(message);
                }
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        }

        public void run() {
            try {
                InputStream is = this.socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = this.socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                String IP = this.socket.getRemoteSocketAddress().toString();
                System.out.println("conexion de client numero :" + this.nombreClient + "IP=" + IP);
                pw.println("Binvenue vous etes le client numero : " + this.nombreClient);

                while(true) {
                    while(true) {
                        String req = br.readLine();
                        if (req.contains("=>")) {
                            String[] RequestParams = req.split("=>");
                            if (RequestParams.length == 2) {
                            }

                            String message = RequestParams[1];
                            int numeroClient = Integer.parseInt(RequestParams[0]);
                            this.broadcastMessage(req, this.socket, numeroClient);
                        } else {
                            this.broadcastMessage(req, this.socket, -1);
                        }
                    }
                }
            } catch (IOException var11) {
                var11.printStackTrace();
            }
        }
    }
}
