package com.jpgame.checkers;

/**
 *
 * @author Jatinder Pandey
 */
public class MoveResult {
    private MoveType moveType;
    private Discs discs;

    public MoveType getMoveType() {
        return moveType;
    }

    public Discs getDiscs() {
        return discs;
    }

    public MoveResult(MoveType moveType) {
        this(moveType,null);
    }

    public MoveResult(MoveType moveType, Discs discs) {
        this.moveType = moveType;
        this.discs = discs;
    }
}
