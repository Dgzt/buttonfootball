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
package com.dgzt.core.setting;

/**
 * The player who will step first in the first half.
 * 
 * @author Dgzt
 */
public enum FirstStep {
	
	/** The player. */
	PLAYER("player"),
	
	/** The bot. */
	BOT("bot");
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The value. */
	private String firstStep;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	private FirstStep(final String firstStep){
		this.firstStep = firstStep;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	@Override
	public String toString() {
		return firstStep;
	}
}
