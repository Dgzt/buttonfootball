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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.dgzt.core.shape.RectangleBorderShape;
import com.dgzt.core.shape.Shape;

/**
 * The time board.
 * 
 * @author Dgzt
 */
public class TimeBoard extends RectangleBorderShape{

	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width of scoreboard in cm. */
	public static final float WIDTH = 4 * Digit.TIME_DIGIT_WIDTH + SecondCircles.WIDTH + Shape.LINE_WIDTH;
	
	/** The height of scoreboard in cm. */
	public static final float HEIGHT = Digit.TIME_DIGIT_HEIGHT + Shape.LINE_WIDTH;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** 13 minute. */
	private static final int HALF_TIME = 13 * 60;
	
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
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public TimeBoard(final ShapeRenderer shapeRenderer) {
		super(shapeRenderer);
		
		firstMinDigit = new Digit(shapeRenderer, Digit.TIME_DIGIT_WIDTH, Digit.TIME_DIGIT_HEIGHT);
		secondMinDigit = new Digit(shapeRenderer, Digit.TIME_DIGIT_WIDTH, Digit.TIME_DIGIT_HEIGHT);
		
		secondCircles = new SecondCircles(shapeRenderer);
		
		firstSecDigit = new Digit(shapeRenderer, Digit.TIME_DIGIT_WIDTH, Digit.TIME_DIGIT_HEIGHT);
		secondSecDigit = new Digit(shapeRenderer, Digit.TIME_DIGIT_WIDTH, Digit.TIME_DIGIT_HEIGHT);
		
		currentTime = HALF_TIME;
		setCurrentTime();
		Timer.schedule(new Task() {
			
			@Override
			public void run() {
				--currentTime;
				setCurrentTime();
			}
		}, 1,1);
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
		
		Gdx.app.log(TimeBoard.class.getName()+".setCurrentTime", "Min: "+String.valueOf(minute)+"\t Sec:"+String.valueOf(secondum));
		
		secondSecDigit.setNumber(secondSec);
		firstSecDigit.setNumber(firstSec);
		secondMinDigit.setNumber(secondMin);
		firstMinDigit.setNumber(firstMin);
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
		
		final float halfLineWidth = (float)(Shape.LINE_WIDTH * scale) / 2;
		final float digitWidth = (float)(Digit.TIME_DIGIT_WIDTH * scale);
		final float digitHeight = (float)(Digit.TIME_DIGIT_HEIGHT * scale);
		final float secondCirclesWidth = (float)(SecondCircles.WIDTH * scale);
		final float secondCirclesHeight = (float)(SecondCircles.HEIGHT * scale);
		
		firstMinDigit.resize(x + halfLineWidth, y + halfLineWidth, digitWidth, digitHeight, scale);
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
		
		secondCircles.draw();
		
		firstSecDigit.draw();
		secondSecDigit.draw();
	}

}
