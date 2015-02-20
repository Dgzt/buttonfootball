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
	// ~ Private enums
	// --------------------------------------------------
	
	/** The game statuses. */
	private enum GameStatus{ PLAYER_IN_GAME, WAITING_AFTER_PLAYER };
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The main window. */
	private final MainWindow mainWindow;
	
	/** The actual status of the game. */
	private GameStatus gameStatus;
	
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
		
		this.gameStatus = GameStatus.PLAYER_IN_GAME;
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
		Gdx.app.log(GameControl.class.getName() + ".allButtonIsStopped()", "");

		mainWindow.showPlayerBallArea();
	}
	
	/** 
	 * The player stepped 
	 */
	public void playerStepped(){
		gameStatus = GameStatus.WAITING_AFTER_PLAYER;
	}
	
	/** 
	 * Return true when the player step now.
	 */
	public boolean isPlayerStep(){
		return gameStatus == GameStatus.PLAYER_IN_GAME;
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------
	
	/** 
	 * Return with the actual status of game.
	 */
	public GameStatus getGameStatus() {
		return gameStatus;
	}
}
