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
import com.dgzt.core.type.StepType;
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
	
	/** The status of the game. */
	private enum GameStatus{ 
		PLAYER_IN_GAME, 
		WAITING_AFTER_PLAYER, 
		OPPONENT_IN_GAME, 
		WAITING_AFTER_OPPONENT 
	};
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The main window. */
	private final MainWindow mainWindow;
	
	/** The bot. */
	private final Bot bot;
	
	/** The actual status of the game. */
	private GameStatus gameStatus;
	
	/** The type of the step process. */
	private final StepType stepType;
	
	/** Number of steps when the player stepped in a line. */
	private short stepNum;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param mainWindow - The main window.
	 * @param stepType - The type of step process.
	 */
	public GameControl(final MainWindow mainWindow, final StepType stepType){
		Gdx.app.log(GameControl.class.getName() + ".init", "stepType: " + stepType);
		
		this.mainWindow = mainWindow;
		this.stepType = stepType;
		this.bot = new Bot(this);
		
		this.stepNum = 0;
		
		if(stepType.equals(StepType.NORMAL) || stepType.equals(StepType.ALWAYS_PLAYER)){
			this.gameStatus = GameStatus.PLAYER_IN_GAME;
		}else{
			this.gameStatus = GameStatus.OPPONENT_IN_GAME;
			Timer.schedule(new Task(){

				@Override
				public void run() {
					bot.step();
					opponentStepepd();
				}
				
			}, 1);
		}
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

		if(gameStatus == GameStatus.WAITING_AFTER_PLAYER){
			mainWindow.showBallArea(Button.PLAYER_COLOR);
		}else if(gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
			mainWindow.showBallArea(Button.OPPONENT_COLOR);
		}
		
		Timer.schedule(new Task(){

			@Override
			public void run() {
				mainWindow.hideBallArea();
				
				if(gameStatus == GameStatus.WAITING_AFTER_PLAYER){
					afterPlayerBallArea();
				}else if(gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
					afterOpponentBallArea();
				}
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
	
	public void opponentStepepd(){
		gameStatus = GameStatus.WAITING_AFTER_OPPONENT;
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
		
		if(stepType.equals(StepType.ALWAYS_PLAYER) || isActualPlayerStepAgain(playerButtons)){
			gameStatus = GameStatus.PLAYER_IN_GAME;
		}else{
			stepNum = 0;
			gameStatus = GameStatus.OPPONENT_IN_GAME;
			bot.step();
			opponentStepepd();
		}
	}
	
	/**
	 * After when end of the opponent's ball area.
	 */
	private void afterOpponentBallArea(){
		Gdx.app.log(GameControl.class.getName() + ".afterOpponentBallArea()", "");
		
		final List<Button> opponentButtons = mainWindow.getTable().getOpponentButtons();
		
		if(stepType.equals(StepType.ALWAYS_BOT) || isActualPlayerStepAgain(opponentButtons)){
			gameStatus = GameStatus.OPPONENT_IN_GAME;
			bot.step();
			opponentStepepd();
		}else{
			stepNum = 0;
			gameStatus = GameStatus.PLAYER_IN_GAME;
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

	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------
	
	/**
	 * Return with the main window.
	 */
	public MainWindow getMainWindow() {
		return mainWindow;
	}
	
}
