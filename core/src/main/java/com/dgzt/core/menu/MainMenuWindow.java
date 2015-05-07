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
package com.dgzt.core.menu;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dgzt.core.MultiInputProcessor;

/**
 * The main menu window.
 * 
 * @author Dgzt
 */
public abstract class MainMenuWindow extends BaseMenuWindow{

	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** Text for start game. */
	private static final String START_GAME = " Start game ";
	
	/** Text for quit. */
	private static final String QUIT = " Quit ";


	/**
	 * Constructor.
	 * 
	 * @param shader - The shader.
	 * @param batch - The sprite batch.
	 * @param camera - The camera.
	 * @param multiInputProcessor - The multi input processor.
	 */
	public MainMenuWindow(final ShaderProgram shader, final Batch batch, final Camera camera, final MultiInputProcessor multiInputProcessor) {
		super(shader, batch, camera, multiInputProcessor);
		
		final WidgetGroup menuGroup = getMenuGroup();
		
		menuGroup.addActor(getStartGameButton());
		
		// Add quit button only in desktop mode.
		if(Gdx.app.getType().equals(ApplicationType.Desktop)){
			menuGroup.addActor(getQuitButton());
		}
		
		
	}
	
	// --------------------------------------------------
	// ~ Abstract methods
	// --------------------------------------------------
	
	/**
	 * Start the game.
	 */
	protected abstract void startGame();
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Start game button.
	 */
	private TextButton getStartGameButton(){
		final TextButton button = new TextButton(START_GAME, getStyle());
		button.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log(getClass().getName() + ".init", "click");
				startGame();
			}
			
		});
		
		return button;
	}
	
	/**
	 * Quit button.
	 */
	private TextButton getQuitButton(){
		final TextButton button = new TextButton(QUIT, getStyle());
		
		button.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
			
		});
		
		return button;
	}
	

}
