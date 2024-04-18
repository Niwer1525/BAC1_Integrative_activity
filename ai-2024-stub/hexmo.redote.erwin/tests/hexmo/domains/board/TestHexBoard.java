package hexmo.domains.board;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class TestHexBoard {

    @Test
    public void test_toString() {
        HexBoard board = new HexBoard(0);
        assertEquals("HexBoard{tiles=1, activeTile=Tile at [q: 0 r: 0] with type unknown}", board.toString());

        HexBoard board2 = new HexBoard(1);
        assertEquals("HexBoard{tiles=7, activeTile=Tile at [q: 0 r: 0] with type unknown}", board2.toString());

        HexBoard board3 = new HexBoard(3);
        assertEquals("HexBoard{tiles=37, activeTile=Tile at [q: 0 r: 0] with type unknown}", board3.toString());
    }
}
