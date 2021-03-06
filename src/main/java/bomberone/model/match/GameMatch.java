package bomberone.model.match;

import bomberone.model.timer.Timer;
import bomberone.model.timer.TimerThread;
import bomberone.model.user.User;
import bomberone.model.world.World;

/**
 * The Match of the Game.
 * 
 */
public interface GameMatch {

    /**
     * Initialize the entire game world.
     */
    void init();

    /**
     * set the User of this game.
     * @param user
     */
    void setUser(User user);

    /**
     * 
     * @return the user
     */
    User getUser();

    /**
     * Initialize the difficulty of the Match.
     * 
     * @param difficulty
     */
    void setDifficulty(Difficulty difficulty);

    /**
     * 
     * @return the Difficulty of the Game.
     */
    Difficulty getDifficulty();

    /**
     * 
     * @return the World of this game
     */
    World getWorld();

    /**
     * 
     * @return the current score.
     */
    int getScore();

    /**
     * Setting the World.
     * 
     * @param world
     */
    void setWorld(World world);

    /**
     * Decrease the current score by the param dec.
     * 
     * @param dec
     */
    void decScore(int dec);

    /**
     * Increase the current score by the param inc.
     * 
     * @param inc
     */
    void incScore(int inc);

    /**
     * This method recall the method updateState of the World.
     * @param elapsed
     */
    void updateGame(int elapsed);

    /**
     * This method sets the boolean gameOver.
     * @param gameOver
     */
    void setGameOver(boolean gameOver);

    /**
     * @return the boolean gameOver
     */
    boolean getGameOver();

    /**
     * This method check if the boolean gameOver must be setted to true.
     */
    void checkGameOver();

    /**
     * @return timer
     */
    Timer getTimer();

    /**
     * 
     * @return timer thread
     */
    TimerThread getTimerThread();
}
