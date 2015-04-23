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

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * The input listener.
 * 
 * @author Dgzt
 */
final public class InputListener extends InputAdapter{
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------

	/** The table. */
	private final MainWindow mainWindow;
	
	/** The game control. */
	private final GameControl gameControl;
	
	/** The last pressed mouse button. */
	private int button;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param mainWindow - The main window.
	 * @param gameControl - The game control.
	 */
	public InputListener(final MainWindow mainWindow, final GameControl gameControl){
		this.mainWindow = mainWindow;
		this.gameControl = gameControl;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * The mouse pressed event.
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.button = button;
		
		if(button == Buttons.LEFT){
			if(gameControl.isPlayerStep()){
				mainWindow.mouseButtonPressed(screenX, screenY);
			}else if(gameControl.isPlayerMoveButton()){
				gameControl.selectMoovingButton(screenX, screenY);
			}
		}
		
		return super.touchDown(screenX, screenY, pointer, button);
	}

	/**
	 * The mouse moving events.
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(button == Buttons.LEFT){
			if(gameControl.isPlayerStep()){
				mainWindow.mouseButtonMoved(screenX, screenY);
			}else if(gameControl.isPlayerMoveButton()){
				gameControl.moveSelectedButton(screenX, screenY);
			}
		}
		return super.touchDragged(screenX, screenY, pointer);
	}

	/**
	 * The mouse releasing events.
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == Buttons.LEFT){
			if(gameControl.isPlayerStep()){
				mainWindow.mouseButtonReleased();
			}else if(gameControl.isPlayerMoveButton()){
				gameControl.endMoveSelectedButton();
			}
		}
		
		return super.touchUp(screenX, screenY, pointer, button);
	}

	/**
	 * The keyboard pressed events.
	 */
	@Override
	public boolean keyDown(int keycode) {
		if(Keys.F == keycode){
			Gdx.app.log(InputListener.class.getName()+".keyDown", "'f' pressed.");
			final FPS fps = mainWindow.getFPS();
			fps.setVisible(!fps.isVisible());
		}else if(Keys.ESCAPE == keycode && Gdx.app.getType().equals(ApplicationType.Desktop)){
			Gdx.app.exit();
		}
		return super.keyDown(keycode);
	}
	
}
