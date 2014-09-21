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
package com.dgzt.html;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.dgzt.core.ButtonFootballGame;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * HTML interface for the game.
 * 
 * @author Dgzt
 */
public class ButtonFootballGameHtml extends GwtApplication {
	/** The default width value. */
	private static final int WIDTH = 640;
	
	/** The default height value. */
	private static final int HEIGHT = 480;
	
	/** This object. */
	private static ButtonFootballGameHtml instance;
	
	/** The game object. */
	private ButtonFootballGame game;

	/**
	 * The html configuration.
	 */
	@Override
	public GwtApplicationConfiguration getConfig() {
		GwtApplicationConfiguration conf = new GwtApplicationConfiguration(WIDTH, HEIGHT);

		Element element = Document.get().getElementById("embed-html");
		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("100%");
		panel.setHeight("100%");
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		element.appendChild(panel.getElement());
		conf.rootPanel = panel;

		return conf;
	}

	/**
	 * The application listener.
	 */
	@Override
	public ApplicationListener getApplicationListener() {
		instance = this;
		setLogLevel(LOG_NONE);
		setLoadingListener(new LoadingListener() {
			@Override
			public void beforeSetup() {
			}

			@Override
			public void afterSetup() {
				scaleCanvas();
				setupResizeHook();
			}
		});
		
		game = new ButtonFootballGame();
		return game;
	}
	
	/**
	 * Scale the canvas on the html page.
	 */
	void scaleCanvas() {
		Element element = Document.get().getElementById("embed-html");
		int width = getWindowInnerWidth();
		int height = getWindowInnerHeight();
		consoleLog(String.valueOf(width)+" x "+String.valueOf(height));
		NodeList<Element> nl = element.getElementsByTagName("canvas");
		if (nl != null && nl.getLength() > 0) {
			Element canvas = nl.getItem(0);
			canvas.setAttribute("width", "" + width + "px");
			canvas.setAttribute("height", "" + height + "px");
			canvas.getStyle().setWidth(width, Style.Unit.PX);
			canvas.getStyle().setHeight(height, Style.Unit.PX);
			canvas.getStyle().setTop(0,Style.Unit.PX);
			canvas.getStyle().setLeft(0,Style.Unit.PX);
			canvas.getStyle().setPosition(Style.Position.ABSOLUTE);
		}
		game.resize(width, height);
	}

	/**
	 * Get the inner width value.
	 * 
	 * @return
	 */
	private native int getWindowInnerWidth() /*-{
		return $wnd.innerWidth;
	}-*/;

	/**
	 * Get the inner height value.
	 * 
	 * @return
	 */
	private native int getWindowInnerHeight() /*-{
		return $wnd.innerHeight;
	}-*/;

	/**
	 * Setup the browser resize event.
	 */
	private native void setupResizeHook() /*-{
		var htmlLauncher_onWindowResize = $entry(@com.dgzt.html.ButtonFootballGameHtml::handleResize());
		$wnd.addEventListener('resize', htmlLauncher_onWindowResize, false);
	}-*/;

	/**
	 * The resize handle.
	 */
	public static void handleResize() {
		instance.scaleCanvas();
	}
}
