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

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

/**
 * Runner for libGDX tests.
 * This file from: 
 * <a href="https://bitbucket.org/TomGrill/libgdx-testing-sample">
 * https://bitbucket.org/TomGrill/libgdx-testing-sample</a>
 * 
 * @author Dgzt
 */
public class GdxTestRunner extends BlockJUnit4ClassRunner implements ApplicationListener{
	   
	   private Map<FrameworkMethod, RunNotifier> invokeInRender = new HashMap<FrameworkMethod, RunNotifier>();
	   
	   public GdxTestRunner(Class<?> klass) throws InitializationError {
	      super(klass);
	      HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
	      
	      new HeadlessApplication(this, conf);
	   }

	   @Override
	   public void create() {
	   }

	   @Override
	   public void resume() {
	   }

	   @Override
	   public void render() {
	      synchronized (invokeInRender) {
	         for(Map.Entry<FrameworkMethod, RunNotifier> each : invokeInRender.entrySet()){
	            super.runChild(each.getKey(), each.getValue());
	         }
	         invokeInRender.clear();
	      }
	   }
	   
	   

	   @Override
	   public void resize(int width, int height) {
	   }

	   @Override
	   public void pause() {
	   }

	   @Override
	   public void dispose() {
	   }

	   @Override
	   protected void runChild(FrameworkMethod method, RunNotifier notifier) {
	      synchronized (invokeInRender) {
	         //add for invoking in render phase, where gl context is available
	         invokeInRender.put(method, notifier);   
	      }
	      //wait until that test was invoked
	      waitUntilInvokedInRenderMethod();
	   }

	   /**
	    *
	    */
	   private void waitUntilInvokedInRenderMethod() {
	      try {
	         while (true){
	            Thread.sleep(10);
	            synchronized (invokeInRender) {
	               if (invokeInRender.isEmpty()) break;
	            }
	         }
	      } catch (InterruptedException e) {
	         e.printStackTrace();
	      }
	   }

	}