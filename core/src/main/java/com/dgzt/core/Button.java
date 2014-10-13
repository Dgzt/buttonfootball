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
import com.dgzt.core.shape.CircleShape;

/**
 * The button object.
 * 
 * @author Dgzt
 */
final public class Button extends CircleShape{

	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The color of the player's buttons. */
	public static final Color PLAYER_COLOR = Color.RED;
	
	/** The color of the opponent's buttons. */
	public static final Color OPPONENT_COLOR = Color.BLUE;
	
	/** The color of the ball. */
	public static final Color BALL_COLOR = Color.BLACK;
	
	/** The radius of the players's and opponent's buttons in cm. */
	public static final float PLAYER_OPPONENT_RADIUS = 2.5f;
	
	/** The radius of ball. */
	public static final float BALL_RADIUS = 1.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The x coordinate value in Box2D. */
	private float box2DX;
	
	/** The y coordinate value in Box2D. */
	private float box2DY;
	
	/** The radius value in Box2D. */
	private final float box2DRadius;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 * @param color - The color of button.
	 * @param box2DX - The x coordinate value of button in Box2D.
	 * @param box2DY - The y coordinate value of button in Box2D.
	 * @param box2DRadius - The radius value of button in Box2D.
	 */
	public Button(final ShapeRenderer shapeRenderer, final Color color, final float box2DX, final float box2DY, final float box2DRadius) {
		super(shapeRenderer, color);
		this.box2DX = box2DX;
		this.box2DY = box2DY;
		this.box2DRadius = box2DRadius;
	}
	
	// --------------------------------------------------
	// ~ Public members
	// --------------------------------------------------
	
	/**
	 * Contains the button the given coordinate.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public boolean contains(final float x, final float y){
		return (Math.abs(getX() - x) < getRadius() && Math.abs(getY() - y) < getRadius());
	}
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/**
	 * Resize the object.
	 * 
	 * @param parentX - The x coordinate value of the parent object.
	 * @param parentY - The y coordinate value of the parent object.
	 * @param scale - The scale value
	 */
	public void resize(final float parentX, final float parentY, final double scale){
		final float x = parentX + (float)(box2DX * scale);
		final float y = parentY + (float)(box2DY * scale);
		final float radius = (float)(box2DRadius * scale);
		
		super.resize(x, y, radius);
	}

}
