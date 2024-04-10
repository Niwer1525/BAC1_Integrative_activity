package hexmo.views;

import hexmo.supervisors.commons.*;
import hexmo.supervisors.playgame.*;
import hexmo.views.components.*;
import hexmo.views.effect.DelegateEffect;
import hexmo.views.effect.EffectsPlayer;
import org.helmo.swing.SwingView;
import org.helmo.swing.engine.Vector2f;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Objects;


public class SwingPlayGameView extends SwingView<ViewId> implements PlayGameView {
    @Serial
	private static final long serialVersionUID = -5965462678420952310L;
    
	private final PlayGameSupervisor supervisor;
    private final Camera camera;
    private final Timer clock;
    private final EffectsPlayer effects;

    private final HexSelector selector;

    private final java.util.List<Drawable> components = new ArrayList<>();
    private final Drawable rose = new RoseBackground(10*Theme.TILE_SIZE);
    
    private final DataPanel gamePanel = new DataPanel("ÉTAT",
            (int)(Theme.PANEL_WIDTH*0.75),
            (int)(Theme.PANEL_HEIGHT*0.5),
            (int)(Theme.PANEL_WIDTH*0.20));
    private final DataPanel commandsPanel = new DataPanel("COMMANDES",
            (int)(Theme.PANEL_WIDTH*0.75),
            (int)(Theme.PANEL_HEIGHT*0.25),
            (int)(Theme.PANEL_WIDTH*0.20));

	private final ImageIcon background;

    public SwingPlayGameView(PlayGameSupervisor supervisor) {
        super(ViewId.PLAY_GAME);
        this.supervisor = Objects.requireNonNull(supervisor);
        this.supervisor.setView(this);

		this.background = new ImageIcon("resources/images/play-game-background.jpg");

        this.camera = new Camera(Vector2f.ZERO, new Vector2f(.5f,.5f), Vector2f.X_AXIS);
        this.clock = new Timer(16, (evt) -> onNewFrameRequested() );

        this.effects = new EffectsPlayer();
        this.effects.push(new DelegateEffect(
                () -> this.camera.scaleOf(0.03f),
                () -> this.camera.getScaleX() > 1.0f && this.camera.getScaleY() > 1.0f
         ));

        this.selector = new HexSelector(Theme.TILE_SIZE, Vector2f.ZERO);

        this.commandsPanel.addAll(
                "← : déplacer vers la gauche",
                "↑ : déplacer vers le haut",
                "→ : déplacer vers la droite",
                "↓ : déplacer vers le bas",
                "P : zoomer",
                "M : dézoomer",
                "SPACE : affecter",
                "ESC : abandonner");
    }

    private void onNewFrameRequested() {
        effects.update();
        this.updateUI();
    }

    @Override
    public void onEnter(ViewId fromView) {
        this.supervisor.onEnter(fromView);
        this.clock.start();
    }

    @Override
    public void onLeave(ViewId toView) {
        this.clock.stop();
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

        int smallestScreenSize = Math.min(Theme.PANEL_WIDTH, Theme.PANEL_HEIGHT);

        var savedTransform = renderer.getTransform();

        var windowTransform = new AffineTransform();
        windowTransform.translate(smallestScreenSize*0.5 + smallestScreenSize/12.,smallestScreenSize*0.5 - smallestScreenSize/12.);
        camera.applyTo(windowTransform);

        renderer.setTransform(windowTransform);
        rose.draw(renderer);

        for (var s : components) {
            s.draw(renderer);
        }
        selector.draw(renderer);

        renderer.setTransform(savedTransform);
        gamePanel.repaint(renderer);
        commandsPanel.repaint(renderer);
    }

    @Override
    public void onKeyTyped(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_P -> scale(0.25f);
            case KeyEvent.VK_M -> scale(-0.25f);
            case KeyEvent.VK_UP -> supervisor.onMove(0, -1);
            case KeyEvent.VK_DOWN -> supervisor.onMove(0, 1);
            case KeyEvent.VK_LEFT -> supervisor.onMove(-1, 0);
            case KeyEvent.VK_RIGHT -> supervisor.onMove(1, 0);
            case KeyEvent.VK_ESCAPE -> supervisor.onStop();
            case KeyEvent.VK_SPACE -> supervisor.onSet();
        }

        this.updateUI();
    }

    private void scale(float v) {
        final float scaleEnd = camera.getScaleX()+v;
        final float scaleInc = v*0.10f;
        effects.push(new DelegateEffect(
                () -> camera.scaleOf(scaleInc),
                () -> Math.abs(camera.getScaleX() - scaleEnd) < 0.01
        ));
    }

    @Override
    public void clearTiles() {
        this.components.clear();
    }

    @Override
    public void setTileAt(float x, float y, TileType tileType) {
        this.components.add(
                new HexTile(tileType, Theme.TILE_SIZE, new Vector2f(x, y)));
    }

    @Override
    public void setActiveTile(float x, float y) {
        this.selector.moveTo(new Vector2f(x, y));

        this.effects.push(new DelegateEffect(
                () -> {
                    final Vector2f moveDir = this.selector.getPos().mult(-1).subtract(this.camera.getPos()).getNormalized();
                    this.camera.move(moveDir, 5.0f);
                },
                () -> this.selector.getPos().mult(-1).subtract(this.camera.getPos()).getLength() <= 10
        ));
    }

    @Override
    public void setActionMessages(String... message) {
        this.gamePanel.addAll(message);
    }

    @Override
    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message , "Erreur", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public boolean askQuestion(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Question", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
