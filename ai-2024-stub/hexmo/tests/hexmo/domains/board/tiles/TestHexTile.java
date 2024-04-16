package hexmo.domains.board.tiles;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hexmo.supervisors.commons.TileType;

public class TestHexTile {

    @Test
    public void test_createHexTile() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, TileType.BLUE);

        assertEquals(0, tile.getQ());
        assertEquals(1, tile.getR());
    }
}
