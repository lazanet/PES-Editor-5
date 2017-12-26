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

final class GammaPixelProcessor
extends BasicPixelProcessor
{
    final private short[] gammaTable;
    final private int shift;
    final private int samplesNoAlpha;
    final private boolean hasAlpha;
    final private boolean shiftAlpha;
    
    public GammaPixelProcessor(Destination dst, short[] gammaTable, int shift)
    {
        super(dst, dst.getRaster().getNumBands());
        this.gammaTable = gammaTable;
        this.shift = shift;
        hasAlpha = samples % 2 == 0;
        samplesNoAlpha = hasAlpha ? samples - 1 : samples; // don't change alpha channel
        shiftAlpha = hasAlpha && shift > 0;
    }
    
    public boolean process(int[] row, int xOffset, int xStep, int yStep, int y, int width)
    {
        int total = samples * width;
        for (int i = 0; i < samplesNoAlpha; i++)
            for (int index = i; index < total; index += samples)
                row[index] = 0xFFFF & gammaTable[row[index] >> shift];
        if (shiftAlpha)
            for (int index = samplesNoAlpha; index < total; index += samples)
                row[index] >>= shift;
        return super.process(row, xOffset, xStep, yStep, y, width);
    }
}
