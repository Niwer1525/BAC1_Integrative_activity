package hexmo.domains.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestHexColor {

    @Test
    public void test_getOpposite() {
        assertEquals(HexColor.RED, HexColor.BLUE.getOpposite());
        assertEquals(HexColor.BLUE, HexColor.RED.getOpposite());
        assertEquals(HexColor.RED, HexColor.RED.getOpposite().getOpposite());
    }

    @Test
    public void test_getDispalyName() {
        assertEquals("rouge", HexColor.RED.getDisplayName());
        assertEquals("bleu", HexColor.BLUE.getDisplayName());
        assertEquals("bleu", HexColor.RED.getOpposite().getDisplayName());
        assertEquals("rouge", HexColor.RED.getOpposite().getOpposite().getDisplayName());
    }
}
