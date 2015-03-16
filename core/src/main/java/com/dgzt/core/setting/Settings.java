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

	/** The step mode. */
	private StepMode stepMode;
	
	/** The sec of the ball area. */
	private int ballAreaSec;
	
	// --------------------------------------------------
	// ~ Constructors.
	// --------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public Settings(){
		stepMode = GameConstans.DEFAULT_STEP_MODE;
		ballAreaSec = GameConstans.DEFAULT_BALL_AREA_SEC;
	}
	
	// --------------------------------------------------
	// ~ Getter / Setter methods
	// --------------------------------------------------

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
	
}
