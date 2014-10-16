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

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dgzt.core.shape.RectangleBorderShape;

/**
 * The scoreboard.
 * 
 * @author Dgzt
 */
public class ScoreBoard extends RectangleBorderShape{

	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width of scoreboard in cm. */
	public static final float WIDTH = 60.0f;
	
	/** The height of scoreboard in cm. */
	public static final float HEIGHT = 20.0f;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public ScoreBoard(final ShapeRenderer shapeRenderer) {
		super(shapeRenderer);
	}

}
