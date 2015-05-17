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

import java.util.Iterator;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

/**
 * The multi input processor.
 * Fix error in {@link InputMultiplexer} when remove processor in override methods.
 * 
 * @author Dgzt
 */
public class MultiInputProcessor implements InputProcessor{
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The maximum of processors. */
	private static final int MAX_PROCESSORS = 4;
	
	/** The processors. */
	private final Array<InputProcessor> processors;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public MultiInputProcessor(){
		processors = new Array<InputProcessor>(MAX_PROCESSORS);
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Add processor.
	 * 
	 * @param inputProcessor - The new input processor.
	 */
	public void add(final InputProcessor inputProcessor){
		processors.add(inputProcessor);
	}
	
	/**
	 * Remove processor.
	 * 
	 * @param inputProcessor - The input processor.
	 */
	public void remove(final InputProcessor inputProcessor){
		processors.removeValue(inputProcessor, true);
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean keyDown(int keycode) {
		final Iterator<InputProcessor> iterator = processors.iterator();
		
		while(iterator.hasNext()){
			final InputProcessor processor = iterator.next();
			
			if(processor.keyDown(keycode)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean keyUp(int keycode) {
		final Iterator<InputProcessor> iterator = processors.iterator();
		
		while(iterator.hasNext()){
			final InputProcessor processor = iterator.next();
			
			if(processor.keyUp(keycode)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean keyTyped(char character) {
		final Iterator<InputProcessor> iterator = processors.iterator();
		
		while(iterator.hasNext()){
			final InputProcessor processor = iterator.next();
			
			if(processor.keyTyped(character)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		final Iterator<InputProcessor> iterator = processors.iterator();
		
		while(iterator.hasNext()){
			final InputProcessor processor = iterator.next();
			
			if(processor.touchDown(screenX, screenY, pointer, button)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		final Iterator<InputProcessor> iterator = processors.iterator();
		
		while(iterator.hasNext()){
			final InputProcessor processor = iterator.next();
			
			if(processor.touchUp(screenX, screenY, pointer, button)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		final Iterator<InputProcessor> iterator = processors.iterator();
		
		while(iterator.hasNext()){
			final InputProcessor processor = iterator.next();
			
			if(processor.touchDragged(screenX, screenY, pointer)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		final Iterator<InputProcessor> iterator = processors.iterator();
		
		while(iterator.hasNext()){
			final InputProcessor processor = iterator.next();
			
			if(processor.mouseMoved(screenX, screenY)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean scrolled(int amount) {
		final Iterator<InputProcessor> iterator = processors.iterator();
		
		while(iterator.hasNext()){
			final InputProcessor processor = iterator.next();
			
			if(processor.scrolled(amount)){
				return true;
			}
		}
		
		return false;
	}

}
