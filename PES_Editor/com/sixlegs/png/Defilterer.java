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

import java.awt.Point;
import java.awt.image.*;
import java.io.*;

class Defilterer
{
    private final InputStream in;
    private final int width;
    private final int bitDepth;
    private final int samples;
    private final PixelProcessor pp;
    private final int bpp;
    private final int[] row;

    public Defilterer(InputStream in, int bitDepth, int samples, int width, PixelProcessor pp)
    {
        this.in = in;
        this.bitDepth = bitDepth;
        this.samples = samples;
        this.width = width;
        this.pp = pp;
        bpp = Math.max(1, (bitDepth * samples) >> 3);
        row = new int[samples * width];
    }

    public boolean defilter(int xOffset, int yOffset,
                            int xStep, int yStep,
                            int passWidth, int passHeight)
    throws IOException
    {
        if (passWidth == 0 || passHeight == 0)
            return true;

        int bytesPerRow = (bitDepth * samples * passWidth + 7) / 8;
        boolean isShort = bitDepth == 16;
        WritableRaster passRow = createInputRaster(bitDepth, samples, width);
        DataBuffer dbuf = passRow.getDataBuffer();
        byte[] byteData = isShort ? null : ((DataBufferByte)dbuf).getData();
        short[] shortData = isShort ? ((DataBufferUShort)dbuf).getData() : null;
        
        int rowSize = bytesPerRow + bpp;
        byte[] prev = new byte[rowSize];
        byte[] cur = new byte[rowSize];

        for (int srcY = 0, dstY = yOffset; srcY < passHeight; srcY++, dstY += yStep) {
            int filterType = in.read();
            if (filterType == -1)
                throw new EOFException("Unexpected end of image data");
            readFully(in, cur, bpp, bytesPerRow);
            defilter(cur, prev, bpp, filterType);
            if (isShort) {
                for (int c = 0, i = bpp; i < rowSize; c++, i += 2)
                    shortData[c] = (short)((cur[i] << 8) | (0xFF & cur[i + 1]));
            } else {
                System.arraycopy(cur, bpp, byteData, 0, bytesPerRow);
            }
            passRow.getPixels(0, 0, passWidth, 1, row);
            if (!pp.process(row, xOffset, xStep, yStep, dstY, passWidth))
                return false;
            byte[] tmp = cur;
            cur = prev;
            prev = tmp;
        }
        return true;
    }

    private static void defilter(byte[] cur, byte[] prev, int bpp, int filterType)
    throws PngException
    {
        int rowSize = cur.length;
        int xc, xp;
        switch (filterType) {
        case 0: // None
            break;
        case 1: // Sub
            for (xc = bpp, xp = 0; xc < rowSize; xc++, xp++)
                cur[xc] = (byte)(cur[xc] + cur[xp]);
            break;
        case 2: // Up
            for (xc = bpp; xc < rowSize; xc++)
                cur[xc] = (byte)(cur[xc] + prev[xc]);
            break;
        case 3: // Average
            for (xc = bpp, xp = 0; xc < rowSize; xc++, xp++)
                cur[xc] = (byte)(cur[xc] + ((0xFF & cur[xp]) + (0xFF & prev[xc])) / 2);
            break;
        case 4: // Paeth
            for (xc = bpp, xp = 0; xc < rowSize; xc++, xp++) {
                byte L = cur[xp];
                byte u = prev[xc];
                byte nw = prev[xp];
                int a = 0xFF & L; //  inline byte->int
                int b = 0xFF & u; 
                int c = 0xFF & nw; 
                int p = a + b - c;
                int pa = p - a; if (pa < 0) pa = -pa; // inline Math.abs
                int pb = p - b; if (pb < 0) pb = -pb; 
                int pc = p - c; if (pc < 0) pc = -pc;
                int result;
                if (pa <= pb && pa <= pc) {
                    result = a;
                } else if (pb <= pc) {
                    result = b;
                } else {
                    result = c;
                }
                cur[xc] = (byte)(cur[xc] + result);
            }
            break;
        default:
            throw new PngException("Unrecognized filter type " + filterType, true);
        }
    }

    private static int[][] bandOffsets = {
        null,
        { 0 },
        { 0, 1 },
        { 0, 1, 2 },
        { 0, 1, 2, 3 },
    };

    private static WritableRaster createInputRaster(int bitDepth, int samples, int width)
    {
        int rowSize = (bitDepth * samples * width + 7) / 8;
        Point origin = new Point(0, 0);
        if ((bitDepth < 8) && (samples == 1)) {
            DataBuffer dbuf = new DataBufferByte(rowSize);
            return Raster.createPackedRaster(dbuf, width, 1, bitDepth, origin);
        } else if (bitDepth <= 8) {
            DataBuffer dbuf = new DataBufferByte(rowSize);
            return Raster.createInterleavedRaster(dbuf, width, 1, rowSize, samples,
                                                  bandOffsets[samples], origin);
        } else {
            DataBuffer dbuf = new DataBufferUShort(rowSize / 2);
            return Raster.createInterleavedRaster(dbuf, width, 1, rowSize / 2, samples,
                                                  bandOffsets[samples], origin);
        }
    }

    private static void readFully(InputStream in, byte[] b, int off, int len)
    throws IOException
    {
        int total = 0;
        while (total < len) {
            int result = in.read(b, off + total, len - total);
            if (result == -1)
                throw new EOFException("Unexpected end of image data");
            total += result;
        }
    }
}
