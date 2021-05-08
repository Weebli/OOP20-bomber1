package bomberOne.model;

/**
 * The difficulty of the gameplay, set by the Player when start the game.
 *
 */
public enum Difficulty {

    /**
     * 
     */
    STANDARD(80),

    /**
     * 
     */
    HARD(100);

    private int numBox;

    Difficulty(final int num) {
        this.numBox = num;
    }

    public int getNumBox() {
        return this.numBox;
    }
}