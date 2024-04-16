package hexmo.domains;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hexmo.domains.board.tiles.HexTile;
import hexmo.supervisors.commons.TileType;

public class TestHexGame {

    /*
     * Test creating new game with board sizes
    */

    @Test
    public void test_newGameR0_WithFirstPlayer() {
        HexGame game = new HexGame(0);
        assertEquals(1, // This should be 1 since there is at least the center tile
            game.getBoard().getTilesCount()
        );
        assertNotNull(game.getTurnPlayer());
    }

    @Test
    public void test_newGameRAlgo() {
        HexGame game = new HexGame(1);
        assertEquals(7,
            game.getBoard().getTilesCount()
        );
        HexGame game2 = new HexGame(2);
        assertEquals(19,
            game2.getBoard().getTilesCount()
        );
    }

    @Test
    public void test_newGameR3_WithFirstPlayer() {
        HexGame game = new HexGame(3);
        assertEquals(37,
            game.getBoard().getTilesCount()
        );
        assertNotNull(game.getTurnPlayer());
    }

    @Test
    public void test_newGameR4_WithFirstPlayer() {
        HexGame game = new HexGame(4);
        assertEquals(61,
            game.getBoard().getTilesCount()
        );
        assertNotNull(game.getTurnPlayer());
    }

    @Test
    public void test_newGameR5_WithFirstPlayer() {
        HexGame game = new HexGame(5);
        assertEquals(91,
            game.getBoard().getTilesCount()
        );
        assertNotNull(game.getTurnPlayer());
    }

    /*
     * Current tile activation
     */

    @Test
    public void test_activateTile_withoutSwap() {
        HexGame game = new HexGame(3);
        HexTile tile = game.play(false);
        assertEquals(TileType.RED,
            tile.getTileType()
        );
    }

    @Test
    public void test_activateTile_withSwap() {
        HexGame game = new HexGame(3);
        HexTile tile = game.play(true);
        assertEquals(TileType.BLUE, tile.getTileType());
    }

    @Test
    public void test_activateTile_withMultipleSwap() {
        HexGame game = new HexGame(3);
        HexTile tile = game.play(true);
        tile = game.play(false);
        tile = game.play(true);
        tile = game.play(false);
        tile = game.play(true);
        assertEquals(TileType.BLUE, tile.getTileType());
    }

    @Test
    public void test_activateTile_Moved() {
        HexGame game = new HexGame(3);
        game.getBoard().moveTo(255, 0);

        /* 
         * This will should be 0 0 case since the "moveTo" 
         * should be unsuccessful...
        */
        HexTile tile = game.play(true);
        assertEquals(0, game.getBoard().getActiveTile().getQ());
        assertEquals(0, game.getBoard().getActiveTile().getR());
        assertEquals(TileType.BLUE, tile.getTileType()); // Should be the defaut color
    }

    @Test
    public void test_firstTurn() {
        HexGame game = new HexGame(3);
        assertTrue(game.isFirstTurn());
        
        game.getBoard().moveTo(0, 0);
        assertTrue(game.isFirstTurn());

        game.play(true);
        assertFalse(game.isFirstTurn());

        game.play(true);
        assertFalse(game.isFirstTurn());
    }

    @Test
    public void test_getMessages() {
        HexGame game = new HexGame(3);
        assertEquals(3, game.getGameMessages().length);

        /* Main tile */
        assertArrayEquals(new String[] { 
            "P1 (rouge) vs P2 (bleu)",
            "Au tour de P1 (rouge)",
            "Case active (q: 0 r: 0 0) Libre"
        }, game.getGameMessages());

        /* Move to [1, 0] */
        game.getBoard().moveTo(1, 0);
        assertArrayEquals(new String[] { 
            "P1 (rouge) vs P2 (bleu)",
            "Au tour de P1 (rouge)",
            "Case active (q: 1 r: 0 0) Libre"
        }, game.getGameMessages());

        /* Play and swap */
        game.play(true);
        assertArrayEquals(new String[] { 
            "P1 (bleu) vs P2 (rouge)",
            "Au tour de P2 (rouge)",
            "Case active (q: 1 r: 0 0) Bleu"
        }, game.getGameMessages());

        /* Move to [2, 0] */
        game.getBoard().moveTo(1, 0);
        assertArrayEquals(new String[] { 
            "P1 (bleu) vs P2 (rouge)",
            "Au tour de P2 (rouge)",
            "Case active (q: 2 r: 0 0) Libre"
        }, game.getGameMessages());

        /* Play and swap (Swap shouldn't affect */
        game.play(true);
        assertArrayEquals(new String[] { 
            "P1 (bleu) vs P2 (rouge)",
            "Au tour de P1 (bleu)",
            "Case active (q: 2 r: 0 0) Rouge"
        }, game.getGameMessages());
    }

    @Test
    public void test_toString() {
        HexGame game = new HexGame(3);
        assertEquals("Game: P1 (rouge) vs P2 (bleu)", game.toString());
        
        game.play(true);
        assertEquals("Game: P1 (bleu) vs P2 (rouge)", game.toString());
    }
}