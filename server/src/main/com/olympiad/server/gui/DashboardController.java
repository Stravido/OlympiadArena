package main.com.olympiad.server.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class DashboardController {

    @FXML private Label portLabel;
    @FXML private Label uptimeLabel;
    @FXML private Label clientCountLabel;
    @FXML private Label currentGameLabel;
    @FXML private Label gameStatusLabel;
    @FXML private Label roundLabel;

    @FXML private TableView<ClientInfo> clientTable;
    @FXML private TableColumn<ClientInfo, Integer> colUid;
    @FXML private TableColumn<ClientInfo, String>  colIp;
    @FXML private TableColumn<ClientInfo, Integer> colPkts;
    @FXML private TableColumn<ClientInfo, String>  colStatus;
    @FXML private TableColumn<ClientInfo, Void>    colAction;

    private final DashboardState state = DashboardState.instance;
    private long startTime;

    @FXML
    public void initialize() {
        startTime = System.currentTimeMillis();
        setupTable();
        bindState();
        startUptime();
    }

    private void setupTable() {
        colUid.setCellValueFactory(new PropertyValueFactory<>("uid"));
        colIp.setCellValueFactory(new PropertyValueFactory<>("ip"));
        colPkts.setCellValueFactory(new PropertyValueFactory<>("packets"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item);
                setStyle(item.equals("Verbunden")
                        ? "-fx-text-fill: #a080ff;"
                        : "-fx-text-fill: #c0506a;");
            }
        });

        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Kick");
            {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6b4060;" +
                        "-fx-font-family: 'Segoe UI'; -fx-font-size: 11px; -fx-cursor: hand;");
                btn.setOnMouseEntered(e -> btn.setStyle(btn.getStyle() + "-fx-text-fill: #c0506a;"));
                btn.setOnAction(e -> {
                    ClientInfo c = getTableView().getItems().get(getIndex());
                    state.removeClient(c.getUid());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

        clientTable.setItems(state.clients);
    }

    private void bindState() {
        state.clients.addListener((javafx.collections.ListChangeListener<ClientInfo>) c ->
                clientCountLabel.setText(String.valueOf(state.clients.size()))
        );
        currentGameLabel.textProperty().bind(state.currentGame);
        gameStatusLabel.textProperty().bind(state.gameStatus);
        roundLabel.textProperty().bind(state.currentRound);
        portLabel.textProperty().bind(state.port);
    }

    private void startUptime() {
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            long ms = System.currentTimeMillis() - startTime;
            long h = ms / 3600000, m = (ms % 3600000) / 60000, s = (ms % 60000) / 1000;
            uptimeLabel.setText(String.format("%02d:%02d:%02d", h, m, s));
        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
    }

    @FXML
    private void onStopServer() {
        Platform.exit();
    }
}