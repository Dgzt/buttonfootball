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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.gate.LeftGate;
import com.dgzt.core.gate.RightGate;
import com.dgzt.core.scoreboard.HalfTimeBoard;
import com.dgzt.core.scoreboard.ScoreBoard;
import com.dgzt.core.scoreboard.TimeLeftBoard;
import com.dgzt.core.setting.Settings;
import com.dgzt.core.util.Box2DDataUtil;

/**
 * Test for {@link GameControl}.
 * 
 * @author Dgzt
 */
public final class GameControlTest extends BaseShapeTester{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	private static final Rectangle MAP_BOX2D_RECTANGLE = Box2DDataUtil.MAP_RECTANGLE;
	private static final Vector2 LEFT_GATE_BOX2D_POSITION = new Vector2(5, MAP_BOX2D_RECTANGLE.y + 50);
	private static final Vector2 RIGHT_GATE_BOX2D_POSITION = new Vector2(500, MAP_BOX2D_RECTANGLE.y + 50);
	
	private static final Vector2 LEFT_GOAL_KICK_BOX2D_POSITION = new Vector2(MAP_BOX2D_RECTANGLE.x + 10, 10);
	private static final Vector2 RIGHT_GOAL_KICK_BOX2D_POSITION = new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width - 10, 10);
	
	private static final Vector2 BALL_LEAVED_MAP_TOP_LEFT = new Vector2(MAP_BOX2D_RECTANGLE.x - 2, MAP_BOX2D_RECTANGLE.y + 10);
	private static final Vector2 BALL_LEAVED_MAP_TOP_RIGHT = new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width + 2, MAP_BOX2D_RECTANGLE.y + 10);
	private static final Vector2 BALL_LEAVED_MAP_BOTTOM_LEFT = new Vector2(MAP_BOX2D_RECTANGLE.x - 2, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height - 10);
	private static final Vector2 BALL_LEAVED_MAP_BOTTOM_RIGHT = new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width + 2, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height - 10);
	private static final Vector2 BALL_LEAVED_MAP_TOP = new Vector2(MAP_BOX2D_RECTANGLE.x + 2, MAP_BOX2D_RECTANGLE.y - 2);
	private static final Vector2 BALL_LEAVED_MAP_BOTTOM = new Vector2(MAP_BOX2D_RECTANGLE.x + 2, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height + 2);
	private static final Vector2 BALL_ON_MAP = new Vector2(MAP_BOX2D_RECTANGLE.x + 40, MAP_BOX2D_RECTANGLE.y + 30);
	
	private static final Vector2 TOP_LEFT_CORCER_KICK_BOX2D_POSITION = new Vector2(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y);
	private static final Vector2 TOP_RIGHT_CORCER_KICK_BOX2D_POSITION = new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y);
	private static final Vector2 BOTTOM_LEFT_CORCER_KICK_BOX2D_POSITION = new Vector2(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
	private static final Vector2 BOTTOM_RIGHT_CORCER_KICK_BOX2D_POSITION = new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
	private static final Vector2 THROW_IN_TOP_BOX2D_POSITION = new Vector2(BALL_LEAVED_MAP_TOP.x, MAP_BOX2D_RECTANGLE.y);
	private static final Vector2 THROW_IN_BOTTOM_BOX2D_POSITION = new Vector2(BALL_LEAVED_MAP_TOP.x, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height);
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The mock table. */
	private Table table;
	
	/** The mock score board. */
	private ScoreBoard scoreBoard;
	
	/** The mock game control. */
	private GameControl gameControl;
	
	// --------------------------------------------------
	// ~ Init methods
	// --------------------------------------------------
	
	/**
	 * Init for tests.
	 */
	@Before
	public void setUp(){
		final Settings settings = new Settings();
		final MainWindow mainWindow = Mockito.mock(MainWindow.class);
		
		final GameWindow gameWindow = Mockito.mock(GameWindow.class);
		Mockito.when(mainWindow.getGameWindow()).thenReturn(gameWindow);
		
		scoreBoard = Mockito.mock(ScoreBoard.class);
		
		// Opponent buttons
		final List<Button> opponentButtons = new ArrayList<Button>();
		opponentButtons.add(Mockito.mock(Button.class));
		
		// Table
		table = Mockito.mock(Table.class);
		Mockito.when(table.getPlayerButtons()).thenReturn(new ArrayList<Button>());
		Mockito.when(table.getOpponentButtons()).thenReturn(opponentButtons);
		
		// Map
		Map map = Mockito.mock(Map.class);
		Mockito.when(map.getLeftGoalKickBox2DPosition()).thenReturn(LEFT_GOAL_KICK_BOX2D_POSITION);
		Mockito.when(map.getRightGoalKickBox2DPosition()).thenReturn(RIGHT_GOAL_KICK_BOX2D_POSITION);
		Mockito.when(table.getMap()).thenReturn(map);
		
		// Ball
		Ball ball = getMockBall();
		Mockito.when(table.getBall()).thenReturn(ball);
		
		// Left gate
		LeftGate leftGate = Mockito.mock(LeftGate.class);
		Mockito.when(leftGate.getBox2DPosition()).thenReturn(LEFT_GATE_BOX2D_POSITION);
		Mockito.when(table.getLeftGate()).thenReturn(leftGate);
		
		// Right gate
		RightGate rightGate = Mockito.mock(RightGate.class);
		Mockito.when(rightGate.getBox2DPosition()).thenReturn(RIGHT_GATE_BOX2D_POSITION);
		Mockito.when(table.getRightGate()).thenReturn(rightGate);	
		
		// Half time board
		HalfTimeBoard halfTimeBoard = Mockito.mock(HalfTimeBoard.class);
		Mockito.when(scoreBoard.getHalfTimeBoard()).thenReturn(halfTimeBoard);
		
		// Player time left board
		final TimeLeftBoard playerTimeLeftBoard = Mockito.mock(TimeLeftBoard.class);
		Mockito.when(scoreBoard.getPlayerTimeLeftBoard()).thenReturn(playerTimeLeftBoard);
		
		// Opponent time left board
		final TimeLeftBoard opponentTimeLeftBoard = Mockito.mock(TimeLeftBoard.class);
		Mockito.when(scoreBoard.getOpponentTimeLeftBoard()).thenReturn(opponentTimeLeftBoard);
		
		final EventListener eventListener = Mockito.mock(EventListener.class);
		
		gameControl = new GameControl(mainWindow, scoreBoard, table, settings, eventListener);
	}
	
	// --------------------------------------------------
	// ~ Test methods
	// --------------------------------------------------
	
	/**
	 * Test for player goal kick on the left side.
	 */
	@Test
	public void test_leftGoalKickFromPlayer() {
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_LEFT.x, BALL_LEAVED_MAP_TOP_LEFT.y);
		
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_SOME_BUTTON, gameControl.getGameStatus());
		assertEquals(LEFT_GOAL_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for opponent goal kick on the left side.
	 */
	@Test
	public void test_leftGoalKickFromOpponent() {
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getPlayerButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_LEFT.x, BALL_LEAVED_MAP_TOP_LEFT.y);
		
		Mockito.when(table.isBallOnLeftGoalKickPosition()).thenReturn(Boolean.TRUE);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(LEFT_GOAL_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for player goal kick on the right side.
	 */
	@Test
	public void test_rightGoalKickFromPlayer(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_RIGHT.x, BALL_LEAVED_MAP_TOP_RIGHT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_SOME_BUTTON, gameControl.getGameStatus());
		assertEquals(RIGHT_GOAL_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for opponent goal kick on the right side.
	 */
	@Test
	public void test_rightGoalKickFromOpponent(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getPlayerButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_RIGHT.x, BALL_LEAVED_MAP_TOP_RIGHT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		Mockito.when(table.isBallOnRightGoalKickPosition()).thenReturn(Boolean.TRUE);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(RIGHT_GOAL_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for opponent corner kick on the top left corner.
	 */
	@Test
	public void test_topLeftCornerKickFromOpponent(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getPlayerButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_LEFT.x, BALL_LEAVED_MAP_TOP_LEFT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		Mockito.when(table.isBallOnTopLeftCornerOfMap()).thenReturn(Boolean.TRUE);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(TOP_LEFT_CORCER_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for player corner kick on the top left corner.
	 */
	@Test
	public void test_topLeftCornerKickFromPlayer(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_LEFT.x, BALL_LEAVED_MAP_TOP_LEFT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_ONE_BUTTON, gameControl.getGameStatus());
		assertEquals(TOP_LEFT_CORCER_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for opponent corner kick on the bottom left corner.
	 */
	@Test
	public void test_bottomLeftCornerKickFromOpponent(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getPlayerButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_BOTTOM_LEFT.x, BALL_LEAVED_MAP_BOTTOM_LEFT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		Mockito.when(table.isBallOnBottomLeftCornerOfMap()).thenReturn(Boolean.TRUE);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(BOTTOM_LEFT_CORCER_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for player corner kick on the bottom left corner.
	 */
	@Test
	public void test_bottomLeftCornerKickFromPlayer(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_BOTTOM_LEFT.x, BALL_LEAVED_MAP_BOTTOM_LEFT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_ONE_BUTTON, gameControl.getGameStatus());
		assertEquals(BOTTOM_LEFT_CORCER_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for player corner kick on the top right corner.
	 */
	@Test
	public void test_topRightCornerKickFromPlayer(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_RIGHT.x, BALL_LEAVED_MAP_TOP_RIGHT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_ONE_BUTTON, gameControl.getGameStatus());
		assertEquals(TOP_RIGHT_CORCER_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for opponent corner kick on the top right corner.
	 */
	@Test
	public void test_topRightCornerKickFromOpponent(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getPlayerButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_RIGHT.x, BALL_LEAVED_MAP_TOP_RIGHT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		Mockito.when(table.isBallOnTopRightCornerOfMap()).thenReturn(Boolean.TRUE);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(TOP_RIGHT_CORCER_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for player corner kick on the bottom right corner.
	 */
	@Test
	public void test_bottomRightCornerKickFromPlayer(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_BOTTOM_RIGHT.x, BALL_LEAVED_MAP_BOTTOM_RIGHT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_ONE_BUTTON, gameControl.getGameStatus());
		assertEquals(BOTTOM_RIGHT_CORCER_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for opponent corner kick on the bottom right corner.
	 */
	@Test
	public void test_bottomRightCornerKickFromOpponent(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getPlayerButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_BOTTOM_RIGHT.x, BALL_LEAVED_MAP_BOTTOM_RIGHT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		Mockito.when(table.isBallOnBottomRightCornerOfMap()).thenReturn(Boolean.TRUE);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(BOTTOM_RIGHT_CORCER_KICK_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for player throw in on the top side.
	 */
	@Test
	public void test_throwInOnTopSideByPlayer(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP.x, BALL_LEAVED_MAP_TOP.y);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_ONE_BUTTON, gameControl.getGameStatus());
		assertEquals(THROW_IN_TOP_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for opponent throw in on the top side.
	 */
	@Test
	public void test_throwInOnTopSideByOpponent(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getPlayerButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP.x, BALL_LEAVED_MAP_TOP.y);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		Mockito.when(table.isBallOnTopBorderOfMap()).thenReturn(Boolean.TRUE);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(THROW_IN_TOP_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for player throw in on the bottom side.
	 */
	@Test
	public void test_throwInOnBottomSideByPlayer(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_BOTTOM.x, BALL_LEAVED_MAP_BOTTOM.y);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_ONE_BUTTON, gameControl.getGameStatus());
		assertEquals(THROW_IN_BOTTOM_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for opponent throw in on the bottom side.
	 */
	@Test
	public void test_throwInOnBottomSideByOpponent(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getPlayerButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_BOTTOM.x, BALL_LEAVED_MAP_BOTTOM.y);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		Mockito.when(table.isBallOnBottomBorderOfMap()).thenReturn(Boolean.TRUE);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(THROW_IN_BOTTOM_BOX2D_POSITION, table.getBall().getBox2DPosition());
	}
	
	/**
	 * Test for time left end when player in game.
	 */
	@Test
	public void test_timeLeftAndAtPlayerPlayerInGame(){
		table.getBall().setBox2DPosition(BALL_ON_MAP.x, BALL_ON_MAP.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		
		gameControl.allButtonIsStoppedEvent();
		
		gameControl.afterOpponentBallArea();

		assertEquals(GameStatus.PLAYER_IN_GAME, gameControl.getGameStatus());
		
		gameControl.timeLeftEndEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
	}

	/**
	 * Test for time left end when player move one button.
	 */
	@Test
	public void test_timeLeftEndAtPlayerMoveOneButton(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_LEFT.x, BALL_LEAVED_MAP_TOP_LEFT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		Mockito.when(table.isBallOnTopLeftCornerOfMap()).thenReturn(Boolean.TRUE);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_ONE_BUTTON, gameControl.getGameStatus());

		gameControl.timeLeftEndEvent();
		assertEquals(GameStatus.PLAYER_IN_GAME, gameControl.getGameStatus());
	}
	
	/**
	 * Test for time left end when player move some button.
	 */
	@Test
	public void test_timeLeftAtPlayerMoveSomeButton(){
		final Button buttonContactBallLastTime = Mockito.mock(Button.class);
		table.getOpponentButtons().add(buttonContactBallLastTime);
		
		table.getBall().setBox2DPosition(BALL_LEAVED_MAP_TOP_LEFT.x, BALL_LEAVED_MAP_TOP_LEFT.y);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		gameControl.buttonContactBall(buttonContactBallLastTime);
		gameControl.ballLeaveMapEvent();
		
		Mockito.when(table.isBallOnTopLeftCornerOfMap()).thenReturn(Boolean.TRUE);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_SOME_BUTTON, gameControl.getGameStatus());

		gameControl.timeLeftEndEvent();
		assertEquals(GameStatus.PLAYER_IN_GAME, gameControl.getGameStatus());
	}
	
	/**
	 * Test for free kick by opponent.
	 */
	@Test
	public void test_freeKickByOpponent(){
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(10, 20);
		final Vector2 opponentButtonPos = new Vector2(20, 40);
		final Vector2 ballStartPos = new Vector2(0,0);
		final Vector2 ballEndPos = new Vector2(15, 30);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballStartPos.x, ballStartPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);
		
		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		
		gameControl.allButtonIsStoppedEvent();
		
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(ballEndPos, ball.getBox2DPosition());
	}
	
	/**
	 * Test for free kick by player.
	 */
	@Test
	public void test_freeKickByPlayer(){
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(10, 20);
		final Vector2 opponentButtonPos = new Vector2(20, 40);
		final Vector2 ballStartPos = new Vector2(0,0);
		final Vector2 ballEndPos = new Vector2(15, 30);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballStartPos.x, ballStartPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);
		
		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		
		gameControl.allButtonIsStoppedEvent();
		
		assertEquals(GameStatus.PLAYER_MOVE_SOME_BUTTON, gameControl.getGameStatus());
		assertEquals(ballEndPos, ball.getBox2DPosition());
	}
	
	/**
	 * Test for free kick in left sector 16 by player.
	 */
	@Test
	public void test_freeKickInLeftSector16ByPlayer(){
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(10, 20);
		final Vector2 opponentButtonPos = new Vector2(20, 40);
		final Vector2 ballStartPos = new Vector2(0,0);
		final Vector2 ballEndPos = new Vector2(15, 30);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballStartPos.x, ballStartPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);

		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.getMap().containsLeftSector16Box2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		
		gameControl.allButtonIsStoppedEvent();
		
		assertEquals(GameStatus.PLAYER_MOVE_SOME_BUTTON, gameControl.getGameStatus());
		assertEquals(ballEndPos, ball.getBox2DPosition());
	}
	
	/**
	 * Test for free kick in left sector 16 by opponent.
	 */
	@Test
	public void test_freeKickInLeftSector16ByOpponent(){
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(10, 20);
		final Vector2 opponentButtonPos = new Vector2(20, 40);
		final Vector2 ballStartPos = new Vector2(0,0);
		final Vector2 ballEndPos = new Vector2(15, 30);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballStartPos.x, ballStartPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);

		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.getMap().containsLeftSector16Box2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		
		gameControl.allButtonIsStoppedEvent();
		
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(ballEndPos, ball.getBox2DPosition());
	}
	
	/**
	 * Test for free kick in right sector 16 by opponent.
	 */
	@Test
	public void test_freeKickInRightSector16ByOpponent(){
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(10, 20);
		final Vector2 opponentButtonPos = new Vector2(20, 40);
		final Vector2 ballStartPos = new Vector2(0,0);
		final Vector2 ballEndPos = new Vector2(15, 30);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballStartPos.x, ballStartPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);

		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.getMap().containsRightSector16Box2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		
		gameControl.allButtonIsStoppedEvent();
		
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
		assertEquals(ballEndPos, ball.getBox2DPosition());
	}
	
	/**
	 * Test for free kick in right sector 16 by player.
	 */
	@Test
	public void test_freeKickInRightSector16ByPlayer(){
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(10, 20);
		final Vector2 opponentButtonPos = new Vector2(20, 40);
		final Vector2 ballStartPos = new Vector2(0,0);
		final Vector2 ballEndPos = new Vector2(15, 30);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballStartPos.x, ballStartPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);

		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.getMap().containsRightSector16Box2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		
		gameControl.allButtonIsStoppedEvent();
		
		assertEquals(GameStatus.PLAYER_MOVE_SOME_BUTTON, gameControl.getGameStatus());
		assertEquals(ballEndPos, ball.getBox2DPosition());
	}
	
	/**
	 * Test for fault out of map.
	 */
	@Test
	public void test_faultOutOfMap(){
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(40, 3);
		final Vector2 opponentButtonPos = new Vector2(45, 3);
		final Vector2 ballPos = new Vector2(100,100);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballPos.x, ballPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);
		
		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.FALSE);
		
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		
		gameControl.allButtonIsStoppedEvent();
		
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus()); // Show ball area
		assertEquals(ballPos, ball.getBox2DPosition());
	}
	
	/**
	 * Test for penalty on left side by opponent.
	 */
	@Test
	public void test_leftPenaltyKickByOpponent(){
		final Rectangle leftSector16Box2DRectangle = Box2DDataUtil.LEFT_SECTOR_16_RECTANGLE;
		
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(leftSector16Box2DRectangle.x + 5, leftSector16Box2DRectangle.y + 5);
		final Vector2 opponentButtonPos = new Vector2(leftSector16Box2DRectangle.x + 15, leftSector16Box2DRectangle.y + 15);
		final Vector2 ballPos = new Vector2(100,100);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballPos.x, ballPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);
		
		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.getMap().containsLeftSector16Box2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.isBallOnLeftPenaltyPosition()).thenReturn(Boolean.TRUE);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
	}
	
	/**
	 * Test for penalty on left side by player.
	 */
	@Test
	public void test_leftPenaltyKickByPlayer(){
		final Rectangle leftSector16Box2DRectangle = Box2DDataUtil.LEFT_SECTOR_16_RECTANGLE;
		
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(leftSector16Box2DRectangle.x + 5, leftSector16Box2DRectangle.y + 5);
		final Vector2 opponentButtonPos = new Vector2(leftSector16Box2DRectangle.x + 15, leftSector16Box2DRectangle.y + 15);
		final Vector2 ballPos = new Vector2(100,100);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballPos.x, ballPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);
		
		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.getMap().containsLeftSector16Box2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.isBallOnLeftPenaltyPosition()).thenReturn(Boolean.TRUE);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_ONE_BUTTON, gameControl.getGameStatus());
	}
	
	/**
	 * Test for penalty on right side by player.
	 */
	@Test
	public void test_rightPenaltyKickByPlayer(){
		final Rectangle rightSector16Box2DRectangle = Box2DDataUtil.RIGHT_SECTOR_16_RECTANGLE;
		
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(rightSector16Box2DRectangle.x + 5, rightSector16Box2DRectangle.y + 5);
		final Vector2 opponentButtonPos = new Vector2(rightSector16Box2DRectangle.x + 15, rightSector16Box2DRectangle.y + 15);
		final Vector2 ballPos = new Vector2(100,100);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballPos.x, ballPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);
		
		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.getMap().containsRightSector16Box2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.isBallOnRightPenaltyPosition()).thenReturn(Boolean.TRUE);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.FIRST_HALF);
		
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_OPPONENT);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.PLAYER_MOVE_ONE_BUTTON, gameControl.getGameStatus());
	}
	
	/**
	 * Test for penalty on right side by opponent.
	 */
	@Test
	public void test_rightPenaltyKickByOpponent(){
		final Rectangle rightSector16Box2DRectangle = Box2DDataUtil.RIGHT_SECTOR_16_RECTANGLE;
		
		final Ball ball = table.getBall();
		final Vector2 playerButtonPos = new Vector2(rightSector16Box2DRectangle.x + 5, rightSector16Box2DRectangle.y + 5);
		final Vector2 opponentButtonPos = new Vector2(rightSector16Box2DRectangle.x + 15, rightSector16Box2DRectangle.y + 15);
		final Vector2 ballPos = new Vector2(100,100);
		
		final Button playerButton = getMockButton();
		final Button opponentButton = getMockButton();
		
		playerButton.setBox2DPosition(playerButtonPos.x, playerButtonPos.y);
		opponentButton.setBox2DPosition(opponentButtonPos.x, opponentButtonPos.y);
		ball.setBox2DPosition(ballPos.x, ballPos.y);
		
		table.getPlayerButtons().add(playerButton);
		table.getOpponentButtons().add(opponentButton);
		
		Mockito.when(table.getMap().containsBox2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.getMap().containsRightSector16Box2DPosition(Mockito.any(Vector2.class))).thenReturn(Boolean.TRUE);
		Mockito.when(table.isBallOnRightPenaltyPosition()).thenReturn(Boolean.TRUE);
		Mockito.when(scoreBoard.getHalfTimeBoard().getHalfTimeType()).thenReturn(HalfTimeType.SECOND_HALF);
		
		gameControl.buttonContactButton(playerButton, opponentButton);
		
		gameControl.setGameStatus(GameStatus.WAITING_AFTER_PLAYER);
		
		gameControl.allButtonIsStoppedEvent();
		assertEquals(GameStatus.WAITING_AFTER_OPPONENT, gameControl.getGameStatus());
	}
}
