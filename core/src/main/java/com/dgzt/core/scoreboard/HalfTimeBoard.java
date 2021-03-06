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
import com.dgzt.core.HalfTimeType;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;

/**
 * The halftime-board.
 * 
 * @author Dgzt
 */
public class HalfTimeBoard extends RectangleBorderShape{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width value in cm. */
	public static final float WIDTH = Digit.GOAL_DIGIT_WIDTH + 2*LineShape.LINE_WIDTH;
	
	/** The height value in cm. */
	public static final float HEIGHT = Digit.GOAL_DIGIT_HEIGHT + 2*LineShape.LINE_WIDTH;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The digit. */
	private final Digit digit;

	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	public HalfTimeBoard(final ShaderProgram shader, final Color color) {
		super(shader, color);
		
		digit = new Digit(shader, Digit.GOAL_DIGIT_WIDTH, Digit.GOAL_DIGIT_HEIGHT, color);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Return with the type of the half time.
	 */
	public HalfTimeType getHalfTimeType() {
		return HalfTimeType.getByValue(digit.getNumber());
	}

	/**
	 * Set the type of the half time.
	 * 
	 * @param halfTimeType - The new type of half time.
	 */
	public void setHalfTimeType(HalfTimeType halfTimeType) {
		digit.setNumber(halfTimeType.getValue());
	}

	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resize(float x, float y, float width, float height, double scale) {
		super.resize(x, y, width, height, scale);
		
		final float lineWidth = (float)(LineShape.LINE_WIDTH * scale);
		final float digitWidth = (float)(Digit.GOAL_DIGIT_WIDTH * scale);
		final float digitHeight = (float)(Digit.GOAL_DIGIT_HEIGHT * scale);
		
		digit.resize(x + lineWidth, y + lineWidth, digitWidth, digitHeight, scale);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		super.draw();
		
		digit.draw();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		digit.dispose();
		
		super.dispose();
	}

}
