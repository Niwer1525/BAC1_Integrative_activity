package hexmo.views.components;

import java.awt.Color;
import java.awt.Graphics2D;

import hexmo.views.Theme;
import org.helmo.swing.engine.GameComponent;

/**
 * Représente un élément de menu à afficher.
 * */
public class MenuItem extends GameComponent {
	private final String item;
	
	/**
	 * Construit un élément de menu identifié par la position {@code itemPos}, affichant le texte {@code label}.
	 * */
	public MenuItem(String label, int left, int top, int width, int height) {
		super(left,top, width, height);
		this.item = label;
	}

	/**
	 * Dessine l'élément de menu.
	 * */
	@Override
	public void repaint(Graphics2D painter) {
		painter.setColor(Theme.SECONDARY_COLOR);
		painter.fillRoundRect(getLeft(), getTop(), getWidth(), getHeight(), 5, 5);
		
		var metrics = painter.getFontMetrics(Theme.ITEM_FONT);
		
		painter.setFont(Theme.ITEM_FONT);
		painter.setColor(Theme.frontColorFor(Theme.SECONDARY_COLOR));
		painter.drawString(item, getLeft()+metrics.getAscent(), getTop()+metrics.getHeight());	
	
		painter.setColor(Color.BLACK);
		painter.fillOval(getPosX()-1, getPosY()-1, 2, 2);
	}


}
