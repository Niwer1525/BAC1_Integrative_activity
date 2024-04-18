package hexmo.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHexGameFactory {

    @Test
    public void test_createHexGameFactory_gameNotCreated() {
        IHexGameFactory factory = new HexGameFactory();

        assertEquals("HexGameFactory{game=null}", factory.toString());
    }

    @Test
    public void test_createHexGameFactory_gameCreated() {
        IHexGameFactory factory = new HexGameFactory();
        factory.startNewGame(25);
        
        assertEquals("HexGameFactory{game=Game: P1 (rouge) vs P2 (bleu)}", factory.toString());
    }

    @Test
    public void test_getGame_gameNotCreated() {
        IHexGameFactory factory = new HexGameFactory();
        
        assertEquals(null, factory.getCurrentGame());
    }

    @Test
    public void test_getGame_gameCreated() {
        IHexGameFactory factory = new HexGameFactory();
        factory.startNewGame(25);
        
        assertEquals("Game: P1 (rouge) vs P2 (bleu)", factory.getCurrentGame().toString());
    }
}