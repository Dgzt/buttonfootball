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
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.TimeUtils;
import com.dgzt.core.shape.Text;

/**
 * Show the actual frame per second.
 * 
 * @author Dgzt
 */
public class FPS extends Text{
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The one second in nanosecond. */
	private static final float ONE_SECOND_IN_NANOSECOND = 1000000000;
	
	/** The color. */
	private static final Color COLOR = Color.RED;
	
	/** The FPS prefix. */
	private static final String FPS_PREFIX = "FPS: ";

	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width of the fps text in cm. */
	public static final float WIDTH = getWidth(FPS_PREFIX + "00");
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The start time. */
	private float startTime;
	
	/** Visible this object. */
	private boolean visible;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param batch - The batch;
	 */
	public FPS(final ShaderProgram shader, final Batch batch){
		super(shader, batch, COLOR);
		visible = false;
		
		startTime = TimeUtils.nanoTime();
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		if(visible){

			if(TimeUtils.nanoTime() - startTime > ONE_SECOND_IN_NANOSECOND){
				setText(FPS_PREFIX + Gdx.graphics.getFramesPerSecond());
				
				startTime = TimeUtils.nanoTime();
			}
			
			super.draw();
		}
	}
	
	// --------------------------------------------------
	// ~ Setters / Getters
	// --------------------------------------------------
	
	public boolean isVisible(){
		return visible;
	}
	
	public void setVisible(final boolean visible){
		this.visible = visible;
	}
	
}
