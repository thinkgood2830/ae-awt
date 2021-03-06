/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All Rights Reserved.
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

/*
  What is the dead simplest thing to do?
  Extend AbstractMap and don't optimize for anything.

  The only new api is 'getValues()' which returns the values struct as
  long as no map api has been called.  If any map api is called,
  create a real map and forward to it, and nuke values because of the
  possibility that the map has been changed.  This is easier than
  trying to create a map that only clears values if the map has been
  changed, or implementing the map API directly on top of the values
  struct.  We can always do that later if need be.
*/

package ae.sun.font;

import ae.java.awt.Paint;
import ae.java.awt.font.GraphicAttribute;
import ae.java.awt.font.NumericShaper;
import ae.java.awt.font.TextAttribute;
import ae.java.awt.font.TransformAttribute;
import ae.java.awt.geom.AffineTransform;
//import ae.java.awt.im.InputMethodHighlight;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import static ae.sun.font.AttributeValues.*;

public final class AttributeMap extends AbstractMap<TextAttribute, Object> {
    private AttributeValues values;
    private Map<TextAttribute, Object> delegateMap;

    public AttributeMap(AttributeValues values) {
        this.values = values;
    }

    public Set<Entry<TextAttribute, Object>> entrySet() {
        return delegate().entrySet();
    }

    public Object put(TextAttribute key, Object value) {
        return delegate().put(key, value);
    }

    // internal API
    public AttributeValues getValues() {
        return values;
    }

    private static boolean first = false; // debug
    private Map<TextAttribute, Object> delegate() {
        if (delegateMap == null) {
            if (first) {
                first = false;
                Thread.dumpStack();
            }
            delegateMap = values.toMap(new HashMap<TextAttribute, Object>(27));

            // nuke values, once map is accessible it might be mutated and values would
            // no longer reflect its contents
            values = null;
        }

        return delegateMap;
    }

    public String toString() {
        if (values != null) {
            return "map of " + values.toString();
        }
        return super.toString();
    }
}
