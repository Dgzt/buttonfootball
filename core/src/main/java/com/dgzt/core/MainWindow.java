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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dgzt.core.menu.BaseMenuWindow;
import com.dgzt.core.menu.EndGameMenuWindow;
import com.dgzt.core.menu.InGameMenuWindow;
import com.dgzt.core.menu.MainMenuWindow;
import com.dgzt.core.menu.button.BaseButton;
import com.dgzt.core.menu.button.MainWindowButton;
import com.dgzt.core.setting.Settings;

/**
 * The main window.
 * 
 * @author Dgzt
 */
public final class MainWindow {

	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The text for menu button. */
	private static final String MENU_BUTTON_TEXT = "Menu";
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shader. */
	private final ShaderProgram shader;
	
	/** The batch. */
	private final Batch batch;
	
	/** The stage. */
	private final Stage stage;
	
	/** The multi input processor. */
	private final MultiInputProcessor multiInputProcessor;
	
	/** The menu button. */
	private final BaseButton menuButton;
	
	/** The game window. */
	private final GameWindow gameWindow;
	
	/** The menu window. This can be null if the menu isn't visible. */
	private BaseMenuWindow menuWindow;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param shader - The shader.
	 * @param batch - The sprite batch.
	 * @param settings - The settings.
	 * @param multiInputProcessor - The multi input processor.
	 */
	public MainWindow(final ShaderProgram shader, final Batch batch, final Settings settings, final Viewport viewport, final MultiInputProcessor multiInputProcessor){
		this.shader = shader;
		this.batch = batch;
		this.multiInputProcessor = multiInputProcessor;
		
		stage = new Stage(viewport);
		multiInputProcessor.add(stage);
		
		menuButton = getMenuButton();
		stage.addActor(menuButton);
		
		menuButton.setVisible(false);
		
		gameWindow = new GameWindow(shader, batch, settings, multiInputProcessor, this);
		menuWindow = getMainMenuWindow();
		
		multiInputProcessor.add(getInputListener(gameWindow.getGameControl()));
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------

	/**
	 * Resize the window.
	 * 
	 * @param width - The width.
	 * @param height - The height.
	 */
	public void resize(final float width, final float height){
		float gameWindowWidth;
		float gameWindowHeight;
		
		final double rate = (double)width/height;
		
		if( GameWindow.WIDTH/GameWindow.HEIGHT > rate ){
			gameWindowWidth = width;
			gameWindowHeight = GameWindow.HEIGHT*(width/GameWindow.WIDTH);
		}else{
			gameWindowWidth = GameWindow.WIDTH*(height/GameWindow.HEIGHT);
			gameWindowHeight = height;
		}
		
		final float gameWindowX = (width-gameWindowWidth)/2;
		final float gameWindowY = (height-gameWindowHeight)/2;
		final double scale = (double)gameWindowWidth / GameWindow.WIDTH;
		
		gameWindow.resize(gameWindowX, gameWindowY, gameWindowWidth, scale);
		
		if(menuWindow != null){
			menuWindow.resize(width, height);
		}
		
		// Resize menu button
		menuButton.setPosition(0, height - menuButton.getHeight());
	}
	
	/**
	 * Draw the window.
	 */
	public void draw(){
		gameWindow.draw();
		
		if(menuWindow != null){
			menuWindow.draw();
		}

		shader.end();
		batch.begin();
		stage.draw();
		batch.end();
		shader.begin();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * Dispose the window.
	 */
	public void dispose(){
		gameWindow.dispose();
		stage.dispose();
	}
	
	/**
	 * Show the end game menu window.
	 * 
	 * @param playerGoals - The number of player's goals.
	 * @param opponentGoals - The number of opponent's goals.
	 */
	public void showEndGameMenuWindow(final int playerGoals, final int opponentGoals){
		Gdx.app.log(getClass().getName() + ".showEndGameMenuWindow()", "init");
		
		menuWindow = getEndGameMenuWindow(playerGoals, opponentGoals);
		menuWindow.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Return with the input listener of main window.
	 * 
	 * @param gameControl - The game control
	 */
	private MainWindowInputListener getInputListener(final GameControl gameControl){
		return new MainWindowInputListener(gameControl) {
			
			@Override
			protected void showOrHideMenuWindow() {
				if(menuWindow == null){
					menuButton.setVisible(false);
					
					gameWindow.getGameControl().pauseGame();
					menuWindow = getInGameMenuWindow();
					menuWindow.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}else{
					menuButton.setVisible(true);
					
					gameWindow.getGameControl().resumeGame();
					menuWindow.dispose();
					menuWindow = null;
				}
			}
		};
	}
	
	/**
	 * Return with the menu button.
	 */
	private BaseButton getMenuButton(){
		final BaseButton button = new MainWindowButton(MENU_BUTTON_TEXT);
		button.addListener(new ClickListener(){
			
			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				menuButton.setVisible(false);
				
				gameWindow.getGameControl().pauseGame();
				menuWindow = getInGameMenuWindow();
				menuWindow.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			}
		});
		
		return button;
	}
	
	/**
	 * Return with the main menu window.
	 */
	private MainMenuWindow getMainMenuWindow(){
		return new MainMenuWindow(shader, batch, stage.getViewport(), multiInputProcessor){

			@Override
			protected void startGame() {
				menuButton.setVisible(true);
				
				menuWindow.dispose();
				menuWindow = null;
				
				gameWindow.getGameControl().startGame();
			}
			
		};
	}
	
	/**
	 * Return with the in game menu window.
	 */
	private InGameMenuWindow getInGameMenuWindow(){
		return new InGameMenuWindow(shader, batch, stage.getViewport(), multiInputProcessor) {
			
			@Override
			protected void resumeGame() {
				menuButton.setVisible(true);
				
				menuWindow.dispose();
				menuWindow = null;
				
				gameWindow.getGameControl().resumeGame();
			}
			
			@Override
			protected void quitToMainMenu() {
				gameWindow.getGameControl().quitGame();
				menuWindow.dispose();
				menuWindow = getMainMenuWindow();
				menuWindow.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			}
		};
	}
	
	/**
	 * Return with the end game menu window.
	 * 
	 * @param playerGoals - The number of player's goals.
	 * @param opponentGoals - The number opponent's goals.
	 */
	private EndGameMenuWindow getEndGameMenuWindow(final int playerGoals, final int opponentGoals){
		return new EndGameMenuWindow(shader, batch, stage.getViewport(), multiInputProcessor, playerGoals, opponentGoals){

			@Override
			protected void quitToMainMenu() {
				// Clear the score board and status
				gameWindow.getGameControl().quitGame();
				
				// Change the menu window to main menu
				menuWindow.dispose();
				menuWindow = getMainMenuWindow();
				menuWindow.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			}
			
		};
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------
	
	/**
	 * Return with the game window.
	 */
	public final GameWindow getGameWindow() {
		return gameWindow;
	}
}
