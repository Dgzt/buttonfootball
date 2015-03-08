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
import com.dgzt.core.type.StepType;

/**
 * The arguments for game.
 * 
 * @author Dgzt
 */
@Parameters(separators = "=")
public class ButtonFootballArgument {
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------

	/** The type of step process. */
	@Parameter(names = "--steptype", description = "The type of step process.")
	private StepType stepType = StepType.NORMAL;
	
	// --------------------------------------------------
	// ~ Getter / Setter methods
	// --------------------------------------------------

	public StepType getStepType() {
		return stepType;
	}

	public void setStepType(final StepType stepType) {
		this.stepType = stepType;
	}
	
	
}
