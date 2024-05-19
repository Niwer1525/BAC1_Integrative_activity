package hexmo.domains.board.path;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import hexmo.domains.board.HexBoard;
import hexmo.domains.player.HexColor;

/**
 * It-3-q3 (Impl + Inter):
 *  voisinsAVisiter (toVisit) est une file d'attente qui contient les cases voisines de la case actuelle. J'ai choisis une Queue 
 *  car elle permet de gérer les éléments de manière FIFO (First In First Out). Cela permet de traiter les cases une par une et de les retirer de la file une fois traitées.
 *  
 *  J'ai choisis d'utiliser LinkedList car elle permet d'ajouter et de retirer des éléments en temps constant (O(1)) (que ce soit en tête ou en queue de liste).
 *  De plus par rapport à une ArrayDeque, LinkedList est légèrement plus adaptée pour traiter des listes de taille variable.
 *  J'ai également écarté PriorityQueue car je n'ai nullement besoin de trier les éléments de la file. (Ce type de collection pourrait être avantageux dans d'autres
 *  petits jeux 2D où il y a des cases plus difficiles a parcourir qeu d'autres)
 * 
 * Les méthodes que j'utilises pour voisinsAVisiter (toVisit) sont les suivantes:
 *  @see AbstractCollection#isEmpty
 *    - Permet de vérifier si la file est vide => O(1)
 *  @see Queue#poll
 *   - Permet de récupérer et de retirer le premier élément de la file => O(1) car on n'accesse qu'à la tête de la liste
 *  @see AbstractCollection#add
 *    - Permet d'ajouter un élément à la file => O(1) car on ajoute à la fin de la liste
 */
public class PathFinder {

    private final HexBoard board;

    public PathFinder(HexBoard board) {
        this.board = board;
    }

    /**
     * <h2>Breadth First Search</h2>
     * Check if the current player has won
     * @param color The color of the player
     * @param boardSize The size of the board
     * @return The winning path
     * 
     * It-3-q1: 
     * Precondition:
     * - La couleur doit être une valeur d'énumération valide
     * - La valeur de boardSize (Taille du plateau) doit être supérieure à 0
     * 
     * Postcondition:
     * - Si un chemin est trouvé, la méthode renvoie la longueur du chemin (Ce sera toujours le chemin le plus court)
     * - Si aucuns chemins n'est trouvés, la méthode renvoie -1
     * 
     * It-3-q2:
     * La CTT (Complexité Temporelle Théorique) de cette méthode est de O(n) où n correspond au nombre total de cases du plateau * 6 (car chaque case a 6 voisins)
     */
    public int findPath(HexColor color, int boardSize) {
        if(color == null) throw new IllegalArgumentException("Color must be a valid enumeration value");
        if(boardSize <= 0) throw new IllegalArgumentException("Board size must be greater than 0");

        Collection<PathCoords> coloredTiles = this.board.getTilesCoordinatesForColor(color); // O(n) ou n correspond au nombre total de cases
        Collection<PathCoords> visited = new HashSet<>();
        Queue<PathCoords> toVisit = new LinkedList<>();

        this.getBorderTiles(visited, toVisit, coloredTiles, color, boardSize);
        return lookupTiles(visited, toVisit, coloredTiles);
    }

    private void getBorderTiles(Collection<PathCoords> visited, Queue<PathCoords> toVisit, Collection<PathCoords> coloredTiles, HexColor color, int boardSize) {
        /* Get tiles on borders (distance 0 or with one of it's component equals to the board rayon) */
        for(var entry : coloredTiles) { // O(n) ou n correspond au nombre total de cases colorées
            if(entry.getCoords().isNotOnBorders(boardSize)) continue; // If we're inside or outside the board, we don't need to add the tile

            entry.setBorder(boardSize, color); // Set the border of the tile
            visited.add(entry); // Add the tile to the seen list
            toVisit.add(entry); // Add the tile to the queue
        }
    }

    private int lookupTiles(Collection<PathCoords> visited, Queue<PathCoords> toVisit, Collection<PathCoords> coloredTiles) { // O(n)
        while(!toVisit.isEmpty()) { // O(n) ou n correspond au nombre total de cases colorées
            var currentCoords = toVisit.poll(); // Get the first element of the queue (And by the way remove it from the queue)
            for(var neighbor : currentCoords.getNeighbors()) { // O(6) = O(1) car on a toujours 6 voisins
                if(!coloredTiles.contains(neighbor)) // If the tile isn't valid or has already been visited, then we continue
                    continue;
                
                if(visited.contains(neighbor)) { // PathCoords: q: 0 r: -2 s: 2, Length: 25, Border: TOP_LEFT / PathCoords: q: 0 r: -3 s: 3, Length: 0, Border: TOP_LEFT
                    var visitedCoords = visited.stream().filter(c -> c.equals(neighbor)).findFirst().orElse(null);
                    if(visitedCoords != null && !currentCoords.isFromTheSameBorderAs(visitedCoords)) {
                        // If we've already seen the tile and it's not the same border, then we've found the path
                        return currentCoords.getLength() + visitedCoords.getLength() + 2;
                    }
                        
                    continue;
                }
                
                neighbor.setBorder(currentCoords.getBorder());
                neighbor.setLength(currentCoords.getLength() + 1);
                visited.add(neighbor); // Add the current tile to the seen list
                toVisit.add(neighbor); // Add neighbors to the queue
            }
        }
        return -1;
    }
}
