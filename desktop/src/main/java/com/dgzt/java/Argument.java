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

	/** The mode of step process. */
	@Parameter(names = "--stepmode", description = "The mode of step process.")
	private StepMode stepMode = GameConstans.DEFAULT_STEP_MODE;
	
	@Parameter(names = "--ballareasec", description = "The sec of the ball area.")
	private int ballAreaSec = GameConstans.DEFAULT_BALL_AREA_SEC;
	
	// --------------------------------------------------
	// ~ Public methods
	// --------------------------------------------------
	
	/**
	 * Convert to {@link Settings}.
	 */
	public Settings toSettings(){
		final Settings settings = new Settings();
		
		settings.setStepMode(stepMode);
		settings.setBallAreaSec(ballAreaSec);
		
		return settings;
	}
	
	// --------------------------------------------------
	// ~ Getter / Setter methods
	// --------------------------------------------------

	public StepMode getStepMode() {
		return stepMode;
	}

	public void setStepMode(final StepMode stepMode) {
		this.stepMode = stepMode;
	}

	public int getBallAreaSec() {
		return ballAreaSec;
	}

	public void setBallAreaSec(int ballAreaSec) {
		this.ballAreaSec = ballAreaSec;
	}
	
}