package bomberone.tools;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import bomberone.model.common.AnimatedObjectsSprites;
import bomberone.model.common.GameImages;
import bomberone.model.common.Maps;
import bomberone.model.user.User;
import bomberone.model.user.UserImpl;
import bomberone.tools.DirectoryLoader;
import bomberone.tools.RankLoader;
import bomberone.tools.ResourcesLoader;

public class TestTools {

    private static final int SCORE_1 = 100;
    private static final int SCORE_2 = 150;

    /**
     * Test if the methods of DirectoryLoader throw IOException.
     */
    @Test
    public void testDirectoryLoader() {
        boolean done = true;
        try {
            DirectoryLoader.start();
        } catch (IOException e) {
            done = false;
        }
        assertTrue(done);
    }

    /**
     * This method test if the RankLoader read and write correctly on the files.
     * @throws IOException 
     */
    @Test
    public void testRankLoader() throws IOException {

        DirectoryLoader.start();
        // Create some User and put them on the Lists
        User user1 = new UserImpl();
        User user2 = new UserImpl();
        user1.setName("GIGI");
        user1.setScore(SCORE_1);
        user2.setName("MARIO");
        user2.setScore(SCORE_2);
        RankLoader.getRankStandard().add(user1);
        RankLoader.getRankHard().add(user2);
        // Save the lists on the file
        RankLoader.writeUsers();
        // Clear the lists
        RankLoader.getRankHard().clear();
        RankLoader.getRankStandard().clear();
        // Restore the lists
        RankLoader.readUsers();
        // Check if the lists were restored correctly
        assertTrue(RankLoader.getRankStandard().get(0).getName().equals(user1.getName()));
        assertTrue(RankLoader.getRankStandard().get(0).getScore() == (user1.getScore()));
        assertTrue(RankLoader.getRankHard().get(0).getName().equals(user2.getName()));
        assertTrue(RankLoader.getRankHard().get(0).getScore() == (user2.getScore()));
    }

    /**
     * This method tests if ResourceLoader load correctly all the images.
     */
    @Test
    public void testResourceLoader() {
        ResourcesLoader.start();
        assertNotNull(GameImages.BOMBER1SCOREBOARD.getImage());
        assertNotNull(GameImages.BACKGROUND.getImage());
        assertNotNull(GameImages.BOMB.getImage());
        assertNotNull(GameImages.BOX.getImage());
        assertNotNull(GameImages.HARDWALL.getImage());
        assertNotNull(GameImages.ICON.getImage());
        assertNotNull(GameImages.POWER_BOMB.getImage());
        assertNotNull(GameImages.POWER_FIREPOWER.getImage());
        assertNotNull(GameImages.POWER_PIERCE.getImage());
        assertNotNull(GameImages.POWER_SPEED.getImage());
        assertNotNull(GameImages.POWER_TIMER.getImage());
        assertNotNull(GameImages.SPAWN.getImage());

        assertNotNull(AnimatedObjectsSprites.BOMBER_WHITE.getImage());
        assertNotNull(AnimatedObjectsSprites.BOMBER_BLACK.getImage());
        assertNotNull(AnimatedObjectsSprites.BOMBER_RED.getImage());
        assertNotNull(AnimatedObjectsSprites.BOMBER_BLUE.getImage());
        assertNotNull(AnimatedObjectsSprites.ENEMIES_HARD.getImage());
        assertNotNull(AnimatedObjectsSprites.ENEMIES_STANDARD.getImage());
    }

    @Test
    public void testMapLoader() {
        ResourcesLoader.start();
        List<List<String>> list = Maps.MAP1.getList();
        assertNotNull(list);
        assertTrue(list.get(0).get(0).equals("H"));
        assertFalse(list.get(9).get(9).equals("H"));
    }
}