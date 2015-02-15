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
import com.dgzt.core.scoreboard.GoalBoard;

/**
 * The game control.
 * 
 * @author Dgzt
 */
public final class GameControl {
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private final MainWindow mainWindow;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param mainWindow - The main window.
	 */
	public GameControl(final MainWindow mainWindow){
		this.mainWindow = mainWindow;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * New opponent goal.
	 */
	public void opponentGoal(){
		final GoalBoard playerGoalBoard = mainWindow.getScoreBoard().getPlayerGoalBoard();
		playerGoalBoard.setNumber(playerGoalBoard.getNumber() + 1);
	}
	
	/**
	 * New player goal.
	 */
	public void playerGoal(){
		final GoalBoard opponentGoalBoard = mainWindow.getScoreBoard().getOpponentGoalBoard();
		opponentGoalBoard.setNumber(opponentGoalBoard.getNumber() + 1);
	}
	
	/**
	 * The all button is stopped event.
	 */
	public void allButtonIsStopped(){
		Gdx.app.log(GameControl.class.getName() + ".allButtonIsStooped()", "");
	}

}
