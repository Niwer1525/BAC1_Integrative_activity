package hexmo.domains.stats;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hexmo.domains.board.HexBoard;
import hexmo.domains.board.TestHexBoard;
import hexmo.domains.board.stats.HexStats;
import hexmo.domains.player.HexColor;
import hexmo.domains.player.HexPlayer;

public class TestHexStats {

    private static final HexPlayer player1 = new HexPlayer("Player1", HexColor.RED);
    private static final HexPlayer player2 = new HexPlayer("Player2", HexColor.BLUE);

    @Test
    public void test_calculateStats_empty() {
        HexStats stats = new HexStats(3, new HexBoard(3));
        stats.calculateStats(player1, player2, 0);
        assertEquals("Vainqueur est Player1 (rouge)", stats.getWinner());
        assertEquals("Score de Player1 10.00", stats.getWinnerScore());
        assertEquals("Score de Player2 5.00", stats.getLoserScore());
        assertEquals("Taux de remplissage moyen 0%", stats.getBoardFillRate());
    }

    @Test
    public void test_calculateStats() {
        final int boardSize = 3;
        final HexBoard board = new HexBoard(boardSize);
        final HexStats stats = new HexStats(boardSize, board);

        stats.calculateStats(player1, player2, redWinShort(board, boardSize));

        assertEquals("Vainqueur est Player1 (rouge)", stats.getWinner());
        assertEquals("Score de Player1 5.00", stats.getWinnerScore());
        assertEquals("Score de Player2 2.50", stats.getLoserScore());
        assertEquals("Taux de remplissage moyen 14%", stats.getBoardFillRate());
    }

    @Test
    public void test_calculateAverageStats() {
        final int boardSize = 3;
        final HexBoard board = new HexBoard(boardSize);
        final HexStats stats = new HexStats(boardSize, board);
        int path = -1;

        stats.calculateStats(player1, player2, path = redWinShort(board, boardSize));
        assertsAverageStats(stats, "5.00", "2.50", "14");
        assertEquals(5, path);

        stats.calculateStats(player2, player1, path =  blueWinShort(board, boardSize));
        assertsAverageStats(stats, "7.50", "7.50", "20");
        assertEquals(5, path);
    }

    @Test
    public void test_negative() {
        final int boardSize = 3;
        final HexBoard board = new HexBoard(boardSize);
        final HexStats stats = new HexStats(boardSize, board);
        
        assertThrows(IllegalArgumentException.class, () -> {
            stats.calculateStats(player1, player2, -1);
        });
    }

    private void assertsAverageStats(HexStats stats, String p1Score, String p2Score, String boardFillRate) {
        assertEquals("Score moyen de Player1 "+p1Score, stats.getPlayerTotalScore(player1));
        assertEquals("Score moyen de Player2 "+p2Score, stats.getPlayerTotalScore(player2));
        assertEquals("Taux de remplissage moyen "+boardFillRate+"%", stats.getAverageBoardFillRate());
    }

    private int redWinShort(HexBoard board, int boardSize) {
        TestHexBoard.move(board, 0, -3).setColor(HexColor.RED);
        TestHexBoard.move(board, 0, -2).setColor(HexColor.RED);
        TestHexBoard.move(board, 1, -2).setColor(HexColor.RED);
        TestHexBoard.move(board, 2, -2).setColor(HexColor.RED);
        TestHexBoard.move(board, 3, -3).setColor(HexColor.RED);
        return board.findPath(HexColor.RED, boardSize);
    }

    private int blueWinShort(HexBoard board, int boardSize) {
        TestHexBoard.move(board, -3, 3).setColor(HexColor.BLUE);
        TestHexBoard.move(board, -2, 2).setColor(HexColor.BLUE);
        TestHexBoard.move(board, -1, 2).setColor(HexColor.BLUE);
        TestHexBoard.move(board, 0, 2).setColor(HexColor.BLUE);
        TestHexBoard.move(board, 0, 3).setColor(HexColor.BLUE);
        return board.findPath(HexColor.BLUE, boardSize);
    }
}
