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
}
