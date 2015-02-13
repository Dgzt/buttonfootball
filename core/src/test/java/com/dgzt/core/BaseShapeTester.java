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

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.badlogic.gdx.graphics.Mesh;

/**
 * Base shape tester. Mock the mesh in the shape. 
 * Return the {@link Mesh#getVertices(float[])} with the parameter 
 * that give in the {@link Mesh#setVertices(float[])} method.
 * 
 * @author Dgzt
 */
public class BaseShapeTester {
	
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
	
	// --------------------------------------------------
	// ~ Inner classes
	// --------------------------------------------------
	
	/**
	 * The Vertices answer. Save the vertices.
	 * 
	 * @author Dgzt
	 */
	private class VerticesAnswer implements Answer<Object>{
		
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
