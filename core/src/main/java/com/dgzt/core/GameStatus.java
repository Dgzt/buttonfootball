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
 * The game statuses.
 * 
 * @author Dgzt
 */
public enum GameStatus {
	
	/** Not in the game. */
	NOT_IN_GAME,
	
	/** The player is on the game. */
	PLAYER_IN_GAME,
	
	/** Waiting after player. */
	WAITING_AFTER_PLAYER, 
	
	/** The opponent is in the game. */
	OPPONENT_IN_GAME, 
	
	/** Waiting after opponent. */
	WAITING_AFTER_OPPONENT,
	
	/** The player move one button. */
	PLAYER_MOVE_ONE_BUTTON,
	
	/** The opponent move one button. */
	OPPONENT_MOVE_ONE_BUTTON,
	
	/** The player move some button. */
	PLAYER_MOVE_SOME_BUTTON,
	
	/** The opponent move some button. */
	OPPONENT_MOVE_SOME_BUTTON,
	
	/** Player goal. */
	PLAYER_GOAL,
	
	/** Opponent goal. */
	OPPONENT_GOAL;
	
}
