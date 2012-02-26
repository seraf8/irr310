package com.irr310.client.script.js.objects;

import org.fenggui.appearance.TextAppearance;
import org.fenggui.binding.render.IFont;
import org.fenggui.binding.render.ImageFont;
import org.fenggui.binding.render.text.ITextRenderer;
import org.fenggui.text.content.factory.simple.TextStyle;
import org.fenggui.text.content.factory.simple.TextStyleEntry;
import org.fenggui.util.Alphabet;
import org.fenggui.util.fonttoolkit.FontFactory;

import fr.def.iss.vd2.lib_v3d.V3DColor;
import fr.def.iss.vd2.lib_v3d.gui.V3DGuiComponent;
import fr.def.iss.vd2.lib_v3d.gui.V3DGuiComponent.GuiYAlignment;
import fr.def.iss.vd2.lib_v3d.gui.V3DLabel;

public class GuiLabel implements GuiComponent {

    private final Gui gui;
    private V3DLabel component;

    public GuiLabel(Gui gui) {
        this.gui = gui;
        component = new V3DLabel("");
        component.setyAlignment(GuiYAlignment.BOTTOM);
        component.setFontStyle("Ubuntu", "bold", 16);
        this.setColor(new Color(0, 0, 0));
        
        
        
        

    }
    
    
    public void setFontStyle(String style, int size) {
        component.setFontStyle("Ubuntu", style, size);
    }

    private void setFontToDefaultStyle(TextAppearance appearance, IFont font, org.fenggui.util.Color color) {
        TextStyle def = appearance.getStyle(TextStyle.DEFAULTSTYLEKEY);
        def.getTextStyleEntry(TextStyleEntry.DEFAULTSTYLESTATEKEY).setColor(color);

        ITextRenderer renderer = appearance.getRenderer(ITextRenderer.DEFAULTTEXTRENDERERKEY).copy();
        renderer.setFont(font);
        appearance.addRenderer(ITextRenderer.DEFAULTTEXTRENDERERKEY, renderer);
    }

    public V3DGuiComponent getComponent() {
        return component;
    }

    public void setText(String text) {
        component.setText(text);
    }

    public void setPosition(Vec2 position) {
        component.setPosition((int) position.getX(), (int) position.getY());
    }

    public void setColor(Color color) {
        component.setColor(new V3DColor(color.r, color.g, color.b, color.a), V3DColor.transparent);
    }

}
