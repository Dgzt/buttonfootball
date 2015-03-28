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
	
	/** The actual scale value. */
	private double scale;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param box2DWorld - The box2D world.
	 * @param eventListener - The event listener.
	 */
	public Table(final ShaderProgram shader, final World box2DWorld, final EventListener eventListener){
		super(shader, Color.GRAY);
		
		addBox2DWalls(box2DWorld);
		
		map = new Map(shader, box2DWorld, (Table.WIDTH - Map.WIDTH) / 2, (Table.HEIGHT - Map.HEIGHT) / 2);
		
		leftGate = new LeftGate(shader, box2DWorld, (Table.WIDTH - Map.WIDTH) / 2 - LeftGate.WIDTH + LineShape.LINE_WIDTH, (Table.HEIGHT - LeftGate.HEIGHT) / 2);
		rightGate = new RightGate(shader, box2DWorld, Table.WIDTH - (Table.WIDTH - Map.WIDTH) / 2  - LineShape.LINE_WIDTH, (Table.HEIGHT - RightGate.HEIGHT) / 2 );
		
		playerButtons = new ArrayList<Button>();
		opponentButtons = new ArrayList<Button>();
		
		addButtons(shader, eventListener, box2DWorld);
		
		ball = new Ball(this, shader, eventListener, box2DWorld, Table.WIDTH / 2, Table.HEIGHT / 2);
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
	 * Move the given player's buttons to the left part of the map.
	 * 
	 * @param player - The player.
	 */
	public void moveButtonsToLeftPartOfMap(final Player player){
		final float goalKeeperX = ( Table.WIDTH - Map.WIDTH ) / 2 + Button.RADIUS;
		final float goalKeeperY = Table.HEIGHT / 2;
		final float buttonDistanceX = Table.WIDTH / 2 / 4;
		final float buttonDistanceY = Table.HEIGHT / 5;
		final float halfTableHeight = Table.HEIGHT / 2;

		final List<Button> buttons = player.equals(Player.PLAYER) ? playerButtons : opponentButtons;

		// Add left goalkeeper
		buttons.get(0).setBox2DPosition(goalKeeperX, goalKeeperY);
		
		// Add left defenders
		for(int i=1; i <= 4; ++i){
			buttons.get(i).setBox2DPosition(buttonDistanceX, i*buttonDistanceY);
		}
		
		// Add left midfielders
		for(int i=1; i <= 4; ++i){
			buttons.get(i+4).setBox2DPosition(2*buttonDistanceX, i*buttonDistanceY);
		}
		
		// Add left forwards
		buttons.get(9).setBox2DPosition(3*buttonDistanceX, halfTableHeight - buttonDistanceY);
		buttons.get(10).setBox2DPosition(3*buttonDistanceX, halfTableHeight + buttonDistanceY);
	}
	
	/**
	 * Move the given player's buttons to the right part of the map.
	 * 
	 * @param player - The player.
	 */
	public void moveButtonsToRightPartOfMap(final Player player){
		final float goalKeeperX = Table.WIDTH - ( Table.WIDTH - Map.WIDTH ) / 2 - Button.RADIUS;
		final float goalKeeperY = Table.HEIGHT / 2;
		final float buttonDistanceX = Table.WIDTH / 2 / 4;
		final float buttonDistanceY = Table.HEIGHT / 5;
		final float halfTableHeight = Table.HEIGHT / 2;
		
		final List<Button> buttons = player.equals(Player.PLAYER) ? playerButtons : opponentButtons;
		
		// Add right goalkeeper
		buttons.get(0).setBox2DPosition(goalKeeperX, goalKeeperY);
				
		// Add right defenders
		for(int i=1; i <= 4; ++i){
			buttons.get(i).setBox2DPosition(Table.WIDTH - buttonDistanceX, i*buttonDistanceY);
		}
		
		// Add right midfielders
		for(int i=1; i <= 4; ++i){
			buttons.get(i+4).setBox2DPosition(Table.WIDTH - 2*buttonDistanceX, i*buttonDistanceY);
		}
		
		// Add right forwards
		buttons.get(9).setBox2DPosition(Table.WIDTH - 3*buttonDistanceX, halfTableHeight - buttonDistanceY);
		buttons.get(10).setBox2DPosition(Table.WIDTH - 3*buttonDistanceX, halfTableHeight + buttonDistanceY);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * Draw the table and the child objects.
	 */
	@Override
	public void draw() {
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
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(){
		map.dispose();
		leftGate.dispose();
		rightGate.dispose();
		
		for(final Button playerButton : playerButtons){
			playerButton.dispose();
		}
		for(final Button opponentButton : opponentButtons){
			opponentButton.dispose();
		}
		
		ball.dispose();
		
		super.dispose();
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Add buttons to the map.
	 * 
	 * @param shader - The shader.
	 * @param eventListener - The event listener.
	 * @param box2DWorld - The box2D world.
	 */
	private void addButtons(final ShaderProgram shader, final EventListener eventListener, final World box2DWorld){
		// The player's buttons.
		for(int i = 0; i < 11; ++i){
			playerButtons.add(new Button(this, shader, eventListener, box2DWorld, Button.PLAYER_COLOR));
		}
		
		// The opponent's buttons.
		for(int i = 0; i < 11; ++i){
			opponentButtons.add(new Button(this, shader, eventListener, box2DWorld, Button.OPPONENT_COLOR));
		}
	}
	
	/**
	 * Add the box2D walls of table.
	 * 
	 * @param box2DWorld - The box2D world.
	 */
	private void addBox2DWalls(final World box2DWorld){
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
	 * Return with the left gate.
	 */
	public LeftGate getLeftGate() {
		return leftGate;
	}

	/**
	 * Return with the right gate.
	 */
	public RightGate getRightGate() {
		return rightGate;
	}
	
	/**
	 * Return with the player's buttons.
	 */
	public List<Button> getPlayerButtons() {
		return playerButtons;
	}
	
	/**
	 * Return with the opponent's buttons.
	 */
	public List<Button> getOpponentButtons() {
		return opponentButtons;
	}
	
	/**
	 * Return with the ball.
	 */
	public Ball getBall(){
		return ball;
	}
	
	/**
	 * Return with the actual scale value.
	 */
	public final double getScale(){
		return scale;
	}

}
