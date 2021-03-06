package bomberone.controllers;

import bomberone.model.GameModel;
import bomberone.views.View;

/**
 * An implementation of Controller.
 *
 */
public abstract class ControllerImpl implements Controller {

    private GameModel model;
    private View view;

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachView(final View view) {
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView() {
        return this.view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachModel(final GameModel model) {
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameModel getModel() {
        return this.model;
    }

    @Override
    public abstract void init();

}
