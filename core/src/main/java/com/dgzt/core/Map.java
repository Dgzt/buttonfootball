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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * The map object.
 * 
 * @author Dgzt
 */
final public class Map extends Shape{

	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width value in cm. */
	public static final float WIDTH = 167.0f;
	
	/** The height value in cm. */
	public static final float HEIGHT = 104.0f;
	
	/** The border of map. */
	private final Shape border;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public Map(final ShapeRenderer shapeRenderer){
		super(shapeRenderer, ShapeType.Filled, Color.GREEN);
		
		border = new Shape(shapeRenderer, ShapeType.Line, Color.WHITE);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * Resize the map and the child objects.
	 */
	@Override
	public void resize(float x, float y, float width, float height) {
		super.resize(x, y, width, height);
		
		border.resize(x, y, width, height);
	}

	/**
	 * Draw the map and the child objects.
	 */
	@Override
	public void draw() {
		super.draw();
		
		border.draw();
	}
	
}
