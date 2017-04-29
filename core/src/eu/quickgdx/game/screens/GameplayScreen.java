package eu.quickgdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eu.quickgdx.game.Constanze;
import eu.quickgdx.game.mechanics.World;
import eu.quickgdx.game.QuickGdx;

/**
 * Created by Mathias Lux, mathias@juggle.at,  on 04.02.2016.
 */
public class GameplayScreen extends ScreenAdapter {
    private final SpriteBatch gameBatch;
    private final SpriteBatch hudBatch;
    public final OrthographicCamera gameCam;
    public final OrthographicCamera hudCam;
    public QuickGdx parentGame;

    Texture backgroundImage;
    BitmapFont menuFont;
    World world;
    String[] menuStrings = {"Play", "Credits", "Exit"};
    int currentMenuItem = 0;

    float offsetLeft = Constanze.GAME_WIDTH / 8, offsetTop = Constanze.GAME_WIDTH / 8, offsetY = Constanze.GAME_HEIGHT / 8;


    public GameplayScreen(QuickGdx game) {
        this.parentGame = game;
        backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
        menuFont = parentGame.getAssetManager().get("menu/Ravie_72.fnt");
        menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        gameCam = new OrthographicCamera(Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        float screenMultiplikator = 2f;
        gameCam.zoom += 2.5f;
        gameCam.position.set(gameCam.viewportWidth / screenMultiplikator, gameCam.viewportHeight / screenMultiplikator, 0);
        gameCam.update();
        hudCam = new OrthographicCamera(Constanze.GAME_WIDTH, Constanze.GAME_HEIGHT);
        hudCam.position.set(hudCam.viewportWidth / screenMultiplikator, hudCam.viewportHeight / screenMultiplikator, 0);
        hudCam.update();
        gameBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();
        this.world = new World(this);
        parentGame.getSoundManager().startBgMusic();
    }

    @Override
    public void render(float delta) {
        handleInput();
        gameBatch.setProjectionMatrix(gameCam.combined);
        hudBatch.setProjectionMatrix(hudCam.combined);
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.update(delta);
        parentGame.getSoundManager().update(delta);
        world.render(delta, gameBatch);
        world.renderHUD(delta, hudBatch);
        gameCam.update();
        hudCam.update();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESCAPE PRESSED");
            parentGame.getSoundManager().playEvent("blip");
        }
    }

}
