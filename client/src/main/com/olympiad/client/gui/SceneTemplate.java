package main.com.olympiad.client.gui;

import javafx.scene.Parent;

import java.io.IOException;

public interface SceneTemplate {
    /**
     * Gibt den Root-Node der Scene zurück.
     * Wird vom SceneManager aufgerufen.
     */
    Parent buildRoot() throws IOException;
}
