package main.com.olympiad.client;

import javafx.application.Application;
import javafx.stage.Stage;
import main.com.olympiad.client.gui.SceneManager;

import static main.com.olympiad.client.gui.SceneManager.SceneType.MAIN_MENU;

public class MainApp extends Application {
    public static MainApp instance;
    public SceneManager sceneManager;
    public static AppSettings appSettings = new AppSettings();

    @Override
    public void start(Stage stage) {
        instance = this;
        sceneManager = new SceneManager(stage);
        sceneManager.switchToScene(MAIN_MENU);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
