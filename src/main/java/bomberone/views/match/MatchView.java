package bomberone.views.match;

import bomberone.views.View;

/**
 * The View of the Match.
 *
 */
public interface MatchView extends View {

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
