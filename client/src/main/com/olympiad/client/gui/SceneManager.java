package main.com.olympiad.client.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.com.olympiad.client.gui.connect.Connect;
import main.com.olympiad.client.gui.credits.Credits;
import main.com.olympiad.client.gui.mainmenu.MainMenu;
import main.com.olympiad.client.gui.settings.Settings;
import main.com.olympiad.client.gui.tictactoe.TicTacToe;

import java.io.IOException;

public class SceneManager {
    private Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public enum SceneType {
        MAIN_MENU, TIC_TAC_TOE, SETTINGS, CREDITS, CONNECT
    }

    public void switchToScene(SceneType type) {
        try {
            Parent root;
            switch (type) {
                case MAIN_MENU:
                    root = new MainMenu().buildRoot();
                    break;
                case CREDITS:
                    root = new Credits().buildRoot();
                    break;
                case SETTINGS:
                    root = new Settings().buildRoot();
                    break;
                case CONNECT:
                    root = new Connect().buildRoot();
                    break;
                case TIC_TAC_TOE:
                    root = new TicTacToe().buildRoot();
                    break;
                default:
                    System.out.println("Scene nicht implementiert: " + type);
                    return;
            }
            if (stage.getScene() == null) {
                stage.setScene(new Scene(root));
            } else {
                stage.getScene().setRoot(root);
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}