package bomberOne.controllers.game.input;

import java.util.List;

import bomberOne.model.GameModel;
import bomberOne.controllers.game.input.commands.Command;

public interface CommandListener {

    /**
     * This method add the "command" to the CommandList.
     * 
     * @param command
     */
    void addCommand(Command command);

    /**
     * Method that return the CommandList.
     * 
     * @return commandList
     */
    List<Command> getCommandList();

    /**
     * Scroll all the commandList and call the method execute() on all the command
     * of the commandList.
     */
    void executeCommands();

    /**
     * Attach the "game" at the GameModel.
     * 
     * @param game
     */
    void setGameModel(GameModel game);

    /**
     * Method that return the GameModel attached.
     * 
     * @return GameModel
     */
    GameModel getGameModel();

    /**
     * 
     * @return the PlayerBeaviour
     */
    PlayerBehavior getPlayerBehaviour();
}