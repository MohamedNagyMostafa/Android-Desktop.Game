package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.connections.IciclesScreenConnection;
import com.mygdx.game.connections.ScreensConnection;
import com.mygdx.game.game_components.Player;
import com.mygdx.game.helper.Constants;

/**
 * Created by mohamednagy on 4/2/2017.
 */
public class IciclesScreen extends InputAdapter implements Screen, IciclesScreenConnection {

    private ScreensConnection m_screensConnection;
    private com.mygdx.game.helper.Difficulty m_difficulty;

    private ExtendViewport m_iciclesViewport;
    private ShapeRenderer m_renderer;

    private ScreenViewport m_hudViewport;
    private SpriteBatch m_batch;
    private BitmapFont m_font;

    private com.mygdx.game.game_components.Player m_player;
    private com.mygdx.game.game_components.Icicles m_icicles;
    private int m_score;

    private int m_topScore;

    public IciclesScreen(ScreensConnection screensConnection, com.mygdx.game.helper.Difficulty difficulty) {
        m_screensConnection = screensConnection;
        m_difficulty = difficulty;
    }

    @Override
    public void show() {
        m_iciclesViewport =
                new ExtendViewport(com.mygdx.game.helper.Constants.WORLD_SIZE, com.mygdx.game.helper.Constants.WORLD_SIZE);

        m_renderer = new ShapeRenderer();
        m_renderer.setAutoShapeType(true);

        m_hudViewport = new ScreenViewport();
        m_batch = new SpriteBatch();

        m_font = new BitmapFont();
        m_font.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        m_player = new Player(m_iciclesViewport);
        m_icicles = new com.mygdx.game.game_components.Icicles(m_iciclesViewport, m_difficulty, this);

        Gdx.input.setInputProcessor(this);

        m_topScore = 0;
    }

    @Override
    public void resize(int width, int height) {

        m_iciclesViewport.update(width, height, true);
        m_hudViewport.update(width, height, true);
        m_font.getData().setScale(Math.min(width, height) / com.mygdx.game.helper.Constants.HUD_FONT_SCREEN_SIZE);

        m_player.init();
        m_icicles.init();
        m_score = 0;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(float delta) {
        m_icicles.update(delta);
        m_player.update(delta);

        if (m_player.hitByIcicle(m_icicles)) {
            m_icicles.init();
            m_score = 0;
        }

        m_iciclesViewport.apply(true);

        Gdx.gl.glClearColor(
                com.mygdx.game.helper.Constants.BACKGROUND_COLOR.r,
                com.mygdx.game.helper.Constants.BACKGROUND_COLOR.g,
                com.mygdx.game.helper.Constants.BACKGROUND_COLOR.b, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        m_renderer.setProjectionMatrix(m_iciclesViewport.getCamera().combined);
        m_renderer.begin(ShapeRenderer.ShapeType.Filled);

        m_icicles.render(m_renderer);
        m_player.render(m_renderer);

        m_renderer.end();

        m_hudViewport.apply();
        m_batch.setProjectionMatrix(m_hudViewport.getCamera().combined);
        m_batch.begin();

        m_topScore = Math.max(m_topScore, m_score);

        final String leftHudText =
                com.mygdx.game.helper.Constants.DEATHS_LABEL + m_player.getPlayerDeaths() + "\n" +
                        com.mygdx.game.helper.Constants.DIFFICULTY_LABEL + m_difficulty.mode;

        final String rightHudText =
                com.mygdx.game.helper.Constants.SCORE_LABEL + m_score + "\n" +
                        com.mygdx.game.helper.Constants.TOP_SCORE_LABEL + m_topScore;

        m_font.draw(m_batch, leftHudText, com.mygdx.game.helper.Constants.HUD_MARGIN,
                m_hudViewport.getWorldHeight() - com.mygdx.game.helper.Constants.HUD_MARGIN);

        m_font.draw(m_batch, rightHudText,
                m_hudViewport.getWorldWidth() - com.mygdx.game.helper.Constants.HUD_MARGIN,
                m_hudViewport.getWorldHeight() - Constants.HUD_MARGIN,
                0, Align.right, false);

        m_batch.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        m_renderer.dispose();
        m_batch.dispose();

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        m_screensConnection.openDifficultyScreen();
        return true;
    }

    @Override
    public void incrementPlayerScore() {
        m_score++;
    }
}
