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

/**
 * Digit number.
 * 
 * @author Dgzt
 */
final public class Digit{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width value of time digit in cm. */
	public static final float TIME_DIGIT_WIDTH = 13.0f;
	
	/** The width value of goal digit in cm. */
	public static final float GOAL_DIGIT_WIDTH = TIME_DIGIT_WIDTH * 0.5f;
	
	/** The height value of time digit in cm. */
	public static final float TIME_DIGIT_HEIGHT = 25.0f;
	
	/** The height value of goal digit in cm. */
	public static final float GOAL_DIGIT_HEIGHT = TIME_DIGIT_HEIGHT * 0.5f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The horizontal line distance in cm. */
	private static final float HORIZONTAL_LINE_DISTANCE = 1.0f;
	
	/** The vertical line distance in cm. */
	private static final float VERTICAL_LINE_DISTANCE = 0.5f;
	
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
	
	/** The x coordinate value. */
	private float x;
	
	/** The y coordinate value. */
	private float y;
	
	/** The base horizontal line length. */
	private final float baseHorizontalLineLength;
	
	/** The base vertical line length. */
	private final float baseVerticalLineLength;
	
	/** The displaying number. */
	private int number;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param baseWidth - The base width value.
	 * @param baseHeight - The base height value.
	 * @param color - The color.
	 */
	public Digit(final ShaderProgram shader, final float baseWidth, final float baseHeight, final Color color){
		setNumber(0);;
		this.baseHorizontalLineLength = baseWidth - 2 * HORIZONTAL_LINE_DISTANCE;
		this.baseVerticalLineLength = (baseHeight - 3 * LineShape.LINE_WIDTH - 6 * VERTICAL_LINE_DISTANCE) / 2;
		
		topLine = new LineShape(shader, color);
		topRightLine = new LineShape(shader, color);
		centerLine = new LineShape(shader, color);
		bottomRightLine = new LineShape(shader, color);
		bottomLine = new LineShape(shader, color);
		bottomLeftLine = new LineShape(shader, color);
		topLeftLine = new LineShape(shader, color);
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
		this.x = x;
		this.y = y;
		final float horizontalLineDistance = (float)(HORIZONTAL_LINE_DISTANCE * scale);
		final float verticalLineDistance = (float)(VERTICAL_LINE_DISTANCE * scale);
		final float horizontalLineLength = (float)(baseHorizontalLineLength * scale);
		final float verticalLineLength = (float)(baseVerticalLineLength * scale);
		
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
		final float halfLineWidth = (float)(LineShape.LINE_WIDTH * scale) / 2;
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
		final float halfLineWidth = (float)(LineShape.LINE_WIDTH * scale) / 2;
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
		final float halfLineWidth = (float)(LineShape.LINE_WIDTH * scale) / 2;
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
	 * Draw the digit.
	 */
	public void draw() {
		if(number != 1 && number != 4){
			topLine.draw();
		}
		if(number != 5 && number != 6){
			topRightLine.draw();
		}
		if(number != 0 && number != 1 && number != 7){
			centerLine.draw();
		}
		if(number != 2){
			bottomRightLine.draw();
		}
		if(number != 1 && number != 4 && number != 7){
			bottomLine.draw();
		}
		if(number != 1 && number != 3 && number != 4 && number != 5 && number != 7 && number != 9){
			bottomLeftLine.draw();
		}
		if(number != 1 && number != 2 && number != 3 && number != 7){
			topLeftLine.draw();
		}
	}
	
	public void dispose(){
		topLine.dispose();
		topRightLine.dispose();
		bottomRightLine.dispose();
		bottomLine.dispose();
		bottomLeftLine.dispose();
		centerLine.dispose();
		topLeftLine.dispose();
	}
	
	// --------------------------------------------------
	// ~ Getter / Setter methods
	// --------------------------------------------------
	
	/**
	 * Return with the x coordinate value.
	 */
	public final float getX(){
		return x;
	}
	
	/**
	 * Return with the y coordinate value.
	 */
	public final float getY(){
		return y;
	}
	
	/**
	 * Set the number of digit. 
	 * If the given number is lesser then 0, then the number will be equal with 0.
	 * if the given number is bigger then 9, then the number will be equal with 9.
	 * 
	 * @param number - The number.
	 */
	public void setNumber(int number){
		if(number < 0){
			number = 0;
		}else if(9 < number){
			number = 9;
		}
		
		this.number = number;
	}
	
	/**
	 * Get the number of digit.
	 */
	public int getNumber(){
		return number;
	}
	
}
