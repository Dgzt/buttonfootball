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
 * The player's enum.
 * 
 * @author Dgzt
 */
public enum Player {
	
	/** The player. */
	PLAYER("player"),
	
	/** The bot. */
	BOT("bot");
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The value. */
	private String player;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------
	
	private Player(final String player){
		this.player = player;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return player;
	}
}
