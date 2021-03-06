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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Util for {@link MathUtil}.
 * 
 * @author Dgzt
 */
public class MathUtilTest {
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	private static final double DELTA = 0.01;
	
	// --------------------------------------------------
	// ~ Tests
	// --------------------------------------------------
	
	/**
	 * Test for {@link MathUtil#distance(float, float)} method.
	 */
	@Test
	public void test_distanceFloatFloat(){
		final float x = 5;
		final float y = 6;
		
		final double distance = Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
		
		assertEquals(distance, MathUtil.distance(x, y), DELTA);
	}
	
	/**
	 * Test for {@link MathUtil#distance(float, float, float, float)} method.
	 */
	@Test
	public void test_distanceFloatFloatFloatFloat(){
		final float x1 = 3;
		final float y1 = 4;
		final float x2 = 5;
		final float y2 = 6;
		
		final float x = x2 - x1;
		final float y = y2 - y1;
		
		final double distance = Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
		
		assertEquals(distance, MathUtil.distance(x1, y1, x2, y2), DELTA);
	}
	
	/**
	 * test for {@link MathUtil#distance(float, float, float, float, float, float)} method.
	 */
	@Test
	public void test_distancePointToLineSegment(){
		final float linePoint1X = 200.0f;
		final float linePoint1Y = 50.0f;
		
		final float linePoint2X = 200.0f;
		final float linePoint2Y = 350.0f;
		
		final float point1X = 50.0f;
		final float point1Y = 50.0f;
		final float point1Distance = 150.0f;
		
		final float point2X = 50.0f;
		final float point2Y = 100.0f;
		final float point2Distance = 150.0f;
		
		final float point3X = 50.0f;
		final float point3Y = 450.0f;
		final float point3Distance = 180.27756377319946f;
		
		assertEquals(point1Distance, MathUtil.distance(point1X, point1Y, linePoint1X, linePoint1Y, linePoint2X, linePoint2Y), DELTA);
		assertEquals(point2Distance, MathUtil.distance(point2X, point2Y, linePoint1X, linePoint1Y, linePoint2X, linePoint2Y), DELTA);
		assertEquals(point3Distance, MathUtil.distance(point3X, point3Y, linePoint1X, linePoint1Y, linePoint2X, linePoint2Y), DELTA);
	}
	
	/**
	 * Test for {@link MathUtil#isRectangleFullyContainsCircle(Rectangle, Circle)} method.
	 */
	@Test
	public void test_isRectangleFullyContainsCircle(){
		final Rectangle rec = new Rectangle(10.0f, 15.0f, 100.0f, 115.0f);
		final float radius = 4.0f;
		
		assertFalse(MathUtil.isRectangleFullyContainsCircle(rec, new Circle(rec.x + radius, rec.y + radius, radius)));
		assertFalse(MathUtil.isRectangleFullyContainsCircle(rec, new Circle(rec.x + rec.width - radius, rec.y + radius, radius)));
		assertFalse(MathUtil.isRectangleFullyContainsCircle(rec, new Circle(rec.x + rec.width - radius, rec.y + rec.height - radius, radius)));
		assertFalse(MathUtil.isRectangleFullyContainsCircle(rec, new Circle(rec.x + radius, rec.y + rec.height - radius, radius)));
		assertTrue(MathUtil.isRectangleFullyContainsCircle(rec, new Circle(rec.x + radius + 1, rec.y + radius + 1, radius)));
	}
	
	/**
	 * Test for {@link MathUtil#midPoint(Vector2, Vector2)} method.
	 */
	@Test
	public void test_midPoint(){
		final Vector2 point1 = new Vector2(-3, 5);
		final Vector2 point2 = new Vector2(8, -1);
		final Vector2 result = new Vector2(2.5f, 2);
		
		assertEquals(result, MathUtil.midPoint(point1, point2));
	}

	/**
	 * Test for {@link MathUtil#scale(Rectangle, double)} method.
	 */
	@Test
	public void test_scaleRectangle(){
		final Rectangle rectangle = new Rectangle(1, 2, 10, 20);
		final double scale = 3.0;
		final Rectangle rectangleResult = new Rectangle(3, 6, 30, 60);
		
		assertEquals(rectangleResult, MathUtil.scale(rectangle, scale));
	}
	
	/**
	 * Test for {@link MathUtil#scale(Circle, double)} method.
	 */
	@Test
	public void test_scaleCircle(){
		final Circle circle = new Circle(10, 15, 20);
		final double scale = 2.0f;
		final Circle circleResult = new Circle(20, 30, 40);
		
		assertEquals(circleResult, MathUtil.scale(circle, scale));
	}
	
	/**
	 * Test for {@link MathUtil#extend(Rectangle, float)} method.
	 */
	@Test
	public void test_extendRectangle(){
		final Rectangle rectangle = new Rectangle(10, 20, 100, 200);
		final float extendValue = 5;
		final Rectangle rectangleResult = new Rectangle(5, 15, 110, 210);
		
		assertEquals(rectangleResult, MathUtil.extend(rectangle, extendValue));
	}
	
	/**
	 * Test for {@link MathUtil#extend(Circle, float)} method.
	 */
	@Test
	public void test_extendCircle(){
		final Circle circle = new Circle(10, 20, 50);
		final float extendValue = 5;
		final Circle circleResult = new Circle(10, 20, 55);
		
		assertEquals(circleResult, MathUtil.extend(circle, extendValue));
	}
}
