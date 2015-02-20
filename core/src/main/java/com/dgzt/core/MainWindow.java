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
package com.dgzt.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.button.Button;
import com.dgzt.core.scoreboard.ScoreBoard;

/**
 * The main window.
 * 
 * @author Dgzt
 */
final public class MainWindow{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width of main window in cm. */
	public static final float WIDTH = Table.WIDTH;

	/** The height of main window in cm. */
	public static final float HEIGHT = ScoreBoard.HEIGHT +Table.HEIGHT;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The game control. */
	private final GameControl gameControl;
	
	/** The score board. */
	private final ScoreBoard scoreBoard;
	
	/** The table. */
	private final Table table;
	
	/** The frame per second rectangle. */
	private final FPS fps;
	
	/** The arrow. */
	private final Arrow arrow;
	
	/** The ball area for player. */
	private final BallArea playerBallArea;
	
	/** The scale. */
	private double scale;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param spriteBatch - The sprite batch.
	 */
	public MainWindow(final ShaderProgram shader, final SpriteBatch spriteBatch){
		gameControl = new GameControl(this);
		
		scoreBoard = new ScoreBoard(shader);
		table = new Table(shader, gameControl);
		fps = new FPS(shader, spriteBatch);
		
		arrow = new Arrow(table, shader);
		
		playerBallArea = new BallArea(shader, Button.PLAYER_COLOR);
		
		Gdx.input.setInputProcessor(new InputListener(this, gameControl));
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the child objects.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param scale - The scale value.
	 */
	public void resize(final float x, final float y, final float width, final double scale){
		this.scale = scale;
		
		final float scoreBoardWidth = (float)(ScoreBoard.WIDTH * scale);
		final float scoreBoardHeight = (float)(ScoreBoard.HEIGHT * scale);
		final float scoreBoardX = x + (width - scoreBoardWidth) / 2;
		final float scoreBoardY = y;
		
		final float tableWidth = (float)(Table.WIDTH * scale);
		final float tableHeight = (float)(Table.HEIGHT * scale);
		final float tableX = x  + (width - tableWidth) / 2;
		final float tableY = scoreBoardY + scoreBoardHeight;
		
		final float fpsWidth = FPS.WIDTH;
		final float fpsX = x + width - fpsWidth;
		final float fpsY = y;
		
		scoreBoard.resize(scoreBoardX, scoreBoardY, scoreBoardWidth, scoreBoardHeight, scale);
		table.resize(tableX, tableY, tableWidth, tableHeight, scale);
		fps.resize(fpsX, fpsY);
		
		playerBallArea.resize(table.getBall(), scale);
	}
	
	/**
	 * Draw the child objects.
	 */
	public void draw() {
		scoreBoard.draw();
		table.draw();
		fps.draw();
		
		arrow.draw();
		
		if(playerBallArea.isVisible()){
			playerBallArea.draw();
		}
	}
	
	/**
	 * Setup the arrow if contains the given position a player button.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public void mouseButtonPressed(final float x, final float y){
		for(final Button playerButton : table.getPlayerButtons()){
			if(playerButton.contains(x, y)){
				arrow.show(playerButton);
			}
		}
	}
	
	/**
	 * Set the new ends point of the arrow.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public void mouseButtonMoved(final float x, final float y){
		arrow.setEndPoint(x, y);
	}
	
	/**
	 * Move the selected button and hide the arrow if the arrow is visible.
	 */
	public void mouseButtonReleased(){
		if(arrow.isVisible()){
			arrow.hide();
			
			final Button movingButton = arrow.getLastSelectedButton();
			movingButton.move(arrow.getX1() - arrow.getX2(), arrow.getY1() - arrow.getY2());
			gameControl.playerStepped();
		}
	}
	
	/**
	 * Show the player's ball area.
	 */
	public void showPlayerBallArea(){
		playerBallArea.resize(table.getBall(), scale);
		
		playerBallArea.setVisible(true);
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------
	
	/**
	 * Return with the score board.
	 */
	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}

	/** Return with the FPS. */
	public FPS getFPS() {
		return fps;
	}

}
