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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.Shape;

/**
 * Digit number.
 * 
 * @author Dgzt
 */
public class Digit implements Shape{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width value in cm. */
	public static final float WIDTH = 13.0f;
	
	/** The height value in cm. */
	public static final float HEIGHT = 25.0f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The horizontal line distance in cm. */
	private static final float HORIZONTAL_LINE_DISTANCE = 1.0f;
	
	/** The vertical line distance in cm. */
	private static final float VERTICAL_LINE_DISTANCE = 0.5f;
	
	/** The horizontal line length in cm. */
	private static final float HORIZONTAL_LINE_LENGTH = WIDTH - 2 * HORIZONTAL_LINE_DISTANCE;
	
	/** The vertical line length in cm. */
	private static final float VERTICAL_LINE_LENGTH = (HEIGHT - 3 * Shape.LINE_WIDTH - 6 * VERTICAL_LINE_DISTANCE) / 2;
	
	/** The color of lines. */
	private static final Color DIGIT_COLOR = Color.WHITE;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------

	/** The top horizontal line. */
	private final LineShape topLine;
	
	/** The top reight vertical line. */
	private final LineShape topRightLine;
	
	/** The center horizontal line. */
	private final LineShape centerLine;
	
	/** The bottom right vertical line. */
	private final LineShape bottomRightLine;
	
	/** The bottom horizontal line. */
	private final LineShape bottomLine;
	
	/** The bottom left vertical line. */
	private final LineShape bottomLeftLine;
	
	/** The top left vertical line. */
	private final LineShape topLeftLine;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public Digit(final ShapeRenderer shapeRenderer){
		topLine = new LineShape(shapeRenderer, DIGIT_COLOR);
		topRightLine = new LineShape(shapeRenderer, DIGIT_COLOR);
		centerLine = new LineShape(shapeRenderer, DIGIT_COLOR);
		bottomRightLine = new LineShape(shapeRenderer, DIGIT_COLOR);
		bottomLine = new LineShape(shapeRenderer, DIGIT_COLOR);
		bottomLeftLine = new LineShape(shapeRenderer, DIGIT_COLOR);
		topLeftLine = new LineShape(shapeRenderer, DIGIT_COLOR);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the shape.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param scale - The scale value.
	 */
	public void resize(final float x, final float y, final float width, final float height, final double scale){
		final float horizontalLineDistance = (float)(HORIZONTAL_LINE_DISTANCE * scale);
		final float verticalLineDistance = (float)(VERTICAL_LINE_DISTANCE * scale);
		final float horizontalLineLength = (float)(HORIZONTAL_LINE_LENGTH * scale);
		final float verticalLineLength = (float)(VERTICAL_LINE_LENGTH * scale);
		
		resizeTopLine(horizontalLineLength, x, y, width, horizontalLineDistance, scale);
		resizeCenterLine(topLine.getX1(), topLine.getX2(), y, height, scale);
		resizeBottomLine(topLine.getX1(), topLine.getX2(), y, height, horizontalLineDistance, scale);
		
		resizeTopLeftLine(verticalLineLength, topLine.getX1(), topLine.getY1(), verticalLineDistance, scale);
		resizeTopRightLine(topLeftLine.getY1(), topLeftLine.getY2(), topLine.getX2(), scale);
		
		resizeBottomLeftLine(topLeftLine.getX1(), verticalLineLength, centerLine.getY1(), verticalLineDistance, scale);
		resizeBottomRightLine(topRightLine.getX1(), bottomLeftLine.getY1(), bottomLeftLine.getY2(), scale);
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Resize the top horizontal line.
	 * 
	 * @param width - The width value.
	 * @param digitX - The x coordinate value of digit.
	 * @param digitY - The y coordinate value of digit.
	 * @param digitWidth - The width value of digit.
	 * @param horizontalLineDistance - The distance of line.
	 * @param scale - The scale value.
	 */
	private void resizeTopLine(final float width, final float digitX, final float digitY, final float digitWidth,final float horizontalLineDistance, final double scale){
		final float x1 = digitX + (digitWidth - width) /2;
		final float y = digitY + horizontalLineDistance;
		final float x2 = x1 + width;
		
		topLine.resize(x1, y, x2, y, scale);
	}
	
	/**
	 * Resize the center horizontal line.
	 * 
	 * @param x1 - The x1 coordinate value.
	 * @param x2 - The x2 coordinate value.
	 * @param digitY - The y coordinate value of digit.
	 * @param digitHeight - The height value of digit.
	 * @param scale - The scale value.
	 */
	private void resizeCenterLine(final float x1, final float x2, final float digitY, final float digitHeight, final double scale){
		final float y = digitY + digitHeight / 2;
		
		centerLine.resize(x1, y, x2, y, scale);
	}
	
	/**
	 * Resize the bottom horizontal line.
	 * 
	 * @param x1 - The x1 coordiante value.
	 * @param x2 - The x2 coordinate value.
	 * @param digitY - The y coordinate value of digit.
	 * @param digitHeight - The height value of digit.
	 * @param horizontalLineDistance - The distance of line.
	 * @param scale - The scale value.
	 */
	private void resizeBottomLine(final float x1, final float x2, final float digitY, final float digitHeight, final float horizontalLineDistance, final double scale){
		final float y = digitY + digitHeight - horizontalLineDistance;
		
		bottomLine.resize(x1, y, x2, y, scale);
	}
	
	/**
	 * Resize the top left vertical line.
	 * 
	 * @param height - The height value of line.
	 * @param topLineX - The x coordinate value of top horizontal line.
	 * @param topLineY - The y coordinate value of top horizontal line.
	 * @param verticalLineDistance - The vertical distance of line.
	 * @param scale - The scale value.
	 */
	private void resizeTopLeftLine(final float height, final float topLineX, final float topLineY, final float verticalLineDistance, final double scale ){
		final float halfLineWidth = (float)(Shape.LINE_WIDTH * scale) / 2;
		final float x = topLineX + halfLineWidth;
		final float y1 = topLineY + halfLineWidth + verticalLineDistance;
		final float y2 = y1 + height;
		
		topLeftLine.resize(x, y1, x, y2, scale);
	}
	
	/**
	 * Resize the top right vertical line.
	 * 
	 * @param y1 - The y1 coordinate value.
	 * @param y2 - The y2 coordinate value.
	 * @param topLineX2 - The x2 coordinate value of top horizontal line.
	 * @param scale - The scale value.
	 */
	private void resizeTopRightLine(final float y1, final float y2, final float topLineX2, final double scale){
		final float halfLineWidth = (float)(Shape.LINE_WIDTH * scale) / 2;
		final float x = topLineX2 - halfLineWidth;
		
		topRightLine.resize(x, y1, x, y2, scale);
	}
	
	/**
	 * Resize the bottom left vertical line.
	 * 
	 * @param x - The x coordinate value.
	 * @param height - The height value.
	 * @param centerLineY - The y coordinate value of center horizontal line.
	 * @param verticalLineDistance - The vertical distance of line.
	 * @param scale - The scale value.
	 */
	private void resizeBottomLeftLine(final float x, final float height, final float centerLineY, final float verticalLineDistance, final double scale){
		final float halfLineWidth = (float)(Shape.LINE_WIDTH * scale) / 2;
		final float y1 = centerLineY + halfLineWidth + verticalLineDistance;
		final float y2 = y1 + height;
		
		bottomLeftLine.resize(x, y1, x, y2, scale);
	}
	
	/**
	 * Resize the bottom right vertical line.
	 * 
	 * @param x - The x coordinate value.
	 * @param y1 - The y1 coordinate value.
	 * @param y2 - The y2 coordinate value.
	 * @param scale - The scale value.
	 */
	private void resizeBottomRightLine(final float x, final float y1, final float y2, final double scale){
		bottomRightLine.resize(x, y1, x, y2, scale);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		topLine.draw();
		topRightLine.draw();
		centerLine.draw();
		bottomRightLine.draw();
		bottomLine.draw();
		bottomLeftLine.draw();
		topLeftLine.draw();
	}
	
}
