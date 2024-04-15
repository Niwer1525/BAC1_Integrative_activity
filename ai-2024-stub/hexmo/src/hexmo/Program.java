package hexmo;

import javax.swing.SwingUtilities;

import hexmo.domains.HexGameFactory;
import hexmo.supervisors.commons.ViewId;
import hexmo.supervisors.gameover.GameOverSupervisor;
import hexmo.supervisors.mainmenu.MainMenuSupervisor;
import hexmo.supervisors.playgame.PlayGameSupervisor;
import hexmo.views.AppFactory;
import hexmo.views.MainWindow;
import hexmo.views.SwingMainWindowFactory;

/**
 * Construit et lance l'application réelle.
 */
public class Program {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> buildAndRun(new SwingMainWindowFactory()));
    }
    
    /**
     * Construit et lance une application fabriquée avec {@code appBuilder}.
     * 
     * Une fabrique construisant l'interface utilisateur pour une technologie précise.
     */
    public static void buildAndRun(AppFactory appBuilder) {
        HexGameFactory gameFactory = new HexGameFactory();
        var menuSupervisor = new MainMenuSupervisor(gameFactory);
        var playSupervisor = new PlayGameSupervisor(gameFactory);
        var endSupervisor = new GameOverSupervisor(gameFactory);

        MainWindow main = appBuilder.ofSupervisors("B1UE09 - HexMo",
                menuSupervisor,
                playSupervisor,
                endSupervisor);

        main.start(ViewId.MAIN_MENU);
    }
}
