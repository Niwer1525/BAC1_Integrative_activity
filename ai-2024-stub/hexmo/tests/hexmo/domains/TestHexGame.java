package hexmo.domains;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        HexTile tile = game.play();
        assertEquals(TileType.RED,
            tile.getTileType()
        );

        HexGame game2 = new HexGame(3);
        HexTile tile2 = game2.play();
        TileType type2 = game2.onFirstTurn(tile2, tile2.getTileType(), false);
        assertEquals(TileType.RED, type2);
    }

    @Test
    public void test_activateTile_withSwap() {
        HexGame game = new HexGame(3);
        HexTile tile = game.play();
    TileType type = game.onFirstTurn(tile, tile.getTileType(), true);
        assertEquals(TileType.BLUE, type);
    }

    @Test
    public void test_activateTile_WithMultipleSwap() {
        HexGame game = new HexGame(3);
        HexTile tile = game.play();
        TileType type = game.onFirstTurn(tile, tile.getTileType(), true);
        type = game.onFirstTurn(tile, type, true);
        type = game.onFirstTurn(tile, type, true);
        type = game.onFirstTurn(tile, type, true);
        assertEquals(TileType.BLUE, type);
    }

    @Test
    public void test_activateTile_Moved() {
        HexGame game = new HexGame(3);
        game.getBoard().moveTo(255, 0);

        /* 
         * This will change the color of the 0 0 case since the "moveTo" 
         * should be unsuccessful...
        */
        HexTile tile = game.play();
        TileType type = game.onFirstTurn(tile, tile.getTileType(), true);
        assertEquals(0, game.getBoard().getActiveTile().getQ());
        assertEquals(0, game.getBoard().getActiveTile().getR());
        assertEquals(TileType.BLUE, type);
    }
}
