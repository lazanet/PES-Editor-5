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

import java.awt.Rectangle;

/**
 * Customizable parameters used by {@link PngImage} when decoding an image.
 * Instances of this class are immutable and can only be constructed
 * using the {@link Builder} inner-class.
 */
final public class PngConfig
{
    /** Read the entire image */
    public static final int READ_ALL = 0;
    /** Read only the header chunk */
    public static final int READ_HEADER = 1;
    /** Read all the metadata up to the image data */
    public static final int READ_UNTIL_DATA = 2;
    /** Read the entire image, skipping over the image data */
    public static final int READ_EXCEPT_DATA = 3;
    /** Read the entire image, skipping over all non-critical chunks except tRNS and gAMA */
    public static final int READ_EXCEPT_METADATA = 4;

    final int readLimit;
    final float defaultGamma;
    final float displayExponent;
    final boolean warningsFatal;
    final boolean progressive;
    final boolean reduce16;
    final boolean gammaCorrect;
    final Rectangle sourceRegion;
    final int[] subsampling;
    final boolean convertIndexed;

    PngConfig(Builder builder)
    {
        this.readLimit = builder.readLimit;
        this.defaultGamma = builder.defaultGamma;
        this.displayExponent = builder.displayExponent;
        this.warningsFatal = builder.warningsFatal;
        this.progressive = builder.progressive;
        this.reduce16 = builder.reduce16;
        this.gammaCorrect = builder.gammaCorrect;
        this.sourceRegion = builder.sourceRegion;
        this.subsampling = builder.subsampling;
        this.convertIndexed = builder.convertIndexed;
        
        boolean subsampleOn = getSourceXSubsampling() != 1 || getSourceYSubsampling() != 1;
        if (progressive && (subsampleOn || getSourceRegion() != null))
            throw new IllegalStateException("Progressive rendering cannot be used with source regions or subsampling");
    }

    /**
     * Builder class used to construct {@link PngConfig PngConfig} instances.
     * Each "setter" method returns an reference to the instance to enable
     * chaining multiple calls.
     * Call {@link #build} to construct a new {@code PngConfig} instance
     * using the current {@code Builder} settings. Example:
     * <pre>PngConfig config = new PngConfig.Builder()
     *        .readLimit(PngConfig.READ_EXCEPT_METADATA)
     *        .warningsFatal(true)
     *        .build();</pre>
     */
    final public static class Builder
    {
        private static final int[] DEFAULT_SUBSAMPLING = { 1, 1, 0, 0 };

        int readLimit = READ_ALL;
        float defaultGamma = 0.45455f;
        float displayExponent = 2.2f;
        boolean warningsFatal;
        boolean progressive;
        boolean reduce16 = true;
        boolean gammaCorrect = true;
        Rectangle sourceRegion;
        int[] subsampling = DEFAULT_SUBSAMPLING;
        boolean convertIndexed;

        /**
         * Create a new builder using default values.
         */
        public Builder()
        {
        }

        /**
         * Create a builder using values from the given configuration.
         * @param cfg the configuration to copy
         */
        public Builder(PngConfig cfg)
        {
            this.readLimit = cfg.readLimit;
            this.defaultGamma = cfg.defaultGamma;
            this.displayExponent = cfg.displayExponent;
            this.warningsFatal = cfg.warningsFatal;
            this.progressive = cfg.progressive;
            this.reduce16 = cfg.reduce16;
            this.gammaCorrect = cfg.gammaCorrect;
            this.subsampling = cfg.subsampling;
        }

        /**
         * Create a configuration using the current values of this builder.
         */
        public PngConfig build()
        {
            return new PngConfig(this);
        }
        
        /**
         * Enables or disables 16-bit reduction. If enabled, 16-bit samples are reduced to 8-bit samples by
         * shifting to the right by 8 bits. Default is <i>true</i>.
         * @param reduce16 enable 16-bit reduction
         */
        public Builder reduce16(boolean reduce16)
        {
            this.reduce16 = reduce16;
            return this;
        }

        /**
         * Sets the default gamma value. This value is used unless the image
         * contains an explicit gamma value. Initial value is <i>1/45455</i>.
         * @param defaultGamma the default gamma value
         */
        public Builder defaultGamma(float defaultGamma)
        {
            this.defaultGamma = defaultGamma;
            return this;
        }

        /**
         * Sets the default display exponent. The proper setting depends on monitor and OS gamma lookup
         * table settings, if any. The default value of <i>2.2</i> should
         * work well with most PC displays. If the operating system has
         * a gamma lookup table (e.g. Macintosh) the display exponent should be lower.
         * @param displayExponent the display exponent
         */
        public Builder displayExponent(float displayExponent)
        {
            this.displayExponent = displayExponent;
            return this;
        }

        /**
         * Enables or disables gamma correction. If enabled, decoded images will be gamma corrected.
         * Sets to false if your application will perform gamma correctly manually.
         * Default is <i>true</i>.
         * @param gammaCorrect use gamma correction
         * @see PngImage#getGamma
         * @see PngImage#getGammaTable
         */
        public Builder gammaCorrect(boolean gammaCorrect)
        {
            this.gammaCorrect = gammaCorrect;
            return this;
        }

        /**
         * Enables or disables progressive display for interlaced images.
         * If enabled, each received pixel is expanded (replicated) to fill a rectangle
         * covering the yet-to-be-transmitted pixel positions below and to the right
         * of the received pixel. This produces a "fade-in" effect as the new image
         * gradually replaces the old, at the cost of some additional processing time.
         * Default is <i>false</i>.
         * @param progressive use progressive display
         * @see PngImage#handlePass
         */
        public Builder progressive(boolean progressive)
        {
            this.progressive = progressive;
            return this;
        }
        
        /**
         * Configures how much of the image to read. Useful when one is interested
         * in only a portion of the image metadata, and would like to avoid
         * reading and/or decoding the actual image data.
         * @param readLimit
         *    {@link #READ_ALL READ_ALL},<br>
         *    {@link #READ_HEADER READ_HEADER},<br>
         *    {@link #READ_UNTIL_DATA READ_UNTIL_DATA},<br>
         *    {@link #READ_EXCEPT_DATA READ_EXCEPT_DATA},<br>
         *    or {@link #READ_EXCEPT_METADATA READ_EXCEPT_METADATA}
         */
        public Builder readLimit(int readLimit)
        {
            this.readLimit = readLimit;
            return this;
        }

        /**
         * Configures whether warnings should be treated as fatal errors.
         * All non-fatal {@link PngException} exceptions are caught and passed to the {@link PngImage#handleWarning}
         * method. If warnings are configured as fatal, that method will re-throw the
         * exception, which will abort image processing. Default is <i>false</i>.
         * @param warningsFatal true if warnings should be treated as fatal errors
         * @see PngImage#handleWarning
         */
        public Builder warningsFatal(boolean warningsFatal)
        {
            this.warningsFatal = warningsFatal;
            return this;
        }

        /**
         * Decode only a particular region of the source image. See the equivalent
         * {@linkplain javax.imageio.IIOParam#setSourceRegion ImageIO method}
         * for full documentation.
         */
        public Builder sourceRegion(Rectangle sourceRegion)
        {
            if (sourceRegion != null) {
                if (sourceRegion.x < 0 ||
                    sourceRegion.y < 0 ||
                    sourceRegion.width <= 0 ||
                    sourceRegion.height <= 0)
                    throw new IllegalArgumentException("invalid source region: " + sourceRegion);
                this.sourceRegion = new Rectangle(sourceRegion);
            } else {
                this.sourceRegion = null;
            }
            return this;
        }

        /**
         * Reduce the size of the decoded image (or source region) by only using
         * periodic rows and/or columns of the image. See the equivalent
         * {@linkplain javax.imageio.IIOParam#setSourceSubsampling ImageIO method}
         * for full documentation.
         */
        public Builder sourceSubsampling(int xsub, int ysub, int xoff, int yoff)
        {
            if (xsub <= 0 || ysub <= 0 ||
                xoff < 0 || xoff >= xsub ||
                yoff < 0 || yoff >= ysub)
                throw new IllegalArgumentException("invalid subsampling values");
            subsampling = new int[]{ xsub, ysub, xoff, yoff };
            return this;
        }

        /**
         * Whether to convert images with an indexed color model
         * (paletted and 1/2/4/8-bit grayscale) to use a component color model.
         */
        public Builder convertIndexed(boolean convertIndexed)
        {
            this.convertIndexed = convertIndexed;
            return this;
        }
    }

    /**
     * Return sthe current indexed image conversion setting.
     * @see Builder#convertIndexed
     */
    public boolean getConvertIndexed()
    {
        return convertIndexed;
    }

    /**
     * Returns the current 16-bit reduction setting.
     * @see Builder#reduce16
     */
    public boolean getReduce16()
    {
        return reduce16;
    }

    /**
     * Returns the current default gamma value.
     * @see Builder#defaultGamma
     */
    public float getDefaultGamma()
    {
        return defaultGamma;
    }

    /**
     * Returns the current gamma correction setting.
     * @see Builder#gammaCorrect
     */
    public boolean getGammaCorrect()
    {
        return gammaCorrect;
    }

    /**
     * Returns the current progressive display setting.
     * @see Builder#progressive
     */
    public boolean getProgressive()
    {
        return progressive;
    }

    /**
     * Returns the current display exponent.
     * @see Builder#displayExponent
     */
    public float getDisplayExponent()
    {
        return displayExponent;
    }
    
    /**
     * Returns the current read limit setting.
     * @return one of
     *    {@link #READ_ALL READ_ALL},<br>
     *    {@link #READ_HEADER READ_HEADER},<br>
     *    {@link #READ_UNTIL_DATA READ_UNTIL_DATA},<br>
     *    {@link #READ_EXCEPT_DATA READ_EXCEPT_DATA},<br>
     *    or {@link #READ_EXCEPT_METADATA READ_EXCEPT_METADATA}
     * @see Builder#readLimit
     */
    public int getReadLimit()
    {
        return readLimit;
    }

    /**
     * Returns whether warnings are treated as fatal errors.
     * @see Builder#warningsFatal
     */
    public boolean getWarningsFatal()
    {
        return warningsFatal;
    }

    /**
     * Returns the source region to be used.
     * @see Builder#sourceRegion
     */
    public Rectangle getSourceRegion()
    {
        return (sourceRegion != null) ? new Rectangle(sourceRegion) : null;
    }

    /**
     * Returns the number of source columns to advance for each pixel.
     * @see Builder#sourceSubsampling
     */
    public int getSourceXSubsampling()
    {
        return subsampling[0];
    }

    /**
     * Returns the number of rows to advance for each pixel.
     * @see Builder#sourceSubsampling
     */
    public int getSourceYSubsampling()
    {
        return subsampling[1];
    }

    /**
     * Returns the horizontal offset of the subsampling grid.
     * @see Builder#sourceSubsampling
     */
    public int getSubsamplingXOffset()
    {
        return subsampling[2];
    }

    /**
     * Returns the vertical offset of the subsampling grid.
     * @see Builder#sourceSubsampling
     */
    public int getSubsamplingYOffset()
    {
        return subsampling[3];
    }
}
