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
import com.dgzt.core.Table;

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
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------

	/**
	 * The constructor.
	 * 
	 * @param parent - The parent.
	 * @param shader - The shader.
	 * @param box2dWorld - The Box2D world.
	 * @param color - The color.
	 * @param box2dx - The x coordinate value in the Box2D.
	 * @param box2dy - The y coordinate value in the Box2D.
	 */
	public Button(final Table parent, 
				final ShaderProgram shader, 
				final World box2dWorld,
				final Color color, 
				final float box2dx, 
				final float box2dy) 
	{
		super(parent, shader, box2dWorld, color, box2dx, box2dy, RADIUS);
	}

}