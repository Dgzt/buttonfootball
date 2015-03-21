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
import com.dgzt.core.setting.Settings;
import com.dgzt.core.setting.StepMode;
import com.dgzt.core.util.MathUtil;

/**
 * The game control.
 * 
 * @author Dgzt
 */
public final class GameControl {
	
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
	
	/** The settings. */
	private final Settings settings;
	
	/** The bot. */
	private final Bot bot;
	
	/** The actual status of the game. */
	private GameStatus gameStatus;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param mainWindow - The main window.
	 * @param settings - The settings.
	 */
	public GameControl(final MainWindow mainWindow, final Settings settings){
		Gdx.app.log(GameControl.class.getName() + ".init", "settings: " + settings);
		
		this.mainWindow = mainWindow;
		this.settings = settings;
		this.bot = new Bot(this);
		
		if(settings.getStepMode().equals(StepMode.NORMAL) || settings.getStepMode().equals(StepMode.ALWAYS_PLAYER)){
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
			
		}, settings.getBallAreaSec());
	}
	
	/** 
	 * The player stepped 
	 */
	public void playerStepped(){
		gameStatus = GameStatus.WAITING_AFTER_PLAYER;
	}
	
	public void opponentStepepd(){
		gameStatus = GameStatus.WAITING_AFTER_OPPONENT;
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
		
		if(settings.getStepMode().equals(StepMode.ALWAYS_PLAYER) || isActualPlayerStepAgain(playerButtons)){
			gameStatus = GameStatus.PLAYER_IN_GAME;
		}else{
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
		
		if(settings.getStepMode().equals(StepMode.ALWAYS_BOT) || isActualPlayerStepAgain(opponentButtons)){
			gameStatus = GameStatus.OPPONENT_IN_GAME;
			bot.step();
			opponentStepepd();
		}else{
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
		if(lowestDistance <= BallArea.RADIUS + Button.RADIUS){
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
