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
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;
import com.dgzt.core.gate.LeftGate;
import com.dgzt.core.gate.RightGate;

/**
 * Test for {@link Table}.
 * 
 * @author Dgzt
 */
public final class TableTest extends BaseShapeTester{

	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	private static final Rectangle TABLE_RECTANGLE = new Rectangle(100.0f, 200.0f, 80.0f, 60.0f);
	private static final double SCALE = 1.0f;
	private static final Vector2 MAP_BOX2D_POSITION = new Vector2(10.0f, 15.0f);
	
	private static final float DISTANCE = 11.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private Table table;
	
	// --------------------------------------------------
	// ~ Init methods
	// --------------------------------------------------
	
	/**
	 * Init method for tests.
	 */
	@Before
	public void setUp(){
		// Shader
		final ShaderProgram shader = Mockito.mock(ShaderProgram.class);
		
		// Box2D world
		final World box2DWorld = new World(new Vector2(0,0), true);
		
		// Event Listener
		final EventListener eventListener = Mockito.mock(EventListener.class);
		
		// Map
		final Map map = Mockito.mock(Map.class);
		Mockito.when(map.getBox2DX()).thenReturn(MAP_BOX2D_POSITION.x);
		Mockito.when(map.getBox2DY()).thenReturn(MAP_BOX2D_POSITION.y);
		Mockito.when(map.getTopLeftCornerBox2DPosition()).thenReturn(MAP_BOX2D_POSITION);
		Mockito.when(map.getTopRightCornerBox2DPosition()).thenReturn(new Vector2(MAP_BOX2D_POSITION.x + Map.WIDTH, MAP_BOX2D_POSITION.y));
		Mockito.when(map.getBottomLeftCornerBox2DPosition()).thenReturn(new Vector2(MAP_BOX2D_POSITION.x, MAP_BOX2D_POSITION.y + Map.HEIGHT));
		Mockito.when(map.getBottomRightCornerBox2DPosition()).thenReturn(new Vector2(MAP_BOX2D_POSITION.x + Map.WIDTH, MAP_BOX2D_POSITION.y + Map.HEIGHT));
		
		table = new Table(shader, box2DWorld, eventListener){
			
			@Override
			protected Mesh getMesh(final boolean isStatic, final int verticesNum, final int maxIndices, final VertexAttribute... vAttribs) {
				return getMockMesh();
			}
			
			@Override
			protected Map createMap(final ShaderProgram shader, final World box2dWorld, final float box2dx, final float box2dy) {
				return map;
			}
			
			@Override
			protected LeftGate createLeftGate(final ShaderProgram shader, final World box2dWorld, final float box2dx, final float box2dy) {
				return Mockito.mock(LeftGate.class);
			}
			
			@Override
			protected RightGate createRightGate(final ShaderProgram shader, final World box2dWorld, final float box2dx, final float box2dy) {
				return Mockito.mock(RightGate.class);
			}
			
			@Override
			protected Button createButton(final ShaderProgram shader, final EventListener eventListener, final World box2dWorld, final Color color) {
				return Mockito.mock(Button.class);
			}
			
			@Override
			protected Ball createBall(final ShaderProgram shader, final EventListener eventListener, final World box2dWorld) {
				return getMockBall();
			}
		};
	}
	
	// --------------------------------------------------
	// ~ Test methods
	// --------------------------------------------------
	
	/**
	 * Test for {@link Table#isBallOnTopLeftCornerOfMap()} method.
	 */
	@Test
	public void test_isBallOnTopLeftCornerOfMap(){
		final Ball ball = table.getBall();
		
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + DISTANCE, MAP_BOX2D_POSITION.y);
		assertFalse(table.isBallOnTopLeftCornerOfMap());
		
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x, MAP_BOX2D_POSITION.y);
		assertTrue(table.isBallOnTopLeftCornerOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnTopBorderOfMap()} method.
	 */
	@Test
	public void test_isBallOnTopBorderOfMap(){
		final Ball ball = table.getBall();

		// False on the top left corner of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x, MAP_BOX2D_POSITION.y);
		assertFalse(table.isBallOnTopBorderOfMap());
		
		// False on the top right corner of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + Map.WIDTH, MAP_BOX2D_POSITION.y);
		assertFalse(table.isBallOnTopBorderOfMap());
		
		// True on top border of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + DISTANCE, MAP_BOX2D_POSITION.y);
		assertTrue(table.isBallOnTopBorderOfMap());
		
		// False on bottom border of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + DISTANCE, MAP_BOX2D_POSITION.y + Map.HEIGHT);
		assertFalse(table.isBallOnTopBorderOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnTopRightCornerOfMap()} method.
	 */
	@Test
	public void test_isBallOnTopRightCornerOfMap(){
		final Ball ball = table.getBall();

		// False on top border of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + Map.WIDTH - DISTANCE, MAP_BOX2D_POSITION.y);
		assertFalse(table.isBallOnTopRightCornerOfMap());
		
		// True on top right corner of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + Map.WIDTH, MAP_BOX2D_POSITION.y);
		assertTrue(table.isBallOnTopRightCornerOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnBottomLeftCornerOfMap()} method.
	 */
	@Test
	public void test_isBallOnBottomLeftCornerOfMap(){
		final Ball ball = table.getBall();
		
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + DISTANCE, MAP_BOX2D_POSITION.y + Map.HEIGHT);
		assertFalse(table.isBallOnBottomLeftCornerOfMap());
		
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x, MAP_BOX2D_POSITION.y + Map.HEIGHT);
		assertTrue(table.isBallOnBottomLeftCornerOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnBottomBorderOfMap()} method.
	 */
	@Test
	public void test_isBallOnBottomBorderOfMap(){
		final Ball ball = table.getBall();

		// False on the bottom left corner of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x, MAP_BOX2D_POSITION.y + Map.HEIGHT);
		assertFalse(table.isBallOnBottomBorderOfMap());
		
		// False on the bottom right corner of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + Map.WIDTH, MAP_BOX2D_POSITION.y + Map.HEIGHT);
		assertFalse(table.isBallOnBottomBorderOfMap());
		
		// True on bottom border of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + DISTANCE, MAP_BOX2D_POSITION.y + Map.HEIGHT);
		assertTrue(table.isBallOnBottomBorderOfMap());
		
		// False on top border of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + DISTANCE, MAP_BOX2D_POSITION.y);
		assertFalse(table.isBallOnBottomBorderOfMap());
	}
	
	/**
	 * Test for {@link Table#isBallOnBottomRightCornerOfMap()} method.
	 */
	@Test
	public void test_isBallOnBottomRightCornerOfMap(){
		final Ball ball = table.getBall();

		// False on bottom border of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + Map.WIDTH - DISTANCE, MAP_BOX2D_POSITION.y +Map.HEIGHT);
		assertFalse(table.isBallOnBottomRightCornerOfMap());
		
		// True on bottom right corner of map
		ball.setBox2DPosition(MAP_BOX2D_POSITION.x + Map.WIDTH, MAP_BOX2D_POSITION.y + Map.HEIGHT);
		assertTrue(table.isBallOnBottomRightCornerOfMap());
	}

	/**
	 * Test for {@link Table#isButtonPositionOnTable(float, float)} method.
	 */
	@Test
	public void test_isButtonPositionOnTable(){
		table.resize(TABLE_RECTANGLE.x, TABLE_RECTANGLE.y, TABLE_RECTANGLE.width, TABLE_RECTANGLE.height, SCALE);
		final float width = (float) (TABLE_RECTANGLE.width * SCALE);
		final float height = (float) (TABLE_RECTANGLE.height * SCALE);
		
		// The correct
		assertTrue(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x + DISTANCE, TABLE_RECTANGLE.y + DISTANCE)));
		
		// Left the table
		assertFalse(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x, TABLE_RECTANGLE.y + DISTANCE)));
		
		// Up the table
		assertFalse(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x + DISTANCE, TABLE_RECTANGLE.y)));
		
		// Right the table
		assertFalse(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x + width, TABLE_RECTANGLE.y + height - DISTANCE)));
		
		// Down the table
		assertFalse(table.isButtonPositionOnTable(new Vector2(TABLE_RECTANGLE.x + width - DISTANCE, TABLE_RECTANGLE.y + height)));
	}
}
