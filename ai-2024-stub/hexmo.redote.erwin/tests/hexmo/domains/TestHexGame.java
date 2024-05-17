package hexmo.domains;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.lang.reflect.Field;
import java.util.Collection;

import org.junit.Test;

import hexmo.domains.board.HexBoard;
import hexmo.domains.board.TestHexBoard;
import hexmo.domains.board.tiles.HexTile;
import hexmo.domains.player.HexColor;

public class TestHexGame {

    /*
     * Test creating new game with board sizes
    */

    @Test
    public void test_newGameRNegative() {
        assertThrows(IllegalArgumentException.class, () ->{
            new HexGame(-22);
        });
    }
    
    @Test
    public void test_newGameR0_WithFirstPlayer() {
        HexGame game = new HexGame(0);
        assertEquals(1, // This should be 1 since there is at least the center tile
            game.getTiles().size()
        );
        assertNotNull(game.getTurnPlayer());
    }

    @Test
    public void test_newGameRayonAlgo() {
        HexGame game = new HexGame(1);
        assertEquals(7,
            game.getTiles().size()
        );
        HexGame game2 = new HexGame(2);
        assertEquals(19,
            game2.getTiles().size()
        );
    }

    @Test
    public void test_newGameR3_WithFirstPlayer() {
        HexGame game = new HexGame(3);
        assertEquals(37,
            game.getTiles().size()
        );
        assertNotNull(game.getTurnPlayer());
    }

    @Test
    public void test_newGameR4_WithFirstPlayer() {
        HexGame game = new HexGame(4);
        assertEquals(61,
            game.getTiles().size()
        );
        assertNotNull(game.getTurnPlayer());
    }

    @Test
    public void test_newGameR5_WithFirstPlayer() {
        HexGame game = new HexGame(5);
        assertEquals(91,
            game.getTiles().size()
        );
        assertNotNull(game.getTurnPlayer());
    }

    @Test
    public void test_firstTurn() {
        HexGame game = new HexGame(3);
        assertTrue(game.isFirstTurn());
        
        game.moveTo(0, 0);
        assertTrue(game.isFirstTurn());

        game.play(true);
        assertFalse(game.isFirstTurn());

        game.play(true);
        assertFalse(game.isFirstTurn());
    }

    @Test
    public void test_getMessagesDefault() {
        HexGame game = new HexGame(3);
        assertEquals(3, game.getGameMessages().length);

        /* Main tile */
        assertArrayEquals(new String[] {
            "P1 (rouge) vs P2 (bleu)",
            "Au tour de P1 (rouge)",
            "Case active (q: 0 r: 0 0) Libre"
        }, game.getGameMessages());
    }

    @Test
    public void test_getMessagesMoves() {
        HexGame game = new HexGame(3);
        
        /* Move to [1, 0] */
        game.moveTo(1, 0);
        assertArrayEquals(new String[] { 
            "P1 (rouge) vs P2 (bleu)",
            "Au tour de P1 (rouge)",
            "Case active (q: 1 r: 0 -1) Libre"
        }, game.getGameMessages());

        /* Play and swap */
        game.play(true);
        assertArrayEquals(new String[] { 
            "P1 (bleu) vs P2 (rouge)",
            "Au tour de P1 (bleu)",
            "Case active (q: 1 r: 0 -1) Rouge"
        }, game.getGameMessages());


        /* Move to [2, 0] */
        game.moveTo(1, 0);
        assertArrayEquals(new String[] { 
            "P1 (bleu) vs P2 (rouge)",
            "Au tour de P1 (bleu)",
            "Case active (q: 2 r: 0 -2) Libre"
        }, game.getGameMessages());

        /* Play and swap (Swap shouldn't affect */
        game.play(true);
        assertArrayEquals(new String[] {
            "P1 (bleu) vs P2 (rouge)",
            "Au tour de P2 (rouge)",
            "Case active (q: 2 r: 0 -2) Bleu"
        }, game.getGameMessages());
    }

    @Test
    public void test_toString() {
        HexGame game = new HexGame(3);
        assertEquals("Game: P1 (rouge) vs P2 (bleu)", game.toString());
        
        game.play(true);
        assertEquals("Game: P1 (bleu) vs P2 (rouge)", game.toString());
    }

    /* 
     * Test playing
     * */

    @Test
    public void test_moveToAndPlay() {
        HexGame game = new HexGame(3);

        int errorCode = game.play(false);
        assertEquals(HexGame.NO_PLAY_ERROR, errorCode);

        HexTile tile = move(game, 0, -1);
        errorCode = game.play(false);
        assertEquals(HexColor.BLUE, tile.getColor());

        tile = move(game, -1, 2);
        errorCode = game.play(false);
        assertEquals(HexColor.RED, tile.getColor());

        tile = move(game, -1, -1);
        errorCode = game.play(false);
        assertEquals(HexColor.BLUE, tile.getColor());
    }

    @Test
    public void test_moveToAndPlay_ErrorsTileAlreadyPlayed() {
        HexGame game = new HexGame(3);

        /* Move to random tile and play, no errors */
        game.moveTo(-1, 0);
        game.moveTo(-1, -1);
        int errorCode = game.play(false);
        assertEquals(HexGame.NO_PLAY_ERROR, errorCode);

        /* Play again at the same pos, should say that an error occured */
        game.moveTo(1, 0);
        errorCode = game.play(false);
        assertEquals(HexGame.NO_PLAY_ERROR, errorCode);

        game.moveTo(-1, 0);
        errorCode = game.play(false);
        assertEquals(HexGame.ERROR_TILE_CLAIMED, errorCode);
    }
    
    @Test
    public void test_moveToAndPlay_ErrorsTileNotValid_cornersAndBorders() {
        HexGame game = new HexGame(3);

        /* Move to blue border */
        game.moveTo(1, 0);
        game.moveTo(1, 1);
        int errorCode = game.play(false);
        assertEquals(HexGame.ERROR_TILE_NOT_VALID, errorCode);

        /* Move to a corner */
        game.moveTo(1, -1);
        errorCode = game.play(false);
        assertEquals(HexGame.NO_PLAY_ERROR, errorCode);
    }

    @Test
    public void test_updateHelperTiles() {
        HexGame game = new HexGame(3);

        Collection<HexTile> helperTiles = game.updateHelper();
        assertEquals(0, helperTiles.size());
    }

    public static HexTile move(HexGame game, int q, int r) {
        // Methode pour me rendre la vie plus facile en utilsant la réflection java 
        // (Seul moyen que j'ai d'obtenir les attributs privés ainsi que les méthodes privées d'une classe sans faire râler PMD)
        try {
            Field boardField = HexGame.class.getDeclaredField("board");
            boardField.setAccessible(true); // Rendre accessible
            var board = (HexBoard) boardField.get(game); // Récupérer avec l'instance
            
            return TestHexBoard.move(board, q, r);
        } catch (Exception e) {
            e.printStackTrace();
            // A priori je contiens mes tests donc ceci ne devrait pas se produire.
        }

        return game.moveTo(0, 0); // Au cas ou je retourne la tuile active
    }
}