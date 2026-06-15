package main.com.olympiad.client;

import javafx.application.Application;
import javafx.stage.Stage;
import main.com.olympiad.client.gui.SceneManager;
import main.com.olympiad.client.network.ClientHandler;

import static main.com.olympiad.client.gui.SceneManager.SceneType.MAIN_MENU;

public class MainApp extends Application {
    public static MainApp instance;
    public SceneManager sceneManager;
    public static AppSettings appSettings = new AppSettings();
    public ClientHandler clientHandler = null;

    @Override
    public void start(Stage stage) {
        instance = this;
        sceneManager = new SceneManager(stage);
        stage.setTitle("Olympiad Arena - Client");
        sceneManager.switchToScene(MAIN_MENU);
    }

    @Override
    public void stop() throws Exception {
        if (clientHandler != null) {
            clientHandler.disconnect();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
