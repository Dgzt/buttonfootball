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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.type.StepType;

/**
 * The game listener.
 * 
 * @author Dgzt
 */
final public class ButtonFootballGame implements ApplicationListener {
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The path of the vertex shader. */
	private static String VERTEX_SHADER = "shader.vsh";
	
	/** The path of the fragment shader. */
	private static String FRAGMENT_SHADER = "shader.fsh";
	
	/** The world view for uniform matrix. */
	private static String WORLD_VIEW = "u_worldView";
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shader. */
	private ShaderProgram shader;
	
	/** The sprite batch for show text. */
	private SpriteBatch spriteBatch;

	/** The camera. */
	private OrthographicCamera camera;
	
	/** The type of step process. */
	private final StepType stepType;
	
	/** The main window. */
	private MainWindow mainWindow;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param stepType - The type of the process type.
	 */
	public ButtonFootballGame(final StepType stepType) {
		this.stepType = stepType;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * Create the objects.
	 */
	@Override
	public void create () {
		// For the alpha channel
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
        camera = new OrthographicCamera();
		
		shader = new ShaderProgram(Gdx.files.internal(VERTEX_SHADER).readString(), Gdx.files.internal(FRAGMENT_SHADER).readString());
		spriteBatch = new SpriteBatch();
		
		if(!shader.isCompiled()){
			Gdx.app.log(ButtonFootballGame.class.getName()+".create", "Problem loading shader: " + shader.getLog());
		}else if(!shader.getLog().isEmpty()){
			Gdx.app.log(ButtonFootballGame.class.getName()+".create", "Shader log: " + shader.getLog());
		}
		
		mainWindow = new MainWindow(shader, spriteBatch, stepType);
	}

	/**
	 * Resize the objects.
	 */
	@Override
	public void resize (int width, int height) {
		Gdx.app.log(ButtonFootballGame.class.getName()+".resize", String.valueOf(width)+" x "+String.valueOf(height));
		
		camera.setToOrtho(false, width, height);
		spriteBatch.setProjectionMatrix(camera.combined);
			
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
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
