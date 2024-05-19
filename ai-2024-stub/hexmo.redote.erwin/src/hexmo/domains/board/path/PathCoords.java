package hexmo.domains.board.path;

import java.util.Set;

import com.tngtech.archunit.thirdparty.com.google.common.collect.Sets;

import hexmo.domains.board.EnumBorder;
import hexmo.domains.board.tiles.AxialCoordinates;
import hexmo.domains.player.HexColor;

public class PathCoords {

    private final AxialCoordinates coords;
    private int length;
    private EnumBorder border = EnumBorder.UNKNOWN;

    public PathCoords(AxialCoordinates coords, int length) {
        if(coords == null) throw new IllegalArgumentException("Coords must not be null");
        if(length < 0) throw new IllegalArgumentException("Length must be positive");
        this.coords = coords;
        this.length = length;
    }

    public AxialCoordinates getCoords() {
        return coords;
    }

    /** 
     * Set the border from where this coords are from
     * @param border The border value
     */
    public void setBorder(EnumBorder border) {
        if(this.border == null || this.border == EnumBorder.UNKNOWN)
            this.border = border;
    }

    /*
     * Set the border from where this coords are from
     */
    public void setBorder(int boardSize, HexColor color) {
        this.setBorder(this.coords.getBorderForCoords(boardSize, color));
    }

    /**
     * @return The border from where this tile comes from
     */
    public EnumBorder getBorder() {
        return this.border;
    }

    /**
     * Check if the border is the same as the given one
     * @param coords The path coord to compare
     * @return true if the border is the same
     */
    public boolean isFromTheSameBorderAs(PathCoords coords) {
        return this.getBorder() == coords.getBorder();
    }

    /**
     * Set the size of the path
     * @param length The new size of the path
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return The size of the path
     */
    public int getLength() {
        return length;
    }

    /**
     * Get the neighbors of the current path coords
     * @return The neighbors of the current path coords (Calculated from the axial coords) with length 0
     */
    public Set<PathCoords> getNeighbors() {
        Set<PathCoords> pathCoords = Sets.newHashSet();

        for(AxialCoordinates coords : this.coords.getNeighbors())
            pathCoords.add(new PathCoords(coords, 0));

        return pathCoords;
    }

    @Override
    public String toString() {
        return String.format("PathCoords: %s, Length: %d, Border: %s", this.coords, this.length, this.border);
    }

    @Override
    public int hashCode() {
        return this.coords.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;

        PathCoords path = (PathCoords) obj;
        return this.coords.equals(path.coords);
    }
}
