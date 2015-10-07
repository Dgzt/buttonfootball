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
package com.dgzt.core.gate;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.util.Box2DDataUtil;

/**
 * The right gate.
 * 
 * @author Dgzt
 */
public class RightGate extends AbstractGate{
	
	// --------------------------------------------------
	// ~ Constructors.
	// --------------------------------------------------

	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param box2DWorld - The world of the Box2D.
	 */
	public RightGate(final ShaderProgram shader, final World box2DWorld) {
		super(shader, box2DWorld, Box2DDataUtil.RIGHT_GATE_POSITION);
	}

	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected short getWallBits() {
		return TOP_WALL_BITS | RIGHT_WALL_BITS | BOTTOM_WALL_BITS;
	}

}
