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

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.BaseShapeTester;

/**
 * Tests for {@link Shape} class.
 * 
 * @author Dgzt
 */
public class ShapeTest extends BaseShapeTester{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	private static final ShaderProgram SHADER_PROGRAM = null;
	
	private static final int SHAPE_TYPE = GL20.GL_TRIANGLES;
	
	private static final int VERTICES_NUM = 4;
	
	private static final short[] INDICES = new short[]{0,1,2,2,3,0};
	
	private static final Color COLOR = Color.RED;
	
	private static final float[] VERTICES = {3.4f, 6.5f, 1.3f, 10.1f};
	
	// --------------------------------------------------
	// ~ Tets methods
	// --------------------------------------------------
	
	/**
	 * Test for {@link Shape#getVertices()} method.
	 */
	@Test
	public void test_getVertices(){
		final Shape shape = getShape();
		
		shape.setVertices(VERTICES);
		assertTrue(Arrays.equals(VERTICES, shape.getVertices()));
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Return with the mock mesh.
	 */
	private Shape getShape(){
		return new Shape(SHADER_PROGRAM, SHAPE_TYPE, VERTICES_NUM, INDICES, COLOR){

			@Override
			protected Mesh getMesh(final boolean isStatic, final int verticesNum, final int maxIndices, final VertexAttribute... vAttribs) {
				return getMockMesh();
			}
		};
	}

}
