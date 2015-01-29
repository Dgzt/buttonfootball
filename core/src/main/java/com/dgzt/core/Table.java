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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleShape;
import com.dgzt.core.util.SensorUserDataEnum;

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
	private final Gate leftGate;
	
	/** The right gate. */
	private final Gate rightGate;
	
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
	 */
	public Table(final ShaderProgram shader){
		super(shader, Color.GRAY);
		
		box2DWorld = new World(new Vector2(0,0), true);
		box2DWorld.setContactListener(new MyContactListener());
		addBox2DTableWalls();
		addBox2DGateWalls();
		addBox2DGateSensors();
		addBox2DMapSensors();
		
		map = new Map(shader);
		
		leftGate = new Gate(shader);
		rightGate = new Gate(shader);
		
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
		
		resizeMap(x, y, width, height, scale);
		
		resizeLeftGate(y, height, map.getX(), scale);
		
		resizeRightGate(map.getX(), map.getWidth(), leftGate.getY(), leftGate.getWidth(), leftGate.getHeight(), scale);
		
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
	 * Add box2D rectangle.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param isSensor - Is the rectangle sensor?
	 * @param userData - The user data of fixture.
	 */
	private void addBox2DRectangle(final float x, final float y, final float width, final float height, final boolean isSensor, final SensorUserDataEnum userData){
		final BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x + width / 2, y + height / 2);
		
		final Body body = box2DWorld.createBody(bodyDef);
		
		final PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.isSensor = isSensor;
		final Fixture fixture = body.createFixture(fixtureDef);
		fixture.setUserData(userData);
	}
	
	/**
	 * Add box2D wall.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 */
	private void addBox2DWall(final float x, final float y, final float width, final float height){
		addBox2DRectangle(x, y, width, height, false, null);
	}
	
	/**
	 * Add box2D rectangle sensor.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The x coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param userData - The user data of fixture.
	 */
	private void addBox2DSensor(final float x, final float y, final float width, final float height, final SensorUserDataEnum userData){
		addBox2DRectangle(x, y, width, height, true, userData);
	}
	
	/**
	 * Add the box2D walls of table.
	 */
	private void addBox2DTableWalls(){
		// Add top wall
		addBox2DWall(0, 0 - BOX2D_WALL_SIZE, Table.WIDTH, BOX2D_WALL_SIZE);
		
		// Add right wall
		addBox2DWall(Table.WIDTH, 0, BOX2D_WALL_SIZE, Table.HEIGHT);
		
		// Add bottom wall
		addBox2DWall(0, Table.HEIGHT, Table.WIDTH, BOX2D_WALL_SIZE);
		
		// Add left Wall
		addBox2DWall(0 - BOX2D_WALL_SIZE, 0, BOX2D_WALL_SIZE, Table.HEIGHT);
	}
	
	/**
	 * Add the box2D walls of gates.
	 */
	private void addBox2DGateWalls(){
		final float leftGateX = 0;
		final float rightGateX = Table.WIDTH - Gate.WIDTH;
		final float topWallY = (Table.HEIGHT - Gate.HEIGHT - LineShape.LINE_WIDTH) / 2;
		final float bottomWallY = (Table.HEIGHT + Gate.HEIGHT - LineShape.LINE_WIDTH) / 2;
		
		// Add top wall of left gate.
		addBox2DWall(leftGateX, topWallY, Gate.WIDTH, LineShape.LINE_WIDTH);
		
		// Add bottom wall of left gate.
		addBox2DWall(leftGateX, bottomWallY, Gate.WIDTH, LineShape.LINE_WIDTH);
		
		// Add top wall of right gate.
		addBox2DWall(rightGateX, topWallY, Gate.WIDTH, LineShape.LINE_WIDTH);
		
		// Add bottom wall of right gate.
		addBox2DWall(rightGateX, bottomWallY, Gate.WIDTH, LineShape.LINE_WIDTH);
	}
	
	/**
	 * Add the sensor of gates to the ball.
	 */
	private void addBox2DGateSensors(){
		final float sensorWidth = Gate.WIDTH - Ball.RADIUS - LineShape.LINE_WIDTH / 2;
		final float sensorY = (Table.HEIGHT - Gate.HEIGHT + LineShape.LINE_WIDTH) / 2;
		final float sensorHeight = Gate.HEIGHT - LineShape.LINE_WIDTH;
		final float leftGateSensorX = 0;
		final float rightGateSensorX = Table.WIDTH - sensorWidth;
		
		// Add the left gate sensor.
		addBox2DSensor(leftGateSensorX, sensorY, sensorWidth, sensorHeight, SensorUserDataEnum.PLAYER_GATE_SENSOR);
		
		// Add the right gate sensor.
		addBox2DSensor(rightGateSensorX, sensorY, sensorWidth, sensorHeight, SensorUserDataEnum.OPPONENT_GATE_SENSOR);
	}
	
	/**
	 * Add the sensor of map to the button.
	 */
	private void addBox2DMapSensors(){
		final float sensorWidth = Map.WIDTH + LineShape.LINE_WIDTH;
		final float sensorHeight = Map.HEIGHT + LineShape.LINE_WIDTH;
		final float sensorX = (Table.WIDTH - sensorWidth) / 2;
		final float sensorY = (Table.HEIGHT - sensorHeight) / 2;
		
		addBox2DSensor(sensorX, sensorY, sensorWidth, sensorHeight, SensorUserDataEnum.MAP_SENSOR);
	}
	
	/**
	 * Resize the map.
	 * 
	 * @param tableX - The x coordinate value of map.
	 * @param tableY - The y coordinate value of map.
	 * @param tableWidth - The width value of map.
	 * @param tableHeight - The height value of map.
	 * @param scale - The scale value.
	 */
	private void resizeMap(final float tableX, final float tableY, final float tableWidth, final float tableHeight, final double scale){
		final float width = (float) ((double)Map.WIDTH * scale);
		final float height = (float) ((double)Map.HEIGHT * scale);
		final float x = tableX + (tableWidth - width)/2;
		final float y = tableY + (tableHeight - height)/2;
		
		map.resize( x, y, width, height, scale );
	}
	
	/**
	 * Resize the left gate.
	 * 
	 * @param tableY - The y coordinate value of table.
	 * @param tableHeight - The height value of table.
	 * @param mapX - The x coordinate value of map.
	 * @param scale - The scale value.
	 */
	private void resizeLeftGate(final float tableY, final float tableHeight, final float mapX, final double scale){
		final float width = (float)(Gate.WIDTH * scale);
		final float height = (float)(Gate.HEIGHT * scale);
		final float x = mapX - width;
		final float y = tableY + (tableHeight - height) / 2;
		
		leftGate.resize(x, y, width, height, scale);
	}
	
	/**
	 * Resize the right gate.
	 * 
	 * @param mapX - The x coordiante value of map.
	 * @param mapWidth - The width value of map.
	 * @param y - The y coordinate value of right gate.
	 * @param width - The width value of right gate.
	 * @param height - The height value of right gate.
	 * @param scale - The scale value.
	 */
	private void resizeRightGate(final float mapX, final float mapWidth, final float y, final float width, final float height, final double scale){
		final float x = mapX + mapWidth;
		rightGate.resize(x, y, width, height, scale);
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
