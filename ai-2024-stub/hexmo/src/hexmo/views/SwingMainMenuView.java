package hexmo.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.util.*;
import java.util.stream.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

import hexmo.supervisors.mainmenu.MainMenuSupervisor;
import hexmo.supervisors.mainmenu.MainMenuView;
import hexmo.supervisors.commons.*;
import hexmo.views.components.*;
import org.helmo.swing.SwingView;
import org.helmo.swing.effect.MoveToEffect;

/**
 * Implémentation Swing du menu principal.
 * */
public final class SwingMainMenuView extends SwingView<ViewId> implements MainMenuView {
	@Serial
	private static final long serialVersionUID = -2211970715714357966L;
	
	private final ImageIcon background;
	
	private final MainMenuSupervisor supervisor;
	private final Timer animator;
	private MoveToEffect moveEffect;
	
	private List<MenuItem> items = List.of();	
	private ItemSelector selector;
	private int selected = 0;

	private final DataPanel commandsPanel;
	
	/**
	 * Construit un menu principal swing composé de son superviseur.
	 * */
	public SwingMainMenuView(MainMenuSupervisor supervisor) {
		super(ViewId.MAIN_MENU);

		this.background = new ImageIcon("resources/images/main-menu-background.jpg");

		this.animator = new Timer(16, (evt) -> onNewFrameRequested());
		
		this.commandsPanel = new DataPanel("COMMANDES", getWidth()/100*3, getHeight()/100*3, getWidth()/10*3);
		this.commandsPanel.addAll(
				"↑ : déplacer vers le haut",
				"↓ : déplacer vers le bas",
				"⏎ : choisir");
		
		this.supervisor = supervisor;
		this.supervisor.setView(this);
	}
	
	@Override
	public void paintComponent(Graphics painter) {		
		super.paintComponent(painter);
		
		int deltaX = (this.getWidth() - background.getIconWidth())/2;
		int deltaY = (this.getHeight() - background.getIconHeight())/2;
        painter.drawImage(background.getImage(), deltaX, deltaY, null);
		
		Graphics2D renderer = (Graphics2D)painter;
		renderer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderer.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		commandsPanel.repaint(renderer);
	
		selector.repaint(renderer);
		
		for(var item : items) {	
			item.repaint(renderer);
		}
	}

	@Override
	public void onKeyTyped(int keyCode) {
		if(items.isEmpty()) {
			return;
		}
		
		int oldSelected = selected;
		if(keyCode == KeyEvent.VK_DOWN) {
			this.selected = (selected + 1) % items.size();
		} else if(keyCode == KeyEvent.VK_UP) {
			this.selected = (selected == 0) ? items.size() - 1 : selected - 1;
		} else if(KeyEvent.VK_ENTER == keyCode) {
			this.supervisor.onItemSelected(this.selected);
		}
		
		if(oldSelected != selected) {
			moveEffect = new MoveToEffect(selector, items.get(selected).getPosition(), 300);
		}
	}

	@Override
	public void setItems(List<String> items) {
		if(items == null) {
			return;
		}
		
		var left = Theme.PANEL_WIDTH/3;
		var top = Theme.PANEL_HEIGHT/6;
		var width = Theme.PANEL_WIDTH/3;
		var height = Theme.PANEL_HEIGHT/12;
		var gap = Theme.PANEL_HEIGHT/20;
		
		this.items = IntStream.range(0, items.size())
				.mapToObj((index) -> new MenuItem(items.get(index), left, top+index*(height+gap), width, height))
				.collect(Collectors.toList());
		this.selected = 0;
		
		selector = new ItemSelector(left-10, top-10, width+20, height+20);
	}
	
	@Override
	public void onEnter(ViewId fromView) {
		animator.start();
	}

	
	@Override
	public void onLeave(ViewId toView) {
		animator.stop();
	}

	private void onNewFrameRequested() {
		if(moveEffect != null) {
			moveEffect.update();
			if(moveEffect.isDone()) {
				moveEffect = null;
			}
			this.updateUI();
		}
		
	}

}
