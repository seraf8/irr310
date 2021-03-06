package com.irr310.client.graphics.fragments;

import fr.def.iss.vd2.lib_v3d.gui.V3DContainer;

public interface GuiTab  {

    public  V3DContainer getContentPane();
    
    public  V3DContainer getLabelPane();
    
    public  void refresh();
    
    public  void setActive(boolean active);

    public void setSize(int width, int height);
}
