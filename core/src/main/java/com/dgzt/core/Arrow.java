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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dgzt.core.shape.LineShape;

/**
 * The arrow.
 * 
 * @author Dgzt
 */
final public class Arrow extends LineShape{

	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** Is visible the arrow. */
	private boolean visible;
	
	/** The scale value. */
	private double scale;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shapeRenderer - The shape renderer.
	 */
	public Arrow(final ShapeRenderer shapeRenderer) {
		super(shapeRenderer, Color.RED);
		visible = false;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------

	/**
	 * Set the scale.
	 * 
	 * @param scale - The scale value.
	 */
	public void setScale(final double scale) {
		this.scale = scale;
	}
	
	/**
	 * Show the arrow.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public void show(final float x, final float y){
		Gdx.app.log(Arrow.class.getName()+".show", String.valueOf(x)+" x "+String.valueOf(y));
		super.resize(x, y, x, y, scale);
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
			Gdx.app.log(Arrow.class.getName()+".setEndPoint", String.valueOf(x)+" x "+String.valueOf(y));
			super.resize(getX1(), getY1(), x, y, scale);
		}
	}
	
	/**
	 * Hide the arrow.
	 */
	public void hide(){
		if(visible){
			Gdx.app.log(Arrow.class.getName()+".hide", "");
			visible = false;
		}
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

	
}
