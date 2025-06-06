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

    // 복사 생성자
    public Queen(Queen other) {
        super(other.getColor());  // Piece 클래스의 복사: enum은 immutable하므로 그대로 사용 가능
    }
    // 복사 메소드
    public Queen deepCopy() {
        return new Queen(this);
    }

    @Override
    public boolean isValidMove(Board board, Cell start, Cell end) {
        int rowDiff = Math.abs(start.getRow() - end.getRow());
        int colDiff = Math.abs(start.getCol() - end.getCol());

        boolean straightMove = (start.getRow() == end.getRow() || start.getCol() == end.getCol());
        boolean diagonalMove = (rowDiff == colDiff);

        if (straightMove || diagonalMove) {
            if (!board.isPathClear(start, end)) {
                return false; // 경로에 기물이 있으면 이동 불가
            }
            Piece dest = end.getPiece();
            if (dest == null || dest.getColor() != this.color) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return (color == PieceColor.WHITE) ? "Q" : "q";
    }


}
