package hexmo.domains.board.tiles;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import hexmo.domains.HexGame;
import hexmo.domains.board.EnumBorder;
import hexmo.domains.player.HexColor;

public class TestAxialCoordinates {

    private static final float TOLERANCE = 0;

    @Test
    public void test_createAxialCoords() {
        AxialCoordinates coords = new AxialCoordinates(0, 0);
        assertEquals(0, coords.getQ());
        assertEquals(0, coords.getR());

        AxialCoordinates coords2 = new AxialCoordinates(2, 2);
        assertEquals(2, coords2.getQ());
        assertEquals(2, coords2.getR());
    }

    @Test
    public void test_getAsXY() {
        AxialCoordinates coords2 = new AxialCoordinates(2, 2);
        assertEquals(5.196152210235596, coords2.asX(), TOLERANCE);
        assertEquals(3.0, coords2.asY(), TOLERANCE);
    }

    @Test
    public void test_hashCode() {
        assertEquals(0, new AxialCoordinates(0, 0).hashCode());

        assertEquals(64, new AxialCoordinates(2, 2).hashCode());

        assertEquals(32, new AxialCoordinates(1, 1).hashCode());

        assertEquals(96, new AxialCoordinates(3, 3).hashCode());
    }

    @Test
    public void test_equals() {
        assertEquals(new AxialCoordinates(0, 0), new AxialCoordinates(0, 0));

        assertNotEquals(new AxialCoordinates(2, 2), new AxialCoordinates(2, 1));

        assertNotEquals(new AxialCoordinates(1, 1), new HexGame(25));

        AxialCoordinates coords = new AxialCoordinates(3, 3);
        assertEquals(coords, coords);
    }
   
    @Test
    public void test_add() {
        AxialCoordinates coords = new AxialCoordinates(0, 1);

        AxialCoordinates coords2 = coords.add(new AxialCoordinates(1, 0));
        assertEquals(1, coords2.getQ());
        assertEquals(1, coords2.getR());
    }

    @Test
    public void test_getDiagonals() {
        AxialCoordinates coords = new AxialCoordinates(0, 0);
        assertEquals(6, coords.getDiagonals().size());
    }

    @Test
    public void test_getCommonNeighbors() {
        AxialCoordinates coords = new AxialCoordinates(0, 0);
        AxialCoordinates coords2 = new AxialCoordinates(1, 0);
        assertEquals(2, coords.getCommonNeighborsWith(coords2).size());
    }

    @Test
    public void test_getBorderTop_NotOnBorders() {
        AxialCoordinates coord = new AxialCoordinates(0, 0);
        assertEquals(EnumBorder.UNKNOWN, coord.getBorderForCoords(3, HexColor.RED));
        assertEquals(EnumBorder.UNKNOWN, coord.getBorderForCoords(3, HexColor.BLUE));
        assertEquals(EnumBorder.UNKNOWN, coord.getBorderForCoords(3, HexColor.UNKNOWN));
    }

    @Test
    public void test_getBorder_OnBorder() {
        AxialCoordinates coord = new AxialCoordinates(1, -3);
        assertEquals(EnumBorder.TOP, coord.getBorderForCoords(3, HexColor.BLUE));
        assertEquals(EnumBorder.UNKNOWN, coord.getBorderForCoords(3, HexColor.RED));
        assertEquals(EnumBorder.UNKNOWN, coord.getBorderForCoords(3, HexColor.UNKNOWN));
    }

    @Test
    public void test_getBorderTop_OnBorderWithCorner() {
        AxialCoordinates coord = new AxialCoordinates(0, -3);
        assertEquals(EnumBorder.TOP, coord.getBorderForCoords(3, HexColor.BLUE));
        assertEquals(EnumBorder.TOP_LEFT, coord.getBorderForCoords(3, HexColor.RED));
        assertEquals(EnumBorder.UNKNOWN, coord.getBorderForCoords(3, HexColor.UNKNOWN));
    }

    @Test
    public void test_getBorderBottom_OnBorder() {
        AxialCoordinates coord = new AxialCoordinates(-2, 3);
        assertEquals(EnumBorder.UNKNOWN, coord.getBorderForCoords(3, HexColor.BLUE));
        assertEquals(EnumBorder.BOTTOM, coord.getBorderForCoords(3, HexColor.RED));
        assertEquals(EnumBorder.UNKNOWN, coord.getBorderForCoords(3, HexColor.UNKNOWN));
    }

    @Test
    public void test_getBorderBottom_OnBorderWithCorner() {
        AxialCoordinates coord = new AxialCoordinates(-3, 3);
        assertEquals(EnumBorder.BOTTOM, coord.getBorderForCoords(3, HexColor.RED));
        assertEquals(EnumBorder.BOTTOM_LEFT, coord.getBorderForCoords(3, HexColor.BLUE));
        assertEquals(EnumBorder.UNKNOWN, coord.getBorderForCoords(3, HexColor.UNKNOWN));
    }

    @Test
    public void test_getBorderBottomRight_OnBorderWithCorner() {
        AxialCoordinates coord = new AxialCoordinates(0, 3);
        assertEquals(EnumBorder.BOTTOM_RIGHT, coord.getBorderForCoords(3, HexColor.BLUE));
    }
    
    @Test
    public void test_getBorder_Null() {
        AxialCoordinates coord = new AxialCoordinates(0, 3);
        assertThrows(IllegalArgumentException.class, () -> {
            coord.getBorderForCoords(3, null);
        });
    }
}
