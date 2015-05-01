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

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
	
	/** The game window. */
	private final GameWindow gameWindow;
	
	private MenuWindow menuWindow;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param shader - The shader.
	 * @param spriteBatch - The sprite batch.
	 * @param settings - The settings.
	 */
	public MainWindow(final ShaderProgram shader, final SpriteBatch spriteBatch, final Settings settings, final Camera camera, final InputMultiplexer inputMultiplexer){
		gameWindow = new GameWindow(shader, spriteBatch, settings, inputMultiplexer);
		menuWindow = new MenuWindow(shader, spriteBatch, camera, inputMultiplexer){

			@Override
			protected void startGame() {
				menuWindow.dispose();
				menuWindow = null;
			}
			
		};
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
		
		//
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
}
