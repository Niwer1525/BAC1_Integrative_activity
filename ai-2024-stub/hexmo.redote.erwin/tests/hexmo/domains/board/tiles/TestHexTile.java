package hexmo.domains.board.tiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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

        assertEquals("Tile at [q: 0 r: 1 s: -1] with type blue", tile.toString());
    }

    @Test
    public void test_createHexTileWithNulle() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            new HexTile(coords, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new HexTile(null, HexColor.RED);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new HexTile(null, null);
        });
    }

    @Test
    public void test_toString() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, HexColor.BLUE);

        assertEquals("Tile at [q: 0 r: 1 s: -1] with type blue", tile.toString());
    }
    
    @Test
    public void test_add() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, HexColor.BLUE);

        AxialCoordinates coords2 = tile.add(new AxialCoordinates(1, 0));
        assertEquals(1, coords2.getQ());
        assertEquals(1, coords2.getR());
    }

    @Test
    public void test_contains() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, HexColor.BLUE);

        assertTrue(tile.contains(0));
        assertTrue(tile.contains(1));
        assertTrue(tile.contains(-1));
    }

    @Test
    public void test_isNotOnBordersNegative() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, HexColor.BLUE);
        assertThrows(IllegalArgumentException.class, () -> {
            tile.isNotOnBorders(-1);
        });
    }

    @Test
    public void test_isNotOnBorders() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, HexColor.BLUE);

        assertFalse(tile.isNotOnBorders(0));
        assertFalse(tile.isNotOnBorders(1));
    }
}
