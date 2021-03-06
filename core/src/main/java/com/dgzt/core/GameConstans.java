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

import com.dgzt.core.setting.StepMode;

/**
 * The game constans.
 * 
 * @author Dgzt
 */
public final class GameConstans {

	/** The default first step. */
	public static final Player DEFAULT_FIRST_STEP = Player.PLAYER;
	
	/** The default step mode. */
	public static final StepMode DEFAULT_STEP_MODE = StepMode.NORMAL;
	
	/** The default sec of the ball area. */
	public static final int DEFAULT_BALL_AREA_SEC = 5;
	
	/** The default half time in sec. */
	public static final int DEFAULT_HALF_TIME = 13 * 60;
	
	/** The default time left in sec. */
	public static final int DEFAULT_TIME_LEFT_SEC = 15;
	
	// Hidden constructor
	private GameConstans(){}
}
