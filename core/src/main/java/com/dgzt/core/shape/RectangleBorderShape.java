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
package com.dgzt.core.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Border of the rectangle.
 * 
 * @author Dgzt
 */
public class RectangleBorderShape{

	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The bottom line. */
	private final LineShape topLine;
	
	/** The right line. */
	private final LineShape rightLine;
	
	/** The bottom line. */
	private final LineShape bottomLine;
	
	/** The left line. */
	private final LineShape leftLine;
	
	/** The scale value. */
	private double scale;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	public RectangleBorderShape(final ShaderProgram shader, final Color color){
		topLine = new LineShape(shader, color);
		rightLine = new LineShape(shader, color);
		bottomLine = new LineShape(shader, color);
		leftLine = new LineShape(shader, color);
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
	 * @param scale The scale value.
	 */
	public void resize(final float x, final float y, final float width, final float height, final double scale){
		this.scale = scale;
		final float halfLineWidth = (float) (LineShape.LINE_WIDTH * scale) / 2;
		
		topLine.resize(x -halfLineWidth, y, x + width +halfLineWidth, y, scale);
		rightLine.resize(x + width, y -halfLineWidth, x + width, y + height+halfLineWidth, scale);
		bottomLine.resize(x-halfLineWidth, y + height, x + width+halfLineWidth, y + height, scale);
		leftLine.resize(x, y-halfLineWidth, x, y + height+halfLineWidth, scale);
	}
	
	/**
	 * Draw the rectangle border.
	 */
	public void draw(){
		topLine.draw();
		rightLine.draw();
		bottomLine.draw();
		leftLine.draw();
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------

	/**
	 * Return with x coordinate value;
	 */
	public final float getX(){
		return topLine.getX1();
	}
	
	/**
	 * Return with y coordinate value;
	 */
	public final float getY(){
		return topLine.getY1();
	}
	
	/**
	 * Return with width value.
	 */
	public final float getWidth(){
		final float lineWidth = (float) (LineShape.LINE_WIDTH * scale);
		
		return topLine.getLength() - lineWidth;
	}
	
	/**
	 * Return width height value.
	 */
	public final float getHeight(){
		final float lineWidth = (float) (LineShape.LINE_WIDTH * scale);
		
		return rightLine.getLength() - lineWidth;
	}
}
