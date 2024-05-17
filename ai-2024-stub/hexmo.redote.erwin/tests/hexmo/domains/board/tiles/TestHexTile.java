package hexmo.domains.board.tiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hexmo.domains.player.HexColor;

public class TestHexTile {
    
    @Test
    public void test_createHexTileCopy() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, HexColor.BLUE);
        HexTile tile2 = new HexTile(tile);

        assertEquals(0, tile2.getQ());
        assertEquals(1, tile2.getR());
        assertEquals(HexColor.BLUE, tile2.getColor());
        assertEquals("Tile at [q: 0 r: 1 s: -1] with type blue", tile2.toString());
    }

    @Test
    public void test_createHexTile() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, HexColor.BLUE);

        assertEquals(0, tile.getQ());
        assertEquals(1, tile.getR());
        assertEquals(HexColor.BLUE, tile.getColor());
        assertFalse(tile.isEmpty());
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
    public void test_contains() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);
        HexTile tile = new HexTile(coords, HexColor.BLUE);

        assertFalse(tile.contains(-2));
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
        HexTile tile = new HexTile(new AxialCoordinates(0, 1), HexColor.BLUE);
        assertFalse(tile.isNotOnBorders(0));
        assertFalse(tile.isNotOnBorders(1));
    }

    @Test
    public void test_isNotOnBordersQ() {
        HexTile tile = new HexTile(new AxialCoordinates(0, 0), HexColor.BLUE);
        assertFalse(tile.isNotOnBorders(0));
        assertTrue(tile.isNotOnBorders(1));
    }

    @Test
    public void testIsNotOnBorders_InsideBoard() {
        // HexTile hexTile = new HexTile(1, 1, -2);

        HexTile tile = new HexTile(new AxialCoordinates(1, 1), HexColor.BLUE);
        assertFalse(tile.isNotOnBorders(2));
    }
    
    @Test
    public void testIsNotOnBorders_OnBorder() {
        // HexTile hexTile = new HexTile(2, -2, 0);
        HexTile tile = new HexTile(new AxialCoordinates(2, -2), HexColor.BLUE);
        assertFalse(tile.isNotOnBorders(2));
    }
    
    @Test
    public void testIsNotOnBorders_OutsideBoard() {
        // HexTile hexTile = new HexTile(3, 0, -3);
        HexTile tile = new HexTile(new AxialCoordinates(3, 0), HexColor.BLUE);
        assertTrue(tile.isNotOnBorders(2));
    }

    @Test
    public void testCanBeClaimed() {
        HexTile tile = new HexTile(new AxialCoordinates(0, 1), HexColor.BLUE);
        assertTrue(tile.canBeClaimed(3, true));
        assertTrue(tile.canBeClaimed(3, false));

        HexTile tile2 = new HexTile(new AxialCoordinates(1, -3), HexColor.BLUE);
        assertFalse(tile2.canBeClaimed(3, true)); // Red cannot claim center tile on the top border (Blue)
        assertTrue(tile2.canBeClaimed(3, false));
    }
}
