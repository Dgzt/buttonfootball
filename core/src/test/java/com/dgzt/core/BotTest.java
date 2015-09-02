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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.math.Vector2;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;

/**
 * Test for {@link Bot}.
 * 
 * @author Dgzt
 */
public final class BotTest extends BaseShapeTester{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	private static final float BALL_BOX2D_X = 300.0f;
	private static final float BALL_BOX2D_Y = 600.0f;
	
	private static final Vector2 NEAREST_BUTTON_BOX2D_POS = new Vector2(BALL_BOX2D_X + 10, BALL_BOX2D_Y + 15);
	private static final Vector2 SECOND_NEAREST_BUTTON_BOX2D_POS = new Vector2(BALL_BOX2D_X + 20, BALL_BOX2D_Y + 30);
	private static final Vector2 THIRD_NEAREST_BUTTON_BOX2D_POS = new Vector2(BALL_BOX2D_X + 40, BALL_BOX2D_Y + 60);
	
	private static final float MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D = 5.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private Table table;
	
	private Bot bot;
	
	// --------------------------------------------------
	// ~ Init methods
	// --------------------------------------------------
	
	/**
	 * Init for tests.
	 */
	@Before
	public void setUp(){
		// First bot's button
		final Button botButton1 = getMockButton();
		final Button botButton2 = getMockButton();
		final Button botButton3 = getMockButton();
		
		final List<Button> botButtons = new ArrayList<Button>();
		botButtons.add(botButton1);
		botButtons.add(botButton2);
		botButtons.add(botButton3);
		
		// Ball
		final Ball ball = Mockito.mock(Ball.class);
		Mockito.when(ball.getBox2DX()).thenReturn(BALL_BOX2D_X);
		Mockito.when(ball.getBox2DY()).thenReturn(BALL_BOX2D_Y);
		
		// Table
		table = Mockito.mock(Table.class);
		Mockito.when(table.getBall()).thenReturn(ball);
		Mockito.when(table.getOpponentButtons()).thenReturn(botButtons);
		
		bot = new Bot(table);
	}
	
	// --------------------------------------------------
	// ~ Test methods
	// --------------------------------------------------
	
	/**
	 * Test for move one button to the top left corner.
	 */
	@Test
	public void test_moveOneButtonToTheTopLeftCorner(){
		table.getOpponentButtons().get(0).setBox2DPosition(SECOND_NEAREST_BUTTON_BOX2D_POS.x, SECOND_NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(1).setBox2DPosition(NEAREST_BUTTON_BOX2D_POS.x, NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(2).setBox2DPosition(THIRD_NEAREST_BUTTON_BOX2D_POS.x, THIRD_NEAREST_BUTTON_BOX2D_POS.y);
		
		Mockito.when(table.isBallOnTopLeftCornerOfMap()).thenReturn(Boolean.TRUE);
		
		bot.moveOneButton();
		
		final Vector2 nearestButtonNewBox2DPosition = new Vector2(BALL_BOX2D_X - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D, BALL_BOX2D_Y - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D);
		
		Assert.assertEquals(table.getOpponentButtons().get(0).getBox2DPosition(), SECOND_NEAREST_BUTTON_BOX2D_POS);
		Assert.assertEquals(table.getOpponentButtons().get(1).getBox2DPosition(), nearestButtonNewBox2DPosition);
		Assert.assertEquals(table.getOpponentButtons().get(2).getBox2DPosition(), THIRD_NEAREST_BUTTON_BOX2D_POS);
	}
	
	/**
	 * Test for move one button to the top border.
	 */
	@Test
	public void test_moveOneButtonToTheTopBorder(){
		table.getOpponentButtons().get(0).setBox2DPosition(SECOND_NEAREST_BUTTON_BOX2D_POS.x, SECOND_NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(1).setBox2DPosition(NEAREST_BUTTON_BOX2D_POS.x, NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(2).setBox2DPosition(THIRD_NEAREST_BUTTON_BOX2D_POS.x, THIRD_NEAREST_BUTTON_BOX2D_POS.y);
		
		Mockito.when(table.isBallOnTopBorderOfMap()).thenReturn(Boolean.TRUE);
		
		bot.moveOneButton();
		
		final Vector2 nearestButtonNewBox2DPosition = new Vector2(BALL_BOX2D_X, BALL_BOX2D_Y - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D);
		
		Assert.assertEquals(table.getOpponentButtons().get(0).getBox2DPosition(), SECOND_NEAREST_BUTTON_BOX2D_POS);
		Assert.assertEquals(table.getOpponentButtons().get(1).getBox2DPosition(), nearestButtonNewBox2DPosition);
		Assert.assertEquals(table.getOpponentButtons().get(2).getBox2DPosition(), THIRD_NEAREST_BUTTON_BOX2D_POS);
	}
	
	/**
	 * Test for move one button to the top right corner.
	 */
	@Test
	public void test_moveOneButtonToTheTopRightCorner(){
		table.getOpponentButtons().get(0).setBox2DPosition(SECOND_NEAREST_BUTTON_BOX2D_POS.x, SECOND_NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(1).setBox2DPosition(NEAREST_BUTTON_BOX2D_POS.x, NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(2).setBox2DPosition(THIRD_NEAREST_BUTTON_BOX2D_POS.x, THIRD_NEAREST_BUTTON_BOX2D_POS.y);
		
		Mockito.when(table.isBallOnTopRightCornerOfMap()).thenReturn(Boolean.TRUE);
		
		bot.moveOneButton();
		
		final Vector2 nearestButtonNewBox2DPosition = new Vector2(BALL_BOX2D_X + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D, BALL_BOX2D_Y - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D);
		
		Assert.assertEquals(table.getOpponentButtons().get(0).getBox2DPosition(), SECOND_NEAREST_BUTTON_BOX2D_POS);
		Assert.assertEquals(table.getOpponentButtons().get(1).getBox2DPosition(), nearestButtonNewBox2DPosition);
		Assert.assertEquals(table.getOpponentButtons().get(2).getBox2DPosition(), THIRD_NEAREST_BUTTON_BOX2D_POS);
	}
	
	/**
	 * Test for move one button to the bottom left corner.
	 */
	@Test
	public void test_moveOneButtonToTheBottomLeftCorner(){
		table.getOpponentButtons().get(0).setBox2DPosition(SECOND_NEAREST_BUTTON_BOX2D_POS.x, SECOND_NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(1).setBox2DPosition(NEAREST_BUTTON_BOX2D_POS.x, NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(2).setBox2DPosition(THIRD_NEAREST_BUTTON_BOX2D_POS.x, THIRD_NEAREST_BUTTON_BOX2D_POS.y);
		
		Mockito.when(table.isBallOnBottomLeftCornerOfMap()).thenReturn(Boolean.TRUE);
		
		bot.moveOneButton();
		
		final Vector2 nearestButtonNewBox2DPosition = new Vector2(BALL_BOX2D_X - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D, BALL_BOX2D_Y + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D);
		
		Assert.assertEquals(table.getOpponentButtons().get(0).getBox2DPosition(), SECOND_NEAREST_BUTTON_BOX2D_POS);
		Assert.assertEquals(table.getOpponentButtons().get(1).getBox2DPosition(), nearestButtonNewBox2DPosition);
		Assert.assertEquals(table.getOpponentButtons().get(2).getBox2DPosition(), THIRD_NEAREST_BUTTON_BOX2D_POS);
	}
	
	/**
	 * Test for move one button to the bottom border.
	 */
	@Test
	public void test_moveOneButtonTotTheBottomBorder(){
		table.getOpponentButtons().get(0).setBox2DPosition(SECOND_NEAREST_BUTTON_BOX2D_POS.x, SECOND_NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(1).setBox2DPosition(NEAREST_BUTTON_BOX2D_POS.x, NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(2).setBox2DPosition(THIRD_NEAREST_BUTTON_BOX2D_POS.x, THIRD_NEAREST_BUTTON_BOX2D_POS.y);
		
		Mockito.when(table.isBallOnBottomBorderOfMap()).thenReturn(Boolean.TRUE);
		
		bot.moveOneButton();
		
		final Vector2 nearestButtonNewBox2DPosition = new Vector2(BALL_BOX2D_X, BALL_BOX2D_Y + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D);
		
		Assert.assertEquals(table.getOpponentButtons().get(0).getBox2DPosition(), SECOND_NEAREST_BUTTON_BOX2D_POS);
		Assert.assertEquals(table.getOpponentButtons().get(1).getBox2DPosition(), nearestButtonNewBox2DPosition);
		Assert.assertEquals(table.getOpponentButtons().get(2).getBox2DPosition(), THIRD_NEAREST_BUTTON_BOX2D_POS);
	}
	
	/**
	 * Test for move one button to the bottom right corner.
	 */
	@Test
	public void test_moveOneButtonToTheBottomRightCorner(){
		table.getOpponentButtons().get(0).setBox2DPosition(SECOND_NEAREST_BUTTON_BOX2D_POS.x, SECOND_NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(1).setBox2DPosition(NEAREST_BUTTON_BOX2D_POS.x, NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(2).setBox2DPosition(THIRD_NEAREST_BUTTON_BOX2D_POS.x, THIRD_NEAREST_BUTTON_BOX2D_POS.y);
		
		Mockito.when(table.isBallOnBottomRightCornerOfMap()).thenReturn(Boolean.TRUE);
		
		bot.moveOneButton();
		
		final Vector2 nearestButtonNewBox2DPosition = new Vector2(BALL_BOX2D_X + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D, BALL_BOX2D_Y + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D);
		
		Assert.assertEquals(table.getOpponentButtons().get(0).getBox2DPosition(), SECOND_NEAREST_BUTTON_BOX2D_POS);
		Assert.assertEquals(table.getOpponentButtons().get(1).getBox2DPosition(), nearestButtonNewBox2DPosition);
		Assert.assertEquals(table.getOpponentButtons().get(2).getBox2DPosition(), THIRD_NEAREST_BUTTON_BOX2D_POS);
	}
	
	/**
	 * Test for move some button to the left goal kick position.
	 */
	@Test
	public void test_moveSomeButtonToLeftGoalKickPosition(){
		table.getOpponentButtons().get(0).setBox2DPosition(SECOND_NEAREST_BUTTON_BOX2D_POS.x, SECOND_NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(1).setBox2DPosition(NEAREST_BUTTON_BOX2D_POS.x, NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(2).setBox2DPosition(THIRD_NEAREST_BUTTON_BOX2D_POS.x, THIRD_NEAREST_BUTTON_BOX2D_POS.y);
		
		Mockito.when(table.isBallOnLeftGoalKickPosition()).thenReturn(Boolean.TRUE);
		
		bot.moveSomeButton();
		
		final Vector2 nearestButtonNewBox2DPosition = new Vector2(BALL_BOX2D_X - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D, BALL_BOX2D_Y);
		
		Assert.assertEquals(table.getOpponentButtons().get(0).getBox2DPosition(), SECOND_NEAREST_BUTTON_BOX2D_POS);
		Assert.assertEquals(table.getOpponentButtons().get(1).getBox2DPosition(), nearestButtonNewBox2DPosition);
		Assert.assertEquals(table.getOpponentButtons().get(2).getBox2DPosition(), THIRD_NEAREST_BUTTON_BOX2D_POS);
	}

	/**
	 * Test for move some button to the right goal kick position.
	 */
	@Test
	public void test_moveSomeButtonToRightGoalKickPosition(){
		table.getOpponentButtons().get(0).setBox2DPosition(SECOND_NEAREST_BUTTON_BOX2D_POS.x, SECOND_NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(1).setBox2DPosition(NEAREST_BUTTON_BOX2D_POS.x, NEAREST_BUTTON_BOX2D_POS.y);
		table.getOpponentButtons().get(2).setBox2DPosition(THIRD_NEAREST_BUTTON_BOX2D_POS.x, THIRD_NEAREST_BUTTON_BOX2D_POS.y);
		
		Mockito.when(table.isBallOnRightGoalKickPosition()).thenReturn(Boolean.TRUE);
		
		bot.moveSomeButton();
		
		final Vector2 nearestButtonNewBox2DPosition = new Vector2(BALL_BOX2D_X + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D, BALL_BOX2D_Y);
		
		Assert.assertEquals(table.getOpponentButtons().get(0).getBox2DPosition(), SECOND_NEAREST_BUTTON_BOX2D_POS);
		Assert.assertEquals(table.getOpponentButtons().get(1).getBox2DPosition(), nearestButtonNewBox2DPosition);
		Assert.assertEquals(table.getOpponentButtons().get(2).getBox2DPosition(), THIRD_NEAREST_BUTTON_BOX2D_POS);
	}
}
