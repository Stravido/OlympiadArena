package main.com.olympiad.client.gui.connect;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.com.olympiad.client.MainApp;
import main.com.olympiad.client.gui.SceneManager;
import main.com.olympiad.client.network.ClientHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectController {

    @FXML private TextField ipField;
    @FXML private TextField portField;
    @FXML private Label statusLabel;

    @FXML
    private void onConnect() {
        String ip = ipField.getText().trim();
        int port;
        try {
            port = Integer.parseInt(portField.getText().trim());
        } catch (NumberFormatException e) {
            setStatus("Ungültiger Port.", "status-error");
            return;
        }

        setStatus("Verbinde mit " + ip + ":" + port + " ...", "status-info");

        new Thread(() -> {
            try {
                Socket socket = new Socket(ip, port);
                new ClientHandler(socket);
                Platform.runLater(() -> setStatus("Verbunden!", "status-success"));
            } catch (IOException e) {
                Platform.runLater(() -> setStatus("Fehler: " + e.getMessage(), "status-error"));
            }
        }).start();
    }

    @FXML
    private void onScan() {
        setStatus("Suche im lokalen Netzwerk...", "status-info");

        new Thread(() -> {
            int port;
            try {
                port = Integer.parseInt(portField.getText().trim());
            } catch (NumberFormatException e) {
                Platform.runLater(() -> setStatus("Ungültiger Port.", "status-error"));
                return;
            }

            try {
                String localIp = InetAddress.getLocalHost().getHostAddress();
                String subnet = localIp.substring(0, localIp.lastIndexOf('.') + 1);

                for (int i = 1; i <= 254; i++) {
                    String host = subnet + i;
                    try (Socket s = new Socket()) {
                        s.connect(new java.net.InetSocketAddress(host, port), 50);
                        final String found = host;
                        Platform.runLater(() -> {
                            ipField.setText(found);
                            setStatus("Server gefunden: " + found, "status-success");
                        });
                        return;
                    } catch (IOException ignored) {}
                }

                Platform.runLater(() -> setStatus("Kein Server gefunden.", "status-error"));
            } catch (IOException e) {
                Platform.runLater(() -> setStatus("Netzwerkfehler: " + e.getMessage(), "status-error"));
            }
        }).start();
    }

    @FXML
    private void onBack() {
        MainApp.instance.sceneManager.switchToScene(SceneManager.SceneType.MAIN_MENU);
    }

    private void setStatus(String text, String styleClass) {
        statusLabel.setText(text);
        statusLabel.getStyleClass().removeAll("status-info", "status-success", "status-error");
        statusLabel.getStyleClass().add(styleClass);
    }
}