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
 * Math utils.
 * 
 * @author Dgzt
 */
public class MathUtil {
	
	// --------------------------------------------------
	// ~ Public static methods
	// --------------------------------------------------
	
	/**
	 * Distance from the origo.
	 * 
	 * @param x - The x coordinate value.
	 * @param y - The y coordinate value.
	 */
	public static double distance(final float x, final float y){
		return distance(0, 0, x, y);
	}

	/**
	 * Distance from two point.
	 * 
	 * @param x1 - The first x coordinate value.
	 * @param y1 - The first y coordinate value.
	 * @param x2 - The second x coordinate value.
	 * @param y2 - The second y coordinate value.
	 */
	public static double distance(final float x1, final float y1, final float x2, final float y2) {
		final float x = x2 - x1;
		final float y = y2 - y1;
		
		return Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
	}
	
}
