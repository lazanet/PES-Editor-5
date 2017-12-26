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

class TextChunkImpl
implements TextChunk
{
    private final String keyword;
    private final String text;
    private final String language;
    private final String translated;
    private final int type;
    
    public TextChunkImpl(String keyword, String text, String language, String translated, int type)
    {
        this.keyword = keyword;
        this.text = text;
        this.language = language;
        this.translated = translated;
        this.type = type;
    }
    
    public String getKeyword()
    {
        return keyword;
    }

    public String getTranslatedKeyword()
    {
        return translated;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getText()
    {
        return text;
    }

    public int getType()
    {
        return type;
    }
}
