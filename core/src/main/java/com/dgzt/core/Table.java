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

/**
 * The table object.
 * 
 * @author Dgzt
 */
public class Table extends Shape{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width in cm. */
	public static final float WIDTH = 184.0f;
	
	/** The height in cm. */
	public static final float HEIGHT = 120.0f;
	
	/** The color. */
	private static final Color COLOR = Color.GRAY;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private final Map map;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public Table( final ShapeRenderer shapeRenderer ){
		super(shapeRenderer, COLOR);
		
		map = new Map(shapeRenderer);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------

	/**
	 * Resize the table and the child objects.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param scale - The scale value.
	 */
	public void resize(final float x, final float y, final float width, final float height, final double scale) {
		super.resize(x, y, width, height);
		
		// Map
		final float mapWidth = (float) ((double)Map.WIDTH * scale);
		final float mapHeight = (float) ((double)Map.HEIGHT * scale);
		final float mapX = x + (width - mapWidth)/2;
		final float mapY = y + (height - mapHeight)/2;
		
		map.resize( mapX, mapY, mapWidth, mapHeight );
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * Draw the table and the child objects.
	 */
	@Override
	public void draw() {
		super.draw();
		
		map.draw();
	}
	
}
