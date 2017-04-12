package com.mygdx.game.game_components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by mohamednagy on 4/2/2017.
 */
public class Icicles {

    private com.mygdx.game.helper.Difficulty m_difficulty;

    private com.mygdx.game.connections.IciclesScreenConnection m_iciclesScreenConnection;
    public DelayedRemovalArray<Icicle> m_icicleList;
    private Viewport m_viewport;

    public Icicles(Viewport viewport, com.mygdx.game.helper.Difficulty difficulty,
                   com.mygdx.game.connections.IciclesScreenConnection iciclesScreenConnection) {

        m_difficulty = difficulty;
        m_viewport = viewport;
        m_iciclesScreenConnection = iciclesScreenConnection;

        init();
    }

    public void init() {
        m_icicleList = new DelayedRemovalArray<Icicle>(false, com.mygdx.game.helper.Constants.ICICLES_ARRAY_SIZE);
    }

    public void update(float delta) {

        if (MathUtils.random() < delta * m_difficulty.spawnsPerSecond) {
            Vector2 newIciclePosition = new Vector2(
                    MathUtils.random() * m_viewport.getWorldWidth(),
                    m_viewport.getWorldHeight()
            );

            Icicle newIcicle = new Icicle(newIciclePosition);
            m_icicleList.add(newIcicle);
        }

        for (Icicle icicle : m_icicleList) {
            icicle.update(delta);
        }

        m_icicleList.begin();
        for (int i = 0; i < m_icicleList.size; i++) {
            if (m_icicleList.get(i).getPosition().y < -com.mygdx.game.helper.Constants.ICICLES_HEIGHT) {

                m_iciclesScreenConnection.incrementPlayerScore();
                m_icicleList.removeIndex(i);

            }
        }
        m_icicleList.end();
    }

    public void render(ShapeRenderer renderer) {

        renderer.setColor(com.mygdx.game.helper.Constants.ICICLE_COLOR);

        for (Icicle icicle : m_icicleList) {
            icicle.render(renderer);
        }
    }
}
