package main.com.olympiad.client.gui.tictactoe;

public class CellClickHandler {
    private String turn = Math.random()==0?"X":"O";
    private TicTacToeController controller;
    public CellClickHandler(TicTacToeController controller) {
        this.controller = controller;
    }

    public void init(){
        if(controller==null){
            System.err.println("NO TIC_TAC_TOE CONTROLLER SET!");
            return;
        }
        controller.setStatus("Player \""+turn+"\"'s turn:");

    }
    public void handleClick(int row, int col){
        if(controller==null){
            System.err.println("NO TIC_TAC_TOE CONTROLLER SET!");
            return;
        }
        turn = turn.equals("X")?"O":"X";
        controller.setStatus("Player \""+turn+"\"");
        System.out.println("Clicked at row "+row+" and col "+col);
        //TODO Game-logic for Tic-Tac-Toe
    }
}
