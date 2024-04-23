package hexmo.domains.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class TestHexPlayer {

    private static final String PLAYER_1_NAME = "Charles";
    private static final String PLAYER_2_NAME = "P3";

    @Test
    public void test_createPlayerWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new HexPlayer(null, HexColor.BLUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new HexPlayer(PLAYER_2_NAME, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new HexPlayer("", null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new HexPlayer(null, null);
        });
    }

    @Test
    public void test_createPlayerNonNull() {
        HexPlayer player = new HexPlayer(PLAYER_1_NAME, HexColor.BLUE);

        assertEquals(PLAYER_1_NAME, player.getName());
        assertEquals(HexColor.BLUE, player.getColor());
    }

    @Test
    public void test_setColor() {
        HexPlayer player = new HexPlayer(PLAYER_2_NAME, HexColor.BLUE);

        assertEquals(PLAYER_2_NAME, player.getName());
        assertEquals(HexColor.BLUE, player.getColor());

        player.setColor(HexColor.RED);
        assertEquals(HexColor.RED, player.getColor());
        
        player.setColor(null);
        assertEquals(HexColor.RED, player.getColor());
    }

    @Test
    public void test_toString() {
        HexPlayer player = new HexPlayer(PLAYER_2_NAME, HexColor.BLUE);

        assertEquals("P3 (bleu)", player.toString());
    }
}
