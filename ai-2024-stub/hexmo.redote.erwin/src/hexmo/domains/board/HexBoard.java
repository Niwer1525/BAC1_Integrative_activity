package hexmo.domains.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hexmo.domains.board.tiles.AxialCoordinates;
import hexmo.domains.board.tiles.HexTile;
import hexmo.domains.player.HexColor;

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
 * Le choix de entre List et Map est plus difficile, car les deux peuvent être utilisés pour stocker des données
 * Cependant la Map est plus adaptée pour stocker des données de type clé-valeur, et ici il peut être intéressant de récupérer une tuile par ses coordonnées
 * De plus la map permet d'éviter le parcours de la liste pour retrouver une tuile, ce qui est plus efficace
 * N.B : Le choix de la HashMap est plus approprié qu'un TreeMap car je n'ai pas besoin de trier les tuiles
 * 
 * It-1-q3 : Composante "S"
 *  Inexistante ?
 */
public class HexBoard {
    private final Map<AxialCoordinates, HexTile> tiles;
    private final List<HexTile> previousHelperTiles;
    private HexTile activeTile;

    /**
     * Create a new board with the given size
     * @param boardSize The size of the board
     * @throws IllegalArgumentException if boardSize is negative
     */
    public HexBoard(int boardSize) {
        if(boardSize < 0) throw new IllegalArgumentException("Board size must be positive");
        this.tiles = new HashMap<>();
        this.previousHelperTiles = new ArrayList<>();

        /* Adjusted loop limits for creating a hexagon */
        for (int q = -boardSize; q <= boardSize; q++) {
            for (int r = Math.max(-boardSize, -q - boardSize); r <= Math.min(boardSize, -q + boardSize); r++) {
                AxialCoordinates axialCoords = new AxialCoordinates(q, r);
                HexTile tile = new HexTile(axialCoords, HexColor.UNKNOWN);
                this.tiles.put(axialCoords, tile);
            }
        }

        /* Get the first active tile, wich is [0 0] */
        this.activeTile = this.getTileAt(0, 0);
    }

    /**
     * This is called on player's movement(s), it will move to a the case in the <code>[dx, dy]</code> direction.
     * @param dx The delta X (In the display coords system)
     * @param dy The delta Y (In the display coords system)
     * @return The target tile, Or null if outside the board dimensions
     */
    public HexTile moveTo(int dx, int dy) {
        AxialCoordinates coords = this.activeTile.getCoords().add(AxialCoordinates.fromXYCoords(dx, dy));
        HexTile tile = this.getTileAt(coords.getQ(), coords.getR());
        if(tile == null) return this.activeTile;
        
        this.activeTile = tile;
        return tile;
    }

    /**
     * @return A copy of all the tiles in this board
     */
    public Collection<HexTile> getTiles() {
        Collection<HexTile> tiles = new ArrayList<>(this.tiles.size());
        for (HexTile tile : this.tiles.values())
            tiles.add(tile);
        return tiles;
    }

    /**
     * @return A copy of the active tile (Focused by the user)
     */
    public HexTile getActiveTile() {
        return this.activeTile;
    }

    /**
     * Check if the active tile is claimed or not.
     * And it's check if this is the first turn and if the tile is not claimed, if so, then it return <code>False</code>. This allow swapping
     * @param firstTurn If it's the first turn set to True, False otherwise
     * @return True if the active tile is claimed, False otherwise
     */
    public boolean isActiveTileClaimed(boolean firstTurn) {
        return this.activeTile == null || (!firstTurn && !this.activeTile.isEmpty());
    }

    @Override
    public String toString() {
        return String.format("HexBoard{tiles=%s, activeTile=%s}", tiles.size(), activeTile);
    }

    /**
     * @return The previous helper tiles (The tiles that were previously highlighted) wich is useful for clearing them
     */
    public Collection<HexTile> getPreviousHelperTiles() {
        Collection<HexTile> tiles = new ArrayList<>(this.previousHelperTiles.size());
        for (HexTile tile : this.previousHelperTiles)
            tiles.add(tile);
        return tiles;
    }

    /**
     * Update the helper tiles
     * @return The helper tiles
     */
    public Collection<HexTile> updateHelper(HexColor activePlayerColor) {
        Collection<HexTile> tiles = this.updateHelper(activePlayerColor, this.activeTile.getCoords());
        this.previousHelperTiles.clear(); // Clear the previous helper tiles
        this.previousHelperTiles.addAll(tiles); // Add the new helper tiles
        return tiles;
    }
    
    private Collection<HexTile> updateHelper(HexColor activePlayerColor, AxialCoordinates centerCoords) {
        Collection<HexTile> helperTiles = new ArrayList<>();

        /* Get 6 diagonals */
        for(AxialCoordinates diagCoords : centerCoords.getDiagonals()) {
            HexTile diagTile = this.getTileAt(diagCoords.getQ(), diagCoords.getR());
            if(diagTile == null || !diagTile.hasColor(activePlayerColor)) continue;

            /* Get common neighbors */
            for(AxialCoordinates neighborCoords : centerCoords.getCommonNeighborsWith(diagCoords)) {
                HexTile helperTile = this.getTileAt(neighborCoords.getQ(), neighborCoords.getR());
                if(helperTile == null || !helperTile.isEmpty()) break; // If one or more neighbors are not empty, then we don't need to check the others
                helperTiles.add(helperTile);
            }

            /* Continue in diagonal until we reach the border */
            // Collection<HexTile> x = this.updateHelper(activePlayerColor, diagCoords);
            // System.out.println(x);
            // helperTiles.addAll(x);
        }
        return helperTiles;
    }

    private HexTile getTileAt(int q, int r) {
        return this.tiles.get(new AxialCoordinates(q, r));
    }
}