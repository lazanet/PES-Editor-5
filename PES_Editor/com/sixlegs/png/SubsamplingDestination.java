/*
com.sixlegs.png - Java package to read and display PNG images
Copyright (C) 1998-2006 Chris Nokleberg

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version.
*/

package com.sixlegs.png;

import java.awt.image.WritableRaster;

final class SubsamplingDestination
extends RasterDestination
{
    private final int xsub;
    private final int ysub;
    private final int xoff;
    private final int yoff;
    private final int[] singlePixel;
    
    public SubsamplingDestination(WritableRaster raster, int sourceWidth,
                                  int xsub, int ysub, int xoff, int yoff)
    {
        super(raster, sourceWidth);
        this.xsub = xsub;
        this.ysub = ysub;
        this.xoff = xoff;
        this.yoff = yoff;
        singlePixel = new int[raster.getNumBands()];
    }

    public void setPixels(int x, int y, int w, int[] pixels)
    {
        if (((y - yoff) % ysub) == 0) {
            int xdst = (x - xoff) / xsub;
            int ydst = (y - yoff) / ysub;
            int startSrc = xdst * xsub + xoff;
            if (startSrc < x) {
                xdst++;
                startSrc += xsub;
            }
            int samples = raster.getNumBands();
            for (int i = startSrc - x, end = x + w; i < end; i += xsub) {
                System.arraycopy(pixels, i * samples, singlePixel, 0, samples);
                super.setPixel(xdst++, ydst, singlePixel);
            }
        }
    }

    public void setPixel(int x, int y, int[] pixel)
    {
        x -= xoff;
        y -= yoff;
        if (x % xsub == 0 && y % ysub == 0)
            super.setPixel(x / xsub, y / ysub, pixel);
    }

    public void getPixel(int x, int y, int[] pixel)
    {
        throw new UnsupportedOperationException();
    }
}
