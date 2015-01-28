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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.dgzt.core.button.Ball;
import com.dgzt.core.util.SensorUserDataEnum;

/**
 * The Box2D contact listener.
 * 
 * @author Dgzt
 */
public class MyContactListener implements ContactListener{

	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * The Box2D begin contact listener method.
	 */
	@Override
	public void beginContact(Contact contact) {
		Gdx.app.log(MyContactListener.class.getName() + ".beginContact", "");
		
		if(contact.getFixtureA().getUserData() instanceof SensorUserDataEnum){
			final SensorUserDataEnum userDataA = (SensorUserDataEnum) contact.getFixtureA().getUserData();
			
			if(userDataA.equals(SensorUserDataEnum.PLAYER_GATE_SENSOR) && contact.getFixtureB().getUserData() instanceof Ball){
				Gdx.app.log(MyContactListener.class.getName() + ".beginContact", "Opponent goal!!");
			}
			
			if(userDataA.equals(SensorUserDataEnum.OPPONENT_GATE_SENSOR) && contact.getFixtureB().getUserData() instanceof Ball){
				Gdx.app.log(MyContactListener.class.getName() + ".beginContact", "Player goal!!");
			}
		}
	}

	/**
	 * The Box2D end contact listener method.
	 */
	@Override
	public void endContact(Contact contact) {
		Gdx.app.log(MyContactListener.class.getName() + ".endContact", "");
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}

}
