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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.Gdx;

/**
 * Test for assets.
 * 
 * @author Dgzt
 */
@RunWith(GdxTestRunner.class)
public final class AssetFilesTest {
	
	// --------------------------------------------------
	// ~ Private static members
	// --------------------------------------------------
	
	/** The prefix for assets. */
	private static final String ASSETS_PREFIX = "../assets/";
	
	/** The path of the vertex shader. */
	private static final String VERTEX_SHADER_PATH = ASSETS_PREFIX + "shader.vsh";
	
	/** The path of the fragment shader. */
	private static final String FRAGMENT_SHADER_PATH = ASSETS_PREFIX + "shader.fsh";

	// --------------------------------------------------
	// ~ Tests
	// --------------------------------------------------
	
	/**
	 * Test for vertex shader file.
	 */
	@Test
	public void test_vertexShader(){
		assertTrue(Gdx.files.internal(VERTEX_SHADER_PATH).exists());
	}
	
	/**
	 * Test for fragment shader file.
	 */
	@Test
	public void test_fragmentShader(){
		assertTrue(Gdx.files.internal(FRAGMENT_SHADER_PATH).exists());
	}
	
	/**
	 * Test for font files.
	 */
	@Test
	public void test_fonts(){
		assertTrue(Gdx.files.internal(ASSETS_PREFIX + FontConstants.SMALL_FONT_FILE).exists());
		assertTrue(Gdx.files.internal(ASSETS_PREFIX + FontConstants.SMALL_FONT_IMAGE).exists());
		assertTrue(Gdx.files.internal(ASSETS_PREFIX + FontConstants.MEDIUM_FONT_FILE).exists());
		assertTrue(Gdx.files.internal(ASSETS_PREFIX + FontConstants.MEDIUN_FONT_IMAGE).exists());
		assertTrue(Gdx.files.internal(ASSETS_PREFIX + FontConstants.LARGE_FONT_FILE).exists());
		assertTrue(Gdx.files.internal(ASSETS_PREFIX + FontConstants.LARGE_FONT_IMAGE_1).exists());
		assertTrue(Gdx.files.internal(ASSETS_PREFIX + FontConstants.LARGE_FONT_IMAGE_2).exists());
	}
}
