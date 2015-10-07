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
 * The player and opponent button.
 * 
 * @author Dgzt
 */
public class Button extends AbstractButton{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The color of the player's buttons. */
	public static final Color PLAYER_COLOR = Color.RED;
	
	/** The color of the opponent's buttons. */
	public static final Color OPPONENT_COLOR = Color.BLUE;
	
	/** The radius of the players's and opponent's buttons in cm. */
	public static final float RADIUS = 2.5f;
	
	/** The diameter in Box2D. */
	public static final float DIAMETER = 2 * RADIUS;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------

	/**
	 * The constructor.
	 * 
	 * @param parent - The parent.
	 * @param shader - The shader.
	 * @param eventListener - The event listener.
	 * @param box2dWorld - The Box2D world.
	 * @param color - The color.
	 */
	public Button(final Table parent, 
				final ShaderProgram shader,
				final EventListener eventListener,
				final World box2dWorld,
				final Color color) 
	{
		super(parent, shader, eventListener, box2dWorld, color, RADIUS);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected short getCategoryBits() {
		return BitsUtil.BUTTON_BITS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected short getMaskBits() {
		return BitsUtil.BUTTON_BITS | BitsUtil.BALL_BITS | BitsUtil.WALL_BITS;
	}

}
