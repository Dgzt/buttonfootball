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
import com.dgzt.core.exception.IllegalGameStatusException;
import com.dgzt.core.scoreboard.GoalBoard;
import com.dgzt.core.scoreboard.ScoreBoard;
import com.dgzt.core.setting.Settings;
import com.dgzt.core.setting.StepMode;
import com.dgzt.core.util.Box2DDataUtil;
import com.dgzt.core.util.Box2DUtil;
import com.dgzt.core.util.MathUtil;

/**
 * The game control.
 * 
 * @author Dgzt
 */
public final class GameControl {
	
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
	
	/** The event listener of box2D */
	private final EventListener eventListener;
	
	/** The bot. */
	private final Bot bot;
	
	/** The actual status of the game. */
	private GameStatus gameStatus;
	
	/** The last ball coordinate when leaved the map.
	 * If the ball is on the map this variable is null.*/
	private Vector2 ballLeavedMapCoordinate;
	
	/** The mooving button. If the player isn't move button then this variable is null. */
	private Button moovingButton;
	
	/** The button which contact with ball the last time. */
	private Button buttonContactBallLastTime;
	
	/** The fault position at the current step. When wasn't fault then this variable is null. */
	private Vector2 faultBox2DPosition;
	
	/** True when the game paused. */
	private boolean gamePaused;
	
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
	 * @param eventListener - The box2D event listener.
	 */
	public GameControl(final MainWindow mainWindow, final ScoreBoard scoreBoard, final Table table, final Settings settings, final EventListener eventListener){
		Gdx.app.log(GameControl.class.getName() + ".init", "settings: " + settings);
		
		this.mainWindow = mainWindow;
		this.scoreBoard = scoreBoard;
		this.table = table;
		this.settings = settings;
		this.eventListener = eventListener;
		this.bot = createBot(table);
		gameStatus = GameStatus.NOT_IN_GAME;
		buttonContactBallLastTime = null;
		faultBox2DPosition = null;
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
		
		scoreBoard.getPlayerTimeLeftBoard().setMaxTimeLeft(settings.getTimeLeftSec());
		scoreBoard.getOpponentTimeLeftBoard().setMaxTimeLeft(settings.getTimeLeftSec());
		
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
			opponentGoalEvent();
		}else{
			playerGoalEvent();
		}
	}

	/**
	 * Goal in right gate event.
	 */
	public void rightGateGoalEvent(){
		if(scoreBoard.getHalfTimeBoard().getHalfTimeType() == HalfTimeType.FIRST_HALF){
			playerGoalEvent();
		}else{
			opponentGoalEvent();
		}
	}
	
	/**
	 * The all button stopped event.
	 */
	public void allButtonIsStoppedEvent(){
		Gdx.app.log(GameControl.class.getName() + ".allButtonIsStopped()", "");

		if(gameStatus != GameStatus.NOT_IN_GAME){
			
			if(gameStatus == GameStatus.WAITING_AFTER_PLAYER || gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
				if(faultBox2DPosition != null){
					fault();
				}else if(ballLeavedMapCoordinate != null){
					ballLeavedMap();
				}else{
					showBallArea();
				}
			}else if(gameStatus == GameStatus.OPPONENT_GOAL || gameStatus == GameStatus.PLAYER_GOAL){
				goal();
			}
		}
		
		// Clear variables
		faultBox2DPosition = null;
		buttonContactBallLastTime = null;
	}
	
	/** 
	 * The player stepped.
	 */
	public void playerStepped(){
		gameStatus = GameStatus.WAITING_AFTER_PLAYER;
		scoreBoard.getPlayerTimeLeftBoard().clear();
	}
	
	/**
	 * The opponent stepped.
	 */
	public void opponentStepped(){
		gameStatus = GameStatus.WAITING_AFTER_OPPONENT;
		scoreBoard.getOpponentTimeLeftBoard().clear();
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
		return gameStatus == GameStatus.PLAYER_MOVE_ONE_BUTTON || gameStatus == GameStatus.PLAYER_MOVE_SOME_BUTTON;
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
		
		// Clear all movements
		clearAllMovements();
		
		// Hide the arrow if it is visible
		mainWindow.getGameWindow().hideArrow();
		
		if(scoreBoard.getHalfTimeBoard().getHalfTimeType() == HalfTimeType.FIRST_HALF){
			Gdx.app.log(GameControl.class.getName() + ".endHalfTime", "End half time");
			
			scoreBoard.getHalfTimeBoard().setHalfTimeType(HalfTimeType.SECOND_HALF);
			
			// Move buttons to the new position
			table.moveButtonsToLeftPartOfMap(Player.BOT);
			table.moveButtonsToRightPartOfMap(Player.PLAYER);
			
			// Move ball to the center
			table.moveBallToCenter();
			
			settings.setFirstStep( settings.getFirstStep() == Player.PLAYER ? Player.BOT : Player.PLAYER );
			setFirstStep();
			
			scoreBoard.getTimeBoard().start(this);
		}else{
			Gdx.app.log(GameControl.class.getName() + ".endHalfTime", "Game end.");
			
			mainWindow.showEndGameMenuWindow(scoreBoard.getPlayerGoalBoard().getNumber(), scoreBoard.getOpponentGoalBoard().getNumber());
		}
	}
	
	/**
	 * The ball leave map event.
	 */
	public void ballLeaveMapEvent(){
		Gdx.app.log(GameControl.class.getName() + ".ballLeaveMapEvent()", "init");
		if(ballLeavedMapCoordinate == null){
			ballLeavedMapCoordinate = table.getBall().getBox2DPosition().cpy();
		}
	}
	
	/**
	 * The button contact with the ball.
	 * 
	 * @param button - The button.
	 */
	public void buttonContactBall(final Button button){
		Gdx.app.log(GameControl.class.getName() + ".ballContactButtonEvent(Button)", "init");
		
		buttonContactBallLastTime = button;
	}
	
	/**
	 * The new playing player's button contact with other player's button.
	 */
	public void buttonContactButton(final Button button1, final Button button2){
		Gdx.app.log(GameControl.class.getName() + ".buttonContactButton", "init");
		
		final Vector2 contactPoint = MathUtil.midPoint(button1.getBox2DPosition(), button2.getBox2DPosition());
		
		if(buttonContactBallLastTime == null && faultBox2DPosition == null && table.getMap().containsBox2DPosition(contactPoint)){
			faultBox2DPosition = contactPoint.cpy();
		}
	}
	
	/**
	 * End of the time left event.
	 */
	public void timeLeftEndEvent(){
		Gdx.app.log(getClass().getName() + ".timeLeftEndEvent()", "init");
		
		switch(gameStatus){
			case PLAYER_IN_GAME:
			case PLAYER_MOVE_ONE_BUTTON:
			case PLAYER_MOVE_SOME_BUTTON :
				playerTimeLeftEnd(); 
				break;
			case OPPONENT_IN_GAME:
			case OPPONENT_MOVE_ONE_BUTTON:
			case OPPONENT_MOVE_SOME_BUTTON:
				opponentTimeLeftEnd(); 
				break;
			default:
				throw new IllegalGameStatusException();
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
			scoreBoard.getPlayerTimeLeftBoard().clear();	
			playerInGame();
		}
	}
	
	/**
	 * Move the selected button to the given position.
	 * 
	 * @param position - The new position.
	 */
	public void moveSelectedButton(final Vector2 position){
		// The new position is ok
		boolean ok = true;
		
		if(moovingButton == null){
			ok = false;
		}
		
		// Check the border of table
		if(ok && !table.isButtonPositionOnTable(position)){
			ok = false;
		}
		
		// The x,y position in box2D world
		final Vector2 box2DPos = Box2DUtil.screenPositionToBox2DPosition(position, new Vector2(table.getX(), table.getY()), table.getScale());
		
		// Check the gates
		if(ok && (table.getLeftGate().isButtonPositionOnWall(box2DPos) || table.getRightGate().isButtonPositionOnWall(box2DPos))){
			ok = false;
		}
		
		// Check the player's button positions
		if(ok){
			for(final Button playerButton : table.getPlayerButtons()){
				if(moovingButton != playerButton && playerButton.getBox2DPosition().dst(box2DPos) < Button.RADIUS * 2){
					ok = false;
				}
			}
		}
		
		// Check the opponent's button positions
		if(ok){
			for(final Button opponentButton : table.getOpponentButtons()){
				if(opponentButton.getBox2DPosition().dst(box2DPos) < Button.RADIUS * 2){
					ok = false;
				}
			}
		}
		
		// Check the ball position
		if(ok && table.getBall().getBox2DPosition().dst(box2DPos) < Button.RADIUS + Ball.RADIUS){
			ok = false;
		}
		
		// If the button will not on other button or ball then move to the new position
		if(ok){
			moovingButton.setBox2DPosition(box2DPos.x, box2DPos.y);
		}
	}
	
	/**
	 * End move the selected button.
	 */
	public void endMoveSelectedButton(){
		Gdx.app.log(GameControl.class.getName() + ".endMoveSelectedButton()", "init");
		if(moovingButton != null){
			moovingButton = null;
		
			if(gameStatus == GameStatus.PLAYER_MOVE_ONE_BUTTON){
				Gdx.app.log(GameControl.class.getName() + ".endMoveSelectedButton()", "Player in game.");
				scoreBoard.getPlayerTimeLeftBoard().clear();
				playerInGame();
			}
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
		
		if(gameStatus == GameStatus.PLAYER_IN_GAME){
			scoreBoard.getPlayerTimeLeftBoard().stop();
		}else if(gameStatus == GameStatus.OPPONENT_IN_GAME){
			scoreBoard.getOpponentTimeLeftBoard().stop();
		}
		
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
		
		if(gameStatus == GameStatus.PLAYER_IN_GAME){
			scoreBoard.getPlayerTimeLeftBoard().resume();
		}else if(gameStatus == GameStatus.OPPONENT_IN_GAME){
			scoreBoard.getOpponentTimeLeftBoard().resume();
		}
		
		gamePaused = false;
	}
	
	/**
	 * Quit the current game.
	 */
	public void quitGame(){
		Gdx.app.log(getClass().getName() + ".quitGame", "init");
		
		// If quit the game before end
		if(gameStatus != GameStatus.NOT_IN_GAME){
			// Set the status
			gameStatus = GameStatus.NOT_IN_GAME;
			
			// Clear the movements
			clearAllMovements();
		}
		
		ballAreaTimer.clear();
		// If visible the ball area then hide it
		mainWindow.getGameWindow().hideBallArea();
		
		// Clear the half time board
		scoreBoard.getHalfTimeBoard().setHalfTimeType(HalfTimeType.NOT_IN_GAME);
		
		// Clear the time board
		scoreBoard.getTimeBoard().clear();
		
		// Clear the player's and opponent's goal board
		scoreBoard.getPlayerGoalBoard().setNumber(0);
		scoreBoard.getOpponentGoalBoard().setNumber(0);
		
		// Clear the time player's and opponent's time left board
		scoreBoard.getPlayerTimeLeftBoard().clear();
		scoreBoard.getOpponentTimeLeftBoard().clear();
		
		// Hide the buttons
		table.setVisibleButtons(false);
	}
	
	// --------------------------------------------------
	// ~ Protected methods
	// --------------------------------------------------
	
	/**
	 * After when end of the player's ball area.
	 */
	protected void afterPlayerBallArea(){
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
	protected void afterOpponentBallArea(){
		Gdx.app.log(GameControl.class.getName() + ".afterOpponentBallArea()", "");
		
		final List<Button> opponentButtons = table.getOpponentButtons();
		
		if(settings.getStepMode().equals(StepMode.ALWAYS_BOT) || isActualPlayerStepAgain(opponentButtons)){
			opponentInGame();
		}else{
			playerInGame();
		}
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Create a bot.
	 * 
	 * @param table - The table.
	 * @return A bot.
	 */
	private Bot createBot(final Table table) {
		return new Bot(table){

			@Override
			protected Player whoIsOnLeftSide() {
				return GameControl.this.whoIsOnLeftSide();
			}
			
		};
	}
	
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
		scoreBoard.getPlayerTimeLeftBoard().start(this);
	}
	
	/**
	 * The opponent in game.
	 */
	private void opponentInGame(){
		Gdx.app.log(GameControl.class.getName() + ".opponentInGame", "init");
		
		gameStatus = GameStatus.OPPONENT_IN_GAME;
		scoreBoard.getOpponentTimeLeftBoard().start(this);
		bot.step();
		opponentStepped();
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
	 * The fault method.
	 */
	private void fault(){
		Gdx.app.log(GameControl.class.getName() + ".fault", "init");
		
		if(isLeftPenaltyKick()){
			leftPenaltyKick();
		}else if(isRightPenaltyKick()){
			rightPenaltyKick();
		}else{
			table.create18CentimeterFreeSpace(faultBox2DPosition);
			table.getBall().setBox2DPosition(faultBox2DPosition.x, faultBox2DPosition.y);
			
			switch (gameStatus) {
				case WAITING_AFTER_PLAYER : 
					opponentMoveSomeButton();
					break;
				case WAITING_AFTER_OPPONENT :
					playerMoveSomeButton();
					break;
				default : 
					throw new IllegalGameStatusException();
			}
		}
	}
	
	/**
	 * Return true when is penalty kick on left side else false.
	 */
	private boolean isLeftPenaltyKick(){
		final boolean containsSector16 = table.getMap().containsLeftSector16Box2DPosition(faultBox2DPosition);
		final boolean isPlayerFaultOnLeftSide = gameStatus == GameStatus.WAITING_AFTER_PLAYER && whoIsOnLeftSide() == Player.PLAYER;
		final boolean isOpponentFaultOnLeftSide = gameStatus == GameStatus.WAITING_AFTER_OPPONENT && whoIsOnLeftSide() == Player.BOT;
		
		return containsSector16 && (isPlayerFaultOnLeftSide || isOpponentFaultOnLeftSide);
	}
	
	/**
	 * Return true when is penalty kick on right side else false.
	 */
	private boolean isRightPenaltyKick(){
		final boolean containsSector16 = table.getMap().containsRightSector16Box2DPosition(faultBox2DPosition);
		final boolean isOpponentFaultOnRightSide = gameStatus == GameStatus.WAITING_AFTER_OPPONENT && whoIsOnRightSide() == Player.BOT;
		final boolean isPlayerFaultOnRightSide = gameStatus == GameStatus.WAITING_AFTER_PLAYER && whoIsOnRightSide() == Player.PLAYER;
		
		return containsSector16 && (isOpponentFaultOnRightSide || isPlayerFaultOnRightSide);
	}

	/**
	 * The left penalty kick.
	 */
	private void leftPenaltyKick() {
		gameStatus = GameStatus.PENALTY_KICK;
		final Player whoIsOnLeftSide = whoIsOnLeftSide();
		
		table.createFreeSpaceForLeftPenaltyKick(whoIsOnLeftSide);
		
		table.moveGoalkeeperToLeftGate(whoIsOnLeftSide);
		
		table.moveBallToLeftPenaltyPosition();
		
		if(whoIsOnLeftSide == Player.PLAYER){
			opponentMoveOneButton();
		}else{
			playerMoveOneButton();
		}
	}
	
	/**
	 * The right penalty kick.
	 */
	private void rightPenaltyKick() {
		gameStatus = GameStatus.PENALTY_KICK;
		final Player whoIsOnRightSide = whoIsOnRightSide();
		
		table.createFreeSpaceForRightPenaltyKick(whoIsOnRightSide);
		
		table.moveGoalkeeperToRightGate(whoIsOnRightSide);
		
		table.moveBallToRightPenaltyPosition();
		
		if(whoIsOnRightSide == Player.BOT){
			playerMoveOneButton();
		}else{
			opponentMoveOneButton();
		}
	}
	
	/**
	 * Show the area of ball.
	 */
	private void showBallArea(){
		if(gameStatus == GameStatus.WAITING_AFTER_PLAYER){
			mainWindow.getGameWindow().showBallArea(Button.PLAYER_COLOR);
		}else if(gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
			mainWindow.getGameWindow().showBallArea(Button.OPPONENT_COLOR);
		}
		
		ballAreaTimer.scheduleTask(new Task(){

			@Override
			public void run() {
				mainWindow.getGameWindow().hideBallArea();
				
				if(gameStatus == GameStatus.WAITING_AFTER_PLAYER){
					afterPlayerBallArea();
				}else if(gameStatus == GameStatus.WAITING_AFTER_OPPONENT){
					afterOpponentBallArea();
				}
			}
			
		}, settings.getBallAreaSec());
		ballAreaTimer.start();
	}
	
	/**
	 * Ball leaved the map. Set the next event.
	 */
	private void ballLeavedMap(){
		Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "init");
		
		final float mapBox2DX = Box2DDataUtil.MAP_RECTANGLE.getX();
		final float mapBox2DY = Box2DDataUtil.MAP_RECTANGLE.getY();
		final float mapBox2DWidth = Box2DDataUtil.MAP_RECTANGLE.getWidth();
		final float mapBox2DHeight = Box2DDataUtil.MAP_RECTANGLE.getHeight();
		
		final Map map = table.getMap();
		final Vector2 newBallPos = ballLeavedMapCoordinate.cpy();
		
		if(ballLeavedMapCoordinate.x < mapBox2DX){
			// The ball leaved the map on the left side
			if(whoIsOnLeftSide() == Player.PLAYER){
				// The player is on the left side
				if(isOpponentButtonContactBallLastTime()){
					Gdx.app.log(getClass().getName() + ".ballLeavedMap()", "Goal kick on left side by player.");
					newBallPos.set(map.getLeftGoalKickBox2DPosition());
					
					table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
					playerMoveSomeButton();
				}else{ 
					// Corner kick from opponent
					if(ballLeavedMapCoordinate.y < (mapBox2DY + mapBox2DHeight / 2)){
						Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Corner kick on top left by opponent.");
						newBallPos.set(mapBox2DX, mapBox2DY);
					}else{
						Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Corner kick on bottom left by opponent.");
						newBallPos.set(mapBox2DX, mapBox2DY + mapBox2DHeight);
					}
					
					table.create18CentimeterFreeSpace(newBallPos);
					table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
					opponentMoveOneButton();
				}
			}else{
				// The opponent is on the left side
				if(isPlayerButtonContactBallLastTime()){
					Gdx.app.log(getClass().getName() + ".ballLeavedMap()", "Goal kick on left side by opponent.");
					newBallPos.set(map.getLeftGoalKickBox2DPosition());
					
					table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
					opponentMoveSomeButton();
				}else{
					if(ballLeavedMapCoordinate.y < (mapBox2DY + mapBox2DHeight / 2)){
						Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Corner kick on top left by player.");
						newBallPos.set(mapBox2DX, mapBox2DY);
					}else{
						Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Corner kick on bottom left by player.");
						newBallPos.set(mapBox2DX, mapBox2DY + mapBox2DHeight);
					}
					
					table.create18CentimeterFreeSpace(newBallPos);
					table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
					playerMoveOneButton();
				}
			}
		}else if(ballLeavedMapCoordinate.x > mapBox2DX + mapBox2DWidth){
			// The ball leaved the map on the right side
			if(whoIsOnRightSide() == Player.BOT){
				// The opponent is on the right side
				if(isPlayerButtonContactBallLastTime()){
					Gdx.app.log(getClass().getName() + ".ballLeavedMap()", "Goal kick on right side by opponent.");
					newBallPos.set(map.getRightGoalKickBox2DPosition());
					
					table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
					opponentMoveSomeButton();
				}else{
					if(ballLeavedMapCoordinate.y < (mapBox2DY + mapBox2DHeight / 2)){
						Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Corner kick on top right by player.");
						newBallPos.set(mapBox2DX + mapBox2DWidth, mapBox2DY);
					}else{
						Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Corner kick on bottom right by player.");
						newBallPos.set(mapBox2DX + mapBox2DWidth, mapBox2DY + mapBox2DHeight);
					}
					
					table.create18CentimeterFreeSpace(newBallPos);
					table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
					playerMoveOneButton();
				}
			}else{
				// The player in on the right side
				if(isOpponentButtonContactBallLastTime()){
					Gdx.app.log(getClass().getName() + ".ballLeavedMap()", "Goal kick on right side by player.");
					newBallPos.set(map.getRightGoalKickBox2DPosition());
					
					table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
					playerMoveSomeButton();
				}else{ 
					// Corner kick from opponent
					if(ballLeavedMapCoordinate.y < (mapBox2DY + mapBox2DHeight / 2)){
						Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Corner kick on top right by opponent.");
						newBallPos.set(mapBox2DX + mapBox2DWidth, mapBox2DY);
					}else{
						Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Corner kick on bottom right by opponent.");
						newBallPos.set(mapBox2DX + mapBox2DWidth, mapBox2DY + mapBox2DHeight);
					}
					
					table.create18CentimeterFreeSpace(newBallPos);
					table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
					opponentMoveOneButton();
				}
			}
		}else{
			// Throw in
			if(ballLeavedMapCoordinate.y < mapBox2DY){
				// The ball leaved the map on top side
				Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Throw in on the top side.");
	
				newBallPos.y = mapBox2DY;
			}else{
				// THe ball leaved the map on the bottom side
				Gdx.app.log(GameControl.class.getName() + ".ballLeavedMap()", "Throw in on the bottom side.");
	
				newBallPos.y = mapBox2DY + mapBox2DHeight;
			}
			
			table.getBall().setBox2DPosition(newBallPos.x, newBallPos.y);
			if(isOpponentButtonContactBallLastTime()){
				playerMoveOneButton();
			}else{
				opponentMoveOneButton();
			}
		}
	}
	
	/**
	 * The player move one button.
	 */
	private void playerMoveOneButton(){
		Gdx.app.log(getClass().getName() + ".playerMoveOneButton()", "init");
		
		if(settings.getStepMode() == StepMode.NORMAL || settings.getStepMode() == StepMode.ALWAYS_PLAYER){
			gameStatus = GameStatus.PLAYER_MOVE_ONE_BUTTON;
			scoreBoard.getPlayerTimeLeftBoard().start(this);
		}else{
			opponentMoveOneButton();
		}
	}
	
	/**
	 * The opponent move one button.
	 */
	private void opponentMoveOneButton(){
		Gdx.app.log(getClass().getName() + ".opponentMoveOneButton()", "init");
		
		if(settings.getStepMode() == StepMode.NORMAL || settings.getStepMode() == StepMode.ALWAYS_BOT){
			gameStatus = GameStatus.OPPONENT_MOVE_ONE_BUTTON;
			scoreBoard.getOpponentTimeLeftBoard().start(this);
			bot.moveOneButton();
			scoreBoard.getOpponentTimeLeftBoard().clear();
			opponentInGame();
		}else{
			playerMoveOneButton();
		}
	}
	
	/**
	 * The player move some button.
	 */
	private void playerMoveSomeButton(){
		Gdx.app.log(getClass().getName() + ".playerMoveSomeButton()", "init");
		
		if(settings.getStepMode() == StepMode.NORMAL || settings.getStepMode() == StepMode.ALWAYS_PLAYER){
			gameStatus = GameStatus.PLAYER_MOVE_SOME_BUTTON;
			scoreBoard.getPlayerTimeLeftBoard().start(this);
		}else{
			opponentMoveSomeButton();
		}
	}
	
	/**
	 * The opponent move some button.
	 */
	private void opponentMoveSomeButton(){
		Gdx.app.log(getClass().getName() + ".opponentMoveSomeButton()", "init");
		
		if(settings.getStepMode() == StepMode.NORMAL || settings.getStepMode() == StepMode.ALWAYS_BOT){
			gameStatus = GameStatus.OPPONENT_MOVE_SOME_BUTTON;
			scoreBoard.getOpponentTimeLeftBoard().start(this);
			bot.moveSomeButton();
			scoreBoard.getOpponentTimeLeftBoard().clear();
			opponentInGame();
		}else{
			playerMoveSomeButton();
		}
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
	
	/**
	 * End of the player's time left.
	 */
	private void playerTimeLeftEnd(){
		Gdx.app.log(getClass().getName() + ".playerTimeLeftEnd()", "init");
		
		// If the player is moving button then reset this variable.
		if(moovingButton != null){
			moovingButton = null;
		}
		
		switch(gameStatus){
			case PLAYER_IN_GAME : 
				opponentInGame(); 
				break;
			case PLAYER_MOVE_ONE_BUTTON :
			case PLAYER_MOVE_SOME_BUTTON :
				playerInGame();
				break;
			default : throw new IllegalGameStatusException();
		}
	}
	
	/**
	 * End of the opponent's time left.
	 */
	private void opponentTimeLeftEnd(){
		Gdx.app.log(getClass().getName() + ".opponentTimeLeftEnd()", "init");
		
		switch(gameStatus){
			case OPPONENT_IN_GAME: 
				playerInGame(); 
				break;
			case OPPONENT_MOVE_ONE_BUTTON: 
			case OPPONENT_MOVE_SOME_BUTTON:
				opponentInGame();
				break;
			default : throw new IllegalGameStatusException();
		}
	}
	
	/**
	 * Clear all movements.
	 */
	private void clearAllMovements(){
		// Clear player's buttons move
		for(final Button button : table.getPlayerButtons()){
			button.clearMove();
		}
		
		// Clear opponent's buttons move
		for(final Button button : table.getOpponentButtons()){
			button.clearMove();
		}
		
		// Clear ball move
		table.getBall().clearMove();
		
		// Clear button move listener
		eventListener.clearMovings();
	}
	
	/**
	 * Return {@link Player#PLAYER} when the player is on the left side else {@link Player#BOT}.
	 */
	private Player whoIsOnLeftSide(){
		return scoreBoard.getHalfTimeBoard().getHalfTimeType() == HalfTimeType.FIRST_HALF ? Player.PLAYER : Player.BOT;
	}
	
	/**
	 * Return {@link Player#BOT} when the player is on the left side else {@link Player#Player}.
	 */
	private Player whoIsOnRightSide(){
		return scoreBoard.getHalfTimeBoard().getHalfTimeType() == HalfTimeType.FIRST_HALF ? Player.BOT : Player.PLAYER;
	}
	
	/**
	 * Return true when the player's button contact with the ball at last time else false.
	 */
	private boolean isPlayerButtonContactBallLastTime(){
		return table.getPlayerButtons().contains(buttonContactBallLastTime);
	}
	
	/**
	 * Return true when the opponent's button contact with the ball at last time else false.
	 */
	private boolean isOpponentButtonContactBallLastTime(){
		return table.getOpponentButtons().contains(buttonContactBallLastTime);
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

	/**
	 * Return with the game status for tests.
	 */
	protected GameStatus getGameStatus() {
		return gameStatus;
	}

	/**
	 * Set the game status for tests.
	 */
	protected void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
	
}
