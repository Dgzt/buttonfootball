/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.dgzt.core.scoreboard;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dgzt.core.shape.Shape;

/**
 * The scoreboard.
 * 
 * @author Dgzt
 */
public class ScoreBoard implements Shape{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width value in cm. */
	public static final float WIDTH = 2*GoalBoard.WIDTH + TimeBoard.WIDTH + Shape.LINE_WIDTH;
	
	/** The height value in cm. */
	public static final float HEIGHT = HalfTimeBoard.HEIGHT + TimeBoard.HEIGHT;

	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The half time board. */
	private final HalfTimeBoard halfTimeBoard;
	
	/** The time board. */
	private final TimeBoard timeBoard;
	
	/** The player's goal board. */
	private final GoalBoard playerGoalBoard;
	
	/** The opponent's goal board. */
	private final GoalBoard opponentGoalBoard;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public ScoreBoard(final ShapeRenderer shapeRenderer){
		halfTimeBoard = new HalfTimeBoard(shapeRenderer);
		
		timeBoard = new TimeBoard(shapeRenderer);
		playerGoalBoard = new GoalBoard(shapeRenderer);
		opponentGoalBoard = new GoalBoard(shapeRenderer);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the shape.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param scale - The scale value.
	 */
	public void resize(final float x, final float y, final float width, final float height, final double scale){
		final float halfLineWidth = (float)(Shape.LINE_WIDTH * scale) / 2;
		final float halfTimeBoardWidth = (float)(HalfTimeBoard.WIDTH * scale);
		final float halfTimeBoardHeight = (float)(HalfTimeBoard.HEIGHT * scale);
		final float goalBoardWidth = (float)(GoalBoard.WIDTH * scale);
		final float goalBoardHeight = (float)(GoalBoard.HEIGHT * scale);
		final float timeBoardWidth = (float)(TimeBoard.WIDTH * scale);
		final float timeBoardHeight = (float)(TimeBoard.HEIGHT * scale);
		
		halfTimeBoard.resize(x + (width - halfTimeBoardWidth)/2, y, halfTimeBoardWidth, halfTimeBoardHeight, scale);
		playerGoalBoard.resize(x + halfLineWidth, y + height - goalBoardHeight, goalBoardWidth, goalBoardHeight, scale);
		timeBoard.resize(playerGoalBoard.getX() + playerGoalBoard.getWidth() + halfLineWidth, y + halfTimeBoardHeight, timeBoardWidth, timeBoardHeight, scale);
		opponentGoalBoard.resize(timeBoard.getX() + timeBoard.getWidth() + halfLineWidth, playerGoalBoard.getY(), goalBoardWidth, goalBoardHeight, scale);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		halfTimeBoard.draw();
		playerGoalBoard.draw();
		timeBoard.draw();
		opponentGoalBoard.draw();
	}

}
