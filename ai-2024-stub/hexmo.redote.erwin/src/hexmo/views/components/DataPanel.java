package hexmo.views.components;

import java.awt.*;
import java.util.ArrayList;

import hexmo.views.Theme;
import org.helmo.swing.engine.GameComponent;

/**
 * Décrit un panneau affichant du texte.
 * <p>
 *     Ce panneau peut avoir un titre et un nombre arbitraire de ligne.
 * </p>
 * */
public class DataPanel extends GameComponent {
	public static final int GAP = 5;
	private final Color background;
	private final java.util.List<String> lines = new ArrayList<>();
	private final String title;

	/**
	 * Construit un panneau dôté d'un titre, d'une position et d'une largeur connues.
	 * */
	public DataPanel(String title, int left, int top, int width) {
		super(left, top, width, 0);
		this.title = (title == null || title.isBlank()) ? "" : title;
		background = Theme.SECONDARY_COLORA;
	}
	
	/**
	 * @return the background
	 */
	public Color getBackground() {
		return background;
	}

	@Override
	public void repaint(Graphics2D painter) {
		int titleHeight =  getRenderedTileHeight(painter);
		
		painter.setFont(Theme.NORMAL_FONT);
		var lineFontMetrics = painter.getFontMetrics();
		
		int linesHeight = computeTotalHeight(lineFontMetrics);
		
		drawPanel(painter, titleHeight + linesHeight);

		painter.setColor(Theme.frontColorFor(Theme.SECONDARY_COLOR));

		drawTitle(painter, titleHeight, lineFontMetrics);		
		drawLines(painter, titleHeight, lineFontMetrics);
	}

	private void drawPanel(Graphics painter, int totalHeight) {
		painter.setColor(getBackground());

		painter.fillRect(getLeft(), getTop(), getWidth(),totalHeight);
	}
	
	private void drawTitle(Graphics painter, int titleHeight, FontMetrics lineFontMetrics) {
		if(!title.isBlank()) {
			painter.setFont(Theme.SUBTITLE_FONT);
			painter.drawString(title, getLeft() + lineFontMetrics.getMaxAscent(), getTop() + titleHeight);
			painter.drawLine(getLeft() + lineFontMetrics.getAscent(), getTop() + titleHeight+ GAP, getLeft() + getWidth() - 2*lineFontMetrics.getAscent(), getTop() + titleHeight+ GAP);
		}
	}
	
	private void drawLines(Graphics painter, int titleHeight, FontMetrics lineFontMetrics) {
		painter.setFont(Theme.NORMAL_FONT);
		for(int i=0; i < lines.size(); ++i) {
			painter.drawString(lines.get(i), getLeft() + lineFontMetrics.getMaxAscent(), getTop() + titleHeight + (i+1)*lineFontMetrics.getHeight()+ GAP);
		}
	}


	private int getRenderedTileHeight(Graphics painter) {
		int titleHeight = 0;
		
		if(!title.isBlank()) {
			painter.setFont(Theme.SUBTITLE_FONT);
			var titleFontMetrics = painter.getFontMetrics();
			titleHeight = titleFontMetrics.getHeight() + GAP;
		}
		
		return titleHeight;
	}

	private int computeTotalHeight(FontMetrics fontMetrics) {
		int lineGap = fontMetrics.getHeight() + GAP;
		return GAP + lines.size()*lineGap;
	}

	/**
	 * Ajoute toutes les lignes non-blanches à ce panneau.
	 * */
	public void addAll(String... lines) {
		if(lines == null) {
			return;
		}
		this.lines.clear();
		for(var line : lines) {
			if(line != null) {
				this.lines.add(line);
			}
		}
	}

}
