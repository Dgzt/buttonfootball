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
	private static final int WIDTH = 184;
	
	/** The height in cm. */
	private static final int HEIGHT = 120;
	
	/** The color. */
	private static final Color COLOR = Color.GRAY;
	
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
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize and scaling the table to the center.
	 * 
	 * @param width - The width of the window.
	 * @param height - The height of the window.
	 */
	public void resize(final float width, final float height){
		float tableWidth;
		float tableHeight;
		
		double rate = (double)width/height;
		
		if( (float)WIDTH/HEIGHT > rate ){
			tableWidth = width;
			tableHeight = HEIGHT*(width/WIDTH);
		}else{
			tableWidth = WIDTH*(height/HEIGHT);
			tableHeight = height;
		}
		
		final float tableX = (width-tableWidth)/2;
		final float tableY = (height-tableHeight)/2;
		
		super.resize(tableX, tableY, tableWidth, tableHeight);
	}

}
