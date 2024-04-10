package hexmo.views;

import hexmo.supervisors.commons.ViewId;
import hexmo.supervisors.gameover.*;
import hexmo.views.components.DataPanel;

import org.helmo.swing.SwingView;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class SwingGameOverView extends SwingView<ViewId> implements GameOverView {
    @Serial
	private static final long serialVersionUID = 5905336236732125141L;
    
    private final GameOverSupervisor supervisor;
	private final Timer animator;
    private final DataPanel commandsPanel = new DataPanel("COMMANDES",
            (int)(Theme.PANEL_WIDTH*0.66),
            (int)(Theme.PANEL_HEIGHT*0.25),
            (int)(Theme.PANEL_WIDTH*0.30));
    private final DataPanel statsPanel = new DataPanel("STATS",
            (int)(Theme.PANEL_WIDTH*0.66),
            (int)(Theme.PANEL_HEIGHT*0.40),
            (int)(Theme.PANEL_WIDTH*0.30));

	private final ImageIcon background;
	
    public SwingGameOverView(GameOverSupervisor endSupervisor) {
        super(ViewId.END_GAME);
        
		this.animator = new Timer(16, (evt) -> this.updateUI());
		this.commandsPanel.addAll(
				"Appuyez sur n'importe quelle touche",
				"pour revenir au menu principal");
		
		this.background = new ImageIcon("resources/images/game-over-background.jpg");
		
        this.supervisor = Objects.requireNonNull(endSupervisor);
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
        statsPanel.repaint(renderer);
    }

	@Override
	public void setStats(String... messages) {
		statsPanel.addAll(messages);		
	}
	
	@Override
	public void onEnter(ViewId fromView) {
		animator.start();
		supervisor.onEnter(fromView);
	}

	
	@Override
	public void onLeave(ViewId toView) {
		animator.stop();
	}
	
	@Override
	public void onKeyTyped(int keyCode) {
		supervisor.onGoToMain();
	}
}
