package main;

import pieces.Mimic;
import pieces.Piece;

public class CheckScanner {
    Board board;

    public CheckScanner(Board board) {
        this.board = board;
    }

    public boolean isKingChecked(Move move) {
        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        int kingCol = king.col;
        int kingRow = king.row;

        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")){
            kingCol = move.newCol;
            kingRow = move.newRow;
        }


        return  hitByRook  (move.newCol, move.newRow, king, kingCol, kingRow, 0, 1)||
                hitByRook  (move.newCol, move.newRow, king, kingCol, kingRow, 1, 0)||
                hitByRook  (move.newCol, move.newRow, king, kingCol, kingRow, 0, -1)||
                hitByRook  (move.newCol, move.newRow, king, kingCol, kingRow, -1, 0)||

                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, -1)||
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, -1)||
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, 1)||
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, 1)||

                hitByKnight(move.newCol, move.newRow, king, kingCol, kingRow) ||
                hitByPawn(move.newCol, move.newRow, king, kingCol, kingRow)||
                hitByDuck(move.newCol, move.newRow, king, kingCol, kingRow, move.piece.isWhite) ||
                hitByKing(king, kingCol, kingRow);
    }

    private boolean hitByRook(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
        for (int i = 1; i < 8; i++){
            if (kingCol + (i * colVal) == col && kingRow + (i * rowVal) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol + (i * colVal), kingRow + (i * rowVal));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, king) &&
                        (piece.name.equals("Rook") || piece.name.equals("Queen") ||
                                (piece.name.equals("Mimic") && ((Mimic) piece).isRookStyle()))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(int col, int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal) {
        for (int i = 1; i < 8; i++){
            if (kingCol - (i * colVal) == col && kingRow - (i * rowVal) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol - (i * colVal), kingRow - (i * rowVal));
            if (piece != null && piece != board.selectedPiece){
                if (!board.sameTeam(piece, king)&& piece.name.equals("Bishop") || piece.name.equals("Queen") || (piece.name.equals("Mimic") && ((Mimic) piece).isBishopStyle())){
                    return true;
                }
                break;
            }
        }
        return false;
    }
    private boolean hitByKnight(int col, int row, Piece king, int kingCol, int kingRow) {
        return  checkKnight(board.getPiece(kingCol - 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow - 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow - 1), king, col, row);
    }

    private boolean checkKnight(Piece p, Piece k, int col, int row){
        if (p != null && p != board.selectedPiece) {
            if (!board.sameTeam(p, k) &&
                    (p.name.equals("Knight") ||
                            (p.name.equals("Mimic") && ((Mimic) p).isKnightStyle()) && !(p.col == col && p.row == row))) {
                return true;
            }
        }
        return false;
    }

    private boolean hitByKing(Piece king, int kingCol, int kingRow) {
        return  checkKing(board.getPiece(kingCol -1, kingRow -1), king) ||
                checkKing(board.getPiece(kingCol +1, kingRow -1), king) ||
                checkKing(board.getPiece(kingCol, kingRow -1), king) ||
                checkKing(board.getPiece(kingCol -1, kingRow), king) ||
                checkKing(board.getPiece(kingCol +1, kingRow), king) ||
                checkKing(board.getPiece(kingCol -1, kingRow +1), king) ||
                checkKing(board.getPiece(kingCol +1, kingRow +1), king) ||
                checkKing(board.getPiece(kingCol, kingRow +1), king);
    }

    private boolean checkKing(Piece p, Piece k){
        return p != null && !board.sameTeam(p, k) && p.name.equals("King") ||
        (p != null && !board.sameTeam(p, k) && p.name.equals("Mimic") && ((Mimic) p).isKingStyle());
    }

    private boolean hitByPawn(int col, int row, Piece king, int kingCol, int kingRow) {
        int colorVal = king.isWhite ? -1 : 1;
        return  checkPawn(board.getPiece(kingCol + 1, kingRow + colorVal), king, col, row) ||
                checkPawn(board.getPiece(kingCol - 1, kingRow + colorVal), king, col, row);
    }

    private boolean checkPawn(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && !(p.col == col && p.row == row);
    }

    private boolean hitByDuck(int col, int row, Piece king, int kingCol, int kingRow, boolean isWhite) {
        if (isWhite){
            return checkDuck(board.getPiece(kingCol - 1, kingRow -1), king, col, row) ||
                    checkDuck(board.getPiece(kingCol, kingRow - 2), king, col, row) ||

                    checkDuck(board.getPiece(kingCol - 2, kingRow), king, col, row) ||
                    checkDuck(board.getPiece(kingCol - 1, kingRow + 1), king, col, row) ||

                    checkDuck(board.getPiece(kingCol + 2, kingRow), king, col, row) ||
                    checkDuck(board.getPiece(kingCol + 1, kingRow - 1), king, col, row);
        } else {
            return checkDuck(board.getPiece(kingCol - 2, kingRow), king, col, row) ||
                    checkDuck(board.getPiece(kingCol - 1, kingRow + 1), king, col, row) ||

                    checkDuck(board.getPiece(kingCol + 2, kingRow), king, col, row) ||
                    checkDuck(board.getPiece(kingCol + 1, kingRow - 1), king, col, row) ||
                    checkDuck(board.getPiece(kingCol + 1, kingRow + 1), king, col, row) ||
                    checkDuck(board.getPiece(kingCol, kingRow + 2), king, col, row);
        }
    }

    private boolean checkDuck(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Duck");
    }

    public boolean isGameOver(Piece king){
        for (Piece piece : board.pieceList){
            if (board.sameTeam(piece, king)){
                board.selectedPiece = piece == king ? king :null;
                for (int row = 0; row < board.rows; row++){
                    for (int col = 0; col < board.cols; col++){
                        Move move = new Move(board, piece, col, row);
                        if (board.isValidMove(move)){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
