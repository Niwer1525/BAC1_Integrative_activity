/**
 * Implémente les interfaces utilisateur de l'application avec Swing.
 * */
package hexmo.views;

import java.io.Serial;
import java.util.*;

import javax.swing.JFrame;

import hexmo.supervisors.commons.ViewId;
import org.helmo.swing.SwingView;
import org.helmo.swing.adapter.MyKeyAdapter;

/**
 * Displays views that made up the application.
 *
 * @author Nicolas Hendrikx
 */

public final class SwingMainWindow extends JFrame implements MainWindow {
	@Serial
	private static final long serialVersionUID = 6621287528953860235L;
	
	private final Map<ViewId, SwingView<ViewId>> nameToView = new LinkedHashMap<>();
	private SwingView<ViewId> current;
	
	/**
	 * Initialise cette fenêtre principale avec un titre et des écrans.
	 * @param title le titre affiché par cette fenêtre principale.
	 * @param views les vues qui composent l'application
	 */
	public SwingMainWindow(String title, SwingView<ViewId>...views) {
		super(Objects.requireNonNull(title));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Theme.WINDOW_WIDTH, Theme.WINDOW_HEIGHT);
		setBackground(Theme.BACKGROUND_COLOR);
		setFont(Theme.NORMAL_FONT);
		setResizable(false);
		
		for(var view : views) {
			Objects.requireNonNull(view);
			this.nameToView.put(view.getViewId(), view);
			view.setRouter(this::goTo);
		}
		
		this.addKeyListener(new MyKeyAdapter(this::onKeyTyped));
	}

	/**
	 * Méthode appelée par une implémentation Swing d'un écran pour changer d'écran affiché.
	 * */
	@Override
	public void goTo(ViewId id) {
		var found = getView(id);
		
		if(current != null) {
			current.onLeave(id);
		}
		found.onEnter(current == null ? ViewId.NONE : current.getViewId());
		
		this.current = found;		
		this.setContentPane(current);
	}

	private SwingView getView(ViewId viewId) {
		if(!nameToView.containsKey(viewId)) {
			throw new IllegalArgumentException("view named ["+viewId+"] does not match any registered view name.");
		}
		return nameToView.get(viewId);
	}

	/**
	 * Affiche le premier écran.
	 * */
	@Override
	public void start(ViewId id) {
		this.goTo(id);
		this.setVisible(true);
	}
	
	private void onKeyTyped(int keyCode) {
		if(current == null) {
			return;
		}
		this.current.onKeyTyped(keyCode);		
	}
	
}
