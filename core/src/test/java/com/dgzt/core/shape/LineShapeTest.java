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
import com.badlogic.gdx.math.Vector2;
import com.dgzt.core.BaseShapeTester;
import com.dgzt.core.GdxTestRunner;

/**
 * Test for {@link LineShape}.
 * 
 * @author Dgzt
 */
@RunWith(GdxTestRunner.class)
public final class LineShapeTest extends BaseShapeTester{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	private static final ShaderProgram SHADER_PROGRAM = null;
	
	private static final Color COLOR = Color.RED;
	
	private static final float X1 = 120f;
	private static final float Y1 = 30f;
	private static final float X2 = 340f;
	private static final float Y2 = 40f;
	
	private static final double SCALE = 3.3d;
	
	private static final double DELTA = 0.001d;
	
	// --------------------------------------------------
	// ~ Test methods
	// --------------------------------------------------
	
	/**
	 * Test for {@link LineShape#resize(float, float, float, float, double)} method.
	 */
	@Test
	public void test_resize(){
		final LineShape lineShape = getLineShape();
		lineShape.resize(X1, Y1, X2, Y2, SCALE);
		
		assertEquals(X1, lineShape.getX1(), DELTA);
		assertEquals(Y1, lineShape.getY1(), DELTA);
		assertEquals(X2, lineShape.getX2(), DELTA);
		assertEquals(Y2, lineShape.getY2(), DELTA);
	}

	/**
	 * Test for {@link LineShape#getLength()} method.
	 */
	@Test
	public void test_getLength(){
		final LineShape lineShape = getLineShape();
		lineShape.resize(X1, Y1, X2, Y2, SCALE);

		assertEquals(Vector2.dst(X1, Y1, X2, Y2), lineShape.getLength(), DELTA);
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------

	/**
	 * Return with the mock mesh.
	 */
	private LineShape getLineShape(){
		return new LineShape(SHADER_PROGRAM, COLOR){
			@Override
			protected Mesh getMesh(final boolean isStatic, final int verticesNum, final int maxIndices, final VertexAttribute... vAttribs) {
				return getMockMesh();
			}
		};
	}
}
