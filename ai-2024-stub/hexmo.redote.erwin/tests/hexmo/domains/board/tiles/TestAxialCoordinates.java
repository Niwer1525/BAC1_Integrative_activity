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
        AxialCoordinates coords = new AxialCoordinates(0, 0);
        assertEquals(0, coords.hashCode());

        AxialCoordinates coords2 = new AxialCoordinates(2, 2);
        assertEquals(64, coords2.hashCode());

        AxialCoordinates coords3 = new AxialCoordinates(1, 1);
        assertEquals(32, coords3.hashCode());

        AxialCoordinates coords4 = new AxialCoordinates(3, 3);
        assertEquals(96, coords4.hashCode());

        AxialCoordinates coords5 = new AxialCoordinates(4, 4);
        assertEquals(128, coords5.hashCode());

        AxialCoordinates coords6 = new AxialCoordinates(5, 5);
        assertEquals(160, coords6.hashCode());
    }

    @Test
    public void test_equals() {
        AxialCoordinates coords = new AxialCoordinates(0, 0);
        AxialCoordinates coords2 = new AxialCoordinates(0, 0);
        assertEquals(coords, coords2);

        AxialCoordinates coords3 = new AxialCoordinates(2, 2);
        AxialCoordinates coords4 = new AxialCoordinates(2, 2);
        assertEquals(coords3, coords4);

        AxialCoordinates coords5 = new AxialCoordinates(1, 1);
        AxialCoordinates coords6 = new AxialCoordinates(1, 1);
        assertEquals(coords5, coords6);

        AxialCoordinates coords7 = new AxialCoordinates(3, 3);
        AxialCoordinates coords8 = new AxialCoordinates(3, 3);
        assertEquals(coords7, coords8);

        AxialCoordinates coords9 = new AxialCoordinates(4, 4);
        AxialCoordinates coords10 = new AxialCoordinates(4, 4);
        assertEquals(coords9, coords10);

        AxialCoordinates coords11 = new AxialCoordinates(5, 5);
        AxialCoordinates coords12 = new AxialCoordinates(5, 5);
        assertEquals(coords11, coords12);
    }
}
