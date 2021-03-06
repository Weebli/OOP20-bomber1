package bomberone.controllers.match.input.commands;

import bomberone.model.common.Direction;
import bomberone.model.match.GameMatch;

public class MoveRight implements Command {

    private Direction dir;

    public MoveRight() {
        this.dir = Direction.RIGHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction dir() {
        return this.dir;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final GameMatch gameMatch) {
        gameMatch.getWorld().getBomber().moveRight();
        gameMatch.getWorld().getBomber().setStatic(false);
    }

}

