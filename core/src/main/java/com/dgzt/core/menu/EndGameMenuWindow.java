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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dgzt.core.MultiInputProcessor;

/**
 * The end game menu window.
 * 
 * @author Dgzt
 */
public abstract class EndGameMenuWindow extends BaseMenuWindow{

	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The color of the text. */
	private static final Color TEXT_COLOR = Color.WHITE;
	
	/** The player text. */
	private static final String PLAYER = "Player";
	
	/** The opponent text. */
	private static final String OPPONENT = "Opponent";
	
	/** The space between player and opponent scores. */
	private static final int SCORE_GROUP_SPACE = 30;
	
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
	 * @param playerGoals - The number of player's goals.
	 * @param opponentGoals - The number of opponent's goals.
	 */
	public EndGameMenuWindow(final ShaderProgram shader, final Batch batch, final Camera camera, final MultiInputProcessor multiInputProcessor, final int playerGoals, final int opponentGoals) {
		super(shader, batch, camera, multiInputProcessor);
		
		final HorizontalGroup hGroup = new HorizontalGroup();
		hGroup.addActor(getScoreVerticalGroup(PLAYER, playerGoals));
		hGroup.space(SCORE_GROUP_SPACE);
		hGroup.addActor(getScoreVerticalGroup(OPPONENT, opponentGoals));

		final WidgetGroup menuGroup = getMenuGroup();
		menuGroup.addActor(hGroup);
		menuGroup.addActor(getQuitToMainMenuButton());
	}
	
	// --------------------------------------------------
	// ~ Abstract methods
	// --------------------------------------------------	
	
	/**
	 * Call this function when clicked to the quit to main menu button.
	 */
	protected abstract void quitToMainMenu();
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------	
	
	/**
	 * Return with the score vertical group.
	 * 
	 * @param name - The name.
	 * @param goals - The number of goals.
	 */
	private VerticalGroup getScoreVerticalGroup(final String name, final int goals){
		final VerticalGroup group = new VerticalGroup();
		
		group.addActor(getLabel(name));
		group.addActor(getLabel(String.valueOf(goals)));
		
		return group;
	}
	
	/**
	 * Get label with the given text.
	 * 
	 * @param text - The text.
	 */
	private Label getLabel(final String text){
		return new Label(text, new Label.LabelStyle(new BitmapFont(), TEXT_COLOR));
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
