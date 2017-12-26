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

class Integers
{
    public static Integer valueOf(int i)
    {
        switch (i) {
        case 0: return INT_0;
        case 1: return INT_1;
        case 2: return INT_2;
        case 3: return INT_3;
        case 4: return INT_4;
        case 5: return INT_5;
        case 6: return INT_6;
        case 7: return INT_7;
        case 8: return INT_8;
        default:
            return new Integer(i);
        }
    }

    private static final Integer INT_0 = new Integer(0);
    private static final Integer INT_1 = new Integer(1);
    private static final Integer INT_2 = new Integer(2);
    private static final Integer INT_3 = new Integer(3);
    private static final Integer INT_4 = new Integer(4);
    private static final Integer INT_5 = new Integer(5);
    private static final Integer INT_6 = new Integer(6);
    private static final Integer INT_7 = new Integer(7);
    private static final Integer INT_8 = new Integer(8);
}
