package hexmo.domains.board.tiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import hexmo.domains.player.HexColor;

public class TestHexTile {

    @Test
    public void test_createHexTile() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, HexColor.BLUE);

        assertEquals(0, tile.getQ());
        assertEquals(1, tile.getR());
        assertEquals(HexColor.BLUE, tile.getColor());
        assertFalse(tile.isEmpty());

        AxialCoordinates coords2 = tile.getCoords();
        assertEquals(coords, coords2);
        assertEquals(0, coords2.getQ());
        assertEquals(1, coords2.getR());

        assertEquals("Tile at [q: 0 r: 1] with type blue", tile.toString());
    }
}
