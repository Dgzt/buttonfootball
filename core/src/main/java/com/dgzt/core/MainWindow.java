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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dgzt.core.scoreboard.ScoreBoard;
import com.dgzt.core.shape.Shape;

/**
 * The main window.
 * 
 * @author Dgzt
 */
public class MainWindow implements Shape{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The width of main window in cm. */
	public static float WIDTH = Table.WIDTH;

	/** The height of main window in cm. */
	public static float HEIGHT = ScoreBoard.HEIGHT + Shape.LINE_WIDTH + Table.HEIGHT;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The scoreboard. */
	private final ScoreBoard scoreBoard;
	
	/** The table. */
	private final Table table;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public MainWindow(final ShapeRenderer shapeRenderer){
		scoreBoard = new ScoreBoard(shapeRenderer);
		table = new Table(shapeRenderer);
		
		Gdx.input.setInputProcessor(new InputListener(table));
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the child objects.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param scale - The scale value.
	 */
	public void resize(final float x, final float y, final float width, final double scale){
		final float halfLineHeight = (float)(Shape.LINE_WIDTH * scale) / 2;
		
		final float scoreBoardWidth = (float)(ScoreBoard.WIDTH * scale);
		final float scoreBoardHeight = (float)(ScoreBoard.HEIGHT * scale);
		final float scoreBoardX = x + (width - scoreBoardWidth) / 2;
		final float scoreBoardY = y + halfLineHeight;
		
		final float tableWidth = (float)(Table.WIDTH * scale);
		final float tableHeight = (float)(Table.HEIGHT * scale);
		final float tableX = x  + (width - tableWidth) / 2;
		final float tableY = scoreBoardY + scoreBoardHeight + halfLineHeight;
		
		scoreBoard.resize(scoreBoardX, scoreBoardY, scoreBoardWidth, scoreBoardHeight, scale);
		table.resize(tableX, tableY, tableWidth, tableHeight, scale);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * Draw the child objects.
	 */
	@Override
	public void draw() {
		scoreBoard.draw();
		table.draw();
	}
}
