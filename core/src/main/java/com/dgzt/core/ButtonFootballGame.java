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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The game listener.
 * 
 * @author Dgzt
 */
public class ButtonFootballGame implements ApplicationListener {
	
	Texture texture;
	SpriteBatch batch;
	float elapsed;

	/**
	 * Create the objects.
	 */
	@Override
	public void create () {
		Gdx.app.log(ButtonFootballGame.class.getName()+".create", "");
		texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
		batch = new SpriteBatch();
	}

	/**
	 * Resize the objects.
	 */
	@Override
	public void resize (int width, int height) {
		Gdx.app.log(ButtonFootballGame.class.getName()+".resize", String.valueOf(width)+" x "+String.valueOf(height));
	}

	/**
	 * Render the objects.
	 */
	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(1, 0, 1, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 100+100*(float)Math.cos(elapsed), 100+25*(float)Math.sin(elapsed));
		batch.end();
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
