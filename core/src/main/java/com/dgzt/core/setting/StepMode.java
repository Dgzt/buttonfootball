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
 * The step process mode.
 * 
 * @author Dgzt
 */
public enum StepMode {
	
	/** The normal step. */
	NORMAL("normal"),
	
	/** Only the player step. */
	ALWAYS_PLAYER("always_player"),
	
	/** Only the bot step. */
	ALWAYS_BOT("always_bot");
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	private String stepMode;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	StepMode(final String stepMode){
		this.stepMode = stepMode;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return stepMode;
	}
	
}
