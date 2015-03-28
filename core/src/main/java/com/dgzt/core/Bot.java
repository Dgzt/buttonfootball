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
	
	/** The bot's buttons. */
	private final List<Button> botButtons;
	
	/** The ball. */
	private final Ball ball;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param botButtons - The bot's buttons.
	 * @param ball - The ball.
	 */
	public Bot(final List<Button> botButtons, final Ball ball){
		this.botButtons = botButtons;
		this.ball = ball;
	}
	
	/**
	 * The bot step.
	 */
	public void step(){
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
