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

/**
 * Border of the circle.
 * 
 * @author Dgzt
 */
final public class CircleBorderShape implements Shape{
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The inner filled circle. */
	private final CircleShape innerCircle;
	
	/** The outer filled circle */
	private final CircleShape outerCircle;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 * @param innerColor - The color of the inner circle.
	 * @param outerColor - The color of the outer circle.
	 */
	public CircleBorderShape(final ShapeRenderer shapeRenderer, final Color innerColor, final Color outerColor){
		innerCircle = new CircleShape(shapeRenderer, innerColor);
		outerCircle = new CircleShape(shapeRenderer, outerColor);
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
	 * @param scale - The scale value.
	 */
	public void resize(final float x, final float y, final float radius, final double scale){
		outerCircle.resize(x, y, radius);
		
		final float lineWidth = (float) (LINE_WIDTH * scale);
		
		innerCircle.resize(x, y, radius - lineWidth);
		
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		outerCircle.draw();
		innerCircle.draw();
	}

}
