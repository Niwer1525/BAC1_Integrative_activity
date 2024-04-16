package hexmo.domains.board;

import java.util.List;

import com.tngtech.archunit.thirdparty.com.google.common.collect.Lists;

import hexmo.domains.board.tiles.AxialCoordinates;
import hexmo.domains.board.tiles.HexTile;
import hexmo.supervisors.commons.TileType;

/**
 * Represent a game board, containing all the tiles
 * 
 * It-1-q1 : 
 *  Plateau R=0 : Contiendra 1 case, la case de départ [0, 0] sera toujours contenue
 *  Plateau R=1 : Contiendra 7 cases au total, 6 de par le rayon et la case de départ [0, 0]
 *  Plateau R=2 : Contiendra 19 cases au total, 12 de par le rayon et la case de départ [0, 0]
 *  Plateau R=x :
 *      Généralison :
 *      La somme des r premiers nombres entiers se calcule ainsi : (x * (x + 1)) / 2
 *      Ensuite lon peut calculer notre hexagone en prenant en compte sa case centrale et 6 autre cases pour les cotés
 *      ce qui donne cette formue : 1 + 6 * (x * (x + 1)) / 2
 *      Vérifications :
 *            1 + 6 * (0 * (0 + 1)) / 2 = 1
 *            1 + 6 * (1 * (1 + 1)) / 2 = 7
 *            1 + 6 * (2 * (2 + 1)) / 2 = 19
 * 
 * It-1-q2 : Choix collection (interface + implémentation)
 * Dans un premier temps, j'ai éliminé les Sets, Queues et Trees car je n'ai nullement besoin d'un tri de plus, 
 * ma contrainte d'unicité est respecté car le traitement de l'ajout de cases est controllé.
 * Le choix de List face à Map me parait plus simple utiliser, je n'ai alors pas besoin de créer un nouvel objet
 * AxialCoordinates à chaque mouvement (Si j'avais par exemple choisi une Map<AxialCoordinates, HexTile>). Cependant celà requière
 * de parcourir toutes les tuiles à chaque mouvement.
 * 
 * L'implémentation choisie pour List est ArrayList car je n'ai n'ai pas besoin d'ordre spécifique
 * 
 * It-1-q3 : Composante "S"
 *  Inexistante ?
 */
public class HexBoard {
    private final List<HexTile> tiles;
    private HexTile activeTile = null;

    /**
     * Create a new board with the given size
     * @param boardSize The size of the board
     */
    public HexBoard(int boardSize) {
        this.tiles = Lists.newArrayList();

        /* Adjusted loop limits for creating a hexagon */
        for (int q = -boardSize; q <= boardSize; q++) {
            for (int r = Math.max(-boardSize, -q - boardSize); r <= Math.min(boardSize, -q + boardSize); r++) {
                AxialCoordinates axialCoords = new AxialCoordinates(q, r);
                HexTile tile = new HexTile(axialCoords, TileType.UNKNOWN);
                this.tiles.add(tile);
                if(q == 0 && r == 0) this.activeTile = tile; // Set the active tile to the default one (0, 0)
            }
        }
    }

    /**
     * This is called on player's movement(s), it will move to a the case in the <code>[dx, dy]</code> direction.
     * @param dx The delta X (In the display coords system)
     * @param dy The delta Y (In the display coords system)
     * @return The target tile, Or null if outside the board dimensions
     */
    public HexTile moveTo(int dx, int dy) {
        int dq = (int)(dx / (Math.sqrt(3) / 2.0));
        int dr = (int)(dy / (3.0 / 2.0)) + dy;

        for(HexTile tile : this.tiles) {
            if(tile.getQ() == this.activeTile.getQ() + dq && tile.getR() == this.activeTile.getR() + dr) {
                this.activeTile = tile;
                return tile;
            }
        }

        return null;
    }

    /**
     * @return All the tiles in this board
     */
    public List<HexTile> getTiles() {
        return List.copyOf(tiles);
    }

    /**
     * @return The amount of tiles contained in this board
     */
    public int getTilesCount() {
        return this.tiles.size();
    }

    /**
     * @return The active tile (Focused by the user)
     */
    public HexTile getActiveTile() {
        return this.activeTile;
    }

    @Override
    public String toString() {
        return String.format("HexBoard{tiles=%s, activeTile=%s}", tiles, activeTile);
    }
}
