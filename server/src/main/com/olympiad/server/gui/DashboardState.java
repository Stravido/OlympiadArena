package main.com.olympiad.server.gui;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.com.olympiad.server.MainApp;

/**
 * Singleton holding all observable server state.
 * ServerHandler updates this; DashboardController observes it.
 * Add new fields here as features grow.
 */
public class DashboardState {
    public static final DashboardState instance = new DashboardState();

    // ── Clients ──────────────────────────────────────────────
    public final ObservableList<ClientInfo> clients =
            FXCollections.observableArrayList();

    // ── Game state ───────────────────────────────────────────
    public final StringProperty  currentGame  = new SimpleStringProperty("—");
    public final StringProperty  gameStatus   = new SimpleStringProperty("Wartend");
    public final StringProperty  currentRound = new SimpleStringProperty("—");
    // Add more game fields here: scores, currentPlayer, timer, etc.

    //Server Info
    public final StringProperty port = new SimpleStringProperty(String.valueOf(MainApp.PORT));
    // ── Helpers ──────────────────────────────────────────────
    public void addClient(int uid) {
        javafx.application.Platform.runLater(() -> {
            clients.add(new ClientInfo(uid));
        });
    }

    public void removeClient(int uid) {
        javafx.application.Platform.runLater(() -> {
            clients.removeIf(c -> c.getUid() == uid);
        });
    }

    public void packetReceived(int uid) {
        javafx.application.Platform.runLater(() -> {
            clients.stream()
                    .filter(c -> c.getUid() == uid)
                    .findFirst()
                    .ifPresent(ClientInfo::incrementPackets);
        });
    }
}