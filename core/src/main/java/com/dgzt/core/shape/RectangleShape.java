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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * The rectangle shape.
 * 
 * @author Dgzt
 */
public class RectangleShape extends Shape{

	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The number of vertices. */
	private static final int VERTICES_NUM = 4;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	public RectangleShape(final ShaderProgram shader, final Color color){
		super(shader, GL20.GL_TRIANGLES, VERTICES_NUM, new short[]{0,1,3,1,2,3}, color);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the shape.
	 * 
	 * @param x - The x coordinate value of the bottom left corner.
	 * @param y - The y coordinate value of the bottom left corner.
	 * @param width - The width value.
	 * @param height The height value.
	 */
	public void resize(final float x, final float y, final float width, final float height){
		final float[] vertices = new float[RectangleShape.VERTICES_NUM * Shape.POSITION_NUM];
		vertices[0] = x;			vertices[1] = y;			// The bottom left corner
		vertices[2] = x;			vertices[3] = y + height;	// The top left corner
		vertices[4] = x + width;	vertices[5] = y + height;	// The top right corner
		vertices[6] = x + width;	vertices[7] = y;			// The bottom right corner
		
		setVertices(vertices);
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------
	
	/**
	 * Return with x coordinate value.
	 */
	public final float getX(){
		return getVertices()[0];
	}
	
	/**
	 * Return with y coordinate value.
	 */
	public final float getY(){
		return getVertices()[1];
	}
	
	/**
	 * Return with width value.
	 */
	public final float getWidth(){
		final float[] vertices = getVertices();
		
		return vertices[4] - vertices[0];
	}
	
	/**
	 * Return with the height value.
	 */
	public final float getHeight(){
		final float[] vertices = getVertices();
		
		return vertices[3] - vertices[1];
	}
}
