package hexmo.domains.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestHexPlayer {

    private static final String PLAYER_1_NAME = "Charles";
    private static final String PLAYER_2_NAME = "P3";

    @Test
    public void test_createPlayer() {
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
