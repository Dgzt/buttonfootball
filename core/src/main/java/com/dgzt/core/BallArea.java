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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.button.Ball;
import com.dgzt.core.shape.FilledCircleShape;

/**
 * The area witch the stepped button then step again.
 * 
 * @author Dgzt
 */
public class BallArea extends FilledCircleShape{
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The alpha value. */
	private static final float ALPHA = 0.75f;
	
	/** The radius. */
	private static final float RADIUS = 4.5f;
	
	/** Temporary color for constructor. */
	private static final Color TEMP_COLOR = Color.BLACK;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The ball. */
	private final Ball ball;
	
	/** The area visiblity. */
	private boolean visible;

	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param ball - The ball.
	 */
	public BallArea(final ShaderProgram shader, final Ball ball) {
		super(shader, TEMP_COLOR);
		
		this.ball = ball;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the area with new color.
	 * 
	 * @param color - The color.
	 * @param scale - The scale.
	 */
	public void resize(final Color color, final double scale){
		setColor(new Color(color.r, color.g, color.b, ALPHA));
		
		resize(scale);
	}
	
	/**
	 * Resize the area.
	 * 
	 * @param scale - The scale.
	 */
	public void resize(final double scale){
		final float radius = (float)(RADIUS * scale);
		
		super.resize(ball.getX(), ball.getY(), radius);
	}
	
	// --------------------------------------------------
	// ~ Getter / setter methods
	// --------------------------------------------------

	/**
	 * Getter for visible.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Setter for visible.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
