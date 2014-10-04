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
import com.dgzt.core.shape.RectangleBorderShape;
import com.dgzt.core.shape.RectangleShape;

/**
 * The map object.
 * 
 * @author Dgzt
 */
final public class Map extends RectangleShape{

	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width value in cm. */
	public static final float WIDTH = 167.0f;
	
	/** The height value in cm. */
	public static final float HEIGHT = 104.0f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The width value of the sector 16 in cm. */
	private static final float SECTOR_16_WIDTH = 30.0f;
	
	/** The height value of the sector 16 in cm. */
	private static final float SECTOR_16_HEIGHT = 60.0f;
	
	/** The width value of the sector 5 in cm. */
	private static final float SECTOR_5_WIDTH = 11.0f;
	
	/** The height value of the sector 5 in cm. */
	private static final float SECTOR_5_HEIGHT = 30.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------	
	
	/** The border of the map. */
	private final RectangleBorderShape mapBorder;

	/** The left sector 16. */
	private final RectangleBorderShape leftSector16;
	
	/** The right sector 16. */
	private final RectangleBorderShape rightSector16;
	
	/** The left sector 5. */
	private final RectangleBorderShape leftSector5;
	
	/** The right sector 5. */
	private final RectangleBorderShape rightSector5;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public Map(final ShapeRenderer shapeRenderer){
		super(shapeRenderer, Color.GREEN);
		
		this.mapBorder = new RectangleBorderShape(shapeRenderer);
		
		this.leftSector16 = new RectangleBorderShape(shapeRenderer);
		this.rightSector16 = new RectangleBorderShape(shapeRenderer);
		
		this.leftSector5 = new RectangleBorderShape(shapeRenderer);
		this.rightSector5 = new RectangleBorderShape(shapeRenderer);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the map and the child objects.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param scale - The scale value.
	 */
	public void resize(final float x, final float y, final float width, final float height, final double scale ) {
		super.resize(x, y, width, height);
		
		mapBorder.resize(x, y, width, height, scale);
		
		// Left sector 16
		final float sector16Width = (float)((double)SECTOR_16_WIDTH * scale);
		final float sector16Height = (float)((double)SECTOR_16_HEIGHT * scale);
		final float sector16Y = y + (height - sector16Height) / 2;
		final float leftSector16X = x;
		
		leftSector16.resize(leftSector16X, sector16Y, sector16Width, sector16Height, scale);
		
		// Right sector 16
		final float rightSector16X = x + width - sector16Width;
		
		rightSector16.resize(rightSector16X, sector16Y, sector16Width, sector16Height, scale);
		
		// Left sector 5
		final float sector5Width = (float)((double)SECTOR_5_WIDTH * scale);
		final float sector5Height = (float)((double)SECTOR_5_HEIGHT * scale);
		final float sector5Y = y + (height - sector5Height) / 2;
		final float leftSector5X = x;
		
		leftSector5.resize(leftSector5X, sector5Y, sector5Width, sector5Height, scale);
		
		// Right sector 5
		final float rightSector5X = x + width - sector5Width;
		
		rightSector5.resize(rightSector5X, sector5Y, sector5Width, sector5Height, scale);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * Draw the map and the child objects.
	 */
	@Override
	public void draw() {
		super.draw();
		
		mapBorder.draw();		
		
		leftSector16.draw();
		rightSector16.draw();
		
		leftSector5.draw();
		rightSector5.draw();
	}
	
}
