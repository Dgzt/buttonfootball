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
package com.dgzt.java;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.dgzt.core.Player;
import com.dgzt.core.GameConstans;
import com.dgzt.core.setting.Settings;
import com.dgzt.core.setting.StepMode;

/**
 * The arguments for game.
 * 
 * @author Dgzt
 */
@Parameters(separators = "=")
public class Argument {
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------

	/** The player who will step first in the first half. */
	@Parameter(names = "--firststep", description = "The player who will first step in the first half.")
	private Player firstStep = GameConstans.DEFAULT_FIRST_STEP;
	
	/** The mode of step process. */
	@Parameter(names = "--stepmode", description = "The mode of step process.")
	private StepMode stepMode = GameConstans.DEFAULT_STEP_MODE;
	
	@Parameter(names = "--ballareasec", description = "The sec of the ball area.")
	private int ballAreaSec = GameConstans.DEFAULT_BALL_AREA_SEC;
	
	@Parameter(names = "--halftime", description = "The half time.")
	private int halfTime = GameConstans.DEFAULT_HALF_TIME;
	
	@Parameter(names = "--timeleft", description = "The time left in sec.")
	private int timeLeftSec = GameConstans.DEFAULT_TIME_LEFT_SEC;
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Convert to {@link Settings}.
	 */
	public Settings toSettings(){
		final Settings settings = new Settings();
		
		settings.setFirstStep(firstStep);
		settings.setStepMode(stepMode);
		settings.setBallAreaSec(ballAreaSec);
		settings.setHalfTime(halfTime);
		settings.setTimeLeftSec(timeLeftSec);
		
		return settings;
	}
	
	// --------------------------------------------------
	// ~ Getter / Setter methods
	// --------------------------------------------------

	public void setFirstStep(Player firstStep) {
		this.firstStep = firstStep;
	}

	public void setStepMode(final StepMode stepMode) {
		this.stepMode = stepMode;
	}
	
	public StepMode getStepMode() {
		return stepMode;
	}

	public Player getFirstStep() {
		return firstStep;
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
	
}
