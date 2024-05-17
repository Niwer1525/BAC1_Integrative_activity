package hexmo.domains.board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import hexmo.domains.board.tiles.HexTile;
import hexmo.domains.player.HexColor;

public class TestHexBoard {

    @Test
    public void test_createBoardWithNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HexBoard(-1);
        });
    }

    @Test
    public void test_toString() {
        HexBoard board = new HexBoard(0);
        assertEquals("HexBoard{tiles=1, activeTile=Tile at [q: 0 r: 0 s: 0] with type unknown}", board.toString());

        HexBoard board2 = new HexBoard(1);
        assertEquals("HexBoard{tiles=7, activeTile=Tile at [q: 0 r: 0 s: 0] with type unknown}", board2.toString());

        HexBoard board3 = new HexBoard(3);
        assertEquals("HexBoard{tiles=37, activeTile=Tile at [q: 0 r: 0 s: 0] with type unknown}", board3.toString());
    }

    @Test
    public void test_isActiveTileClaimed() {
        HexBoard board = new HexBoard(20);
        assertFalse(board.isActiveTileClaimed(false));
        assertFalse(board.isActiveTileClaimed(true));

        HexTile tile = board.moveTo(1, 0);
        tile.setColor(HexColor.BLUE);
        assertTrue(board.isActiveTileClaimed(false));
        assertFalse(board.isActiveTileClaimed(true));
    }

    @Test
    public void test_updateHelper_playerRedTwoBridges() {
        HexBoard board = new HexBoard(3);
        board.moveTo(0, 0).setColor(HexColor.RED);
        board.moveTo(-1, -1).setColor(HexColor.RED);
        board.moveTo(1, -1);
        board.moveTo(0, -1).setColor(HexColor.RED);
        assertEquals(4, board.updateHelper(HexColor.RED).size());
    }

    /* @Test
    public void test_updateHelper_playerBlueTwoBridges() {
        HexBoard board = new HexBoard(3);
        board.moveTo(-1,1);
        board.moveTo(0, 1).setColor(HexColor.BLUE);
        board.moveTo(1, 0);
        board.moveTo(0, -1);
        board.moveTo(1, 0).setColor(HexColor.BLUE);
        board.moveTo(-1, 0);
        board.moveTo(-1, 0);
        board.moveTo(-1, 0).setColor(HexColor.BLUE);
        assertEquals(4, board.updateHelper(HexColor.BLUE).size());
    } */

    @Test
    public void test_updateHelper_playerRedOneBridges() {
        HexBoard board = new HexBoard(3);
        board.moveTo(0, 0).setColor(HexColor.RED);
        board.moveTo(-1, -1).setColor(HexColor.RED);
        board.moveTo(1, -1);
        board.moveTo(0, -1).setColor(HexColor.RED);
        board.moveTo(0, 1);
        board.moveTo(0, 1).setColor(HexColor.RED);
        assertEquals(2, board.updateHelper(HexColor.RED).size());
    }

    /* @Test
    public void test_updateHelper_playerBlueOneBridges() {
        HexBoard board = new HexBoard(3);
        board.moveTo(-1,1);
        board.moveTo(0, 1).setColor(HexColor.BLUE);
        board.moveTo(1, 0).setColor(HexColor.BLUE);
        board.moveTo(0, -1);
        board.moveTo(1, 0).setColor(HexColor.BLUE);
        board.moveTo(-1, 0);
        board.moveTo(-1, 0);
        board.moveTo(-1, 0).setColor(HexColor.BLUE);
        assertEquals(2, board.updateHelper(HexColor.BLUE).size());
    } */

    @Test
    public void test_updateHelper_playerRedOutsideBounds() {
        HexBoard board = new HexBoard(3);
        board.moveTo(0, 0).setColor(HexColor.RED);
        board.moveTo(-1, -1).setColor(HexColor.RED);
        board.moveTo(1, -1);
        board.moveTo(0, -1).setColor(HexColor.RED);
        board.moveTo(0, -1).setColor(HexColor.RED); // Outside bounds
        assertEquals(4, board.updateHelper(HexColor.RED).size());
    }

    @Test
    public void test_updateHelper_playerRedBorders() {
        HexBoard board = new HexBoard(3);
        board.moveTo(1, 0);
        board.moveTo(1, 0);
        board.moveTo(1, 0).setColor(HexColor.RED);
        board.moveTo(-1, 0);
        board.moveTo(-1, 1).setColor(HexColor.RED);
        assertEquals(2, board.updateHelper(HexColor.RED).size());
    }

    @Test
    public void test_findPathLength_case1() {
        final int boardSize = 3;
        HexBoard board = new HexBoard(boardSize);
        move(board, 0, -3).setColor(HexColor.RED);
        move(board, 0, -1).setColor(HexColor.RED);
        move(board, 1, -1).setColor(HexColor.RED);
        move(board, 2, -1).setColor(HexColor.RED);
        move(board, 3, -3).setColor(HexColor.RED);
        assertEquals(-1, board.findPath(HexColor.BLUE, boardSize));
        assertEquals(5, board.findPath(HexColor.RED, boardSize));
    }

    @Test
    public void test_findPathLength_case2() {
        final int boardSize = 4;
        HexBoard board = new HexBoard(boardSize);

        //RED
        move(board, 0, -4).setColor(HexColor.RED);
        move(board, 0, -3).setColor(HexColor.RED);
        move(board, 0, -2).setColor(HexColor.RED);
        move(board, -1, -1).setColor(HexColor.RED);
        move(board, -1, 0).setColor(HexColor.RED);
        move(board, 0, 0).setColor(HexColor.RED);
        move(board, 1, 0).setColor(HexColor.RED);
        
        //BLUE
        move(board, -4, 2).setColor(HexColor.BLUE);
        move(board, -3, 2).setColor(HexColor.BLUE);
        move(board, -2, 2).setColor(HexColor.BLUE);
        move(board, -1, 2).setColor(HexColor.BLUE);
        move(board, 0, 2).setColor(HexColor.BLUE);
        move(board, 1, 2).setColor(HexColor.BLUE);
        move(board, 1, 3).setColor(HexColor.BLUE);

        assertEquals(7, board.findPath(HexColor.RED, boardSize));
    }

    @Test
    public void test_findPathLength_case3() {
        
    }

    @Test
    public void test_findPathLength_case4() {
    }

    public static HexTile move(HexBoard board, int q, int r) {
        // Methode pour me rendre la vie plus facile en utilsant la réflection java 
        // (Seul moyen que j'ai d'obtenir les attributs privés ainsi que les méthodes privées d'une classe sans faire râler PMD)
        try {
            // Tentons d'invoker la methode "getTileAt"
            var getTileMethod = HexBoard.class.getDeclaredMethod("getTileAt", int.class, int.class);
            getTileMethod.setAccessible(true); // Rendre accessible
            
            var tile = (HexTile) getTileMethod.invoke(board, q, r); // Récupérer avec l'instance

            return tile != null ? tile : board.moveTo(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            // A priori je contiens mes tests donc ceci ne devrait pas se produire.
        }
        return board.moveTo(0, 0);
    }
}