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
package com.dgzt.core.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.EventListener;
import com.dgzt.core.Table;
import com.dgzt.core.shape.FilledCircleShape;

/**
 * The abstract button.
 * 
 * @author Dgzt
 */
public abstract class AbstractButton extends FilledCircleShape{

	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The density of the fixture definition. */
	private static final float FIXTURE_DEFINITION_DENSITY = 1.0f;
	
	/** The friction of the fixture definition. */
	private static final float FIXTURE_DEFINITION_FRICTION = 10.0f;
	
	/** The restitution of the fixture definition. */
	private static final float FIXTURE_DEFINITION_RESTITUTION = 0.4f;
	
	/** The linear damping for body of box2D. */
	private static final float BOX2D_BODY_LINEAR_DAMPING = 3.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The parent object. */
	private final Table parent;
	
	/** The event listener. */
	private final EventListener eventListener;
	
	/** The x coordinate value in Box2D. */
	private float box2DX;
	
	/** The y coordinate value in Box2D. */
	private float box2DY;

	/** The radius value in Box2D. */
	private final float box2DRadius;
	
	/** The box2D body. */
	private final Body box2DBody;
	
	/** Is the button mooving? */
	private boolean mooving;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param parent - The parent object.
	 * @param shader - The shader.
	 * @param eventListener - The event listener.
	 * @param box2DWorld - The box2D world.
	 * @param color - The color of button.
	 * @param box2DX - The x coordinate value of button in Box2D.
	 * @param box2DY - The y coordinate value of button in Box2D.
	 * @param box2DRadius - The radius value of button in Box2D.
	 */
	public AbstractButton(
			final Table parent, 
			final ShaderProgram shader,
			final EventListener eventListener, 
			final World box2DWorld, 
			final Color color, 
			final float box2DX, 
			final float box2DY, 
			final float box2DRadius
	) {
		super(shader, color);
		this.parent = parent;
		this.eventListener = eventListener;
		this.box2DX = box2DX;
		this.box2DY = box2DY;
		this.box2DRadius = box2DRadius;
		this.mooving = false;
		
		this.box2DBody = createBox2DBody(box2DWorld);
	}
	
	// --------------------------------------------------
	// ~ Abstract methods
	// --------------------------------------------------
	
	/**
	 * The category bits.
	 */
	protected abstract short getCategoryBits();
	
	/**
	 * The mask bits.
	 */
	protected abstract short getMaskBits();

	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Contains the button the given coordinate.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public boolean contains(final float x, final float y){
		return (Math.abs(getX() - x) < getRadius() && Math.abs(getY() - y) < getRadius());
	}
	
	/**
	 * Resize the object.
	 */
	public void resize(){
		final float x = parent.getX() + (float)(box2DX * parent.getScale());
		final float y = parent.getY() + (float)(box2DY * parent.getScale());
		final float radius = (float)(box2DRadius * parent.getScale());
		
		super.resize(x, y, radius);
	}
	
	/**
	 * Move the button to the given position.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public void move(final float x, final float y){
		box2DBody.setLinearVelocity(x, y);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * Draw the shape. 
	 * If changed the position in the box2D world then resize the shape.
	 */
	@Override
	public void draw() {
		if( box2DX != box2DBody.getPosition().x || box2DY != box2DBody.getPosition().y ){
			box2DX = box2DBody.getPosition().x;
			box2DY = box2DBody.getPosition().y;

			resize();
			
			if(mooving == false){
				mooving = true;
				Gdx.app.log(AbstractButton.class.getName() + ".draw()", "Start mooving.");
				eventListener.buttonStartMooving();
			}
		}else if(mooving == true){
			mooving = false;
			Gdx.app.log(AbstractButton.class.getName() + ".draw()", "Stop mooving.");
			eventListener.buttonEndMooving();
		}
		
		super.draw();
	}
	
	@Override
	public void dispose(){
		final World box2DWorld = box2DBody.getWorld();
		box2DWorld.destroyBody(box2DBody);
		box2DBody.setUserData(null);
		
		super.dispose();
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------

	/**
	 * Create the box2D body.
	 * 
	 * @param box2DWorld - The box2D world.
	 * @return - The box2D body.
	 */
	private Body createBox2DBody(final World box2DWorld){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(box2DX, box2DY);
		
		Body body = box2DWorld.createBody(bodyDef);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(box2DRadius);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = FIXTURE_DEFINITION_DENSITY;
		fixtureDef.friction = FIXTURE_DEFINITION_FRICTION;
		fixtureDef.restitution = FIXTURE_DEFINITION_RESTITUTION;

		fixtureDef.filter.categoryBits = getCategoryBits();
		fixtureDef.filter.maskBits = getMaskBits();
		
		final Fixture fixture = body.createFixture(fixtureDef);
		fixture.setUserData(this);
		
		circle.dispose();
		
		// The linear damping.
		body.setLinearDamping(BOX2D_BODY_LINEAR_DAMPING);
		
		return body;
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
