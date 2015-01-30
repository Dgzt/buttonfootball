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
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;

/**
 * The goal board.
 * 
 * @author Dgzt
 */
final public class GoalBoard extends RectangleBorderShape{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width value in cm. */
	public static final float WIDTH = 2*Digit.GOAL_DIGIT_WIDTH + 2*LineShape.LINE_WIDTH;
	
	/** The height value in cm. */
	public static final float HEIGHT = (Digit.GOAL_DIGIT_HEIGHT + 2*LineShape.LINE_WIDTH);
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The first digit. */
	private final Digit firstDigit;
	
	/** The second digit. */
	private final Digit secondDigit;

	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	public GoalBoard(final ShaderProgram shader, final Color color) {
		super(shader, color);
		
		firstDigit = new Digit(shader, Digit.GOAL_DIGIT_WIDTH, Digit.GOAL_DIGIT_HEIGHT, color);
		secondDigit = new Digit(shader, Digit.GOAL_DIGIT_WIDTH, Digit.GOAL_DIGIT_HEIGHT, color);
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
		final float digitWidth = (float)(Digit.GOAL_DIGIT_WIDTH * scale);
		final float digitHeight = (float)(Digit.GOAL_DIGIT_HEIGHT * scale);
		
		firstDigit.resize(x + lineWidth, y + lineWidth, digitWidth, digitHeight, scale);
		secondDigit.resize(firstDigit.getX() + digitWidth, firstDigit.getY(), digitWidth, digitHeight, scale);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		super.draw();
		
		firstDigit.draw();
		secondDigit.draw();
	}

}
