package test.java.piece;

import board.Board;
import data.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piece.Bishop;
import piece.Pawn;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BishopTest {
    private Board testBoard;
    private static final int START_ROW = 4;
    private static final int START_COL = 4;

    // 비숍은 대각선으로만 움직임 → 4방향: 우상, 좌상, 우하, 좌하
    private static final int[][] DIAGONAL_DIRECTIONS = {
            {-1, +1}, // 우상단
            {-1, -1}, // 좌상단
            {+1, +1}, // 우하단
            {+1, -1}  // 좌하단
    };
    private static final String[] DIRECTION_NAMES = {
            "우상단", "좌상단", "우하단", "좌하단"
    };

    @BeforeEach
    void setUp() {
        testBoard = new Board(false); // 초기화 안된 빈 보드
    }

    // 1. 비숍의 기본 대각선 이동 테스트: 4개 방향 각각에서 3칸 이동 가능해야 함
    @Test
    void testBishopBasicDiagonalMovement() {
        Bishop bishop = new Bishop(PieceColor.WHITE);
        testBoard.setPieceTest(START_ROW, START_COL, bishop); // 중앙 배치

        for (int i = 0; i < DIAGONAL_DIRECTIONS.length; i++) {
            int dRow = DIAGONAL_DIRECTIONS[i][0];
            int dCol = DIAGONAL_DIRECTIONS[i][1];
            int newRow = START_ROW + 3 * dRow;
            int newCol = START_COL + 3 * dCol;

            boolean moved = testBoard.movePiece(START_ROW, START_COL, newRow, newCol);
            assertTrue(moved, "Bishop은 " + DIRECTION_NAMES[i] + " 방향으로 3칸 이동 가능해야 한다.");

            // 다음 테스트를 위해 중앙으로 다시 배치 (실패할 경우 상태 보존 차원)
            testBoard.setPieceTest(START_ROW, START_COL, bishop);
        }
    }

    // 2. 비숍이 보드 밖으로 이동할 수 없는지 테스트
    @Test
    void testBishopCantMoveOutOfBoard() {
        Bishop bishop = new Bishop(PieceColor.WHITE);
        // 예: 보드의 오른쪽 상단 모서리에 배치
        testBoard.setPieceTest(0, 7, bishop);
        // 보드 밖으로 이동 시도 (예: (-1, 8))
        boolean moved = testBoard.movePiece(0, 7, -1, 8);
        assertFalse(moved, "Bishop은 보드 밖으로 이동할 수 없어야 한다.");
    }

    // 3. 비숍 캡처 테스트: 목적지에 상대 기물이 있을 경우 캡처 가능
    @Test
    void testBishopCaptureEnemy() {
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Pawn enemyPawn = new Pawn(PieceColor.BLACK);
        testBoard.setPieceTest(START_ROW, START_COL, bishop);
        // 예: 우상단 방향으로 3칸 떨어진 곳에 상대 기물 배치
        int targetRow = START_ROW - 3;
        int targetCol = START_COL + 3;
        testBoard.setPieceTest(targetRow, targetCol, enemyPawn);

        boolean moved = testBoard.movePiece(START_ROW, START_COL, targetRow, targetCol);
        assertTrue(moved, "Bishop은 상대 기물을 캡처할 수 있어야 한다.");
    }

    // 4. 비숍 경로가 아군 기물에 의해 차단된 경우 이동 불가
    @Test
    void testBishopCannotMoveIfBlockedByAlly() {
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Pawn allyPawn = new Pawn(PieceColor.WHITE);
        testBoard.setPieceTest(START_ROW, START_COL, bishop);
        // 예: 좌하단 방향으로 3칸 이동할 때, 이동 경로의 중간 칸 (5,3)에 아군 기물 배치
        testBoard.setPieceTest(START_ROW + 1, START_COL - 1, allyPawn);

        boolean moved = testBoard.movePiece(START_ROW, START_COL, START_ROW + 3, START_COL - 3);
        assertFalse(moved, "Bishop은 경로 중간에 아군 기물이 있으면 이동할 수 없어야 한다.");
    }

    // 5. 비숍은 중간 경로에 기물이 있을 경우 뛰어넘을 수 없음 (즉, 경로 점프 불가)
    @Test
    void testBishopCannotJumpOverPieces() {
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Pawn blockingPawn = new Pawn(PieceColor.BLACK); // 적 기물이라도 중간에 있으면 이동 불가
        testBoard.setPieceTest(START_ROW, START_COL, bishop);
        // 예: 우하단 방향으로 이동할 때, 중간 칸 (5,5)에 기물 배치, 목적지는 (6,6)로 설정
        testBoard.setPieceTest(START_ROW + 1, START_COL + 1, blockingPawn);
        boolean moved = testBoard.movePiece(START_ROW, START_COL, START_ROW + 2, START_COL + 2);
        assertFalse(moved, "Bishop은 중간 경로에 기물이 있으면 이동할 수 없어야 한다.");
    }
}
