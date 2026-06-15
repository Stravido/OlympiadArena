package main.com.olympiad.client.gui.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.com.olympiad.client.MainApp;
import main.com.olympiad.client.gui.SceneManager;

public class TicTacToeController {

    // ------------ Labels ------------
    @FXML public Label statusLabel;
    @FXML public Label score1Label;
    @FXML public Label score2Label;

    // ------------ Board ------------
    @FXML public GridPane board;

    @FXML public Button cell00, cell01, cell02;
    @FXML public Button cell10, cell11, cell12;
    @FXML public Button cell20, cell21, cell22;

    /** Convenient 2D access: cells[row][col] */
    public Button[][] cells;

    // ------------ Callback set by game logic ------------
    /** Called when a cell is clicked. Implement from outside. */
    public CellClickHandler cellClickHandler = new CellClickHandler(this);

    @FXML
    public void initialize() {
        cells = new Button[][] {
                { cell00, cell01, cell02 },
                { cell10, cell11, cell12 },
                { cell20, cell21, cell22 }
        };
        cellClickHandler.init();
    }

    @FXML
    private void onCellClick(javafx.event.ActionEvent e) {
        if (cellClickHandler == null) return;
        Button btn = (Button) e.getSource();
        String[] parts = btn.getUserData().toString().split(",");
        cellClickHandler.handleClick(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    // ------------ Public API ------------
    /** Set a cell's symbol: "X", "O", or "" */
    public void setCell(int row, int col, String symbol) {
        Button btn = cells[row][col];
        btn.setText(symbol);
        btn.getStyleClass().removeAll("ttt-cell-x", "ttt-cell-o");
        btn.getStyleClass().add(symbol.equals("X")? "ttt-cell-x" : symbol.equals("O")?"ttt-cell-o":"");
    }

    /** Disable a cell (after it's been played) */
    public void disableCell(int row, int col) {
        cells[row][col].setDisable(true);
    }

    /** Highlight winning cells
     * @param winCells 2D Array containing Arrays with 2 values: index 0 -> row; index 1 -> col
     *                 of each cell which is used to win.
     * */
    public void highlightWin(int[][] winCells) {
        for (int[] pos : winCells) {
            cells[pos[0]][pos[1]].getStyleClass().add("ttt-cell-win");
        }
    }

    /** Disable the entire board */
    public void disableBoard() {
        for (Button[] row : cells)
            for (Button cell : row)
                cell.setDisable(true);
    }

    /** Reset the board to empty */
    public void resetBoard() {
        for (Button[] row : cells)
            for (Button cell : row) {
                cell.setText("");
                cell.setDisable(false);
                cell.getStyleClass().removeAll("ttt-cell-x", "ttt-cell-o", "ttt-cell-win");
            }
    }

    /** Update the status text */
    public void setStatus(String text) {
        statusLabel.setText(text);
    }

    /** Update scores */
    public void setScores(int p1, int p2) {
        score1Label.setText(String.valueOf(p1));
        score2Label.setText(String.valueOf(p2));
    }

    @FXML
    private void onBack() {
        MainApp.instance.sceneManager.switchToScene(SceneManager.SceneType.MAIN_MENU);
    }
}