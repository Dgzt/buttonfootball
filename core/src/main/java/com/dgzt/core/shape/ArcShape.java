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

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Arc shape.
 * 
 * @author Dgzt
 */
public class ArcShape extends Shape{
	
	// --------------------------------------------------
	// ~ Static members
	// --------------------------------------------------
	
	/** The radius of the small arcs. */
	public static final float SMALL_RADIUS = 4.0f;

	/** The radius of the small arcs. */
	public static final float BIG_RADIUS = 30.0f;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The start degrees. */
	private final int startDegrees;
	
	/** The number of degrees. */
	private final int degreesNum;
	
	/** The x coordinate value. */
	private float x;
	
	/** The y coordinate value. */
	private float y;	
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * The constructor.
	 * 
	 * @param shader - The shader.
	 * @param backgroundColor - The color of background.
	 * @param arcColor - The color of arc.
	 * @param start - The start degrees.
	 * @param degrees - The number of degrees.
	 */
	public ArcShape(final ShaderProgram shader, final int startDegrees, final int degreesNum, final Color color ){
		super(shader, GL20.GL_TRIANGLES, degreesNum*2, getIndices(degreesNum), color);
		this.startDegrees = startDegrees;
		this.degreesNum = degreesNum;
	}

	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Return with the indices.
	 * 
	 * @param degrees - The number of degrees.
	 */
	private static short[] getIndices(final int degrees){
		final List<Short> tmpList = new ArrayList<Short>();
		for(short i=0,num=0;i<degrees*2;++i,++num){
			if(num!=0 && num%3==0){
				num=0;
				i-=2;
			}
			tmpList.add(new Short(i));
		}
		
		final short[] indices = new short[tmpList.size()];
		for(int i=0; i < tmpList.size(); ++i){
			indices[i]=tmpList.get(i).shortValue();
		}
		
		return indices;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Resize the arc.
	 * 
	 * @param x - The new x coordinate value.
	 * @param y - The new y coordinate value.
	 * @param radius - The new radius value.
	 * @param scale - The scale.
	 */
	public void resize(final float x, final float y, final float radius, final double scale){
		this.x = x;
		this.y = y;
		
		final float lineWidth = (float) (LineShape.LINE_WIDTH * scale);
		
		final float smallRadius = radius - lineWidth;
		
		final float[] vertices = new float[degreesNum*Shape.POSITION_NUM*2];
		
		
		for(int i=0,degrees=startDegrees;degrees<startDegrees+degreesNum;++degrees){
			final float angle = (float) Math.toRadians(degrees);
			
			vertices[i++]=x + (float)Math.sin( angle ) * radius;
			vertices[i++]=y + (float)Math.cos( angle ) * radius;
			
			vertices[i++]=x + (float)Math.sin( angle ) * smallRadius;
			vertices[i++]=y + (float)Math.cos( angle ) * smallRadius;
		}
		
		super.setVertices(vertices);
	}
	
	// --------------------------------------------------
	// ~ Getter methods
	// --------------------------------------------------
	
	/**
	 * Return with the x coordinate value.
	 */
	public final float getX(){
		return x;
	}

	/**
	 * Return with the y coordiante value.
	 */
	public final float getY(){
		return y;
	}
}
