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

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

class ImageFactory
{
    private static short[] GAMMA_TABLE_45455 =
        PngImage.createGammaTable(0.45455f, 2.2f, false);
    private static short[] GAMMA_TABLE_100000 =
        PngImage.createGammaTable(1f, 2.2f, false);

    public static BufferedImage createImage(PngImage png, InputStream in)
    throws IOException
    {
        return createImage(png, in, new Dimension(png.getWidth(), png.getHeight()));
    }

    // width and height are overridable for APNG
    public static BufferedImage createImage(PngImage png, InputStream in, Dimension size)
    throws IOException
    {
        PngConfig config = png.getConfig();

        int width     = size.width;
        int height    = size.height;
        int bitDepth  = png.getBitDepth();
        int samples   = png.getSamples();
        boolean interlaced = png.isInterlaced();

        boolean indexed = isIndexed(png);
        boolean convertIndexed = indexed && config.getConvertIndexed();
        short[] gammaTable = config.getGammaCorrect() ? getGammaTable(png) : null;
        ColorModel dstColorModel = createColorModel(png, gammaTable, convertIndexed);

        int dstWidth = width;
        int dstHeight = height;
        Rectangle sourceRegion = config.getSourceRegion();
        if (sourceRegion != null) {
            if (!new Rectangle(dstWidth, dstHeight).contains(sourceRegion))
                throw new IllegalStateException("Source region " + sourceRegion + " falls outside of " +
                                                width + "x" + height + " image");
            dstWidth = sourceRegion.width;
            dstHeight = sourceRegion.height;
        }

        Destination dst;
        int xsub = config.getSourceXSubsampling();
        int ysub = config.getSourceYSubsampling();
        if (xsub != 1 || ysub != 1) {
            int xoff = config.getSubsamplingXOffset();
            int yoff = config.getSubsamplingYOffset();
            int subw = calcSubsamplingSize(dstWidth, xsub, xoff, 'X');
            int subh = calcSubsamplingSize(dstHeight, ysub, yoff, 'Y');
            WritableRaster raster = dstColorModel.createCompatibleWritableRaster(subw, subh);
            dst = new SubsamplingDestination(raster, width, xsub, ysub, xoff, yoff);
        } else {
            dst = new RasterDestination(dstColorModel.createCompatibleWritableRaster(dstWidth, dstHeight), width);
        }
        if (sourceRegion != null)
            dst = new SourceRegionDestination(dst, sourceRegion);


        // Destination dst = createDestination(config, dstColorModel, size, interlaced);
        BufferedImage image = new BufferedImage(dstColorModel, dst.getRaster(), false, null);

        PixelProcessor pp = null;
        if (!indexed) {
            int[] trans = (int[])png.getProperty(PngConstants.TRANSPARENCY, int[].class, false);
            int shift = (bitDepth == 16 && config.getReduce16()) ? 8 : 0;
            if (shift != 0 || trans != null || gammaTable != null) {
                if (gammaTable == null)
                    gammaTable = getIdentityTable(bitDepth - shift);
                if (trans != null) {
                    pp = new TransGammaPixelProcessor(dst, gammaTable, trans, shift);
                } else {
                    pp = new GammaPixelProcessor(dst, gammaTable, shift);
                }
            }
        }
        if (convertIndexed) {
            IndexColorModel srcColorModel = (IndexColorModel)createColorModel(png, gammaTable, false);
            dst = new ConvertIndexedDestination(dst, width, srcColorModel, (ComponentColorModel)dstColorModel);
        }

        if (pp == null)
            pp = new BasicPixelProcessor(dst, samples);
        if (config.getProgressive() && interlaced && !convertIndexed)
            pp = new ProgressivePixelProcessor(dst, pp, width, height);
        pp = new ProgressUpdater(png, image, pp);

        InflaterInputStream inflate = new InflaterInputStream(in, new Inflater(), 0x1000);
        Defilterer d = new Defilterer(inflate, bitDepth, samples, width, pp);
        
        // TODO: if not progressive, initialize to fully transparent?
        boolean complete;
        if (interlaced) {
            complete =
                d.defilter(0, 0, 8, 8, (width + 7) / 8, (height + 7) / 8) &&
                png.handlePass(image, 0) &&
                d.defilter(4, 0, 8, 8, (width + 3) / 8, (height + 7) / 8) &&
                png.handlePass(image, 1) &&
                d.defilter(0, 4, 4, 8, (width + 3) / 4, (height + 3) / 8) &&
                png.handlePass(image, 2) &&
                d.defilter(2, 0, 4, 4, (width + 1) / 4, (height + 3) / 4) &&
                png.handlePass(image, 3) && 
                d.defilter(0, 2, 2, 4, (width + 1) / 2, (height + 1) / 4) &&
                png.handlePass(image, 4) &&
                d.defilter(1, 0, 2, 2, width / 2, (height + 1) / 2) &&
                png.handlePass(image, 5) &&
                d.defilter(0, 1, 1, 2, width, height / 2) &&
                png.handlePass(image, 6);
        } else {
            complete =
                d.defilter(0, 0, 1, 1, width, height) &&
                png.handlePass(image, 0);
        }
        // TODO: handle complete?
        dst.done();
        return image;
    }

    private static short[] getGammaTable(PngImage png)
    {
        PngConfig config = png.getConfig();
        if ((png.getBitDepth() != 16 || config.getReduce16()) &&
            config.getDisplayExponent() == 2.2f) {
            float gamma = png.getGamma();
            if (gamma == 0.45455f)
                return GAMMA_TABLE_45455;
            if (gamma == 1f)
                return GAMMA_TABLE_100000;
        }
        return png.getGammaTable();
    }

    private static int calcSubsamplingSize(int len, int sub, int off, char desc)
    {
        int size = (len - off + sub - 1) / sub;
        if (size == 0)
            throw new IllegalStateException("Source " + desc + " subsampling " + sub + ", offset " + off +
                                            " is invalid for image dimension " + len);
        return size;
    }

    private static boolean isIndexed(PngImage png)
    {
        int colorType = png.getColorType();
        return colorType == PngConstants.COLOR_TYPE_PALETTE ||
            (colorType == PngConstants.COLOR_TYPE_GRAY && png.getBitDepth() < 16);
    }

    private static ColorModel createColorModel(PngImage png, short[] gammaTable, boolean convertIndexed)
    throws PngException
    {
        Map props = png.getProperties();
        int colorType = png.getColorType();
        int bitDepth = png.getBitDepth();
        int outputDepth = (bitDepth == 16 && png.getConfig().getReduce16()) ? 8 : bitDepth;

        if (isIndexed(png) && !convertIndexed) {
            byte[] r, g, b;
            if (colorType == PngConstants.COLOR_TYPE_PALETTE) {
                byte[] palette = (byte[])png.getProperty(PngConstants.PALETTE, byte[].class, true);
                int paletteSize = palette.length / 3;
                r = new byte[paletteSize];
                g = new byte[paletteSize];
                b = new byte[paletteSize];
                for (int i = 0, p = 0; i < paletteSize; i++) {
                    r[i] = palette[p++];
                    g[i] = palette[p++];
                    b[i] = palette[p++];
                }
                applyGamma(r, gammaTable);
                applyGamma(g, gammaTable);
                applyGamma(b, gammaTable);
            } else {
                int paletteSize = 1 << bitDepth;
                r = g = b = new byte[paletteSize];
                for (int i = 0; i < paletteSize; i++) {
                    r[i] = (byte)(i * 255 / (paletteSize - 1));
                }
                applyGamma(r, gammaTable);
            }
            if (props.containsKey(PngConstants.PALETTE_ALPHA)) {
                byte[] trans = (byte[])png.getProperty(PngConstants.PALETTE_ALPHA, byte[].class, true);
                byte[] a = new byte[r.length];
                Arrays.fill(a, trans.length, r.length, (byte)0xFF);
                System.arraycopy(trans, 0, a, 0, trans.length);
                return new IndexColorModel(outputDepth, r.length, r, g, b, a);
            } else {
                int trans = -1;
                if (props.containsKey(PngConstants.TRANSPARENCY))
                    trans = ((int[])png.getProperty(PngConstants.TRANSPARENCY, int[].class, true))[0];
                return new IndexColorModel(outputDepth, r.length, r, g, b, trans);
            }
        } else {
            int dataType = (outputDepth == 16) ?
                DataBuffer.TYPE_USHORT : DataBuffer.TYPE_BYTE;
            int colorSpace =
                (colorType == PngConstants.COLOR_TYPE_GRAY ||
                 colorType == PngConstants.COLOR_TYPE_GRAY_ALPHA) ?
                ColorSpace.CS_GRAY :
                ColorSpace.CS_sRGB;
            int transparency = png.getTransparency();
            // TODO: cache/enumerate color models?
            return new ComponentColorModel(ColorSpace.getInstance(colorSpace),
                                           null,
                                           transparency != Transparency.OPAQUE,
                                           false,
                                           transparency,
                                           dataType);
        }
    }

     private static void applyGamma(byte[] palette, short[] gammaTable)
     {
         if (gammaTable != null) {
             for (int i = 0; i < palette.length; i++)
                 palette[i] = (byte)gammaTable[0xFF & palette[i]];
         }
     }
    
    private static short[] getIdentityTable(int bitDepth)
    {
        // TODO: cache identity tables?
        int size = 1 << bitDepth;
        short[] table = new short[size];
        for (int i = 0; i < size; i++)
            table[i] = (short)i;
        return table;
    }
}
