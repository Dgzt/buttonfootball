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
package com.dgzt.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.dgzt.core.shape.ArcShape;
import com.dgzt.core.shape.CircleBorderShape;
import com.dgzt.core.shape.FilledCircleShape;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;
import com.dgzt.core.util.Box2DDataUtil;

/**
 * Test for {@link Map}.
 * 
 * @author Dgzt
 */
public final class MapTest extends BaseShapeTester{
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private static final Rectangle MAP_BOX2D_RECTANGLE = Box2DDataUtil.MAP_RECTANGLE;
	
	private Map map;
	
	// --------------------------------------------------
	// ~ Setup method
	// --------------------------------------------------
	
	@Before
	public void setUp(){
		// Shader
		final ShaderProgram shader = Mockito.mock(ShaderProgram.class);
		
		// Box2D world
		final World box2DWorld = new World(new Vector2(0,0), true);
		
		map = new Map(shader, box2DWorld){
			
			@Override
			protected Mesh getMesh(final boolean isStatic, final int verticesNum, final int maxIndices, final VertexAttribute... vAttribs) {
				return getMockMesh();
			}
			
			@Override
			protected RectangleBorderShape getRectangleBorderShape(final ShaderProgram shader, final Color color) {
				return Mockito.mock(RectangleBorderShape.class);
			}
			
			@Override
			protected CircleBorderShape getCircleBorderShape(final ShaderProgram shader, final Color color) {
				return Mockito.mock(CircleBorderShape.class);
			}
			
			@Override
			protected LineShape getLineShape(final ShaderProgram shader, final Color color) {
				return Mockito.mock(LineShape.class);
			}
			
			@Override
			protected FilledCircleShape getFilledCircleShape(final ShaderProgram shader, final Color color) {
				return Mockito.mock(FilledCircleShape.class);
			}
			
			@Override
			protected ArcShape getArcShape(final ShaderProgram shader, final int startDegrees, final int degreesNum, final Color color) {
				return Mockito.mock(ArcShape.class);
			}
		};
	}
	
	// --------------------------------------------------
	// ~ Test methods
	// --------------------------------------------------
	
	@Test
	public void test_getTopLeftCornerBox2DPosition(){
		assertEquals(map.getTopLeftCornerBox2DPosition(), new Vector2(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y));
	}
	
	@Test
	public void test_getTopRightCornerBox2DPosition(){
		assertEquals(map.getTopRightCornerBox2DPosition(), new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y));
	}
	
	@Test
	public void test_getBottomLeftCornerBox2DPosition(){
		assertEquals(map.getBottomLeftCornerBox2DPosition(), new Vector2(MAP_BOX2D_RECTANGLE.x, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height));
	}
	
	@Test
	public void test_getBottomRightCornerBox2DPosition(){
		assertEquals(map.getBottomRightCornerBox2DPosition(), new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height));
	}

	/**
	 * Test for {@link Map#containsBox2DPosition(Vector2)} method.
	 */
	@Test
	public void test_containsBox2DPosition(){
		final Vector2 containsPoint = new Vector2(MAP_BOX2D_RECTANGLE.x + 10, MAP_BOX2D_RECTANGLE.y + 30);
		final Vector2 notContainsPoint = new Vector2(MAP_BOX2D_RECTANGLE.x - 4, MAP_BOX2D_RECTANGLE.y + 30);
		
		assertTrue(map.containsBox2DPosition(containsPoint));
		assertFalse(map.containsBox2DPosition(notContainsPoint));
	}
	
	/**
	 * Test for {@link Map#containsLeftSector16Box2DPosition(Vector2)} method.
	 */
	@Test
	public void test_containsLeftSector16Box2DPosition(){
		final Vector2 inLeftSector16 = new Vector2(MAP_BOX2D_RECTANGLE.x + 10, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height / 2);
		final Vector2 notInLeftSector16 = new Vector2(MAP_BOX2D_RECTANGLE.x + 40, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height / 2);
		
		assertTrue(map.containsLeftSector16Box2DPosition(inLeftSector16));
		assertFalse(map.containsLeftSector16Box2DPosition(notInLeftSector16));
	}
	
	/**
	 * Test for {@link Map#containsRightSector16Box2DPosition(Vector2)} method.
	 */
	@Test
	public void test_containsRightSector16Box2DPosition(){
		final Vector2 inRightSector16 = new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width - 10, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height / 2);
		final Vector2 notInRightSector16 = new Vector2(MAP_BOX2D_RECTANGLE.x + MAP_BOX2D_RECTANGLE.width - 40, MAP_BOX2D_RECTANGLE.y + MAP_BOX2D_RECTANGLE.height / 2);
		
		assertTrue(map.containsRightSector16Box2DPosition(inRightSector16));
		assertFalse(map.containsRightSector16Box2DPosition(notInRightSector16));
	}
}
