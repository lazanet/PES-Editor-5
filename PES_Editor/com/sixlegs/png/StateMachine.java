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

class StateMachine
{
    public static final int STATE_START = 0;
    public static final int STATE_SAW_IHDR = 1;
    public static final int STATE_SAW_IHDR_NO_PLTE = 2;
    public static final int STATE_SAW_PLTE = 3;
    public static final int STATE_IN_IDAT = 4;
    public static final int STATE_AFTER_IDAT = 5;
    public static final int STATE_END = 6;

    private PngImage png;
    private int state = STATE_START;
    private int type;

    public StateMachine(PngImage png)
    {
        this.png = png;
    }

    public int getState()
    {
        return state;
    }

    public int getType()
    {
        return type;
    }

    public void nextState(int type)
    throws PngException
    {
        state = nextState(png, state, this.type = type);
    }
        
    private static int nextState(PngImage png, int state, int type)
    throws PngException
    {
        for (int i = 0; i < 4; i++) {
            int c = 0xFF & (type >>> (8 * i));
            if (c < 65 || (c > 90 && c < 97) || c > 122)
                throw new PngException("Corrupted chunk type: 0x" + Integer.toHexString(type), true);
        }
        if (PngConstants.isPrivate(type) && !PngConstants.isAncillary(type))
            throw new PngException("Private critical chunk encountered: " + PngConstants.getChunkName(type), true);
        switch (state) {
        case STATE_START:
            if (type == PngConstants.IHDR)
                return STATE_SAW_IHDR;
            throw new PngException("IHDR chunk must be first chunk", true);
        case STATE_SAW_IHDR:
        case STATE_SAW_IHDR_NO_PLTE:
            switch (type) {
            case PngConstants.PLTE:
                return STATE_SAW_PLTE;
            case PngConstants.IDAT:
                errorIfPaletted(png);
                return STATE_IN_IDAT;
            case PngConstants.bKGD:
                return STATE_SAW_IHDR_NO_PLTE;
            case PngConstants.tRNS:
                errorIfPaletted(png);
                return STATE_SAW_IHDR_NO_PLTE;
            case PngConstants.hIST:
                throw new PngException("PLTE must precede hIST", true);
            }
            return state;
        case STATE_SAW_PLTE:
            switch (type) {
            case PngConstants.cHRM:
            case PngConstants.gAMA:
            case PngConstants.iCCP:
            case PngConstants.sBIT:
            case PngConstants.sRGB:
                throw new PngException(PngConstants.getChunkName(type) + " cannot appear after PLTE", true);
            case PngConstants.IDAT:
                return STATE_IN_IDAT;
            case PngConstants.IEND:
                throw new PngException("Required data chunk(s) not found", true);
            }
            return STATE_SAW_PLTE;
        default: // STATE_IN_IDAT, STATE_AFTER_IDAT
            switch (type) {
            case PngConstants.PLTE:
            case PngConstants.cHRM:
            case PngConstants.gAMA:
            case PngConstants.iCCP:
            case PngConstants.sBIT:
            case PngConstants.sRGB:
            case PngConstants.bKGD:
            case PngConstants.hIST:
            case PngConstants.tRNS:
            case PngConstants.pHYs:
            case PngConstants.sPLT:
            case PngConstants.oFFs:
            case PngConstants.pCAL:
            case PngConstants.sCAL:
            case PngConstants.sTER:
                throw new PngException(PngConstants.getChunkName(type) + " cannot appear after IDAT", true);
            case PngConstants.IEND:
                return STATE_END;
            case PngConstants.IDAT:
                if (state == STATE_IN_IDAT)
                    return STATE_IN_IDAT;
                throw new PngException("IDAT chunks must be consecutive", true);
            }
            return STATE_AFTER_IDAT;
        }
    }

    private static void errorIfPaletted(PngImage png)
    throws PngException
    {
        if (png.getColorType() == PngConstants.COLOR_TYPE_PALETTE)
            throw new PngException("Required PLTE chunk not found", true);
    }
}
