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
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * The abstract shape class.
 * 
 * @author Dgzt
 */
public abstract class Shape {
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The number of position (x,y coordinate system value). */
	protected static final int POSITION_NUM = 2;
	
	/** The number of color (r,g,b,a). */
	private static final int COLOR_NUM = 4;
	
	/** The position attribute. */
	private static final String POSITION_ATTR = "a_position";
	
	/** The color attribute. */
	private static final String COLOR_ATTR = "a_color";
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The shader. */
	private final ShaderProgram shader;
	
	/** The type of the shape. */
	private final int type;
	
	/** The color. */
	private final Color color;
	
	/** The mesh. */
	private final Mesh mesh;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param type - The type of the shape.
	 * @param verticesNum - The number of vertices.
	 * @param indices - The indices array.
	 * @param color - The color of the shape.
	 */
	public Shape(final ShaderProgram shader, final int type, final int verticesNum, final short[] indices, final Color color){
		this.shader = shader;
		this.type = type;
		this.color = color;
		
		final VertexAttribute positionAttr = new VertexAttribute(Usage.Position, POSITION_NUM, POSITION_ATTR);
		final VertexAttribute colorAttr = new VertexAttribute(Usage.ColorPacked, COLOR_NUM, COLOR_ATTR);
		
		mesh = new Mesh(true, verticesNum, indices.length, positionAttr, colorAttr);
		mesh.setIndices(indices);
	}

	// --------------------------------------------------
	// ~ Protected methods
	// --------------------------------------------------
	
	/**
	 * Set the vertices.
	 * 
	 * @param onlyVertices - The vertices array.
	 */
	protected void setVertices(final float[] onlyVertices){
		final float[] vertices = new float[onlyVertices.length + onlyVertices.length/POSITION_NUM];

		for(int i=0,j=0; i < onlyVertices.length; ++i, ++j){
			vertices[j]=onlyVertices[i];
			if((i+1)%POSITION_NUM == 0){
				vertices[++j]=color.toFloatBits();
			}
		}
		
		mesh.setVertices(vertices);
	}
	
	/**
	 * Return with the vertices array.
	 */
	protected float[] getVertices(){
		float[] vertices = new float[mesh.getMaxVertices()*2];
		vertices = mesh.getVertices(vertices);
		
		final float[] onlyVertices = new float[vertices.length - vertices.length/(POSITION_NUM+1)];
		for(int i=0, j=0; i<vertices.length; ++i){
			if((i+1)%(POSITION_NUM+1) != 0){
				onlyVertices[j++] = vertices[i];
			}
		}
		
		return onlyVertices;
	}

	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Draw the shape.
	 */
	public void draw(){
		mesh.render(shader, type);
	}
}
