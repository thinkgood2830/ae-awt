/*
 * Copyright 2005 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package ae.sun.java2d.pipe;

import ae.java.awt.font.GlyphVector;
import ae.sun.awt.SunHints;
import ae.sun.font.GlyphList;
import ae.sun.java2d.SunGraphics2D;

/**
 * A delegate pipe of SG2D which implements redispatching of
 * for the src mode loops in the drawGlyphVector case where
 * the installed loop may not match the glyphvector.
 */

public abstract class GlyphListLoopPipe extends GlyphListPipe {

    protected void drawGlyphList(SunGraphics2D sg2d, GlyphList gl,
                                 int aaHint) {
        switch (aaHint) {
         case SunHints.INTVAL_TEXT_ANTIALIAS_OFF:
             sg2d.loops.drawGlyphListLoop.
                 DrawGlyphList(sg2d, sg2d.surfaceData, gl);
             return;
         case SunHints.INTVAL_TEXT_ANTIALIAS_ON:
             sg2d.loops.drawGlyphListAALoop.
                 DrawGlyphListAA(sg2d, sg2d.surfaceData, gl);
             return;
        case SunHints.INTVAL_TEXT_ANTIALIAS_LCD_HRGB:
        case SunHints.INTVAL_TEXT_ANTIALIAS_LCD_VRGB:
            sg2d.loops.drawGlyphListLCDLoop.
                DrawGlyphListLCD(sg2d,sg2d.surfaceData, gl);
            return;
        }
    }
}
