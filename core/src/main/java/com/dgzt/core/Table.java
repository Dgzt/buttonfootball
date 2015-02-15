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

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.gate.LeftGate;
import com.dgzt.core.gate.RightGate;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleShape;
import com.dgzt.core.util.Box2DUtil;

/**
 * The table object.
 * 
 * @author Dgzt
 */
final public class Table extends RectangleShape{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width in cm. */
	public static final float WIDTH = 184.0f;
	
	/** The height in cm. */
	public static final float HEIGHT = 120.0f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The Box2D wall size in cm. */
	private static final float BOX2D_WALL_SIZE = 10.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The map. */
	private final Map map;
	
	/** The left gate. */
	private final LeftGate leftGate;
	
	/** The right gate. */
	private final RightGate rightGate;
	
	/** The player's buttons. */
	private final List<Button> playerButtons;
	
	/** The opponent's buttons. */
	private final List<Button> opponentButtons;
	
	/** The ball. */
	private final Ball ball;
	
	/** The arrow. */
	private final Arrow arrow;
	
	/** The box2d world. */
	private final World box2DWorld;
	
	/** The actual scale value. */
	private double scale;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param gameControl - The game control.
	 */
	public Table(final ShaderProgram shader, final GameControl gameControl){
		super(shader, Color.GRAY);
		
		box2DWorld = new World(new Vector2(0,0), true);
		box2DWorld.setContactListener(new MyContactListener(gameControl));
		addBox2DWalls();
		
		map = new Map(shader, box2DWorld, (Table.WIDTH - Map.WIDTH) / 2, (Table.HEIGHT - Map.HEIGHT) / 2);
		
		leftGate = new LeftGate(shader, box2DWorld, (Table.WIDTH - Map.WIDTH) / 2 - LeftGate.WIDTH + LineShape.LINE_WIDTH, (Table.HEIGHT - LeftGate.HEIGHT) / 2);
		rightGate = new RightGate(shader, box2DWorld, Table.WIDTH - (Table.WIDTH - Map.WIDTH) / 2  - LineShape.LINE_WIDTH, (Table.HEIGHT - RightGate.HEIGHT) / 2 );
		
		playerButtons = new ArrayList<Button>();
		opponentButtons = new ArrayList<Button>();
		
		addButtons(shader);
		
		ball = new Ball(this, shader, box2DWorld, Table.WIDTH / 2, Table.HEIGHT / 2);
		
		arrow = new Arrow(this, shader);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------

	/**
	 * Resize the table and the child objects.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param scale - The scale value.
	 */
	public void resize(final float x, final float y, final float width, final float height, final double scale) {
		super.resize(x, y, width, height);
		this.scale = scale;
		
		map.resize(x, y, scale);
		
		leftGate.resize(x, y, scale);
		
		rightGate.resize(x, y, scale);
		
		for(final Button playerButton : playerButtons){
			playerButton.resize();
		}
		
		for(final Button opponentButton : opponentButtons){
			opponentButton.resize();
		}
		
		ball.resize();
	}
	
	/**
	 * Setup the arrow if contains the given position a player button.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public void mouseButtonPressed(final float x, final float y){
		for(final Button playerButton : playerButtons){
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
		}
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * Draw the table and the child objects.
	 */
	@Override
	public void draw() {
		// Step the box2d world
		box2DWorld.step(1/60f, 6, 2);
		
		// Draw the shapes.
		super.draw();
		map.draw();
		
		for(final Button playerButton : playerButtons){
			playerButton.draw();
		}
		
		for(final Button opponentButton : opponentButtons){
			opponentButton.draw();
		}
		
		ball.draw();
		
		leftGate.draw();
		rightGate.draw();
		
		arrow.draw();
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Add buttons to the table.
	 * 
	 * @param shader - The shader.
	 */
	private void addButtons(final ShaderProgram shader){
		final float playerGoalKeeperX = ( Table.WIDTH - Map.WIDTH ) / 2 + Button.RADIUS;
		final float opponentGoalKeeperX = Table.WIDTH - ( Table.WIDTH - Map.WIDTH ) / 2 - Button.RADIUS;
		final float goalKeeperY = Table.HEIGHT / 2;
		final float buttonDistanceX = Table.WIDTH / 2 / 4;
		final float buttonDistanceY = Table.HEIGHT / 5;
		final float halfTableHeight = Table.HEIGHT / 2;
		
		// Add player buttons
		// ------------------
		
		// Add player goalkeeper
		playerButtons.add(new Button(this, shader, box2DWorld, Button.PLAYER_COLOR, playerGoalKeeperX, goalKeeperY));
		
		// Add player defenders
		for(int i=1; i <= 4; ++i){
			playerButtons.add(new Button(this, shader, box2DWorld, Button.PLAYER_COLOR, buttonDistanceX, i*buttonDistanceY));
		}
		
		// Add player midfielders
		for(int i=1; i <= 4; ++i){
			playerButtons.add(new Button(this, shader, box2DWorld, Button.PLAYER_COLOR, 2*buttonDistanceX, i*buttonDistanceY));
		}
		
		// Add player forwards
		playerButtons.add(new Button(this, shader, box2DWorld, Button.PLAYER_COLOR, 3*buttonDistanceX, halfTableHeight - buttonDistanceY));
		playerButtons.add(new Button(this, shader, box2DWorld, Button.PLAYER_COLOR, 3*buttonDistanceX, halfTableHeight + buttonDistanceY));
		
		// Add opponent buttons
		// --------------------
		
		// Add opponent goalkeeper
		opponentButtons.add(new Button(this, shader, box2DWorld, Button.OPPONENT_COLOR, opponentGoalKeeperX, goalKeeperY));
		
		// Add opponent defenders
		for(int i=1; i <= 4; ++i){
			opponentButtons.add(new Button(this, shader, box2DWorld, Button.OPPONENT_COLOR, Table.WIDTH - buttonDistanceX, i*buttonDistanceY));
		}
		
		// Add opponent midfielders
		for(int i=1; i <= 4; ++i){
			opponentButtons.add(new Button(this, shader, box2DWorld, Button.OPPONENT_COLOR, Table.WIDTH - 2*buttonDistanceX, i*buttonDistanceY));
		}
		
		// Add opponent forwards
		opponentButtons.add(new Button(this, shader, box2DWorld, Button.OPPONENT_COLOR, Table.WIDTH - 3*buttonDistanceX, halfTableHeight - buttonDistanceY));
		opponentButtons.add(new Button(this, shader, box2DWorld, Button.OPPONENT_COLOR, Table.WIDTH - 3*buttonDistanceX, halfTableHeight + buttonDistanceY));
	}
	
	/**
	 * Add the box2D walls of table.
	 */
	private void addBox2DWalls(){
		// Add top wall
		Box2DUtil.addWall(box2DWorld, 0, 0 - BOX2D_WALL_SIZE, Table.WIDTH, BOX2D_WALL_SIZE);
		
		// Add right wall
		Box2DUtil.addWall(box2DWorld, Table.WIDTH, 0, BOX2D_WALL_SIZE, Table.HEIGHT);
		
		// Add bottom wall
		Box2DUtil.addWall(box2DWorld, 0, Table.HEIGHT, Table.WIDTH, BOX2D_WALL_SIZE);
		
		// Add left Wall
		Box2DUtil.addWall(box2DWorld, 0 - BOX2D_WALL_SIZE, 0, BOX2D_WALL_SIZE, Table.HEIGHT);
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------
	
	/**
	 * Return with the actual scale value.
	 */
	public final double getScale(){
		return scale;
	}
}
