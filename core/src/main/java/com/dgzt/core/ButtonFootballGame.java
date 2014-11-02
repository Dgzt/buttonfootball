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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * The game listener.
 * 
 * @author Dgzt
 */
final public class ButtonFootballGame implements ApplicationListener {
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The vertex shader. */
	private static String VERTEX_SHADER = 
		     "attribute vec4 a_position;    \n"
		     + "attribute vec4 a_color;\n" 
		     + "attribute vec2 a_texCoords;\n"
		     + "uniform mat4 u_worldView;\n"
		     + "varying vec4 v_color;"
		     + "varying vec2 v_texCoords;"
		     + "void main()                  \n"
		     + "{                            \n"
		     + "   v_color = a_color; \n"
		     + "   v_texCoords = a_texCoords; \n"
		     + "   gl_Position =  u_worldView * a_position; \n" +
		     "}                            \n";
	
	/** The fragment shader. */
	private static String FRAGMENT_SHADER = 
		     "#ifdef GL_ES                \n"
		     + "precision mediump float;    \n"
		     + "#endif                      \n"
		     + "varying vec4 v_color;\n"
		     + "void main()                 \n"
		     + "{                           \n"
		     + "  gl_FragColor = v_color;   \n"
		     + "}";
	
	/** The world view for uniform matrix. */
	private static String WORLD_VIEW = "u_worldView";
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shader. */
	private ShaderProgram shader;

	/** The camera. */
	private OrthographicCamera camera;
	
	/** The main window. */
	private MainWindow mainWindow;
	
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
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
		
		Gdx.app.log(ButtonFootballGame.class.getName()+".create", "");
		shader = new ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER);
		
		mainWindow = new MainWindow(shader);
	}

	/**
	 * Resize the objects.
	 */
	@Override
	public void resize (int width, int height) {
		Gdx.app.log(ButtonFootballGame.class.getName()+".resize", String.valueOf(width)+" x "+String.valueOf(height));
		
		camera.setToOrtho(true, width, height);

		shader.begin();
		shader.setUniformMatrix(WORLD_VIEW, camera.combined);
		shader.end();
		
		float mainWindowWidth;
		float mainWindowHeight;
		
		final double rate = (double)width/height;
		
		if( MainWindow.WIDTH/MainWindow.HEIGHT > rate ){
			mainWindowWidth = width;
			mainWindowHeight = MainWindow.HEIGHT*(width/MainWindow.WIDTH);
		}else{
			mainWindowWidth = MainWindow.WIDTH*(height/MainWindow.HEIGHT);
			mainWindowHeight = height;
		}
		
		final float mainWindowX = (width-mainWindowWidth)/2;
		final float mainWindowY = (height-mainWindowHeight)/2;
		final double scale = (double)mainWindowWidth / MainWindow.WIDTH;
		
		mainWindow.resize(mainWindowX, mainWindowY, mainWindowWidth, scale);
	}

	/**
	 * Render the objects.
	 */
	@Override
	public void render () {
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);;	

		shader.begin();
		mainWindow.draw();
		shader.end();
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
