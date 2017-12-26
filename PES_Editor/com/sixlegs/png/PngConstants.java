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

/**
 * This interface defines all registered chunk types and provides methods
 * helper methods to examine them. It also defines all of the keys used in the property map
 * of a decoded {@link PngImage}, and applicable enumerated values.
 * @see PngImage#getProperty
 * @see PngImage#getProperties
 */
abstract public class PngConstants
{
    
    /**
     * Returns {@code true} if the given chunk type has the ancillary bit set
     * (the first letter is lowercase).
     * An ancillary chunk is once which is not strictly necessary
     * in order to meaningfully display the contents of the file.
     * @param chunkType the chunk type
     * @return whether the chunk type ancillary bit is set
     */
    public static boolean isAncillary(int chunkType)
    {
        return ((chunkType & 0x20000000) != 0);
    }

    /**
     * Returns {@code true} if the given chunk type has the private bit set
     * (the second letter is lowercase).
     * All unregistered chunk types should have this bit set.
     * @param chunkType the chunk type
     * @return whether the chunk type private bit is set
     */
    public static boolean isPrivate(int chunkType)
    {
        return ((chunkType & 0x00200000) != 0);
    }

    /**
     * Returns {@code true} if the given chunk type has the reserved bit set
     * (the third letter is lowercase).
     * The meaning of this bit is currently undefined, but reserved for future use.
     * Images conforming to the current version of the PNG specification must
     * not have this bit set.
     * @param chunkType the chunk type
     * @return whether the chunk type reserved bit is set
     */
    public static boolean isReserved(int chunkType)
    {
        return ((chunkType & 0x00002000) != 0);
    }

    /**
     * Returns {@code true} if the given chunk type has the safe-to-copy bit set
     * (the fourth letter is lowercase).
     * Chunks marked as safe-to-copy may be copied to a modified PNG file
     * whether or not the software recognizes the chunk type.
     * @param chunkType the chunk type
     * @return whether the chunk safe-to-copy bit is set
     */
    public static boolean isSafeToCopy(int chunkType)
    {
        return ((chunkType & 0x00000020) != 0);
    }

    /**
     * Returns the four-character ASCII name corresponding to the given
     * chunk type. For example, {@code PngConstants.getChunkName(PngConstants.IHDR)} will
     * return {@code "IHDR"}.
     * @param chunkType the chunk type
     * @return the four-character ASCII chunk name
     */
    public static String getChunkName(int chunkType)
    {
        return ("" + 
                (char)((chunkType >>> 24) & 0xFF) + 
                (char)((chunkType >>> 16) & 0xFF) + 
                (char)((chunkType >>>  8) & 0xFF) + 
                (char)((chunkType       ) & 0xFF));
    }

    /**
     * Returns the chunk type corresponding to the given four-character
     * ASCII chunk name.
     * @param chunkName the four-character ASCII chunk name
     * @return the chunk type
     * @throws NullPointerException if {@code name} is null
     * @throws IndexOutOfBoundsException if {@code name} has less than four characters
     */
    public static int getChunkType(String chunkName)
    {
        return ((((int)chunkName.charAt(0) & 0xFF) << 24) | 
                (((int)chunkName.charAt(1) & 0xFF) << 16) | 
                (((int)chunkName.charAt(2) & 0xFF) <<  8) | 
                (((int)chunkName.charAt(3) & 0xFF)      ));
    }

    /** Eight byte magic number that begins all PNG images */
    public static final long SIGNATURE = 0x89504E470D0A1A0AL;
    
    /** Image header */
    public static final int IHDR = 0x49484452;
    /** Palette */
    public static final int PLTE = 0x504c5445;
    /** Image data */
    public static final int IDAT = 0x49444154;
    /** Image trailer */
    public static final int IEND = 0x49454e44;

    /** Background color */
    public static final int bKGD = 0x624b4744;
    /** Primary chromaticities */
    public static final int cHRM = 0x6348524d;
    /** Image gamma */
    public static final int gAMA = 0x67414d41;
    /** Palette histogram */
    public static final int hIST = 0x68495354;
    /** Embedded ICC profile */
    public static final int iCCP = 0x69434350;
    /** International textual data */
    public static final int iTXt = 0x69545874;
    /** Physical pixel dimensions */
    public static final int pHYs = 0x70485973;
    /** Significant bits */
    public static final int sBIT = 0x73424954;
    /** Suggested palette */
    public static final int sPLT = 0x73504c54;
    /** Standard RGB color space */
    public static final int sRGB = 0x73524742;
    /** Textual data */
    public static final int tEXt = 0x74455874;
    /** Image last-modification time */
    public static final int tIME = 0x74494d45;
    /** Transparency */
    public static final int tRNS = 0x74524e53;
    /** Compressed textual data */
    public static final int zTXt = 0x7a545874;

    /** Image offset */
    public static final int oFFs = 0x6f464673;
    /** Calibration of pixel values */
    public static final int pCAL = 0x7043414c;
    /** Physical scale of image subject */
    public static final int sCAL = 0x7343414c;
    /** GIF Graphic Control Extension */
    public static final int gIFg = 0x67494667;
    /** GIF Application Extension */
    public static final int gIFx = 0x67494678;
    /** Indicator of Stereo Image */
    public static final int sTER = 0x73544552;

    
    /** {@link #IHDR IHDR}: Bit depth */
    public static final String BIT_DEPTH = "bit_depth";
    /** {@link #IHDR IHDR}: Color type */
    public static final String COLOR_TYPE = "color_type";
    /** {@link #IHDR IHDR}: Compression method */
    public static final String COMPRESSION = "compression";
    /** {@link #IHDR IHDR}: Filter method */
    public static final String FILTER = "filter";
    /** {@link #gAMA gAMA}: Gamma */
    public static final String GAMMA = "gamma";
    /** {@link #IHDR IHDR}: Width */
    public static final String WIDTH = "width";
    /** {@link #IHDR IHDR}: Height */
    public static final String HEIGHT = "height";
    /** {@link #IHDR IHDR}: Interlace method */
    public static final String INTERLACE = "interlace";
    /** {@link #PLTE PLTE}: Palette entries */
    public static final String PALETTE = "palette";
    /** {@link #PLTE PLTE}: Palette alpha */
    public static final String PALETTE_ALPHA = "palette_alpha";
    /** {@link #tRNS tRNS}: Transparency samples */
    public static final String TRANSPARENCY = "transparency";
    /** {@link #bKGD bKGD}: Background samples */
    public static final String BACKGROUND = "background_rgb";
    /** {@link #pHYs pHYs}: Pixels per unit, X axis */
    public static final String PIXELS_PER_UNIT_X = "pixels_per_unit_x";
    /** {@link #pHYs pHYs}: Pixels per unit, Y axis */
    public static final String PIXELS_PER_UNIT_Y = "pixels_per_unit_y";
    /** {@link #sRGB sRGB}: Rendering intent */
    public static final String RENDERING_INTENT = "rendering_intent";
    /** {@link #sBIT sBIT}: Significant bits */
    public static final String SIGNIFICANT_BITS = "significant_bits";
    /** {@link #tEXt tEXt}/{@link #zTXt zTXt}/{@link #iTXt iTXt}: List of {@linkplain TextChunk text chunks} */
    public static final String TEXT_CHUNKS = "text_chunks";
    /** {@link #tIME tIME}: Image last-modification time */
    public static final String TIME = "time";
    /** {@link #pHYs pHYs}: Unit specifier */
    public static final String UNIT = "unit";
    /** {@link #cHRM cHRM}: Chromaticity */
    public static final String CHROMATICITY = "chromaticity";
    /** {@link #iCCP iCCP}: ICC profile */
    public static final String ICC_PROFILE = "icc_profile";
    /** {@link #iCCP iCCP}: ICC profile name */
    public static final String ICC_PROFILE_NAME = "icc_profile_name";
    /** {@link #hIST hIST}: Palette histogram */
    public static final String HISTOGRAM = "histogram";
    /** {@link #sPLT sPLT}: List of {@linkplain SuggestedPalette suggested palettes} */
    public static final String SUGGESTED_PALETTES = "suggested_palettes";

    /** {@link #gIFg gIFg}: GIF disposal method */
    public static final String GIF_DISPOSAL_METHOD = "gif_disposal_method";
    /** {@link #gIFg gIFg}: GIF user input flag */
    public static final String GIF_USER_INPUT_FLAG = "gif_user_input_flag";
    /** {@link #gIFg gIFg}: GIF delay time (hundredths of a second) */
    public static final String GIF_DELAY_TIME = "gif_delay_time";
    /** {@link #sCAL sCAL}: Unit for physical scale of image subject */
    public static final String SCALE_UNIT = "scale_unit";
    /** {@link #sCAL sCAL}: Physical width of pixel */
    public static final String PIXEL_WIDTH = "pixel_width";
    /** {@link #sCAL sCAL}: Physical height of pixel */
    public static final String PIXEL_HEIGHT = "pixel_height";
    /** {@link #oFFs oFFs}: Unit for image offset */
    public static final String POSITION_UNIT = "position_unit";
    /** {@link #sTER sTER}: Indicator of stereo image */
    public static final String STEREO_MODE = "stereo_mode";

    /** {@link #IHDR IHDR}: Grayscale color type */
    public static final int COLOR_TYPE_GRAY = 0;
    /** {@link #IHDR IHDR}: Grayscale+alpha color type */
    public static final int COLOR_TYPE_GRAY_ALPHA = 4;
    /** {@link #IHDR IHDR}: Palette color type */
    public static final int COLOR_TYPE_PALETTE = 3;
    /** {@link #IHDR IHDR}: RGB color type */
    public static final int COLOR_TYPE_RGB = 2;
    /** {@link #IHDR IHDR}: RGBA color type */
    public static final int COLOR_TYPE_RGB_ALPHA = 6;

    /** {@link #IHDR IHDR}: No interlace */
    public static final int INTERLACE_NONE = 0;
    /** {@link #IHDR IHDR}: Adam7 interlace */
    public static final int INTERLACE_ADAM7 = 1;

    /** {@link #IHDR IHDR}: Adaptive filtering */
    public static final int FILTER_BASE = 0;

    /** {@link #IHDR IHDR}: Deflate/inflate compression */
    public static final int COMPRESSION_BASE = 0;  

    /** {@link #pHYs pHYs}: Unit is unknown */
    public static final int UNIT_UNKNOWN = 0;
    /** {@link #pHYs pHYs}: Unit is the meter */
    public static final int UNIT_METER = 1;

    /** {@link #sRGB sRGB}: Perceptual rendering intent */
    public static final int SRGB_PERCEPTUAL = 0;
    /** {@link #sRGB sRGB}: Relative colorimetric rendering intent */
    public static final int SRGB_RELATIVE_COLORIMETRIC = 1;
    /** {@link #sRGB sRGB}: Saturation rendering intent */
    public static final int SRGB_SATURATION_PRESERVING = 2;
    /** {@link #sRGB sRGB}: Absolute colormetric rendering intent */
    public static final int SRGB_ABSOLUTE_COLORIMETRIC = 3;

    /** {@link #oFFs oFFs}: Image X position */
    public static final String POSITION_X = "position_x";
    /** {@link #oFFs oFFs}: Image Y position */
    public static final String POSITION_Y = "position_y";
    /** {@link #oFFs oFFs}: Unit is the pixel (true dimensions unspecified) */
    public static final int POSITION_UNIT_PIXEL = 0;
    /** {@link #oFFs oFFs}: Unit is the micrometer (10^-6 meter) */
    public static final int POSITION_UNIT_MICROMETER = 1;

    /** {@link #sCAL sCAL}: Unit is the meter */
    public static final int SCALE_UNIT_METER = 1;
    /** {@link #sCAL sCAL}: Unit is the radian */
    public static final int SCALE_UNIT_RADIAN = 2;

    /** {@link #sTER sTER}: Cross-fuse layout */
    public static final int STEREO_MODE_CROSS = 0;
    /** {@link #sTER sTER}: Diverging-fuse layout */
    public static final int STEREO_MODE_DIVERGING = 1;
}
