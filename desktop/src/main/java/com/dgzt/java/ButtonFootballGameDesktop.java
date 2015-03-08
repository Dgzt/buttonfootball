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
package com.dgzt.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.beust.jcommander.JCommander;
import com.dgzt.core.ButtonFootballGame;

/**
 * The desktop interface for the game.
 * 
 * @author Dgzt
 */
public class ButtonFootballGameDesktop {
	
	/**
	 * The main function.
	 * 
	 * @param args - The arguments.
	 */
	public static void main (String[] args) {
		
		final ButtonFootballArgument bfa = new ButtonFootballArgument();
		new JCommander(bfa, args);
		
		
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new ButtonFootballGame(bfa.getStepType()), config);
	}
}
