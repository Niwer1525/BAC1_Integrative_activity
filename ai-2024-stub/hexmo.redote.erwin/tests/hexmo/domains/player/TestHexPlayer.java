package hexmo.domains.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestHexPlayer {

    @Test
    public void test_createPlayer() {
        HexPlayer player = new HexPlayer("Charles", HexColor.BLUE);

        assertEquals("Charles", player.getName());
        assertEquals(HexColor.BLUE, player.getColor());
    }

    @Test
    public void test_setColor() {
        HexPlayer player = new HexPlayer("Charles", HexColor.BLUE);

        assertEquals("Charles", player.getName());
        assertEquals(HexColor.BLUE, player.getColor());

        player.setColor(HexColor.RED);
        assertEquals(HexColor.RED, player.getColor());
        
        player.setColor(null);
        assertEquals(HexColor.RED, player.getColor());
    }

    @Test
    public void test_toString() {
        HexPlayer player = new HexPlayer("P3", HexColor.BLUE);

        assertEquals("P3 (bleu)", player.toString());
    }
}
