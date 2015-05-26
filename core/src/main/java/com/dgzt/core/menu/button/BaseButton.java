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
package com.dgzt.core.menu.button;

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
public class BaseButton extends TextButton{

	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The prefix and postfix for text. */
	private static final String TEXT_PREFIX_POSTFIX = " ";
	
	/** The name for default drawable. */
	private static final String DEFAULT_DRAWABLE = "default";
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param text - The text.
	 * @param backgroundColor - The background color.
	 * @param textColor	- The text color.
	 */
	public BaseButton(final String text, final Color backgroundColor, final Color textColor) {
		super(TEXT_PREFIX_POSTFIX + text + TEXT_PREFIX_POSTFIX, getSkin(backgroundColor, textColor));
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------

	/**
	 * Return with the skin of button.
	 * 
	 * @param backgroundColor - The background color.
	 * @param textColor - The text color.
	 */
	private static Skin getSkin(final Color backgroundColor, final Color textColor){
		final Skin skin = new Skin();
		
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(backgroundColor);
		pixmap.fill();
		
		skin.add(DEFAULT_DRAWABLE, new Texture(pixmap));
		
		final BitmapFont bfont = new BitmapFont(Gdx.files.internal(FontConstants.SMALL_FONT_FILE), Gdx.files.internal(FontConstants.SMALL_FONT_IMAGE), false);
		skin.add(DEFAULT_DRAWABLE,bfont);
		
		final TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable(DEFAULT_DRAWABLE, backgroundColor);
 
		textButtonStyle.font = skin.getFont(DEFAULT_DRAWABLE);
		textButtonStyle.fontColor = textColor;
		
		skin.add(DEFAULT_DRAWABLE, textButtonStyle);
		
		return skin;
	}

}
