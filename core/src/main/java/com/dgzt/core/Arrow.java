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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.button.Button;
import com.dgzt.core.shape.LineShape;

/**
 * The arrow.
 * 
 * @author Dgzt
 */
public class Arrow extends LineShape{

	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The parent object. */
	private final Table parent;
	
	/** Is visible the arrow. */
	private boolean visible;
	
	/** The last selected player buttons. */
	private Button lastSelectedButton;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param parent - The parent object.
	 * @param shader - The shader.
	 */
	public Arrow(final Table parent, final ShaderProgram shader) {
		super(shader, Color.RED);
		this.parent = parent;
		visible = false;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Show the arrow.
	 * 
	 * @param selectedButton - The selected player button.
	 */
	public void show(final Button selectedButton){
		this.lastSelectedButton = selectedButton;
		super.resize(selectedButton.getX(), selectedButton.getY(), selectedButton.getX(), selectedButton.getY(), parent.getScale());
		visible = true;
	}
	
	/**
	 * Set the new end point of arrow.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public void setEndPoint(final float x, final float y){
		if(visible){
			super.resize(getX1(), getY1(), x, y, parent.getScale());
		}
	}
	
	/**
	 * Hide the arrow.
	 */
	public void hide(){
		visible = false;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * Draw the arrow if it is visible.
	 */
	@Override
	public void draw() {
		if(visible){
			super.draw();
		}
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------

	/**
	 * Return with the visibility of arrow.
	 */
	public boolean isVisible(){
		return visible;
	}
	
	/**
	 * Return with the last selected player button.
	 */
	public Button getLastSelectedButton(){
		return lastSelectedButton;
	}
}
