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
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.shape.ArcShape;
import com.dgzt.core.shape.CircleBorderShape;
import com.dgzt.core.shape.FilledCircleShape;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;
import com.dgzt.core.shape.RectangleShape;
import com.dgzt.core.util.BitsUtil;
import com.dgzt.core.util.Box2DDataUtil;
import com.dgzt.core.util.Box2DUtil;

/**
 * The map object.
 * 
 * @author Dgzt
 */
public class Map extends RectangleShape{
	
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
	
	/** The central big circle border. */
	private final CircleBorderShape centralBigCircle;
	
	/** The center line. */
	private final LineShape centerLine;
	
	/** The central small filled circle. */
	private final FilledCircleShape centralSmallCircle;
	
	/** The left small filled circle. */
	private final FilledCircleShape leftSmallCircle;
	
	/** The right small filled circle. */
	private final FilledCircleShape rightSmallCircle;
	
	/** The bottom left small arc. */
	private final ArcShape bottomLeftSmallArc;
	
	/** The bottom right small arc. */
	private final ArcShape bottomRightSmallArc;
	
	/** The top right small arc. */
	private final ArcShape topRightSmallArc;
	
	/** The top left small arc. */
	private final ArcShape topLeftSmallArc;
	
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
	 */
	public Map(final ShaderProgram shader, final World box2DWorld){
		super(shader, Color.GREEN);

		this.mapBorder = getRectangleBorderShape(shader, Color.WHITE);
		
		this.leftSector16 = getRectangleBorderShape(shader, Color.WHITE);
		this.rightSector16 = getRectangleBorderShape(shader, Color.WHITE);
		
		this.leftSector5 = getRectangleBorderShape(shader, Color.WHITE);
		this.rightSector5 = getRectangleBorderShape(shader, Color.WHITE);

		this.centralBigCircle = getCircleBorderShape(shader, Color.WHITE);
		
		this.centerLine = getLineShape(shader, Color.WHITE);
		
		this.centralSmallCircle = getFilledCircleShape(shader, Color.WHITE);
		
		this.leftSmallCircle = getFilledCircleShape(shader, Color.WHITE);
		
		this.rightSmallCircle = getFilledCircleShape(shader, Color.WHITE);
		
		this.bottomLeftSmallArc = getArcShape(shader, 0, 90, Color.WHITE);
		
		this.bottomRightSmallArc = getArcShape(shader, 270, 90, Color.WHITE);
		
		this.topRightSmallArc = getArcShape(shader, 180, 90, Color.WHITE);
		
		this.topLeftSmallArc = getArcShape(shader, 90, 90, Color.WHITE);
		
		this.leftBigArc = getArcShape(shader, 37, 109, Color.WHITE);
		
		this.rightBigArc = getArcShape(shader, 217, 109, Color.WHITE);
		
		addSensor(box2DWorld);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the map and the child objects.
	 * 
	 * @param tablePos - The position of table on screen.
	 * @param scale - The scale value.
	 */
	public void resize(final Vector2 tablePos, final double scale ) {
		final Rectangle mapRectangle = Box2DUtil.box2DRectangleToScreenRectangle(tablePos, Box2DDataUtil.MAP_RECTANGLE, scale);
		
		super.resize(mapRectangle.x, mapRectangle.y, mapRectangle.width, mapRectangle.height);
		
		resizeBottomLeftSmallArc(tablePos, scale);
		resizeBottomRightSmallArc(tablePos, scale);
		resizeTopRightSmallArc(tablePos, scale);
		resizeTopLeftSmallArc(tablePos, scale);
		
		resizeCentralBigCircle(tablePos, scale);
		resizeCentralSmallCircle(tablePos, scale);
		
		resizeLeftSmallCircle(tablePos, scale);
		resizeRightSmallCircle(tablePos, scale);
		
		resizeLeftBigArc(tablePos, scale);
		resizeRightBigArc(tablePos, scale);
		
		resizeMapBorder(mapRectangle, scale);
		
		resizeLeftSector16(tablePos, scale);
		resizeRightSector16(tablePos, scale);
		
		resizeLeftSector5(tablePos, scale);
		resizeRightSector5(tablePos, scale);
		
		resizeCenterLine(mapRectangle, scale);
	}
	
	/**
	 * Return with the left goal kick position in Box2D coordinate system.
	 */
	public Vector2 getLeftGoalKickBox2DPosition(){
		final Rectangle leftSector5Box2DRectangle = Box2DDataUtil.LEFT_SECTOR_5_RECTANGLE;
		
		return new Vector2(
				leftSector5Box2DRectangle.getX() + leftSector5Box2DRectangle.getWidth(), 
				leftSector5Box2DRectangle.getY() + leftSector5Box2DRectangle.getHeight() / 2
		);
	}
	
	/**
	 * Return with the eight goal kick position in Box2D coordinate system.
	 */
	public Vector2 getRightGoalKickBox2DPosition(){
		final Rectangle rightSector5Box2DRectangle = Box2DDataUtil.RIGHT_SECTOR_5_RECTANGLE;
		
		return new Vector2(
				rightSector5Box2DRectangle.getX(), 
				rightSector5Box2DRectangle.getY() + rightSector5Box2DRectangle.getHeight() / 2
		);
	}
	
	/**
	 * Return with the position of the top left corner in Box2D.
	 */
	public Vector2 getTopLeftCornerBox2DPosition(){
		return new Vector2(Box2DDataUtil.MAP_RECTANGLE.getX(), Box2DDataUtil.MAP_RECTANGLE.getY());
	}
	
	/**
	 * Return with the position of the top right corner in Box2D.
	 */
	public Vector2 getTopRightCornerBox2DPosition(){
		return new Vector2(Box2DDataUtil.MAP_RECTANGLE.getX() + Box2DDataUtil.MAP_RECTANGLE.getWidth(), Box2DDataUtil.MAP_RECTANGLE.getY());
	}
	
	/**
	 * Return with the position of the bottom left corner in Box2D.
	 */
	public Vector2 getBottomLeftCornerBox2DPosition(){
		return new Vector2(Box2DDataUtil.MAP_RECTANGLE.getX(), Box2DDataUtil.MAP_RECTANGLE.getY() + Box2DDataUtil.MAP_RECTANGLE.getHeight());
	}
	
	/**
	 * Return with the position of the bottom right corner in Box2D.
	 */
	public Vector2 getBottomRightCornerBox2DPosition(){
		return new Vector2(Box2DDataUtil.MAP_RECTANGLE.getX() + Box2DDataUtil.MAP_RECTANGLE.getWidth(), Box2DDataUtil.MAP_RECTANGLE.getY() + Box2DDataUtil.MAP_RECTANGLE.getHeight());
	}
	
	/**
	 * Return true when the map contains the given position.
	 * 
	 * @param box2DPosition - The position in box2D coordinate system.
	 */
	public boolean containsBox2DPosition(final Vector2 box2DPosition){
		return Box2DDataUtil.MAP_RECTANGLE.contains(box2DPosition);
	}
	
	/**
	 * Return true when the left sector 16 contains the given box2D position else false.
	 * 
	 * @param box2DPosition - The Box2D position.
	 */
	public boolean containsLeftSector16Box2DPosition(final Vector2 box2DPosition){
		return Box2DDataUtil.LEFT_SECTOR_16_RECTANGLE.contains(box2DPosition);
	}
	
	/**
	 * Return true when the right sector 16 contains the given box2D position else false.
	 * 
	 * @param box2DPosition - The Box2D position.
	 */
	public boolean containsRightSector16Box2DPosition(final Vector2 box2DPosition){
		return Box2DDataUtil.RIGHT_SECTOR_16_RECTANGLE.contains(box2DPosition);
	}
	
	// --------------------------------------------------
	// ~ Protected methods
	// --------------------------------------------------
	
	/**
	 * Create a {@link RectangleBorderShape} object.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	protected RectangleBorderShape getRectangleBorderShape(final ShaderProgram shader, final Color color){
		return new RectangleBorderShape(shader, color);
	}
	
	/**
	 * Create a {@link CircleBorderShape} object.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	protected CircleBorderShape getCircleBorderShape(final ShaderProgram shader, final Color color){
		return new CircleBorderShape(shader, color);
	}
	
	/**
	 * Create a {@link LineShape} object.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	protected LineShape getLineShape(final ShaderProgram shader, final Color color){
		return new LineShape(shader, color);
	}
	
	/**
	 * Create a {@link FilledCircleShape} object.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	protected FilledCircleShape getFilledCircleShape(final ShaderProgram shader, final Color color){
		return new FilledCircleShape(shader, color);
	}
	
	/**
	 * Create a {@link ArcShape} object.
	 * 
	 * @param shader - The shader.
	 * @param startDegrees - The starting degrees.
	 * @param degreesNum - The number of degrees.
	 * @param color - The color.
	 */
	protected ArcShape getArcShape(final ShaderProgram shader, final int startDegrees, final int degreesNum, final Color color){
		return new ArcShape(shader, startDegrees, degreesNum, color);
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
		
		centralBigCircle.draw();
		
		centralSmallCircle.draw();
		
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
		centralBigCircle.dispose();
		centerLine.dispose();
		centralSmallCircle.dispose();
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
		Box2DUtil.addSensor(box2DWorld, Box2DDataUtil.MAP_RECTANGLE.getX(), Box2DDataUtil.MAP_RECTANGLE.getY(), Box2DDataUtil.MAP_RECTANGLE.getWidth(), Box2DDataUtil.MAP_RECTANGLE.getHeight(), this, BitsUtil.MAP_SENSOR_BITS, BitsUtil.BALL_BITS);
	}
	
	/**
	 * Resize the bottom left small arc.
	 * 
	 * @param tablePosition - The position of table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeBottomLeftSmallArc(final Vector2 tablePosition, final double scale){
		final Circle arc = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.BOTTOM_LEFT_ARC, scale);
		
		bottomLeftSmallArc.resize(arc.x, arc.y, arc.radius, scale);
	}
	
	/**
	 * Resize the bottom right small arc.
	 * 
	 * @param tablePosition - The position of table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeBottomRightSmallArc(final Vector2 tablePosition, final double scale){
		final Circle arc = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.BOTTOM_RIGHT_ARC, scale);
		
		bottomRightSmallArc.resize(arc.x, arc.y, arc.radius, scale);
	}
	
	/**
	 * Resize the top right small arc.
	 * 
	 * @param tablePosition - The position of table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeTopRightSmallArc(final Vector2 tablePosition, final double scale){
		final Circle arc = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.TOP_RIGHT_ARC, scale);
		
		topRightSmallArc.resize(arc.x, arc.y, arc.radius, scale);
	}
	
	/**
	 * Resize the top left small arc.
	 * 
	 * @param tablePosition - The position of table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeTopLeftSmallArc(final Vector2 tablePosition, final double scale){
		final Circle arc = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.TOP_LEFT_ARC, scale);
		
		topLeftSmallArc.resize(arc.x, arc.y, arc.radius, scale);
	}
	
	/**
	 * Resize the center big circle.
	 * 
	 * @param tablePosition - The position of the table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeCentralBigCircle(final Vector2 tablePosition, final double scale){
		final Circle circle = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.CENTRAL_BIG_CIRCLE, scale);
		
		centralBigCircle.resize(circle.x, circle.y, circle.radius, scale);
	}
	
	/**
	 * Resize the center small circle.
	 * 
	 * @param tablePosition - The position of table on screen.
	 * @param radius - The radius value.
	 */
	private void resizeCentralSmallCircle(final Vector2 tablePosition, final double scale){
		final Circle circle = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.CENTRAL_SMALL_CIRCLE, scale);
		
		centralSmallCircle.resize(circle.x, circle.y, circle.radius);
	}
	
	/**
	 * Resize the left small circle.
	 * 
	 * @param tablePosition - The position of table on screen.
	 * @param radius - The radius value.
	 */
	private void resizeLeftSmallCircle(final Vector2 tablePosition, final double scale){
		final Circle circle = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.LEFT_SMALL_CIRCLE, scale);
		
		leftSmallCircle.resize(circle.x, circle.y, circle.radius);
	}
	
	/**
	 * Resize the right small circle.
	 * 
	 * @param tablePosition - The position of table on screen.
	 * @param radius - The radius value.
	 */
	private void resizeRightSmallCircle(final Vector2 tablePosition, final double scale){
		final Circle circle = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.RIGHT_SMALL_CIRCLE, scale);
		
		rightSmallCircle.resize(circle.x, circle.y, circle.radius);
	}
	
	/**
	 * Resize left big arc.
	 * 
	 * @param tablePosition - The position of the table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeLeftBigArc(final Vector2 tablePosition, final double scale){
		final Circle circle = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.LEFT_BIG_CIRCLE, scale);
		
		leftBigArc.resize(circle.x, circle.y, circle.radius, scale);
	}
	
	/**
	 * Resize right big arc.
	 * 
	 * @param tablePosition - The position of the table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeRightBigArc(final Vector2 tablePosition, final double scale){
		final Circle circle = Box2DUtil.box2DCircleToScreenCircle(tablePosition, Box2DDataUtil.RIGHT_BIG_CIRCLE, scale);
		
		rightBigArc.resize(circle.x, circle.y, circle.radius, scale);
	}
	
	/**
	 * Resize border of map.
	 * 
	 * @param mapRectangle - The rectangle of map on screen.
	 * @param scale - The scale value.
	 */
	private void resizeMapBorder(final Rectangle mapRectangle, final double scale){
		mapBorder.resize(mapRectangle.x, mapRectangle.y, mapRectangle.width, mapRectangle.height, scale);
	}
	
	/**
	 * Resize left sector 16.
	 * 
	 * @param tablePosition - The position of the table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeLeftSector16(final Vector2 tablePosition, final double scale){
		final Rectangle rec = Box2DUtil.box2DRectangleToScreenRectangle(tablePosition, Box2DDataUtil.LEFT_SECTOR_16_RECTANGLE, scale);
		
		leftSector16.resize(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight(), scale);
	}
	
	/**
	 * Resize right sector 16.
	 * 
	 * @param tablePosition - The position of the table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeRightSector16(final Vector2 tablePosition, final double scale){
		final Rectangle rec = Box2DUtil.box2DRectangleToScreenRectangle(tablePosition, Box2DDataUtil.RIGHT_SECTOR_16_RECTANGLE, scale);
		
		rightSector16.resize(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight(), scale);
	}
	
	/**
	 * Resize the left sector 5.
	 * 
	 * @param tablePosition - The position of table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeLeftSector5(final Vector2 tablePosition, final double scale){
		final Rectangle rectangle = Box2DUtil.box2DRectangleToScreenRectangle(tablePosition, Box2DDataUtil.LEFT_SECTOR_5_RECTANGLE, scale);
		
		leftSector5.resize(rectangle.x, rectangle.y, rectangle.width, rectangle.height, scale);
	}
	
	/**
	 * Resize the right sector 5.
	 * 
	 * @param tablePosition - The position of table on screen.
	 * @param scale - The scale value.
	 */
	private void resizeRightSector5(final Vector2 tablePosition, final double scale){
		final Rectangle rectangle = Box2DUtil.box2DRectangleToScreenRectangle(tablePosition, Box2DDataUtil.RIGHT_SECTOR_5_RECTANGLE, scale);
		
		rightSector5.resize(rectangle.x, rectangle.y, rectangle.width, rectangle.height, scale);
	}
	
	/**
	 * Resize the center line.
	 * 
	 * @param mapRectangle - The rectangle of map on screen.
	 * @param scale - The scale value.
	 */
	private void resizeCenterLine(final Rectangle mapRectangle, final double scale){
		final float x1 = mapRectangle.getX() + mapRectangle.getWidth() / 2;
		final float y1 = mapRectangle.getY();
		final float x2 = x1;
		final float y2 = y1 + mapRectangle.getHeight();
		centerLine.resize(x1, y1, x2, y2, scale);
	}

}
