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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.button.Button;

/**
 * The scoreboard.
 * 
 * @author Dgzt
 */
public class ScoreBoard{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width value in cm. */
	public static final float WIDTH = 2*GoalBoard.WIDTH + TimeBoard.WIDTH;
	
	/** The height value in cm. */
	public static final float HEIGHT = HalfTimeBoard.HEIGHT + TimeBoard.HEIGHT;

	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The color. */
	private static final Color COLOR = Color.WHITE;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The half time board. */
	private final HalfTimeBoard halfTimeBoard;
	
	/** The time board. */
	private final TimeBoard timeBoard;
	
	/** The player's time left board. */
	private final TimeLeftBoard playerTimeLeftBoard;
	
	/** The player's goal board. */
	private final GoalBoard playerGoalBoard;
	
	/** The opponent's time left board. */
	private final TimeLeftBoard opponentTimeLeftBoard;
	
	/** The opponent's goal board. */
	private final GoalBoard opponentGoalBoard;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 */
	public ScoreBoard(final ShaderProgram shader){
		halfTimeBoard = new HalfTimeBoard(shader, COLOR);
		
		timeBoard = new TimeBoard(shader, COLOR);
		playerTimeLeftBoard = new TimeLeftBoard(shader, COLOR, Button.PLAYER_COLOR);
		playerGoalBoard = new GoalBoard(shader, COLOR, Button.PLAYER_COLOR);
		opponentTimeLeftBoard = new TimeLeftBoard(shader, COLOR, Button.OPPONENT_COLOR);
		opponentGoalBoard = new GoalBoard(shader, COLOR, Button.OPPONENT_COLOR);
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
		final float halfTimeBoardWidth = (float)(HalfTimeBoard.WIDTH * scale);
		final float halfTimeBoardHeight = (float)(HalfTimeBoard.HEIGHT * scale);
		final float timeLeftBoardWidth = (float)(TimeLeftBoard.WIDTH * scale);
		final float timeLeftBoardHeight = (float)(TimeLeftBoard.HEIGHT * scale);
		final float goalBoardWidth = (float)(GoalBoard.WIDTH * scale);
		final float goalBoardHeight = (float)(GoalBoard.HEIGHT * scale);
		final float timeBoardWidth = (float)(TimeBoard.WIDTH * scale);
		final float timeBoardHeight = (float)(TimeBoard.HEIGHT * scale);
		
		halfTimeBoard.resize(x + (width - halfTimeBoardWidth)/2, y, halfTimeBoardWidth, halfTimeBoardHeight, scale);
		playerTimeLeftBoard.resize(x, y + height - goalBoardHeight - timeLeftBoardHeight, timeLeftBoardWidth, timeLeftBoardHeight, scale);
		playerGoalBoard.resize(x, y + height - goalBoardHeight, goalBoardWidth, goalBoardHeight, scale);
		timeBoard.resize(playerGoalBoard.getX() + playerGoalBoard.getWidth(), y + halfTimeBoardHeight, timeBoardWidth, timeBoardHeight, scale);
		opponentTimeLeftBoard.resize(timeBoard.getX() + timeBoard.getWidth(), playerTimeLeftBoard.getY(), timeLeftBoardWidth, timeLeftBoardHeight, scale);
		opponentGoalBoard.resize(timeBoard.getX() + timeBoard.getWidth(), playerGoalBoard.getY(), goalBoardWidth, goalBoardHeight, scale);
	}
	
	/**
	 * Draw the scoreboard.
	 */
	public void draw() {
		halfTimeBoard.draw();
		if(playerTimeLeftBoard.isVisible()){
			playerTimeLeftBoard.draw();
		}
		playerGoalBoard.draw();
		timeBoard.draw();
		if(opponentTimeLeftBoard.isVisible()){
			opponentTimeLeftBoard.draw();
		}
		opponentGoalBoard.draw();
	}
	
	/**
	 * Dispose the score board.
	 */
	public void dispose(){
		halfTimeBoard.dispose();
		playerTimeLeftBoard.dispose();
		playerGoalBoard.dispose();
		timeBoard.dispose();
		opponentTimeLeftBoard.dispose();
		opponentGoalBoard.dispose();
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------

	/**
	 * Return with the player's time left board.
	 */
	public TimeLeftBoard getPlayerTimeLeftBoard() {
		return playerTimeLeftBoard;
	}
	
	/**
	 * Get the player's goal board.
	 */
	public GoalBoard getPlayerGoalBoard() {
		return playerGoalBoard;
	}
	
	/**
	 * Return with the opponent's time left board.
	 */
	public TimeLeftBoard getOpponentTimeLeftBoard() {
		return opponentTimeLeftBoard;
	}

	/**
	 * Get the opponent's goal board.
	 */
	public GoalBoard getOpponentGoalBoard() {
		return opponentGoalBoard;
	}

	/**
	 * Return with the time board.
	 */
	public TimeBoard getTimeBoard() {
		return timeBoard;
	}
	
	/**
	 * Return with the half time board.
	 */
	public HalfTimeBoard getHalfTimeBoard(){
		return halfTimeBoard;
	}
	
}
