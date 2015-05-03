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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dgzt.core.shape.RectangleShape;

/**
 * The menu window.
 * 
 * @author Dgzt
 */
public abstract class MenuWindow extends RectangleShape{

	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The background color. */
	private static final Color BACKGROUND_COLOR = new Color(0.0f, 0.0f, 0.0f, 0.5f);
	
	/** The multi input processor. */
	private final MultiInputProcessor multiInputProcessor;
	
	/** The stage. */
	private final Stage stage;
	
	/** The viewport. */
	private final ScreenViewport viewport;
	
	/** The shader. */
	private final ShaderProgram shader;
	
	/** The batch. */
	private final Batch batch;
	
	/** The start game button. */
	private final Button startGameButton;
	
	/**
	 * Constructor.
	 * 
	 * @param shader - The shader.
	 * @param batch - The sprite batch.
	 * @param camera - The camera.
	 * @param multiInputProcessor - The multi input processor.
	 */
	public MenuWindow(final ShaderProgram shader, final Batch batch, final Camera camera, final MultiInputProcessor multiInputProcessor) {
		super(shader, BACKGROUND_COLOR);
		this.shader = shader;
		this.batch = batch;
		this.multiInputProcessor = multiInputProcessor;
		
		viewport = new ScreenViewport(camera);
		stage = new Stage(viewport);
		
		startGameButton = new TextButton(" Start game ", getStyle());
		startGameButton.addListener(new ClickListener(){

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log(getClass().getName() + ".init", "click");
				startGame();
			}
			
		});
		
		stage.addActor(startGameButton);
		
		multiInputProcessor.add(stage);
	}
	
	// --------------------------------------------------
	// ~ Abstract methods
	// --------------------------------------------------
	
	/**
	 * Start the game.
	 */
	protected abstract void startGame();
	
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
		viewport.update((int)width, (int)height, true);
		
		startGameButton.setPosition((width - startGameButton.getWidth()) / 2, ( height - startGameButton.getHeight()) / 2);
	}

	/**
	 * Draw the window.
	 */
	@Override
	public void draw() {
		super.draw();
		
		shader.end();
		batch.begin();
		startGameButton.draw(batch, 1.0f);
		batch.end();
		shader.begin();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * Dispose the window.
	 */
	@Override
	public void dispose() {
		multiInputProcessor.remove(stage);
		stage.dispose();
		
		super.dispose();
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------

	/**
	 * Return with the style of button.
	 */
	private TextButtonStyle getStyle(){
		final Skin skin = new Skin();
		
		Pixmap pixmap = new Pixmap(20, 20, Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.fill();
		
		skin.add("white", new Texture(pixmap));
		
		BitmapFont bfont=new BitmapFont();
		bfont.scale(0.3f);
		skin.add("default",bfont);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.GREEN);
 
		textButtonStyle.font = skin.getFont("default");
		
		return textButtonStyle;
	}
}
