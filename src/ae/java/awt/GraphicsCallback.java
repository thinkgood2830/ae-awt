/*
 * Copyright 1999-2000 Sun Microsystems, Inc.  All Rights Reserved.
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

package ae.java.awt;

import ae.java.awt.peer.LightweightPeer;
import ae.sun.awt.SunGraphicsCallback;


abstract class GraphicsCallback extends SunGraphicsCallback {

    static final class PaintCallback extends GraphicsCallback {
        private static PaintCallback instance = new PaintCallback();

        private PaintCallback() {}
        public void run(Component comp, Graphics cg) {
            comp.paint(cg);
        }
        static PaintCallback getInstance() {
            return instance;
        }
    }
    static final class PrintCallback extends GraphicsCallback {
        private static PrintCallback instance = new PrintCallback();

        private PrintCallback() {}
        public void run(Component comp, Graphics cg) {
            comp.print(cg);
        }
        static PrintCallback getInstance() {
            return instance;
        }
    }
    static final class PaintAllCallback extends GraphicsCallback {
        private static PaintAllCallback instance = new PaintAllCallback();

        private PaintAllCallback() {}
        public void run(Component comp, Graphics cg) {
            comp.paintAll(cg);
        }
        static PaintAllCallback getInstance() {
            return instance;
        }
    }
    static final class PrintAllCallback extends GraphicsCallback {
        private static PrintAllCallback instance = new PrintAllCallback();

        private PrintAllCallback() {}
        public void run(Component comp, Graphics cg) {
            comp.printAll(cg);
        }
        static PrintAllCallback getInstance() {
            return instance;
        }
    }
    static final class PeerPaintCallback extends GraphicsCallback {
        private static PeerPaintCallback instance = new PeerPaintCallback();

        private PeerPaintCallback() {}
        public void run(Component comp, Graphics cg) {
            comp.validate();
            if (comp.peer instanceof LightweightPeer) {
                comp.lightweightPaint(cg);
            } else {
                comp.peer.paint(cg);
            }
        }
        static PeerPaintCallback getInstance() {
            return instance;
        }
    }
    static final class PeerPrintCallback extends GraphicsCallback {
        private static PeerPrintCallback instance = new PeerPrintCallback();

        private PeerPrintCallback() {}
        public void run(Component comp, Graphics cg) {
            comp.validate();
            if (comp.peer instanceof LightweightPeer) {
                comp.lightweightPrint(cg);
            } else {
                comp.peer.print(cg);
            }
        }
        static PeerPrintCallback getInstance() {
            return instance;
        }
    }
    static final class PaintHeavyweightComponentsCallback
        extends GraphicsCallback
    {
        private static PaintHeavyweightComponentsCallback instance =
            new PaintHeavyweightComponentsCallback();

        private PaintHeavyweightComponentsCallback() {}
        public void run(Component comp, Graphics cg) {
            if (comp.peer instanceof LightweightPeer) {
                comp.paintHeavyweightComponents(cg);
            } else {
                comp.paintAll(cg);
            }
        }
        static PaintHeavyweightComponentsCallback getInstance() {
            return instance;
        }
    }
    static final class PrintHeavyweightComponentsCallback
        extends GraphicsCallback
    {
        private static PrintHeavyweightComponentsCallback instance =
            new PrintHeavyweightComponentsCallback();

        private PrintHeavyweightComponentsCallback() {}
        public void run(Component comp, Graphics cg) {
            if (comp.peer instanceof LightweightPeer) {
                comp.printHeavyweightComponents(cg);
            } else {
                comp.printAll(cg);
            }
        }
        static PrintHeavyweightComponentsCallback getInstance() {
            return instance;
        }
    }
}
