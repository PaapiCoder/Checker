package com.jpgame.checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jatinder Pandey
 */
public class Checkers extends Application {
    public static final int TILE_SIZE = 90;
    private static final int HEIGHT = 8;
    private static final int WIDTH = 8;
    private  Tile[][] board = new Tile[WIDTH][HEIGHT];
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Parent createChecker(){
        Pane layout = new Pane();
        layout.setPrefSize(HEIGHT* TILE_SIZE,WIDTH* TILE_SIZE);
        layout.getChildren().addAll(tileGroup,pieceGroup);
        for (int y = 0; y < HEIGHT; y++){
            for (int x = 0; x < WIDTH; x++){
                Tile tile = new Tile((x+y)%2==0, x,y);
                board[x][y] = tile;
                tileGroup.getChildren().add(tile);
                Discs discs = null;
                if(y<=2 && (x+y)%2!=0){
                    discs = createDiscs(DiscsType.DARK,x,y);
                }
                if(y>=5 && (x+y)%2!=0){
                    discs = createDiscs(DiscsType.LIGHT,x,y);
                }
                if(discs !=null) {
                    tile.setDiscs(discs);
                    pieceGroup.getChildren().add(discs);
                }
            }
        }
        return layout;
    }

    private MoveResult tryMove(Discs discs, int newX, int newY){
        try {
            if (board[newX][newY].hasDiscs() || (newX + newY) % 2 == 0) {
                return new MoveResult(MoveType.NON);
            }
            int x0 = toBoard(discs.getPreX());
            int y0 = toBoard(discs.getPreY());
            if (Math.abs(newX - x0) == 1 && newY - y0 == discs.getDiscsType().moveDir) {
                return new MoveResult(MoveType.NOR);
            } else if (Math.abs(newX - x0) == 2 && newY - y0 == discs.getDiscsType().moveDir * 2) {
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;
                if (board[x1][y1].hasDiscs() && board[x1][y1].getDiscs().getDiscsType() != discs.getDiscsType()) {
                    return new MoveResult(MoveType.WON, board[x1][y1].getDiscs());
                }
            }
        }catch (Exception e){
            return new MoveResult(MoveType.NON);
        }
        return new MoveResult(MoveType.NON);
    }

    private int toBoard(double pos){
        return (int)(pos+ TILE_SIZE /2)/ TILE_SIZE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(new VBox(), WIDTH*TILE_SIZE, HEIGHT*TILE_SIZE*1.04);
        ((VBox) scene.getRoot()).getChildren().addAll(createMenuBar(), createChecker());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Checker");
        primaryStage.show();
    }

    private Discs createDiscs(DiscsType discsType, int x, int y){
        Discs discs =  new Discs(discsType,x,y);
        discs.setOnMouseReleased(e -> {
            int newX = toBoard(discs.getLayoutX());
            int newY = toBoard(discs.getLayoutY());
            MoveResult result = tryMove(discs,newX,newY);
            int x0 = toBoard(discs.getPreX());
            int y0 = toBoard(discs.getPreY());
            switch (result.getMoveType()){
                case NON:
                    discs.abortMove();
                    break;
                case NOR:
                    discs.move(newX,newY);
                    board[x0][y0].setDiscs(null);
                    board[newX][newY].setDiscs(discs);
                    break;
                case WON:
                    discs.move(newX,newY);
                    board[x0][y0].setDiscs(null);
                    board[newX][newY].setDiscs(discs);
                    Discs otherDiscs = result.getDiscs();
                    board[toBoard(otherDiscs.getPreX())][toBoard(otherDiscs.getPreY())].setDiscs(null);
                    pieceGroup.getChildren().remove(otherDiscs);
                    break;
            }
        });
        return discs;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();
        Menu menuControl = new Menu("Controls");
        menuControl.getItems().addAll(exitGame());
        menuBar.getMenus().addAll(menuControl);
        return  menuBar;
    }

    private MenuItem exitGame(){
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(t -> System.exit(0));
        return exit;
    }
}