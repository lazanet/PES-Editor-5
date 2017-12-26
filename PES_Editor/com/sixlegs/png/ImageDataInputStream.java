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

class ImageDataInputStream
extends InputStream
{
    private final PngInputStream in;
    private final StateMachine machine;
    private final byte[] onebyte = new byte[1];
    private boolean done;

    public ImageDataInputStream(PngInputStream in, StateMachine machine)
    {
        this.in = in;
        this.machine = machine;
    }
    
    public int read()
    throws IOException
    {
        return (read(onebyte, 0, 1) == -1) ? -1 : 0xFF & onebyte[0];
    }

    public int read(byte[] b, int off, int len)
    throws IOException
    {
        if (done)
            return -1;
        try {
            int total = 0;
            while ((total != len) && !done) {
                while ((total != len) && in.getRemaining() > 0) {
                    int amt = Math.min(len - total, in.getRemaining());
                    in.readFully(b, off + total, amt);
                    total += amt;
                }
                if (in.getRemaining() <= 0) {
                    in.endChunk(machine.getType());
                    machine.nextState(in.startChunk());
                    done = machine.getType() != PngConstants.IDAT;
                }
            }
            return total;
        } catch (EOFException e) {
            done = true;
            return -1;
        }
    }
}
