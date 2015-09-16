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

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector2;
import com.dgzt.core.button.Ball;
import com.dgzt.core.button.Button;

/**
 * Base shape tester. Mock the mesh in the shape. 
 * Return the {@link Mesh#getVertices(float[])} with the parameter 
 * that give in the {@link Mesh#setVertices(float[])} method.
 * 
 * @author Dgzt
 */
@RunWith(GdxTestRunner.class)
public class BaseShapeTester {

	// --------------------------------------------------
	// ~ Protected static members
	// --------------------------------------------------
	
	protected static final double DELTA = 0.0001d;
	
	// --------------------------------------------------
	// ~ Protected methods
	// --------------------------------------------------
	
	/**
	 * Return with the mock {@link Mesh} object.
	 */
	protected Mesh getMockMesh(){
		final Mesh mesh = Mockito.mock(Mesh.class);
		
		final VerticesAnswer verticesAnswer = new VerticesAnswer();
		
		Mockito.doAnswer(verticesAnswer).when(mesh).setVertices(Mockito.any(float[].class));
		
		Mockito.when(mesh.getVertices(Mockito.any(float[].class))).thenAnswer(new Answer<float[]>() {
		    @Override
		    public float[] answer(InvocationOnMock invocation) throws Throwable {
		        return verticesAnswer.getVertices();
		    }
		});
		
		return mesh;
	}
	
	/**
	 * Return with mock {@link Button}.
	 */
	protected Button getMockButton(){
		final Button button = Mockito.mock(Button.class);
		final Vector2 box2DPosition = new Vector2();
		
		Mockito.doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				final float argumentBox2DPosX = (Float) invocation.getArguments()[0];
				final float argumentBox2DPosY = (Float) invocation.getArguments()[1];
				
				box2DPosition.set(argumentBox2DPosX, argumentBox2DPosY);
				
				return null;
			}
		}).when(button).setBox2DPosition(Mockito.anyFloat(), Mockito.anyFloat());
		
		Mockito.doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				final Vector2 newPosition = (Vector2) invocation.getArguments()[0];
				
				box2DPosition.set(newPosition);
				
				return null;
			}
		}).when(button).setBox2DPosition(Mockito.any(Vector2.class));
		
		Mockito.when(button.getBox2DPosition()).thenAnswer(new Answer<Vector2>() {

			@Override
			public Vector2 answer(InvocationOnMock invocation) throws Throwable {
				return box2DPosition.cpy();
			}
		});
		
		Mockito.when(button.getBox2DX()).thenAnswer(new Answer<Float>(){

			@Override
			public Float answer(InvocationOnMock invocation) throws Throwable {
				return box2DPosition.x;
			}
			
		});
		
		Mockito.when(button.getBox2DY()).thenAnswer(new Answer<Float>(){

			@Override
			public Float answer(InvocationOnMock invocation) throws Throwable {
				return box2DPosition.y;
			}
			
		});
		
		return button;
	}
	
	/**
	 * Return with mock {@link Ball}.
	 */
	protected Ball getMockBall(){
		final Ball ball = Mockito.mock(Ball.class);
		final Vector2 box2DPosition = new Vector2();
		
		Mockito.doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				final float argumentBox2DPosX = (Float) invocation.getArguments()[0];
				final float argumentBox2DPosY = (Float) invocation.getArguments()[1];
				
				box2DPosition.set(argumentBox2DPosX, argumentBox2DPosY);
				
				return null;
			}
		}).when(ball).setBox2DPosition(Mockito.anyFloat(), Mockito.anyFloat());
		
		Mockito.doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				final Vector2 newPosition = (Vector2) invocation.getArguments()[0];
				
				box2DPosition.set(newPosition);
				
				return null;
			}
		}).when(ball).setBox2DPosition(Mockito.any(Vector2.class));
		
		Mockito.when(ball.getBox2DPosition()).thenAnswer(new Answer<Vector2>() {

			@Override
			public Vector2 answer(InvocationOnMock invocation) throws Throwable {
				return box2DPosition.cpy();
			}
		});
		
		Mockito.when(ball.getBox2DX()).thenAnswer(new Answer<Float>(){

			@Override
			public Float answer(InvocationOnMock invocation) throws Throwable {
				return box2DPosition.x;
			}
			
		});
		
		Mockito.when(ball.getBox2DY()).thenAnswer(new Answer<Float>(){

			@Override
			public Float answer(InvocationOnMock invocation) throws Throwable {
				return box2DPosition.y;
			}
			
		});
		
		return ball;
	}
	
	/**
	 * Return with an answer with the copy of the given value.
	 * 
	 * @param returnValue - The return value. 
	 */
	protected Answer<Vector2> getVector2Answer(final Vector2 returnValue){
		return new Answer<Vector2>(){

			@Override
			public Vector2 answer(InvocationOnMock invocation) throws Throwable {
				return returnValue.cpy();
			}
			
		};
	}
	
	// --------------------------------------------------
	// ~ Inner classes
	// --------------------------------------------------
	
	/**
	 * The Vertices answer. Save the vertices.
	 * 
	 * @author Dgzt
	 */
	private final class VerticesAnswer implements Answer<Object>{
		
		// --------------------------------------------------
		// ~ Private members
		// --------------------------------------------------
		
		private float[] vertices;
		
		// --------------------------------------------------
		// ~ Public methods
		// --------------------------------------------------

		/**
		 * Return with the given vertices.
		 */
		public float[] getVertices(){
			return vertices;
		}
		
		// --------------------------------------------------
		// ~ Override methods
		// --------------------------------------------------
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object answer(InvocationOnMock invocation) throws Throwable {
			vertices = (float[]) invocation.getArguments()[0];
			return null;
		}

	}

}
