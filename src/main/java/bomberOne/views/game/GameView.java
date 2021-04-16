package bomberOne.views.game;

import bomberOne.views.View;

public interface GameView extends View {

    /**
     * Prepare the world with the walls and the background.
     */
    void drawGame();

    /**
     * Render the view every Frame.
     */
    void render();

    /**
     * When the Game ends, it switch the View to the RankView.
     */
    void switchToRank();
}
