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
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.menu.BaseMenuWindow;
import com.dgzt.core.menu.InGameMenuWindow;
import com.dgzt.core.menu.MainMenuWindow;
import com.dgzt.core.setting.Settings;

/**
 * The main window.
 * 
 * @author Dgzt
 */
public class MainWindow {
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shader. */
	private final ShaderProgram shader;
	
	/** The batch. */
	private final Batch batch;
	
	/** The camera. */
	private final Camera camera;
	
	/** The multi input processor. */
	private final MultiInputProcessor multiInputProcessor;
	
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
	public MainWindow(final ShaderProgram shader, final Batch batch, final Settings settings, final Camera camera, final MultiInputProcessor multiInputProcessor){
		this.shader = shader;
		this.batch = batch;
		this.camera = camera;
		this.multiInputProcessor = multiInputProcessor;
		
		gameWindow = new GameWindow(shader, batch, settings, multiInputProcessor);
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
	}
	
	/**
	 * Draw the window.
	 */
	public void draw(){
		gameWindow.draw();
		
		if(menuWindow != null){
			menuWindow.draw();
		}
	}
	
	/**
	 * Dispose the window.
	 */
	public void dispose(){
		gameWindow.dispose();
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
					gameWindow.getGameControl().pauseGame();
					menuWindow = getInGameMenuWindow();
					menuWindow.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}else{
					gameWindow.getGameControl().resumeGame();
					menuWindow.dispose();
					menuWindow = null;
				}
			}
		};
	}
	
	/**
	 * Return with the main menu window.
	 */
	private MainMenuWindow getMainMenuWindow(){
		return new MainMenuWindow(shader, batch, camera, multiInputProcessor){

			@Override
			protected void startGame() {
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
		return new InGameMenuWindow(shader, batch, camera, multiInputProcessor) {
			
			@Override
			protected void resumeGame() {
				menuWindow.dispose();
				menuWindow = null;
				
				gameWindow.getGameControl().resumeGame();
			}
			
			@Override
			protected void quitToMainMenu() {
				gameWindow.getGameControl().quitGame();
			}
		};
	}
}
