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

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dgzt.core.shape.RectangleBorderShape;
import com.dgzt.core.shape.Shape;

/**
 * The scoreboard.
 * 
 * @author Dgzt
 */
public class ScoreBoard extends RectangleBorderShape{

	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width of scoreboard in cm. */
	public static final float WIDTH = 4 * Digit.WIDTH + SecondCircles.WIDTH + Shape.LINE_WIDTH;
	
	/** The height of scoreboard in cm. */
	public static final float HEIGHT = Digit.HEIGHT + Shape.LINE_WIDTH;
	
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
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public ScoreBoard(final ShapeRenderer shapeRenderer) {
		super(shapeRenderer);
		
		firstMinDigit = new Digit(shapeRenderer);
		secondMinDigit = new Digit(shapeRenderer);
		
		secondCircles = new SecondCircles(shapeRenderer);
		
		firstSecDigit = new Digit(shapeRenderer);
		secondSecDigit = new Digit(shapeRenderer);
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
		final float digitWidth = (float)(Digit.WIDTH * scale);
		final float digitHeight = (float)(Digit.HEIGHT * scale);
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