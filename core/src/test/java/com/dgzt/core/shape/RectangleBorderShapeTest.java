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
 * Test for {@link RectangleBorderShapeTest}.
 * 
 * @author Dgzt
 */
public final class RectangleBorderShapeTest extends BaseShapeTester{
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------

	private final Color COLOR = Color.RED;
	private final float X = 120.0f;
	private final float Y = 190.0f;
	private final float WIDTH = 110.0f;
	private final float HEIGHT = 45.0f;
	private final double SCALE = 1.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private RectangleBorderShape rectangleBorderShape;

	// --------------------------------------------------
	// ~ Init methods
	// --------------------------------------------------
	
	/**
	 * Init method.
	 */
	@Before
	public void setUp(){
		final ShaderProgram shader = Mockito.mock(ShaderProgram.class);
		
		rectangleBorderShape = new RectangleBorderShape(shader, COLOR){
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
	 * Test for {@link RectangleBorderShape#getX()} method.
	 */
	@Test
	public void test_getX(){
		rectangleBorderShape.resize(X, Y, WIDTH, HEIGHT, SCALE);
		
		Assert.assertEquals(X, rectangleBorderShape.getX(), DELTA);
	}
	
	/**
	 * Test for {@link RectangleBorderShape#getY()} method.
	 */
	@Test
	public void test_getY(){
		rectangleBorderShape.resize(X, Y, WIDTH, HEIGHT, SCALE);
		
		Assert.assertEquals(Y, rectangleBorderShape.getY(), DELTA);
	}

	/**
	 * Test for {@link RectangleBorderShape#getWidth()} method.
	 */
	@Test
	public void test_getWidth(){
		rectangleBorderShape.resize(X, Y, WIDTH, HEIGHT, SCALE);
		
		Assert.assertEquals(WIDTH, rectangleBorderShape.getWidth(), DELTA);
	}
	
	/**
	 * Test for {@link RectangleBorderShape#getHeight()} method.
	 */
	@Test
	public void test_getHeight(){
		rectangleBorderShape.resize(X, Y, WIDTH, HEIGHT, SCALE);
		
		Assert.assertEquals(HEIGHT, rectangleBorderShape.getHeight(), DELTA);
	}

	/**
	 * Test for {@link RectangleBorderShape#getScale()} method.
	 */
	@Test
	public void test_getScale(){
		rectangleBorderShape.resize(X, Y, WIDTH, HEIGHT, SCALE);
		
		Assert.assertEquals(SCALE, rectangleBorderShape.getScale(), DELTA);
	}
}
