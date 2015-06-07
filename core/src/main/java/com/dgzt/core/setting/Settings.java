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

import com.dgzt.core.Player;
import com.dgzt.core.GameConstans;

/**
 * The settings.
 * 
 * @author Dgzt
 */
public class Settings {
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The player who will first step in the first half. */
	private Player firstStep;

	/** The step mode. */
	private StepMode stepMode;
	
	/** The sec of the ball area. */
	private int ballAreaSec;
	
	/** The half time in sec. */
	private int halfTime;
	
	/**	The time left in sec. */
	private int timeLeftSec;
	
	// --------------------------------------------------
	// ~ Constructors.
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public Settings(){
		firstStep = GameConstans.DEFAULT_FIRST_STEP;
		stepMode = GameConstans.DEFAULT_STEP_MODE;
		ballAreaSec = GameConstans.DEFAULT_BALL_AREA_SEC;
		halfTime = GameConstans.DEFAULT_HALF_TIME;
		timeLeftSec = GameConstans.DEFAULT_TIME_LEFT_SEC;
	}
	
	// --------------------------------------------------
	// ~ Getter / Setter methods
	// --------------------------------------------------
	
	public Player getFirstStep(){
		return firstStep;
	}
	
	public void setFirstStep(final Player firstStep){
		this.firstStep = firstStep;
	}

	public StepMode getStepMode() {
		return stepMode;
	}

	public void setStepMode(StepMode stepMode) {
		this.stepMode = stepMode;
	}

	public int getBallAreaSec() {
		return ballAreaSec;
	}

	public void setBallAreaSec(int ballAreaSec) {
		this.ballAreaSec = ballAreaSec;
	}

	public int getHalfTime() {
		return halfTime;
	}

	public void setHalfTime(int halfTime) {
		this.halfTime = halfTime;
	}
	
	public int getTimeLeftSec() {
		return timeLeftSec;
	}

	public void setTimeLeftSec(int timeLeftSec) {
		this.timeLeftSec = timeLeftSec;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Settings {" +
				" firstStep = " + firstStep + "," +
				" stepMode = " + stepMode + "," +
				" ballAreaSec = " + ballAreaSec + "," +
				" halfTime = " + halfTime + "," +
				" timeLeftSec = " + timeLeftSec + 
				" }";
	}
	
}
