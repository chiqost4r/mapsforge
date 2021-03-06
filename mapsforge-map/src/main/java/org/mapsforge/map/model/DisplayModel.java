/*
 * Copyright © 2014 Ludwig M Brinckmann
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.mapsforge.map.model;

import org.mapsforge.map.model.common.Observable;

/**
 * Encapsulates the display characteristics for a MapView, such as tile size and background color. The size of map tiles
 * is used to adapt to devices with differing pixel densities and users with different preferences: The larger the tile,
 * the larger everything is rendered, the effect is one of effectively stretching everything. The default device
 * dependent scale factor is determined at the GraphicFactory level, while the DisplayModel allows further adaptation to
 * cater for user needs or application development (maybe a small map and large map, or to prevent upscaling for
 * downloaded tiles that do not scale well).
 */

public class DisplayModel extends Observable {

	private static final int DEFAULT_BACKGROUND_COLOR = 0xffeeeeee; // format AARRGGBB
	private static final int DEFAULT_TILE_SIZE = 256;
	private static final float DEFAULT_MAX_TEXT_WIDTH_FACTOR = 0.7f;
	private static final int DEFAULT_MAX_TEXT_WIDTH = (int) (DEFAULT_TILE_SIZE * DEFAULT_MAX_TEXT_WIDTH_FACTOR);

	private static float defaultUserScaleFactor = 1f;
	private static float deviceScaleFactor = 1f;


	/**
	 * Get the default scale factor for all newly created DisplayModels.
	 * 
	 * @return the default scale factor to be applied to all new DisplayModels.
	 */
	public static synchronized float getDefaultUserScaleFactor() {
		return defaultUserScaleFactor;
	}

	/**
	 * Returns the device scale factor.
	 * 
	 * @return the device scale factor.
	 */
	public static synchronized float getDeviceScaleFactor() {
		return deviceScaleFactor;
	}

	/**
	 * Set the default scale factor for all newly created DisplayModels, so can be used to apply user settings from a
	 * device.
	 * 
	 * @param scaleFactor
	 *            the default scale factor to be applied to all new DisplayModels.
	 */
	public static synchronized void setDefaultUserScaleFactor(float scaleFactor) {
		defaultUserScaleFactor = scaleFactor;
	}

	/**
	 * Set the device scale factor.
	 * 
	 * @param scaleFactor
	 *            the device scale factor.
	 */
	public static synchronized void setDeviceScaleFactor(float scaleFactor) {
		deviceScaleFactor = scaleFactor;
	}

	private int backgroundColor = DEFAULT_BACKGROUND_COLOR;

	private int fixedTileSize;

	private int maxTextWidth = DEFAULT_MAX_TEXT_WIDTH;
	private float maxTextWidthFactor = DEFAULT_MAX_TEXT_WIDTH_FACTOR;
	private int tileSize = DEFAULT_TILE_SIZE;
	private int tileSizeMultiple = 1;

	private float userScaleFactor = defaultUserScaleFactor;

	public DisplayModel() {
		super();
		this.setTileSize();
	}

	/**
	 * Returns the background color.
	 * 
	 * @return the background color.
	 */
	public synchronized int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Returns the maximum width of text beyond which the text is broken into lines.
	 *
	 * @return the maximum text width
	 */
	public int getMaxTextWidth() {
		return maxTextWidth;
	}

	/**
	 * Returns the overall scale factor.
	 * 
	 * @return the combined device/user scale factor.
	 */
	public synchronized float getScaleFactor() {
		return deviceScaleFactor * this.userScaleFactor;
	}

	/**
	 * Width and height of a map tile in pixel after system and user scaling is applied.
	 */
	public synchronized int getTileSize() {
		return tileSize;
	}

	/**
	 * Gets the tile size multiple.
	 */
	public synchronized int getTileSizeMultiple() {
		return this.tileSizeMultiple;
	}

	/**
	 * Returns the minimum repeating tile size > 1, used for sizing shader bitmaps
	 */
	public synchronized int getTilingSize() {
		if (this.fixedTileSize != 0) {
			return this.fixedTileSize;
		}
		if (this.tileSizeMultiple == 1) {
			return tileSize;
		}
		return this.tileSizeMultiple;
	}

	/**
	 * Returns the user scale factor.
	 * 
	 * @return the user scale factor.
	 */
	public synchronized float getUserScaleFactor() {
		return this.userScaleFactor;
	}

	/**
	 * Set the background color.
	 * 
	 * @param color
	 *            the color to use.
	 */
	public synchronized void setBackgroundColor(int color) {
		this.backgroundColor = color;
	}

	/**
	 * Forces the tile size to a fixed value
	 *
	 * @param tileSize
	 *            the fixed tile size to use if != 0, if 0 the tile size will be calculated
	 */
	public void setFixedTileSize(int tileSize) {
		this.fixedTileSize = tileSize;
		setTileSize();
	}

	/**
	 * Sets the factor to compute the maxTextWidth
	 *
	 * @param maxTextWidthFactor
	 *              to compute maxTextWidth
	 */
	public void setMaxTextWidthFactor(float maxTextWidthFactor) {
		this.maxTextWidthFactor = maxTextWidthFactor;
		this.setMaxTextWidth();
	}

	/**
	 * Clamps the tile size to a multiple of the supplied value.
	 *
	 * @param multiple tile size multiple
	 */
	public synchronized void setTileSizeMultiple(int multiple) {
		this.tileSizeMultiple = multiple;
		setTileSize();
	}

	/**
	 * Set the user scale factor.
	 * 
	 * @param scaleFactor
	 *            the user scale factor to use.
	 */
	public synchronized void setUserScaleFactor(float scaleFactor) {
		userScaleFactor = scaleFactor;
		setTileSize();
	}

	private void setMaxTextWidth() {
		this.maxTextWidth = (int) (this.tileSize * maxTextWidthFactor);
	}

	private void setTileSize() {
		if (this.fixedTileSize == 0) {
			float temp = DEFAULT_TILE_SIZE * deviceScaleFactor * userScaleFactor;
			// this will clamp to the nearest multiple of the tileSizeMultiple
			// and make sure we do not end up with 0
			this.tileSize = Math.max(tileSizeMultiple,
					(int) (Math.round(temp / this.tileSizeMultiple) * this.tileSizeMultiple));
		} else {
			this.tileSize = this.fixedTileSize;
		}
		this.setMaxTextWidth();
	}

}
