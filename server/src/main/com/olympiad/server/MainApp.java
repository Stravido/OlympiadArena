package main.com.olympiad.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.com.olympiad.server.network.ServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;

public class MainApp extends Application {
    public static final int PORT = 12345;
    private ServerHandler serverHandler;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/main/com/olympiad/server/gui/Dashboard.fxml")));
        stage.setTitle("Olympiad Arena - Server Dashboard");
        stage.setScene(new Scene(root, 900, 580));
        stage.setMinWidth(700);
        stage.setMinHeight(450);
        stage.show();

        System.out.println("Server startet auf Port " + PORT + "...");
        new Thread(() -> {
            try {
                serverHandler = new ServerHandler(new ServerSocket(PORT));
                System.out.println("Server gestartet auf Port " + PORT);
            } catch (IOException e) {
                System.out.println("FEHLER: " + e.getMessage());
            }
        }).start();
    }
    @Override
    public void stop() {
        if (serverHandler != null) serverHandler.disconnect();
    }
    public static void main(String[] args) {
        launch(args);
    }
}