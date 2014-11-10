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
 * Circle shape.
 * 
 * @author Dgzt
 */
public class FilledCircleShape extends Shape{
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The number of vertices. */
	private static final int VERTICES_NUM = 362;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------

	/** The radius value. */
	private float radius;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	public FilledCircleShape(final ShaderProgram shader, final Color color){
		super(shader, GL20.GL_TRIANGLE_FAN, FilledCircleShape.VERTICES_NUM, getIndices(), color);
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Return with the indices.
	 */
	private static short[] getIndices(){
		final short[] indices = new short[FilledCircleShape.VERTICES_NUM];
		
		for(short i=0;i<FilledCircleShape.VERTICES_NUM;++i){
			indices[i]=i;
		}
		
		return indices;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the shape.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param radius - The radius value.
	 */
	public void resize(final float x, final float y, final float radius){
		this.radius = radius;
		
		final float[] vertices = new float[FilledCircleShape.VERTICES_NUM * Shape.POSITION_NUM];
		
		vertices[0]=x;
		vertices[1]=y;
		for(int i=0;i<FilledCircleShape.VERTICES_NUM-1;++i){
			final float angle = (float) Math.toRadians(i);
			
			vertices[i*Shape.POSITION_NUM+2]=x + (float)Math.sin( angle ) * radius;
			vertices[i*Shape.POSITION_NUM+3]=y + (float)Math.cos( angle ) * radius;
		}
		
		super.setVertices(vertices);
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------
	
	/**
	 * Return with the x coordinate value.
	 */
	public final float getX(){
		return getVertices()[0];
	}
	
	/**
	 * Return with the y coordinate value.
	 */
	public final float getY(){
		return getVertices()[1];
	}
	
	/**
	 * Return with the radius value.
	 */
	public final float getRadius(){
		return radius;
	}
}
