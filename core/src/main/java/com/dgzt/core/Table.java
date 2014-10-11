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
import com.dgzt.core.shape.RectangleShape;

/**
 * The table object.
 * 
 * @author Dgzt
 */
final public class Table extends RectangleShape{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width in cm. */
	public static final float WIDTH = 184.0f;
	
	/** The height in cm. */
	public static final float HEIGHT = 120.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The map. */
	private final Map map;
	
	/** The left gate. */
	private final Gate leftGate;
	
	/** The right gate. */
	private final Gate rightGate;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public Table( final ShapeRenderer shapeRenderer ){
		super(shapeRenderer, Color.GRAY);
		
		map = new Map(shapeRenderer);
		
		leftGate = new Gate(shapeRenderer);
		
		rightGate = new Gate(shapeRenderer);
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
		
		resizeMap(x, y, width, height, scale);
		
		resizeLeftGate(y, height, map.getX(), scale);
		
		resizeRightGate(map.getX(), map.getWidth(), leftGate.getY(), leftGate.getWidth(), leftGate.getHeight(), scale);
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
		
		leftGate.draw();
		rightGate.draw();
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Resize the map.
	 * 
	 * @param tableX - The x coordinate value of map.
	 * @param tableY - The y coordinate value of map.
	 * @param tableWidth - The width value of map.
	 * @param tableHeight - The height value of map.
	 * @param scale - The scale value.
	 */
	private void resizeMap(final float tableX, final float tableY, final float tableWidth, final float tableHeight, final double scale){
		final float width = (float) ((double)Map.WIDTH * scale);
		final float height = (float) ((double)Map.HEIGHT * scale);
		final float x = tableX + (tableWidth - width)/2;
		final float y = tableY + (tableHeight - height)/2;
		
		map.resize( x, y, width, height, scale );
	}
	
	/**
	 * Resize the left gate.
	 * 
	 * @param tableY - The y coordinate value of table.
	 * @param tableHeight - The height value of table.
	 * @param mapX - The x coordinate value of map.
	 * @param scale - The scale value.
	 */
	private void resizeLeftGate(final float tableY, final float tableHeight, final float mapX, final double scale){
		final float width = (float)(Gate.WIDTH * scale);
		final float height = (float)(Gate.HEIGHT * scale);
		final float x = mapX - width;
		final float y = tableY + (tableHeight - height) / 2;
		
		leftGate.resize(x, y, width, height, scale);
	}
	
	/**
	 * Resize the right gate.
	 * 
	 * @param mapX - The x coordiante value of map.
	 * @param mapWidth - The width value of map.
	 * @param y - The y coordinate value of right gate.
	 * @param width - The width value of right gate.
	 * @param height - The height value of right gate.
	 * @param scale - The scale value.
	 */
	private void resizeRightGate(final float mapX, final float mapWidth, final float y, final float width, final float height, final double scale){
		final float x = mapX + mapWidth;
		rightGate.resize(x, y, width, height, scale);
	}
}
