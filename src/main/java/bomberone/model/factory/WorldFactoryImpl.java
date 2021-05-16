package bomberone.model.factory;

import bomberone.model.match.Difficulty;
import bomberone.model.user.User;
import bomberone.model.world.World;
import bomberone.model.world.WorldImpl;

public class WorldFactoryImpl implements WorldFactory {

    @Override
    public final World createWorldStandard(final User user) {
        return new WorldImpl(Difficulty.STANDARD, user.getSkin());
    }

    @Override
    public final World createWorldHard(final User user) {
        return new WorldImpl(Difficulty.HARD, user.getSkin());
    }
}
