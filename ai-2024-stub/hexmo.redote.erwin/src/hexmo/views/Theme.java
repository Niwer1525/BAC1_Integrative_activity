package hexmo.views;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.UIManager;

import hexmo.supervisors.commons.TileType;

/**
 * Définit l'aspect général des vues.
 * */
public final class Theme {
	static {
		 try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			 Logger.getLogger("errors").severe(e.getMessage());
		}
	}
	
	//Dimensions
	public static final int WINDOW_WIDTH = 1024;
	public static final int WINDOW_HEIGHT = 1024;
	public static final int PANEL_WIDTH = 1024;
	public static final int PANEL_HEIGHT = 1024;
	public static final int TILE_SIZE = 64;
	
	//Police de caractères
	public static final Font ITEM_FONT = new Font("Ravie", Font.PLAIN, 20);
	public static final Font SUBTITLE_FONT = new Font("Segoe", Font.PLAIN, 16);
	public static final Font NORMAL_FONT = new Font("Segoe", Font.PLAIN, 12);
	
	//Couleurs
	public static final Color BACKGROUND_COLOR = Color.decode("#cff2fc");
	public static final Color PRIMARY_COLOR = Color.decode("#1f241e");
	public static final Color SECONDARY_COLOR = Color.decode("#231e24");
	
	public static final Color PRIMARY_COLORA =  new Color(12, 14, 11, 200);
	public static final Color SECONDARY_COLORA =  new Color(13, 11, 14, 200);

	public static final Color RED = Color.decode("#FF0000");
	public static final Color BLUE = Color.decode("#0000FF");

	public static final Color UNKNOWN = Color.decode("#fcefcf");
	/**
	 * Définit la couleur des cases
	 * */
	public static final Map<TileType, Color> TILES = Map.of(
			TileType.UNKNOWN, UNKNOWN,
			TileType.RED, RED,
			TileType.BLUE, BLUE,
			TileType.HIGHLIGHT, SECONDARY_COLOR
	);
	
	/**
	 * Détermine la couleur du texte à afficher pour la couleur {@code background}.
	 * Retourne la couleur noir si {@code background == null}.
	 * */
	public static Color frontColorFor(Color background) {
		if(PRIMARY_COLOR == background || SECONDARY_COLOR == background) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}
}
