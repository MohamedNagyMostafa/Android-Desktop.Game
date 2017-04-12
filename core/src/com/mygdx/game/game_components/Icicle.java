package com.mygdx.game.game_components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.helper.Constants;

/**
 * Created by mohamednagy on 4/2/2017.
 */
public class Icicle {

    private Vector2 m_position;
    private Vector2 m_velocity;

    public Icicle(Vector2 position) {
        m_position = position;
        m_velocity = new Vector2();
    }

    public void update(float delta) {
        m_velocity.y += Constants.ICICLES_ACCELERATION * delta ;
        m_position.y += m_velocity.y * delta;
    }

    public void render(ShapeRenderer renderer) {
        renderer.triangle(
                m_position.x, m_position.y,
                m_position.x - Constants.ICICLES_WIDTH / 2,
                m_position.y + Constants.ICICLES_HEIGHT,
                m_position.x + Constants.ICICLES_WIDTH / 2,
                m_position.y + Constants.ICICLES_HEIGHT
        );
    }

    public Vector2 getPosition() {
        return m_position;
    }

}
