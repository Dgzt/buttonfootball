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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * The input listener of {@link MainWindow}.
 * 
 * @author Dgzt
 */
public abstract class MainWindowInputListener extends InputAdapter{
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The game control. */
	private final GameControl gameControl;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param gameControl - The game control.
	 */
	public MainWindowInputListener(final GameControl gameControl){
		this.gameControl = gameControl;
	}
	
	// --------------------------------------------------
	// ~ Abstract methods
	// --------------------------------------------------

	/**
	 * Show or hide the menu window.
	 */
	protected abstract void showOrHideMenuWindow();
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean keyDown(final int keycode) {
		if(Keys.ESCAPE == keycode && gameControl.isInGame()){
			Gdx.app.log(MainWindowInputListener.class.getName() + ".keyDown", "Escape pressed.");
			
			showOrHideMenuWindow();
		}else if(Keys.Q == keycode && Gdx.app.getType().equals(ApplicationType.Desktop)){
			Gdx.app.log(MainWindowInputListener.class.getName() + ".keyDown", "Quit.");
			
			Gdx.app.exit();
		}
		
		return super.keyDown(keycode);
	}
}
