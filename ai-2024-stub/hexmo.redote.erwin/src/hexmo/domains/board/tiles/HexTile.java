package hexmo.domains.board.tiles;

import hexmo.domains.player.HexColor;

/**
 * Represent a tile on the board
 * 
 * It-2-q2 : Dans l'optique de vérifier si une case est valide pour un joueur,
 * la case doit être au bord du plateau, ensuite on pourra vérifier si la case correspond à une case de 
 * son joueur ou d'un coin.
 * 
 * Etape 1 :
 *  Il faut vérifier le bord. Pour se faire il suffit de vérifier qu'aucune des composantes de la tuile n'est supérieur à la taille du plateau
 *  ou n'est inférieur à la taille négative du plateau. (Ex: q > 3 ou q < -3 alors la tuile n'est pas sur le bord du plateau)
 *  Exemple pour un plateau de rayon 3 et une tuile de composante [q: 3 r: -1 s: 1]
 *      q = 3 > 3 => faux, la tuile est sur le bord et ce même si r et s ne sont pas sur le bord
 *  Exemple pour un plateau de rayon 3 et une tuile de composante [q: 2 r: -2 s: -4]
 *      q = 2 < 3 => vrai, la tuile n'est pas sur le bord
 *
 * Etape 2 :
 *  Il faut s'assurer que le joueur n'est pas devant les cases (Qui ne sont pas des coins) du joueur opposé.
 *  Pour cela il suffit de vérifier si au moins l'une des compoantes (Q, R, S) est EGAL à la taille du plateau ou à la taille négative du plateau selon la couleur du joueur
 *  Exemple pour un plateau de rayon 3 et une tuile de composante [q: r: s: ]
 *      Si le joueur est rouge, alors il ne peut pas jouer sur les cases où q,r ou s = 3
 *      Si le joueur est bleu, alors il ne peut pas jouer sur les cases où q,r ou s = -3
 *  
 *  @see HexTile#isNotOnBorders(int)
 */
public class HexTile {

    private final AxialCoordinates coords;
    private HexColor color;

    /**
     * Create a copy of the given tile
     * @param original The tile to copy
     */
    public HexTile(HexTile original) {
        this.coords = original.coords;
        this.color = original.color;
    }

    /**
     * Create a new tile with the given coordinates and type
     * @param coords The coordinates of the tile
     * @param color The color of the tile
     * @throws IllegalArgumentException if coords is null
     * @throws IllegalArgumentException if color is null
     */
    public HexTile(AxialCoordinates coords, HexColor color) {
        if(coords == null) throw new IllegalArgumentException("Coordinates must be provided");
        if(color == null) throw new IllegalArgumentException("Color must be provided");
        this.coords = coords;
        this.color = color;
    }

    /**
     * Update the color of this tile
     * @param type The new tile type (Color)
     */
    public void setColor(HexColor type) {
        this.color = type;
    }

    /**
     * @return The current tile type (Color)
     */
    public HexColor getColor() {
        return this.color;
    }

    /**
     * Check if the tile has the given color
     * @param color The color to check
     * @return True if the tile has the given color
     */
    public boolean hasColor(HexColor color) {
        return this.color == color;
    }

    /**
     * @return True if the tile is avalbile to be claimed
     */
    public boolean isEmpty() {
        return this.color == HexColor.UNKNOWN;
    }

    /**
     * @return The coordinates of the tile
     */
    public AxialCoordinates getCoords() {
        return this.coords;
    }

    /**
     * @return The coordinates of the tile
     */
    public int getQ() {
        return this.coords.getQ();
    }

    /**
     * @return The coordinates of the tile
     */
    public int getR() {
        return this.coords.getR();
    }

    /**
     * @return The S component of the tile
     */
    public int getS() {
        return this.coords.getS();
    }

    /**
     * Check if at least one of the components is equals to the value
     * @param x The value to check
     * @return True if at least one of the components is equals to the value
     */
    public boolean contains(int x) {
        return this.getQ() == x || this.getR() == x || this.getS() == x;
    }

    /**
     * Check if the tile is not on the borders of the board
     * @param boardSize The size of the board
     * @return True if the tile is not on the borders of the board
     * @throws IllegalArgumentException if boardSize is negative
     */
    public boolean isNotOnBorders(int boardSize) {
        return this.getCoords().isNotOnBorders(boardSize);
    }
    
    @Override
    public String toString() {
        return String.format("Tile at [%s] with type %s", this.coords, this.color);
    }
}
