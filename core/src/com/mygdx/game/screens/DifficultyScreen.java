package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.connections.ScreensConnection;
import com.mygdx.game.helper.Constants;

/**
 * Created by mohamednagy on 4/2/2017.
 */
public class DifficultyScreen extends InputAdapter implements Screen {

    ShapeRenderer m_renderer;
    FitViewport m_viewport;

    BitmapFont m_font;
    SpriteBatch m_batch;

    ScreensConnection screensConnection;

    public DifficultyScreen(ScreensConnection screensConnection){
        this.screensConnection = screensConnection;
    }

    @Override
    public void show() {
        m_renderer = new ShapeRenderer();
        m_batch = new SpriteBatch();

        m_viewport = new FitViewport(com.mygdx.game.helper.Constants.DIFFICULTY_WORLD_SIZE, com.mygdx.game.helper.Constants.DIFFICULTY_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        m_font = new BitmapFont();
        m_font.getData().setScale(com.mygdx.game.helper.Constants.DIFFICULTY_LABEL_SCALE);
        m_font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {

        m_viewport.apply();
        Gdx.gl.glClearColor(com.mygdx.game.helper.Constants.BACKGROUND_COLOR.r, com.mygdx.game.helper.Constants.BACKGROUND_COLOR.g, com.mygdx.game.helper.Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        m_renderer.setProjectionMatrix(m_viewport.getCamera().combined);

        m_renderer.begin(ShapeRenderer.ShapeType.Filled);

        m_renderer.setColor(com.mygdx.game.helper.Constants.EASY_COLOR);

        m_renderer.circle(com.mygdx.game.helper.Constants.EASY_CENTER_COORDINATE.x ,
                com.mygdx.game.helper.Constants.EASY_CENTER_COORDINATE.y ,
                com.mygdx.game.helper.Constants.DIFFICULTY_BUBBLE_RADIUS);

        m_renderer.setColor(com.mygdx.game.helper.Constants.MEDIUM_COLOR);
        
       m_renderer.circle(com.mygdx.game.helper.Constants.MEDIUM_CENTER_COORDINATE.x ,
                com.mygdx.game.helper.Constants.EASY_CENTER_COORDINATE.y ,
                com.mygdx.game.helper.Constants.DIFFICULTY_BUBBLE_RADIUS);

       m_renderer.setColor(com.mygdx.game.helper.Constants.HARD_COLOR);

       m_renderer.circle(com.mygdx.game.helper.Constants.HARD_CENTER_COORDINATE.x,
               com.mygdx.game.helper.Constants.EASY_CENTER_COORDINATE.y,
               com.mygdx.game.helper.Constants.DIFFICULTY_BUBBLE_RADIUS);

       m_renderer.end();

       m_batch.setProjectionMatrix(m_viewport.getCamera().combined);

       m_batch.begin();

        final GlyphLayout easyLayout = new GlyphLayout(m_font, com.mygdx.game.helper.Constants.EASY_MODE);

       m_font.draw(m_batch,
               com.mygdx.game.helper.Difficulty.EASY.mode,
               com.mygdx.game.helper.Constants.EASY_CENTER_COORDINATE.x,
               com.mygdx.game.helper.Constants.EASY_CENTER_COORDINATE.y + easyLayout.height / 2,
               0,
               com.mygdx.game.helper.Constants.DIFFICULTY_ALIGN,
               false);

        final GlyphLayout mediumLayout = new GlyphLayout(m_font, com.mygdx.game.helper.Constants.MEDIUM_MODE);

        m_font.draw(m_batch,
               com.mygdx.game.helper.Constants.MEDIUM_MODE,
               com.mygdx.game.helper.Constants.MEDIUM_CENTER_COORDINATE.x,
               com.mygdx.game.helper.Constants.MEDIUM_CENTER_COORDINATE.y + mediumLayout.height / 2,
               0,
                com.mygdx.game.helper.Constants.DIFFICULTY_ALIGN,
               false);

        final GlyphLayout hardLayout = new GlyphLayout(m_font, com.mygdx.game.helper.Constants.HARD_MODE);

        m_font.draw(
               m_batch,
                com.mygdx.game.helper.Constants.HARD_MODE,
                com.mygdx.game.helper.Constants.HARD_CENTER_COORDINATE.x,
                com.mygdx.game.helper.Constants.HARD_CENTER_COORDINATE.y + hardLayout.height / 2,
                0,
                com.mygdx.game.helper.Constants.DIFFICULTY_ALIGN,
                false);

        m_batch.end();
    }

    @Override
    public void resize(int width, int height) {
        m_viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        m_batch.dispose();
        m_font.dispose();
        m_renderer.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 screenTouch = m_viewport.unproject(new Vector2(screenX, screenY));

        if (screenTouch.dst(com.mygdx.game.helper.Constants.EASY_CENTER_COORDINATE) < com.mygdx.game.helper.Constants.DIFFICULTY_BUBBLE_RADIUS) {
            screensConnection.openIciclesScreen(com.mygdx.game.helper.Difficulty.EASY);
        }

        if (screenTouch.dst(com.mygdx.game.helper.Constants.MEDIUM_CENTER_COORDINATE) < com.mygdx.game.helper.Constants.DIFFICULTY_BUBBLE_RADIUS) {
            screensConnection.openIciclesScreen(com.mygdx.game.helper.Difficulty.MEDIUM);
        }

        if (screenTouch.dst(com.mygdx.game.helper.Constants.HARD_CENTER_COORDINATE) < Constants.DIFFICULTY_BUBBLE_RADIUS) {
            screensConnection.openIciclesScreen(com.mygdx.game.helper.Difficulty.HARD);
        }

        return true;
    }
}
