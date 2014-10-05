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
 * Arc shape.
 * 
 * @author Dgzt
 */
final public class ArcShape implements Shape{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The radius of the small arcs. */
	public static final float SMALL_RADIUS = 4.0f;

	/** The radius of the small arcs. */
	public static final float BIG_RADIUS = 30.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shape renderer. */
	private final ShapeRenderer shapeRenderer;
	
	/** The color of background. */
	private final Color backgroundColor;
	
	/** The color of arc. */
	private final Color arcColor;
	
	/** The start degrees. */
	private final float start;
	
	/** The number of degrees. */
	private final float degrees;
	
	/** The x coordinate value. */
	private float x;
	
	/** The y coordinate value. */
	private float y;
	
	/** The radius of arc. */
	private float radius;
	
	/** The radius of background arc. */
	private float smallRadius;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 * @param backgroundColor - The color of background.
	 * @param arcColor - The color of arc.
	 * @param start - The start degrees.
	 * @param degrees - The number of degrees.
	 */
	public ArcShape(final ShapeRenderer shapeRenderer, final Color backgroundColor, final Color arcColor, final float start, final float degrees){
		this.shapeRenderer = shapeRenderer;
		this.backgroundColor = backgroundColor;
		this.arcColor = arcColor;
		this.start = start;
		this.degrees = degrees;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the arc.
	 * 
	 * @param x - The new x coordinate value.
	 * @param y - The new y coordinate value.
	 * @param radius - The new radius value.
	 * @param scale - The scale.
	 */
	public void resize(final float x, final float y, final float radius, final double scale){
		this.x = x;
		this.y = y;
		this.radius = radius;
		
		final float lineWidth = (float) (LINE_WIDTH * scale);
		
		this.smallRadius = radius - lineWidth;
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
		shapeRenderer.setColor(arcColor);
		shapeRenderer.arc(x, y, radius, start, degrees);
		shapeRenderer.setColor(backgroundColor);
		shapeRenderer.arc(x, y, smallRadius, start, degrees);
		shapeRenderer.end();
	}

}
