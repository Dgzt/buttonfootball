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
package com.dgzt.core.util;

/**
 * Util for bits
 * 
 * @author Dgzt
 */
public class BitsUtil {
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------

	/** 
	 * Bits for ball. 
	 * 
	 * 0000 0000 0000 0010
	 */
	public static final short BALL_BITS = 2; 
	
	/**
	 * Bits for buttons.
	 * 
	 * 0000 0000 0000 0100 
	 */
	public static final short BUTTON_BITS = 4;
	
	/** 
	 * Bits for walls.
	 * 
	 * 0000 0000 0000 1000 
	 */
	public static final short WALL_BITS = 8;
	
	/** 
	 * Bits for gate sensors.
	 * 
	 * 0000 0000 0001 0000 
	 */
	public static final short GATE_SENSOR_BITS = 16;
	
	/** 
	 * Bits for map.
	 * 
	 * 0000 0000 0010 0000 
	 */
	public static final short MAP_SENSOR_BITS = 32;
}
