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
import com.dgzt.core.gate.AbstractGate;
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
	
	/** The main window. */
	private final MainWindow mainWindow;
	
	/** The number of the mooving buttons. */
	private short moovingButtonNum;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param mainWindow - The main window.
	 */
	public EventListener(final MainWindow mainWindow){
		this.mainWindow = mainWindow;
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
			mainWindow.getGameControl().allButtonIsStoppedEvent();
		}
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * The Box2D begin contact listener method.
	 */
	@Override
	public void beginContact(final Contact contact) {
		Gdx.app.log(EventListener.class.getName() + ".beginContact", "");
	}

	/**
	 * The Box2D end contact listener method.
	 */
	@Override
	public void endContact(final Contact contact) {
		Gdx.app.log(EventListener.class.getName() + ".endContact", "");
		
		final Object userDataA = contact.getFixtureA().getUserData();
		
		if(userDataA instanceof Map){
			final Ball ball = (Ball) contact.getFixtureB().getUserData();
			final LeftGate leftGate = mainWindow.getTable().getLeftGate();
			final RightGate rightGate = mainWindow.getTable().getRightGate();
			
			if(
				(ball.getBox2DY() < leftGate.getBox2DY() || ball.getBox2DY() > leftGate.getBox2DY() + AbstractGate.HEIGHT) // Left gate
				|| 
				(ball.getBox2DY() < rightGate.getBox2DY() || ball.getBox2DY() > rightGate.getBox2DY() + AbstractGate.HEIGHT) // Right gate
			){
				Gdx.app.log(EventListener.class.getName() + ".endContact", "The ball leaved map.");
				mainWindow.getGameControl().ballLeavedMap();
			}else{
				final Map map = mainWindow.getTable().getMap();
				
				if(ball.getBox2DX() < map.getBox2DX()){
					Gdx.app.log(EventListener.class.getName() + ".endContact", "Goal in left gate!");
					
					mainWindow.getGameControl().leftGateGoalEvent();
				}else if(ball.getBox2DX() > map.getBox2DX() + Map.WIDTH){
					Gdx.app.log(EventListener.class.getName() + ".endContact", "Goal in right gate!");
					
					mainWindow.getGameControl().rightGateGoalEvent();
				}
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}

}
