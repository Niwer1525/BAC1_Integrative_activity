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
        /* AxialCoordinates coords2 = new AxialCoordinates(2, 2);
        assertEquals(5.196152, coords2.asX(), TOLERANCE);
        assertEquals(2.2f, coords2.asY(), TOLERANCE); */
    }
}
