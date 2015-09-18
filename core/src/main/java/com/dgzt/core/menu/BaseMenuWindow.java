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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dgzt.core.MultiInputProcessor;
import com.dgzt.core.shape.RectangleShape;

/**
 * The base menu window.
 * 
 * @author Dgzt
 */
public class BaseMenuWindow extends RectangleShape{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The background color. */
	private static final Color BACKGROUND_COLOR = new Color(0.0f, 0.0f, 0.0f, 0.5f);
	
	/** The space in the menu. */
	private static final int MENU_SPACE = 15;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shader. */
	private final ShaderProgram shader;
	
	/** The multi input processor. */
	private final MultiInputProcessor multiInputProcessor;
	
	/** The stage. */
	private final Stage stage;
	
	/** The menu vertical group. */
	private final VerticalGroup menuGroup;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------

	/**
	 * Constructor.
	 * 
	 * @param shader - The shader program.
	 * @param batch - The batch.
	 * @param viewport - The viewport.
	 * @param multiInputProcessor - The multi input processor.
	 */
	public BaseMenuWindow(final ShaderProgram shader, final Batch batch, final Viewport viewport, final MultiInputProcessor multiInputProcessor) {
		super(shader, BACKGROUND_COLOR);
		this.shader = shader;
		
		stage = new Stage(viewport, batch);
		
		menuGroup = new VerticalGroup();
		menuGroup.setFillParent(true);
		menuGroup.space(MENU_SPACE);
		
		stage.addActor(menuGroup);
		
		multiInputProcessor.add(stage);
		
		this.multiInputProcessor = multiInputProcessor;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the window.
	 * 
	 * @param width - The new width value.
	 * @param height - The new height value.
	 */
	public void resize(final float width, final float height){
		super.resize(0, 0, width, height);
		
		menuGroup.setPosition(0, -(height - menuGroup.getPrefHeight()) / 2);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * Dispose the menu window.
	 */
	@Override
	public void dispose() {
		multiInputProcessor.remove(stage);
		stage.dispose();
		
		super.dispose();
	}
	
	/**
	 * Draw the menu window.
	 */
	@Override
	public void draw(){
		super.draw();
		
		shader.end();
		stage.draw();
		shader.begin();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------

	/**
	 * Return with the menu group.
	 */
	public VerticalGroup getMenuGroup() {
		return menuGroup;
	}
}
