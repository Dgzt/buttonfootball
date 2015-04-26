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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.button.Button;
import com.dgzt.core.scoreboard.ScoreBoard;
import com.dgzt.core.setting.Settings;

/**
 * The game window.
 * 
 * @author Dgzt
 */
final public class GameWindow{
	
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
	
	/** The box2D world. */
	private final World box2DWorld;
	
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
	
	/** The ball area. */
	private final BallArea ballArea;
	
	/** The scale. */
	private double scale;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param spriteBatch - The sprite batch.s
	 * @param settings - The settings.
	 */
	public GameWindow(final ShaderProgram shader, final SpriteBatch spriteBatch, final Settings settings){
		// The box2D world
		box2DWorld = new World(new Vector2(0,0), true);
		// The event listener
		final EventListener eventListener = new EventListener(this);
		box2DWorld.setContactListener(eventListener);
		
		scoreBoard = new ScoreBoard(shader);
		table = new Table(shader, box2DWorld, eventListener);
		fps = new FPS(shader, spriteBatch);
		
		arrow = new Arrow(table, shader);
		
		ballArea = new BallArea(shader, table.getBall());
		
		gameControl = new GameControl(this, scoreBoard, table, settings);
		
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
		
		ballArea.resize(scale);
	}
	
	/**
	 * Draw the child objects.
	 */
	public void draw() {
		// Step the box2d world
		box2DWorld.step(1/60f, 6, 2);
		
		// Draw the shapes
		scoreBoard.draw();
		table.draw();
		fps.draw();
		
		arrow.draw();
		
		if(ballArea.isVisible()){
			ballArea.draw();
		}
	}
	
	/**
	 * Dispose the main window.
	 */
	public void dispose(){
		scoreBoard.dispose();
		table.dispose();
		arrow.dispose();
		ballArea.dispose();
		box2DWorld.dispose();
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
	 * Show the ball area with the given color.
	 * 
	 * @param color - The new color.
	 */
	public void showBallArea(final Color color){
		ballArea.resize(color, scale);
		
		ballArea.setVisible(true);
	}
	
	/**
	 * Hide the ball area.
	 */
	public void hideBallArea(){
		ballArea.setVisible(false);
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------

	/** 
	 * Return with the FPS. 
	 */
	public FPS getFPS() {
		return fps;
	}
	
	/**
	 * Return with the table.
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Return with the game control.
	 */
	public GameControl getGameControl() {
		return gameControl;
	}

}
