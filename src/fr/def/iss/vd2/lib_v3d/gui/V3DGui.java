// Copyright 2010 DEF
//
// This file is part of V3dScene.
//
// V3dScene is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// V3dScene is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with V3dScene.  If not, see <http://www.gnu.org/licenses/>.

package fr.def.iss.vd2.lib_v3d.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.fenggui.Container;
import org.fenggui.Display;
import org.fenggui.FengGUI;
import org.fenggui.binding.render.ImageFont;
import org.fenggui.binding.render.jogl.EventBinding;
import org.fenggui.event.mouse.MouseButton;
import org.fenggui.layout.StaticLayout;
import org.fenggui.util.Alphabet;
import org.fenggui.util.fonttoolkit.FontFactory;

import fr.def.iss.vd2.lib_v3d.V3DMouseEvent;
import fr.def.iss.vd2.lib_v3d.camera.V3DCameraBinding;

/**
 * @author fberto
 */
public class V3DGui implements V3DLocalisable {

    
    private static Map<LabelStyle, ImageFont> fontMap = new HashMap<LabelStyle, ImageFont>();
    private Display display = null;
    List<V3DGuiComponent> guiComponentList = new CopyOnWriteArrayList<V3DGuiComponent>();
    boolean generated = false;
    private V3DCameraBinding parentBinding;
    Container rootContainer;
    EventBinding eventBinding;
    private int y;
    private int x;
    private int width;
    private int height;

    public V3DGui(V3DCameraBinding parent) {
        generated = false;
        parentBinding = parent;
    }

    public void generate() {
        generated = false;
    }

    public void doGenerate() {

        if (display == null) {
            display = FengGUI.createWidget(Display.class);
            FengGUI.setCanvasSize(this.width, this.height);
            // Repair
            // eventBinding = new EventBinding(canvas, display);
        } else {
            display.removeAllWidgets();
        }

        rootContainer = FengGUI.createWidget(Container.class);

        for (V3DGuiComponent component : guiComponentList) {
            rootContainer.addWidget(component.getFenGUIWidget());
            component.setParent(this);
        }

        display.addWidget(rootContainer);
        rootContainer.pack();
        rootContainer.setLayoutManager(new StaticLayout());
        rootContainer.layout();

        generated = true;

    }

    public boolean isFocused() {
        return false;
    }

    public boolean containsPoint(int x, int y) {
        for (V3DGuiComponent component : guiComponentList) {
            if (component.containsPoint(x - this.x, y - this.y)) {
                return true;
            }
        }

        return false;
    }

    public void add(V3DGuiComponent component) {
        guiComponentList.add(component);
        generate();
    }

    public void display() {
        if (!generated) {
            doGenerate();
            repack();
        }

        if (display != null) {
            display.display();
        }

    }

    @Override
    public void repack() {
        this.x = parentBinding.mouseX;
        this.y = parentBinding.mouseY;
        this.width = parentBinding.width;
        this.height = parentBinding.height;
        FengGUI.setCanvasSize(this.width, this.height);
        
        if (rootContainer != null) {
            
            
            rootContainer.setXY(parentBinding.x, parentBinding.y);
            rootContainer.setSize(parentBinding.width, parentBinding.height);
            display.setSize(parentBinding.width, parentBinding.height);
            for (V3DGuiComponent component : guiComponentList) {
                component.repack();
            }
            rootContainer.layout();
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getAbsoluteX() {
        return x;
    }

    @Override
    public int getAbsoluteY() {
        return y;
    }
    
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void remove(V3DGuiComponent component) {
        if (guiComponentList.contains(component)) {
            guiComponentList.remove(component);
            generate();
        }
    }

    public void clear() {
        guiComponentList.clear();
        generate();
    }

    public void onEvent(V3DMouseEvent e) {

        if (display != null) {
            
            MouseButton mouseButton = null;
            switch (e.getButton()) {
                case 1:
                    mouseButton = MouseButton.LEFT;
                    break;
                case 2:
                    mouseButton = MouseButton.RIGHT;
                    break;
                case 3:
                    mouseButton = MouseButton.MIDDLE;
                    break;
                default:
                    mouseButton =  MouseButton.LEFT;
            }
            
            switch (e.getAction()) {
                case MOUSE_CLICKED:
                    display.fireMouseClickEvent(e.getX(), e.getY(), mouseButton, 1);
                    break;
                case MOUSE_DRAGGED:
                    display.fireMouseDraggedEvent(e.getX(), e.getY(), mouseButton, 0);
                    break;
                case MOUSE_MOVED:
                    display.fireMouseMovedEvent(e.getX(), e.getY(), mouseButton, 0);
                    break;
                case MOUSE_PRESSED:
                    display.fireMousePressedEvent(e.getX(), e.getY(), mouseButton, 1);
                    break;
                case MOUSE_RELEASED:
                    display.fireMouseReleasedEvent(e.getX(), e.getY(), mouseButton, 1);
                    break;
                default:
                    break;
            }

        }
    }

    public static ImageFont getFont(LabelStyle labelStyle) {
        ImageFont font;
        if (fontMap.containsKey(labelStyle)) {
            font = fontMap.get(labelStyle);
        } else {
            font = createFont(labelStyle);
            fontMap.put(labelStyle, font);
        }
        return font;
    }
    private static ImageFont createFont(LabelStyle labelstyle) {
        
        String style = "R";
        if(labelstyle.getStyle() == "bold") {
            style = "B";
        }
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/"+labelstyle.getFont()+"-"+style+".ttf"));
            font = font.deriveFont((float)labelstyle.getSize());
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FontFactory.renderStandardFont(font, true, Alphabet.getDefaultAlphabet());
    }
        
}
