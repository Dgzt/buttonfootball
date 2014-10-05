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

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;

/**
 * Gate object.
 * 
 * @author Dgzt
 */
final public class Gate extends RectangleBorderShape{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width of the gate. */
	public static final float WIDTH = 8.0f;
	
	/** The height of the gate. */
	public static final float HEIGHT = 13.0f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------	
	
	/** Number of column line. */
	private static final int COLUMN_LINES = 4;
	
	/** Number of row line. */
	private static final int ROW_LINES = 3;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The column lines. */
	private final List<LineShape> columnLines;

	/** The row lines. */
	private final List<LineShape> rowLines;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public Gate(ShapeRenderer shapeRenderer) {
		super(shapeRenderer);

		columnLines = new ArrayList<LineShape>();
		for(int i=0; i < COLUMN_LINES; ++i){
			columnLines.add(new LineShape(shapeRenderer));
		}
		
		rowLines = new ArrayList<LineShape>();
		for(int i=0; i < ROW_LINES; ++i){
			rowLines.add(new LineShape(shapeRenderer));
		}
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * Resize the shape and the child shapes.
	 */
	@Override
	public void resize(final float x, final float y, final float width, final float height, final double scale) {
		super.resize(x, y, width, height, scale);
		
		final float columnLineDistance = width / (COLUMN_LINES + 1);
		final float rowLineDistance = height / (ROW_LINES + 1);
		
		for(int i=0; i < COLUMN_LINES; ++i){
			final float lineX = x + (i+1) * columnLineDistance;
			columnLines.get(i).resize(lineX, y, lineX, y + height, scale);
		}
		
		for(int i=0; i < ROW_LINES; ++i){
			final float lineY = y + (i+1) * rowLineDistance;
			rowLines.get(i).resize(x, lineY, x + width, lineY, scale);
		}
	}

	/**
	 * Draw the shape and the child shapes.
	 */
	@Override
	public void draw() {
		super.draw();
		
		for(int i=0; i < COLUMN_LINES; ++i){
			columnLines.get(i).draw();
		}
		
		for(int i=0; i < ROW_LINES; ++i){
			rowLines.get(i).draw();
		}
	}
	
}
