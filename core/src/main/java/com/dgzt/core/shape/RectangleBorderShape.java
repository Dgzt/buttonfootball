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

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Border of the rectangle.
 * 
 * @author Dgzt
 */
public class RectangleBorderShape implements Shape{

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
	 * @param shapeRenderer - The shape renderer.
	 */
	public RectangleBorderShape(final ShapeRenderer shapeRenderer){
		topLine = new LineShape(shapeRenderer);
		rightLine = new LineShape(shapeRenderer);
		bottomLine = new LineShape(shapeRenderer);
		leftLine = new LineShape(shapeRenderer);
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
		final float halfLineWidth = (float) (LINE_WIDTH * scale) / 2;
		
		topLine.resize(x - halfLineWidth, y, x + width + halfLineWidth, y, scale);
		rightLine.resize(x + width, y - halfLineWidth, x + width, y + height + halfLineWidth, scale);
		bottomLine.resize(x - halfLineWidth, y + height, x + width + halfLineWidth, y + height, scale);
		leftLine.resize(x, y - halfLineWidth, x, y + height + halfLineWidth, scale);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	 * Return with y coordinate value;
	 */
	public final float getY(){
		return topLine.getY1();
	}
	
	/**
	 * Return with width value.
	 */
	public final float getWidth(){
		final float halfLineWidth = (float) (LINE_WIDTH * scale) / 2;
		final float leftX = topLine.getX1() + halfLineWidth;
		final float rightX = topLine.getX2() - halfLineWidth;
		
		return rightX - leftX;
	}
	
	/**
	 * Return width height value.
	 */
	public final float getHeight(){
		final float halfLineWidth = (float) (LINE_WIDTH * scale) / 2;
		final float topY = rightLine.getY1() + halfLineWidth;
		final float bottomY = rightLine.getY2() - halfLineWidth;

		return bottomY - topY;
	}
}
