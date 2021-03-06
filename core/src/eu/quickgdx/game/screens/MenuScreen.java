package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.QuickGdx;
import eu.quickgdx.game.ScreenManager;

/**
 * Created by Mathias Lux, mathias@juggle.at,  on 04.02.2016.
 */
public class MenuScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final OrthographicCamera cam;
    private QuickGdx parentGame;

    Texture backgroundImage;
    BitmapFont menuFont;

    String[] menuStrings = {"PLAY", "CREDITS", "EXIT"};
    int currentMenuItem = 0;

    float offsetLeft = Constanze.GAME_WIDTH / 8, offsetTop = Constanze.GAME_WIDTH / 8, offsetY = Constanze.GAME_HEIGHT / 8;


    public MenuScreen(QuickGdx game) {
        this.parentGame = game;
        game.getSoundManager().initzializeMusic();
        backgroundImage = parentGame.getAssetManager().getTexture(Constanze.ASSET_MENU_SCREEN);
        menuFont = parentGame.getAssetManager().get("fonts/retro.fnt");
        menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        menuFont.setColor(Color.SKY);
        // Create camera that projects the game onto the actual screen size.
        cam = new OrthographicCamera(Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        handleInput();
        // camera:
        cam.update();
        batch.setProjectionMatrix(cam.combined);


        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw bgImage ...
        batch.draw(backgroundImage, 0, 0, Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        // draw Strings ...
        for (int i = 0; i < menuStrings.length; i++) {
            if (i == currentMenuItem) menuFont.setColor(0.2f, 1f, 0.2f, 1f);
            else menuFont.setColor(Color.SKY);
            menuFont.draw(batch, menuStrings[i], offsetLeft, Constanze.GAME_HEIGHT - offsetTop - i * offsetY);
        }
        batch.end();
    }

    private void handleInput() {
        // keys ...
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            currentMenuItem = (currentMenuItem + 1) % menuStrings.length;
            parentGame.getSoundManager().playEvent("blip");
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            currentMenuItem = (currentMenuItem - 1) % menuStrings.length;
            parentGame.getSoundManager().playEvent("blip");
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (menuStrings[currentMenuItem].equals("EXIT")) {
                Gdx.app.exit();
                parentGame.getSoundManager().playEvent("explode");
            } else if (menuStrings[currentMenuItem].equals("CREDITS")) {
                parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Credits);}
            else if (menuStrings[currentMenuItem].equals("PLAY")) {
                    parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.ChooseGame);
                }
            }

        // touch
        if (Gdx.input.justTouched()) {
            Vector3 touchWorldCoords = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
            // find the menu item ..
            for (int i = 0; i < menuStrings.length; i++) {
                if (touchWorldCoords.x > offsetLeft) {
                    float pos = Constanze.GAME_HEIGHT - offsetTop - i * offsetY;
                    if (touchWorldCoords.y < pos && touchWorldCoords.y > pos-menuFont.getLineHeight()) {
                        // it's there
                        if (menuStrings[i].equals("EXIT")) {
                            Gdx.app.exit();
                        } else if (menuStrings[i].equals("PLAY")) {
                            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.ChooseGame);
                        } else if (menuStrings[i].equals("CREDITS")) {
                            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Credits);
                        }
                    }
                }

            }
        }
    }


}
