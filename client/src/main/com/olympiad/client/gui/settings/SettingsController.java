package main.com.olympiad.client.gui.settings;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import main.com.olympiad.client.MainApp;
import main.com.olympiad.client.gui.SceneManager;

public class SettingsController {

    @FXML private Slider volumeSlider;
    @FXML private Label volumeValue;
    @FXML private CheckBox fullscreenCheck;
    @FXML private CheckBox musicCheck;

    @FXML
    public void initialize() {
        fullscreenCheck.setSelected(MainApp.appSettings.isFullscreen());
        musicCheck.setSelected(MainApp.appSettings.isMusic());
        volumeSlider.setValue(MainApp.appSettings.getVolume());
        volumeValue.setText(MainApp.appSettings.getVolume() + "%");

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                volumeValue.setText((int) newVal.doubleValue() + "%")
        );
    }

    @FXML
    private void onVolumeChanged() {
        int vol = (int) volumeSlider.getValue();
        MainApp.appSettings.setVolume(vol);
        // TODO: AudioManager.setVolume(vol);
        System.out.println("Lautstärke: " + vol);
    }

    @FXML
    private void onFullscreenToggled() {
        boolean fs = fullscreenCheck.isSelected();
        MainApp.instance.sceneManager.getStage().setFullScreen(fs);
        MainApp.appSettings.setFullscreen(fs);
    }

    @FXML
    private void onMusicToggled() {
        boolean music = musicCheck.isSelected();
        MainApp.appSettings.setMusic(music);
        // TODO: AudioManager.setMusicEnabled(music);
        System.out.println("Musik: " + music);
    }

    @FXML
    private void onBack() {
        MainApp.instance.sceneManager.switchToScene(SceneManager.SceneType.MAIN_MENU);
    }
}