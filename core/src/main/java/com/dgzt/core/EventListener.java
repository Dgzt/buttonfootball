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
import com.dgzt.core.button.Button;
import com.dgzt.core.gate.AbstractGate;
import com.dgzt.core.gate.LeftGate;
import com.dgzt.core.gate.RightGate;

/**
 * The event listener.
 * 
 * @author Dgzt
 */
public class EventListener implements ContactListener{
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The game window. */
	private final GameWindow gameWindow;
	
	/** The number of the moving buttons. */
	private short movingButtonNum;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param gameWindow - The game window.
	 */
	public EventListener(final GameWindow gameWindow){
		this.gameWindow = gameWindow;
		this.movingButtonNum = 0;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------

	/**
	 * The button is start moving.
	 */
	public void buttonStartMoving(){
		++movingButtonNum;
	}
	
	/**
	 * The button is stopped.
	 */
	public void buttonEndMoving(){
		--movingButtonNum;
		if(movingButtonNum == 0){
			gameWindow.getGameControl().allButtonIsStoppedEvent();
		}
	}
	
	/**
	 * Clear the moving number.
	 */
	public void clearMovings(){
		movingButtonNum = 0;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * The Box2D begin contact listener method.
	 */
	@Override
	public void beginContact(final Contact contact) {
		Gdx.app.log(EventListener.class.getName() + ".beginContact", "init");
		
		final Object userDataA = contact.getFixtureA().getUserData();
		final Object userDataB = contact.getFixtureB().getUserData();
		
		// The ball contact with button
		if(userDataB instanceof Ball && userDataA instanceof Button){
			final Button button = (Button) userDataA;
			gameWindow.getGameControl().buttonContactBall(button);
		}
		
		// The button contact with button
		if(userDataA instanceof Button && userDataB instanceof Button){
			final GameStatus gameStatus = gameWindow.getGameControl().getGameStatus();
			
			if(gameStatus.equals(GameStatus.WAITING_AFTER_PLAYER) || gameStatus.equals(GameStatus.WAITING_AFTER_OPPONENT)){
				final Button buttonA = (Button) userDataA;
				final Button buttonB = (Button) userDataB;
				final Table table = gameWindow.getTable();
				
				if( (table.getPlayerButtons().contains(buttonA) && table.getOpponentButtons().contains(buttonB)) ||
					(table.getPlayerButtons().contains(buttonB) && table.getOpponentButtons().contains(buttonA)) ){
					gameWindow.getGameControl().buttonContactButton(buttonA, buttonB);
				}
			}
			
		}
	}

	/**
	 * The Box2D end contact listener method.
	 */
	@Override
	public void endContact(final Contact contact) {
		Gdx.app.log(EventListener.class.getName() + ".endContact", "init");
		
		final Object userDataA = contact.getFixtureA().getUserData();
		
		if(userDataA instanceof Map){
			final Ball ball = (Ball) contact.getFixtureB().getUserData();
			final LeftGate leftGate = gameWindow.getTable().getLeftGate();
			final RightGate rightGate = gameWindow.getTable().getRightGate();
			
			if(
				(ball.getBox2DY() < leftGate.getBox2DY() || ball.getBox2DY() > leftGate.getBox2DY() + AbstractGate.HEIGHT) // Left gate
				|| 
				(ball.getBox2DY() < rightGate.getBox2DY() || ball.getBox2DY() > rightGate.getBox2DY() + AbstractGate.HEIGHT) // Right gate
			){
				Gdx.app.log(EventListener.class.getName() + ".endContact", "The ball leaved map.");
				gameWindow.getGameControl().ballLeaveMapEvent();
			}else{
				final Map map = gameWindow.getTable().getMap();
				
				if(ball.getBox2DX() < map.getBox2DX()){
					Gdx.app.log(EventListener.class.getName() + ".endContact", "Goal in left gate!");
					
					gameWindow.getGameControl().leftGateGoalEvent();
				}else if(ball.getBox2DX() > map.getBox2DX() + Map.WIDTH){
					Gdx.app.log(EventListener.class.getName() + ".endContact", "Goal in right gate!");
					
					gameWindow.getGameControl().rightGateGoalEvent();
				}
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}
}
