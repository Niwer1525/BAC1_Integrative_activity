package hexmo.domains.board.path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import hexmo.domains.board.EnumBorder;
import hexmo.domains.board.tiles.AxialCoordinates;
import hexmo.domains.player.HexColor;

public class TestPathCoords {

    @Test
    public void test_constructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PathCoords(null, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new PathCoords(new AxialCoordinates(0, 0), -1);
        });
    }

    @Test
    public void test_getCoords() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 0);
        assertEquals(2, path.getCoords().getQ());
        assertEquals(0, path.getCoords().getR());
    }

    @Test
    public void test_setBorder() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 0);
        path.setBorder(EnumBorder.BOTTOM);
        assertEquals(EnumBorder.BOTTOM, path.getBorder());
    }

    @Test
    public void test_setBorderWithNotNull() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 0);
        path.setBorder(EnumBorder.UNKNOWN);
        path.setBorder(EnumBorder.BOTTOM);
        path.setBorder(null);
        path.setBorder(EnumBorder.UNKNOWN);
        path.setBorder(EnumBorder.TOP_LEFT);
        assertEquals(EnumBorder.BOTTOM, path.getBorder());
    }

    @Test
    public void test_setBorderWithCoords() {
        PathCoords path = new PathCoords(new AxialCoordinates(-3, 3), 0);
        path.setBorder(3, HexColor.RED);
        assertEquals(EnumBorder.BOTTOM, path.getBorder());
    }

    @Test
    public void test_areFromTheSameBorderAs() {
        PathCoords path1 = new PathCoords(new AxialCoordinates(2, 0), 0);
        path1.setBorder(EnumBorder.BOTTOM);

        PathCoords path2 = new PathCoords(new AxialCoordinates(2, 0), 0);
        path2.setBorder(EnumBorder.BOTTOM);

        PathCoords path3 = new PathCoords(new AxialCoordinates(2, 0), 0);
        path3.setBorder(EnumBorder.TOP);

        PathCoords path4 = new PathCoords(new AxialCoordinates(2, 0), 0);
        path4.setBorder(EnumBorder.TOP);

        assertEquals(true, path1.isFromTheSameBorderAs(path2));
        assertEquals(false, path1.isFromTheSameBorderAs(path3));
        assertEquals(false, path1.isFromTheSameBorderAs(path4));
    }

    @Test
    public void test_setLength() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 0);
        path.setLength(3);
        assertEquals(3, path.getLength());
    }

    @Test
    public void test_getLength() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 3);
        assertEquals(3, path.getLength());
    }

    @Test
    public void test_getNeighbors() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 3);
        assertEquals(6, path.getNeighbors().size());
    }

    @Test
    public void test_toString() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 3);
        assertEquals("PathCoords: q: 2 r: 0 s: -2, Length: 3, Border: null", path.toString());

        PathCoords path2 = new PathCoords(new AxialCoordinates(2, 0), 3);
        path2.setBorder(EnumBorder.BOTTOM_LEFT);
        assertEquals("PathCoords: q: 2 r: 0 s: -2, Length: 3, Border: BOTTOM_LEFT", path2.toString());
    }

    @Test
    public void test_hashCode() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 3);
        assertEquals(62, path.hashCode());
    }

    @Test
    public void test_equals() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 3);
        PathCoords path2 = new PathCoords(new AxialCoordinates(2, 0), 3);
        PathCoords path3 = new PathCoords(new AxialCoordinates(2, 0), 4);

        assertEquals(true, path.equals(path));
        assertEquals(true, path.equals(path2));
        assertEquals(true, path.equals(path3));
    }

    @Test
    public void test_equals_NullAndNotSameClass() {
        PathCoords path = new PathCoords(new AxialCoordinates(2, 0), 3);
        assertNotEquals(null, path);
        assertNotEquals(path, new AxialCoordinates(2, 0));
    }
}
