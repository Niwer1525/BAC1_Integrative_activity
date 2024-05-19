package hexmo.domains.board.stats;

import hexmo.domains.board.HexBoard;
import hexmo.domains.player.HexPlayer;

import java.util.HashMap;
import java.util.Map;

public class HexStats {

    private final HexBoard board;
    private final int minTilesToWin;

    private HexPlayer winner;
    private HexPlayer loser;
    private int pathDistance;

    private static Map<HexPlayer, Double> playerScores = new HashMap<>();
    private static int totalBoardFillRate = 0;
    private boolean isFirstCalculation = true;

    public HexStats(int boardSize, HexBoard board) {
        this.board = board;
        this.minTilesToWin = boardSize + 2; // +2 since we've excepted the center tiles of the opposite colors
    }

    /**
     * Calculate the stats of the game
     * @param winner The player that won
     * @param loser The player that lost
     * @param pathDistance The distance of winner's path (Number of tiles in the path)
     * @throws IllegalArgumentException If the path distance is negative
     */
    public void calculateStats(HexPlayer winner, HexPlayer loser, int pathDistance) {
        if(pathDistance == -1) throw new IllegalArgumentException("Path distance cannot be negative");
        this.winner = winner;
        this.loser = loser;
        this.pathDistance = pathDistance;

        double winnerScore = Math.max(1.0, minTilesToWin - (pathDistance - minTilesToWin));
        double loserScore = Math.max(0.0, (minTilesToWin - (pathDistance - minTilesToWin)) / 2.0);

        playerScores.put(winner, playerScores.getOrDefault(winner, 0.0) + winnerScore);
        playerScores.put(loser, playerScores.getOrDefault(loser, 0.0) + loserScore);
        totalBoardFillRate += Math.round((double) this.board.getOccupiedTiles() / (double) this.board.getTiles().size() * 100.0);
        if(isFirstCalculation)
            isFirstCalculation = false;
        else
            totalBoardFillRate /= 2;
    }

    /**
     * Get the winner of the game
     * @return The formatted winner string
     */
    public String getWinner() {
        return String.format("Vainqueur est %s (%s)", this.winner.getName(), this.winner.getColor().getDisplayName());
    }

    /**
     * Get the winner's score
     * @return The formatted winner score string
     */
    public String getWinnerScore() {
        final double winnerScore = Math.max(1.0, minTilesToWin - (pathDistance - minTilesToWin));
        return String.format("Score de %s %.2f", this.winner.getName(), winnerScore);
    }

    /**
     * Get the loser's score
     * @return The formatted loser score string
     */
    public String getLoserScore() {
        final double loserScore = Math.max(0.0, (minTilesToWin - (pathDistance - minTilesToWin)) / 2.0);
        return String.format("Score de %s %.2f", this.loser.getName(), loserScore);
    }

    /**
     * Get the board fill rate
     * @return The formatted board fill rate string
     */
    public String getBoardFillRate() {
        final double boardFillRate = (double) this.board.getOccupiedTiles() / (double) this.board.getTiles().size() * 100.0;
        return String.format("Taux de remplissage moyen %s", Math.round(boardFillRate)+"%");
    }

    /**
     * Get the average score of the winner
     * @param player The player 1
     * @return The formatted average score of the winner string
     */
    public String getPlayerTotalScore(HexPlayer player) {
        return String.format("Score moyen de %s %.2f", player.getName(), playerScores.get(player));
    }

    /**
     * Get the average board fill rate
     * @return The formatted average board fill rate string
     */
    public String getAverageBoardFillRate() {
        return String.format("Taux de remplissage moyen %s", Math.round(totalBoardFillRate)+"%");
    }
}
