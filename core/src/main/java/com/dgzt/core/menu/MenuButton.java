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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.dgzt.core.FontConstants;

/**
 * The button for menu.
 * 
 * @author Dgzt
 */
public final class MenuButton extends TextButton{

	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param text - The text.
	 */
	public MenuButton(final String text) {
		super(text, getMenuButtonStyle());
		
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------

	/**
	 * Return with the style of button.
	 */
	private static TextButtonStyle getMenuButtonStyle(){
		final Skin skin = new Skin();
		
		final Pixmap pixmap = new Pixmap(40, 30, Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.fill();
		
		skin.add("white", new Texture(pixmap));
		
		final BitmapFont bfont = new BitmapFont(Gdx.files.internal(FontConstants.SMALL_FONT_FILE), Gdx.files.internal(FontConstants.SMALL_FONT_IMAGE), false);
		skin.add("default",bfont);
		
		final TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.GREEN);
 
		textButtonStyle.font = skin.getFont("default");
		
		return textButtonStyle;
	}

}
