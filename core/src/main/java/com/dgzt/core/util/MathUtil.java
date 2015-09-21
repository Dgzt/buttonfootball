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

/**
 * Math utils.
 * 
 * @author Dgzt
 */
public class MathUtil {
	
	// --------------------------------------------------
	// ~ Public static methods
	// --------------------------------------------------
	
	/**
	 * Distance from the origo.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public static double distance(final float x, final float y){
		return distance(0, 0, x, y);
	}

	/**
	 * Distance from two point.
	 * 
	 * @param x1 - The first x coordinate value.
	 * @param y1 - The first y coordinate value.
	 * @param x2 - The second x coordinate value.
	 * @param y2 - The second y coordinate value.
	 */
	public static double distance(final float x1, final float y1, final float x2, final float y2) {
		final float x = x2 - x1;
		final float y = y2 - y1;
		
		return Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
	}
	
	/**
	 * Point distance to line segment.
	 * 
	 * @param pointX - The x coordinate value of point.
	 * @param pointY - The y coordinate value of point.
	 * @param lineP1X - The x coordinate value of first point of line segment.
	 * @param lineP1Y - The y coordinate value of first point of line segment.
	 * @param lineP2X - The x coordinate value of second point of line segment.
	 * @param lineP2Y - The y coordinate value of second point of line segment.
	 * @return
	 */
	public static double distance(
			final float pointX, 
			final float pointY, 
			final float lineP1X, 
			final float lineP1Y, 
			final float lineP2X,
			final float lineP2Y
	){
		final double lineLength = Math.pow(distance(lineP1X, lineP1Y, lineP2X, lineP2Y), 2);
		
		// Is equal 0
		if(Double.compare(lineLength, 0) == 0){
			return distance(pointX, pointY, lineP1X, lineP1Y);
		}
		
		final double t = ((pointX - lineP1X) * (lineP2X - lineP1X) + (pointY - lineP1Y) * (lineP2Y - lineP1Y)) / lineLength;

		if(t < 0){
			return distance(pointX, pointY, lineP1X, lineP1Y);
		}
		if(t > 1){
			return distance(pointX, pointY, lineP2X, lineP2Y);
		}
		
		return distance(pointX, pointY, (float) (lineP1X + t * (lineP2X - lineP1X)), (float) (lineP1Y + t * (lineP2Y - lineP1Y)) );
	}
	
	/**
	 * Return true when the rectangle fully contains the circle else false.
	 * If the border of the circle is on the border of rectangle then return false.
	 * 
	 * @param rec - The rectangle.
	 * @param circle - The circle.
	 */
	public static boolean isRectangleFullyContainsCircle(final Rectangle rec, final Circle circle){
		return rec.x + circle.radius < circle.x &&
				circle.x < rec.x + rec.width - circle.radius &&
				rec.y + circle.radius < circle.y &&
				circle.y < rec.y + rec.height - circle.radius;
	}

	/**
	 * Return with the mid point from two given points.
	 * 
	 * @param point1 - The first point.
	 * @param point2 - The second point.
	 */
	public static Vector2 midPoint(final Vector2 point1, final Vector2 point2){
		final float x = ( point1.x + point2.x ) / 2;
		final float y = ( point1.y + point2.y ) / 2;
		
		return new Vector2(x, y);
	}
	
	/**
	 * Return with the scaled rectangle.
	 * 
	 * @param rec - The rectangle.
	 * @param scale - The scale value.
	 */
	public static Rectangle scale(final Rectangle rec, final double scale){
		final Rectangle retRec = new Rectangle(rec);
		retRec.x *= scale;
		retRec.y *= scale;
		retRec.width *= scale;
		retRec.height *= scale;
		
		return retRec;
	}
	
	/**
	 * Return with the scaled circle.
	 * 
	 * @param circle - The circle.
	 * @param scale - The scale.
	 */
	public static Circle scale(final Circle circle, final double scale){
		final Circle retCircle = new Circle(circle);
		retCircle.x *= scale;
		retCircle.y *= scale;
		retCircle.radius *= scale;
		
		return retCircle;
	}
	
	/**
	 * Return with the extended rectangle.
	 * 
	 * @param rec - The rectangle.
	 * @param value - The extend value.
	 */
	public static Rectangle extend(final Rectangle rec, final float value){
		final Rectangle retRec = new Rectangle(rec);
		retRec.x -= value;
		retRec.y -= value;
		retRec.width += 2 * value;
		retRec.height += 2 * value;
		
		return retRec;
	}
	
	/**
	 * Return with the extended circle.
	 * 
	 * @param circle - The circle.
	 * @param value - The extend value.
	 */
	public static Circle extend(final Circle circle, final float value){
		final Circle retCircle = new Circle(circle);
		retCircle.radius += value;
		
		return retCircle;
	}
}
