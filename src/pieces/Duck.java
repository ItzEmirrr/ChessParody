package pieces;

import main.Board;

public class Duck extends Piece {
    public Duck(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;

        this.isWhite = isWhite;
        this.name = "Duck";

        this.sprite = duck.getScaledInstance(board.tileSize, board.tileSize, 1);
    }

    public boolean isValidMovement(int col, int row) {
        if (isWhite){
            return Math.abs(this.col - col) + Math.abs(this.row - row) == 2 &&
                    !(this.row < row && col >= this.col);
        } else {
            return Math.abs(this.col - col) + Math.abs(this.row - row) == 2 &&
                    !(this.row > row && col <= this.col);
        }
    }


    public boolean hasJumpedOver(int col, int row) {
        if (Math.abs(this.col - col) + Math.abs(this.row - row) == 2) {
            int midCol = (this.col + col) / 2;
            int midRow = (this.row + row) / 2;

            if (board.getPiece(midCol, midRow) != null){
                return true;
            }
        }
        return false;
    }
}
