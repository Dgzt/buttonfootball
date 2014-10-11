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
package com.dgzt.core.shape;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link RectangleBorderShape}.
 * 
 * @author Dgzt
 */
public class RectangleBorderShapeTest {
	
	// --------------------------------------------------
	// ~ Constant members
	// --------------------------------------------------	
	
	/** The x coordinate value. */
	private static final float X = 10.0f;
	
	/** The y coordinate value. */
	private static final float Y = 20.0f;
	
	/** The width value. */
	private static final float WIDTH = 200.0f;
	
	/** The height value. */
	private static final float HEIGHT = 300.0f;
	
	/** The scale value. */
	private static final double SCALE = 2.3;
	
	/** The delta value. */
	private static final float DELTA = 0.1f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The border of rectangle shape. */
	private RectangleBorderShape shape;
	
	// --------------------------------------------------
	// ~ Tests
	// --------------------------------------------------	
	
	/**
	 * Declarate the shape.
	 */
	@Before
	public void setUp(){
		shape = new RectangleBorderShape(null);
		shape.resize(X, Y, WIDTH, HEIGHT, SCALE);
	}
	
	/**
	 * Test for {@code getY()} method.
	 */
	@Test
	public void test_getY(){
		assertEquals(Y, shape.getY(), DELTA);
	}
	
	/**
	 * Test for {@code getWidth()} method.
	 */
	@Test
	public void test_getWidth(){
		assertEquals(WIDTH, shape.getWidth(), DELTA);
	}
	
	/**
	 * Test for {@code getHeight()} method.
	 */
	@Test
	public void test_getHeight(){
		assertEquals(HEIGHT, shape.getHeight(), DELTA);
	}

}
