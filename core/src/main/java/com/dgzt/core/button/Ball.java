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
package com.dgzt.core.button;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.EventListener;
import com.dgzt.core.Table;
import com.dgzt.core.util.BitsUtil;

/**
 * The ball.
 * 
 * @author Dgzt
 */
public class Ball extends AbstractButton{

	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The radius of ball. */
	public static final float RADIUS = 1.0f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The color of the ball. */
	private static final Color COLOR = Color.BLACK;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------

	/**
	 * The constructor.
	 * 
	 * @param parent - The parent.
	 * @param shader - The shader.
	 * @param eventListener - The event listener.
	 * @param box2DWorld - The box2D world.
	 */
	public Ball(final Table parent, 
				final ShaderProgram shader,
				final EventListener eventListener,
				final World box2DWorld) 
	{
		super(parent, shader, eventListener, box2DWorld, COLOR, RADIUS);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected short getCategoryBits() {
		return BitsUtil.BALL_BITS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected short getMaskBits() {
		return BitsUtil.BUTTON_BITS | BitsUtil.WALL_BITS | BitsUtil.GATE_SENSOR_BITS | BitsUtil.MAP_SENSOR_BITS;
	}
}
