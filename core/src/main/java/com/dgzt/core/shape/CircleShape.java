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
 * Circle shape.
 * 
 * @author Dgzt
 */
public class CircleShape implements Shape{
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------

	/** The shape renderer. */
	private final ShapeRenderer shapeRenderer;
	
	/** The color of the circle */
	private final Color color;
	
	/** The x coordinate value. */
	private float x;
	
	/** The y coordinate value. */
	private float y;
	
	/** The radius value. */
	private float radius;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 * @param color - The color of the circle.
	 */
	public CircleShape(final ShapeRenderer shapeRenderer, final Color color){
		this.shapeRenderer = shapeRenderer;
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
	 * @param radius - The radius value.
	 */
	public void resize(final float x, final float y, final float radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
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
		shapeRenderer.setColor(color);
		shapeRenderer.circle(x, y, radius);
		shapeRenderer.end();
		
	}
	
	// --------------------------------------------------
	// ~ Getter methods
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
	 * Return with the radius value.
	 */
	public final float getRadius(){
		return radius;
	}
}
