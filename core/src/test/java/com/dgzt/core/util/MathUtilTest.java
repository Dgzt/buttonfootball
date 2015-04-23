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

import org.junit.Test;

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

}
