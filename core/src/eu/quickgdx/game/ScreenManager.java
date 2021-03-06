package eu.quickgdx.game;

import com.badlogic.gdx.Screen;

import eu.quickgdx.game.screens.ChoosingScreen;
import eu.quickgdx.game.screens.ContinueExitGameScreen;
import eu.quickgdx.game.screens.CreditsScreen;
import eu.quickgdx.game.screens.GameOverScreen;
import eu.quickgdx.game.screens.GameplayScreen;
import eu.quickgdx.game.screens.LoadingScreen;
import eu.quickgdx.game.screens.MenuScreen;

/**
 * Created by Mathias Lux, mathias@juggle.at, on 04.02.2016.
 */
public class ScreenManager {
    public enum ScreenState {Loading, Menu, Game, Credits, Help, GameOver,ChooseGame,ContinueExitGame};
    private Screen currentScreen;
    private ScreenState currentState;
    private QuickGdx parentGame;

    public ScreenManager(QuickGdx game) {
        this.parentGame = game;
        currentScreen = new LoadingScreen(game);
        currentState = ScreenState.Loading;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public ScreenState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ScreenState state) {
        if (state != currentState) { // only if state changes.
            currentState = state;
            if (state == ScreenState.Menu) {
                currentScreen = new MenuScreen(parentGame);
                parentGame.getSoundManager().playEvent("lcr");
            } else if (state == ScreenState.Credits) {
                currentScreen = new CreditsScreen(parentGame);
            } else if (state == ScreenState.Game) {
                currentScreen = new GameplayScreen(parentGame);
            } else if (state == ScreenState.GameOver) {
                currentScreen = new GameOverScreen(parentGame);
            } else if (state == ScreenState.ChooseGame) {
                currentScreen = new ChoosingScreen(parentGame);
            } else if (state == ScreenState.ContinueExitGame) {
                currentScreen = new ContinueExitGameScreen(parentGame);
            }
        }
    }

    public QuickGdx getParentGame() {
        return parentGame;
    }

    public void setParentGame(QuickGdx parentGame) {
        this.parentGame = parentGame;
    }
}
