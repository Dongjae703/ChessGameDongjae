package piece;

import board.Board;
import board.Cell;
import data.PieceColor;

//////////////////////////////////////////////
// 3) board.Board 클래스: 8x8 체스판 전체를 관리.

public class Queen extends Piece {
    public Queen(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, Cell startCell, Cell endCell) {
        int startRow = startCell.getRow();
        int startCol = startCell.getCol();
        int endRow = endCell.getRow();
        int endCol = endCell.getCol();
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        // piece.Queen: 수직, 수평, 대각선 이동
        boolean straightMove = (startRow == endRow || startCol == endCol);
        boolean diagonalMove = (rowDiff == colDiff);

        if ((straightMove || diagonalMove) && board.isPathClear(startCell, endCell)) {
            Piece dest = endCell.getPiece();
            if (dest == null || dest.getColor() != this.color)
                return true;
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return (color == PieceColor.WHITE) ? "Q" : "q";
    }
}
