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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Border of the rectangle.
 * 
 * @author Dgzt
 */
public class RectangleBorderShape{

	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The bottom line. */
	private final LineShape topLine;
	
	/** The right line. */
	private final LineShape rightLine;
	
	/** The bottom line. */
	private final LineShape bottomLine;
	
	/** The left line. */
	private final LineShape leftLine;
	
	/** The scale. */
	private double scale;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	public RectangleBorderShape(final ShaderProgram shader, final Color color){
		topLine = createLineShape(shader, color);
		rightLine = createLineShape(shader, color);
		bottomLine = createLineShape(shader, color);
		leftLine = createLineShape(shader, color);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the shape.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 * @param width - The width value.
	 * @param height - The height value.
	 * @param scale The scale value.
	 */
	public void resize(final float x, final float y, final float width, final float height, final double scale){
		this.scale = scale;
		final float halfLineWidth = (float) (LineShape.LINE_WIDTH * scale) / 2;
		bottomLine.resize(x, y + halfLineWidth, x + width, y + halfLineWidth, scale);
		rightLine.resize(x + width - halfLineWidth, y, x + width - halfLineWidth, y + height, scale);
		topLine.resize(x, y + height - halfLineWidth, x + width, y + height - halfLineWidth, scale);
		leftLine.resize(x + halfLineWidth, y, x + halfLineWidth, y + height, scale);
	}
	
	/**
	 * Draw the rectangle border.
	 */
	public void draw(){
		topLine.draw();
		rightLine.draw();
		bottomLine.draw();
		leftLine.draw();
	}
	
	/**
	 * Dispose the rectangle border.
	 */
	public void dispose(){
		topLine.dispose();
		rightLine.dispose();
		bottomLine.dispose();
		leftLine.dispose();
	}
	
	// --------------------------------------------------
	// ~ Protected methods
	// --------------------------------------------------
	
	/**
	 * Return with {@link Mesh}. If the return object is null then use the origin mesh of the {@link LineShape} else this.
	 * Create for tests.
	 * 
	 * @param isStatic - Is static.
	 * @param verticesNum - The number of vertices.
	 * @param maxIndices - The max indices.
	 * @param vAttribs - The attributes.
	 */
	protected Mesh getMesh(final boolean isStatic, final int verticesNum, final int maxIndices, final VertexAttribute... vAttribs){
		return null;
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Create a {@link LineShape} object.
	 * 
	 * @param shader - The shader.
	 * @param color - The color.
	 */
	private LineShape createLineShape(final ShaderProgram shader, final Color color){
		return new LineShape(shader, color){
			
			@Override
			protected Mesh getMesh(final boolean isStatic, final int verticesNum, final int maxIndices, final VertexAttribute... vAttribs) {
				final Mesh rectangleBorderShapeMesh = RectangleBorderShape.this.getMesh(isStatic, verticesNum, maxIndices, vAttribs);
				
				return rectangleBorderShapeMesh == null ? super.getMesh(isStatic, verticesNum, maxIndices, vAttribs) : rectangleBorderShapeMesh;
			}
		};
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------

	/**
	 * Return with x coordinate value;
	 */
	public final float getX(){
		return bottomLine.getX1();
	}
	
	/**
	 * Return with y coordinate value;
	 */
	public final float getY(){
		final float halfLineWidth = (float) (LineShape.LINE_WIDTH * scale) / 2;
		
		return bottomLine.getY1() - halfLineWidth;
	}
	
	/**
	 * Return with width value.
	 */
	public final float getWidth(){
		return bottomLine.getLength();
	}
	
	/**
	 * Return width height value.
	 */
	public final float getHeight(){
		return rightLine.getLength();
	}
	
	/**
	 * Return with the scale.
	 */
	public final double getScale(){
		return scale;
	}
}
