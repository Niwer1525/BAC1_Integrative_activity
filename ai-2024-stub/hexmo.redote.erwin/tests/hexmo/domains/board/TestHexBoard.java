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
}
