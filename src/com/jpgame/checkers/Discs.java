package com.jpgame.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import static com.jpgame.checkers.Checkers.TILE_SIZE;

/**
 *
 * @author Jatinder Pandey
 */
public class Discs extends StackPane {

    private DiscsType discsType;
    private final double discsSize = 0.4;
    private double currX,preX;
    private double currY,preY;

    public DiscsType getDiscsType() {
        return discsType;
    }

    public double getPreX() {
        return preX;
    }

    public double getPreY() {
        return preY;
    }

    public Discs(DiscsType discsType, int x, int y) {
        this.discsType = discsType;
        move(x, y);
        Sphere discs = new Sphere(TILE_SIZE * discsSize);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(discsType == DiscsType.DARK ? Color.INDIANRED : Color.DARKSLATEGRAY);
        discs.setMaterial(material);
        discs.setTranslateX((TILE_SIZE - TILE_SIZE * discsSize * 2) / 2);
        discs.setTranslateY((TILE_SIZE - TILE_SIZE * discsSize * 2) / 2);
        getChildren().addAll(discs);

        setOnMousePressed(e->{
            currX = e.getSceneX();
            currY = e.getSceneY();
        });
        setOnMouseDragged(e->{
            relocate(e.getSceneX()-currX+preX,e.getSceneY()-currY+preY);
        });
    }
    public void move(int x, int y){
        preX = x* TILE_SIZE;
        preY = y* TILE_SIZE;
        relocate(preX,preY);
    }

    public void abortMove(){
        relocate(preX,preY);
    }
}
