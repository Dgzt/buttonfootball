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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Test for {@link Box2DUtil}.
 * 
 * @author Dgzt
 */
public class Box2DUtilTest {
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	private final static double DELTA = 0.01;
	
	private final static float X = 10;
	private final static float Y = 20;
	private final static float WIDTH = 25;
	private final static float HEIGHT = 35;
	private final static short WALL_MASK_BITS = BitsUtil.BUTTON_BITS | BitsUtil.BALL_BITS;
	
	private final static String SENSOR_USER_DATA = "Temp user data";
	private final static short SENSOR_CATEGORY_BITS = 4;
	private final static short SENSOR_MASKS_BITS = 8;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private World box2DWorld;
	
	// --------------------------------------------------
	// ~ Setup method
	// --------------------------------------------------
	
	/**
	 * Setup the variables to the tests.
	 */
	@Before
	public void setUp(){
		box2DWorld = new World(new Vector2(0,0), true);
	}
	
	// --------------------------------------------------
	// ~ Tests
	// --------------------------------------------------
	
	/**
	 * Test for {@link Box2DUtil#addWall} method.
	 */
	@Test
	public void test_addWall(){
		Box2DUtil.addWall(box2DWorld, X, Y, WIDTH, HEIGHT);
		
		assertEquals(1, box2DWorld.getFixtureCount());

		final Array<Fixture> fixtures = new Array<Fixture>();
		box2DWorld.getFixtures(fixtures);
		final Fixture fixture = fixtures.get(0);
		
		assertEquals(X + (WIDTH / 2), fixture.getBody().getPosition().x, DELTA);
		assertEquals(Y + (HEIGHT / 2), fixture.getBody().getPosition().y, DELTA);
		assertFalse(fixture.isSensor());
		assertEquals(BitsUtil.WALL_BITS, fixture.getFilterData().categoryBits);
		assertEquals(WALL_MASK_BITS, fixture.getFilterData().maskBits);
		assertNull(fixture.getUserData());
	}
	
	/**
	 * Test for {@link Box2DUtil#addSensor} method.
	 */
	@Test
	public void test_addSensor(){
		Box2DUtil.addSensor(box2DWorld, X, Y, WIDTH, HEIGHT, SENSOR_USER_DATA, SENSOR_CATEGORY_BITS, SENSOR_MASKS_BITS);
		
		assertEquals(1, box2DWorld.getFixtureCount());
		
		final Array<Fixture> fixtures = new Array<Fixture>();
		box2DWorld.getFixtures(fixtures);
		final Fixture fixture = fixtures.get(0);
		
		assertEquals(X + (WIDTH / 2), fixture.getBody().getPosition().x, DELTA);
		assertEquals(Y + (HEIGHT / 2), fixture.getBody().getPosition().y, DELTA);
		assertTrue(fixture.isSensor());
		assertEquals(SENSOR_CATEGORY_BITS, fixture.getFilterData().categoryBits);
		assertEquals(SENSOR_MASKS_BITS, fixture.getFilterData().maskBits);
		assertEquals(SENSOR_USER_DATA, fixture.getUserData());
	}

	/**
	 * Test for {@link Box2DUtil#screenPositionToBox2DPosition(Vector2, Vector2, double)} method.
	 */
	@Test
	public void test_screenPositionToBox2DPosition(){
		final Vector2 screenPosition = new Vector2(126.0f, 189.0f);
		final Vector2 tablePosition = new Vector2(100.0f, 150.0f);
		final double scale = 1.3d;
		final Vector2 result = new Vector2(20.0f, 30.0f);
		
		final Vector2 box2DPos = Box2DUtil.screenPositionToBox2DPosition(screenPosition, tablePosition, scale);
		
		assertEquals(result, box2DPos);
	}
}
