package bomberone.controllers.match.input;


import bomberone.controllers.match.input.commands.MoveDown;
import bomberone.controllers.match.input.commands.MoveLeft;
import bomberone.controllers.match.input.commands.MoveRight;
import bomberone.controllers.match.input.commands.MoveUp;
import bomberone.controllers.match.input.commands.PlantBomb;
import bomberone.model.match.GameMatch;

/**
 * That class is the CommandListner of the game.
 *
 */
public class CommandListenerImpl implements CommandListener {
    private GameMatch game;
    private PlayerBehavior behaviour;
    private boolean isActionCompleted;

    public CommandListenerImpl() {
        this.behaviour = new PlayerBehavior();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void executeCommands() {
        if (this.behaviour.isToggleDownPressed()) {
            new MoveDown().execute(game);
        }
        if (this.behaviour.isToggleUpPressed()) {
            new MoveUp().execute(game);
        }
        if (this.behaviour.isToggleLeftPressed()) {
            new MoveLeft().execute(game);
        }
        if (this.behaviour.isToggleRightPressed()) {
            new MoveRight().execute(game);
        }
        if (!this.behaviour.isToggleLeftPressed() && !this.behaviour.isToggleDownPressed() && !this.behaviour.isToggleRightPressed() && !this.behaviour.isToggleUpPressed()) {
            this.getGameMatch().getWorld().getBomber().setStatic(true);
        }
        if (this.behaviour.isToggleActionPressed()) {
            if (!this.isActionCompleted) {
                new PlantBomb().execute(game);
                this.isActionCompleted = true;
            }
        } else {
            this.isActionCompleted = false;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameMatch(final GameMatch game) {
        this.game = game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameMatch getGameMatch() {
        return this.game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerBehavior getPlayerBehaviour() {
        return this.behaviour;
    }
}
