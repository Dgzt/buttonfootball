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
		BALL_LEAVED_MAP_BY_OPPONENT,
		PLAYER_MOVE_ONE_BUTTON,
		OPPONENT_MOVE_ONE_BUTTON,
		PLAYER_GOAL,
		OPPONENT_GOAL
	};
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The game window. */
	private final GameWindow gameWindow;
	
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
	
	/** The mooving button. If the player isn't move button then this variable is null. */
	private Button moovingButton;
	
	/** True when the game paused. */
	private boolean gamePaused;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param gameWindow - The game window.
	 * @param scoreBoard - The score board.
	 * @param table - The table.
	 * @param settings - The settings.
	 */
	public GameControl(final GameWindow gameWindow, final ScoreBoard scoreBoard, final Table table, final Settings settings){
		Gdx.app.log(GameControl.class.getName() + ".init", "settings: " + settings);
		
		this.gameWindow = gameWindow;
		this.scoreBoard = scoreBoard;
		this.table = table;
		this.settings = settings;
		this.bot = new Bot(table.getOpponentButtons(), table.getBall());
		gameStatus = GameStatus.NOT_IN_GAME;
		this.gamePaused = true;
		
		ballAreaTimer = new Timer();
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Start the game.
	 */
	public void startGame(){
		// Add the buttons to table.
		table.moveButtonsToLeftPartOfMap(Player.PLAYER);
		table.moveButtonsToRightPartOfMap(Player.BOT);
		
		// Show the buttons
		table.setVisibleButtons(true);
		
		// Add the ball to the table.
		table.moveBallToCenter();
		
		scoreBoard.getHalfTimeBoard().setHalfTimeType(HalfTimeType.FIRST_HALF);
		
		scoreBoard.getTimeBoard().setHalfTime(settings.getHalfTime());
		scoreBoard.getTimeBoard().start(this);

		gamePaused = false;
		setFirstStep();
	}
	
	/**
	 * Goal in left gate event.
	 */
	public void leftGateGoalEvent(){
		if(scoreBoard.getHalfTimeBoard().getHalfTimeType() == HalfTimeType.FIRST_HALF){
			playerGoalEvent();
		}else{
			opponentGoalEvent();
		}
	}

	/**
	 * Goal in right gate event.
	 */
	public void rightGateGoalEvent(){
		if(scoreBoard.getHalfTimeBoard().getHalfTimeType() == HalfTimeType.FIRST_HALF){
			opponentGoalEvent();
		}else{
			playerGoalEvent();
		}
	}
	
	/**
	 * The all button stopped event.
	 */
	public void allButtonIsStoppedEvent(){
		Gdx.app.log(GameControl.class.getName() + ".allButtonIsStopped()", "");

		if(gameStatus != GameStatus.NOT_IN_GAME){
			
			if(gameStatus == GameStatus.WAITING_AFTER_PLAYER || gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
				if(ballLeavedMapCoordinate != null){
					moveBallToBorderOfMap();
					ballLeavedMapCoordinate = null;
					gameStatus = (gameStatus == GameStatus.WAITING_AFTER_PLAYER) ? GameStatus.BALL_LEAVED_MAP_BY_PLAYER : GameStatus.BALL_LEAVED_MAP_BY_OPPONENT;
				}else{
					showBallArea();
				}
			}else if(gameStatus == GameStatus.BALL_LEAVED_MAP_BY_PLAYER || gameStatus == GameStatus.BALL_LEAVED_MAP_BY_OPPONENT){
				Gdx.app.log(GameControl.class.getName() + ".allButtonIsStopped()", "The ball leaved map.");
				if(gameStatus == GameStatus.BALL_LEAVED_MAP_BY_OPPONENT || settings.getStepMode() == StepMode.ALWAYS_PLAYER){
					gameStatus = GameStatus.PLAYER_MOVE_ONE_BUTTON;
				}else{
					gameStatus = GameStatus.OPPONENT_MOVE_ONE_BUTTON;
					throw new RuntimeException("The opponent move button to the ball.");
				}
			}else if(gameStatus == GameStatus.OPPONENT_GOAL || gameStatus == GameStatus.PLAYER_GOAL){
				goal();
			}
		}
	}
	
	/** 
	 * The player stepped.
	 */
	public void playerStepped(){
		gameStatus = GameStatus.WAITING_AFTER_PLAYER;
	}
	
	/**
	 * The opponent stepped.
	 */
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
	 * Return true when the player can move button.
	 */
	public boolean isPlayerMoveButton(){
		return gameStatus == GameStatus.PLAYER_MOVE_ONE_BUTTON;	
	}
	
	/**
	 * Return true when the player is in the game.
	 */
	public boolean isInGame(){
		return gameStatus != GameStatus.NOT_IN_GAME;
	}
	
	/**
	 * End the half time.
	 */
	public void endHalfTime(){
		scoreBoard.getTimeBoard().stop();
		ballAreaTimer.stop();
		gameStatus = GameStatus.NOT_IN_GAME;
		
		if(scoreBoard.getHalfTimeBoard().getHalfTimeType() == HalfTimeType.FIRST_HALF){
			Gdx.app.log(GameControl.class.getName() + ".endHalfTime", "End half time");
			scoreBoard.getHalfTimeBoard().setHalfTimeType(HalfTimeType.SECOND_HALF);
			
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
	
	/**
	 * Select a button to move to the ball.
	 * 
	 * @param x - The x coordinate value to select button.
	 * @param y - The y coordinate value to select button.
	 */
	public void selectMoovingButton(final float x, final float y){
		for(final Button button : table.getPlayerButtons()){
			if(button.contains(x, y)){
				moovingButton = button;
				Gdx.app.log(GameControl.class.getName() + ".selectMoovingButton()", "Selected button.");
				break;
			}
		}
		
		if(moovingButton == null){
			Gdx.app.log(GameControl.class.getName() + ".selectMoovingButton()", "Not selected button.");
			if(gameStatus == GameStatus.BALL_LEAVED_MAP_BY_OPPONENT || settings.getStepMode() == StepMode.ALWAYS_PLAYER){
				gameStatus = GameStatus.PLAYER_IN_GAME;
			}else{
				throw new RuntimeException("Not implemented when bot start after the ball leaved the map.");
			}
		}
	}
	
	/**
	 * Move the selected button to the given position.
	 * 
	 * @param x - The new x coordinate value.
	 * @param y - The new y coordinate value.
	 */
	public void moveSelectedButton(final float x, final float y){
		// The new position is ok
		boolean ok = true;
		
		// Check the player's button positions
		for(final Button playerButton : table.getPlayerButtons()){
			if(moovingButton != playerButton && MathUtil.distance(playerButton.getX(), playerButton.getY(), x, y) < table.getScale() * (Button.RADIUS * 2)){
				ok = false;
			}
		}
		
		// Check the opponent's button positions
		if(ok){
			for(final Button opponentButton : table.getOpponentButtons()){
				if(MathUtil.distance(opponentButton.getX(), opponentButton.getY(), x, y) < table.getScale() * (Button.RADIUS * 2)){
					ok = false;
				}
			}
		}
		
		// Check the ball position
		if(ok && MathUtil.distance(table.getBall().getX(), table.getBall().getY(), x, y) < table.getScale() * (Button.RADIUS + Ball.RADIUS)){
			ok = false;
		}
		
		// If the button will not on other button or ball then move to the new position
		if(ok){
			moovingButton.setPosition(x, y);
		}
	}
	
	/**
	 * End move the selected button.
	 */
	public void endMoveSelectedButton(){
		moovingButton = null;
		
		if(gameStatus == GameStatus.PLAYER_MOVE_ONE_BUTTON){
			Gdx.app.log(GameControl.class.getName() + ".endMoveSelectedButton()", "Player in game.");
			gameStatus = GameStatus.PLAYER_IN_GAME;
		}else if(gameStatus == GameStatus.OPPONENT_MOVE_ONE_BUTTON){
			Gdx.app.log(GameControl.class.getName() + ".endMoveSelectedButton()", "Bot in game.");
			gameStatus = GameStatus.OPPONENT_IN_GAME;
			bot.step();
			opponentStepepd();
		}
	}
	
	/**
	 * Pause the game.
	 */
	public void pauseGame(){
		Gdx.app.log(getClass().getName() + ".pauseGame", "init");
		if(gameStatus == GameStatus.WAITING_AFTER_PLAYER || gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
			ballAreaTimer.stop();
		}
		scoreBoard.getTimeBoard().stop();
		gamePaused = true;
	}
	
	/**
	 * Resume the game.
	 */
	public void resumeGame(){
		Gdx.app.log(getClass().getName() + ".resumeGame", "init");
		if(gameStatus == GameStatus.WAITING_AFTER_PLAYER || gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
			ballAreaTimer.start();
		}
		scoreBoard.getTimeBoard().resume();
		gamePaused = false;
	}
	
	/**
	 * Quit the current game.
	 */
	public void quitGame(){
		Gdx.app.log(getClass().getName() + ".quitGame", "init");
		
		//Set the status
		gameStatus = GameStatus.NOT_IN_GAME;
		
		ballAreaTimer.clear();
		// If visible the ball area then hide it
		gameWindow.hideBallArea();
		
		// Clear the half time board
		scoreBoard.getHalfTimeBoard().setHalfTimeType(HalfTimeType.NOT_IN_GAME);
		
		// Clear the time board
		scoreBoard.getTimeBoard().clear();
		
		// Clear the player's and opponent's goal board
		scoreBoard.getPlayerGoalBoard().setNumber(0);
		scoreBoard.getOpponentGoalBoard().setNumber(0);
		
		// Hide the buttons
		table.setVisibleButtons(false);
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Player goal event.
	 */
	private void playerGoalEvent(){
		Gdx.app.log(GameControl.class.getName() + ".playerGoalEvent", "init");
		
		gameStatus = GameStatus.PLAYER_GOAL;
	}
	
	private void opponentGoalEvent(){
		Gdx.app.log(GameControl.class.getName() + ".opponentGoalEvent", "init");
		
		gameStatus = GameStatus.OPPONENT_GOAL;		
	}
	
	/**
	 * The player in game.
	 */
	private void playerInGame(){
		Gdx.app.log(GameControl.class.getName() + ".playerInGame", "init");
		
		gameStatus = GameStatus.PLAYER_IN_GAME;
	}
	
	/**
	 * The opponent in game.
	 */
	private void opponentInGame(){
		Gdx.app.log(GameControl.class.getName() + ".opponentInGame", "init");
		
		gameStatus = GameStatus.OPPONENT_IN_GAME;
		bot.step();
		opponentStepepd();
	}
	
	/**
	 * Set the first step.
	 */
	private void setFirstStep(){
		// The player step first.
		if(settings.getFirstStep().equals(Player.PLAYER)){
			
			playerInGame();
		}
		// The bot step first.
		else{
			gameStatus = GameStatus.OPPONENT_IN_GAME;
			
			Timer.schedule(new Task(){
				
				@Override
				public void run() {
					opponentInGame();
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
			playerInGame();
		}else{
			opponentInGame();
		}
	}
	
	/**
	 * After when end of the opponent's ball area.
	 */
	private void afterOpponentBallArea(){
		Gdx.app.log(GameControl.class.getName() + ".afterOpponentBallArea()", "");
		
		final List<Button> opponentButtons = table.getOpponentButtons();
		
		if(settings.getStepMode().equals(StepMode.ALWAYS_BOT) || isActualPlayerStepAgain(opponentButtons)){
			opponentInGame();
		}else{
			playerInGame();
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
			gameWindow.showBallArea(Button.PLAYER_COLOR);
		}else if(gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
			gameWindow.showBallArea(Button.OPPONENT_COLOR);
		}
		
		ballAreaTimer.scheduleTask(new Task(){

			@Override
			public void run() {
				gameWindow.hideBallArea();
				
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
		
		// Move to the top left corner
		if(ballLeavedMapCoordinate.x < map.getBox2DX() && ballLeavedMapCoordinate.y < (map.getBox2DY() + Map.HEIGHT / 2)){
			newBallPos.set(map.getBox2DX(), map.getBox2DY());
		}else
		// Move to the top line
		if(ballLeavedMapCoordinate.x >= map.getBox2DX() && ballLeavedMapCoordinate.x <= (map.getBox2DX() + Map.WIDTH) && ballLeavedMapCoordinate.y < map.getBox2DY()){
			newBallPos.y = map.getBox2DY();
		}else
		// Move to the top right corner
		if(ballLeavedMapCoordinate.x > (map.getBox2DX() + Map.WIDTH) && ballLeavedMapCoordinate.y < (map.getBox2DY() + Map.HEIGHT / 2)){
			newBallPos.set(map.getBox2DX() + Map.WIDTH, map.getBox2DY());
		}else
		// Move to the bottom right corner
		if(ballLeavedMapCoordinate.x > (map.getBox2DX() + Map.WIDTH) && ballLeavedMapCoordinate.y > (map.getBox2DY() + Map.HEIGHT / 2)){
			newBallPos.set(map.getBox2DX() + Map.WIDTH, map.getBox2DY() + Map.HEIGHT);
		}else
		// Move to the bottom line
		if(ballLeavedMapCoordinate.x >= map.getBox2DX() && ballLeavedMapCoordinate.x <= map.getBox2DX() + Map.WIDTH && ballLeavedMapCoordinate.y > map.getBox2DY() + Map.HEIGHT){
			newBallPos.y = map.getBox2DY() + Map.HEIGHT;
		}else
		// Move to the bottom left corner
		if(ballLeavedMapCoordinate.x < map.getBox2DX() && ballLeavedMapCoordinate.y > (map.getBox2DY() + Map.HEIGHT / 2)){
			newBallPos.set(map.getBox2DX(), map.getBox2DY() + Map.HEIGHT);
		}
			
		table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
	}
	
	/**
	 * The player or opponent goal. Set the goalboard, buttons and ball.
	 */
	private void goal(){
		// Move the buttons to the original position
		if(scoreBoard.getHalfTimeBoard().getHalfTimeType() == HalfTimeType.FIRST_HALF){
			table.moveButtonsToLeftPartOfMap(Player.PLAYER);
			table.moveButtonsToRightPartOfMap(Player.BOT);
		}else{
			table.moveButtonsToLeftPartOfMap(Player.BOT);
			table.moveButtonsToRightPartOfMap(Player.PLAYER);
		}
		
		// Move ball to the original position
		table.moveBallToCenter();
		
		// Opponent goal
		if(gameStatus == GameStatus.OPPONENT_GOAL){
			opponentGoal();
		}
		// Player goal
		else if(gameStatus == GameStatus.PLAYER_GOAL){
			playerGoal();
		}
	}
	
	/**
	 * Opponent goal. Set the opponent's goal board and the game status.
	 */
	private void opponentGoal(){
		// Set the opponent's goal board
		final GoalBoard goalBoard = scoreBoard.getOpponentGoalBoard();
		goalBoard.setNumber(goalBoard.getNumber() + 1);
		
		// Set who will step
		if(settings.getStepMode() != StepMode.ALWAYS_BOT){
			playerInGame();
		}else{
			opponentInGame();
		}
	}
	
	/**
	 * Player goal. Set the player's goal board and the game status.
	 */
	private void playerGoal(){
		// Set the player's goal board
		final GoalBoard goalBoard = scoreBoard.getPlayerGoalBoard();
		goalBoard.setNumber(goalBoard.getNumber() + 1);
		
		// Set who wll step
		if(settings.getStepMode() != StepMode.ALWAYS_PLAYER){
			opponentInGame();
		}else{
			playerInGame();
		}
	}
	
	// --------------------------------------------------
	// ~ Setter / Getter methods
	// --------------------------------------------------
	
	/**
	 * Return true when the game is paused.
	 */
	public boolean isGamePaused(){
		return gamePaused;
	}
	
}
