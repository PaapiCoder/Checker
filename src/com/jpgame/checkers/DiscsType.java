package com.jpgame.checkers;

/**
 *
 * @author Jatinder Pandey
 */
public enum DiscsType {
    DARK(1), LIGHT(-1);

    final int moveDir;

    DiscsType(int moveDir) {
        this.moveDir = moveDir;
    }
}
