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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * The abstract shape.
 * 
 * @author Dgzt
 */
public class Shape {
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shape renderer. */
	private final ShapeRenderer shapeRenderer;
	
	/** The type of shape. */
	private final ShapeType shapeType;
	
	/** The x coordinate value. */
	private float x;
	
	/** The y coordinate value. */
	private float y;
	
	/** The width value. */
	private float width;
	
	/** The height value. */
	private float height;
	
	/** The color. */
	private final Color color;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 * @param color - The color of the shape.
	 */
	public Shape( final ShapeRenderer shapeRenderer, final ShapeType shapeType, final Color color){
		this.shapeRenderer = shapeRenderer;
		this.shapeType = shapeType;
		this.color = color;
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
	 * @param height The height value.
	 */
	public void resize(final float x, final float y, final float width, final float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Draw the shape.
	 */
	public void draw(){
		shapeRenderer.begin(shapeType);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.end();
	}
}
