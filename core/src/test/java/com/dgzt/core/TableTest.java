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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.gate.LeftGate;
import com.dgzt.core.gate.RightGate;
import com.dgzt.core.util.Box2DDataUtil;

/**
 * Test for {@link Table}.
 * 
 * @author Dgzt
 */
public final class TableTest extends BaseShapeTester{

	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	private static final Rectangle TABLE_RECTANGLE = new Rectangle(100.0f, 200.0f, 80.0f, 60.0f);
	private static final double SCALE = 1.0f;
	private static final float DISTANCE = 11.0f;
	private static final Rectangle MAP_BOX2D_RECTANGLE = Box2DDataUtil.MAP_RECTANGLE;
	private static final Vector2 MAP_LEFT_GOAL_KICK_BOX2D_POSITION = new Vector2(MAP_BOX2D_RECTANGLE.x + DISTANCE, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height / 2);
	private static final Vector2 MAP_RIGHT_GOAL_KICK_BOX2D_POSITION = new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width - DISTANCE, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.getHeight() / 2);
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private Table table;
	
	// --------------------------------------------------
	// ~ Init methods
	// --------------------------------------------------
	
	/**
	 * Init method for tests.
	 */
	@Before
	public void setUp(){
		// Shader
		final ShaderProgram shader = Mockito.mock(ShaderProgram.class);
		
		// Box2D world
		final World box2DWorld = new World(new Vector2(0,0), true);
		
		// Event Listener
		final EventListener eventListener = Mockito.mock(EventListener.class);
		
		// Map
		final Map map = Mockito.mock(Map.class);
		Mockito.when(map.getTopLeftCornerBox2DPosition()).thenReturn(new Vector2(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y));
		Mockito.when(map.getTopRightCornerBox2DPosition()).thenReturn(new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y));
		Mockito.when(map.getBottomLeftCornerBox2DPosition()).thenReturn(new Vector2(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height));
		Mockito.when(map.getBottomRightCornerBox2DPosition()).thenReturn(new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height));
		Mockito.when(map.getLeftGoalKickBox2DPosition()).thenReturn(MAP_LEFT_GOAL_KICK_BOX2D_POSITION);
		Mockito.when(map.getRightGoalKickBox2DPosition()).thenReturn(MAP_RIGHT_GOAL_KICK_BOX2D_POSITION);
		
		table = new Table(shader, box2DWorld, eventListener){
			
			@Override
			protected Mesh getMesh(final boolean isStatic, final int verticesNum, final int maxIndices, final VertexAttribute... vAttribs) {
				return getMockMesh();
			}
			
			@Override
			protected Map createMap(final ShaderProgram shader, final World box2dWorld) {
				return map;
			}
			
			@Override
			protected LeftGate createLeftGate(final ShaderProgram shader, final World box2dWorld) {
				return Mockito.mock(LeftGate.class);
			}
			
			@Override
			protected RightGate createRightGate(final ShaderProgram shader, final World box2dWorld) {
				return Mockito.mock(RightGate.class);
			}
			
			@Override
			protected Button createButton(final ShaderProgram shader, final EventListener eventListener, final World box2dWorld, final Color color) {
				return getMockButton();
			}
			
			@Override
			protected Ball createBall(final ShaderProgram shader, final EventListener eventListener, final World box2dWorld) {
				return getMockBall();
			}
		};
	}
	
	// --------------------------------------------------
	// ~ Test methods
	// --------------------------------------------------
	
	/**
	 * Test for {@link Table#moveGoalkeeperToLeftGate(Player)} method.
	 */
	@Test
	public void test_moveGoalkeeperToLeftGate(){
		final Vector2 basePosition = new Vector2(10, 20);
		final List<Button> buttons = table.getPlayerButtons();
		
		for(final Button button : buttons){
			button.setBox2DPosition(basePosition);
		}
		
		table.moveGoalkeeperToLeftGate(Player.PLAYER);
		
		Assert.assertEquals(table.getPlayerButtons().get(0).getBox2DPosition(), Box2DDataUtil.LEFT_GOALKEEPER_POSITION);
		for(int i = 1; i < buttons.size(); ++i){
			Assert.assertEquals(table.getPlayerButtons().get(i).getBox2DPosition(), basePosition);
		}
	}
	
	/**
	 * Test for {@link Table#moveGoalkeeperToRightGate(Player)} method.
	 */
	@Test
	public void test_moveGoalkeeperToRightGate(){
		final Vector2 basePosition = new Vector2(10, 20);
		final List<Button> buttons = table.getOpponentButtons();
		
		for(final Button button : buttons){
			button.setBox2DPosition(basePosition);
		}
		
		table.moveGoalkeeperToRightGate(Player.BOT);
		
		Assert.assertEquals(table.getOpponentButtons().get(0).getBox2DPosition(), Box2DDataUtil.RIGHT_GOALKEEPER_POSITION);
		for(int i = 1; i < buttons.size(); ++i){
			Assert.assertEquals(table.getOpponentButtons().get(i).getBox2DPosition(), basePosition);
		}
	}
	
	/**
	 * Test for {@link Table#moveBallToCenter()} method.
	 */
	@Test
	public void test_moveBallToCenter(){
		final Vector2 ballPos = new Vector2(23,32);
		final Ball ball = table.getBall();
		
		ball.setBox2DPosition(ballPos);
		table.moveBallToCenter();
		
		Assert.assertEquals(ball.getBox2DPosition(), new Vector2(Box2DDataUtil.CENTRAL_SMALL_CIRCLE.x, Box2DDataUtil.CENTRAL_SMALL_CIRCLE.y));
	}
	
	/**
	 * Test for {@link Table#moveBallToLeftPenaltyPosition()} method.
	 */
	@Test
	public void test_moveBallToLeftPenaltyPosition(){
		final Vector2 ballPos = new Vector2(23,32);
		final Ball ball = table.getBall();
		
		ball.setBox2DPosition(ballPos);
		table.moveBallToLeftPenaltyPosition();
		
		Assert.assertEquals(ball.getBox2DPosition(), new Vector2(Box2DDataUtil.LEFT_SMALL_CIRCLE.x, Box2DDataUtil.LEFT_SMALL_CIRCLE.y));
	}
	
	/**
	 * Test for {@link Table#moveBallToRightPenaltyPosition()} method.
	 */
	@Test
	public void test_moveBallToRightPenaltyPosition(){
		final Vector2 ballPos = new Vector2(23,32);
		final Ball ball = table.getBall();
		
		ball.setBox2DPosition(ballPos);
		table.moveBallToRightPenaltyPosition();
		
		Assert.assertEquals(ball.getBox2DPosition(), new Vector2(Box2DDataUtil.RIGHT_SMALL_CIRCLE.x, Box2DDataUtil.RIGHT_SMALL_CIRCLE.y));
	}
	
	/**
	 * Test for {@link Table#create18CentimeterFreeSpace(Vector2)} method.
	 */
	@Test
	public void test_create18CentimeterFreeSpace(){
		table.getPlayerButtons().clear();
		table.getOpponentButtons().clear();
		
		final Vector2 ballPos = new Vector2(30, 30);
		final Vector2 movingPlayerButtonPos = new Vector2(ballPos.x - 2, ballPos.y -2);
		final Vector2 movingOpponentButtonPos = new Vector2(ballPos.x + 2, ballPos.y + 2);
		final Vector2 notMovingOpponentButtonPos = new Vector2(ballPos.x + 30, ballPos.y + 30);
		
		table.getBall().setBox2DPosition(ballPos);
		
		final Button movingPlayerButton = getMockButton();
		movingPlayerButton.setBox2DPosition(movingPlayerButtonPos);
		table.getPlayerButtons().add(movingPlayerButton);
		
		final Button movingOpponentButton = getMockButton();
		movingOpponentButton.setBox2DPosition(movingOpponentButtonPos);
		table.getOpponentButtons().add(movingOpponentButton);
		final Button notMovingOpponentButton = getMockButton();
		notMovingOpponentButton.setBox2DPosition(notMovingOpponentButtonPos);
		table.getOpponentButtons().add(notMovingOpponentButton);
		
		table.create18CentimeterFreeSpace(ballPos);
		
		Assert.assertNotEquals(movingPlayerButtonPos, movingPlayerButton.getBox2DPosition());
		Assert.assertNotEquals(movingOpponentButtonPos, movingOpponentButton.getBox2DPosition());
		Assert.assertEquals(notMovingOpponentButtonPos, notMovingOpponentButton.getBox2DPosition());
	}
	
	/**
	 * Test for {@link Table#createFreeSpaceForLeftPenaltyKick(Player)} method. 
	 * The player is on left side.
	 */
	@Test
	public void test_createFreeSpaceForLeftPenaltyKickByOpponent(){
		final float threshold = 0.1f;
		final Rectangle rectangle = Box2DDataUtil.LEFT_SECTOR_16_RECTANGLE;
		final Circle circle = Box2DDataUtil.LEFT_BIG_CIRCLE;
		
		// Contains positions
		final Vector2 containsPlayer1Point = new Vector2(rectangle.x , rectangle.y);
		final Vector2 containsOpponent0Point = new Vector2(rectangle.x + rectangle.width, rectangle.y);
		final Vector2 containsPlayer2Point = new Vector2(circle.x + circle.radius - Button.RADIUS, circle.y);
		
		// Not contains positions
		final Vector2 notContainsPlayer3Point = new Vector2(rectangle.x, rectangle.y + rectangle.height + Button.RADIUS + threshold);
		final Vector2 notContainsOpponent1Point = new Vector2(rectangle.x + rectangle.width + Button.RADIUS + threshold, rectangle.y + rectangle.height);
		final Vector2 notContainsOpponent2Point = new Vector2(circle.x + circle.radius + Button.RADIUS + 1.0f, circle.y);
		
		final List<Button> playerButtons = table.getPlayerButtons();
		final List<Button> opponentButtons = table.getOpponentButtons();
		
		table.moveGoalkeeperToLeftGate(Player.PLAYER);
		playerButtons.get(1).setBox2DPosition(containsPlayer1Point);
		playerButtons.get(2).setBox2DPosition(containsPlayer2Point);
		playerButtons.get(3).setBox2DPosition(notContainsPlayer3Point);
		
		opponentButtons.get(0).setBox2DPosition(containsOpponent0Point);
		opponentButtons.get(1).setBox2DPosition(notContainsOpponent1Point);
		opponentButtons.get(2).setBox2DPosition(notContainsOpponent2Point);
		
		table.createFreeSpaceForLeftPenaltyKick(Player.PLAYER);
		
		Assert.assertNotEquals(containsPlayer1Point, playerButtons.get(1).getBox2DPosition());
		Assert.assertNotEquals(containsPlayer2Point, playerButtons.get(2).getBox2DPosition());
		Assert.assertNotEquals(containsOpponent0Point, opponentButtons.get(0).getBox2DPosition());
		
		Assert.assertEquals(Box2DDataUtil.LEFT_GOALKEEPER_POSITION, playerButtons.get(0).getBox2DPosition());
		Assert.assertEquals(notContainsPlayer3Point, playerButtons.get(3).getBox2DPosition());
		Assert.assertEquals(notContainsOpponent1Point, opponentButtons.get(1).getBox2DPosition());
		Assert.assertEquals(notContainsOpponent2Point, opponentButtons.get(2).getBox2DPosition());
	}
	
	/**
	 * Test for {@link Table#createFreeSpaceForLeftPenaltyKick(Player)} method. 
	 * The opponent is on left side.
	 */
	@Test
	public void test_createFreeSpaceForLeftPenaltyKickByPlayer(){
		final float threshold = 0.1f;
		final Rectangle rectangle = Box2DDataUtil.LEFT_SECTOR_16_RECTANGLE;
		final Circle circle = Box2DDataUtil.LEFT_BIG_CIRCLE;
		
		// Contains positions
		final Vector2 containsOpponent1Point = new Vector2(rectangle.x , rectangle.y);
		final Vector2 containsPlayer0Point = new Vector2(rectangle.x + rectangle.width, rectangle.y);
		final Vector2 containsOpponent2Point = new Vector2(circle.x + circle.radius - Button.RADIUS, circle.y);
		
		// Not contains positions
		final Vector2 notContainsOpponent3Point = new Vector2(rectangle.x, rectangle.y + rectangle.height + Button.RADIUS + threshold);
		final Vector2 notContainsPlayer1Point = new Vector2(rectangle.x + rectangle.width + Button.RADIUS + threshold, rectangle.y + rectangle.height);
		final Vector2 notContainsPlayer2Point = new Vector2(circle.x + circle.radius + Button.RADIUS + 1.0f, circle.y);
		
		final List<Button> playerButtons = table.getPlayerButtons();
		final List<Button> opponentButtons = table.getOpponentButtons();
		
		table.moveGoalkeeperToLeftGate(Player.BOT);
		opponentButtons.get(1).setBox2DPosition(containsOpponent1Point);
		opponentButtons.get(2).setBox2DPosition(containsOpponent2Point);
		opponentButtons.get(3).setBox2DPosition(notContainsOpponent3Point);
		
		playerButtons.get(0).setBox2DPosition(containsPlayer0Point);
		playerButtons.get(1).setBox2DPosition(notContainsPlayer1Point);
		playerButtons.get(2).setBox2DPosition(notContainsPlayer2Point);
		
		table.createFreeSpaceForLeftPenaltyKick(Player.BOT);
		
		Assert.assertNotEquals(containsOpponent1Point, opponentButtons.get(1).getBox2DPosition());
		Assert.assertNotEquals(containsOpponent2Point, opponentButtons.get(2).getBox2DPosition());
		Assert.assertNotEquals(containsPlayer0Point, playerButtons.get(0).getBox2DPosition());
		
		Assert.assertEquals(Box2DDataUtil.LEFT_GOALKEEPER_POSITION, opponentButtons.get(0).getBox2DPosition());
		Assert.assertEquals(notContainsOpponent3Point, opponentButtons.get(3).getBox2DPosition());
		Assert.assertEquals(notContainsPlayer1Point, playerButtons.get(1).getBox2DPosition());
		Assert.assertEquals(notContainsPlayer2Point, playerButtons.get(2).getBox2DPosition());
	}
	
	/**
	 * Test for {@link Table#createFreeSpaceForRightPenaltyKick(Player)} method. 
	 * The opponent is on right side.
	 */
	@Test
	public void test_createFreeSpaceForRightPenaltyKickByPlayer(){
		final float threshold = 0.1f;
		final Rectangle rectangle = Box2DDataUtil.RIGHT_SECTOR_16_RECTANGLE;
		final Circle circle = Box2DDataUtil.RIGHT_BIG_CIRCLE;
		
		// Contains positions
		final Vector2 containsOpponent1Point = new Vector2(rectangle.x, rectangle.y);
		final Vector2 containsPlayer0Point = new Vector2(rectangle.x + rectangle.width, rectangle.y);
		final Vector2 containsOpponent2Point = new Vector2(circle.x - circle.radius + Button.RADIUS, circle.y);
		
		// Not contains positions
		final Vector2 notContainsOpponent3Point = new Vector2(rectangle.x, rectangle.y + rectangle.height + Button.RADIUS + threshold);
		final Vector2 notContainsPlayer1Point = new Vector2(rectangle.x + rectangle.width + Button.RADIUS + threshold, rectangle.y + rectangle.height);
		final Vector2 notContainsPlayer2Point = new Vector2(circle.x - circle.radius - Button.RADIUS - 1.0f, circle.y);
		
		final List<Button> playerButtons = table.getPlayerButtons();
		final List<Button> opponentButtons = table.getOpponentButtons();
		
		table.moveGoalkeeperToRightGate(Player.BOT);
		opponentButtons.get(1).setBox2DPosition(containsOpponent1Point);
		opponentButtons.get(2).setBox2DPosition(containsOpponent2Point);
		opponentButtons.get(3).setBox2DPosition(notContainsOpponent3Point);
		
		playerButtons.get(0).setBox2DPosition(containsPlayer0Point);
		playerButtons.get(1).setBox2DPosition(notContainsPlayer1Point);
		playerButtons.get(2).setBox2DPosition(notContainsPlayer2Point);
		
		table.createFreeSpaceForRightPenaltyKick(Player.BOT);
		
		Assert.assertNotEquals(containsOpponent1Point, opponentButtons.get(1).getBox2DPosition());
		Assert.assertNotEquals(containsOpponent2Point, opponentButtons.get(2).getBox2DPosition());
		Assert.assertNotEquals(containsPlayer0Point, playerButtons.get(0).getBox2DPosition());
		
		Assert.assertEquals(Box2DDataUtil.RIGHT_GOALKEEPER_POSITION, opponentButtons.get(0).getBox2DPosition());
		Assert.assertEquals(notContainsOpponent3Point, opponentButtons.get(3).getBox2DPosition());
		Assert.assertEquals(notContainsPlayer1Point, playerButtons.get(1).getBox2DPosition());
		Assert.assertEquals(notContainsPlayer2Point, playerButtons.get(2).getBox2DPosition());
	}
	
	/**
	 * Test for {@link Table#createFreeSpaceForRightPenaltyKick(Player)} method. 
	 * The player is on right side.
	 */
	@Test
	public void test_createFreeSpaceForRightPenaltyKickByOpponent(){
		final float threshold = 0.1f;
		final Rectangle rectangle = Box2DDataUtil.RIGHT_SECTOR_16_RECTANGLE;
		final Circle circle = Box2DDataUtil.RIGHT_BIG_CIRCLE;
		
		// Contains positions
		final Vector2 containsPlayer1Point = new Vector2(rectangle.x , rectangle.y);
		final Vector2 containsOpponent0Point = new Vector2(rectangle.x + rectangle.width, rectangle.y);
		final Vector2 containsPlayer2Point = new Vector2(circle.x - circle.radius + Button.RADIUS, circle.y);
		
		// Not contains positions
		final Vector2 notContainsPlayer3Point = new Vector2(rectangle.x, rectangle.y + rectangle.height + Button.RADIUS + threshold);
		final Vector2 notContainsOpponent1Point = new Vector2(rectangle.x + rectangle.width + Button.RADIUS + threshold, rectangle.y + rectangle.height);
		final Vector2 notContainsOpponent2Point = new Vector2(circle.x - circle.radius - Button.RADIUS - 1.0f, circle.y);
		
		final List<Button> playerButtons = table.getPlayerButtons();
		final List<Button> opponentButtons = table.getOpponentButtons();
		
		table.moveGoalkeeperToRightGate(Player.PLAYER);
		playerButtons.get(1).setBox2DPosition(containsPlayer1Point);
		playerButtons.get(2).setBox2DPosition(containsPlayer2Point);
		playerButtons.get(3).setBox2DPosition(notContainsPlayer3Point);
		
		opponentButtons.get(0).setBox2DPosition(containsOpponent0Point);
		opponentButtons.get(1).setBox2DPosition(notContainsOpponent1Point);
		opponentButtons.get(2).setBox2DPosition(notContainsOpponent2Point);
		
		table.createFreeSpaceForRightPenaltyKick(Player.PLAYER);
		
		Assert.assertNotEquals(containsPlayer1Point, playerButtons.get(1).getBox2DPosition());
		Assert.assertNotEquals(containsPlayer2Point, playerButtons.get(2).getBox2DPosition());
		Assert.assertNotEquals(containsOpponent0Point, opponentButtons.get(0).getBox2DPosition());
		
		Assert.assertEquals(Box2DDataUtil.RIGHT_GOALKEEPER_POSITION, playerButtons.get(0).getBox2DPosition());
		Assert.assertEquals(notContainsPlayer3Point, playerButtons.get(3).getBox2DPosition());
		Assert.assertEquals(notContainsOpponent1Point, opponentButtons.get(1).getBox2DPosition());
		Assert.assertEquals(notContainsOpponent2Point, opponentButtons.get(2).getBox2DPosition());
	}
	
	/**
	 * Test for {@link Table#isFreeBox2DPosition(Button, Vector2)} method.
	 */
	@Test
	public void test_isFreeBox2DPosition(){
		final float treshold = 1.0f;
		
		table.moveButtonsToLeftPartOfMap(Player.PLAYER);
		table.moveButtonsToRightPartOfMap(Player.BOT);
		
		final List<Button> allButton = new ArrayList<Button>(table.getPlayerButtons());
		allButton.addAll(table.getOpponentButtons());
		
		for(final Button buttonA : allButton){
			// Check the other buttons
			for(final Button buttonB : allButton){
				final Vector2 contaisPoint = new Vector2(buttonB.getBox2DPosition());
				contaisPoint.x += Button.DIAMETER;
				
				final Vector2 notContaisPoint = new Vector2(buttonB.getBox2DPosition());
				notContaisPoint.x += Button.DIAMETER + treshold;
				
				if(buttonA != buttonB){
					Assert.assertFalse(table.isFreeBox2DPosition(buttonA, contaisPoint));
					Assert.assertTrue(table.isFreeBox2DPosition(buttonA, notContaisPoint));
				}else{
					Assert.assertTrue(table.isFreeBox2DPosition(buttonA, contaisPoint));
					Assert.assertTrue(table.isFreeBox2DPosition(buttonA, notContaisPoint));
				}
			}
			
			// Check the ball
			final Vector2 contaisPoint = new Vector2(table.getBall().getBox2DPosition());
			contaisPoint.x += Ball.RADIUS + Button.RADIUS;
			
			final Vector2 notContaisPoint = new Vector2(table.getBall().getBox2DPosition());
			notContaisPoint.x += Ball.RADIUS + Button.RADIUS + treshold;
			
			Assert.assertFalse(table.isFreeBox2DPosition(buttonA, contaisPoint));
			Assert.assertTrue(table.isFreeBox2DPosition(buttonA, notContaisPoint));
		}
	}
	
	/**
	 * Test for {@link Table#isBallOnTopLeftCornerOfMap()} method.
	 */
	@Test
	public void test_isBallOnTopLeftCornerOfMap(){
		final Ball ball = table.getBall();
		
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + DISTANCE, MAP_BOX2D_RECTANGLE.y);
		assertFalse(table.isBallOnTopLeftCornerOfMap());
		
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y);
		assertTrue(table.isBallOnTopLeftCornerOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnTopBorderOfMap()} method.
	 */
	@Test
	public void test_isBallOnTopBorderOfMap(){
		final Ball ball = table.getBall();

		// False on the top left corner of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y);
		assertFalse(table.isBallOnTopBorderOfMap());
		
		// False on the top right corner of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y);
		assertFalse(table.isBallOnTopBorderOfMap());
		
		// True on top border of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + DISTANCE, MAP_BOX2D_RECTANGLE.y);
		assertTrue(table.isBallOnTopBorderOfMap());
		
		// False on bottom border of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + DISTANCE, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
		assertFalse(table.isBallOnTopBorderOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnTopRightCornerOfMap()} method.
	 */
	@Test
	public void test_isBallOnTopRightCornerOfMap(){
		final Ball ball = table.getBall();

		// False on top border of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width - DISTANCE, MAP_BOX2D_RECTANGLE.y);
		assertFalse(table.isBallOnTopRightCornerOfMap());
		
		// True on top right corner of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y);
		assertTrue(table.isBallOnTopRightCornerOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnBottomLeftCornerOfMap()} method.
	 */
	@Test
	public void test_isBallOnBottomLeftCornerOfMap(){
		final Ball ball = table.getBall();
		
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + DISTANCE, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
		assertFalse(table.isBallOnBottomLeftCornerOfMap());
		
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
		assertTrue(table.isBallOnBottomLeftCornerOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnBottomBorderOfMap()} method.
	 */
	@Test
	public void test_isBallOnBottomBorderOfMap(){
		final Ball ball = table.getBall();

		// False on the bottom left corner of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
		assertFalse(table.isBallOnBottomBorderOfMap());
		
		// False on the bottom right corner of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
		assertFalse(table.isBallOnBottomBorderOfMap());
		
		// True on bottom border of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + DISTANCE, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
		assertTrue(table.isBallOnBottomBorderOfMap());
		
		// False on top border of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + DISTANCE, MAP_BOX2D_RECTANGLE.y);
		assertFalse(table.isBallOnBottomBorderOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnBottomRightCornerOfMap()} method.
	 */
	@Test
	public void test_isBallOnBottomRightCornerOfMap(){
		final Ball ball = table.getBall();

		// False on bottom border of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width - DISTANCE, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
		assertFalse(table.isBallOnBottomRightCornerOfMap());
		
		// True on bottom right corner of map
		ball.setBox2DPosition(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
		assertTrue(table.isBallOnBottomRightCornerOfMap());
	}

	/**
	 * Test for {@link Table#isButtonPositionOnTable(float, float)} method.
	 */
	@Test
	public void test_isButtonPositionOnTable(){
		table.resize(TABLE_RECTANGLE.x, TABLE_RECTANGLE.y, TABLE_RECTANGLE.width, TABLE_RECTANGLE.height, SCALE);
		final float width = (float) (TABLE_RECTANGLE.width * SCALE);
		final float height = (float) (TABLE_RECTANGLE.height * SCALE);
		
		// The correct
		assertTrue(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x + DISTANCE, TABLE_RECTANGLE.y + DISTANCE)));
		
		// Left the table
		assertFalse(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x, TABLE_RECTANGLE.y + DISTANCE)));
		
		// Up the table
		assertFalse(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x + DISTANCE, TABLE_RECTANGLE.y)));
		
		// Right the table
		assertFalse(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x + width, TABLE_RECTANGLE.y + height - DISTANCE)));
		
		// Down the table
		assertFalse(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x + width - DISTANCE, TABLE_RECTANGLE.y + height)));
	}
	
	/**
	 * Test for {@link Table#isBallOnLeftGoalKickPosition()} method.
	 */
	@Test
	public void test_isBallOnLeftGoalKickPosition(){
		final Ball ball = table.getBall();
		
		ball.setBox2DPosition(MAP_LEFT_GOAL_KICK_BOX2D_POSITION.x - DISTANCE, MAP_LEFT_GOAL_KICK_BOX2D_POSITION.y);
		assertFalse(table.isBallOnLeftGoalKickPosition());
		
		ball.setBox2DPosition(MAP_LEFT_GOAL_KICK_BOX2D_POSITION.x, MAP_LEFT_GOAL_KICK_BOX2D_POSITION.y);
		assertTrue(table.isBallOnLeftGoalKickPosition());
	}
	
	/**
	 * Test for {@link Table#isBallOnRightGoalKickPosition()} method.
	 */
	@Test
	public void test_isBallOnRightGoalKickPosition(){
		final Ball ball = table.getBall();
		
		ball.setBox2DPosition(MAP_RIGHT_GOAL_KICK_BOX2D_POSITION.x - DISTANCE, MAP_RIGHT_GOAL_KICK_BOX2D_POSITION.y);
		assertFalse(table.isBallOnRightGoalKickPosition());
		
		ball.setBox2DPosition(MAP_RIGHT_GOAL_KICK_BOX2D_POSITION.x, MAP_RIGHT_GOAL_KICK_BOX2D_POSITION.y);
		assertTrue(table.isBallOnRightGoalKickPosition());
	}
	
	/**
	 * Test for {@link Table#isBallOnLeftPenaltyPosition()} method.
	 */
	@Test
	public void test_isBallOnLeftPenaltyPosition(){
		final Vector2 otherPosition = new Vector2(100, 200);
		final Vector2 leftPenaltyPosition = new Vector2(Box2DDataUtil.LEFT_SMALL_CIRCLE.x, Box2DDataUtil.LEFT_SMALL_CIRCLE.y);
		
		table.getBall().setBox2DPosition(otherPosition);
		assertFalse(table.isBallOnLeftPenaltyPosition());
		
		table.getBall().setBox2DPosition(leftPenaltyPosition);
		assertTrue(table.isBallOnLeftPenaltyPosition());
	}
	
	/**
	 * Test for {@link Table#isBallOnRightPenaltyPosition()} method.
	 */
	@Test
	public void test_isBallOnRightPenaltyPosition(){
		final Vector2 otherPosition = new Vector2(100, 200);
		final Vector2 rightPenaltyPosition = new Vector2(Box2DDataUtil.RIGHT_SMALL_CIRCLE.x, Box2DDataUtil.LEFT_SMALL_CIRCLE.y);
		
		table.getBall().setBox2DPosition(otherPosition);
		assertFalse(table.isBallOnRightPenaltyPosition());
		
		table.getBall().setBox2DPosition(rightPenaltyPosition);
		assertTrue(table.isBallOnRightPenaltyPosition());
	}
}
