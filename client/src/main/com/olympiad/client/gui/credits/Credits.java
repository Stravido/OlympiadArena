package main.com.olympiad.client.gui.credits;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import main.com.olympiad.client.gui.SceneTemplate;

import java.io.IOException;
import java.util.Objects;

public class Credits implements SceneTemplate {
    @Override
    public Parent buildRoot() throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Credits.fxml")));
    }
}
