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
import com.dgzt.core.shape.RectangleShape;

/**
 * The time left board.
 * 
 * @author Dgzt
 */
public class TimeLeftBoard extends RectangleBorderShape{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width value. */
	public static final float WIDTH = GoalBoard.HEIGHT;
	
	/** The height value. */
	public static final float HEIGHT = 10.0f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The time left line distance from border. */
	private static final float DISTANCE_FROM_BORDER = 1.0f;
	
	/** The width of time left line. */
	private static final float TIME_LEFT_LINE_WIDTH = WIDTH - 2 * LineShape.LINE_WIDTH - 2 * DISTANCE_FROM_BORDER;
	
	/** The height of time left line. */
	private static final float TIME_LEFT_LINE_HEIGHT = HEIGHT - 2 * LineShape.LINE_WIDTH - 2 * DISTANCE_FROM_BORDER;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private final RectangleShape timeLeftLine;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------

	/**
	 * Constructor.
	 * 
	 * @param shader - The shader.
	 * @param borderColor - The color of border.
	 * @param lineColor - The color of line.
	 */
	public TimeLeftBoard(final ShaderProgram shader, final Color borderColor, final Color lineColor) {
		super(shader, borderColor);
		
		timeLeftLine = new RectangleShape(shader, lineColor);
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
		
		final float timeLeftWidth = (float)(TIME_LEFT_LINE_WIDTH * scale);
		final float timeLeftHeight = (float)(TIME_LEFT_LINE_HEIGHT * scale);
		final float timeLeftX = x + (width - timeLeftWidth) / 2;
		final float timeLeftY = y + (height - timeLeftHeight) / 2;
		
		timeLeftLine.resize(timeLeftX, timeLeftY, timeLeftWidth, timeLeftHeight);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		super.draw();
		
		timeLeftLine.draw();
	}
}
