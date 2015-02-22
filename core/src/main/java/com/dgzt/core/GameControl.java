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

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.scoreboard.GoalBoard;
import com.dgzt.core.util.MathUtil;

/**
 * The game control.
 * 
 * @author Dgzt
 */
public final class GameControl {

	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The ball area's waiting time. */
	private static final int WAITING_AREA_SEC = 5;
	
	// --------------------------------------------------
	// ~ Private enums
	// --------------------------------------------------
	
	/** The game statuses. */
	private enum GameStatus{ PLAYER_IN_GAME, WAITING_AFTER_PLAYER, OPPONENT_IN_GAME };
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The main window. */
	private final MainWindow mainWindow;
	
	/** The actual status of the game. */
	private GameStatus gameStatus;
	
	/** Number of steps when the player stepped in a line. */
	private short stepNum;
	
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
		this.stepNum = 0;
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

		mainWindow.showBallArea(Button.PLAYER_COLOR);
		
		Timer.schedule(new Task(){

			@Override
			public void run() {
				afterPlayerBallArea();
			}
			
		}, WAITING_AREA_SEC);
	}
	
	/** 
	 * The player stepped 
	 */
	public void playerStepped(){
		gameStatus = GameStatus.WAITING_AFTER_PLAYER;
		++stepNum;
	}
	
	/** 
	 * Return true when the player step now.
	 */
	public boolean isPlayerStep(){
		return gameStatus == GameStatus.PLAYER_IN_GAME;
	}

	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * After when end of the player's ball area.
	 */
	private void afterPlayerBallArea(){
		Gdx.app.log(GameControl.class.getName() + ".afterPlayerBallArea()", "");
		
		final List<Button> playerButtons = mainWindow.getTable().getPlayerButtons();
		
		if(isActualPlayerStepAgain(playerButtons)){
			gameStatus = GameStatus.PLAYER_IN_GAME;
		}else{
			stepNum = 0;
			gameStatus = GameStatus.OPPONENT_IN_GAME;
		}
	}
	
	/**
	 * Return true when the actual player step again, false when the next player step.
	 * 
	 * @param buttons - The actual player's buttons.
	 */
	private boolean isActualPlayerStepAgain(final List<Button> buttons){
		final Ball ball = mainWindow.getTable().getBall();
		
		double lowestDistance = Double.MAX_VALUE;
		for(final Button playerButton : buttons){
			final double actualDistance = MathUtil.distance(ball.getBox2DX(), ball.getBox2DY(), playerButton.getBox2DX(), playerButton.getBox2DY());
			
			if(actualDistance < lowestDistance){
				lowestDistance = actualDistance;
			}
		}
		
		Gdx.app.log(GameControl.class.getName() + ".isActualPlayerStepAgain()", "lowestDistance: " + String.valueOf(lowestDistance));
		if(lowestDistance <= BallArea.RADIUS + Button.RADIUS && stepNum < 2){
			Gdx.app.log(GameControl.class.getName() + ".isActualPlayerStepAgain()", "Actual player step again");
			return true;
		}else{
			Gdx.app.log(GameControl.class.getName() + ".isActualPlayerStepAgain()", "Next player step");
			return false;
		}
	}
}
