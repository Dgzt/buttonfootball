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
package com.dgzt.core.scoreboard;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;

/**
 * The time board.
 * 
 * @author Dgzt
 */
final public class TimeBoard extends RectangleBorderShape{

	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width of scoreboard in cm. */
	public static final float WIDTH = 4*Digit.TIME_DIGIT_WIDTH + SecondCircles.WIDTH + 2*LineShape.LINE_WIDTH;
	
	/** The height of scoreboard in cm. */
	public static final float HEIGHT = Digit.TIME_DIGIT_HEIGHT + 2*LineShape.LINE_WIDTH;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The delay second of the timer. */
	private static final int TIMER_DELAY_SECOND = 1;
	
	/** The interval second of the timer. */
	private static final int TIMER_INTERVAL_SECOND = 1;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The first minute digit. */
	private final Digit firstMinDigit;
	
	/** The second minute digit. */
	private final Digit secondMinDigit;
	
	/** The second circles. */
	private final SecondCircles secondCircles;
	
	/** The first moment digit. */
	private final Digit firstSecDigit;
	
	/** The second moment digit. */
	private final Digit secondSecDigit;
	
	/** The current time. */
	private int currentTime;
	
	/** Visible the second circles. */
	private boolean visibleSecondCircles;
	
	/** The half time. */
	private int halfTime;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	public TimeBoard(final ShaderProgram shader, final Color color) {
		super(shader, color);
		this.halfTime = 0;
		
		firstMinDigit = new Digit(shader, Digit.TIME_DIGIT_WIDTH, Digit.TIME_DIGIT_HEIGHT, color);
		secondMinDigit = new Digit(shader, Digit.TIME_DIGIT_WIDTH, Digit.TIME_DIGIT_HEIGHT, color);
		
		secondCircles = new SecondCircles(shader);
		
		firstSecDigit = new Digit(shader, Digit.TIME_DIGIT_WIDTH, Digit.TIME_DIGIT_HEIGHT, color);
		secondSecDigit = new Digit(shader, Digit.TIME_DIGIT_WIDTH, Digit.TIME_DIGIT_HEIGHT, color);
	}

	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Start the time board.
	 */
	public void start(){
		currentTime = halfTime;
		setCurrentTime();
		visibleSecondCircles = isVisibleSecondCircles();
		Timer.schedule(new Task() {
			
			@Override
			public void run() {
				--currentTime;
				setCurrentTime();
				
				visibleSecondCircles = isVisibleSecondCircles();
			}
		}, TIMER_DELAY_SECOND, TIMER_INTERVAL_SECOND);
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Set the current time on the digits.
	 */
	private void setCurrentTime(){
		final int secondum = currentTime % 60;
		final int minute = currentTime / 60;
		final int secondSec = secondum % 10;
		final int firstSec = secondum / 10;
		final int secondMin = minute % 10;
		final int firstMin = minute / 10;
		
		secondSecDigit.setNumber(secondSec);
		firstSecDigit.setNumber(firstSec);
		secondMinDigit.setNumber(secondMin);
		firstMinDigit.setNumber(firstMin);
	}
	
	/**
	 * Return the visibility of second circles. The second circles visible every even second.
	 */
	private boolean isVisibleSecondCircles(){
		return currentTime % 2 == 0;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resize(final float x, final float y, final float width, final float height, final double scale) {
		super.resize(x, y, width, height, scale);
		
		final float lineWidth = (float)(LineShape.LINE_WIDTH * scale);
		final float digitWidth = (float)(Digit.TIME_DIGIT_WIDTH * scale);
		final float digitHeight = (float)(Digit.TIME_DIGIT_HEIGHT * scale);
		final float secondCirclesWidth = (float)(SecondCircles.WIDTH * scale);
		final float secondCirclesHeight = (float)(SecondCircles.HEIGHT * scale);
		
		firstMinDigit.resize(x + lineWidth, y + lineWidth, digitWidth, digitHeight, scale);
		secondMinDigit.resize(firstMinDigit.getX() + digitWidth, firstMinDigit.getY(), digitWidth, digitHeight, scale);
		
		secondCircles.resize(secondMinDigit.getX() + digitWidth, y, secondCirclesWidth, secondCirclesHeight, scale);
		
		firstSecDigit.resize(secondCircles.getX() + secondCircles.getWidth(), secondMinDigit.getY(), digitWidth, digitHeight, scale);
		secondSecDigit.resize(firstSecDigit.getX() + digitWidth, firstSecDigit.getY(), digitWidth, digitHeight, scale);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		super.draw();
		
		firstMinDigit.draw();
		secondMinDigit.draw();
		
		if(visibleSecondCircles){
			secondCircles.draw();
		}
		
		firstSecDigit.draw();
		secondSecDigit.draw();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(){
		firstMinDigit.dispose();
		secondMinDigit.dispose();
		
		secondCircles.dispose();
		
		firstSecDigit.dispose();
		secondSecDigit.dispose();
		
		super.dispose();
	}

	// --------------------------------------------------
	// ~ Getter / Setter methods
	// --------------------------------------------------
	
	/**
	 * Set the half time.
	 * 
	 * @param halfTime - The new half time.
	 */
	public void setHalfTime(int halfTime) {
		this.halfTime = halfTime;
	}

}
