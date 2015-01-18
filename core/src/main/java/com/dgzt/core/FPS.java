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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.TimeUtils;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;

/**
 * Show the actual frame per second.
 * 
 * @author Dgzt
 */
public class FPS extends RectangleBorderShape{
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The width of digit in cm. */
	private static final float DIGIT_WIDTH = 6.5f;
	
	/** The height of digit in cm. */
	private static final float DIGIT_HEIGHT = 12.5f;
	
	/** The one second in nanosecond. */
	private static final float ONE_SECOND_IN_NANOSECOND = 1000000000;
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width in cm. */
	public static final float WIDTH = 2 * DIGIT_WIDTH + LineShape.LINE_WIDTH;
	
	/** The height in cm. */
	public static final float HEIGHT = DIGIT_HEIGHT + LineShape.LINE_WIDTH;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The first digit. */
	private final Digit firstFpsDigit;
	
	/** The second digit. */
	private final Digit secondFpsDigit;
	
	/** The start time. */
	private float startTime;
	
	/** Visible this object. */
	private boolean visible;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 */
	public FPS(final ShaderProgram shader){
		super(shader);
		visible = false;
		
		firstFpsDigit = new Digit(shader, DIGIT_WIDTH, DIGIT_HEIGHT);
		secondFpsDigit = new Digit(shader, DIGIT_WIDTH, DIGIT_HEIGHT);
		
		startTime = TimeUtils.nanoTime();
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
		
		final float halfLine = (float)(LineShape.LINE_WIDTH * scale) / 2;
		final float digitWidth = (float)(DIGIT_WIDTH * scale);
		final float digitHeight = (float)(DIGIT_HEIGHT * scale);
		
		firstFpsDigit.resize(x + halfLine, y + halfLine, digitWidth, digitHeight, scale);
		secondFpsDigit.resize(firstFpsDigit.getX() + digitWidth, firstFpsDigit.getY(), digitWidth, digitHeight, scale);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		if(visible){
			super.draw();

			firstFpsDigit.draw();
			secondFpsDigit.draw();

			if(TimeUtils.nanoTime() - startTime > ONE_SECOND_IN_NANOSECOND){
				firstFpsDigit.setNumber(Gdx.graphics.getFramesPerSecond() / 10);
				secondFpsDigit.setNumber(Gdx.graphics.getFramesPerSecond() % 10);
				
				startTime = TimeUtils.nanoTime();
			}
		}
	}
	
	// --------------------------------------------------
	// ~ Setters / Getters
	// --------------------------------------------------
	
	public boolean isVisible(){
		return visible;
	}
	
	public void setVisible(final boolean visible){
		this.visible = visible;
	}
	
}
