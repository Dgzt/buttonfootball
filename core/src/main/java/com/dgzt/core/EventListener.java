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
import com.dgzt.core.gate.LeftGate;
import com.dgzt.core.gate.RightGate;

/**
 * The event listener.
 * 
 * @author Dgzt
 */
public final class EventListener implements ContactListener{
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The game control. */
	private final GameControl gameControl;
	
	/** The number of the mooving buttons. */
	private short moovingButtonNum;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param gameControl - The game control.
	 */
	public EventListener(final GameControl gameControl){
		this.gameControl = gameControl;
		this.moovingButtonNum = 0;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------

	/**
	 * The button is start mooving.
	 */
	public void buttonStartMooving(){
		++moovingButtonNum;
	}
	
	/**
	 * The button is stopped.
	 */
	public void buttonEndMooving(){
		--moovingButtonNum;
		if(moovingButtonNum == 0){
			gameControl.allButtonIsStopped();
		}
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * The Box2D begin contact listener method.
	 */
	@Override
	public void beginContact(Contact contact) {
		Gdx.app.log(EventListener.class.getName() + ".beginContact", "");
		
		final Object userDataA = contact.getFixtureA().getUserData();
			
		if(userDataA instanceof LeftGate){
			Gdx.app.log(EventListener.class.getName() + ".beginContact", "Opponent goal!!");
			gameControl.opponentGoal();
		}
			
		if(userDataA instanceof RightGate){
			Gdx.app.log(EventListener.class.getName() + ".beginContact", "Player goal!!");
			gameControl.playerGoal();
		}
	}

	/**
	 * The Box2D end contact listener method.
	 */
	@Override
	public void endContact(Contact contact) {
		Gdx.app.log(EventListener.class.getName() + ".endContact", "");
		
		final Object userDataA = contact.getFixtureA().getUserData();
		
		if(userDataA instanceof Map){
			Gdx.app.log(EventListener.class.getName() + ".endContact", "Ball out the map.");
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}

}
