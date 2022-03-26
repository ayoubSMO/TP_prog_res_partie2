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
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientChat extends Application {
    PrintWriter pw;

    public ClientChat() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage Firstpage) throws Exception {
        Firstpage.setTitle("Client Chat space");
        BorderPane borderPane = new BorderPane();
        Label labelHost = new Label("Host:");
        TextField textfieldHost = new TextField("Localhost");
        Label labelPort = new Label("Port:");
        TextField textfieldPort = new TextField("1234");
        Button btnConnecter = new Button("Connecter");
        HBox hbox = new HBox();
        hbox.setSpacing(10.0D);
        hbox.setPadding(new Insets(10.0D));
        hbox.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.YELLOW, (CornerRadii)null, (Insets)null)}));
        hbox.getChildren().addAll(new Node[]{labelHost, textfieldHost, labelPort, textfieldPort, btnConnecter});
        borderPane.setTop(hbox);
        ObservableList<String> listModel = FXCollections.observableArrayList();
        ListView<String> listView = new ListView(listModel);
        VBox vBox = new VBox();
        vBox.setSpacing(10.0D);
        vBox.setPadding(new Insets(10.0D));
        vBox.getChildren().add(listView);
        borderPane.setCenter(vBox);
        Label labelMessage = new Label("Message:");
        TextField textfieldMessage = new TextField();
        textfieldMessage.setPrefWidth(250.0D);
        Button btnEnvoyer = new Button("Envoyer");
        HBox hbox2 = new HBox();
        hbox2.setSpacing(10.0D);
        hbox2.setPadding(new Insets(10.0D));
        hbox2.getChildren().addAll(new Node[]{labelMessage, textfieldMessage, btnEnvoyer});
        borderPane.setBottom(hbox2);
        Scene Myscene = new Scene(borderPane, 500.0D, 400.0D);
        Firstpage.setScene(Myscene);
        Firstpage.show();
        btnConnecter.setOnAction((evt) -> {
            String host = textfieldHost.getText();
            int port = Integer.parseInt(textfieldPort.getText());

            try {
                Socket socket = new Socket(host, port);
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = socket.getOutputStream();
                this.pw = new PrintWriter(os, true);
                (new Thread(() -> {
                    while(true) {
                        try {
                            String reponse = br.readLine();
                            Platform.runLater(() -> {
                                listModel.add(reponse);
                            });
                        } catch (IOException var3) {
                            var3.printStackTrace();
                        }
                    }
                })).start();
            } catch (IOException var12) {
                var12.printStackTrace();
            }

        });
        btnEnvoyer.setOnAction((evt) -> {
            String message = textfieldMessage.getText();
            this.pw.println(message);
        });
    }
}
