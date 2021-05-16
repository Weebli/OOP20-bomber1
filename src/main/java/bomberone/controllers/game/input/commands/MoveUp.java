package bomberone.controllers.game.input.commands;

import bomberone.model.common.Direction;
import bomberone.model.match.GameMatch;

public class MoveUp implements Command {

    Direction dir;

    public MoveUp() {
        this.dir = Direction.UP;
    }

    @Override
    public Direction dir() {
        return this.dir;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final GameMatch gameModel) {
        gameModel.getWorld().getBomber().moveUp();
        gameModel.getWorld().getBomber().setStatic(false);
    }

}