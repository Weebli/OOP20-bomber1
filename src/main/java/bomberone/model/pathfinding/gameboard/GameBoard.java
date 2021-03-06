package bomberone.model.pathfinding.gameboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * This object represent the playground of the game, specifically a current
 * status of the game ground. This object is used in the path finding enemy AI.
 */
public class GameBoard {

    /* Fields. */
    private int rows;
    private int columns;
    private ArrayList<ArrayList<BoardPoint>> currentGameBoard;

    /* Constructors. */
    public GameBoard(final int rowsToSet, final int columnsToSet) {
        this.rows = rowsToSet;
        this.columns = columnsToSet;
        this.currentGameBoard = new ArrayList<ArrayList<BoardPoint>>();

        // Setting the ground.
        for (int i = 0; i < this.rows; i++) {
            this.currentGameBoard.add(new ArrayList<BoardPoint>());
            for (int j = 0; j < this.columns; j++) {
                this.currentGameBoard.get(i).add(new BoardPointImpl(i, j, Markers.ACCESSIBLE));
            }
        }
    }

    public GameBoard(final List<List<String>> mapLayout) {
        this.rows = mapLayout.size();
        this.columns = mapLayout.get(0).size();
        this.currentGameBoard = new ArrayList<ArrayList<BoardPoint>>();

        // Setting the map.
        for (int i = 0; i < this.rows; i++) {
            this.currentGameBoard.add(new ArrayList<BoardPoint>());
            for (int j = 0; j < this.columns; j++) {

                BoardPoint newPoint = new BoardPointImpl(i, j, Markers.ACCESSIBLE);
                String item = mapLayout.get(i).get(j);

                for (Markers currentMarker : Markers.values()) {
                    if (String.valueOf(currentMarker.getValue()).equals(item)) {
                        newPoint.setMarker(currentMarker);
                        break;
                    }
                }

                this.currentGameBoard.get(i).add(newPoint);
            }
        }
    }

    /* Methods. */

    /**
     * Gets the game board as an ArrayList<ArrayList<BoardPoint>>. The first
     * ArrayList represents the rows of the game board. Every row of the game board
     * is filled with BoardPoint objects that represent the current status of a
     * specific tile.
     * 
     * @return the game board as an ArrayList<ArrayList<BoardPoint>>.
     */
    public ArrayList<ArrayList<BoardPoint>> getGameBoard() {
        return this.currentGameBoard;
    }

    /**
     * This method sets the game board.
     * 
     * @param gameBoard the new game board.
     */
    public void setGameBoard(final ArrayList<ArrayList<BoardPoint>> gameBoard) {
        this.currentGameBoard = gameBoard;
    }

    /**
     * This method gets the game board's rows number.
     * 
     * @return the rows number.
     */
    public int getRowsQuantity() {
        return this.rows;
    }

    /**
     * This method gets the gameboard's columns number.
     * 
     * @return the columns number
     */
    public int getColumnsQuantity() {
        return this.columns;
    }

    /**
     * This method finds the spot position in the game board.
     * 
     * @return the spot position.
     */
    public Optional<BoardPoint> findSpotLocation() {
        return this.searchMarker(Markers.SPOT);
    }

    /**
     * This method checks if the arguments passed could be an actual position.
     * 
     * @param x the first coordinate.
     * @param y the second coordinate.
     * @return true if the position is in the game board boundaries, otherwise
     *         false.
     */
    public boolean isLegal(final int x, final int y) {
        return (x >= 0 && x < this.rows) && (y >= 0 && y < this.columns);
    }

    /**
     * This method gets an item at the position specified by the arguments.
     * 
     * @param x the first coordinate of the board point.
     * @param y the second coordinate of the board point.
     * @return the BoardPoint object.
     */
    public Optional<BoardPoint> getItem(final int x, final int y) {
        return this.isLegal(x, y) ? Optional.of(this.currentGameBoard.get(x).get(y)) : Optional.empty();
    }

    /**
     * This method sets an item at the position specified by the arguments.
     * 
     * @param newPoint The point to set.
     * @return true if the item is valid, otherwise false.
     */
    public boolean setItem(final BoardPoint newPoint) {
        boolean result = this.isLegal(newPoint.getX(), newPoint.getY());
        if (result) {
            this.currentGameBoard.get(newPoint.getX()).set(newPoint.getY(), newPoint);
        }

        return result;
    }

    /**
     * This method sets some new BoardPoints object to some specified positions.
     * 
     * @param positions A List of BoardPoint, so the positions where the marker has
     *                  to be set.
     */
    public void setItems(final List<BoardPoint> positions) {
        for (BoardPoint currentPosition : positions) {
            this.setItem(currentPosition);
        }
    }

    /**
     * This method checks if the position specified by the arguments is accessible,
     * so checks if the marker in the position is a walkable marker.
     * 
     * @param x The first coordinate of the point to check.
     * @param y The second coordinate of the point to check.
     * @return true if the marker in the position is equal to the ground marker,
     *         otherwise false.
     */
    public boolean isAccessible(final int x, final int y) {
        boolean result = this.isLegal(x, y);
        Markers marker = this.currentGameBoard.get(x).get(y).getMarker();
        boolean secondResult = marker == Markers.ACCESSIBLE || marker == Markers.SPOT;

        return result && secondResult;
    }

    /**
     * This method checks if two points are on the same row or column and if the
     * position between them are all accessible.
     * 
     * @param currentPoint A point of the game board.
     * @param destination  A destination point.
     * @param mode         The checking policy which specify if the accessibility
     *                     has to be checked in row or in column.
     * @return true is the positions between the points are all accessible,
     *         otherwise false.
     */
    private boolean checkAccessibility(final BoardPoint currentPoint, final BoardPoint destination,
            final Accessibility mode) {
        int k;
        int m;
        int xToCheck = 0;
        int yToCheck = 0;
        boolean result = false;
        int currentCoordinates;
        int destinationCoordinates;

        if (mode.equals(Accessibility.ROWS)) {
            currentCoordinates = currentPoint.getY();
            destinationCoordinates = destination.getY();
            k = Math.min(currentPoint.getX(), destination.getX());
            m = Math.max(currentPoint.getX(), destination.getX());
        } else {
            currentCoordinates = currentPoint.getX();
            destinationCoordinates = destination.getX();
            k = Math.min(currentPoint.getY(), destination.getY());
            m = Math.max(currentPoint.getY(), destination.getY());
        }

        if (currentCoordinates == destinationCoordinates) {
            while (k <= m) {

                xToCheck = mode.equals(Accessibility.ROWS) ? k : currentCoordinates;
                yToCheck = mode.equals(Accessibility.ROWS) ? currentCoordinates : k;
                result = this.isAccessible(xToCheck, yToCheck);

                if (!result) {
                    break;
                }

                k++;
            }
        }

        return result;
    }

    /**
     * This method checks if the BoardPoint marked as a spot is visible from the
     * position passed as arguments.
     * 
     * @param x The first coordinate of the current position.
     * @param y The second coordinate of the current position.
     * @return true if the spot is on the same row or column of the current position
     *         and all the BoardPoint objects are marked as accessible.
     */
    public boolean isSpotVisible(final int x, final int y) {
        boolean result = false;
        Optional<BoardPoint> currentPosition = this.getItem(x, y);
        Optional<BoardPoint> spotLocation = this.findSpotLocation();

        if (!spotLocation.isEmpty() && !currentPosition.isEmpty()) {
            result = this.checkAccessibility(currentPosition.get(), spotLocation.get(), Accessibility.ROWS)
                    || this.checkAccessibility(currentPosition.get(), spotLocation.get(), Accessibility.COLUMNS);
        }

        return result;
    }

    /**
     * This method sets the player position in the game board.
     * 
     * @param x The new coordinate x of the position of the player.
     * @param y The new coordinate x of the position of the player.
     */
    public void setSpotLocation(final int x, final int y) {
        this.resetItem(Markers.SPOT);
        this.setItem(new BoardPointImpl(x, y, Markers.SPOT));
    }

    /**
     * This method searches the marker passed as a parameter.
     * 
     * @param markerToFind The marker to find in the game board.
     * @return a Optional<BoardPoint> object, which contains the position of the
     *         marker passed as argument, if it is found, otherwise an empty
     *         Optional object.
     */
    public Optional<BoardPoint> searchMarker(final Markers markerToFind) {
        Optional<BoardPoint> position = Optional.empty();
        Iterator<ArrayList<BoardPoint>> rowsIterator = this.currentGameBoard.iterator();

        while (rowsIterator.hasNext()) {
            Iterator<BoardPoint> itemIterator = rowsIterator.next().iterator();
            while (itemIterator.hasNext()) {
                BoardPoint currentItem = itemIterator.next();
                if (currentItem.getMarker() == markerToFind) {
                    position = Optional.of(currentItem);
                    break;
                }
            }
        }

        return position;
    }

    /**
     * This method changes the first occurrence of the marker specified as the first
     * argument to the marker passed as second argument.
     * 
     * @param markerToChange The marker to change.
     * @param newMarker      The new marker.
     * @return true if the marker if the marker is present in the game board and it
     *         is changed, otherwise false.
     */
    public boolean changeItem(final Markers markerToChange, final Markers newMarker) {
        boolean result = true;
        Optional<BoardPoint> itemToChange = this.searchMarker(markerToChange);
        if (itemToChange.isEmpty()) {
            result = false;
        } else {
            BoardPoint newItem = itemToChange.get();
            newItem.setMarker(newMarker);
            this.setItem(newItem);
        }

        return result;
    }

    /**
     * This method changes all the occurrences of the marker that are equal to the
     * marker passed as first argument to the maker passed as second argument.
     * 
     * @param markerToChange The marker to change.
     * @param newMarker      The new Marker.
     */
    public void changeAllItems(final Markers markerToChange, final Markers newMarker) {
        boolean result;

        do {
            result = this.changeItem(markerToChange, newMarker);
        } while (result);
    }

    /**
     * This method set all BoardPoint objects marker to the new marker passed as
     * second argument.
     * 
     * @param points    A List<BoardPoint> objects that have to get the marker
     *                  changed.
     * 
     */
    public void changeAllItems(final List<BoardPoint> points) {
        boolean result;

        for (BoardPoint currentPoint : points) {
            result = this.setItem(currentPoint);
            if (!result) {
                break;
            }
        }
    }

    /**
     * This method changes the first occurrence of the marker passed as argument to
     * a ground marker.
     * 
     * @param markerToChange The marker to change
     */
    public void resetItem(final Markers markerToChange) {
        this.changeItem(markerToChange, Markers.ACCESSIBLE);
    }

    /**
     * This method sets the item at the specified position as accessible.
     * 
     * @param x The first coordinate.
     * @param y The second coordinate.
     */
    public void resetItem(final int x, final int y) {
        this.setItem(new BoardPointImpl(x, y, Markers.ACCESSIBLE));
    }

    /**
     * This method changes all the occurrences of the marker passed as a argument to
     * a ground marker.
     * 
     * @param markerToChange The maker to change.
     */
    public void resetAllItems(final Markers markerToChange) {
        this.changeAllItems(markerToChange, Markers.ACCESSIBLE);
    }
}
