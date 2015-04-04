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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.shape.ArcShape;
import com.dgzt.core.shape.CircleBorderShape;
import com.dgzt.core.shape.FilledCircleShape;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;
import com.dgzt.core.shape.RectangleShape;
import com.dgzt.core.util.BitsUtil;
import com.dgzt.core.util.Box2DUtil;

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
	private static final float LEFT_RIGHT_CIRCLE_DISTANCE = 20.5f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------	
	
	/** The x coordinate value in box2D */
	private final float box2DX;
	
	/** The y coordinate value in box2D */
	private final float box2DY;
	
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
	private final FilledCircleShape centerSmallCircle;
	
	/** The left small filled circle. */
	private final FilledCircleShape leftSmallCircle;
	
	/** The right small filled circle. */
	private final FilledCircleShape rightSmallCircle;
	
	/** The top left small arc. */
	private final ArcShape topLeftSmallArc;
	
	/** The top right small arc. */
	private final ArcShape topRightSmallArc;
	
	/** The bottom right small arc. */
	private final ArcShape bottomRightSmallArc;
	
	/** The bottom left small arc. */
	private final ArcShape bottomLeftSmallArc;
	
	/** The left big arc. */
	private final ArcShape leftBigArc;
	
	/** The right big arc. */
	private final ArcShape rightBigArc;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param box2DWorld - The world of the Box2D.
	 * @param box2DX - The x coordinate value in Box2D.
	 * @param box2DY - The y coordinate value in Box2D.
	 */
	public Map(final ShaderProgram shader, final World box2DWorld, final float box2DX, final float box2DY){
		super(shader, Color.GREEN);
		this.box2DX = box2DX;
		this.box2DY = box2DY;
		
		this.mapBorder = new RectangleBorderShape(shader, Color.WHITE);
		
		this.leftSector16 = new RectangleBorderShape(shader, Color.WHITE);
		this.rightSector16 = new RectangleBorderShape(shader, Color.WHITE);
		
		this.leftSector5 = new RectangleBorderShape(shader, Color.WHITE);
		this.rightSector5 = new RectangleBorderShape(shader, Color.WHITE);

		this.centerBigCircle = new CircleBorderShape(shader, Color.WHITE);
		
		this.centerLine = new LineShape(shader, Color.WHITE);
		
		this.centerSmallCircle = new FilledCircleShape(shader, Color.WHITE);
		
		this.leftSmallCircle = new FilledCircleShape(shader, Color.WHITE);
		
		this.rightSmallCircle = new FilledCircleShape(shader, Color.WHITE);
		
		this.topLeftSmallArc = new ArcShape(shader, 0, 90, Color.WHITE);
		
		this.topRightSmallArc = new ArcShape(shader, 270, 90, Color.WHITE);
		
		this.bottomRightSmallArc = new ArcShape(shader, 180, 90, Color.WHITE);
		
		this.bottomLeftSmallArc = new ArcShape(shader, 90, 90, Color.WHITE);
		
		this.leftBigArc = new ArcShape(shader, 37, 109, Color.WHITE);
		
		this.rightBigArc = new ArcShape(shader, 217, 109, Color.WHITE);
		
		addSensor(box2DWorld);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the map and the child objects.
	 * 
	 * @param tableX - The x coordinate value of table.
	 * @param tableY - The y coordinate value of table.
	 * @param scale - The scale value.
	 */
	public void resize(final float tableX, final float tableY, final double scale ) {
		final float x = tableX + (float)(box2DX * scale);
		final float y = tableY + (float)(box2DY * scale);
		final float width = (float)(Map.WIDTH * scale);
		final float height = (float)(Map.HEIGHT * scale);
		
		super.resize(x, y, width, height);
		
		final float smallArcRadius = (float)(ArcShape.SMALL_RADIUS * scale);
		final float bigCircleRadius = (float)((double)BIG_CIRCLE_RADIUS * scale);
		final float smallCircleRadius = (float) (LineShape.LINE_WIDTH * scale);
		final float leftRightSmallCircleDistance = (float)(LEFT_RIGHT_CIRCLE_DISTANCE * scale);
		final float sector16Width = (float)((double)SECTOR_16_WIDTH * scale);
		final float sector16Height = (float)((double)SECTOR_16_HEIGHT * scale);
		final float sector5Width = (float)((double)SECTOR_5_WIDTH * scale);
		final float sector5Height = (float)((double)SECTOR_5_HEIGHT * scale);
		
		resizeTopLeftSmallArc(x, y, smallArcRadius, scale);
		resizeTopRightSmallArc(y, x, width, smallArcRadius, scale);
		resizeBottomRightSmallArc(topRightSmallArc.getX(), y, height, smallArcRadius, scale);
		resizeBottomLeftSmallArc(x, bottomRightSmallArc.getY(), smallArcRadius, scale);
		
		resizeCenterBigCircle(x, y, width, height, bigCircleRadius, scale);
		resizeCenterSmallCircle(centerBigCircle.getX(), centerBigCircle.getY(), smallCircleRadius);
		
		resizeLeftSmallCircle(centerSmallCircle.getY(), x, leftRightSmallCircleDistance, smallCircleRadius);
		resizeRightSmallCircle(leftSmallCircle.getY(), x, width, leftRightSmallCircleDistance, smallCircleRadius);
		
		resizeLeftBigArc(leftSmallCircle.getX(), leftSmallCircle.getY(), bigCircleRadius, scale);
		resizeRightBigArc(rightSmallCircle.getX(), rightSmallCircle.getY(), bigCircleRadius, scale);
		
		resizeMapBorder(x, y, width, height, scale);
		
		resizeLeftSector16(x, sector16Width, sector16Height, y, height, scale);
		resizeRightSector16(leftSector16.getY(), leftSector16.getWidth(), leftSector16.getHeight(), x, width, scale);
		
		resizeLeftSector5(x, sector5Width, sector5Height, y, height, scale);
		resizeRightSector5(leftSector5.getY(), sector5Width, sector5Height, x, width, scale);
		
		resizeCenterLine(x, y, width, height, scale);
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
		bottomRightSmallArc.draw();
		bottomLeftSmallArc.draw();
		
		centerBigCircle.draw();
		
		centerSmallCircle.draw();
		
		leftBigArc.draw();
		
		rightBigArc.draw();
		
		rightSmallCircle.draw();
		
		leftSmallCircle.draw();
		
		
		mapBorder.draw();		
		
		leftSector16.draw();
		rightSector16.draw();
		
		leftSector5.draw();
		rightSector5.draw();
		
		centerLine.draw();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(){
		mapBorder.dispose();
		leftSector16.dispose();
		rightSector16.dispose();
		leftSector5.dispose();
		rightSector5.dispose();
		centerBigCircle.dispose();
		centerLine.dispose();
		centerSmallCircle.dispose();
		leftSmallCircle.dispose();
		rightSmallCircle.dispose();
		topLeftSmallArc.dispose();
		topRightSmallArc.dispose();
		bottomRightSmallArc.dispose();
		bottomLeftSmallArc.dispose();
		leftBigArc.dispose();
		rightBigArc.dispose();
		
		super.dispose();
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Add the sensor to the box2D.
	 * 
	 * @param box2DWorld - The world of the Box2D.
	 */
	private void addSensor(final World box2DWorld){
		Box2DUtil.addSensor(box2DWorld, box2DX, box2DY, Map.WIDTH, Map.HEIGHT, this, BitsUtil.MAP_SENSOR_BITS, BitsUtil.BALL_BITS);
	}
	
	/**
	 * Resize the top left small arc.
	 * 
	 * @param x - The x coordinate value of the arc.
	 * @param y - The y coordinate value of the arc.
	 * @param radius - The radius value.
	 * @param scale - The scale value.
	 */
	private void resizeTopLeftSmallArc(final float x, final float y, final float radius, final double scale){
		topLeftSmallArc.resize(x, y, radius, scale);
	}
	
	/**
	 * Resize the top right small arc.
	 * 
	 * @param y - The y coordinate value of the arc.
	 * @param mapX - The x coordinate value of the map.
	 * @param mapWidth - The width value of the map.
	 * @param radius The radius value.
	 * @param scale - The scale value.
	 */
	private void resizeTopRightSmallArc(final float y, final float mapX, final float mapWidth, final float radius, final double scale){
		final float x = mapX + mapWidth;
		
		topRightSmallArc.resize(x, y, radius, scale);
	}
	
	/**
	 * Resize the bottom right small arc.
	 * 
	 * @param x - The x coordinate value of the arc.
	 * @param mapY - The y coordinate value of the map.
	 * @param mapHeight - The height value of the map.
	 * @param radius - The radius value.
	 * @param scale - The scale value.
	 */
	private void resizeBottomRightSmallArc(final float x, final float mapY, final float mapHeight, final float radius, final double scale){
		final float y = mapY + mapHeight;
		
		bottomRightSmallArc.resize(x, y, radius, scale);
	}
	
	/**
	 * Resize the bottom left small arc.
	 * 
	 * @param x - The x coordinate value of the arc.
	 * @param y - The y coordinate value of the arc.
	 * @param radius - The radius value.
	 * @param scale - The scale value.
	 */
	private void resizeBottomLeftSmallArc(final float x, final float y, final float radius, final double scale){
		bottomLeftSmallArc.resize(x, y, radius, scale);
	}
	
	/**
	 * Resize the center big circle.
	 * 
	 * @param mapX - The x coordinate value of the map.
	 * @param mapY - The y coordinate value of the map.
	 * @param mapWidth - The width value of the map.
	 * @param mapHeight - The height value of the map.
	 * @param radius - The radius value.
	 * @param scale - The scale value.
	 */
	private void resizeCenterBigCircle(final float mapX, final float mapY, final float mapWidth, final float mapHeight, final float radius, final double scale){
		final float x = mapX + mapWidth / 2;
		final float y = mapY + mapHeight / 2;
		
		centerBigCircle.resize(x, y, radius, scale);
	}
	
	/**
	 * Resize the center small circle.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordiante value.
	 * @param radius - The radius value.
	 */
	private void resizeCenterSmallCircle(final float x, final float y, final float radius){
		centerSmallCircle.resize(x, y, radius);
	}
	
	/**
	 * Resize the left small circle.
	 * 
	 * @param y - The y coordinate value.
	 * @param mapX - The x coordinate value of the map.
	 * @param distance - The distance from the left border of map.
	 * @param radius - The radius value.
	 */
	private void resizeLeftSmallCircle(final float y, final float mapX, final float distance, final float radius){
		final float x = mapX + distance;
		
		leftSmallCircle.resize(x, y, radius);
	}
	
	/**
	 * Resize the right small circle.
	 * 
	 * @param y - The y coordinate value.
	 * @param mapX - The x coordinate value of map.
	 * @param mapWidth - The width value of map.
	 * @param distance - The distance from the right border of map.
	 * @param radius - The radius value.
	 */
	private void resizeRightSmallCircle(final float y, final float mapX, final float mapWidth, final float distance, final float radius){
		final float x = mapX + mapWidth - distance;
		
		rightSmallCircle.resize(x, y, radius);
	}
	
	/**
	 * Resize left big arc.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param radius - The radius value.
	 * @param scale - The scale value.
	 */
	private void resizeLeftBigArc(final float x, final float y, final float radius, final double scale){
		leftBigArc.resize(x, y, radius, scale);
	}
	
	/**
	 * Resize right big arc.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param radius - The radius value.
	 * @param scale - The scale value.
	 */
	private void resizeRightBigArc(final float x, final float y, final float radius, final double scale){
		rightBigArc.resize(x, y, radius, scale);
	}
	
	/**
	 * Resize border of map.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param scale - The scale value.
	 */
	private void resizeMapBorder(final float x, final float y, final float width, final float height, final double scale){
		mapBorder.resize(x, y, width, height, scale);
	}
	
	/**
	 * Resize left sector 16.
	 * 
	 * @param x - The x coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param mapY - The y coordinate value of the map.
	 * @param mapHeight - The height value of the map.
	 * @param scale - The scale value.
	 */
	private void resizeLeftSector16(final float x, final float width, final float height, final float mapY, final float mapHeight, final double scale){
		final float y = mapY + (mapHeight - height) / 2;
		leftSector16.resize(x, y, width, height, scale);
	}
	
	/**
	 * Resize right sector 16.
	 * 
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param mapX - The x coordinate value of the map.
	 * @param mapWidth - The width value of the map.
	 * @param scale - The scale value.
	 */
	private void resizeRightSector16(final float y, final float width, final float height, final float mapX, final float mapWidth, final double scale){
		final float x = mapX + mapWidth - width;
		
		rightSector16.resize(x, y, width, height, scale);
	}
	
	/**
	 * Resize the left sector 5.
	 * 
	 * @param x - The x coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param mapY - The y coordinate value of the map.
	 * @param mapHeight - The height value of the map.
	 * @param scale - The scale value.
	 */
	private void resizeLeftSector5(final float x, final float width, final float height, final float mapY, final float mapHeight, final double scale){
		final float y = mapY + ( mapHeight - height ) / 2;
		
		leftSector5.resize(x, y, width, height, scale);
	}
	
	/**
	 * Resize the right sector 5.
	 * 
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param mapX - The x coordinate value of the map.
	 * @param mapWidth - The width value of the map.
	 * @param scale - The scale value.
	 */
	private void resizeRightSector5(final float y, final float width, final float height, final float mapX, final float mapWidth, final double scale){
		final float x = mapX + mapWidth - width;
		
		rightSector5.resize(x, y, width, height, scale);
		
	}
	
	/**
	 * Resize the center line.
	 * 
	 * @param mapX - The x coordinate value of the map.
	 * @param mapY - The y coordinate value of the map.
	 * @param mapWidth - The width value of the map.
	 * @param mapHeight - The height value of the map.
	 * @param scale - The scale value.
	 */
	private void resizeCenterLine(final float mapX, final float mapY, final float mapWidth, final float mapHeight, final double scale){
		final float x1 = mapX + mapWidth / 2;
		final float y1 = mapY;
		final float x2 = x1;
		final float y2 = y1 + mapHeight;
		centerLine.resize(x1, y1, x2, y2, scale);
	}

	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------
	
	/**
	 * Return with the x coordinate value in Box2D.
	 */
	public float getBox2DX() {
		return box2DX;
	}
	
	/**
	 * Return with the y coordinate value in Box2D.
	 */
	public float getBox2DY() {
		return box2DY;
	}
}
