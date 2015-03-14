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

import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.util.MathUtil;

/**
 * The opponent roBot.
 * 
 * @author Dgzt
 */
public class Bot {
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The game control. */
	private final GameControl gameControl;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param gameControl - The game control.
	 */
	public Bot(final GameControl gameControl){
		this.gameControl = gameControl;
	}
	
	/**
	 * The bot step.
	 */
	public void step(){
		final Ball ball = gameControl.getMainWindow().getTable().getBall();
		final List<Button> botButtons = gameControl.getMainWindow().getTable().getOpponentButtons();
		
		// TODO - Temp step
		
		double lowestDistance = Double.MAX_VALUE;
		Button lowestDistanceButton = null;
		for( final Button button : botButtons ){
			final double actualDistance = MathUtil.distance(ball.getBox2DX(), ball.getBox2DY(), button.getBox2DX(), button.getBox2DY());
			
			if(actualDistance < lowestDistance){
				lowestDistance = actualDistance;
				lowestDistanceButton = button;
			}
		}
		
		final float power = 4;
		
		lowestDistanceButton.move(power * (ball.getBox2DX() - lowestDistanceButton.getBox2DX()), power * (ball.getBox2DY() - lowestDistanceButton.getBox2DY())); 
	}
	
}
