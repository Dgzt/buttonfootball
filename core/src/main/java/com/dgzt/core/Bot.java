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

import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.exception.IllegalBotButtonMoveException;
import com.dgzt.core.gate.AbstractGate;
import com.dgzt.core.util.MathUtil;

/**
 * The opponent roBot.
 * 
 * @author Dgzt
 */
public abstract class Bot {

	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	private static final float MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D = 5.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The table. */
	private final Table table;
	
	/** Selected button to move. */
	private Button selectedButton;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param table - The table.
	 */
	public Bot(final Table table){
		this.table = table;
		this.selectedButton = null;
	}
	
	// --------------------------------------------------
	// ~ Abstract methods
	// --------------------------------------------------
	
	/**
	 * Return that who is on the left side.
	 */
	protected abstract Player whoIsOnLeftSide();
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * The bot step.
	 */
	public void step(){
		// TODO - Temp step
		
		if(selectedButton == null){
			selectedButton = getLowestDistanceButton();	
		}
		final float power = 4;
		
		selectedButton.move(power * (table.getBall().getBox2DX() - selectedButton.getBox2DX()), power * (table.getBall().getBox2DY() - selectedButton.getBox2DY()));
		selectedButton = null;
	}
	
	/**
	 * Move one button to the ball.
	 */
	public void moveOneButton(){
		final Ball ball = table.getBall();
		selectedButton = getLowestDistanceButton();
		
		if(table.isBallOnTopLeftCornerOfMap()){
			selectedButton.setBox2DPosition(
					ball.getBox2DX() - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D,
					ball.getBox2DY() - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D
			);
		}else if(table.isBallOnTopBorderOfMap()){
			selectedButton.setBox2DPosition(
					ball.getBox2DX(),
					ball.getBox2DY() - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D
			);
		}else if(table.isBallOnTopRightCornerOfMap()){
			selectedButton.setBox2DPosition(
					ball.getBox2DX() + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D,
					ball.getBox2DY() - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D
			);
		}else if(table.isBallOnBottomLeftCornerOfMap()){
			selectedButton.setBox2DPosition(
					ball.getBox2DX() - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D,
					ball.getBox2DY() + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D
			);
		}else if(table.isBallOnBottomBorderOfMap()){
			selectedButton.setBox2DPosition(
					ball.getBox2DX(),
					ball.getBox2DY() + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D
			);
		}else if(table.isBallOnBottomRightCornerOfMap()){
			selectedButton.setBox2DPosition(
					ball.getBox2DX() + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D,
					ball.getBox2DY() + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D
			);
		}else{
			throw new IllegalBotButtonMoveException("Unhandled ball position on 'moveOneButton' function.");
		}
	}
	
	/**
	 * Move some button to the ball.
	 */
	public void moveSomeButton(){
		final Ball ball = table.getBall();
		selectedButton = getLowestDistanceButton();
		
		if(table.isBallOnLeftGoalKickPosition()){
			selectedButton.setBox2DPosition(
					ball.getBox2DX() - MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D,
					ball.getBox2DY()
			);
		}else if(table.isBallOnRightGoalKickPosition()){
			selectedButton.setBox2DPosition(
					ball.getBox2DX() + MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D,
					ball.getBox2DY()
			);
		}else{
			final boolean doAttackToLeftSide = whoIsOnLeftSide() == Player.PLAYER;
			final Vector2 ballPosition = ball.getBox2DPosition();
			final Vector2 gatePosition = doAttackToLeftSide ? table.getLeftGate().getBox2DPosition() : table.getRightGate().getBox2DPosition();
			gatePosition.add(0, AbstractGate.HEIGHT / 2);
			final boolean isGateUnderBall = gatePosition.y < ballPosition.y;
			
			final float x1 = doAttackToLeftSide ? ballPosition.x - gatePosition.x : gatePosition.x - ballPosition.x;
			final float y1 = isGateUnderBall ? ballPosition.y - gatePosition.y : gatePosition.y - ballPosition.y;
			
			// Angle in radiant
			final float angle = (float) Math.atan(y1/x1);
			
			final float x2 = MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D * MathUtils.cos(angle);
			final float y2 = MOVE_ONE_BUTTON_DISTANCE_IN_BOX2D * MathUtils.sin(angle);
			
			if(doAttackToLeftSide){
				ballPosition.x += x2;
			}else{
				ballPosition.x -= x2;
			}
			
			if(isGateUnderBall){
				ballPosition.y += y2;
			}else{
				ballPosition.y -= y2;
			}
			
			selectedButton.setBox2DPosition(ballPosition.x, ballPosition.y);
		}
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Get the lowest distance button from the ball.
	 * 
	 * @return A button.
	 */
	private Button getLowestDistanceButton(){
		final List<Button> botButtons = table.getOpponentButtons();
		double lowestDistance = Double.MAX_VALUE;
		Button lowestDistanceButton = null;
		
		for( final Button button : botButtons){
			final double actualDistance = MathUtil.distance(table.getBall().getBox2DX(), table.getBall().getBox2DY(), button.getBox2DX(), button.getBox2DY());
			
			if(actualDistance < lowestDistance){
				lowestDistance = actualDistance;
				lowestDistanceButton = button;
			}
		}
		
		return lowestDistanceButton;
	}
}
