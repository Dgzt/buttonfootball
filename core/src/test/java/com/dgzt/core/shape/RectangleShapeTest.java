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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.BaseShapeTester;

/**
 * Test for {@link RectangleShapeTest}.
 * 
 * @author Dgzt
 */
public final class RectangleShapeTest extends BaseShapeTester{
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------

	private static final Color COLOR = Color.RED;
	private static final float X = 120.0f;
	private static final float Y = 190.0f;
	private static final float WIDTH = 110.0f;
	private static final float HEIGHT = 45.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private RectangleShape rectangleShape;

	// --------------------------------------------------
	// ~ Init methods
	// --------------------------------------------------
	
	/**
	 * Init method.
	 */
	@Before
	public void setUp(){
		final ShaderProgram shader = Mockito.mock(ShaderProgram.class);
		
		rectangleShape = new RectangleShape(shader, COLOR){
			@Override
			protected Mesh getMesh(final boolean isStatic, final int verticesNum, final int maxIndices, final VertexAttribute... vAttribs) {
				return getMockMesh();
			}
		};
	}
	
	// --------------------------------------------------
	// ~ Test methods
	// --------------------------------------------------
	
	/**
	 * Test for {@link RectangleShape#getHeight()} method.
	 */
	@Test
	public void test_getHeight(){
		rectangleShape.resize(X, Y, WIDTH, HEIGHT);
		
		Assert.assertEquals(HEIGHT, rectangleShape.getHeight(), DELTA);
	}
	
}
