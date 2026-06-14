package main.com.olympiad.client.gui.mainmenu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import main.com.olympiad.client.MainApp;
import main.com.olympiad.client.gui.SceneManager;

public class MainMenuController {

    @FXML
    private void onPlay() {
        getSceneManager().switchToScene(SceneManager.SceneType.CONNECT);
    }

    @FXML
    private void onSettings() {
        getSceneManager().switchToScene(SceneManager.SceneType.SETTINGS);
    }

    @FXML
    private void onCredits() {
        getSceneManager().switchToScene(SceneManager.SceneType.CREDITS);
    }

    @FXML
    private void onQuit() {
        Platform.exit();
    }

    private SceneManager getSceneManager() {
        return MainApp.instance.sceneManager;
    }
}