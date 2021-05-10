package bomberOne.model.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bomberone.model.Difficulty;
import bomberone.model.World;
import bomberone.model.WorldImpl;
import bomberone.model.common.P2d;
import bomberone.model.user.Skins;
import bomberone.tools.ResourcesLoader;

public class TestWorld {

    private World world;

    @BeforeEach
    public void init() {
        ResourcesLoader.start();
        world = new WorldImpl(Difficulty.STANDARD, Skins.BLACK);
    }

    @Test
    public void testSetBox() {
        assertFalse(world.getGameObjectCollection().getHardWallList().size() == 0);
        assertEquals(world.getBomber().getPosition(), new P2d(32,32));
        assertEquals(world.getGameObjectCollection().getBoxList().size(), 80);
        assertEquals(world.getGameObjectCollection().getPowerUpList().size(), 20);
        assertEquals(world.getGameObjectCollection().getEnemyList().size(), 0);
    }
}
