package com.jpgame.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jatinder Pandey
 */
public class Tile extends Rectangle {

    private Discs discs;

    public boolean hasDiscs(){
        return discs !=null;
    }

    public Discs getDiscs() {
        return discs;
    }

    public void setDiscs(Discs discs) {
        this.discs = discs;
    }

    public Tile(boolean light, int x, int y){
        setWidth(Checkers.TILE_SIZE);
        setHeight(Checkers.TILE_SIZE);
        relocate(x* Checkers.TILE_SIZE,y* Checkers.TILE_SIZE);
        setFill(light? Color.INDIANRED:Color.DARKSLATEGRAY);
    }
}
