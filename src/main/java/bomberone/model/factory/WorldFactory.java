package bomberone.model.factory;

import bomberone.model.user.User;
import bomberone.model.world.World;

public interface WorldFactory {

    /**
     * This method creates the World for easy Difficulty.
     * @param user The user of the game
     * @return the easy World
     */
    World createWorldStandard(User user);

    /**
     * This method creates the World for hard Difficulty.
     * @param user The user of the game
     * @return the hard World
     */
    World createWorldHard(User user);
}
