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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dgzt.core.MultiInputProcessor;

/**
 * The in game menu window.
 * 
 * @author Dgzt
 */
public abstract class InGameMenuWindow extends BaseMenuWindow{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------

	/** The text of resume game button. */
	private static final String RESUME_GAME = " Resume game ";
	
	/** The text of quit to main menu button. */
	private static final String QUIT_TO_MAIN_MENU = " Quit to main menu ";
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param batch - The batch.
	 * @param camera - The camera.
	 * @param multiInputProcessor - The multi input processor.
	 */
	public InGameMenuWindow(final ShaderProgram shader, final Batch batch, final Camera camera, final MultiInputProcessor multiInputProcessor) {
		super(shader, batch, camera, multiInputProcessor);
		
		final WidgetGroup menuGroup = getMenuGroup();
		menuGroup.addActor(getResumeGameButton());
		menuGroup.addActor(getQuitToMainMenuButton());
	}
	
	// --------------------------------------------------
	// ~ Abstract methods
	// --------------------------------------------------
	
	/**
	 * Call this function when clicked to the resume game button.
	 */
	protected abstract void resumeGame();
	
	/**
	 * Call this function when clicked to the quit to main menu button.
	 */
	protected abstract void quitToMainMenu();
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Return with the resume game button.
	 */
	private TextButton getResumeGameButton(){
		final TextButton button = new TextButton(RESUME_GAME, getStyle());
		button.addListener(new ClickListener(){

			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				resumeGame();
			}
			
		});
		return button;
	}
	
	/**
	 * Return with the quit to main menu button.
	 */
	private TextButton getQuitToMainMenuButton(){
		final TextButton button = new TextButton(QUIT_TO_MAIN_MENU, getStyle());
		button.addListener(new ClickListener(){

			@Override
			public void clicked(final InputEvent event, final float x, final float y) {
				quitToMainMenu();
			}
			
		});
		return button;
	}

}
