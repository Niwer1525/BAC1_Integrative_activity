package hexmo.domains.board.tiles;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

        assertEquals(new AxialCoordinates(2, 2), new AxialCoordinates(2, 2));

        assertEquals(new AxialCoordinates(1, 1), new AxialCoordinates(1, 1));

        assertEquals(new AxialCoordinates(3, 3), new AxialCoordinates(3, 3));
    }
}
