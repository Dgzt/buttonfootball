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
package com.dgzt.core.gate;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.button.Button;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;
import com.dgzt.core.util.BitsUtil;
import com.dgzt.core.util.Box2DUtil;

/**
 * Gate object.
 * 
 * @author Dgzt
 */
public abstract class AbstractGate extends RectangleBorderShape{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width of the gate in cm. */
	public static final float WIDTH = 8.0f;
	
	/** The height of the gate in cm. */
	public static final float HEIGHT = 13.0f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------	
	
	/** Number of column line. */
	private static final int COLUMN_LINES = 3;
	
	/** Number of row line. */
	private static final int ROW_LINES = 3;
	
	/** The color. */
	private static final Color COLOR = Color.WHITE;
	
	/** The bits of walls. */
	protected static final short TOP_WALL_BITS = 2;
	protected static final short RIGHT_WALL_BITS = 4;
	protected static final short BOTTOM_WALL_BITS = 8;
	protected static final short LEFT_WALL_BITS = 16;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The x coordinate value in Box2D. */
	private final float box2DX;
	
	/** The y coordiante value in Box2D. */
	private final float box2DY;
	
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
	 * @param shader - The shader.
	 * @param box2DWorld - The world in the Box2D.
	 * @param box2DX - The x coordinate value in Box2D.
	 * @param box2DY - The y coordinate value in Box2D.
	 */
	public AbstractGate(final ShaderProgram shader, final World box2DWorld, final float box2DX, final float box2DY) {
		super(shader, COLOR);
		this.box2DX = box2DX;
		this.box2DY = box2DY;

		columnLines = new ArrayList<LineShape>();
		for(int i=0; i < COLUMN_LINES; ++i){
			columnLines.add(new LineShape(shader, COLOR));
		}
		
		rowLines = new ArrayList<LineShape>();
		for(int i=0; i < ROW_LINES; ++i){
			rowLines.add(new LineShape(shader, COLOR));
		}
		
		addBox2DSensor(box2DWorld);
		addBox2DWalls(box2DWorld);
	}
	
	// --------------------------------------------------
	// ~ Abstract methods
	// --------------------------------------------------
	
	/**
	 * Return the wall bits.
	 */
	protected abstract short getWallBits();
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the gate.
	 * 
	 * @param tableX - The x coordinate value of table.
	 * @param tableY - The y coordinate value of table.
	 * @param scale - The scale value.
	 */
	public void resize(final float tableX, final float tableY, final double scale){
		final float lineWidth = (float)(LineShape.LINE_WIDTH * scale);
		
		final float x = tableX + (float)(box2DX * scale);
		final float y = tableY + (float)(box2DY * scale);
		final float width = (float)(AbstractGate.WIDTH * scale);
		final float height = (float)(AbstractGate.HEIGHT * scale);
		
		super.resize(x, y, width, height, scale);
		
		final float columnLineDistance = (width - 2*lineWidth) / (COLUMN_LINES + 1);
		final float rowLineDistance = (height - 2*lineWidth) / (ROW_LINES + 1);
		
		for(int i=0; i < COLUMN_LINES; ++i){
			final float lineX = x + lineWidth + (i+1)*columnLineDistance;
			columnLines.get(i).resize(lineX, y, lineX, y + height, scale);
		}
		
		for(int i=0; i < ROW_LINES; ++i){
			final float lineY = y + lineWidth + (i+1)*rowLineDistance;
			rowLines.get(i).resize(x, lineY, x + width, lineY, scale);
		}
	}
	
	/**
	 * Return true when the given position is on the wall of gate else false.
	 * 
	 * @param box2DPos - The position in Box2D world.
	 */
	public boolean isButtonPositionOnWall(final Vector2 box2DPos){
		final Circle button = new Circle(box2DPos, Button.RADIUS);

		if(isTopWall() && Intersector.overlaps(button, getTopBox2DWallRectangle())){
			return true;
		}
		
		if(isRightWall() && Intersector.overlaps(button, getRightBox2DWallRectangle())){
			return true;
		}
		
		if(isBottomWall() && Intersector.overlaps(button, getBottomBox2DWallRectangle())){
			return true;
		}
		
		if(isLeftWall() && Intersector.overlaps(button, getLeftBox2DWallRectangle())){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Return with the Box2D position.
	 */
	public Vector2 getBox2DPosition(){
		return new Vector2(box2DX, box2DY);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose(){
		for(final LineShape line : columnLines){
			line.dispose();
		}
		for(final LineShape line : rowLines){
			line.dispose();
		}
		
		super.dispose();
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/** 
	 * Add the sensor of gate to the Box2D.
	 * 
	 * @param box2DWorld - The world of the Box2D.
	 */
	private void addBox2DSensor(final World box2DWorld){
		Box2DUtil.addSensor(box2DWorld, box2DX, box2DY, AbstractGate.WIDTH, AbstractGate.HEIGHT, this, BitsUtil.GATE_SENSOR_BITS, BitsUtil.BALL_BITS);
	}
	
	/** 
	 * Add the walls of gate to the Box2D. 
	 * 
	 * @param box2DWorld - The world of the Box2D.
	 */
	private void addBox2DWalls(final World box2DWorld){
		if(isTopWall()){
			final Rectangle rec = getTopBox2DWallRectangle();
			Box2DUtil.addWall(box2DWorld, rec.x, rec.y, rec.width, rec.height);
		}
		if(isRightWall()){
			final Rectangle rec = getRightBox2DWallRectangle();
			Box2DUtil.addWall(box2DWorld, rec.x, rec.y, rec.width, rec.height);
		}
		if(isBottomWall()){
			final Rectangle rec = getBottomBox2DWallRectangle();
			Box2DUtil.addWall(box2DWorld, rec.x, rec.y, rec.width, rec.height);
		}
		if(isLeftWall()){
			final Rectangle rec = getLeftBox2DWallRectangle();
			Box2DUtil.addWall(box2DWorld, rec.x, rec.y, rec.width, rec.height);
		}
	}
	
	/**
	 * Return true when the top wall is exists else false.
	 */
	private boolean isTopWall(){
		return (getWallBits() & TOP_WALL_BITS) != 0;
	}
	
	/**
	 * Return true when the right wall is exists else false.
	 */
	private boolean isRightWall(){
		return (getWallBits() & RIGHT_WALL_BITS) != 0;
	}
	
	/**
	 * Return true when the bottom wall is exists else false.
	 */
	private boolean isBottomWall(){
		return (getWallBits() & BOTTOM_WALL_BITS) != 0;
	}
	
	/**
	 * Return true when the left wall is exists else false.
	 */
	private boolean isLeftWall(){
		return (getWallBits() & LEFT_WALL_BITS) != 0;
	}
	
	/**
	 * Return with the rectangle of the top wall in Box2D world.
	 */
	private Rectangle getTopBox2DWallRectangle(){
		return new Rectangle(box2DX, box2DY, AbstractGate.WIDTH, LineShape.LINE_WIDTH);
	}
	
	/**
	 * Return with the rectangle of the bottom wall in Box2D world.
	 */
	private Rectangle getBottomBox2DWallRectangle(){
		return new Rectangle(box2DX, box2DY + AbstractGate.HEIGHT - LineShape.LINE_WIDTH, AbstractGate.WIDTH, LineShape.LINE_WIDTH);
	}
	
	/**
	 * Return with the rectangle of the left wall in Box2D world.
	 */
	private Rectangle getLeftBox2DWallRectangle(){
		return new Rectangle(box2DX, box2DY, LineShape.LINE_WIDTH, AbstractGate.HEIGHT);
	}
	
	/**
	 * Return with the rectangle of the right wall in Box2D world.
	 */
	private Rectangle getRightBox2DWallRectangle(){
		return new Rectangle(box2DX + AbstractGate.WIDTH - LineShape.LINE_WIDTH, box2DY, LineShape.LINE_WIDTH, AbstractGate.HEIGHT);
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
