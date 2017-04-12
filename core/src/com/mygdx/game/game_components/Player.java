package com.mygdx.game.game_components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by mohamednagy on 4/2/2017.
 */
public class Player {


    private Vector2 m_position;

    private Viewport m_viewport;

    private int m_deaths;

    public Player(Viewport viewport) {
        m_viewport = viewport;

        init();
    }

    public void init() {
        m_position = new Vector2(m_viewport.getWorldWidth() / 2,
                com.mygdx.game.helper.Constants.PLAYER_HEAD_HEIGHT);
        m_deaths = 0;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            m_position.x -= delta * com.mygdx.game.helper.Constants.PLAYER_MOVEMENT_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            m_position.x += delta * com.mygdx.game.helper.Constants.PLAYER_MOVEMENT_SPEED;
        }

        float yAccelerometer = -Gdx.input.getAccelerometerY();

        float accelerometerInput =
                yAccelerometer /
                        (com.mygdx.game.helper.Constants.GRAVITATIONAL_ACCELERATION * com.mygdx.game.helper.Constants.ACCELEROMETER_SENSITIVITY);

        m_position.x += -delta * accelerometerInput * com.mygdx.game.helper.Constants.PLAYER_MOVEMENT_SPEED;

        worldBounds();
    }

    private void worldBounds() {
        if (m_position.x - com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS < 0) {
            m_position.x = com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS;
        }

        if (m_position.x + com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS > m_viewport.getWorldWidth()) {
            m_position.x = m_viewport.getWorldWidth() -
                    com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS;
        }
    }

    public boolean hitByIcicle(Icicles icicles) {
        for (com.mygdx.game.game_components.Icicle icicle : icicles.m_icicleList) {
            if (icicle.getPosition().dst(m_position) < com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS) {
                m_deaths++;
                return true;
            }
        }

        return false;
    }

    public void render(ShapeRenderer renderer) {

        renderer.setColor(com.mygdx.game.helper.Constants.PLAYER_COLOR);
        renderer.set(ShapeRenderer.ShapeType.Filled);

        drawPlayer(renderer);

    }

    private void drawPlayer(ShapeRenderer renderer){

        drawPlayerHead(renderer);
        drawPlayerBody(renderer);
        drawPlayerHands(renderer);
        drawPlayerFeeds(renderer);

    }

    private void drawPlayerHead(ShapeRenderer renderer){

        renderer.circle(m_position.x,
                m_position.y,
                com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                com.mygdx.game.helper.Constants.PLAYER_HEAD_SEGMENTS);
    }

    private void drawPlayerBody(ShapeRenderer renderer){

        renderer.rectLine(
                m_position.x,
                m_position.y - com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                m_position.x,
                m_position.y - 3 * com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                com.mygdx.game.helper.Constants.PLAYER_LIMB_WIDTH);
    }

    private void drawPlayerHands(ShapeRenderer renderer){
        renderer.rectLine(
                m_position.x,
                m_position.y - com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                m_position.x + com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                m_position.y - 2 * com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS ,
                com.mygdx.game.helper.Constants.PLAYER_LIMB_WIDTH);

        renderer.rectLine(
                m_position.x,
                m_position.y - com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                m_position.x - com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                m_position.y - 2 * com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                com.mygdx.game.helper.Constants.PLAYER_LIMB_WIDTH);
    }

    private void drawPlayerFeeds(ShapeRenderer renderer){

        renderer.rectLine(
                m_position.x,
                m_position.y - 3 * com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                m_position.x + com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                m_position.y - 4 * com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                com.mygdx.game.helper.Constants.PLAYER_LIMB_WIDTH);

        renderer.rectLine(
                m_position.x,
                m_position.y - 3 * com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                m_position.x - com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS,
                m_position.y - 4 * com.mygdx.game.helper.Constants.PLAYER_HEAD_RADIUS ,
                com.mygdx.game.helper.Constants.PLAYER_LIMB_WIDTH);
    }

    public int getPlayerDeaths() {
        return m_deaths;
    }
}
