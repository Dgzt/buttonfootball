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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.util.MathUtil;

/**
 * Line shape.
 * 
 * @author Dgzt
 */
public class LineShape extends Shape{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width of the line. */
	public static final float LINE_WIDTH = 1.0f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------

	/** The number of the vertices. */
	private static final int VERTICES_NUM = 4;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The first x coordinate value. */
	private float x1;
	
	/** The first y coordinate value. */
	private float y1;
	
	/** The second x coordinate value. */
	private float x2;
	
	/** The second y coordinate value. */
	private float y2;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	public LineShape(final ShaderProgram shader, final Color color){
		super(shader, GL20.GL_TRIANGLES, VERTICES_NUM, new short[]{0,1,2,2,3,1}, color);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the shape.
	 * 
	 * @param x1 - The first x coordinate value.
	 * @param y1 - The first y coordinate value.
	 * @param x2 - The second x coordinate value.
	 * @param y2 - The second y coordinate value.
	 * @param scale - The scale value.
	 */
	public void resize(final float x1, final float y1, final float x2, final float y2, final double scale){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		final float lineWidth = (float) (LineShape.LINE_WIDTH * scale);

		final float vectorX = x2 - x1;
		final float vectorY = (Gdx.graphics.getHeight() - y2) - (Gdx.graphics.getHeight() - y1);
		
		final float vectorLength = (float) MathUtil.distance(vectorX, vectorY);
		
		final float vectorXUnit = vectorX / vectorLength;
		final float vectorYUnit = vectorY / vectorLength;
		
		final float vectorPXUnit = -vectorYUnit;
		final float vectorPYUnit = vectorXUnit;
		
		final float[] vertices = new float[VERTICES_NUM * POSITION_NUM];
		vertices[0] = x1 - lineWidth/2 * vectorPXUnit;		vertices[1] = Gdx.graphics.getHeight() - y1 - lineWidth/2 * vectorPYUnit;
		vertices[2] = x1 + lineWidth/2 * vectorPXUnit;		vertices[3] = Gdx.graphics.getHeight() - y1 + lineWidth/2 * vectorPYUnit;
		vertices[4] = x2 - lineWidth/2 * vectorPXUnit;		vertices[5] = Gdx.graphics.getHeight() - y2 - lineWidth/2 * vectorPYUnit;
		vertices[6] = x2 + lineWidth/2 * vectorPXUnit;		vertices[7] = Gdx.graphics.getHeight() - y2 + lineWidth/2 * vectorPYUnit;
		
		setVertices(vertices);
	}
	
	/**
	 * Return with the length of line.
	 */
	public float getLength(){
		return (float) MathUtil.distance(x1, y1, x2, y2);
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------

	/**
	 * Return with first x coordinate value.
	 */
	public final float getX1(){ 
		return x1;
	}
	
	/**
	 * Return with first y coordinate value.
	 */
	public final float getY1(){
		return y1;
	}
	
	/**
	 * Return with second x coordinate value.
	 */
	public final float getX2(){
		return x2;
	}
	
	/**
	 * Return with second y coordinate value.
	 */
	public final float getY2(){
		return y2;
	}
	
}
