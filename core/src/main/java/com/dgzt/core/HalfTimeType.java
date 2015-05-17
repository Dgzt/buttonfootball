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

/**
 * The type of the half time.
 * 
 * @author Dgzt
 */
public enum HalfTimeType {

	NOT_IN_GAME(0),
	FIRST_HALF(1),
	SECOND_HALF(2);
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------

	private final int value;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	private HalfTimeType(final int value){
		this.value = value;
	}
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Return with type by value.
	 * 
	 * @param value - The value.
	 */
	public static HalfTimeType getByValue(final int value){
		for(final HalfTimeType halfType : HalfTimeType.values()){
			if(halfType.getValue() == value){
				return halfType;
			}
		}
		
		return null;
	}
	
	/**
	 * Return with the value.
	 */
	public int getValue(){
		return value;
	}
}
