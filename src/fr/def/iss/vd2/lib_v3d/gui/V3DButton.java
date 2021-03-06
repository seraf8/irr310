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

import org.fenggui.Button;
import org.fenggui.appearance.TextAppearance;
import org.fenggui.binding.render.IFont;
import org.fenggui.binding.render.ImageFont;
import org.fenggui.binding.render.text.ITextRenderer;
import org.fenggui.decorator.background.PlainBackground;
import org.fenggui.decorator.border.PaddingBorder;
import org.fenggui.text.content.factory.simple.TextStyle;
import org.fenggui.text.content.factory.simple.TextStyleEntry;
import org.fenggui.util.Color;
import org.fenggui.util.Dimension;
import org.fenggui.util.Point;
import org.fenggui.util.Span;

import fr.def.iss.vd2.lib_v3d.V3DColor;

/**
 * @author fberto
 */
public class V3DButton extends V3DGuiComponent {

    Button button;
    private int xPos;
    private int yPos;
    private V3DColor backgroundColor;
    private int topPadding = 0;
    private int leftPadding = 0;
    private int rightPadding = 0;
    private int bottomPadding = 0;

    public V3DButton(String text) {
        button = new Button(text);
        button.setXY(0, 0);
        button.setExpandable(true);
        button.getAppearance().add(new PlainBackground(new Color(1.0f, 1.0f, 1.0f, 0.8f)));
        button.setShrinkable(true);
        this.backgroundColor = V3DColor.transparent;
    }

    public void setColor(V3DColor foregroundColor, V3DColor backgroundColor) {
        this.backgroundColor = backgroundColor;
        updateBackground();
        // button.getAppearance().add(new PlainBackground(new
        // Color(backgroundColor.r, backgroundColor.g, backgroundColor.b,
        // backgroundColor.a)));

        TextStyle style = button.getAppearance().getStyle(TextStyle.DEFAULTSTYLEKEY);
        TextStyleEntry textEntryStyle = new TextStyleEntry();
        textEntryStyle.setColor(new Color(foregroundColor.r, foregroundColor.g, foregroundColor.b, foregroundColor.a));
        style.addStyle(TextStyleEntry.DEFAULTSTYLESTATEKEY, textEntryStyle);

    }

    public void setPadding(int topPadding, int leftPadding, int rightPadding, int bottomPadding) {
        this.topPadding = topPadding;
        this.leftPadding = leftPadding;
        this.rightPadding = rightPadding;
        this.bottomPadding = bottomPadding;
        updateBackground();
    }

    private void updateBackground() {
        button.getAppearance().removeAll();
        button.getAppearance().add(new PlainBackground(new Color(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a)));
        if (leftPadding != 0 || rightPadding != 0 || topPadding != 0 || bottomPadding != 0) {
            PaddingBorder border = new PaddingBorder(topPadding, leftPadding, rightPadding, bottomPadding);
            border.setColor(new Color(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a));
            border.setSpan(Span.PADDING);
            button.getAppearance().add(border);
        }

    }

    @Override
    public Button getFenGUIWidget() {
        return button;
    }

    @Override
    public boolean containsPoint(int x, int y) {
        if (x < this.x || y < this.y) {
            return false;
        }

        if (x > this.x + button.getWidth() || y > this.y + button.getHeight()) {
            return false;
        }

        return true;
    }

    @Override
    public void repack() {
        button.updateMinSize();
        if (parent != null) {
            xPos = 0;
            yPos = 0;
            if (xAlignment == GuiXAlignment.LEFT) {
                xPos = x;
            } else {
                xPos = parent.getWidth() - button.getWidth() - x;
            }

            if (yAlignment == GuiYAlignment.BOTTOM) {
                yPos = y;
            } else {
                yPos = parent.getHeight() - button.getMinHeight() - y;
            }

            button.setXY(xPos, yPos);
        }
        
        button.setSizeToMinSize();
        button.layout();
    }

    public void setText(String text) {
        button.setText(text);
        if (parent != null) {
            parent.repack();
        }
    }

    
    public Dimension getSize() {
        return button.getSize();
    }

    public Point getComputedPosition() {
        Point position = button.getPosition();
        if (parent != null) {
            return new Point(xPos, yPos);
        } else {
            return position;
        }

    }
    public void setFontStyle(String font, String style, int size) {
        setFontStyle(new LabelStyle(font, style, size));
    }


    private void setFontStyle(LabelStyle labelStyle) {
        ImageFont font = V3DGui.getFont(labelStyle);

        setFontToDefaultStyle(button.getAppearance(), font, org.fenggui.util.Color.BLACK);

    }

    private void setFontToDefaultStyle(TextAppearance appearance, IFont font, org.fenggui.util.Color color) {
        TextStyle def = appearance.getStyle(TextStyle.DEFAULTSTYLEKEY);
        def.getTextStyleEntry(TextStyleEntry.DEFAULTSTYLESTATEKEY).setColor(color);

        ITextRenderer renderer = appearance.getRenderer(ITextRenderer.DEFAULTTEXTRENDERERKEY).copy();
        renderer.setFont(font);
        appearance.addRenderer(ITextRenderer.DEFAULTTEXTRENDERERKEY, renderer);
    }

}
