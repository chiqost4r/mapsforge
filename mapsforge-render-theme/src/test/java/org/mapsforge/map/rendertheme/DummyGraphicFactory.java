/*
 * Copyright 2010, 2011, 2012 mapsforge.org
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
package org.mapsforge.map.rendertheme;

import java.io.InputStream;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.Canvas;
import org.mapsforge.core.graphics.Color;
import org.mapsforge.core.graphics.GraphicFactory;
import org.mapsforge.core.graphics.Matrix;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Path;

public final class DummyGraphicFactory implements GraphicFactory {
	public static final DummyGraphicFactory INSTANCE = new DummyGraphicFactory();

	private DummyGraphicFactory() {
		// do nothing
	}

	@Override
	public Bitmap createBitmap(InputStream inputStream) {
		return new DummyBitmap();
	}

	@Override
	public Bitmap createBitmap(int width, int height) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Canvas createCanvas() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int createColor(Color color) {
		return color.ordinal();
	}

	@Override
	public int createColor(int alpha, int red, int green, int blue) {
		return 0;
	}

	@Override
	public int createColor(String colorString) {
		return 0;
	}

	@Override
	public Matrix createMatrix() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Paint createPaint() {
		return new DummyPaint();
	}

	@Override
	public Path createPath() {
		throw new UnsupportedOperationException();
	}
}