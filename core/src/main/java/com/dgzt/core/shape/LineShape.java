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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * Line shape.
 * 
 * @author Dgzt
 */
final public class LineShape implements Shape{
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shape renderer. */
	private final ShapeRenderer shapeRenderer;
	
	/** The first x coordinate value. */
	private float x1;
	
	/** The first y coordinate value. */
	private float y1;
	
	/** The second x coordinate value. */
	private float x2;
	
	/** The second y coordinate value. */
	private float y2;
	
	/** The width of the line. */
	private float lineWidth;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public LineShape(final ShapeRenderer shapeRenderer){
		this.shapeRenderer = shapeRenderer;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the shape.
	 * 
	 * @param x1 - The first x coordinate value.
	 * @param y1 - The first y coordinate value.
	 * @param x2 - The second x coordinate value.
	 * @param y2 - The second y coordinate value.
	 * @param scale - The scale value.
	 */
	public void resize(final float x1, final float y1, final float x2, final float y2, final double scale){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		lineWidth = (float) (LINE_WIDTH * scale);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rectLine(x1, y1, x2, y2, lineWidth);
		shapeRenderer.end();
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------

	/**
	 * Return with first x coordinate value.
	 */
	public final float getX1(){
		return x1;
	}
	
	/**
	 * Return with first y coordinate value.
	 */
	public final float getY1(){
		return y1;
	}
	
	/**
	 * Return with second x coordinate value.
	 */
	public final float getX2(){
		return x2;
	}
	
	/**
	 * Return with second y coordinate value.
	 */
	public final float getY2(){
		return y2;
	}
}
