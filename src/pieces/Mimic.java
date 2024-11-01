package pieces;

import main.Board;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Mimic extends Piece {
    private Board board;
    private Random random;
    private int moveType;

    public Mimic(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.board = board;
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Mimic";
        this.random = new Random();
        this.moveType = generateNewMoveType();
        if (isWhite){this.sprite = white_mimic.getScaledInstance(board.tileSize, board.tileSize, 1);}
        else {
            this.sprite = black_mimic.getScaledInstance(board.tileSize, board.tileSize, 1);
        }
    }
    private int generateNewMoveType() {
        return random.nextInt(4) + 1;
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        switch (moveType) {
            case 1:
                return isRookMovement(col, row);
            case 2:
                return isBishopMovement(col, row);
            case 3:
                return isKnightMovement(col, row);
            case 4:
                return isQueenMovement(col, row);
            default:
                return false;
        }
    }


    public void finishMove() {
        this.moveType = generateNewMoveType();
    }

    private boolean isRookMovement(int col, int row) {
        return this.col == col || this.row == row;
    }

    private boolean isBishopMovement(int col, int row) {
        return Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    private boolean isKnightMovement(int col, int row) {
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
    }

    private boolean isQueenMovement(int col, int row) {
        return this.col == col || this.row == row || Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    public boolean isRookStyle() {
        return moveType == 1;
    }

    public boolean isBishopStyle() {
        return moveType == 2;
    }

    public boolean isKnightStyle() {
        return moveType == 3;
    }

    public boolean isKingStyle() {
        return moveType == 4;
    }
}
