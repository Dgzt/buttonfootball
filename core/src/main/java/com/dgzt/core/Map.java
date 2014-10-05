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
import com.dgzt.core.shape.ArcShape;
import com.dgzt.core.shape.CircleBorderShape;
import com.dgzt.core.shape.CircleShape;
import com.dgzt.core.shape.LineShape;
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
	
	/** The radius of the big circle. */
	private static final float BIG_CIRCLE_RADIUS = 16.0f;
	
	/** The left and right small circle distance from border of map. */
	private static final float LEFT_RIGHT_SMALL_CIRCLE_DISTANCE = 20.5f;
	
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
	
	/** The center big circle border. */
	private final CircleBorderShape centerBigCircle;
	
	/** The center line. */
	private final LineShape centerLine;
	
	/** The center small filled circle. */
	private final CircleShape centerSmallCircle;
	
	/** The left small filled circle. */
	private final CircleShape leftSmallCircle;
	
	/** The right small filled circle. */
	private final CircleShape rightSmallCircle;
	
	/** The top left small arc. */
	private final ArcShape topLeftSmallArc;
	
	/** The top right small arc. */
	private final ArcShape topRightSmallArc;
	
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

		this.centerBigCircle = new CircleBorderShape(shapeRenderer, Color.GREEN, Color.WHITE);
		
		this.centerLine = new LineShape(shapeRenderer);
		
		this.centerSmallCircle = new CircleShape(shapeRenderer, Color.WHITE);
		
		this.leftSmallCircle = new CircleShape(shapeRenderer, Color.WHITE);
		
		this.rightSmallCircle = new CircleShape(shapeRenderer, Color.WHITE);
		
		this.topLeftSmallArc = new ArcShape(shapeRenderer, Color.GREEN, Color.WHITE, 0, 90);
		
		this.topRightSmallArc = new ArcShape(shapeRenderer, Color.GREEN, Color.WHITE, 90, 90);
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
		
		// Top left small arc
		final float topLeftSmallArcX = x;
		final float topLeftSmallArcY = y;
		final float smallArcRadius = (float)(ArcShape.SMALL_RADIUS * scale);
		
		topLeftSmallArc.resize(topLeftSmallArcX, topLeftSmallArcY, smallArcRadius, scale);
		
		// Top right small arc
		final float topRightSmallArcX = x + width;
		final float topRightSmallArcY = y;
		
		topRightSmallArc.resize(topRightSmallArcX, topRightSmallArcY, smallArcRadius, scale);
		
		// Border of map
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
		
		// Center big circle
		final float centerCircleX = x + width / 2;
		final float centerCircleY = y + height / 2;
		final float bigCircleRadius = (float)((double)BIG_CIRCLE_RADIUS * scale);
		
		centerBigCircle.resize(centerCircleX, centerCircleY, bigCircleRadius, scale);
		
		// Center line
		final float centerLineX1 = x + width / 2;
		final float centerLineY1 = y;
		final float centerLineX2 = centerLineX1;
		final float centerLineY2 = centerLineY1 + height;
		
		centerLine.resize(centerLineX1, centerLineY1, centerLineX2, centerLineY2, scale);
		
		// Center small circle
		final float smallCircleRadius = (float) (LINE_WIDTH * scale);
		
		centerSmallCircle.resize(centerCircleX, centerCircleY, smallCircleRadius);
		
		// Left small circle
		final float leftRightSmallCircleDistance = (float)(LEFT_RIGHT_SMALL_CIRCLE_DISTANCE * scale);
		final float leftSmallCircleX = x + leftRightSmallCircleDistance;
		final float leftSmallCircleY = centerCircleY;
		
		leftSmallCircle.resize(leftSmallCircleX, leftSmallCircleY, smallCircleRadius);
		
		// Right small circle
		final float rightSmallCircleX = x + width - leftRightSmallCircleDistance;
		final float rightSmallCircleY = leftSmallCircleY;
		
		rightSmallCircle.resize(rightSmallCircleX, rightSmallCircleY, smallCircleRadius);
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
		
		topLeftSmallArc.draw();
		topRightSmallArc.draw();
		
		mapBorder.draw();		
		
		leftSector16.draw();
		rightSector16.draw();
		
		leftSector5.draw();
		rightSector5.draw();

		centerBigCircle.draw();
		
		centerLine.draw();
		
		centerSmallCircle.draw();
		
		leftSmallCircle.draw();
		
		rightSmallCircle.draw();
	}
	
}
