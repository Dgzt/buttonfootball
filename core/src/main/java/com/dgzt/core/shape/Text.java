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
package com.dgzt.core.shape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Show text.
 * 
 * @author Dgzt
 */
public class Text {
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shader. */
	private final ShaderProgram shader;
	
	/** The sprite batch to draw text. */
	private final SpriteBatch spriteBatch;
	
	/** The font for text. */
	private final BitmapFont font;
	
	/** The showing text. */
	private String text;
	
	/** The x coordinate value. */
	private float x;
	
	/** The y coordinate value. */
	private float y;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param shader - The shader.
	 * @param spriteBatch - The sprite batch.
	 * @param color - The color.
	 */
	public Text(final ShaderProgram shader, final SpriteBatch spriteBatch, final Color color){
		this.shader = shader;
		this.spriteBatch = spriteBatch;
		font = new BitmapFont();
		font.setColor(color);
		text = "";
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the position.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public void resize(final float x, final float y){
		this.x = x;
		this.y = Gdx.graphics.getHeight() - y;
	}
	
	/**
	 * Set the text.
	 * 
	 * @param text - The text.
	 */
	public void setText(final String text){
		this.text = text;
	}
	
	/**
	 * Draw the text.
	 */
	public void draw(){
		shader.end();
		spriteBatch.begin();
		
		font.draw(spriteBatch, text, x, y);
		
		spriteBatch.end();
		shader.begin();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	// --------------------------------------------------
	// ~ Static methods
	// --------------------------------------------------

	/**
	 * Return with the width of the given text.
	 * 
	 * @param text - The text.
	 */
	public static float getWidth(final String text){
		return new BitmapFont().getBounds(text).width;
	}
	
	/**
	 * Return with the height of the given text.
	 * 
	 * @param text - The text.
	 */
	public static float getHeight(final String text){
		return new BitmapFont().getBounds(text).height;
	}
}
