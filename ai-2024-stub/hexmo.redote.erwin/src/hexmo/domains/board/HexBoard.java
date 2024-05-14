package hexmo.domains.board;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

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
 * It-1-q3 : Composante "S" correspond à : -q - r
 *  Car la somme des trois composantes doit être égale à 0, donc S = -q - r
 *  Exemple : [q: 1 r: 2 s: -3] => 1 + 2 + (-3) = 0
 *  Exemple : [q: 0 r: 0 s: 0] => 0 + 0 + 0 = 0
 * 
 * It-2-q1 : Conditions de cases formant un pont
 *  Les 4 cases formant un pont doivent chacune respecter des contitions (N.B : Evidemment les cases doivent respecter les conditions de base, c-à-d être sur le plateau):
 *    La case en diagonale de celle que l'on veut vérifier doit être de la même couleur
 *    Les deux cases voisines qui sont communes aux cases diagonale doivent être de la même vide (Ni bleu, ni rouge)
 * 
 * It-3-q3 : Choix collection (interface + implémentation) pour les cases voisines
 *  J'ai choisi d'utiliser un set pour stocker les cases voisines car je n'ai pas besoin de clé-valeur (Map), et je n'ai pas besoin de trier les cases (TreeSet)
 *  Je n'ai pas non plus besoin de l'ordre d'insertion (Queue), j'ai cependant besoin d'une contrainte d'unicité pour éviter de me retrouver avec plusieurs fois la même tuile
 *  donc le choix le plus adapté est le Set.
 * 
 *  Quand à l'implémentation, j'ai choisi HashSet car il est plus rapide que le TreeSet, et je n'ai pas besoin de trier les cases
 *   Les CTT des fonctions que j'utilises :
 *      add() : O(1) Il s'agit d'une map dans laquel l'on "put" un élément
 *      addAll() : O(n) Il s'agit d'une map dans laquel l'on "put" plusieurs éléments
 *      retainAll() : O(n) Il effectue une boucle sur ses propres éléments pour vérifier si ils sont contenus dans l'autre collection
 *  @see AbstractCollection#retainAll
 */
public class HexBoard {
    private final Map<AxialCoordinates, HexTile> tiles;
    private HexTile activeTile;

    /**
     * Create a new board with the given size
     * @param boardSize The size of the board
     * @throws IllegalArgumentException if boardSize is negative
     */
    public HexBoard(int boardSize) {
        if(boardSize < 0) throw new IllegalArgumentException("Board size must be positive");
        this.tiles = new HashMap<>();

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
     * Update the helper tiles from the center of the board [0, 0]
     * @return The helper tiles
     */
    public Collection<HexTile> updateHelper(HexColor activePlayerColor) {
        return this.updateHelper(activePlayerColor, new AxialCoordinates(0, 0), null);
    }
    
    /*
     * Il s'agit alors d'un O(n) ou n correspond au nombre de cases qui seront retournées par la récursion de cette fonction.
     */
    private Collection<HexTile> updateHelper(HexColor activePlayerColor, AxialCoordinates centerCoords, AxialCoordinates previousCoordinates) {
        Collection<HexTile> helperTiles = new HashSet<>();

        /* Get 6 diagonals */
        for(AxialCoordinates diagCoords : centerCoords.getDiagonals()) { // O(1), 6 iterations
            if(diagCoords.equals(previousCoordinates))
                continue; // Prevent from doing the calculations again
            if(!tileExistAndIsUseable(diagCoords, activePlayerColor))
                continue; // Prevent from continuing if the tile is not the same color as the player or if the tile is null

            /* Get common neighbors */
            handleNeighbors(centerCoords, diagCoords, activePlayerColor, helperTiles);
            
            /* Continue in diagonal until we reach the border */
            helperTiles.addAll(this.updateHelper(activePlayerColor, diagCoords, centerCoords)); // O(n) ou n correspond au nombre qui sera retourné par la récurssion de cette fonction.
        }
        return helperTiles;
    }

    private boolean tileExistAndIsUseable(AxialCoordinates coords, HexColor color) { // O(1)
        HexTile tile = this.getTileAt(coords.getQ(), coords.getR());
        return tile != null && tile.hasColor(color);
    }

    private void handleNeighbors(AxialCoordinates centerCoords, AxialCoordinates coords, HexColor activePlayerColor, Collection<HexTile> helperTiles) {
        if(!tileExistAndIsUseable(centerCoords, activePlayerColor)) return;

        Collection<HexTile> commonNeighbors = new HashSet<>(2);
        for(AxialCoordinates neighborCoords : centerCoords.getCommonNeighborsWith(coords)) { // O(1) car 6 itérations pour les voisins et donc les voisins commun resetera à 6 car retailAll() est en O(n) et n=6
            HexTile neighborTile = this.getTileAt(neighborCoords.getQ(), neighborCoords.getR());
            if(neighborTile == null || !neighborTile.isEmpty()) {
                commonNeighbors.clear();
                break; // If one or more neighbors are not empty, then we don't need to check the others
            }

            commonNeighbors.add(neighborTile); // O(1)
        }
        helperTiles.addAll(commonNeighbors); // O(1)
    }

    private Map<AxialCoordinates, HexTile> getTilesCoordinatesByColor(HexColor color) {
        Map<AxialCoordinates, HexTile> coloredTiles = new HashMap<>();

        for(Entry<AxialCoordinates, HexTile> entry : this.tiles.entrySet()) {
            if (entry.getValue().hasColor(color)) coloredTiles.put(entry.getKey(), entry.getValue());
        }

        return coloredTiles;
    }

    /**
     * Check if the current player has won
     * @param color The color of the player
     * @param boardSize The size of the board
     * @return The winning path
     */
    public Collection<AxialCoordinates> checkWin(HexColor color, int boardSize) {
        Map<AxialCoordinates, HexTile> coloredTiles = this.getTilesCoordinatesByColor(color);
        Collection<AxialCoordinates> seen = new HashSet<>();
        Queue<AxialCoordinates> neighborsToVisit = new LinkedList<>();

        /* Get tiles on borders (distance 0 or with one of it's component equals to the board rayon) */
        for(Entry<AxialCoordinates, HexTile> entry : coloredTiles.entrySet()) {
            if(entry.getValue().isNotOnBorders(boardSize)) continue; // If we're inside or outside the board, we don't need to add the tile
            neighborsToVisit.add(entry.getKey()); // Add the tile to the queue
        }

        while(!neighborsToVisit.isEmpty()) {
            AxialCoordinates currentCoords = neighborsToVisit.poll(); // Get the first element of the queue
            HexTile currentTile = coloredTiles.get(currentCoords); // Try to get the tile at the current coordinates.
            
            if(currentTile == null || seen.contains(currentCoords))
                continue; // Prevent continuing if the tile is null or if the tile has been already seen

            seen.add(currentCoords); // Add the current tile to the seen list

            /* Check if we reached the other side */
            // if(currentTile.contains(boardSize))
            // //     return seen; // If we reached the other side, then we return the path

            /* Add neighbors to the queue */
            neighborsToVisit.addAll(currentCoords.getNeighbors());
        }

        return seen;
    }
    
    private HexTile getTileAt(int q, int r) { // O(1)
        return this.tiles.get(new AxialCoordinates(q, r));
    }
}