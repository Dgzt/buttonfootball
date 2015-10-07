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
package com.dgzt.core.util;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dgzt.core.button.Button;
import com.dgzt.core.gate.LeftGate;
import com.dgzt.core.shape.LineShape;

/**
 * Util for box2D datas.
 * 
 * @author Dgzt
 */
public final class Box2DDataUtil {
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	// Table
	private static final float TABLE_X = 0.0f;
	private static final float TABLE_Y = 0.0f;
	private static final float TABLE_WIDTH = 184.0f;
	private static final float TABLE_HEIGHT = 120.0f;
	
	// Map
	private static final float MAP_WIDTH = 167.0f;
	private static final float MAP_HEIGHT = 104.0f;
	private static final float MAP_X = (TABLE_WIDTH - MAP_WIDTH) / 2;
	private static final float MAP_Y = (TABLE_HEIGHT - MAP_HEIGHT) / 2;
	
	// Border arcs
	private static final float BORDER_ARC_RADIUS = 4.0f;
	private static final float LEFT_ARC_X = MAP_X;
	private static final float RIGHT_ARC_X = MAP_X + MAP_WIDTH;
	private static final float BOTTOM_ARC_Y = MAP_Y;
	private static final float TOP_ARC_Y = MAP_Y + MAP_HEIGHT;
	
	// Left gate
	private static final float LEFT_GATE_X = MAP_X - LeftGate.WIDTH + LineShape.LINE_WIDTH;
	private static final float LEFT_GATE_Y = (TABLE_HEIGHT - LeftGate.HEIGHT) / 2;
	
	// Right gate
	private static final float RIGHT_GATE_X = MAP_X + MAP_WIDTH - LineShape.LINE_WIDTH;
	private static final float RIGHT_GATE_Y = LEFT_GATE_Y;
	
	// Sector 5
	private static final float SECTOR_5_WIDTH = 11.0f;
	private static final float SECTOR_5_HEIGHT = 30.0f;
	private static final float SECTOR_5_Y = MAP_Y + (MAP_HEIGHT - SECTOR_5_HEIGHT) / 2;
	private static final float LEFT_SECTOR_5_X = MAP_X;
	private static final float RIGHT_SECTOR_5_X = MAP_X + MAP_WIDTH - SECTOR_5_WIDTH;

	// Left sector 16
	private static final float LEFT_SECTOR_16_WIDTH = 30.0f;
	private static final float LEFT_SECTOR_16_HEIGHT = 60.0f;
	private static final float LEFT_SECTOR_16_X = MAP_X;
	private static final float LEFT_SECTOR_16_Y = MAP_Y + (MAP_HEIGHT - LEFT_SECTOR_16_HEIGHT) / 2;
	
	// Right Sector 16
	private static final float RIGHT_SECTOR_16_WIDTH = LEFT_SECTOR_16_WIDTH;
	private static final float RIGHT_SECTOR_16_HEIGHT = LEFT_SECTOR_16_HEIGHT;
	private static final float RIGHT_SECTOR_16_X = MAP_X + MAP_WIDTH - RIGHT_SECTOR_16_WIDTH;
	private static final float RIGHT_SECTOR_16_Y = LEFT_SECTOR_16_Y;
	
	// The radius of the circles
	private static final float SMALL_CIRCLE_RADIUS = 1.0f;
	private static final float BIG_CIRCLE_RADIUS = 16.0f;
	
	// Left and right big circle
	private static final float LEFT_RIGHT_CIRCLE_DISTANCE = 20.5f;
	
	// Left circle
	private static final float LEFT_CIRCLE_X = MAP_X + LEFT_RIGHT_CIRCLE_DISTANCE;
	private static final float LEFT_CIRCLE_Y = MAP_Y + MAP_HEIGHT /2;
	
	// Central circle
	private static final float CENTRAL_CIRCLE_X = MAP_X + MAP_WIDTH / 2;
	private static final float CENTRAL_CIRCLE_Y = LEFT_CIRCLE_Y;
	
	// Right circle
	private static final float RIGHT_CIRCLE_X = MAP_X + MAP_WIDTH - LEFT_RIGHT_CIRCLE_DISTANCE;
	private static final float RIGHT_CIRCLE_Y = LEFT_CIRCLE_Y;
	
	// Left goalkeeper
	private static final float LEFT_GOALKEEPER_X = MAP_X + Button.RADIUS;
	private static final float LEFT_GOALKEEPER_Y = MAP_Y + MAP_HEIGHT / 2;
	
	// Right goalkeeper
	private static final float RIGHT_GOALKEEPER_X = MAP_X + MAP_WIDTH - Button.RADIUS;
	private static final float RIGHT_GOALKEEPER_Y = LEFT_GOALKEEPER_Y;
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	public static final Rectangle TABLE_RECTANGLE = new Rectangle(TABLE_X, TABLE_Y, TABLE_WIDTH, TABLE_HEIGHT);
	
	public static final Rectangle MAP_RECTANGLE = new Rectangle(MAP_X, MAP_Y, MAP_WIDTH, MAP_HEIGHT);
	
	// Small arcs
	public static final Circle BOTTOM_LEFT_ARC = new Circle(LEFT_ARC_X, BOTTOM_ARC_Y, BORDER_ARC_RADIUS);
	public static final Circle TOP_LEFT_ARC = new Circle(LEFT_ARC_X, TOP_ARC_Y, BORDER_ARC_RADIUS);
	public static final Circle TOP_RIGHT_ARC = new Circle(RIGHT_ARC_X, TOP_ARC_Y, BORDER_ARC_RADIUS);
	public static final Circle BOTTOM_RIGHT_ARC = new Circle(RIGHT_ARC_X, BOTTOM_ARC_Y, BORDER_ARC_RADIUS);
	
	// Gates
	public static final Vector2 LEFT_GATE_POSITION = new Vector2(LEFT_GATE_X, LEFT_GATE_Y);
	public static final Vector2 RIGHT_GATE_POSITION = new Vector2(RIGHT_GATE_X, RIGHT_GATE_Y);
	
	// Sector 5
	public static final Rectangle LEFT_SECTOR_5_RECTANGLE = new Rectangle(LEFT_SECTOR_5_X, SECTOR_5_Y, SECTOR_5_WIDTH, SECTOR_5_HEIGHT);
	public static final Rectangle RIGHT_SECTOR_5_RECTANGLE = new Rectangle(RIGHT_SECTOR_5_X, SECTOR_5_Y, SECTOR_5_WIDTH, SECTOR_5_HEIGHT);

	// Sector 16
	public static final Rectangle LEFT_SECTOR_16_RECTANGLE = new Rectangle(LEFT_SECTOR_16_X, LEFT_SECTOR_16_Y, LEFT_SECTOR_16_WIDTH, LEFT_SECTOR_16_HEIGHT);
	public static final Rectangle RIGHT_SECTOR_16_RECTANGLE = new Rectangle(RIGHT_SECTOR_16_X, RIGHT_SECTOR_16_Y, RIGHT_SECTOR_16_WIDTH, RIGHT_SECTOR_16_HEIGHT);
	
	// Left circles
	public static final Circle LEFT_SMALL_CIRCLE = new Circle(LEFT_CIRCLE_X, LEFT_CIRCLE_Y, SMALL_CIRCLE_RADIUS);
	public static final Circle LEFT_BIG_CIRCLE = new Circle(LEFT_CIRCLE_X, LEFT_CIRCLE_Y, BIG_CIRCLE_RADIUS);

	// Central circles
	public static final Circle CENTRAL_SMALL_CIRCLE = new Circle(CENTRAL_CIRCLE_X, CENTRAL_CIRCLE_Y, SMALL_CIRCLE_RADIUS);
	public static final Circle CENTRAL_BIG_CIRCLE = new Circle(CENTRAL_CIRCLE_X, CENTRAL_CIRCLE_Y, BIG_CIRCLE_RADIUS);

	// Right circles
	public static final Circle RIGHT_SMALL_CIRCLE = new Circle(RIGHT_CIRCLE_X, RIGHT_CIRCLE_Y, SMALL_CIRCLE_RADIUS);
	public static final Circle RIGHT_BIG_CIRCLE = new Circle(RIGHT_CIRCLE_X, RIGHT_CIRCLE_Y, BIG_CIRCLE_RADIUS);
	
	// Goalkeepers
	public static final Vector2 LEFT_GOALKEEPER_POSITION = new Vector2(LEFT_GOALKEEPER_X, LEFT_GOALKEEPER_Y);
	public static final Vector2 RIGHT_GOALKEEPER_POSITION = new Vector2(RIGHT_GOALKEEPER_X, RIGHT_GOALKEEPER_Y);
}
