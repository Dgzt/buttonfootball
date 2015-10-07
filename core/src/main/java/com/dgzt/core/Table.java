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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.gate.LeftGate;
import com.dgzt.core.gate.RightGate;
import com.dgzt.core.shape.RectangleShape;
import com.dgzt.core.util.Box2DDataUtil;
import com.dgzt.core.util.Box2DUtil;
import com.dgzt.core.util.MathUtil;

/**
 * The table object.
 * 
 * @author Dgzt
 */
public class Table extends RectangleShape{
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The Box2D wall size in cm. */
	private static final float BOX2D_WALL_SIZE = 10.0f;
	
	/** The distance at free kick */
	private static final float FREE_KICK_DISTANCE = 18.0f;
	
	/** The distance at free kick with radius of button */
	private static final float FREE_KICK_DISTANCE_WITH_BUTTON = FREE_KICK_DISTANCE + Button.RADIUS;
	
	/** The ID of goalkeeper in button list. */
	private static final int GOALKEEPER_ID = 0;
	
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
	
	/** The Player's and Opponent's button and ball visibility. */
	private boolean visibleButtons;
	
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
		
		map = createMap(shader, box2DWorld);
		
		leftGate = createLeftGate(shader, box2DWorld);
		rightGate = createRightGate(shader, box2DWorld);
		
		playerButtons = new ArrayList<Button>();
		opponentButtons = new ArrayList<Button>();
		
		addButtons(shader, eventListener, box2DWorld);
		
		ball = createBall(shader, eventListener, box2DWorld);
		
		visibleButtons = false;
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
		
		map.resize(new Vector2(x, y), scale);
		
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
	 * Move the goalkeeper to the left gate.
	 * 
	 * @param player - The player who is on the left side.
	 */
	public void moveGoalkeeperToLeftGate(final Player player){
		final List<Button> buttons = player == Player.PLAYER ? playerButtons : opponentButtons;
		
		buttons.get(GOALKEEPER_ID).setBox2DPosition(Box2DDataUtil.LEFT_GOALKEEPER_POSITION);
	}
	
	/**
	 * Move the goalkeeper to the right gate.
	 * 
	 * @param player - The player who is on the right side.
	 */
	public void moveGoalkeeperToRightGate(final Player player){
		final List<Button> buttons = player == Player.BOT ? opponentButtons : playerButtons;
		
		buttons.get(GOALKEEPER_ID).setBox2DPosition(Box2DDataUtil.RIGHT_GOALKEEPER_POSITION);
	}
	
	/**
	 * Move the given player's buttons to the left part of the map.
	 * 
	 * @param player - The player.
	 */
	public void moveButtonsToLeftPartOfMap(final Player player){
		final float buttonDistanceX = Box2DDataUtil.TABLE_RECTANGLE.getWidth() / 2 / 4;
		final float buttonDistanceY = Box2DDataUtil.TABLE_RECTANGLE.getHeight() / 5;
		final float halfTableHeight = Box2DDataUtil.TABLE_RECTANGLE.getHeight() / 2;

		final List<Button> buttons = player.equals(Player.PLAYER) ? playerButtons : opponentButtons;

		// Add left goalkeeper
		moveGoalkeeperToLeftGate(player);
		
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
		final float buttonDistanceX = Box2DDataUtil.TABLE_RECTANGLE.getWidth() / 2 / 4;
		final float buttonDistanceY = Box2DDataUtil.TABLE_RECTANGLE.getHeight() / 5;
		final float halfTableHeight = Box2DDataUtil.TABLE_RECTANGLE.getHeight() / 2;
		
		final List<Button> buttons = player.equals(Player.PLAYER) ? playerButtons : opponentButtons;
		
		// Add right goalkeeper
		moveGoalkeeperToRightGate(player);
		
		// Add right defenders
		for(int i=1; i <= 4; ++i){
			buttons.get(i).setBox2DPosition(Box2DDataUtil.TABLE_RECTANGLE.getWidth() - buttonDistanceX, i*buttonDistanceY);
		}
		
		// Add right midfielders
		for(int i=1; i <= 4; ++i){
			buttons.get(i+4).setBox2DPosition(Box2DDataUtil.TABLE_RECTANGLE.getWidth() - 2*buttonDistanceX, i*buttonDistanceY);
		}
		
		// Add right forwards
		buttons.get(9).setBox2DPosition(Box2DDataUtil.TABLE_RECTANGLE.getWidth() - 3*buttonDistanceX, halfTableHeight - buttonDistanceY);
		buttons.get(10).setBox2DPosition(Box2DDataUtil.TABLE_RECTANGLE.getWidth() - 3*buttonDistanceX, halfTableHeight + buttonDistanceY);
	}
	
	/**
	 * Move ball to the center of the map.
	 */
	public void moveBallToCenter(){
		ball.setBox2DPosition(Box2DDataUtil.TABLE_RECTANGLE.getWidth() / 2, Box2DDataUtil.TABLE_RECTANGLE.getHeight() / 2);
	}
	
	/**
	 * Move ball to the left penalty position.
	 */
	public void moveBallToLeftPenaltyPosition(){
		final Circle leftSmallCircle = Box2DDataUtil.LEFT_SMALL_CIRCLE; 
		
		ball.setBox2DPosition(leftSmallCircle.x, leftSmallCircle.y);
	}
	
	/**
	 * Move ball to the right penalty position.
	 */
	public void moveBallToRightPenaltyPosition(){
		final Circle rightSmallCircle = Box2DDataUtil.RIGHT_SMALL_CIRCLE;
		
		ball.setBox2DPosition(rightSmallCircle.x, rightSmallCircle.y);
	}
	
	/**
	 * Create 18 centimeter free space for given position.
	 * 
	 * @param box2DPosition - The box2D position.
	 */
	public void create18CentimeterFreeSpace(final Vector2 box2DPosition){
		Gdx.app.log(getClass().getName() + ".create18CentimeterFreeSpace", "init");
		
		final List<Button> buttons = new ArrayList<Button>();
		buttons.addAll(playerButtons);
		buttons.addAll(opponentButtons);

		for(final Button button : buttons){
			if(box2DPosition.dst(button.getBox2DPosition()) < FREE_KICK_DISTANCE_WITH_BUTTON){
				final Vector2 newButtonPos = Box2DUtil.newDistancePosition(box2DPosition, button.getBox2DPosition(), FREE_KICK_DISTANCE_WITH_BUTTON);
				
				button.setBox2DPosition(newButtonPos);
			}
		}

	}
	
	/**
	 * Create free space for left penalty kick.
	 * 
	 * @param whoIsOnLeftSide - The player who is on left side.
	 */
	public void createFreeSpaceForLeftPenaltyKick(final Player whoIsOnLeftSide){
		final Rectangle rectangle = MathUtil.extend(Box2DDataUtil.LEFT_SECTOR_16_RECTANGLE, Button.RADIUS);
		final Circle circle = MathUtil.extend(Box2DDataUtil.LEFT_BIG_CIRCLE, Button.RADIUS);
		final float newX = rectangle.getX() + rectangle.getWidth();
		
		final List<Button> leftPlayerButtons = whoIsOnLeftSide == Player.PLAYER ? playerButtons : opponentButtons;
		final List<Button> rightPlayerButtons = whoIsOnLeftSide == Player.PLAYER ? opponentButtons : playerButtons;
		
		final List<Button> buttons = new ArrayList<Button>();
		buttons.addAll(leftPlayerButtons.subList(1, leftPlayerButtons.size()));
		buttons.addAll(rightPlayerButtons);
		
		moveButtonsToFreeSpace(rectangle, circle, newX, buttons);
	}
	
	/**
	 * Create free space for right penalty kick.
	 * 
	 * @param whoIsOnRightSide - The player who is on right side.
	 */
	public void createFreeSpaceForRightPenaltyKick(final Player whoIsOnRightSide){
		final Rectangle rectangle = MathUtil.extend(Box2DDataUtil.RIGHT_SECTOR_16_RECTANGLE, Button.RADIUS);
		final Circle circle = MathUtil.extend(Box2DDataUtil.RIGHT_BIG_CIRCLE, Button.RADIUS);
		final float newX = rectangle.getX();
		
		final List<Button> rightPlayerButtons = whoIsOnRightSide == Player.BOT ? opponentButtons : playerButtons;
		final List<Button> leftPlayerButtons = whoIsOnRightSide == Player.BOT ? playerButtons : opponentButtons;
		
		final List<Button> buttons = new ArrayList<Button>();
		buttons.addAll(rightPlayerButtons.subList(1, rightPlayerButtons.size()));
		buttons.addAll(leftPlayerButtons);
		
		moveButtonsToFreeSpace(rectangle, circle, newX, buttons);
	}
	
	/**
	 * Return true when the given position is free.
	 * 
	 * @param actualButton - The button which check position.
	 * @param box2DPosition - The box2D position.
	 */
	public boolean isFreeBox2DPosition(final Button actualButton, final Vector2 box2DPosition){
		final List<Button> buttons = new ArrayList<Button>(playerButtons);
		buttons.addAll(opponentButtons);
		
		for(final Button otherButton : buttons){
			if(actualButton != otherButton && box2DPosition.dst(otherButton.getBox2DPosition()) <= Button.DIAMETER){
				return false;
			}
		}
		
		if(box2DPosition.dst(ball.getBox2DPosition()) <= Button.RADIUS + Ball.RADIUS){
			return false;
		}
		
		return true;
	}
	
	/**
	 * Return true when the ball is on the top left corner of map else false.
	 */
	public boolean isBallOnTopLeftCornerOfMap(){
		return ball.getBox2DPosition().equals(map.getTopLeftCornerBox2DPosition());
	}
	
	/**
	 * Return true when the ball is on the top border of map else false.
	 */
	public boolean isBallOnTopBorderOfMap(){
		final float mapBox2DX = Box2DDataUtil.MAP_RECTANGLE.getX();
		final float mapBox2DY = Box2DDataUtil.MAP_RECTANGLE.getY();
		final float mapBox2DWidth = Box2DDataUtil.MAP_RECTANGLE.getWidth();
		
		return mapBox2DX < ball.getBox2DX() && ball.getBox2DX() < (mapBox2DX + mapBox2DWidth) && Float.compare(ball.getBox2DY(), mapBox2DY) == 0;
	}
	
	/**
	 * Return true when the ball is on the top right corner of map else false.
	 */
	public boolean isBallOnTopRightCornerOfMap(){
		return ball.getBox2DPosition().equals(map.getTopRightCornerBox2DPosition());
	}
	
	/**
	 * Return true when the ball is on the bottom left corner of map else false.
	 */
	public boolean isBallOnBottomLeftCornerOfMap(){
		return ball.getBox2DPosition().equals(map.getBottomLeftCornerBox2DPosition());
	}
	
	/**
	 * Return true when the ball is on the bottom border of map else false.
	 */
	public boolean isBallOnBottomBorderOfMap(){
		final float mapBox2DX = Box2DDataUtil.MAP_RECTANGLE.getX();
		final float mapBox2DY = Box2DDataUtil.MAP_RECTANGLE.getY();
		final float mapBox2DWidth = Box2DDataUtil.MAP_RECTANGLE.getWidth();
		final float mapBox2DHeight = Box2DDataUtil.MAP_RECTANGLE.getHeight();
		
		return mapBox2DX < ball.getBox2DX() && ball.getBox2DX() < (mapBox2DX + mapBox2DWidth) && Float.compare(ball.getBox2DY(), mapBox2DY + mapBox2DHeight) == 0;
	}
	
	/**
	 * Return true when the ball is on the bottom right corner of map else false.
	 */
	public boolean isBallOnBottomRightCornerOfMap(){
		return ball.getBox2DPosition().equals(map.getBottomRightCornerBox2DPosition());
	}
	
	/**
	 * Return true when the ball is on the left goal kick position else false.
	 */
	public boolean isBallOnLeftGoalKickPosition(){
		return ball.getBox2DPosition().equals(map.getLeftGoalKickBox2DPosition());
	}
	
	/**
	 * Return true when the ball is on the right goal kick position else false.
	 */
	public boolean isBallOnRightGoalKickPosition(){
		return ball.getBox2DPosition().equals(map.getRightGoalKickBox2DPosition());
	}
	
	/**
	 * Return true when the ball is on the left penalty position else false.
	 */
	public boolean isBallOnLeftPenaltyPosition(){
		final Circle leftSmallCircle = Box2DDataUtil.LEFT_SMALL_CIRCLE;
		
		return ball.getBox2DPosition().equals(new Vector2(leftSmallCircle.x, leftSmallCircle.y));
	}
	
	/**
	 * Return true when the ball is on the right penalty position else false.
	 */
	public boolean isBallOnRightPenaltyPosition(){
		final Circle rightSmallCircle = Box2DDataUtil.RIGHT_SMALL_CIRCLE;
		
		return ball.getBox2DPosition().equals(new Vector2(rightSmallCircle.x, rightSmallCircle.y));
	}
	
	/**
	 * Return true when the position of button is on the table.
	 * 
	 * @param position - The position.
	 */
	public boolean isButtonPositionOnTable(final Vector2 position){
		final Rectangle rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
		final Circle circle = new Circle(position, (float) (getScale() * Button.RADIUS));
		
		return MathUtil.isRectangleFullyContainsCircle(rectangle, circle);
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
		
		if(visibleButtons){
			for(final Button playerButton : playerButtons){
				playerButton.draw();
			}
			
			for(final Button opponentButton : opponentButtons){
				opponentButton.draw();
			}
			
			ball.draw();
		}
		
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
	// ~ Protected methods
	// --------------------------------------------------
	
	/**
	 * Create {@link Map} object.
	 * 
	 * @param shader - The shader.
	 * @param box2DWorld - The Box2D world.
	 */
	protected Map createMap(final ShaderProgram shader, final World box2DWorld){
		return new Map(shader, box2DWorld);
	}
	
	/**
	 * Create {@link LeftGate} object.
	 * 
	 * @param shader - The shader.
	 * @param box2DWorld - The Box2D world.
	 */
	protected LeftGate createLeftGate(final ShaderProgram shader, final World box2DWorld){
		return new LeftGate(shader, box2DWorld);
	}

	/**
	 * Create {@link RightGate} object.
	 * 
	 * @param shader - The shader.
	 * @param box2DWorld - The Box2D world.
	 * @param box2DX - The x coordinate value in Box2D world.
	 * @param box2DY - The y coordinate value in Box2D world.
	 */
	protected RightGate createRightGate(final ShaderProgram shader, final World box2DWorld){
		return new RightGate(shader, box2DWorld);
	}

	/**
	 * Create {@link Button} object.
	 * 
	 * @param shader - The shader.
	 * @param eventListener - The event listener.
	 * @param box2DWorld - The Box2D world.
	 * @param color - The color.
	 */
	protected Button createButton(final ShaderProgram shader, final EventListener eventListener, final World box2DWorld, final Color color){
		return new Button(this, shader, eventListener, box2DWorld, color);
	}
	
	/**
	 * Create {@link Ball} object.
	 * 
	 * @param shader - The shader.
	 * @param eventListener - The event listener.
	 * @param box2DWorld - The Box2D world.
	 */
	protected Ball createBall(final ShaderProgram shader, final EventListener eventListener, final World box2DWorld){
		return new Ball(this, shader, eventListener, box2DWorld);
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
			playerButtons.add(createButton(shader, eventListener, box2DWorld, Button.PLAYER_COLOR));
		}
		
		// The opponent's buttons.
		for(int i = 0; i < 11; ++i){
			opponentButtons.add(createButton(shader, eventListener, box2DWorld, Button.OPPONENT_COLOR));
		}
	}
	
	/**
	 * Add the box2D walls of table.
	 * 
	 * @param box2DWorld - The box2D world.
	 */
	private void addBox2DWalls(final World box2DWorld){
		final float tableBox2DWidth = Box2DDataUtil.TABLE_RECTANGLE.getWidth();
		final float tableBox2DHeight = Box2DDataUtil.TABLE_RECTANGLE.getHeight();
		
		// Add top wall
		Box2DUtil.addWall(box2DWorld, 0, 0 - BOX2D_WALL_SIZE, tableBox2DWidth, BOX2D_WALL_SIZE);
		
		// Add right wall
		Box2DUtil.addWall(box2DWorld, tableBox2DWidth, 0, BOX2D_WALL_SIZE, tableBox2DHeight);
		
		// Add bottom wall
		Box2DUtil.addWall(box2DWorld, 0, tableBox2DHeight, tableBox2DWidth, BOX2D_WALL_SIZE);
		
		// Add left Wall
		Box2DUtil.addWall(box2DWorld, 0 - BOX2D_WALL_SIZE, 0, BOX2D_WALL_SIZE, tableBox2DHeight);
	}
	
	/**
	 * Move the left or right sector 16 contains buttons to the free space.
	 * 
	 * @param rectangle - The left or right sector 16 box2D rectangle.
	 * @param circle - The left or right big box2D circle.
	 * @param newX - The new x box2D coordinate value.
	 * @param buttons - The moving buttons.
	 */
	private void moveButtonsToFreeSpace(final Rectangle rectangle, final Circle circle, final float newX, final List<Button> buttons) {
		for(final Button button : buttons){
			if(rectangle.contains(button.getBox2DPosition()) || circle.contains(button.getBox2DPosition())){
				final Vector2 defaultPos = new Vector2(newX, button.getBox2DY());
				final Vector2 newPos = new Vector2(defaultPos);
				
				boolean isUp = true;
				final float treshold = 0.1f;
				float up = treshold;
				float down = treshold;
				
				while(!isFreeBox2DPosition(button, newPos) || circle.contains(newPos)){
					newPos.set(defaultPos);
					if(isUp){
						newPos.y += up;
						up += treshold;
					}else{
						newPos.y -= down;
						down += treshold;
					}
					
					isUp = !isUp;
				}
				
				button.setBox2DPosition(newPos);
			}
		}
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
	 * Return with the map.
	 */
	public Map getMap(){
		return map;
	}
	
	/**
	 * Return with the actual scale value.
	 */
	public final double getScale(){
		return scale;
	}

	/**
	 * Set the visibility of Player's and Opponent's buttons and ball.
	 * 
	 * @param visibleButtons
	 */
	public void setVisibleButtons(final boolean visibleButtons) {
		this.visibleButtons = visibleButtons;
	}

}
