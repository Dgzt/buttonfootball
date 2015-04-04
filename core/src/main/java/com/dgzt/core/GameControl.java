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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.scoreboard.GoalBoard;
import com.dgzt.core.scoreboard.ScoreBoard;
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
		NOT_IN_GAME,
		PLAYER_IN_GAME,
		WAITING_AFTER_PLAYER, 
		OPPONENT_IN_GAME, 
		WAITING_AFTER_OPPONENT,
		BALL_LEAVED_MAP_BY_PLAYER,
		BALL_LEAVED_MAP_BY_OPPONENT
	};
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The main window. */
	private final MainWindow mainWindow;
	
	/** The scoreboard. */
	private final ScoreBoard scoreBoard;
	
	/** The table. */
	private final Table table;
	
	/** The timer of the ball area. */
	private final Timer ballAreaTimer;
	
	/** The settings. */
	private final Settings settings;
	
	/** The bot. */
	private final Bot bot;
	
	/** The actual status of the game. */
	private GameStatus gameStatus;
	
	/** The last ball coordinate when leaved the map.
	 * If the ball is on the map this variable is null.*/
	private Vector2 ballLeavedMapCoordinate;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param mainWindow - The main window.
	 * @param scoreBoard - The score board.
	 * @param table - The table.
	 * @param settings - The settings.
	 */
	public GameControl(final MainWindow mainWindow, final ScoreBoard scoreBoard, final Table table, final Settings settings){
		Gdx.app.log(GameControl.class.getName() + ".init", "settings: " + settings);
		
		this.mainWindow = mainWindow;
		this.scoreBoard = scoreBoard;
		this.table = table;
		this.settings = settings;
		this.bot = new Bot(table.getOpponentButtons(), table.getBall());
		
		// Add the buttons to table.
		table.moveButtonsToLeftPartOfMap(Player.PLAYER);
		table.moveButtonsToRightPartOfMap(Player.BOT);
		
		scoreBoard.getHalfTimeBoard().setHalfTime(1);
		
		scoreBoard.getTimeBoard().setHalfTime(settings.getHalfTime());
		scoreBoard.getTimeBoard().start(this);

		setFirstStep();
		
		ballAreaTimer = new Timer();
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * New opponent goal.
	 */
	public void opponentGoal(){
		final GoalBoard playerGoalBoard = scoreBoard.getPlayerGoalBoard();
		playerGoalBoard.setNumber(playerGoalBoard.getNumber() + 1);
	}
	
	/**
	 * New player goal.
	 */
	public void playerGoal(){
		final GoalBoard opponentGoalBoard = scoreBoard.getOpponentGoalBoard();
		opponentGoalBoard.setNumber(opponentGoalBoard.getNumber() + 1);
	}
	
	/**
	 * The all button is stopped event.
	 */
	public void allButtonIsStopped(){
		Gdx.app.log(GameControl.class.getName() + ".allButtonIsStopped()", "");

		if(gameStatus != GameStatus.NOT_IN_GAME){
			
			if(gameStatus == GameStatus.BALL_LEAVED_MAP_BY_PLAYER || gameStatus == GameStatus.BALL_LEAVED_MAP_BY_OPPONENT){
				Gdx.app.log(GameControl.class.getName() + ".allButtonIsStopped()", "The ball leaved map.");
			}else{
				if(ballLeavedMapCoordinate != null){
					moveBallToBorderOfMap();
					ballLeavedMapCoordinate = null;
					gameStatus = (gameStatus == GameStatus.WAITING_AFTER_PLAYER) ? GameStatus.BALL_LEAVED_MAP_BY_PLAYER : GameStatus.BALL_LEAVED_MAP_BY_OPPONENT;
				}else{
					showBallArea();
				}
			}
		}
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
	
	/**
	 * End the half time.
	 */
	public void endHalfTime(){
		scoreBoard.getTimeBoard().stop();
		ballAreaTimer.stop();
		gameStatus = GameStatus.NOT_IN_GAME;
		
		if(scoreBoard.getHalfTimeBoard().getHalfTime() == 1){
			Gdx.app.log(GameControl.class.getName() + ".endHalfTime", "End half time");
			scoreBoard.getHalfTimeBoard().setHalfTime(2);
			
			table.moveButtonsToLeftPartOfMap(Player.BOT);
			table.moveButtonsToRightPartOfMap(Player.PLAYER);
			
			settings.setFirstStep( settings.getFirstStep() == Player.PLAYER ? Player.BOT : Player.PLAYER );
			setFirstStep();
			
			scoreBoard.getTimeBoard().start(this);
		}else{
			Gdx.app.log(GameControl.class.getName() + ".endHalfTime", "Game end.");
		}
	}
	
	/**
	 * The ball leaved map.
	 */
	public void ballLeavedMap(){
		if(ballLeavedMapCoordinate == null){
			ballLeavedMapCoordinate = table.getBall().getBox2DPosition().cpy();
		}
	}

	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Set the first step.
	 */
	private void setFirstStep(){
		if(settings.getFirstStep().equals(Player.PLAYER)){
			// The player step first.
			this.gameStatus = GameStatus.PLAYER_IN_GAME;
		}else{ 
			// The bot step first.
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
	
	/**
	 * After when end of the player's ball area.
	 */
	private void afterPlayerBallArea(){
		Gdx.app.log(GameControl.class.getName() + ".afterPlayerBallArea()", "");
		
		final List<Button> playerButtons = table.getPlayerButtons();
		
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
		
		final List<Button> opponentButtons = table.getOpponentButtons();
		
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
		final Ball ball = table.getBall();
		
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
	
	/**
	 * Show the area of ball.
	 */
	private void showBallArea(){
		if(gameStatus == GameStatus.WAITING_AFTER_PLAYER){
			mainWindow.showBallArea(Button.PLAYER_COLOR);
		}else if(gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
			mainWindow.showBallArea(Button.OPPONENT_COLOR);
		}
		
		ballAreaTimer.scheduleTask(new Task(){

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
	 * Move the ball to the border of map.
	 */
	private void moveBallToBorderOfMap(){
		final Map map = table.getMap();
		Vector2 newBallPos = ballLeavedMapCoordinate.cpy();
		
		if(ballLeavedMapCoordinate.y < map.getBox2DY()){
			newBallPos.y = map.getBox2DY();
		}else if(ballLeavedMapCoordinate.y > map.getBox2DY() + Map.HEIGHT){
			newBallPos.y = map.getBox2DY() + Map.HEIGHT;
		}
		
		table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
	}
	
}
