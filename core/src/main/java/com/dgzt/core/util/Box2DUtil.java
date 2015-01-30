package com.dgzt.core.util;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DUtil {
	
	/**
	 * Add box2D wall.
	 * 
	 * @param box2DWorld - The world of the Box2D.
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 */
	public static void addWall(
			final World box2DWorld,
			final float x, 
			final float y, 
			final float width, 
			final float height
	){
		addRectangle(box2DWorld, x, y, width, height, false, null, BitsUtil.WALL_BITS, (short) (BitsUtil.BALL_BITS | BitsUtil.BUTTON_BITS));
	}
	
	/**
	 * Add box2D rectangle sensor.
	 * 
	 * @param box2DWorld - The world of the Box2D.
	 * @param x - The x coordinate value.
	 * @param y - The x coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param userData - The user data of fixture.
	 * @param categoryBits - The category bits of filter.
	 * @param maskBits - The mask bits of filter.
	 */
	public static void addSensor(
			final World box2dWorld,
			final float x, 
			final float y, 
			final float width, 
			final float height, 
			final Object userData,
			final short categoryBits,
			final short maskBits
	){
		addRectangle(box2dWorld, x, y, width, height, true, userData, categoryBits, maskBits);
	}

	/**
	 * Add box2D rectangle.
	 * 
	 * @param box2DWorld - The world of the Box2D.
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param isSensor - Is the rectangle sensor?
	 * @param userData - The user data of fixture.
	 * @param categoryBits - The category bits of filter.
	 * @param maskBits - The mask bits of filter.
	 */
	private static void addRectangle(
			final World box2DWorld,
			final float x, 
			final float y, 
			final float width, 
			final float height, 
			final boolean isSensor, 
			final Object userData,
			final short cateforyBits,
			final short maskBits
	){
		final BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x + width / 2, y + height / 2);
		
		final Body body = box2DWorld.createBody(bodyDef);
		
		final PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.isSensor = isSensor;

		fixtureDef.filter.categoryBits = cateforyBits;
		fixtureDef.filter.maskBits = maskBits;

		final Fixture fixture = body.createFixture(fixtureDef);
		fixture.setUserData(userData);
	}
	
}
