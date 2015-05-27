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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.dgzt.core.BaseShapeTester;
import com.dgzt.core.GdxTestRunner;

/**
 * Test for {@link FilledCircleShape}.
 * 
 * @author Dgzt
 */
@RunWith(GdxTestRunner.class)
public final class FilledCircleShapeTest extends BaseShapeTester{
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	private static final ShaderProgram SHADER_PROGRAM = null;
	
	private static final Color COLOR = Color.RED;
	
	private static final float X = 130f;
	private static final float Y = 23f;
	private static final float RADIUS = 12f;
	
	private static final double DELTA = 0.001d;
	
	// --------------------------------------------------
	// ~ Test methods
	// --------------------------------------------------
	
	/**
	 * Test for {@link FilledCircleShape#resize(float, float, float)} method.
	 */
	@Test
	public void test_resize(){
		final FilledCircleShape shape = getFilledCircleShape();
		shape.resize(X, Y, RADIUS);
		
		assertEquals(shape.getX(), X, DELTA);
		assertEquals(shape.getY(), Y, DELTA);
		assertEquals(shape.getRadius(), RADIUS, DELTA);
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------

	/**
	 * Return with the mock mesh.
	 */
	private FilledCircleShape getFilledCircleShape(){
		return new FilledCircleShape(SHADER_PROGRAM, COLOR){
			@Override
			protected Mesh getMesh(boolean isStatic, int verticesNum, int maxIndices, VertexAttribute... vAttribs) {
				return getMockMesh();
			}
		};
	}
}
