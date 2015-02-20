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
	private static final float RADIUS = 3.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The area visiblity. */
	private boolean visible;

	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	public BallArea(final ShaderProgram shader, final Color color) {
		super(shader, new Color(color.r, color.g, color.b, ALPHA));
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the area.
	 * 
	 * @param ball - The ball.
	 * @param scale - The scale.
	 */
	public void resize(final Ball ball, final double scale){
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
