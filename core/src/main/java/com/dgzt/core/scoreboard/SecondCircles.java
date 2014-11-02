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
import com.dgzt.core.shape.FilledCircleShape;
import com.dgzt.core.shape.Shape;

/**
 * The second circles container class.
 * 
 * @author Dgzt
 */
public class SecondCircles implements Shape{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width value in cm. */
	public static final float WIDTH = 10.0f;
	
	/** The height value in cm. */
	public static final float HEIGHT = TimeBoard.HEIGHT;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The radius of circles value in cm. */
	private static final float CIRCLE_RADIUS = 1.5f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------

	/** The top circle. */
	private final FilledCircleShape topCircle;
	
	/** The bottom circle. */
	private final FilledCircleShape bottomCircle;
	
	/** The x coordinate value. */
	private float x;
	
	/** The width value. */
	private float width;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public SecondCircles(final ShapeRenderer shapeRenderer){
		topCircle = new FilledCircleShape(shapeRenderer, Color.WHITE);
		bottomCircle = new FilledCircleShape(shapeRenderer, Color.WHITE);
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
		this.width = width;
		final float circleRadius = (float)(CIRCLE_RADIUS * scale);
		
		resizeTopCircle(circleRadius, x, y, width, height);
		resizeBottomCircle(topCircle.getX(), circleRadius, y, height);
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Resize the top circle.
	 * 
	 * @param radius - The radius value.
	 * @param parentX - The x coordinate value of parent shape.
	 * @param parentY - The y coordinate value of parent shape.
	 * @param parentWidth - The width value of parent.
	 * @param parentHeight - The height value of parent.
	 */
	private void resizeTopCircle(final float radius, final float parentX, final float parentY, final float parentWidth, final float parentHeight){
		final float x = parentX + parentWidth / 2;
		final float y = parentY + parentHeight / 3;
		
		topCircle.resize(x, y, radius);
	}
	
	/**
	 * Resize the bottom circle.
	 * 
	 * @param x - The x coordinate value.
	 * @param radius - The radius value.
	 * @param parentY - The y coordinate value of parent.
	 * @param parentHeight - The height value of parent.
	 */
	private void resizeBottomCircle(final float x, final float radius, final float parentY, final float parentHeight){
		final float y = parentY + parentHeight / 3 * 2;
		
		bottomCircle.resize(x, y, radius);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		topCircle.draw();
		bottomCircle.draw();
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
	 * Return with the width value.
	 */
	public final float getWidth(){
		return width;
	}

}
