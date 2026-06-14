package main.com.olympiad.client.gui.credits;


import javafx.fxml.FXML;
import main.com.olympiad.client.MainApp;
import main.com.olympiad.client.gui.SceneManager;

public class CreditsController {

    @FXML
    private void onBack() {
        MainApp.instance.sceneManager.switchToScene(SceneManager.SceneType.MAIN_MENU);
    }
}