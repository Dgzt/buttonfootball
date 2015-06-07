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
package com.dgzt.core.scoreboard;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.dgzt.core.GameControl;
import com.dgzt.core.Player;
import com.dgzt.core.shape.LineShape;
import com.dgzt.core.shape.RectangleBorderShape;
import com.dgzt.core.shape.RectangleShape;

/**
 * The time left board.
 * 
 * @author Dgzt
 */
public class TimeLeftBoard extends RectangleBorderShape{
	
	// --------------------------------------------------
	// ~ Public static members
	// --------------------------------------------------
	
	/** The width value. */
	public static final float WIDTH = GoalBoard.WIDTH;
	
	/** The height value. */
	public static final float HEIGHT = 10.0f;
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The time left line distance from border. */
	private static final float DISTANCE_FROM_BORDER = 1.0f;
	
	/** The timer delay in sec. */
	private static final float TIMER_DELAY_SEC = 0.1f;
	
	/** The width of time left line. */
	private static final float TIME_LEFT_LINE_WIDTH = WIDTH - 2 * LineShape.LINE_WIDTH - 2 * DISTANCE_FROM_BORDER;
	
	/** The height of time left line. */
	private static final float TIME_LEFT_LINE_HEIGHT = HEIGHT - 2 * LineShape.LINE_WIDTH - 2 * DISTANCE_FROM_BORDER;
	
	// --------------------------------------------------
	// ~ Private members
	// --------------------------------------------------
	
	/** The time left line. */
	private final RectangleShape timeLeftLine;
	
	/** The timer for time left line. */
	private final Timer timer;
	
	/** The max time left. */
	private int maxTimeLeft;
	
	/** The current time sec. */
	private float currentTimeSec;
	
	/** The visibility. */
	private boolean visible;
	
	// --------------------------------------------------
	// ~ Constructors
	// --------------------------------------------------

	/**
	 * Constructor.
	 * 
	 * @param shader - The shader.
	 * @param borderColor - The color of border.
	 * @param lineColor - The color of line.
	 */
	public TimeLeftBoard(final ShaderProgram shader, final Color borderColor, final Color lineColor) {
		super(shader, borderColor);
		
		timeLeftLine = new RectangleShape(shader, lineColor);
		
		timer = new Timer();
		
		visible = false;
	}

	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------
	
	/**
	 * Start the countdown.
	 * 
	 * @param gameControl - The game control.
	 * @param player - The player.
	 */
	public void start(final GameControl gameControl, final Player player){
		currentTimeSec = maxTimeLeft;
		resizeTimeLeftLine();
		
		timer.scheduleTask(new Task(){

			@Override
			public void run() {
				currentTimeSec -= TIMER_DELAY_SEC;
				resizeTimeLeftLine();
				
				if(currentTimeSec <= 0){
					cancel();
				}
				
			}
			
		}, TIMER_DELAY_SEC, TIMER_DELAY_SEC);
		timer.start();
		visible = true;
	}
	
	/**
	 * Stop the countdown.
	 */
	public void stop(){
		timer.stop();
	}
	
	/**
	 * Resume the countdown.
	 */
	public void resume(){
		timer.start();
	}
	
	/**
	 * Clear.
	 */
	public void clear(){
		timer.clear();
		visible = false;
	}
	
	// --------------------------------------------------
	// ~ Override methods
	// --------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resize(float x, float y, float width, float height, double scale) {
		super.resize(x, y, width, height, scale);
		
		resizeTimeLeftLine();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void draw() {
		super.draw();
		
		timeLeftLine.draw();
	}
	
	// --------------------------------------------------
	// ~ Private methods
	// --------------------------------------------------
	
	/**
	 * Resize the time left line.
	 */
	private void resizeTimeLeftLine(){
		final double scale = getScale();
		final float timeLeftBoardWidth = getWidth();
		final float timeLeftBoardHeight = getHeight();
		final float timeLeftBoardX = getX();
		final float timeLeftBoardY = getY();
		
		final float fullWidth = (float)(TIME_LEFT_LINE_WIDTH * scale);
		final float widthRate = currentTimeSec / maxTimeLeft;
		final float width = widthRate * fullWidth;
		final float height = (float)(TIME_LEFT_LINE_HEIGHT * scale);
		final float x = timeLeftBoardX + (timeLeftBoardWidth - fullWidth) / 2;
		final float y = timeLeftBoardY + (timeLeftBoardHeight - height) / 2;
		
		timeLeftLine.resize(x, y, width, height);
	}
	
	// --------------------------------------------------
	// ~ Setter / Getter methods
	// --------------------------------------------------
	
	/**
	 * Set the max time left sec.
	 * 
	 * @param maxTimeLeft - The new max time left sec.
	 */
	public void setMaxTimeLeft(int maxTimeLeft) {
		this.maxTimeLeft = maxTimeLeft;
	}
	
	/**
	 * Return with the visiblity.
	 */
	public boolean isVisible(){
		return visible;
	}
}
