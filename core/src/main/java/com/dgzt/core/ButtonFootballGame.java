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

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * The game listener.
 * 
 * @author Dgzt
 */
public class ButtonFootballGame implements ApplicationListener {

	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shape renderer. */
	private ShapeRenderer shapeRenderer;

	/** The camera. */
	private OrthographicCamera camera;
	
	/** The camera. */
	private Table table;
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * Create the objects.
	 */
	@Override
	public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
		
		Gdx.app.log(ButtonFootballGame.class.getName()+".create", "");
		shapeRenderer = new ShapeRenderer();
		
		table = new Table(shapeRenderer);
	}

	/**
	 * Resize the objects.
	 */
	@Override
	public void resize (int width, int height) {
		Gdx.app.log(ButtonFootballGame.class.getName()+".resize", String.valueOf(width)+" x "+String.valueOf(height));
		camera.setToOrtho(true, width, height);
		
		table.resize(width, height);
	}

	/**
	 * Render the objects.
	 */
	@Override
	public void render () {
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);;	
		
		shapeRenderer.begin(ShapeType.Filled);
		
		table.draw();
		
		shapeRenderer.end();
	}

	/**
	 * Pause the game.
	 */
	@Override
	public void pause () {
	}

	/**
	 * Resume the game.
	 */
	@Override
	public void resume () {
	}

	/**
	 * Dispose the game.
	 */
	@Override
	public void dispose () {
	}
}
