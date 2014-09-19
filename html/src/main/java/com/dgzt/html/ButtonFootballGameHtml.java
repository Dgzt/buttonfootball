package com.dgzt.html;

import com.dgzt.core.ButtonFootballGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class ButtonFootballGameHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new ButtonFootballGame();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
