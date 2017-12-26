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
 * A suggested palette. Suggested palettes can be useful
 * when the display device is not capable of displaying
 * the full range of colors present in the image. 
 * @see PngImage#getProperty
 * @see PngConstants#SUGGESTED_PALETTES
 */
public interface SuggestedPalette
{
    /**
     * Returns palette name. This is any convenient name for
     * referring to the palette. The name will be unique across all
     * suggested palettes in the same image.
     */
    String getName();

    /**
     * Returns the number of samples.
     */
    int getSampleCount();

    /**
     * Returns the sample depth. This specifies the width of each color and alpha component
     * of each sample in this palette.
     * @return 8 or 16
     */
    int getSampleDepth();

    /**
     * Retrieve a sample value. The red, green, blue, and alpha components of the sample
     * at the given index are stored into the short array. Each component is of the depth
     * specified by {@link #getSampleDepth getSampleDepth}. The color samples are not
     * premultiplied by alpha. An alpha value of 0 means fully transparent.
     * @throws IndexOutOfBoundsException if index < 0, index >= {@link #getSampleCount getSampleCount}, or
     * {@code pixel.length} is less than 4
     * @throws NullPointerException if {@code pixel} is null
     * @param index the sample index
     * @param pixel the array in which to store the sample components
     */
    void getSample(int index, short[] pixel);

    /**
     * Retrieve a sample frequency value. The frequency value is proportional to the
     * fraction of pixels in the image that are closest to that palette entry in RGBA
     * space. The range of individual values will reasonably fill 0 to 65535.
     * Entries appear in decreasing order of frequency.
     * @param index the sample index
     */
    int getFrequency(int index);
}
