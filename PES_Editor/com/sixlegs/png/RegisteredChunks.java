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

import java.io.*;
import java.util.*;
import java.util.zip.*;

class RegisteredChunks
{
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("UTC");
    private static final String ISO_8859_1 = "ISO-8859-1";
    private static final String US_ASCII = "US-ASCII";
    private static final String UTF_8 = "UTF-8";

    public static boolean read(int type, DataInput in, int length, PngImage png)
    throws IOException
    {
        Map props = png.getProperties();
        switch (type) {
        case PngConstants.IHDR: read_IHDR(in, length, props); break;
        case PngConstants.IEND: checkLength(PngConstants.IEND, length, 0); break;
        case PngConstants.PLTE: read_PLTE(in, length, props, png); break;
        case PngConstants.bKGD: read_bKGD(in, length, props, png); break;
        case PngConstants.tRNS: read_tRNS(in, length, props, png); break;
        case PngConstants.sBIT: read_sBIT(in, length, props, png); break;
        case PngConstants.hIST: read_hIST(in, length, props, png); break;
        case PngConstants.sPLT: read_sPLT(in, length, props, png); break;
        case PngConstants.cHRM: read_cHRM(in, length, props); break;
        case PngConstants.gAMA: read_gAMA(in, length, props); break;
        case PngConstants.iCCP: read_iCCP(in, length, props); break;
        case PngConstants.pHYs: read_pHYs(in, length, props); break;
        case PngConstants.sRGB: read_sRGB(in, length, props); break;
        case PngConstants.tIME: read_tIME(in, length, props); break;
        case PngConstants.gIFg: read_gIFg(in, length, props); break;
        case PngConstants.oFFs: read_oFFs(in, length, props); break;
        case PngConstants.sCAL: read_sCAL(in, length, props); break;
        case PngConstants.sTER: read_sTER(in, length, props); break;
            // case PngConstants.gIFx: read_gIFx(in, length, props); break;
        case PngConstants.iTXt:
        case PngConstants.tEXt:
        case PngConstants.zTXt:
            readText(type, in, length, props, png);
            break;
        default:
            return false;
        }
        return true;
    }

    private static void read_IHDR(DataInput in, int length, Map props)
    throws IOException
    {
        checkLength(PngConstants.IHDR, length, 13);
        int width = in.readInt();
        int height = in.readInt();
        if (width <= 0 || height <= 0)
            throw new PngException("Bad image size: " + width + "x" + height, true);

        byte bitDepth = in.readByte();
        switch (bitDepth) {
        case 1: case 2: case 4: case 8: case 16: break;
        default: throw new PngException("Bad bit depth: " + bitDepth, true);
        }

        byte[] sbits = null;
        int colorType = in.readUnsignedByte();
        switch (colorType) {
        case PngConstants.COLOR_TYPE_RGB:
        case PngConstants.COLOR_TYPE_GRAY: 
            break;
        case PngConstants.COLOR_TYPE_PALETTE: 
            if (bitDepth == 16)
                throw new PngException("Bad bit depth for color type " + colorType + ": " + bitDepth, true);
            break;
        case PngConstants.COLOR_TYPE_GRAY_ALPHA: 
        case PngConstants.COLOR_TYPE_RGB_ALPHA: 
            if (bitDepth <= 4)
                throw new PngException("Bad bit depth for color type " + colorType + ": " + bitDepth, true);
            break;
        default:
            throw new PngException("Bad color type: " + colorType, true);
        }

        int compression = in.readUnsignedByte();
        if (compression != PngConstants.COMPRESSION_BASE) 
            throw new PngException("Unrecognized compression method: " + compression, true);

        int filter = in.readUnsignedByte();
        if (filter != PngConstants.FILTER_BASE)
            throw new PngException("Unrecognized filter method: " + filter, true);

        int interlace = in.readUnsignedByte();
        switch (interlace) {
        case PngConstants.INTERLACE_NONE:
        case PngConstants.INTERLACE_ADAM7:
            break;
        default:
            throw new PngException("Unrecognized interlace method: " + interlace, true);
        }

        props.put(PngConstants.WIDTH, Integers.valueOf(width));
        props.put(PngConstants.HEIGHT, Integers.valueOf(height));
        props.put(PngConstants.BIT_DEPTH, Integers.valueOf(bitDepth));
        props.put(PngConstants.INTERLACE, Integers.valueOf(interlace));
        props.put(PngConstants.COMPRESSION, Integers.valueOf(compression));
        props.put(PngConstants.FILTER, Integers.valueOf(filter));
        props.put(PngConstants.COLOR_TYPE, Integers.valueOf(colorType));
    }

    private static void read_PLTE(DataInput in, int length, Map props, PngImage png)
    throws IOException
    {
        if (length == 0)
            throw new PngException("PLTE chunk cannot be empty", true);
        if (length % 3 != 0)
            throw new PngException("PLTE chunk length indivisible by 3: " + length, true);
        int size = length / 3;
        if (size > 256)
            throw new PngException("Too many palette entries: " + size, true);
        switch (png.getColorType()) {
        case PngConstants.COLOR_TYPE_PALETTE:
            if (size > (2 << (png.getBitDepth() - 1)))
                throw new PngException("Too many palette entries: " + size, true);
            break;
        case PngConstants.COLOR_TYPE_GRAY:
        case PngConstants.COLOR_TYPE_GRAY_ALPHA:
            throw new PngException("PLTE chunk found in grayscale image", false);
        }
        byte[] palette = new byte[length];
        in.readFully(palette);
        props.put(PngConstants.PALETTE, palette);
    }

    private static void read_tRNS(DataInput in, int length, Map props, PngImage png)
    throws IOException
    {
        switch (png.getColorType()) {
        case PngConstants.COLOR_TYPE_GRAY:
            checkLength(PngConstants.tRNS, length, 2);
            props.put(PngConstants.TRANSPARENCY, new int[]{ in.readUnsignedShort() });
            break;
        case PngConstants.COLOR_TYPE_RGB:
            checkLength(PngConstants.tRNS, length, 6);
            props.put(PngConstants.TRANSPARENCY, new int[]{
                in.readUnsignedShort(),
                in.readUnsignedShort(),
                in.readUnsignedShort(),
            });
            break;
        case PngConstants.COLOR_TYPE_PALETTE:
            int paletteSize = ((byte[])png.getProperty(PngConstants.PALETTE, byte[].class, true)).length / 3;
            if (length > paletteSize)
                throw new PngException("Too many transparency palette entries (" + length + " > " + paletteSize + ")", true);
            byte[] trans = new byte[length];
            in.readFully(trans);
            props.put(PngConstants.PALETTE_ALPHA, trans);
            break;
        default:
            throw new PngException("tRNS prohibited for color type " + png.getColorType(), true);
        }
    }

    private static void read_bKGD(DataInput in, int length, Map props, PngImage png)
    throws IOException
    {
        int[] background;
        switch (png.getColorType()) {
        case PngConstants.COLOR_TYPE_PALETTE:
            checkLength(PngConstants.bKGD, length, 1);
            background = new int[]{ in.readUnsignedByte() };
            break;
        case PngConstants.COLOR_TYPE_GRAY:
        case PngConstants.COLOR_TYPE_GRAY_ALPHA:
            checkLength(PngConstants.bKGD, length, 2);
            background = new int[]{ in.readUnsignedShort() };
            break;
        default:
            // truecolor
            checkLength(PngConstants.bKGD, length, 6);
            background = new int[]{
                in.readUnsignedShort(),
                in.readUnsignedShort(),
                in.readUnsignedShort(),
            };
        }
        props.put(PngConstants.BACKGROUND, background);
    }

    private static void read_cHRM(DataInput in, int length, Map props)
    throws IOException
    {
        checkLength(PngConstants.cHRM, length, 32);
        float[] array = new float[8];
        for (int i = 0; i < 8; i++)
            array[i] = in.readInt() / 100000f;
        if (!props.containsKey(PngConstants.CHROMATICITY))
            props.put(PngConstants.CHROMATICITY, array);
    }

    private static void read_gAMA(DataInput in, int length, Map props)
    throws IOException
    {
        checkLength(PngConstants.gAMA, length, 4);
        int gamma = in.readInt();
        if (gamma == 0)
            throw new PngException("Meaningless zero gAMA chunk value", false);
        if (!props.containsKey(PngConstants.RENDERING_INTENT))
            props.put(PngConstants.GAMMA, new Float(gamma / 100000f));
    }

    private static void read_hIST(DataInput in, int length, Map props, PngImage png)
    throws IOException
    {
        // TODO: ensure it is divisible by three
        int paletteSize = ((byte[])png.getProperty(PngConstants.PALETTE, byte[].class, true)).length / 3;
        checkLength(PngConstants.hIST, length, paletteSize * 2);
        int[] array = new int[paletteSize];
        for (int i = 0; i < paletteSize; i++)
            array[i] = in.readUnsignedShort();
        props.put(PngConstants.HISTOGRAM, array);
    }

    private static void read_iCCP(DataInput in, int length, Map props)
    throws IOException
    {
        String name = readKeyword(in, length);
        byte[] data = readCompressed(in, length - name.length() - 1, true);
        props.put(PngConstants.ICC_PROFILE_NAME, name);
        props.put(PngConstants.ICC_PROFILE, data);
    }

    private static void read_pHYs(DataInput in, int length, Map props)
    throws IOException
    {
        checkLength(PngConstants.pHYs, length, 9);
        int pixelsPerUnitX = in.readInt();
        int pixelsPerUnitY = in.readInt();
        int unit = in.readUnsignedByte();
        if (unit != PngConstants.UNIT_UNKNOWN && unit != PngConstants.UNIT_METER)
            throw new PngException("Illegal pHYs chunk unit specifier: " + unit, false);
        props.put(PngConstants.PIXELS_PER_UNIT_X, Integers.valueOf(pixelsPerUnitX));
        props.put(PngConstants.PIXELS_PER_UNIT_Y, Integers.valueOf(pixelsPerUnitY));
        props.put(PngConstants.UNIT, Integers.valueOf(unit));
    }

    private static void read_sBIT(DataInput in, int length, Map props, PngImage png)
    throws IOException
    {
        boolean paletted = png.getColorType() == PngConstants.COLOR_TYPE_PALETTE;
        int count = paletted ? 3 : png.getSamples();
        checkLength(PngConstants.sBIT, length, count);
        int depth = paletted ? 8 : png.getBitDepth();
        byte[] array = new byte[count];
        for (int i = 0; i < count; i++) {
            byte bits = in.readByte();
            if (bits <= 0 || bits > depth)
                throw new PngException("Illegal sBIT sample depth", false);
            array[i] = bits;
        }
        props.put(PngConstants.SIGNIFICANT_BITS, array);
    }

    private static void read_sRGB(DataInput in, int length, Map props)
    throws IOException
    {
        checkLength(PngConstants.sRGB, length, 1);
        int intent = in.readByte();
        props.put(PngConstants.RENDERING_INTENT, Integers.valueOf(intent));
        props.put(PngConstants.GAMMA, new Float(0.45455));
        props.put(PngConstants.CHROMATICITY, new float[]{
            0.3127f, 0.329f, 0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f,
        });
    }

    private static void read_tIME(DataInput in, int length, Map props)
    throws IOException
    {
        checkLength(PngConstants.tIME, length, 7);
        Calendar cal = Calendar.getInstance(TIME_ZONE);
        cal.set(in.readUnsignedShort(),
                check(in.readUnsignedByte(), 1, 12, "month") - 1,
                check(in.readUnsignedByte(), 1, 31, "day"),
                check(in.readUnsignedByte(), 0, 23, "hour"),
                check(in.readUnsignedByte(), 0, 59, "minute"),
                check(in.readUnsignedByte(), 0, 60, "second"));
        props.put(PngConstants.TIME, cal.getTime());
    }

    private static int check(int value, int min, int max, String field)
    throws PngException
    {
        if (value < min || value > max)
            throw new PngException("tIME " + field + " value " + value +
                                   " is out of bounds (" + min + "-" + max + ")", false);
        return value;
    }

    private static void read_sPLT(DataInput in, int length, Map props, PngImage png)
    throws IOException
    {
        String name = readKeyword(in, length);
        int sampleDepth = in.readByte();
        if (sampleDepth != 8 && sampleDepth != 16)
            throw new PngException("Sample depth must be 8 or 16", false);
        
        length -= (name.length() + 2);
        if ((length % ((sampleDepth == 8) ? 6 : 10)) != 0)
            throw new PngException("Incorrect sPLT data length for given sample depth", false);
        byte[] bytes = new byte[length];
        in.readFully(bytes);

        List palettes = (List)png.getProperty(PngConstants.SUGGESTED_PALETTES, List.class, false);
        if (palettes == null)
            props.put(PngConstants.SUGGESTED_PALETTES, palettes = new ArrayList());
        for (Iterator it = palettes.iterator(); it.hasNext();) {
            if (name.equals(((SuggestedPalette)it.next()).getName()))
                throw new PngException("Duplicate suggested palette name " + name, false);
        }
        palettes.add(new SuggestedPaletteImpl(name, sampleDepth, bytes));
    }

    private static void readText(int type, DataInput in, int length, Map props, PngImage png)
    throws IOException
    {
        byte[] bytes = new byte[length];
        in.readFully(bytes);
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));

        String keyword = readKeyword(data, length);
        String enc = ISO_8859_1;
        boolean compressed = false;
        boolean readMethod = true;
        String language = null;
        String translated = null;
        switch (type) {
        case PngConstants.tEXt:
            break;
        case PngConstants.zTXt:
            compressed = true;
            break;
        case PngConstants.iTXt:
            enc = UTF_8;
            int flag = data.readByte();
            int method = data.readByte();
            if (flag == 1) {
                compressed = true;
                readMethod = false;
                if (method != 0)
                    throw new PngException("Unrecognized " + PngConstants.getChunkName(type) + " compression method: " + method, false);
            } else if (flag != 0) {
                throw new PngException("Illegal " + PngConstants.getChunkName(type) + " compression flag: " + flag, false);
            }
            language = readString(data, data.available(), US_ASCII);
            // TODO: split language on hyphens, check that each component is 1-8 characters
            translated = readString(data, data.available(), UTF_8);
            // TODO: check for line breaks?
        }

        String text;
        if (compressed) {
            text = new String(readCompressed(data, data.available(), readMethod), enc);
        } else {
            text = new String(bytes, bytes.length - data.available(), data.available(), enc);
        }
        if (text.indexOf('\0') >= 0)
            throw new PngException("Text value contains null", false);
        List chunks = (List)png.getProperty(PngConstants.TEXT_CHUNKS, List.class, false);
        if (chunks == null)
            props.put(PngConstants.TEXT_CHUNKS, chunks = new ArrayList());
        chunks.add(new TextChunkImpl(keyword, text, language, translated, type));
    }

    private static void read_gIFg(DataInput in, int length, Map props)
    throws IOException
    {
        checkLength(PngConstants.gIFg, length, 4);
        int disposalMethod = in.readUnsignedByte();
        int userInputFlag = in.readUnsignedByte();
        int delayTime = in.readUnsignedShort();
        props.put(PngConstants.GIF_DISPOSAL_METHOD, Integers.valueOf(disposalMethod));
        props.put(PngConstants.GIF_USER_INPUT_FLAG, Integers.valueOf(userInputFlag));
        props.put(PngConstants.GIF_DELAY_TIME, Integers.valueOf(delayTime));
    }

    private static void read_oFFs(DataInput in, int length, Map props)
    throws IOException
    {
        checkLength(PngConstants.oFFs, length, 9);
        int x = in.readInt();
        int y = in.readInt();
        int unit = in.readByte();
        if (unit != PngConstants.POSITION_UNIT_PIXEL &&
            unit != PngConstants.POSITION_UNIT_MICROMETER)
            throw new PngException("Illegal oFFs chunk unit specifier: " + unit, false);
        props.put(PngConstants.POSITION_X, Integers.valueOf(x));
        props.put(PngConstants.POSITION_Y, Integers.valueOf(y));
        props.put(PngConstants.POSITION_UNIT, Integers.valueOf(unit));
    }

    private static void read_sCAL(DataInput in, int length, Map props)
    throws IOException
    {
        byte[] bytes = new byte[length];
        in.readFully(bytes);
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));
        
        int unit = data.readByte();
        if (unit != PngConstants.SCALE_UNIT_METER && unit != PngConstants.SCALE_UNIT_RADIAN)
            throw new PngException("Illegal sCAL chunk unit specifier: " + unit, false);
        double width = readFloatingPoint(data, data.available());
        double height = readFloatingPoint(data, data.available());
        if (width <= 0 || height <= 0)
            throw new PngException("sCAL measurements must be >= 0", false);
        props.put(PngConstants.SCALE_UNIT, Integers.valueOf(unit));
        props.put(PngConstants.PIXEL_WIDTH, new Double(width));
        props.put(PngConstants.PIXEL_HEIGHT, new Double(height));
    }

    private static void read_sTER(DataInput in, int length, Map props)
    throws IOException
    {
        checkLength(PngConstants.sTER, length, 1);
        int mode = in.readByte();
        switch (mode) {
        case PngConstants.STEREO_MODE_CROSS:
        case PngConstants.STEREO_MODE_DIVERGING:
            props.put(PngConstants.STEREO_MODE, Integers.valueOf(mode));
            break;
        default:
            throw new PngException("Unknown sTER mode: " + mode, false);
        }
    }

    public static void checkLength(int chunk, int length, int correct)
    throws PngException
    {
        if (length != correct)
            throw new PngException("Bad " + PngConstants.getChunkName(chunk) + " chunk length: " + length + " (expected " + correct + ")", true);
    }

    private static byte[] readCompressed(DataInput in, int length, boolean readMethod)
    throws IOException
    {
        if (readMethod) {
            int method = in.readByte();
            if (method != 0)
                throw new PngException("Unrecognized compression method: " + method, false);
            length--;
        }
        byte[] data = new byte[length];
        in.readFully(data);
        byte[] tmp = new byte[0x1000];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Inflater inf = new Inflater();
        inf.reset();
        inf.setInput(data, 0, length);
        try {
            while (!inf.needsInput()) {
                out.write(tmp, 0, inf.inflate(tmp));
            }
        } catch (DataFormatException e) {
            throw new PngException("Error reading compressed data", e, false);
        }
        return out.toByteArray();
    }

    private static String readString(DataInput in, int limit, String enc)
    throws IOException
    {
        return new String(readToNull(in, limit), enc);
    }

    private static String readKeyword(DataInput in, int limit)
    throws IOException
    {
        String keyword = readString(in, limit, ISO_8859_1);
        if (keyword.length() == 0 || keyword.length() > 79)
            throw new PngException("Invalid keyword length: " + keyword.length(), false);
        return keyword;
    }

    // TODO: performance
    private static byte[] readToNull(DataInput in, int limit)
    throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i < limit; i++) {
            int c = in.readUnsignedByte();
            if (c == 0)
                return out.toByteArray();
            out.write(c);
        }
        return out.toByteArray();
    }

    private static double readFloatingPoint(DataInput in, int limit)
    throws IOException
    {
        String s = readString(in, limit, "US-ASCII");
        int e = Math.max(s.indexOf('e'), s.indexOf('E'));
        double d = Double.valueOf(s.substring(0, (e < 0 ? s.length() : e))).doubleValue();
        if (e >= 0)
            d *= Math.pow(10d, Double.valueOf(s.substring(e + 1)).doubleValue());
        return d;
    }
}
